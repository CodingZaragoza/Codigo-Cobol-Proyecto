package gov.cms.fiss.pricers.ipps.core.rules.cost_factor_determination;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.EvaluatingCalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import java.util.List;

/**
 * Encapsulates the labor rate determination as a sub-sequence of rules.
 *
 * <p>Converted from {@code 2050-RATES-TB} in the COBOL code.
 */
public class RetrieveLaborCosts
    extends EvaluatingCalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  public RetrieveLaborCosts(
      List<CalculationRule<IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext>>
          calculationRules) {
    super(calculationRules);
  }
}
