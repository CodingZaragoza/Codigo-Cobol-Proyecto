package gov.cms.fiss.pricers.hha.core.rules;

import gov.cms.fiss.pricers.common.api.annotations.FixedValue;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingRequest;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingResponse;
import gov.cms.fiss.pricers.hha.core.HhaPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ApplyRuralAddOn
    implements CalculationRule<HhaClaimPricingRequest, HhaClaimPricingResponse, HhaPricerContext> {
  @Override
  public void calculate(HhaPricerContext calculationContext) {
    final @FixedValue BigDecimal ruralMultiplier = calculationContext.getRuralAddon();
    calculationContext.setFederalEpisodeRateAmount(
        calculationContext
            .getFederalEpisodeRateAmount()
            .multiply(ruralMultiplier)
            .setScale(2, RoundingMode.HALF_UP));
    calculationContext.setOutlierThresholdAmount(
        calculationContext
            .getOutlierThresholdAmount()
            .multiply(ruralMultiplier)
            .setScale(2, RoundingMode.HALF_UP));
  }
}
