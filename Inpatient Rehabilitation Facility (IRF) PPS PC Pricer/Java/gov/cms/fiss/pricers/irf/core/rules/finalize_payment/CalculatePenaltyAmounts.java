package gov.cms.fiss.pricers.irf.core.rules.finalize_payment;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.api.v2.IrfPaymentData;
import gov.cms.fiss.pricers.irf.core.IrfPricerContext;
import java.math.RoundingMode;

/**
 * Calculates penalty amounts.
 *
 * <p>Converted from {@code 5000-FINAL-PAYMENTS} in the COBOL code.
 */
public class CalculatePenaltyAmounts
    implements CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  @Override
  public void calculate(IrfPricerContext calculationContext) {
    final IrfPaymentData paymentData = calculationContext.getPaymentData();

    // ** RULE: ALL CLAIMS WITH PENALTIES HAVE THEIR PPS FEDERAL PENALTY
    // ** AMOUNT COMPUTED AS THE (PPS FEDERAL PAY AMOUNT TIMES 25%)
    // ** ROUNDED. IN OTHER WORDS, THEY PAY A 25% PENALTY OF THE FEDERAL
    // ** PAID AMOUNT

    // IF B-SPEC-PAY-IND = '2' OR '3'
    if (calculationContext.hasPenalties()) {
      // ** RULE: ALL CLAIMS WITH PENALTIES HAVE THEIR PPS FEDERAL PENALTY
      // ** AMOUNT COMPUTED AS THE (PPS FEDERAL PAY AMOUNT TIMES 25%)
      // ** ROUNDED. THE PENALTY AMOUNT IS SUBTRACTED FROM THE FEDERAL
      // ** PAY AMOUNT

      // COMPUTE PPS-FED-PENALTY-AMT ROUNDED =
      //           (PPS-FED-PAY-AMT * .25)
      paymentData.setFederalPenaltyAmount(
          paymentData
              .getFederalPaymentAmount()
              .multiply(IrfPricerContext.TWENTY_FIVE_PERCENT)
              .setScale(2, RoundingMode.HALF_UP));

      // ** RULE: ALL CLAIMS WITH PENALTIES HAVE THEIR PPS FEDERAL LOW
      // ** INCOME PATIENT PENALTY AMOUNT COMPUTED AS THE (PPS LOW INCOME
      // ** PATIENT PAY AMOUNT TIMES 25%) ROUNDED. IN OTHER WORDS, THEY
      // ** PAY A 25% LOW INCOME PATIENT PENALTY

      // COMPUTE PPS-FED-PAY-AMT =
      //            (PPS-FED-PAY-AMT - PPS-FED-PENALTY-AMT)
      paymentData.setFederalPaymentAmount(
          paymentData
              .getFederalPaymentAmount()
              .subtract(paymentData.getFederalPenaltyAmount())
              .setScale(2, RoundingMode.HALF_UP));

      // ** RULE: ALL CLAIMS WITH PENALTIES HAVE THEIR PPS LOW INCOME
      // ** PATIENT PENALTY AMOUNT COMPUTED AS THE (PPS LOW INCOME PATIENT
      // ** PAY AMOUNT MINUS THE PPS LOW INCOME PATIENT PENALTY AMOUNT)
      // ** ROUNDED.

      // COMPUTE PPS-LIP-PENALTY-AMT ROUNDED =
      //            (PPS-LIP-PAY-AMT * .25)
      paymentData.setLowIncomePaymentPenaltyAmount(
          paymentData
              .getLowIncomePayment()
              .multiply(IrfPricerContext.TWENTY_FIVE_PERCENT)
              .setScale(2, RoundingMode.HALF_UP));

      // ** RULE: ALL CLAIMS WITH PENALTIES HAVE THEIR PPS OUTLIER PENALTY
      // ** AMOUNT COMPUTED AS THE (PPS OUTLIER PAY AMOUNT TIMES 25%)
      // ** ROUNDED. IN OTHER WORDS, THEY PAY A 25% OUTLIER PENALTY

      // COMPUTE PPS-LIP-PAY-AMT =
      //            (PPS-LIP-PAY-AMT - PPS-LIP-PENALTY-AMT)
      paymentData.setLowIncomePayment(
          paymentData
              .getLowIncomePayment()
              .subtract(paymentData.getLowIncomePaymentPenaltyAmount())
              .setScale(2, RoundingMode.HALF_UP));

      // ** RULE: ALL CLAIMS WITH PENALTIES HAVE THEIR PPS OUTLIER PENALTY
      // ** AMOUNT COMPUTED AS THE (PPS OUTLIER PAY AMOUNT MINUS THE PPS
      // ** OUTLIER PENALTY AMOUNT) ROUNDED.

      // COMPUTE PPS-OUT-PENALTY-AMT ROUNDED =
      //            (PPS-OUTLIER-PAY-AMT * .25)
      paymentData.setOutlierPenaltyAmount(
          paymentData
              .getOutlierPayment()
              .multiply(IrfPricerContext.TWENTY_FIVE_PERCENT)
              .setScale(2, RoundingMode.HALF_UP));

      // ** RULE: ALL CLAIMS WITH PENALTIES HAVE THEIR PPS TEACH PENALTY
      // ** AMOUNT COMPUTED AS THE (PPS TEACH PAY AMOUNT TIMES 25%)
      // ** ROUNDED. IN OTHER WORDS, THEY PAY A 25% PENALTY OF THE TEACH
      // ** PAID AMOUNT

      // COMPUTE PPS-OUTLIER-PAY-AMT =
      //            (PPS-OUTLIER-PAY-AMT - PPS-OUT-PENALTY-AMT)
      paymentData.setOutlierPayment(
          paymentData
              .getOutlierPayment()
              .subtract(paymentData.getOutlierPenaltyAmount())
              .setScale(2, RoundingMode.HALF_UP));

      // ** RULE: ALL CLAIMS WITH PENALTIES HAVE THEIR PPS TEACH PENALTY
      // ** AMOUNT COMPUTED AS THE (PPS TEACH PAY AMOUNT MINUS THE PPS THE
      // ** PPS TEACH PENALTY AMOUNT) ROUNDED.

      // COMPUTE PPS-TEACH-PENALTY-AMT ROUNDED =
      //            (PPS-TEACH-PAY-AMT * .25)
      paymentData.setTeachingPaymentPenaltyAmount(
          paymentData
              .getTeachingPayment()
              .multiply(IrfPricerContext.TWENTY_FIVE_PERCENT)
              .setScale(2, RoundingMode.HALF_UP));

      // ** RULE: ALL CLAIMS WITH PENALTIES HAVE THEIR PPS TOTAL PENALTY
      // ** AMOUNT COMPUTED AS THE (PPS FEDERAL PENALTY AMOUNT PLUS THE
      // ** PPS LOW INCOME PATIENT PENALTY AMOUNT PLUS THE PPS OUTLIER
      // ** PENALTY AMOUNT PLUS THE PPS TEACH PENALTY AMOUNT.

      // COMPUTE PPS-TEACH-PAY-AMT =
      //            (PPS-TEACH-PAY-AMT - PPS-TEACH-PENALTY-AMT)
      paymentData.setTeachingPayment(
          paymentData
              .getTeachingPayment()
              .subtract(paymentData.getTeachingPaymentPenaltyAmount())
              .setScale(2, RoundingMode.HALF_UP));

      // COMPUTE PPS-TOTAL-PENALTY-AMT =
      //            (PPS-FED-PENALTY-AMT + PPS-LIP-PENALTY-AMT
      //            + PPS-OUT-PENALTY-AMT + PPS-TEACH-PENALTY-AMT).
      paymentData.setTotalPenaltyAmount(
          paymentData
              .getFederalPenaltyAmount()
              .add(paymentData.getLowIncomePaymentPenaltyAmount())
              .add(paymentData.getOutlierPenaltyAmount())
              .add(paymentData.getTeachingPaymentPenaltyAmount())
              .setScale(2, RoundingMode.HALF_UP));
    }
  }
}
