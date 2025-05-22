package gov.cms.fiss.pricers.fqhc.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.EvaluatingCalculationRule;
import gov.cms.fiss.pricers.fqhc.api.v2.FqhcClaimPricingRequest;
import gov.cms.fiss.pricers.fqhc.api.v2.FqhcClaimPricingResponse;
import gov.cms.fiss.pricers.fqhc.api.v2.IoceServiceLineData;
import gov.cms.fiss.pricers.fqhc.api.v2.ServiceLinePaymentData;
import gov.cms.fiss.pricers.fqhc.core.FqhcPricerContext;
import gov.cms.fiss.pricers.fqhc.core.ServiceLineContext;
import java.util.List;

/**
 * Goes through all service lines of a claim and processes them, and accumulates daily summary
 * information for the lines.
 *
 * <pre>
 * ****************************************************************
 *
 * PROCESS EACH SERVICE LINE BASED ON ITS TYPE
 * SOME LINES WILL BE PAID, AND SOME WILL NOT
 *
 * ****************************************************************
 * </pre>
 *
 * <p>Converted from {@code 4000-PROCESS-LINE} in the COBOL code.
 */
public class DefaultLineProcessing
    implements CalculationRule<
        FqhcClaimPricingRequest, FqhcClaimPricingResponse, FqhcPricerContext> {
  private final EvaluatingCalculationRule<
          IoceServiceLineData, ServiceLinePaymentData, ServiceLineContext>
      evaluationRule;

  public DefaultLineProcessing(
      List<CalculationRule<IoceServiceLineData, ServiceLinePaymentData, ServiceLineContext>>
          calculationRules) {
    evaluationRule = new EvaluatingCalculationRule<>(calculationRules);
  }

  @Override
  public void calculate(FqhcPricerContext calculationContext) {
    // *----------------------------------------------------------------*
    // *                                                                *
    // *   STEP 2 - LOOP THROUGH ALL CLAIM LINES                        *
    // *   ------   (IF THE DAY SUMMARY TABLE HAS AT LEAST ONE RECORD)  *
    // *            - PAID LINES: CALCULATE PAYMENT, COINSURANCE,       *
    // *              ACCUMULATE CLAIM TOTALS, SET RETURN CODE, MOVE    *
    // *              LINE INFO. TO OUTPUT RECORD                       *
    // *            - NON-PAID LINES: SET APPROPRIATE RETURN CODE       *
    // *                                                                *
    // *----------------------------------------------------------------*
    //   IF W-DAY-SUM-MAX > 0
    //      PERFORM 4000-PROCESS-LINE
    //         THRU 4000-PROCESS-LINE-EXIT
    //           VARYING LN-SUB FROM 1 BY 1
    //             UNTIL LN-SUB > I-SRVC-LINE-CNT.
    for (final IoceServiceLineData lineInput : calculationContext.getIoceServiceLines()) {
      // *----------------------------------------------------------------*
      // * MOVE SERVICE LINE INPUT RECORD TO WORKING STORAGE HOLD AREA    *
      // *   (TO PROCESS LINE USING CONDITIONS AND WITHOUT SUBSCRIPTS)    *
      // *----------------------------------------------------------------*
      //   MOVE I-SERVICE-LINE (LN-SUB) TO HL-SERVICE-LINE.
      final ServiceLinePaymentData serviceLinePayment =
          calculationContext.getServiceLinePayments(lineInput.getLineNumber());
      // Only price this line if it has a return code indicating pricing should occur
      if (Integer.parseInt(serviceLinePayment.getReturnCodeData().getCode()) < 10) {
        // *----------------------------------------------------------------*
        // * LINE RETURN CODE INDICATES LINE SHOULD NOT BE PROCESSED: STOP  *
        // *   (NON-COVERED/MA PLAN AMT ZERO/IOCE FLAG VALUE INVALID)       *
        // *----------------------------------------------------------------*
        //   IF O-LITEM-RETURN-CODE (LN-SUB) >= 10
        //      GO TO 4000-PROCESS-LINE-EXIT.
        evaluationRule.calculate(
            new ServiceLineContext(lineInput, serviceLinePayment, calculationContext));
      }
    }
  }
}
