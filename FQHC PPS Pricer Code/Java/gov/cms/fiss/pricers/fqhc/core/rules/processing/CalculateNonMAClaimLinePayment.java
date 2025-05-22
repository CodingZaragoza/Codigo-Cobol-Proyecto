package gov.cms.fiss.pricers.fqhc.core.rules.processing;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.fqhc.api.v2.IoceServiceLineData;
import gov.cms.fiss.pricers.fqhc.api.v2.ServiceLinePaymentData;
import gov.cms.fiss.pricers.fqhc.core.FqhcPricerContext;
import gov.cms.fiss.pricers.fqhc.core.ServiceLineContext;
import gov.cms.fiss.pricers.fqhc.core.daily.DaySummary;
import java.math.BigDecimal;
import org.apache.commons.lang3.StringUtils;

/**
 * Set the payment amount for non MA plan claim lines.
 *
 * <pre>
 * ----------------------------------------------------------------*
 * PAID LINE: FIND DAY SUMMARY RECORD, CALCULATE PAYMENT,
 * CALCULATE COINSURANCE &amp; REIMBURSEMENT, AND UPDATE CLAIM TOTALS
 * (FQHC PER DIEM VISIT)
 * ----------------------------------------------------------------*
 * </pre>
 *
 * <p>Converted from {@code 4200-CALC-LINE-PYMT} in the COBOL code (continuation).
 */
public class CalculateNonMAClaimLinePayment
    implements CalculationRule<IoceServiceLineData, ServiceLinePaymentData, ServiceLineContext> {

  @Override
  public void calculate(ServiceLineContext calculationContext) {
    final IoceServiceLineData ioceServiceLine = calculationContext.getInput();
    final ServiceLinePaymentData serviceLinePayment = calculationContext.getOutput();
    final DaySummary daySummary = calculationContext.getDaySummary();
    final BigDecimal ppsRate = calculationContext.getPpsRate();
    final FqhcPricerContext fqhcPricerContext = calculationContext.getFqhcPricerContext();
    BigDecimal payment = BigDecimalUtils.ZERO;

    // *----------------------------------------------------------------*
    // * MEDICAL NON-MA PLAN CLAIM LINE:                                *
    // *   LINE PAYMENT IS LESSOR OF PPS RATE AND DAY'S MEDICAL CHARGES *
    // *----------------------------------------------------------------*
    //   IF MEDICAL-LINE
    //      IF HL-DS-TOT-MEDICAL-CHRGS < HL-PPS-RATE
    //         MOVE HL-DS-TOT-MEDICAL-CHRGS TO HL-LITEM-PYMT
    //      ELSE
    //         MOVE HL-PPS-RATE TO HL-LITEM-PYMT
    //      END-IF
    //   END-IF.

    // 09/21/2021 CLG replaced setPayment with Payment variable.
    if (StringUtils.equals(
        ServiceLineContext.LINE_MEDICAL, ioceServiceLine.getCompositeAdjustmentFlag())) {
      if (BigDecimalUtils.isLessThan(daySummary.getTotalMedicalCharges(), ppsRate)) {
        payment = daySummary.getTotalMedicalCharges();
      } else {
        payment = ppsRate;
      }
    }

    // *----------------------------------------------------------------*
    // * MENTAL NON-MA PLAN CLAIM LINE:                                 *
    // *   LINE PAYMENT IS LESSOR OF PPS RATE AND DAY'S MENTAL CHARGES  *
    // *----------------------------------------------------------------*
    //   IF MENTAL-LINE
    //      IF HL-DS-TOT-MENTAL-CHRGS < HL-PPS-RATE
    //         MOVE HL-DS-TOT-MENTAL-CHRGS TO HL-LITEM-PYMT
    //      ELSE
    //         MOVE HL-PPS-RATE TO HL-LITEM-PYMT
    //      END-IF
    //   END-IF.
    if (StringUtils.equals(
        ServiceLineContext.LINE_MENTAL, ioceServiceLine.getCompositeAdjustmentFlag())) {
      if (BigDecimalUtils.isLessThan(daySummary.getTotalMentalCharges(), ppsRate)) {
        payment = daySummary.getTotalMentalCharges();
      } else {
        payment = ppsRate;
      }
    }

    // *----------------------------------------------------------------*
    // * MODIFIER 59 NON-MA PLAN CLAIM LINE:                            *
    // *   LINE PAYMENT IS LESSOR OF PPS RATE AND DAY'S MOD 59 CHARGES  *
    // *----------------------------------------------------------------*
    //   IF MOD59-LINE
    //      IF HL-DS-TOT-MOD59-CHRGS < HL-PPS-RATE
    //         MOVE HL-DS-TOT-MOD59-CHRGS TO HL-LITEM-PYMT
    //      ELSE
    //         MOVE HL-PPS-RATE TO HL-LITEM-PYMT
    //      END-IF
    //   END-IF.
    if (StringUtils.equals(
        ServiceLineContext.LINE_MOD59, ioceServiceLine.getCompositeAdjustmentFlag())) {
      if (BigDecimalUtils.isLessThan(daySummary.getTotalMod59Charges(), ppsRate)) {
        payment = daySummary.getTotalMod59Charges();
      } else {
        payment = ppsRate;
      }
    }
    // Check if Service From Date is After or Equal to IOP start date 01/01/2024 &
    // Payment Indicator = '15'
    if (LocalDateUtils.isAfterOrEqual(
            fqhcPricerContext.getClaimData().getServiceFromDate(),
            calculationContext.getFqhcPricerContext().getIopStartDate())
        && StringUtils.equalsAny(
            ioceServiceLine.getCompositeAdjustmentFlag(),
            ServiceLineContext.LINE_IOP_LTE3,
            ServiceLineContext.LINE_IOP_GTE4)) {
      // Use lesser of IOP rate or Covered Charges for line payment
      if (BigDecimalUtils.isLessThan(daySummary.getTotalIopCharges(), ppsRate)) {
        payment = daySummary.getTotalIopCharges();
      } else {
        payment = ppsRate;
      }
    }
    serviceLinePayment.setPayment(payment);
    calculationContext.setUnreducedPayment(payment);
  }
}
