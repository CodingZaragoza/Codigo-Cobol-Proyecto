package gov.cms.fiss.pricers.ipps.core.rules.billing_validation;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.ResultCode;

/**
 * Validates the review code for claims with a zero length of stay.
 *
 * <p>Converted from {@code 1000-EDIT-THE-BILL-INFO} in the COBOL code (continued).
 *
 * @since 2019
 */
public class ValidateLengthOfStay
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public boolean shouldExecute(IppsPricerContext calculationContext) {
    return ResultCode.RC_00_OK.equals(calculationContext.getResultCode());
  }

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    //     IF  PPS-RTC = 00
    //         IF  B-LOS NOT NUMERIC
    //             MOVE 56 TO PPS-RTC
    //         ELSE
    //         IF  B-LOS = 0
    //             IF B-REVIEW-CODE NOT = 00 AND
    //                              NOT = 03 AND
    //                              NOT = 06 AND
    //                              NOT = 07 AND
    //                              NOT = 09 AND
    //                              NOT = 11
    //             MOVE 56 TO PPS-RTC.
    if (0 == calculationContext.getClaimData().getLengthOfStay()
        && calculationContext.isInvalidReviewCode()) {
      calculationContext.applyResultCode(ResultCode.RC_56_INVALID_LENGTH_OF_STAY);
    }
  }
}
