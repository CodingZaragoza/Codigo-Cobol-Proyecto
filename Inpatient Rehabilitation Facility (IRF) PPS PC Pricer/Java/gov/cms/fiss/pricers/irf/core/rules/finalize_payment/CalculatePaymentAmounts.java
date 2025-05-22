package gov.cms.fiss.pricers.irf.core.rules.finalize_payment;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.api.v2.IrfPaymentData;
import gov.cms.fiss.pricers.irf.core.IrfPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Calculates payment amounts.
 *
 * <p>Converted from {@code 5000-FINAL-PAYMENTS} in the COBOL code.
 */
// TODO: DDS to evaluate for a better name - these are adjustments
public class CalculatePaymentAmounts
    implements CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  @Override
  public void calculate(IrfPricerContext calculationContext) {
    final IrfPaymentData paymentData = calculationContext.getPaymentData();
    // ** RULE: CLAIMS WHERE THE FED IS PAYING 100% OF THE PAYMENT ARE
    // ** ASSIGNED ZERO DOLLARS FOR THE PPS FACILITY SPECIFIC PAYMENT
    // ** AMOUNT
    // ** RULE: CLAIMS WHERE THE FEDS ARE NOT PAYING 100% OF THE BILL
    // ** HAVE THEIR PPS FEDERAL PAYMENT AMOUNT COMPUTED AS THE (PPS
    // ** FEDERAL RATE PERCENT TIMES THE PPS FEDERAL PAYMENT AMOUNT)
    // ** ROUNDED

    // IF PPS-FED-RATE-PCT = 1.0000
    if (BigDecimalUtils.equals(BigDecimal.ONE, paymentData.getFederalRatePercent())) {
      // MOVE 0 TO PPS-FAC-SPEC-PAY-AMT
      paymentData.setFacilitySpecificPayment(BigDecimalUtils.ZERO);
    } else {
      // ** RULE: CLAIMS WHERE THE FEDS ARE NOT PAYING 100% OF THE BILL
      // ** HAVE THEIR PPS FACILITY SPECIFIC PAYMENT AMOUNT COMPUTED AS
      // ** THE PPS FACILITY SPECIFIC RATE PERCENT TIMES THE PPS FACILITY
      // ** SPECIFIC RATE PREBLEND AMOUNT) ROUNDED

      // ELSE
      //    COMPUTE PPS-FED-PAY-AMT ROUNDED =
      //            (PPS-FED-RATE-PCT * PPS-FED-PAY-AMT)
      paymentData.setFederalPaymentAmount(
          paymentData
              .getFederalRatePercent()
              .multiply(paymentData.getFederalPaymentAmount())
              .setScale(2, RoundingMode.HALF_UP));

      // ** RULE: CLAIMS WHERE THE FEDS ARE NOT PAYING 100% OF THE BILL
      // ** HAVE THEIR PPS OUTLIER PAYMENT AMOUNT COMPUTED AS THE (PPS
      // ** FEDERAL RATE PERCENT TIMES THE PPS OUTLIER PAYMENT AMOUNT)
      // ** ROUNDED

      //    COMPUTE PPS-FAC-SPEC-PAY-AMT ROUNDED =
      //            (PPS-FAC-RATE-PCT * PPS-FAC-SPEC-RT-PREBLEND)
      paymentData.setFacilitySpecificPayment(
          paymentData
              .getFacilityRatePercent()
              .multiply(paymentData.getFacilitySpecificRatePreBlend())
              .setScale(2, RoundingMode.HALF_UP));

      // ** RULE: CLAIMS WHERE THE FEDS ARE NOT PAYING 100% OF THE BILL
      // ** HAVE THEIR PPS TEACH PAYMENT AMOUNT COMPUTED AS THE (PPS
      // ** FEDERAL RATE PERCENT TIMES THE PPS TEACH PAYMENT AMOUNT)
      // ** ROUNDED

      //    COMPUTE PPS-OUTLIER-PAY-AMT ROUNDED =
      //            (PPS-FED-RATE-PCT * PPS-OUTLIER-PAY-AMT)
      paymentData.setOutlierPayment(
          paymentData
              .getFederalRatePercent()
              .multiply(paymentData.getOutlierPayment())
              .setScale(2, RoundingMode.HALF_UP));

      // ** RULE: CLAIMS WHERE THE FEDS ARE NOT PAYING 100% OF THE BILL
      // ** HAVE THEIR PPS LOW INCOME PATIENT PAYMENT AMOUNT COMPUTED AS
      // ** THE (PPS FEDERAL RATE PERCENT TIMES THE PPS LOW INCOME PATIENT
      // ** PAYMENT AMOUNT) ROUNDED

      //    COMPUTE PPS-TEACH-PAY-AMT ROUNDED =
      //            (PPS-FED-RATE-PCT * PPS-TEACH-PAY-AMT)
      paymentData.setTeachingPayment(
          paymentData
              .getFederalRatePercent()
              .multiply(paymentData.getTeachingPaymentPenaltyAmount())
              .setScale(2, RoundingMode.HALF_UP));

      //    COMPUTE PPS-LIP-PAY-AMT ROUNDED =
      //            (PPS-FED-RATE-PCT * PPS-LIP-PAY-AMT).
      paymentData.setLowIncomePayment(
          paymentData
              .getFederalRatePercent()
              .multiply(paymentData.getLowIncomePayment())
              .setScale(2, RoundingMode.HALF_UP));
    }
  }
}
