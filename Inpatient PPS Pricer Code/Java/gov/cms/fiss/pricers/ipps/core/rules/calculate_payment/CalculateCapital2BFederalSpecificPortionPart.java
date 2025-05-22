package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;

/**
 * Determine the capital 2B federal specific portion part amount.
 *
 * <p>Converted from {@code 3000-CALC-PAYMENT} in the COBOL code (continued).
 *
 * @since 2019
 */
public class CalculateCapital2BFederalSpecificPortionPart
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    // ***********************************************************
    // ***  DETERMINES THE FEDERAL AMOUNT THAT WOULD BE PAID IF
    // ***  THE PROVIDER WAS TYPE B-HOLD-HARMLESS 100% FED RATE
    //     COMPUTE H-CAPI2-B-FSP-PART ROUNDED = H-CAPI-FSP-PART.
    calculationContext.setCapital2BFederalSpecificPortionPart(
        calculationContext.getCapitalFederalSpecificPortionPart());
  }
}
