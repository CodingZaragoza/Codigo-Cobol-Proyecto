package gov.cms.fiss.pricers.fqhc.core.rules.processing;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.fqhc.api.v2.IoceServiceLineData;
import gov.cms.fiss.pricers.fqhc.api.v2.ServiceLinePaymentData;
import gov.cms.fiss.pricers.fqhc.core.ReturnCode;
import gov.cms.fiss.pricers.fqhc.core.ServiceLineContext;
import java.math.BigDecimal;
import org.apache.commons.lang3.StringUtils;

/**
 * Set the line return code for Non-MA plan claim lines.
 *
 * <pre>
 * ----------------------------------------------------------------
 *  PAID LINE: FIND DAY SUMMARY RECORD, CALCULATE PAYMENT,
 *  CALCULATE COINSURANCE &amp; REIMBURSEMENT, AND UPDATE CLAIM TOTALS
 *    (FQHC PER DIEM VISIT)
 * ----------------------------------------------------------------
 *
 * ----------------------------------------------------------------
 * SET LINE RETURN CODE FOR NON-MA PLAN CLAIM LINES
 *     01 - LINE PAYMENT IS THE PPS RATE
 *     05 - LINE PAYMENT IS THE PPS RATE WITH ADD-ON PAYMENT
 *     02 - LINE PAYMENT IS THE DAY'S CHARGES FOR ITS VISIT TYPE
 *          ZERO OUT ADD-ON PAYMENT IF CHARGES USED
 *     21 - LINE PAYMENT BASED ON GRANDFATHERED TRIBAL FQHC (GFTF)
 *          PAYMENT
 *     22 - LINE PAYMENT BASED ON GRANDFATHERED TRIBAL FQHC (GFTF)
 *          SUBMITTED CHARGES
 * ----------------------------------------------------------------
 * </pre>
 *
 * <p>Converted from {@code 4200-CALC-LINE-PYMT} in the COBOL code (continuation).
 */
public class SetNonMAClaimLineReturnCodes
    implements CalculationRule<IoceServiceLineData, ServiceLinePaymentData, ServiceLineContext> {

  @Override
  public boolean shouldExecute(ServiceLineContext calculationContext) {
    return !(calculationContext.getFqhcPricerContext().isMaClaim()
        && StringUtils.equals(
            calculationContext.getInput().getRevenueCode(), ServiceLineContext.REV_CODE_MA_CLAIM));
  }

  @Override
  public void calculate(ServiceLineContext calculationContext) {
    final IoceServiceLineData ioceServiceLine = calculationContext.getInput();
    final ServiceLinePaymentData serviceLinePayment = calculationContext.getOutput();
    final BigDecimal ppsRate = calculationContext.getPpsRate();

    //   IF PAID-ENCOUNTER
    //      IF HL-LITEM-PYMT = HL-PPS-RATE
    //         MOVE 01 TO HL-LITEM-RETURN-CODE
    //      ELSE
    //         MOVE 02 TO HL-LITEM-RETURN-CODE
    //         MOVE 0 TO HL-LITEM-ADD-ON-PYMT
    //      END-IF
    //   END-IF.
    // Added PI = '15' to logic
    if (StringUtils.equalsAny(
        ioceServiceLine.getPaymentIndicator(),
        ServiceLineContext.PI_PAID_ENCOUNTER,
        ServiceLineContext.PI_IOP_SERVICE_15)) {
      if (BigDecimalUtils.equals(serviceLinePayment.getPayment(), ppsRate)) {
        serviceLinePayment.setReturnCodeData(
            ReturnCode.LINE_PAY_BASED_ON_PPS_RATE_01.toReturnCodeData());
      } else {
        serviceLinePayment.setReturnCodeData(
            ReturnCode.LINE_PAY_BASED_ON_PROV_SUB_CHRGS_02.toReturnCodeData());
        serviceLinePayment.setAddOnPayment(BigDecimalUtils.ZERO);
      }
    }

    //   IF PAID-WITH-ADD-ON
    //      IF HL-LITEM-PYMT = HL-PPS-RATE
    //         MOVE 05 TO HL-LITEM-RETURN-CODE
    //      ELSE
    //         MOVE 02 TO HL-LITEM-RETURN-CODE
    //         MOVE 0 TO HL-LITEM-ADD-ON-PYMT
    //      END-IF
    //   END-IF.
    if (StringUtils.equals(
        ioceServiceLine.getPaymentIndicator(), ServiceLineContext.PI_PAID_WITH_ADD_ON)) {
      if (BigDecimalUtils.equals(serviceLinePayment.getPayment(), ppsRate)) {
        serviceLinePayment.setReturnCodeData(
            ReturnCode.LINE_PAY_BASED_ON_PPS_RATE_W_ADD_ON_PAY_05.toReturnCodeData());
      } else {
        serviceLinePayment.setReturnCodeData(
            ReturnCode.LINE_PAY_BASED_ON_PROV_SUB_CHRGS_02.toReturnCodeData());
        serviceLinePayment.setAddOnPayment(BigDecimalUtils.ZERO);
      }
    }

    //   IF PAID-GFTF
    //      IF HL-LITEM-PYMT = HL-PPS-RATE
    //         MOVE 21 TO HL-LITEM-RETURN-CODE
    //      ELSE
    //         MOVE 22 TO HL-LITEM-RETURN-CODE
    //         MOVE 0 TO HL-LITEM-ADD-ON-PYMT
    //      END-IF
    //   END-IF.
    if (StringUtils.equals(
        ioceServiceLine.getPaymentIndicator(), ServiceLineContext.PI_PAID_GFTF)) {
      if (BigDecimalUtils.equals(serviceLinePayment.getPayment(), ppsRate)) {
        serviceLinePayment.setReturnCodeData(
            ReturnCode.LINE_PAY_BASED_ON_GF_TRIBAL_PAY_21.toReturnCodeData());
      } else {
        serviceLinePayment.setReturnCodeData(
            ReturnCode.LINE_PAY_BASED_ON_GF_TRIBAL_SUB_CHRGS_22.toReturnCodeData());
        serviceLinePayment.setAddOnPayment(BigDecimalUtils.ZERO);
      }
    }
  }
}
