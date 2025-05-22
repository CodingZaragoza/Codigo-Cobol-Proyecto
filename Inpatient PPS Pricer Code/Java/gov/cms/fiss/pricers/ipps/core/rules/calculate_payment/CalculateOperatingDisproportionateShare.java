package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.EvaluatingCalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import java.util.List;

/**
 * Encapsulates the claim's eligibility determination for a calculated operating disproportionate
 * share hospital adjustment as a sub-sequence of rules.
 *
 * <p>Converted from {@code 3900A-CALC-OPER-DSH} in the COBOL code.
 */
public class CalculateOperatingDisproportionateShare
    extends EvaluatingCalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  public CalculateOperatingDisproportionateShare(
      List<CalculationRule<IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext>>
          calculationRules) {
    super(calculationRules);
  }
}
