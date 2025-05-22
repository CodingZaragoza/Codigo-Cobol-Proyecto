package gov.cms.fiss.pricers.ltch.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.core.LtchPricerContext;
import java.math.BigDecimal;
import org.apache.commons.lang3.StringUtils;

public class AdjustCapitalGeographicAreaFactor
    implements CalculationRule<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {

  @Override
  public boolean shouldExecute(LtchPricerContext calculationContext) {
    return StringUtils.equals(
            calculationContext.getProviderData().getSupplementalWageIndexIndicator(), "2")
        && BigDecimalUtils.isGreaterThanZero(calculationContext.getSupplementalWageIndex());
  }

  @Override
  public void calculate(LtchPricerContext calculationContext) {
    calculationContext.setHoldCapitalGeographicAreaFactor(
        BigDecimalUtils.pow(
            calculationContext.getSupplementalWageIndex(), new BigDecimal("0.6848"), 10));
  }
}
