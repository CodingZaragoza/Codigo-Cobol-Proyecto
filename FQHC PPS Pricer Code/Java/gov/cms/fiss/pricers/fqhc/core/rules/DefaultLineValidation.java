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
 * Iterates over the service lines included in the input to determine if they are valid and sets up
 * the rates and fields required to process the claim as a whole.
 *
 * <pre>
 * *****************************************************************
 *
 *  VALIDATE CLAIM LINES:
 *    - GRANDFATHERED TRIBAL FQHC CLAIM FROM DATE EDIT
 *    - MEDICARE ADVANTAGE (MA) LINE EDIT
 *    - IOCE FLAG VALUES
 *    * NOTE: RETURN CODES THAT INDICATE LINE NOT PROCESSED
 *            ARE PASSED DIRECTLY TO THE OUTPUT RECORD
 *            BECAUSE THE HOLD AREA WILL NOT BE USED
 *
 *  GET RATES FOR THE LINE'S DATE OF SERVICE:
 *    - BASE RATE
 *    - GEOGRAPHIC ADJUSTMENT FACTOR (GAF)
 *    - ADD-ON RATE
 *    - GRANDFATHERED TRIBAL FQHC (GFTF) RATE
 *
 *  UPDATE DAY SUMMARY TABLE USING LINE'S INFORMATION
 *
 * *****************************************************************
 * </pre>
 *
 * <p>Converted from {@code 3000-VALIDATE-LINE} in the COBOL code.
 */
public class DefaultLineValidation
    implements CalculationRule<
        FqhcClaimPricingRequest, FqhcClaimPricingResponse, FqhcPricerContext> {
  private final EvaluatingCalculationRule<
          IoceServiceLineData, ServiceLinePaymentData, ServiceLineContext>
      evaluationRule;

  public DefaultLineValidation(
      List<CalculationRule<IoceServiceLineData, ServiceLinePaymentData, ServiceLineContext>>
          calculationRules) {
    evaluationRule = new EvaluatingCalculationRule<>(calculationRules);
  }

  @Override
  public void calculate(FqhcPricerContext calculationContext) {
    // Loop through all claim lines and validate, gather rates, and populate the daily summary table
    // *----------------------------------------------------------------*
    // *                                                                *
    // *   STEP 1 - LOOP THROUGH ALL CLAIM LINES: VALIDATE LINES,       *
    // *   ------   GET RATES, AND POPULATE DAY SUMMARY TABLE           *
    // *                                                                *
    // *----------------------------------------------------------------*
    //   PERFORM 3000-VALIDATE-LINE
    //     THRU 3000-VALIDATE-LINE-EXIT
    //       VARYING LN-SUB FROM 1 BY 1
    //         UNTIL LN-SUB > I-SRVC-LINE-CNT.
    for (final IoceServiceLineData lineInput : calculationContext.getIoceServiceLines()) {
      // *----------------------------------------------------------------*
      // *  MOVE SERVICE LINE INPUT RECORD TO WORKING STORAGE HOLD AREA   *
      // *  (TO PROCESS LINE USING CONDITIONS AND WITHOUT SUBSCRIPTS)     *
      // *----------------------------------------------------------------*
      //   MOVE I-SERVICE-LINE (LN-SUB) TO HL-SERVICE-LINE.
      final ServiceLinePaymentData serviceLinePayment =
          calculationContext.getServiceLinePayments(lineInput.getLineNumber());

      // Processes each individual line with a unique context; this is separate from the per-claim
      // context, but still has access to the per-claim information.
      evaluationRule.calculate(
          new ServiceLineContext(lineInput, serviceLinePayment, calculationContext));
    }
  }
}
