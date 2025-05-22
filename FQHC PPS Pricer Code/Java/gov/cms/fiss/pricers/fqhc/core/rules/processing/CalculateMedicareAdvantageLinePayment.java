package gov.cms.fiss.pricers.fqhc.core.rules.processing;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.fqhc.api.v2.IoceServiceLineData;
import gov.cms.fiss.pricers.fqhc.api.v2.ServiceLinePaymentData;
import gov.cms.fiss.pricers.fqhc.core.FqhcPricerContext;
import gov.cms.fiss.pricers.fqhc.core.ReturnCode;
import gov.cms.fiss.pricers.fqhc.core.ServiceLineContext;
import java.math.BigDecimal;
import org.apache.commons.lang3.StringUtils;

/**
 * Calculates the service line payment amounts.
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
 * FOR EACH PAID LINE, CALCULATE THE LINE PAYMENT BASED ON THE
 * LINE TYPE, SET LINE RETURN CODE
 *
 * ****************************************************************
 * </pre>
 *
 * <p>Converted from {@code 4200-CALC-LINE-PYMT} in the COBOL code.
 */
public class CalculateMedicareAdvantageLinePayment
    implements CalculationRule<IoceServiceLineData, ServiceLinePaymentData, ServiceLineContext> {

  @Override
  public void calculate(ServiceLineContext calculationContext) {
    final IoceServiceLineData ioceServiceLine = calculationContext.getInput();
    final ServiceLinePaymentData serviceLinePayment = calculationContext.getOutput();
    final FqhcPricerContext fqhcPricerContext = calculationContext.getFqhcPricerContext();

    // MA plan claim /IOP claim
    // *----------------------------------------------------------------*
    // * MA PLAN CLAIM LINE:                                            *
    // *   LINE PAYMENT = SUPPLEMENTAL/WRAP-AROUND PAYMENT              *
    // *----------------------------------------------------------------*
    //   IF MA-CLAIM AND MA-CLAIM-REV
    if (fqhcPricerContext.isMaClaim()
        && StringUtils.equals(
            ioceServiceLine.getRevenueCode(), ServiceLineContext.REV_CODE_MA_CLAIM)) {
      final BigDecimal payment;
      // Supplemental/Wrap-around payment made
      if (BigDecimalUtils.isGreaterThan(
          calculationContext.getPpsRate(),
          fqhcPricerContext.getClaimData().getMedicareAdvantagePlanAmount())) {
        // *----------------------------------------------------------------*
        // *   SUPPLEMENTAL/WRAP AROUND PAYMENT MADE                        *
        // *----------------------------------------------------------------*
        //   IF HL-PPS-RATE > I-MA-PLAN-AMT
        //      COMPUTE HL-LITEM-PYMT =
        //              HL-PPS-RATE - I-MA-PLAN-AMT
        //      MOVE 06 TO HL-LITEM-RETURN-CODE

        // 09/21/2021 clg added payment variable for the coinsurance
        payment =
            calculationContext
                .getPpsRate()
                .subtract(fqhcPricerContext.getClaimData().getMedicareAdvantagePlanAmount());

        serviceLinePayment.setReturnCodeData(
            ReturnCode.LINE_SUP_MA_PAY_APPLIED_06.toReturnCodeData());
      }
      // No Supplemental/Wrap-around payment made
      else {
        // *----------------------------------------------------------------*
        // *   NO SUPPLEMENTAL/WRAP AROUND PAYMENT MADE                     *
        // *----------------------------------------------------------------*
        //      ELSE
        //         MOVE 0  TO HL-LITEM-PYMT
        //         MOVE 07 TO HL-LITEM-RETURN-CODE
        //      END-IF
        //      GO TO 4200-CALC-LINE-PYMT-EXIT
        //   END-IF.
        payment = BigDecimalUtils.ZERO;
        serviceLinePayment.setReturnCodeData(
            ReturnCode.LINE_SUP_MA_PAY_NOT_APPLIED_07.toReturnCodeData());
      }

      serviceLinePayment.setPayment(payment);
      calculationContext.setUnreducedPayment(payment);
    }
  }
}
