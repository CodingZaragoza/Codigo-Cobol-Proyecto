package gov.cms.fiss.pricers.hha.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.EvaluatingCalculationRule;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingRequest;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingResponse;
import gov.cms.fiss.pricers.hha.core.HhaPricerContext;
import java.util.List;

/**
 * HHDRV and HHCAL repeat several validations. To ensure comparison testing is not adversely
 * impacted several validations are duplicated here as well.
 */
public class ValidateInput
    extends EvaluatingCalculationRule<
        HhaClaimPricingRequest, HhaClaimPricingResponse, HhaPricerContext> {
  public ValidateInput(
      List<CalculationRule<HhaClaimPricingRequest, HhaClaimPricingResponse, HhaPricerContext>>
          calculationRules) {
    super(calculationRules);
  }
}
