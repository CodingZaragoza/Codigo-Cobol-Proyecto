package gov.cms.fiss.pricers.ipf.core.rules.calculate_payment;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimData;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Do the per diem calculation adjusted over the total stay, which is used in more than one place.
 *
 * <pre>
 * **************************************************************
 * **  CALCULATE THE DAY LOS FOR TOTAL PAYMENT W/O  TEACH-ADJ
 * **************************************************************
 * </pre>
 *
 * <p>Converted from {@code 3000-CALC-PAYMENT} in the COBOL code.
 */
public class CalculatePerDiemOverStayForContext
    implements CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {
  @Override
  public void calculate(IpfPricerContext calculationContext) {

    final IpfClaimData claimData = calculationContext.getClaimData();
    BigDecimal federalPayment = BigDecimal.ZERO;
    final BigDecimal perDiemAmount = calculationContext.getPerDiemAmount();

    final int lengthOfStay = claimData.getLengthOfStay();
    //      MOVE ZEROES TO DAYS-UPTO-21
    //                     DAYS-OVER-21
    //                     IPF-FED-PAYMENT.

    //      MOVE 001    TO SUB
    //                     SUB2.
    //
    //      COMPUTE SUB2 = SUB2 + BILL-PRIOR-DAYS.
    //      COMPUTE WK-TOTAL-LOS = BILL-LOS + BILL-PRIOR-DAYS.
    final int totalLengthOfStay = lengthOfStay + claimData.getPriorDays();

    //      IF WK-TOTAL-LOS > 21
    //         COMPUTE DAYS-OVER-21 = (WK-TOTAL-LOS - 21)
    //         MOVE 21 TO DAYS-UPTO-21
    //      ELSE
    //         MOVE WK-TOTAL-LOS TO DAYS-UPTO-21.
    final int daysUpTo21 = Math.min(21, totalLengthOfStay);
    int daysOver21 = Math.max(0, totalLengthOfStay - 21);

    //      PERFORM 3100-GET-EACH-DAY THRU 3100-EXIT  VARYING
    //              SUB FROM SUB2 BY 1 UNTIL
    //              SUB > DAYS-UPTO-21.
    for (int day = claimData.getPriorDays(); day < daysUpTo21; day++) {
      // COMPUTE IPF-FED-PAYMENT ROUNDED =
      //    IPF-FED-PAYMENT + (WK-PER-DIEM-AMT * DAY-VALUE2 (SUB))
      federalPayment =
          federalPayment
              .add(perDiemAmount.multiply(calculationContext.getDayValue(day)))
              .setScale(2, RoundingMode.HALF_UP);
    }

    //      IF WK-TOTAL-LOS > 21
    //         IF BILL-LOS > 0
    //            IF DAYS-OVER-21 > BILL-LOS
    //               MOVE BILL-LOS  TO DAYS-OVER-21
    //            END-IF
    //         END-IF
    //         COMPUTE IPF-FED-PAYMENT ROUNDED =
    //                 IPF-FED-PAYMENT +
    //        (DAYS-OVER-21 * (WK-PER-DIEM-AMT *
    //                          DAY-VALUE2 (22)))
    //      END-IF.
    if (totalLengthOfStay > 21) {
      if (lengthOfStay > 0 && daysOver21 > lengthOfStay) {
        daysOver21 = lengthOfStay;
      }
      final BigDecimal paymentAdjustment =
          calculationContext
              .getPerDiemAmount()
              .multiply(calculationContext.getDayValue(21))
              .multiply(new BigDecimal(daysOver21))
              .setScale(2, RoundingMode.HALF_UP);
      federalPayment = federalPayment.add(paymentAdjustment);
    }
    calculationContext.setFederalPayment(federalPayment);
  }
}
