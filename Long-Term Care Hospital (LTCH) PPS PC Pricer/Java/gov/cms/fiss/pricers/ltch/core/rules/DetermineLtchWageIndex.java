package gov.cms.fiss.pricers.ltch.core.rules;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimData;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.api.v2.LtchPaymentData;
import gov.cms.fiss.pricers.ltch.core.LtchPricerContext;
import gov.cms.fiss.pricers.ltch.core.codes.ReturnCode;
import gov.cms.fiss.pricers.ltch.core.models.LtchWageIndexTableEntry;
import gov.cms.fiss.pricers.ltch.core.tables.CbsaWageIndexEntry;
import gov.cms.fiss.pricers.ltch.core.tables.DataTables;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;

/**
 * GET LTCH WAGE INDEX.
 *
 * <p>Converted from {@code 0550-GET-CBSA}
 */
public class DetermineLtchWageIndex
    implements CalculationRule<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {

  @Override
  public void calculate(LtchPricerContext calculationContext) {
    final LtchClaimData claimData = calculationContext.getClaimData();
    final InpatientProviderData providerData = calculationContext.getProviderData();
    final LtchPaymentData paymentData = calculationContext.getPaymentData();
    final LocalDate fyBegin = calculationContext.getFyBegin();
    final LocalDate fyEnd = calculationContext.getFyEnd();
    final LtchWageIndexTableEntry wageIndexTable = calculationContext.getLtchWageIndexTableEntry();
    final DataTables dataTables = calculationContext.getDataTables();
    boolean specialWageIndexSet = false;
    if (calculationContext.isSpecialPaymentIndicatorSpecial(
        providerData.getSpecialPaymentIndicator())) {
      // * USE SPECIAL WAGE INDEX WHEN INDICATED - FOR LTCH WAGE INDEX    *
      // * TO USE THE SPECIAL WAGE INDEX IT MUST:                         *
      // *   1) BE GREATER THAN 0, AND                                    *
      // *   2) BE IN A PSF RECORD WITH AN EFFECTIVE DATE WITHIN THE      *
      // *      CLAIM'S FISCAL YEAR.                                      *
      if (BigDecimalUtils.isGreaterThanZero(providerData.getSpecialWageIndex())
          && LocalDateUtils.inRange(providerData.getEffectiveDate(), fyBegin, fyEnd)) {
        // MOVE ZEROS                    TO W-NEW-CBSA
        // MOVE P-NEW-EFF-DATE           TO W-NEW-EFF-DATE-C
        // MOVE P-NEW-SPECIAL-WAGE-INDEX TO W-NEW-INDEX1-RECORD-C
        // MOVE P-NEW-SPECIAL-WAGE-INDEX TO W-NEW-INDEX2-RECORD-C
        // MOVE P-NEW-SPECIAL-WAGE-INDEX TO W-NEW-INDEX3-RECORD-C
        wageIndexTable.setCbsa(0);
        wageIndexTable.setEffectiveDate(providerData.getEffectiveDate());
        wageIndexTable.setLtchWageIndex1(providerData.getSpecialWageIndex());
        wageIndexTable.setLtchWageIndex2(providerData.getSpecialWageIndex());
        wageIndexTable.setLtchWageIndex3(providerData.getSpecialWageIndex());
        specialWageIndexSet = true;
      } else {
        calculationContext.applyReturnCode(ReturnCode.INVALID_WAGE_INDEX_52);
      }
    }

    // *6-20-16
    // *ADDED FOLLOWING LINE TO ALLOW LTCH PRICER TO WORK
    // *WITH THE NEW ARIZONA STATE CODE OF 00
    String trimmedGeolocation;
    if (providerData.getCbsaActualGeographicLocation() != null) {
      trimmedGeolocation = providerData.getCbsaActualGeographicLocation().trim();
    } else {
      trimmedGeolocation = "";
    }
    if ("00".equalsIgnoreCase(trimmedGeolocation)) {
      trimmedGeolocation = "03";
    }

    // * TO SELECT THE WAGE INDEX, IT MUST BE EITHER:                   *
    // * 1) AN INDIAN HEALTH CBSA (98 OR 99) WITH AN EFFECTIVE DATE ON  *
    // *    OR BEFORE THE CLAIM DISCHARGE DATE, OR                      *
    // * 2) A CBSA WITH AN EFFECTIVE DATE ON OR BEFORE THE CLAIM
    // *    DISCHARGE DATE AND A CLAIM DISCHARGE DATE ON OR BEFORE
    // *    09/30/2009 OR
    // * 3) A NON-INDIAN HEATLH CBSA WITH AN EFFECTIVE DATE ON OR BEFORE*
    // *    THE CLAIM DISHARGE DATE AND WITHIN THE CLAIM'S FISCAL YEAR  *
    final CbsaWageIndexEntry cbsaIndex =
        dataTables.getCbsaWageIndexEntry(trimmedGeolocation, providerData.getEffectiveDate());
    if (!specialWageIndexSet
        && cbsaIndex != null
        && LocalDateUtils.isAfterOrEqual(claimData.getDischargeDate(), cbsaIndex.getEffectiveDate())
        && (StringUtils.equalsAny(trimmedGeolocation, "98", "99")
            || LocalDateUtils.inRange(cbsaIndex.getEffectiveDate(), fyBegin, fyEnd))) {
      // MOVE CBSAX-CBSA (CU2)        TO W-NEW-CBSA
      // MOVE CBSAX-EFF-DATE (CU2)    TO W-NEW-EFF-DATE-C
      // MOVE CBSAX-WAGE-INDEX1 (CU2) TO W-NEW-INDEX1-RECORD-C
      // MOVE CBSAX-WAGE-INDEX2 (CU2) TO W-NEW-INDEX2-RECORD-C
      // MOVE CBSAX-WAGE-INDEX3 (CU2) TO W-NEW-INDEX3-RECORD-C
      wageIndexTable.setCbsa(Integer.valueOf(trimmedGeolocation));
      wageIndexTable.setEffectiveDate(cbsaIndex.getEffectiveDate());
      wageIndexTable.setLtchWageIndex1(BigDecimalUtils.ZERO);
      wageIndexTable.setLtchWageIndex2(BigDecimalUtils.ZERO);
      wageIndexTable.setLtchWageIndex3(cbsaIndex.getGeographicWageIndex());
    } else if (cbsaIndex == null) {
      calculationContext.applyReturnCode(ReturnCode.CBSA_WAGE_INDEX_NOT_FOUND_60);
    }
    calculationContext.setPaymentData(paymentData);
    calculationContext.setLtchWageIndexTableEntry(wageIndexTable);
  }
}
