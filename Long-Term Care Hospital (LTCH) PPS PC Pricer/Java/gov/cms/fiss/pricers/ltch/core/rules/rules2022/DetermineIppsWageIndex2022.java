package gov.cms.fiss.pricers.ltch.core.rules.rules2022;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.core.LtchPricerContext;
import gov.cms.fiss.pricers.ltch.core.rules.rules2021.DetermineIppsWageIndex2021;
import gov.cms.fiss.pricers.ltch.core.tables.CbsaWageIndexEntry;
import java.time.LocalDate;
import java.util.Optional;

public class DetermineIppsWageIndex2022 extends DetermineIppsWageIndex2021
    implements CalculationRule<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {

  @Override
  protected CbsaWageIndexEntry getIppsCbsaTableEntry(
      LtchPricerContext calculationContext, CbsaWageIndexEntry entry) {
    entry = super.getIppsCbsaTableEntry(calculationContext, entry);
    calculationContext.setImputedWageIndexFloor(
        getImputedWageFloorIpps(
            calculationContext,
            getGeolocation(calculationContext.getProviderData().getStateCode()),
            calculationContext.getClaimData().getDischargeDate()));
    if (entry != null
        && BigDecimalUtils.isGreaterThan(
            calculationContext.getImputedWageIndexFloor().getImputedFloorWageIndex(),
            entry.getGeographicWageIndex())) {
      entry =
          CbsaWageIndexEntry.builder()
              .cbsa(calculationContext.getImputedWageIndexFloor().getCbsa())
              .effectiveDate(calculationContext.getImputedWageIndexFloor().getEffectiveDate())
              .geographicWageIndex(
                  calculationContext.getImputedWageIndexFloor().getImputedFloorWageIndex())
              .build();
    }

    return entry;
  }

  private CbsaWageIndexEntry getImputedWageFloorIpps(
      LtchPricerContext context, String geolocation, LocalDate dischargeDate) {
    return Optional.ofNullable(
            context.getDataTables().getIppsCbsaWageIndex(geolocation, dischargeDate))
        .orElse(CbsaWageIndexEntry.builder().build());
  }
}
