package gov.cms.fiss.pricers.fqhc.core.rules.processing;

import gov.cms.fiss.pricers.common.api.annotations.FixedValue;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.fqhc.api.v2.IoceServiceLineData;
import gov.cms.fiss.pricers.fqhc.api.v2.ServiceLinePaymentData;
import gov.cms.fiss.pricers.fqhc.core.FqhcPricerContext;
import gov.cms.fiss.pricers.fqhc.core.ReturnCode;
import gov.cms.fiss.pricers.fqhc.core.ServiceLineContext;
import gov.cms.fiss.pricers.fqhc.core.daily.DaySummary;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * Calculates the coinsurance reimbursement amounts for service lines and updates the line return
 * code.
 *
 * <pre>
 * ----------------------------------------------------------------
 *  PAID LINE: FIND DAY SUMMARY RECORD, CALCULATE PAYMENT,
 *  CALCULATE COINSURANCE &amp; REIMBURSEMENT, AND UPDATE CLAIM TOTALS
 *    (FQHC PER DIEM VISIT)
 * ----------------------------------------------------------------
 *
 * ****************************************************************
 *
 * FOR EACH PAID LINE, DETERMINE HOW TO CALCULATE COINSURANCE
 * BASED ON THE DAY STATISTICS AND LINE TYPE, CALCULATE MEDICARE
 * REIMBURSEMENT, AND SET LINE RETURN CODE
 *
 * ****************************************************************
 * </pre>
 *
 * <p>Converted from {@code 4300-CALC-COIN-REIM} in the COBOL code.
 */
public class CalculateCoinsurancePayment
    implements CalculationRule<IoceServiceLineData, ServiceLinePaymentData, ServiceLineContext> {

  @Override
  public void calculate(ServiceLineContext calculationContext) {
    final IoceServiceLineData ioceServiceLine = calculationContext.getInput();
    final ServiceLinePaymentData serviceLinePayment = calculationContext.getOutput();
    final DaySummary daySummary = calculationContext.getDaySummary();
    final FqhcPricerContext fqhcPricerContext = calculationContext.getFqhcPricerContext();

    // MA Plan claim line
    // *----------------------------------------------------------------*
    // * MA PLAN CLAIM LINE                                             *
    // *----------------------------------------------------------------*
    //   IF MA-CLAIM AND MA-CLAIM-REV
    //      MOVE 0 TO HL-LITEM-COIN
    //      MOVE HL-LITEM-PYMT TO HL-LITEM-REIM
    //      GO TO 4300-CALC-COIN-REIM-EXIT
    //   END-IF.
    // Added payment Indicator = 16 for 2024
    if (fqhcPricerContext.isMaClaim()
            && StringUtils.equals(
                ioceServiceLine.getRevenueCode(), ServiceLineContext.REV_CODE_MA_CLAIM)
        || StringUtils.equals(
            ioceServiceLine.getPaymentIndicator(),
            ServiceLineContext.PI_MA_SERVICE_IOP_WRAP_AROUND_16)) {
      serviceLinePayment.setCoinsuranceAmount(BigDecimalUtils.ZERO);

      return;
    }

    // 06/15/2020 COVID19 - CLG
    // *----------------------------------------------------------------
    // * NO COINSURANCE N/A PKG LINES ON THE DAY (NON-MA CLAIM LINE)
    // *----------------------------------------------------------------
    //    IF NOT COINNA-PKG-LINE-PRESENT
    //    PERFORM 4320-STANDARD-COIN
    //    THRU 4320-STANDARD-COIN-EXIT
    //    END-IF.
    if (!daySummary.isCoinsuranceNotApplicablePackageLinePresent()) {
      calculateAndSetStandardCoinsurance(
          calculationContext, fqhcPricerContext.getCoinsuranceRate());
    } else {
      // Preventive services were in some way applied
      // *----------------------------------------------------------------*
      // * ONLY COINSURANCE N/A PKG LINES ON THE DAY (NON-MA CLAIM LN)    *
      // *----------------------------------------------------------------*
      //       IF COINNA-PKG-LINE-PRESENT AND
      //          NOT OTHER-PKG-LINE-PRESENT
      //          PERFORM 4330-ALL-COINNA-PKG
      //             THRU 4330-ALL-COINNA-PKG-EXIT
      //       END-IF.
      if (!daySummary.isOtherPackageLinePresent()
          && daySummary.isCoinsuranceNotApplicablePackageLinePresent()) {
        serviceLinePayment.setCoinsuranceAmount(BigDecimalUtils.ZERO);

        adjustReturnCodeForPreventiveCare(serviceLinePayment, daySummary);
      } else {
        // *----------------------------------------------------------------*
        // * SOME COINSURANCE N/A PKG LINES ON THE DAY (NON-MA CLAIM LN)    *
        // *----------------------------------------------------------------*
        //       IF COINNA-PKG-LINE-PRESENT AND
        //          OTHER-PKG-LINE-PRESENT
        //
        //          PERFORM 4340-SOME-COINNA-PKG
        //             THRU 4340-SOME-COINNA-PKG-EXIT
        //       END-IF.
        updatePaymentCoinsuranceNotApplicable(
            calculationContext, daySummary, fqhcPricerContext.getCoinsuranceRate());
      }
    }

    //  06/23/2020 - COVID19 UPDATE
    //   *----------------------------------------------------------------*
    //   * WAIVE COINSURANCE FOR COINSURANCE N/A PAID LINE                *
    //   * (EFFECTIVE MARCH 1, 2020 - INCLUDES COVID-19 SERVICES)         *
    //   *----------------------------------------------------------------*
    //     IF FQHC-COINNA-SERVICE MOVE 0 TO HL-LITEM-COIN.
    if (calculationContext.isCoinsuranceNAPaymentFlag()) {
      serviceLinePayment.setCoinsuranceAmount(BigDecimalUtils.ZERO);
    }
  }

  private void calculateAndSetStandardCoinsurance(
      ServiceLineContext calculationContext, @FixedValue BigDecimal coinsuranceRate) {
    // ******************************************************************
    // *                                                                *
    // * CALCULATE STANDARD COINSURANCE                                 *
    // * FOR LINE WITH NO ASSOCIATED PREVENTIVE SERVICE(S)              *
    // *   20% COINSURANCE                                              *
    // *                                                                *
    // ******************************************************************
    //  4320-STANDARD-COIN.
    //
    //        COMPUTE HL-LITEM-COIN ROUNDED =
    //                HL-LITEM-PYMT * W-COIN-RATE.
    //
    //  4320-STANDARD-COIN-EXIT.
    //        EXIT.
    calculationContext
        .getOutput()
        .setCoinsuranceAmount(
            calculationContext
                .getUnreducedPayment()
                .multiply(coinsuranceRate)
                .setScale(2, RoundingMode.HALF_UP));
  }

  /**
   * Calculations for when a service line claim has some preventive services in the claim.
   *
   * <pre>
   * ****************************************************************
   *
   * DETERMINE COINSURANCE CALCULATION FOR LINE WITH SOME
   * PREVENTIVE PREVENTIVE SERVICE(S) AND SOME OTHER SERVICES ON
   * THE SAME DAY
   *
   * ****************************************************************
   * </pre>
   *
   * <p>Converted from {@code 4340-SOME-COINNA} in the COBOL code.
   *
   * @param calculationContext the service line context to process
   * @param daySummary the day summary to gather daily values for calculations from
   * @param coinsuranceRate the coinsurance rate
   */
  private void updatePaymentCoinsuranceNotApplicable(
      ServiceLineContext calculationContext, DaySummary daySummary, BigDecimal coinsuranceRate) {
    final IoceServiceLineData ioceServiceLine = calculationContext.getInput();
    final ServiceLinePaymentData serviceLinePayment = calculationContext.getOutput();
    // Don't adjust for preventive services
    // *----------------------------------------------------------------*
    // * LINE SHOULD NOT BE ADJUSTED FOR PREVENTIVE CHARGES:            *
    // * - MENTAL LINE WITH MEDICAL PAID LINE ON SAME DAY               *
    // * - MOD 59 LINE WITH MEDICAL OR MENTAL PAID LINE ON SAME DAY     *
    // *----------------------------------------------------------------*
    //   IF (MENTAL-LINE AND MEDICAL-PAID-LINE-PRESENT) OR
    //      (MOD59-LINE AND (MEDICAL-PAID-LINE-PRESENT OR
    //       MENTAL-PAID-LINE-PRESENT))
    //      PERFORM 4320-STANDARD-COIN
    //         THRU 4320-STANDARD-COIN-EXIT
    if (StringUtils.equals(
                ioceServiceLine.getCompositeAdjustmentFlag(), ServiceLineContext.LINE_MENTAL)
            && daySummary.isMedicalPaidLinePresent()
        || StringUtils.equals(
                ioceServiceLine.getCompositeAdjustmentFlag(), ServiceLineContext.LINE_MOD59)
            && (daySummary.isMedicalPaidLinePresent() || daySummary.isMentalPaidLinePresent())
        || StringUtils.equalsAny(
                ioceServiceLine.getCompositeAdjustmentFlag(),
                ServiceLineContext.LINE_IOP_LTE3,
                ServiceLineContext.LINE_IOP_GTE4)
            && (daySummary.isMedicalPaidLinePresent() || daySummary.isMod59PaidLinePresent())) {
      calculateAndSetStandardCoinsurance(calculationContext, coinsuranceRate);
    }
    // Line should be adjusted for preventive charges
    else {
      // **************************************************************************************
      // 6/12/2020 - CLG - COVID19 Updates
      //      4341-ADJ-FOR-COINNA-PKG.
      //
      // *----------------------------------------------------------------*
      // *    LINE PAYMENT EXCEEDS COINSURANCE N/A PKG CHARGES:           *
      // *    ADJUST COINSURANCE FOR COINSURANCE N/A PKG CHARGES          *
      // *----------------------------------------------------------------*
      //      IF HL-LITEM-PYMT > HL-DS-TOT-COINNA-PKG-CHRGS
      //
      //      COMPUTE HL-BASE-COIN-AMT ROUNDED =
      //          HL-LITEM-PYMT - HL-DS-TOT-COINNA-PKG-CHRGS
      //
      //      COMPUTE HL-LITEM-COIN ROUNDED =
      //          HL-BASE-COIN-AMT * W-COIN-RATE
      //
      //
      if (BigDecimalUtils.isGreaterThan(
          serviceLinePayment.getPayment(),
          daySummary.getTotalCoinsuranceNotApplicablePackageCharges())) {
        final BigDecimal baseCoin =
            serviceLinePayment
                .getPayment()
                .subtract(daySummary.getTotalCoinsuranceNotApplicablePackageCharges())
                .setScale(2, RoundingMode.HALF_UP);
        serviceLinePayment.setCoinsuranceAmount(
            baseCoin.multiply(coinsuranceRate).setScale(2, RoundingMode.HALF_UP));
      }
      // **************************************************************************************
      // Line payment equals or is less than preventive charges; 0% coinsurance
      //  *----------------------------------------------------------------*
      //  * LINE PAYMENT LESS THAN OR EQUAL TO PREVENTIVE CHARGES:         *
      //  *   0% COINSURANCE                                               *
      //  *----------------------------------------------------------------*
      //         ELSE
      //            MOVE 0 TO HL-LITEM-COIN
      //         END-IF.
      else {
        serviceLinePayment.setCoinsuranceAmount(BigDecimalUtils.ZERO);
      }

      adjustReturnCodeForPreventiveCare(serviceLinePayment, daySummary);
    }
  }

  /**
   * Updates the return code if the line indicates preventive care. The provided output line will be
   * modified as needed.
   *
   * @param serviceLinePayment the current line output
   * @param daySummary the current summary information
   */
  protected void adjustReturnCodeForPreventiveCare(
      ServiceLinePaymentData serviceLinePayment, DaySummary daySummary) {
    // *----------------------------------------------------------------*
    // * SET LINE RETURN CODE TO INDICATE PREVENTIVE SERVICE IS PRESENT *
    // *----------------------------------------------------------------*
    //    IF PREVENTIVE-LINE-PRESENT AND
    //      (HL-LITEM-RETURN-CODE = 01 OR 05)
    //      MOVE 03 TO HL-LITEM-RETURN-CODE
    //      END-IF.
    //
    //    IF PREVENTIVE-LINE-PRESENT AND
    //      HL-LITEM-RETURN-CODE = 02
    //      MOVE 04 TO HL-LITEM-RETURN-CODE
    //      END-IF.
    if (daySummary.isPreventivePresent()
        && List.of("01", "05").contains(serviceLinePayment.getReturnCodeData().getCode())) {
      serviceLinePayment.setReturnCodeData(
          ReturnCode.LINE_PAY_BASED_ON_PPS_RATE_PS_PRES_03.toReturnCodeData());
    }

    if (daySummary.isPreventivePresent()
        && StringUtils.equals(serviceLinePayment.getReturnCodeData().getCode(), "02")) {
      serviceLinePayment.setReturnCodeData(
          ReturnCode.LINE_PAY_BASED_ON_PROV_SUB_CHRGS_PS_PRES_04.toReturnCodeData());
    }
  }
}
