package gov.cms.fiss.pricers.ltch.core.rules.rules2021;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.core.LtchPricerContext;
import gov.cms.fiss.pricers.ltch.core.codes.ReturnCode;
import gov.cms.fiss.pricers.ltch.core.rules.rules2020.DetermineIppsWageIndex;
import gov.cms.fiss.pricers.ltch.core.tables.CbsaWageIndexEntry;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;

public class DetermineIppsWageIndex2021 extends DetermineIppsWageIndex
    implements CalculationRule<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {

  @Override
  protected CbsaWageIndexEntry getIppsCbsaTableEntry(
      LtchPricerContext calculationContext, CbsaWageIndexEntry entry) {
    if (entry != null
        && BigDecimalUtils.isGreaterThan(
            calculationContext.getRuralWageIndexFloor().getRuralFloorWageIndex(),
            entry.getGeographicWageIndex())) {
      entry =
          CbsaWageIndexEntry.builder()
              .cbsa(calculationContext.getRuralWageIndexFloor().getCbsa())
              .effectiveDate(calculationContext.getRuralWageIndexFloor().getEffectiveDate())
              .geographicWageIndex(
                  calculationContext.getRuralWageIndexFloor().getRuralFloorWageIndex())
              .build();
    }
    return entry;
  }

  @Override
  protected void assignFloorCBSA(
      LtchPricerContext calculationContext, String returnCode, String geolocation) {
    final LocalDate dischargeDate = calculationContext.getClaimData().getDischargeDate();
    CbsaWageIndexEntry entry = CbsaWageIndexEntry.builder().build();
    if (StringUtils.equals(ReturnCode.NORMAL_DRG_00.getCode(), returnCode)) {
      entry = getRuralFloorIpps2021(calculationContext, geolocation, dischargeDate);
    }
    calculationContext.setRuralWageIndexFloor(entry);
  }

  /** Converted from 0190-GET-RURAL-FLOOR-IPPS in COBOL. */
  private CbsaWageIndexEntry getRuralFloorIpps2021(
      LtchPricerContext context, String geolocation, LocalDate dischargeDate) {
    CbsaWageIndexEntry ruralEntry =
        context.getDataTables().getIppsCbsaWageIndex(geolocation, dischargeDate);
    if (ruralEntry == null) {
      ruralEntry = CbsaWageIndexEntry.DEFAULT.build();
    }
    return ruralEntry;
  }
}
