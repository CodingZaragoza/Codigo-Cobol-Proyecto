package gov.cms.fiss.pricers.esrd.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.EvaluatingCalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.util.List;

/**
 * Encapsulates the rules that move the calculation results to the output data structures.
 *
 * <p>Converted from {@code 9100-MOVE-RESULTS} in the COBOL code.
 *
 * @since 2020
 */
public class MoveResults
    extends EvaluatingCalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  public MoveResults(
      List<CalculationRule<EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext>>
          calculationRules) {
    super(calculationRules);
  }
}
