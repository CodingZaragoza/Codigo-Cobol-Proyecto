package gov.cms.fiss.pricers.esrd.core.rules.validation;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import gov.cms.fiss.pricers.esrd.core.codes.ReturnCode;

/**
 * Performs revenue code validation.
 *
 * <p>Converted from {@code 1000-VALIDATE-BILL-ELEMENTS} in the COBOL code.
 *
 * @since 2020
 */
public class ValidateRevenueCode
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public boolean shouldExecute(EsrdPricerContext calculationContext) {
    //    IF PPS-RTC = 00  THEN
    //    END-IF.
    return calculationContext.matchesReturnCode(ReturnCode.CALCULATION_STARTED_00);
  }

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    //       IF B-REV-CODE  = '0821' OR '0831' OR '0841' OR '0851'
    //                               OR '0881'
    //          NEXT SENTENCE
    //       ELSE
    //          MOVE 57                     TO PPS-RTC
    //       END-IF
    if (calculationContext.isRevenueCodeInvalid()) {
      calculationContext.applyReturnCode(ReturnCode.INVALID_REVENUE_CODE_57);
    }
  }
}
