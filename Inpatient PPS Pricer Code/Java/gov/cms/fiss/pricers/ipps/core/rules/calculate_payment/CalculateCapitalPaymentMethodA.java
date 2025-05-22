package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import java.math.BigDecimal;

/**
 * Determines the capital sole community hospital amount.
 *
 * <p>Converted from {@code 3000-CALC-PAYMENT} in the COBOL code (continued).
 *
 * @since 2019
 */
public class CalculateCapitalPaymentMethodA
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    // ***********************************************************
    // ***  CAPITAL PAYMENT METHOD A
    //     IF P-N-SCH-REBASED-FY90 OR P-N-EACH
    //        MOVE 1.00 TO H-CAPI-SCH
    //     ELSE
    //        MOVE 0.85 TO H-CAPI-SCH.
    if (calculationContext.isSchRebasedFy90ProviderType()
        || calculationContext.isEachProviderType()) {
      calculationContext.setCapitalSch(BigDecimal.ONE);
    } else {
      calculationContext.setCapitalSch(new BigDecimal("0.85"));
    }
  }
}
