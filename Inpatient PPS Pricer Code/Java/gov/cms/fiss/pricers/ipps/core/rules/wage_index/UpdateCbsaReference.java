package gov.cms.fiss.pricers.ipps.core.rules.wage_index;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.CbsaReference;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.ResultCode;
import gov.cms.fiss.pricers.ipps.core.tables.CbsaWageIndexEntry;
import org.apache.commons.lang3.StringUtils;

/**
 * Determines the default wage index value.
 *
 * <p>Converted from {@code 0650-N-GET-CBSA-WAGE-INDX} in the COBOL code.
 *
 * @since 2019
 */
public class UpdateCbsaReference
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public boolean shouldExecute(IppsPricerContext calculationContext) {
    return ResultCode.RC_00_OK == calculationContext.getResultCode();
  }

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final CbsaWageIndexEntry cbsaTableEntry =
        calculationContext.getCbsaWageIndexEntry(calculationContext.getCbsaLocation());
    final CbsaReference cbsaReference = calculationContext.getCbsaReference();

    //     IF  B-N-DISCHARGE-DATE NOT < T-CBSA-EFF-DATE (MA2)
    //       IF (HOLD-PROV-CBSA = '   98'  OR
    //           HOLD-PROV-CBSA = '   99') OR
    //          (T-CBSA-EFF-DATE (MA2) >= W-FY-BEGIN-DATE AND
    //           T-CBSA-EFF-DATE (MA2) <= W-FY-END-DATE)
    //         MOVE T-CBSA            (MA2) TO W-NEW-CBSA-X
    //         MOVE T-CBSA-EFF-DATE   (MA2) TO W-NEW-CBSA-EFF-DATE
    //         MOVE T-CBSA-WAGE-INDX1 (MA2) TO W-NEW-CBSA-WI
    //         IF P-NEW-CBSA-WI-RECLASS
    //            MOVE T-CBSA-WAGE-INDX2 (MA2) TO W-NEW-CBSA-WI.
    if (LocalDateUtils.isAfterOrEqual(
            calculationContext.getDischargeDate(), cbsaTableEntry.getEffectiveDate())
        && (StringUtils.equalsAny(
                calculationContext.getCbsaLocation(),
                IppsPricerContext.INDIAN_SPECIAL_CBSA_LOCATION,
                IppsPricerContext.INDIAN_DEFAULT_CBSA_LOCATION)
            || LocalDateUtils.inRange(
                cbsaTableEntry.getEffectiveDate(),
                calculationContext.fiscalYearStart(),
                calculationContext.fiscalYearEnd()))) {
      cbsaReference.setCbsa(cbsaTableEntry.getCbsa());
      cbsaReference.setEffectiveDate(cbsaTableEntry.getEffectiveDate());
      cbsaReference.setWageIndex(cbsaTableEntry.getGeographicWageIndex());
      if (calculationContext.isCbsaSpecialPaymentIndicatorReclassified()) {
        cbsaReference.setWageIndex(cbsaTableEntry.getReclassifiedWageIndex());
      }
    }
  }
}
