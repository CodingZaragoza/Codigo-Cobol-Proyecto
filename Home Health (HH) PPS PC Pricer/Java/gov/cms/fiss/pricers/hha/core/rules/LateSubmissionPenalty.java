package gov.cms.fiss.pricers.hha.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimData;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingRequest;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingResponse;
import gov.cms.fiss.pricers.hha.api.v2.HhaPaymentData;
import gov.cms.fiss.pricers.hha.core.HhaPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;

/**
 * CALCULATE THE LATE SUBMISSION PENALTY AMOUNT
 *
 * <p>Converted from {@code 9110-COMPUTE-LATE-SUB-PENALTY} in the COBOL code.
 */
public class LateSubmissionPenalty
    implements CalculationRule<HhaClaimPricingRequest, HhaClaimPricingResponse, HhaPricerContext> {
  private static final BigDecimal THIRTY_DAYS = new BigDecimal("30");

  @Override
  public boolean shouldExecute(HhaPricerContext calculationContext) {
    return BigDecimalUtils.isGreaterThanZero(calculationContext.getPaymentData().getHhrgPayment())
        && !calculationContext.isLateFilingPenaltyWaiverIndicator()
        && calculationContext.getClaimData().getNoticeReceiptDate() != null;
  }

  @Override
  public void calculate(HhaPricerContext calculationContext) {
    final HhaClaimData claimData = calculationContext.getClaimData();
    final HhaPaymentData paymentData = calculationContext.getPaymentData();
    final BigDecimal hrgPayment = paymentData.getHhrgPayment();
    final BigDecimal outlierPayment = paymentData.getOutlierPayment();
    long daysDifference =
        claimData.getServiceFromDate().until(claimData.getNoticeReceiptDate(), ChronoUnit.DAYS);

    // CALCULATE LATE SUBMISSION PENALTY IF DAYS DIFFERENCE IS GREATER THAN 5
    if (daysDifference <= 5) {
      return;
    }

    // *----------------------------------------------------------------*
    // * IF THE DAYS DIFFERENCE IS GREAT THAN 30 DAYS, USE 30 DAYS.     *
    // * IF THE DAYS DIFFERENCE IS LESS THAN 31 DAYS, USE ACTUAL DAYS.  *
    // *----------------------------------------------------------------*
    daysDifference = Math.min(daysDifference, 30);

    // *----------------------------------------------------------------*
    // * COMPUTE HRG-PAY PENALTY AMOUNT                                 *
    // *----------------------------------------------------------------*

    // COMPUTE WS-HRG-PENALTY ROUNDED =
    //         H-HHA-HRG-PAY * WS-DAYS-DIFFERENCE / 30.
    final BigDecimal hrgPenalty =
        hrgPayment
            .multiply(BigDecimal.valueOf(daysDifference))
            .divide(THIRTY_DAYS, 2, RoundingMode.HALF_UP);

    // *----------------------------------------------------------------*
    // * COMPUTE HRG-PAY REDUCED BY PENALTY                             *
    // *----------------------------------------------------------------*

    // COMPUTE WS-HRG-REDUCED ROUNDED =
    //         H-HHA-HRG-PAY - WS-HRG-PENALTY.
    final BigDecimal reducedHrg = hrgPayment.subtract(hrgPenalty).abs();
    paymentData.setHhrgPayment(reducedHrg);

    // *----------------------------------------------------------------*
    // * COMPUTE OUTLIER PENALTY AMOUNT                                 *
    // *----------------------------------------------------------------*

    // COMPUTE WS-OUTL-PENALTY ROUNDED =
    //         H-HHA-OUTLIER-PAYMENT * WS-DAYS-DIFFERENCE / 30.
    final BigDecimal outlierPenalty =
        outlierPayment
            .multiply(BigDecimal.valueOf(daysDifference))
            .divide(THIRTY_DAYS, 2, RoundingMode.HALF_UP);

    // *----------------------------------------------------------------*
    // * COMPUTE OUTLIER REDUCED BY PENALTY                             *
    // *----------------------------------------------------------------*

    // COMPUTE WS-OUTL-REDUCED ROUNDED =
    //         H-HHA-OUTLIER-PAYMENT - WS-OUTL-PENALTY.
    final BigDecimal reducedOutlier = outlierPayment.subtract(outlierPenalty);
    paymentData.setOutlierPayment(reducedOutlier);

    // *----------------------------------------------------------------*
    // * COMPUTE THE TOTAL-PAYMENT AMOUNT                               *
    // *----------------------------------------------------------------*

    // COMPUTE H-HHA-TOTAL-PAYMENT ROUNDED =
    //         H-HHA-HRG-PAY + H-HHA-OUTLIER-PAYMENT.
    paymentData.setTotalPayment(reducedHrg.add(reducedOutlier));

    // *----------------------------------------------------------------*
    // * COMPUTE LATE SUBMISSION PENALTY AMOUNT                         *
    // *----------------------------------------------------------------*

    // COMPUTE H-HHA-LATE-SUB-PEN-AMT ROUNDED =
    //         WS-HRG-PENALTY + WS-OUTL-PENALTY.
    paymentData.setLateSubmissionPenaltyAmount(hrgPenalty.add(outlierPenalty));
  }
}
