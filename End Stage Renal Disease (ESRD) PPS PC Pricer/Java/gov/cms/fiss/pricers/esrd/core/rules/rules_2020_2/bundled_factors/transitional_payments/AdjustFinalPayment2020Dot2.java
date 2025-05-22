package gov.cms.fiss.pricers.esrd.core.rules.rules_2020_2.bundled_factors.transitional_payments;

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
public class AdjustFinalPayment2020Dot2
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    //     COMPUTE H-FINAL-AMT-WITHOUT-HDPA = H-FINAL-AMT-WITHOUT-HDPA +
    //                                        H-TDAPA-PAYMENT.
    calculationContext.setFinalAmountWithoutHdpa(
        calculationContext
            .getFinalAmountWithoutHdpa()
            .add(calculationContext.getTdapaPayment().setScale(2, RoundingMode.DOWN))
            .setScale(2, RoundingMode.HALF_UP));

    //     COMPUTE H-FINAL-AMT-WITH-HDPA = H-FINAL-AMT-WITH-HDPA +
    //                                     H-TDAPA-PAYMENT.
    calculationContext.setFinalAmountWithHdpa(
        calculationContext
            .getFinalAmountWithHdpa()
            .add(calculationContext.getTdapaPayment().setScale(2, RoundingMode.DOWN))
            .setScale(2, RoundingMode.HALF_UP));

    //     IF B-DATA-CODE = '94'
    //        THEN MOVE H-FINAL-AMT-WITH-HDPA TO H-PPS-FINAL-PAY-AMT
    //        ELSE MOVE H-FINAL-AMT-WITHOUT-HDPA TO H-PPS-FINAL-PAY-AMT
    //     END-IF.
    if (calculationContext.hasDemoCode(EsrdPricerContext.DEMO_CODE_ETC_PARTICIPANT)) {
      calculationContext.setFinalPaymentAmount(calculationContext.getFinalAmountWithHdpa());
    } else {
      calculationContext.setFinalPaymentAmount(calculationContext.getFinalAmountWithoutHdpa());
    }
  }
}
