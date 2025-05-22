package gov.cms.fiss.pricers.fqhc.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.fqhc.api.v2.FqhcClaimData;
import gov.cms.fiss.pricers.fqhc.api.v2.FqhcClaimPricingRequest;
import gov.cms.fiss.pricers.fqhc.api.v2.FqhcClaimPricingResponse;
import gov.cms.fiss.pricers.fqhc.core.FqhcPricerContext;
import gov.cms.fiss.pricers.fqhc.core.ReturnCode;

/** Converted from {@code 0000-MAIN-CONTROL} in the COBOL code. */
public class DefaultServiceDateValidation
    implements CalculationRule<
        FqhcClaimPricingRequest, FqhcClaimPricingResponse, FqhcPricerContext> {

  @Override
  public void calculate(FqhcPricerContext calculationContext) {
    final FqhcClaimData inputRecord = calculationContext.getClaimData();
    // *----------------------------------------------------------------*
    // * CLAIM FROM DATE IS VALID; PROCESS THE CLAIM                    *
    // *----------------------------------------------------------------*
    //      IF I-SRVC-FROM-DATE NUMERIC AND
    //         I-SRVC-FROM-DATE >= W-FQHC-PPS-START-DATE
    if (LocalDateUtils.isAfterOrEqual(
        inputRecord.getServiceFromDate(), calculationContext.getFqhcStartDate())) {
      // Date allowed so set initial valid return code and process claim
      calculationContext.applyClaimReturnCode(ReturnCode.CLAIM_OK_01);

      //       PERFORM 2000-PROCESS-CLAIM
      //          THRU 2000-PROCESS-CLAIM-EXIT
    } else {
      // *----------------------------------------------------------------*
      // * CLAIM FROM DATE IS INVALID; DO NOT PROCESS THE CLAIM           *
      // *----------------------------------------------------------------*
      //    ELSE
      //      MOVE 02 TO O-CLM-RETURN-CODE
      //    END-IF.
      calculationContext.applyClaimReturnCode(ReturnCode.CLAIM_INVALID_SERVICE_DATE_02);
      calculationContext.setCalculationCompleted();
    }
  }
}
