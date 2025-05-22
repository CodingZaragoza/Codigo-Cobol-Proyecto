package gov.cms.fiss.pricers.hospice.core.rules.routine_home_care;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.hospice.api.v2.EndOfLifeAddOnDaysPaymentData;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingRequest;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingResponse;
import gov.cms.fiss.pricers.hospice.core.HospicePricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CalculateEndOfLifeServiceIntensityAddOn
    implements CalculationRule<
        HospiceClaimPricingRequest, HospiceClaimPricingResponse, HospicePricerContext> {
  /**
   * Calculates end-of-life service-intensity add-on
   *
   * <pre>
   * ****************************************************************
   * **** V200    CALCULATE END-OF-LIFE SERVICE-INTENSITY ADD-ON
   * ****************************************************************
   * </pre>
   *
   * <p>Converted from {@code V200-CALC-RHC-EOL-SIA} in the COBOL code.
   */
  @Override
  public void calculate(HospicePricerContext calculationContext) {
    // *===============================================================
    // *  SET  INDICATOR  [SIA-UNITS-IND = 'Y']
    // *  SIA INDICATOR WILL BE USED TO HELP DETERMINE THE CORRECT RTC
    // *===============================================================
    // IF  BILL-EOL-ADD-ON-DAY1-UNITS > ZEROES OR
    //    BILL-EOL-ADD-ON-DAY2-UNITS > ZEROES OR
    //    BILL-EOL-ADD-ON-DAY3-UNITS > ZEROES OR
    //    BILL-EOL-ADD-ON-DAY4-UNITS > ZEROES OR
    //    BILL-EOL-ADD-ON-DAY5-UNITS > ZEROES OR
    //    BILL-EOL-ADD-ON-DAY6-UNITS > ZEROES OR
    //    BILL-EOL-ADD-ON-DAY7-UNITS > ZEROES
    final List<EndOfLifeAddOnDaysPaymentData> endOfLifeAddOnDaysPayments =
        calculationContext.getEndOfLifeAddOnDaysPaymentData();
    final List<Integer> endOfLifeAddOnDaysUnits = calculationContext.getEndOfLifeAddOnDaysUnits();
    if (endOfLifeAddOnDaysUnits.stream().anyMatch(units -> units > 0)) {
      // MOVE 'Y' TO SIA-UNITS-IND
      calculationContext.setSiaUnitsIndicator(true);
    } else {
      // MOVE 'N' TO SIA-UNITS-IND
      calculationContext.setSiaUnitsIndicator(false);

      // GO TO V200-CALC-RHC-EOL-SIA-EXIT
      return;
    }

    // *===============================================================
    // *     IF ANY OF THE EOL FIELDS ARE GREATER THAN ZERO
    // *     THEN COMPUTE THE >> CHC-WAGE-INDEXED-RATE << FOR
    // *     SIA PAYMENT AMOUNT CALCULATION (QIP OR NON-QIP)
    // *===============================================================
    // *---------------------------------------------------------------
    // * CALCULATE SIA PAYMENT RATE: HOURLY CHC RATE W/ QIP REDUCTION
    // *---------------------------------------------------------------
    // IF  BILL-QIP-IND = '1'
    if (calculationContext.isReportingQualityData()) {
      // COMPUTE SIA-PYMT-RATE ROUNDED =
      //  (((2020-V200-CHC-LS-RATE-Q * BILL-BENE-WAGE-INDEX)
      //       + 2020-V200-CHC-NLS-RATE-Q) / 24)
      calculationContext.setSiaPaymentRate(
          calculationContext
              .getContinuousHomeCareLsRateQuality()
              .multiply(calculationContext.getPatientWageIndex())
              .add(calculationContext.getContinuousHomeCareNlsRateQuality())
              .divide(new BigDecimal("24"), 2, RoundingMode.HALF_UP));
    }
    // *---------------------------------------------------------------
    // * CALCULATE SIA PAYMENT RATE: HOURLY CHC RATE W/OUT QIP REDUCT.
    // *---------------------------------------------------------------
    else {
      // COMPUTE SIA-PYMT-RATE ROUNDED =
      //  (((2020-V200-CHC-LS-RATE * BILL-BENE-WAGE-INDEX)
      //       + 2020-V200-CHC-NLS-RATE) / 24)
      calculationContext.setSiaPaymentRate(
          calculationContext
              .getContinuousHomeCareLsRate()
              .multiply(calculationContext.getPatientWageIndex())
              .add(calculationContext.getContinuousHomeCareNlsRate())
              .divide(new BigDecimal("24"), 2, RoundingMode.HALF_UP));
    }

    // *===============================================================
    // *     CALCULATE END OF LIFE SIA PAYMENT FOR UP TO 7 DAYS
    // *       - SIA UNITS IN 15 MIN BLOCKS - CONVERT TO HOURS
    // *       - SIA PAYMENT AMT IS CAPPED AT 4 HOURS
    // *===============================================================

    // *---------------------------------------------------------------
    // * CALCULATE >> DAY 1-7 EOL SIA PYMT UP TO 4 HRS, 1 UNIT = 15 MIN
    // *---------------------------------------------------------------
    // IF  BILL-EOL-ADD-ON-DAY1-UNITS > ZEROES
    for (int i = 0; i < endOfLifeAddOnDaysUnits.size(); i++) {
      final Integer endOfLifeAddOnDayUnit = endOfLifeAddOnDaysUnits.get(i);
      if (endOfLifeAddOnDayUnit > 0) {
        final BigDecimal endOfLifeHours;
        // IF EOL-UNITS1 >= 16
        if (endOfLifeAddOnDayUnit >= 16) {
          // MOVE 4 TO EOL-HOURS1
          endOfLifeHours = new BigDecimal(4);
        } else {
          // COMPUTE EOL-HOURS1 ROUNDED = (EOL-UNITS1 / 4)
          endOfLifeHours =
              new BigDecimal(endOfLifeAddOnDayUnit).divide(new BigDecimal(4), 2, RoundingMode.DOWN);
        }
        // COMPUTE BILL-EOL-ADD-ON-DAY1-PAY ROUNDED =
        //        EOL-HOURS1 * SIA-PYMT-RATE
        final EndOfLifeAddOnDaysPaymentData endOfLifeAddOnDaysPaymentData =
            new EndOfLifeAddOnDaysPaymentData();
        endOfLifeAddOnDaysPaymentData.setIndex(i + 1);
        endOfLifeAddOnDaysPaymentData.setPayment(
            endOfLifeHours
                .multiply(calculationContext.getSiaPaymentRate())
                .setScale(2, RoundingMode.HALF_UP));
        endOfLifeAddOnDaysPayments.set(i, endOfLifeAddOnDaysPaymentData);
      } else {
        final EndOfLifeAddOnDaysPaymentData endOfLifeAddOnDaysPaymentData =
            new EndOfLifeAddOnDaysPaymentData();
        endOfLifeAddOnDaysPaymentData.setPayment(BigDecimal.ZERO.setScale(2, RoundingMode.DOWN));
        endOfLifeAddOnDaysPaymentData.setIndex(i + 1);
        endOfLifeAddOnDaysPayments.set(i, endOfLifeAddOnDaysPaymentData);
      }
    }

    // *---------------------------------------------------------------
    // *  CALCULATE >> TOTAL CLAIM EOL SIA ADD-ON PAYMENT
    // *---------------------------------------------------------------
    calculationContext.setSiaPayAmountTotal(BigDecimal.ZERO);
    // COMPUTE SIA-PAY-AMT-TOTAL =
    //        BILL-EOL-ADD-ON-DAY1-PAY +
    //        BILL-EOL-ADD-ON-DAY2-PAY +
    //        BILL-EOL-ADD-ON-DAY3-PAY +
    //        BILL-EOL-ADD-ON-DAY4-PAY +
    //        BILL-EOL-ADD-ON-DAY5-PAY +
    //        BILL-EOL-ADD-ON-DAY6-PAY +
    //        BILL-EOL-ADD-ON-DAY7-PAY.
    for (final EndOfLifeAddOnDaysPaymentData endOfLifePay : endOfLifeAddOnDaysPayments) {
      calculationContext.setSiaPayAmountTotal(
          calculationContext.getSiaPayAmountTotal().add(endOfLifePay.getPayment()));
    }
  }
}
