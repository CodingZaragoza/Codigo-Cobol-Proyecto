package gov.cms.fiss.pricers.ipf.core.rules.calculate_payment;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;

/**
 * Calculate the federal payment.
 *
 * <p>Converted from {@code 3000-CALC-PAYMENT} in the COBOL code.
 */
public class CalculateFederalPayment
    implements CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {
  @Override
  public void calculate(IpfPricerContext calculationContext) {
    //      COMPUTE IPF-FED-PAYMENT ROUNDED =
    //                       WK-FED-PORTION + WK-TEACH-PORTION.
    calculationContext
        .getAdditionalVariables()
        .setFederalPayment(
            calculationContext.getFederalPortion().add(calculationContext.getTeachingPortion()));
  }
}
