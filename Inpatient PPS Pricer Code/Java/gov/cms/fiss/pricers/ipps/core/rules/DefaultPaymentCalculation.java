package gov.cms.fiss.pricers.ipps.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.EvaluatingCalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.ResultCode;
import java.util.List;

/**
 * Encapsulates the claim payment calculations as a sub-sequence of rules.
 *
 * <p>Converted from {@code 3000-CALC-PAYMENT} in the COBOL code.
 */
public class DefaultPaymentCalculation
    extends EvaluatingCalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  public DefaultPaymentCalculation(
      List<CalculationRule<IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext>>
          calculationRules) {
    super(calculationRules);
  }

  @Override
  public boolean shouldExecute(IppsPricerContext calculationContext) {
    return ResultCode.RC_00_OK == calculationContext.getResultCode();
  }
}
