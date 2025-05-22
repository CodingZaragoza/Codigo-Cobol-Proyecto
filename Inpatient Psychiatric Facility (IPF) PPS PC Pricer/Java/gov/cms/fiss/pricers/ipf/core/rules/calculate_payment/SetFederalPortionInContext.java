package gov.cms.fiss.pricers.ipf.core.rules.calculate_payment;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;

/**
 * Set the federal portion of the payment.
 *
 * <p>Converted from {@code 3000-CALC-PAYMENT} in the COBOL code.
 */
public class SetFederalPortionInContext
    implements CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {
  @Override
  public void calculate(IpfPricerContext calculationContext) {
    //      MOVE IPF-FED-PAYMENT TO WK-FED-PORTION.
    calculationContext.setFederalPortion(calculationContext.getFederalPayment());
  }
}
