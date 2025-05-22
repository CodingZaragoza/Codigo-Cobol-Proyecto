package gov.cms.fiss.pricers.snf.core.rules.rate_selection;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.EvaluatingCalculationRule;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingRequest;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingResponse;
import gov.cms.fiss.pricers.snf.core.SnfPricerContext;
import java.util.List;

/**
 * RATE COMPONENTS SELECTION
 *
 * <p>Converted from {@code 600-CAPTURE-RATE-COMPONENTS} in the COBOL code.
 */
public class PdpmRateComponentSelection
    extends EvaluatingCalculationRule<
        SnfClaimPricingRequest, SnfClaimPricingResponse, SnfPricerContext> {
  public PdpmRateComponentSelection(
      List<CalculationRule<SnfClaimPricingRequest, SnfClaimPricingResponse, SnfPricerContext>>
          rules) {
    super(rules);
  }
}
