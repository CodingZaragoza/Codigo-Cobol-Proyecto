package gov.cms.fiss.pricers.ltch.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.core.LtchPricerContext;
import gov.cms.fiss.pricers.ltch.core.rules.rules2020.DetermineIppsWageIndex;

public class RecalculateLaborSharesForSupplementWageIndex extends DetermineIppsWageIndex
    implements CalculationRule<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {
  @Override
  public boolean shouldExecute(LtchPricerContext calculationContext) {
    return "2".equals(calculationContext.getProviderData().getSupplementalWageIndexIndicator());
  }

  @Override
  public void calculate(LtchPricerContext calculationContext) {
    calculationContext.calculateIppsLaborShares();
  }
}
