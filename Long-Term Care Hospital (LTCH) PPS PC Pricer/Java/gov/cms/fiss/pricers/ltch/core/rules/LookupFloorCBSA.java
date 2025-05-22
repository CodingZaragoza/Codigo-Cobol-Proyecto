package gov.cms.fiss.pricers.ltch.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.core.LtchPricerContext;
import gov.cms.fiss.pricers.ltch.core.tables.WageIppsIndexRuralEntry;
import java.time.LocalDate;
import java.util.Optional;

public class LookupFloorCBSA
    implements CalculationRule<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {

  @Override
  public void calculate(LtchPricerContext calculationContext) {
    final String geolocation = calculationContext.getProviderData().getStateCode();
    final LocalDate dischargeDate = calculationContext.getClaimData().getDischargeDate();
    final WageIppsIndexRuralEntry ruralEntry =
        Optional.ofNullable(
                calculationContext
                    .getDataTables()
                    .getWageIppsIndexRural(geolocation, dischargeDate))
            .orElse(WageIppsIndexRuralEntry.DEFAULT.build());
    calculationContext.setHoldProvIppsCbsaRural(ruralEntry);
  }
}
