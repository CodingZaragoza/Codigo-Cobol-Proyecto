package gov.cms.fiss.pricers.hospice.core.rules.routine_home_care;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.hospice.api.v2.BillingGroupData;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingRequest;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingResponse;
import gov.cms.fiss.pricers.hospice.api.v2.HospicePaymentData;
import gov.cms.fiss.pricers.hospice.core.HospicePricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class EvaluateRoutineHomeCareDays
    implements CalculationRule<
        HospiceClaimPricingRequest, HospiceClaimPricingResponse, HospicePricerContext> {
  /**
   * Evaluates RHC day calculations.
   *
   * <pre>
   * ****************************************************************
   *  V200-EVAL-RHC-DAYS.
   * ****************************************************************
   * </pre>
   *
   * <p>Converted from {@code V200-EVAL-RHC-DAYS} in the COBOL code.
   */
  @Override
  public void calculate(HospicePricerContext calculationContext) {
    final BillingGroupData billGroup =
        calculationContext.getBillGroup(HospicePricerContext.ROUTINE_HOME_CARE_REVENUE_CODE);
    // *---------------------------------------------------------------
    // * SERVICE BEYOND 60TH DAY - APPLY RHC LOW RATE TO ALL RHC DAYS
    // *---------------------------------------------------------------
    if (calculationContext.getPriorServiceDays() >= 60) {
      // IF PRIOR-SVC-DAYS >= 60
      //    MOVE BILL-UNITS1 TO LR-BILL-UNITS1
      calculationContext.setLowRateBillUnits(billGroup.getUnits());

      // PERFORM V200-APPLY-LOW-RHC-RATE
      //    THRU V200-APPLY-LOW-RHC-RATE-EXIT
      applyLowRhcRate(calculationContext);
      // END-IF.
    }

    // *---------------------------------------------------------------
    // * SERVICE ON 60TH DAY OR EARLIER - APPLY RHC HIGH/LOW RATES
    // *---------------------------------------------------------------
    // IF PRIOR-SVC-DAYS < 60
    if (calculationContext.getPriorServiceDays() < 60) {
      // COMPUTE HIGH-RATE-DAYS-LEFT = 60 - PRIOR-SVC-DAYS
      final int highRateDaysLeft = 60 - calculationContext.getPriorServiceDays();
      // *---------------------------------------------------------------
      // * - RHC DAYS <= HIGH RATE DAYS LEFT - APPLY RHC HIGH RATE TO ALL
      // *---------------------------------------------------------------
      // IF BILL-UNITS1 <= HIGH-RATE-DAYS-LEFT
      if (billGroup.getUnits() <= highRateDaysLeft) {
        // MOVE BILL-UNITS1 TO HR-BILL-UNITS1
        calculationContext.setHighRateBillUnits(billGroup.getUnits());

        // PERFORM V200-APPLY-HIGH-RHC-RATE
        //    THRU V200-APPLY-HIGH-RHC-RATE-EXIT
        applyHighRhcRate(calculationContext);
      }

      // *---------------------------------------------------------------
      // * - RHC DAYS > HIGH RATE DAYS LEFT - APPLY RHC HIGH & LOW RATES
      // *---------------------------------------------------------------
      else {
        // MOVE HIGH-RATE-DAYS-LEFT TO HR-BILL-UNITS1
        calculationContext.setHighRateBillUnits(highRateDaysLeft);

        // PERFORM V200-APPLY-HIGH-RHC-RATE
        //    THRU V200-APPLY-HIGH-RHC-RATE-EXIT
        applyHighRhcRate(calculationContext);

        // COMPUTE LR-BILL-UNITS1 =
        //         BILL-UNITS1 - HR-BILL-UNITS1
        calculationContext.setLowRateBillUnits(
            billGroup.getUnits() - calculationContext.getHighRateBillUnits());

        // PERFORM V200-APPLY-LOW-RHC-RATE
        //    THRU V200-APPLY-LOW-RHC-RATE-EXIT
        applyLowRhcRate(calculationContext);
      }
      // END-IF.
    }
    // END-IF.
  }

  /**
   * Applies the high RHC rate.
   *
   * <pre>
   * ***************************************************************
   * *** V200    CALCULATE RHC HIGH-RATE PAYMENT
   * ***************************************************************
   * </pre>
   *
   * <p>Converted from {@code V200-APPLY-LOW-RHC-RATE} in the COBOL code.
   *
   * @param calculationContext the current calculation context
   */
  protected void applyHighRhcRate(HospicePricerContext calculationContext) {
    // *---------------------------------------------------------------
    // * FY 2020- CR # 11411
    // *---------------------------------------------------------------
    //     MOVE HR-BILL-UNITS1  TO BILL-HIGH-RHC-DAYS.
    final HospicePaymentData paymentData = calculationContext.getPaymentData();
    paymentData.setHighRoutineHomeCareDays(calculationContext.getHighRateBillUnits());

    if (calculationContext.isReportingQualityData()) {
      // *---------------------------------------------------------------
      // * QIP REDUCTION APPLIED
      // *---------------------------------------------------------------
      //     IF BILL-QIP-IND = '1'
      //        COMPUTE HR-BILL-PAY-AMT1 ROUNDED =
      //           ((2020-V200-HIGH-RHC-LS-RATE-Q * BILL-BENE-WAGE-INDEX)
      //           + 2020-V200-HIGH-RHC-NLS-RATE-Q) *  HR-BILL-UNITS1
      calculationContext.setHighRateBillPayAmount(
          calculationContext
              .getHighRoutineHomeCareLsRateQuality()
              .multiply(calculationContext.getPatientWageIndex())
              .add(calculationContext.getHighRoutineHomeCareNlsRateQuality())
              .multiply(new BigDecimal(paymentData.getHighRoutineHomeCareDays()))
              .setScale(2, RoundingMode.HALF_UP));
    } else {
      // *---------------------------------------------------------------
      // * NO QIP REDUCTION
      // *---------------------------------------------------------------
      //     ELSE
      //        COMPUTE HR-BILL-PAY-AMT1 ROUNDED =
      //           ((2020-V200-HIGH-RHC-LS-RATE * BILL-BENE-WAGE-INDEX)
      //           + 2020-V200-HIGH-RHC-NLS-RATE) *  HR-BILL-UNITS1
      calculationContext.setHighRateBillPayAmount(
          calculationContext
              .getHighRoutineHomeCareLsRate()
              .multiply(calculationContext.getPatientWageIndex())
              .add(calculationContext.getHighRoutineHomeCareNlsRate())
              .multiply(new BigDecimal(paymentData.getHighRoutineHomeCareDays()))
              .setScale(2, RoundingMode.HALF_UP));
      //     END-IF.
    }

    //     MOVE 'Y' TO RHC-HIGH-DAY-IND.
    calculationContext.setRoutineHomeCareHighDayIndicator(true);
  }

  /**
   * Applies the low RHC rate.
   *
   * <pre>
   * ***************************************************************
   * *** V200    CALCULATE RHC LOW-RATE PAYMENT
   * ***************************************************************
   * </pre>
   *
   * <p>Converted from {@code V200-APPLY-LOW-RHC-RATE} in the COBOL code.
   *
   * @param calculationContext the current calculation context
   */
  protected void applyLowRhcRate(HospicePricerContext calculationContext) {
    // *---------------------------------------------------------------
    // * FY 2020- CR # 11411
    // *---------------------------------------------------------------
    //     MOVE LR-BILL-UNITS1  TO BILL-LOW-RHC-DAYS.
    final HospicePaymentData paymentData = calculationContext.getPaymentData();
    paymentData.setLowRoutineHomeCareDays(calculationContext.getLowRateBillUnits());

    if (calculationContext.isReportingQualityData()) {
      // *---------------------------------------------------------------
      // * QIP REDUCTION APPLIED
      // *---------------------------------------------------------------
      //     IF BILL-QIP-IND = '1'
      //        COMPUTE LR-BILL-PAY-AMT1 ROUNDED =
      //           ((2020-V200-LOW-RHC-LS-RATE-Q * BILL-BENE-WAGE-INDEX)
      //           + 2020-V200-LOW-RHC-NLS-RATE-Q) *  LR-BILL-UNITS1
      calculationContext.setLowRateBillPayAmount(
          calculationContext
              .getLowRoutineHomeCareLsRateQuality()
              .multiply(calculationContext.getPatientWageIndex())
              .add(calculationContext.getLowRoutineHomeCareNlsRateQuality())
              .multiply(new BigDecimal(paymentData.getLowRoutineHomeCareDays())));
    } else {
      // *---------------------------------------------------------------
      // * NO QIP REDUCTION
      // *---------------------------------------------------------------
      //     ELSE
      //        COMPUTE LR-BILL-PAY-AMT1 ROUNDED =
      //           ((2020-V200-LOW-RHC-LS-RATE * BILL-BENE-WAGE-INDEX)
      //           + 2020-V200-LOW-RHC-NLS-RATE) *  LR-BILL-UNITS1
      calculationContext.setLowRateBillPayAmount(
          calculationContext
              .getLowRoutineHomeCareLsRate()
              .multiply(calculationContext.getPatientWageIndex())
              .add(calculationContext.getLowRoutineHomeCareNlsRate())
              .multiply(new BigDecimal(paymentData.getLowRoutineHomeCareDays())));
      //     END-IF.
    }

    //     MOVE 'Y' TO RHC-LOW-DAY-IND.
    calculationContext.setRoutineHomeCareLowDayIndicator(true);
  }
}
