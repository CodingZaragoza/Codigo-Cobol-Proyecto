package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.EvaluatingCalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import java.util.List;

/**
 * Encapsulates post-outlier processing calculations as a sub-sequence of rules.
 *
 * <p>Converted from {@code 3000-CALC-PAYMENT} in the COBOL code (continued).
 *
 * @since 2019
 */
public class PostProcessOutlierCosts
    extends EvaluatingCalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {
  public PostProcessOutlierCosts(
      List<CalculationRule<IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext>>
          list) {
    super(list);
  }

  @Override
  public boolean shouldExecute(IppsPricerContext calculationContext) {
    return !calculationContext.isOutlierReconciliation();
  }
}
