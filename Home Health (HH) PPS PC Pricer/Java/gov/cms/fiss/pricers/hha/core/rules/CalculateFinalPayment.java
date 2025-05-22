package gov.cms.fiss.pricers.hha.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.EvaluatingCalculationRule;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingRequest;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingResponse;
import gov.cms.fiss.pricers.hha.core.HhaPricerContext;
import java.util.List;

public class CalculateFinalPayment
    extends EvaluatingCalculationRule<
        HhaClaimPricingRequest, HhaClaimPricingResponse, HhaPricerContext> {

  public CalculateFinalPayment(
      List<CalculationRule<HhaClaimPricingRequest, HhaClaimPricingResponse, HhaPricerContext>>
          calculationRules) {
    super(calculationRules);
  }

  @Override
  public boolean shouldExecute(HhaPricerContext calculationContext) {
    return calculationContext.isBillTypeClaim();
  }
}
