package gov.cms.fiss.pricers.ltch.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationEvaluator;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.core.LtchPricerContext;
import gov.cms.fiss.pricers.ltch.core.codes.ReturnCode;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

/**
 * SkipOnErrorCode applies its conditional to each of its immediate children, rather than applying
 * it once. This problem seems to be unique to LTCH and would add significant complexity to abstract
 * further.
 */
public class SkipOnErrorCode
    implements CalculationRule<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {

  private final CalculationEvaluator<
          LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext>
      evaluator;

  public SkipOnErrorCode(
      List<CalculationRule<LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext>>
          rules) {
    evaluator =
        new CalculationEvaluator<>(rules.stream().map(Mutator::new).collect(Collectors.toList()));
  }

  public SkipOnErrorCode(
      CalculationRule<LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> rule) {
    evaluator = new CalculationEvaluator<>(List.of(new Mutator(rule)));
  }

  @Override
  public void calculate(LtchPricerContext pricerContext) {
    evaluator.evaluateRulesForContext(pricerContext);
  }

  private static class Mutator
      implements CalculationRule<
          LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {

    private final CalculationRule<
            LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext>
        rule;

    public Mutator(
        CalculationRule<LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext>
            rule) {
      this.rule = rule;
    }

    @Override
    public boolean shouldExecute(LtchPricerContext calculationContext) {
      return StringUtils.equals(
              ReturnCode.NORMAL_DRG_00.getCode(), calculationContext.getReturnCode())
          && rule.shouldExecute(calculationContext);
    }

    @Override
    public void calculate(LtchPricerContext calculationContext) {
      rule.calculate(calculationContext);
    }
  }
}
