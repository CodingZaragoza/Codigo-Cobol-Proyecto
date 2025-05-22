package gov.cms.fiss.pricers.ltch.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.EvaluatingCalculationRule;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.core.LtchPricerContext;
import java.util.List;

public class SetFinalReturnCodes
    extends EvaluatingCalculationRule<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {

  /** 7000-SET-FINAL-RETURN-CODES. SET FINAL RETURN CODES FOR PRICED CLAIMS */
  public SetFinalReturnCodes(
      List<CalculationRule<LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext>>
          calculationRules) {
    super(calculationRules);
  }
}
