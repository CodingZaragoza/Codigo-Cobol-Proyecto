package gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.transitional_payments;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.RoundingMode;

/**
 * Adjusts final payment.
 *
 * <p>Converted from {@code 2000-CALCULATE-BUNDLED-FACTORS} (continued) in the COBOL code.
 *
 * @since 2020
 */
public class AdjustFinalPayment
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    //     COMPUTE H-PPS-FINAL-PAY-AMT = H-PPS-FINAL-PAY-AMT +
    //                                   H-TDAPA-PAYMENT.
    calculationContext.setFinalPaymentAmount(
        calculationContext
            .getFinalPaymentAmount()
            .add(calculationContext.getTdapaPayment())
            .setScale(2, RoundingMode.DOWN));
  }
}
