package gov.cms.fiss.pricers.irf.core.rules.finalize_payment;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.api.v2.IrfPaymentData;
import gov.cms.fiss.pricers.irf.core.IrfPricerContext;
import gov.cms.fiss.pricers.irf.core.ResultCode;
import java.math.BigDecimal;

/**
 * Updates the result code based on calculated values.
 *
 * <p>Converted from {@code 5000-FINAL-PAYMENTS} in the COBOL code.
 */
public class UpdateResultCode
    implements CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  @Override
  public void calculate(IrfPricerContext calculationContext) {
    final IrfPaymentData paymentData = calculationContext.getPaymentData();

    // ** REQUIREMENT: THE SYSTEM MUST RETURN A SUCCESSFUL PAYMENT CODE
    // ** SHOWING THAT THE CLAIM WAS PAID AS A NORMAL CASE MIX GROUP
    // ** PAYMENT (CODE 01) IF NO EDITS FAILED AND THE FEDS ARE PAYING
    // ** 100% OF THE CLAIM AND THERE WERE NO PATIENT TRANSFERS AND
    // ** THERE WAS AN OUTLIER PAYMENT.
    // ** REQUIREMENT: THE SYSTEM MUST RETURN A SUCCESSFUL PAYMENT CODE
    // ** SHOWING THAT THE CLAIM WAS PAID AS A NORMAL CASE MIX GROUP
    // ** PAYMENT (CODE 00) IF NO EDITS FAILED AND THE FEDS ARE PAYING
    // ** 100% OF THE CLAIM AND THERE WERE NO PATIENT TRANSFERS AND
    // ** THERE WAS NOT A OUTLIER PAYMENT.

    // IF PPS-FED-RATE-PCT = 1.0000
    if (BigDecimalUtils.equals(BigDecimal.ONE, paymentData.getFederalRatePercent())) {
      // IF PPS-TRANSFER-PCT = 1.0000
      if (BigDecimalUtils.equals(BigDecimal.ONE, paymentData.getTransferPercent())) {
        // IF PPS-OUTLIER-PAY-AMT > 0.0
        if (BigDecimalUtils.isGreaterThanZero(paymentData.getOutlierPayment())) {
          // MOVE 01 TO PPS-RTC
          calculationContext.applyResultCode(ResultCode.CMG_PAYMENT_OUTLIER_01);
        }

        // ** REQUIREMENT: THE SYSTEM MUST RETURN A SUCCESSFUL PAYMENT CODE
        // ** SHOWING THAT THE CLAIM WAS PAID AS A TRANSFER PER DIEM PAYMENT
        // ** (CODE 03) IF NO EDITS FAILED AND THE FEDS ARE PAYING 100% OF
        // ** THE CLAIM AND THERE WAS AN OUTLIER PAYMENT.
        // ** REQUIREMENT: THE SYSTEM MUST RETURN A SUCCESSFUL PAYMENT CODE
        // ** SHOWING THAT THE CLAIM WAS PAID AS A TRANSFER PER DIEM PAYMENT
        // ** (CODE 02) IF NO EDITS FAILED AND THE FEDS ARE PAYING 100% OF
        // ** THE CLAIM AND THERE WAS NO OUTLIER PAYMENT.
      } else if (BigDecimalUtils.isGreaterThanZero(paymentData.getOutlierPayment())) {
        // ELSE
        //   IF PPS-OUTLIER-PAY-AMT > 0.0
        //      MOVE 03 TO PPS-RTC
        calculationContext.applyResultCode(ResultCode.TRANSFER_PAID_OUTLIER_03);
      } else {
        //  ELSE
        //     MOVE 02 TO PPS-RTC
        calculationContext.applyResultCode(ResultCode.TRANSFER_PAID_NO_OUTLIER_02);
      }

      // ** REQUIREMENT: THE SYSTEM MUST RETURN A SUCCESSFUL PAYMENT CODE
      // ** SHOWING THAT THE CLAIM WAS PAID AS A BLENDED PAYMENT (CODE 05)
      // ** IF NO EDITS FAILED AND THE FEDS ARE NOT PAYING 100% OF THE
      // ** CLAIM AND THERE WERE NO PATIENT TRANSFERS AND THERE WAS AN
      // ** OUTLIER PAYMENT.
      // ** REQUIREMENT: THE SYSTEM MUST RETURN A SUCCESSFUL PAYMENT CODE
      // ** SHOWING THAT THE CLAIM WAS PAID AS A BLENDED PAYMENT (CODE 04)
      // ** IF NO EDITS FAILED AND THE FEDS ARE NOT PAYING 100% OF THE
      // ** CLAIM AND THERE WERE NO PATIENT TRANSFERS AND THERE WAS NO
      // ** OUTLIER PAYMENT.
    } else if (BigDecimalUtils.equals(BigDecimal.ONE, paymentData.getTransferPercent())) {
      // ELSE
      //    IF PPS-TRANSFER-PCT = 1.0000
      //       IF PPS-OUTLIER-PAY-AMT > 0.0
      if (BigDecimalUtils.isGreaterThanZero(paymentData.getOutlierPayment())) {
        //        MOVE 05 TO PPS-RTC
        calculationContext.applyResultCode(ResultCode.BLEND_CMG_PAY_OUTLIER_05);
      } else {
        //     ELSE
        //        MOVE 04 TO PPS-RTC
        calculationContext.applyResultCode(ResultCode.BLEND_CMG_PAY_NO_OUTLIER_04);
      }

      // ** REQUIREMENT: THE SYSTEM MUST RETURN A SUCCESSFUL PAYMENT CODE
      // ** SHOWING THAT THE CLAIM WAS PAID AS A BLENDED TRANSFER PAYMENT
      // ** (CODE 07) IF NO EDITS FAILED AND THE FEDS ARE NOT PAYING 100%
      // ** OF THE CLAIM AND THERE WAS A PATIENT TRANSFER AND THERE WAS AN
      // ** OUTLIER PAYMENT.
      // ** REQUIREMENT: THE SYSTEM MUST RETURN A SUCCESSFUL PAYMENT CODE
      // ** SHOWING THAT THE CLAIM WAS PAID AS A BLENDED TRANSFER PAYMENT
      // ** (CODE 06) IF NO EDITS FAILED AND THE FEDS ARE NOT PAYING 100%
      // ** OF THE CLAIM AND THERE WAS A PATIENT TRANSFER AND THERE WAS NO
      // ** OUTLIER PAYMENT.
    } else if (BigDecimalUtils.isGreaterThanZero(paymentData.getOutlierPayment())) {
      // ELSE
      //    IF PPS-OUTLIER-PAY-AMT > 0.0
      //       MOVE 07 TO PPS-RTC
      calculationContext.applyResultCode(ResultCode.BLEND_TRNSFR_PAY_OUTLIER_07);
    } else {
      //    ELSE
      //       MOVE 06 TO PPS-RTC.
      calculationContext.applyResultCode(ResultCode.BLEND_TRNSFR_PAY_NO_OUTLIER_06);
    }
  }
}
