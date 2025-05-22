package gov.cms.fiss.pricers.irf.core.rules.bill_review;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimData;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.api.v2.IrfPaymentData;
import gov.cms.fiss.pricers.irf.core.IrfPricerContext;
import gov.cms.fiss.pricers.irf.core.ResultCode;

/**
 * Validate length of stay.
 *
 * <pre>
 * ********************
 * ** REQUIREMENT: THE SYSTEM MUST VALIDATE THAT THE LENGTH OF STAY
 * ** ON THE BILL IS NUMERIC
 * ** REQUIREMENT: THE SYSTEM MUST VALIDATE THAT THE BILLED LENGTH
 * ** OF STAY IS A NUMBER THAT IS ZERO OR GREATER
 *
 * ** RULE: A BILLED LENGTH OF STAY FOR ZERO DAYS, MEANING THE
 * ** PATIENT WAS RELEASED ON THE SAME DAY, IS ASSIGNED A VALUE OF
 * ** ONE DAY
 * ** REQUIREMENT: THE SYSTEM MUST RETURN AN INVALID LENGTH OF STAY
 * ** CODE (56) TO THE CALLING PROGRAM IF THE BILLED LENGTH OF STAY
 * ** IN NOT NUMERIC OR IS A NUMBER LESS THAN ZERO
 * </pre>
 *
 * Converted from {@code 1000-EDIT-THE-BILL-INFO} in the COBOL code.
 */
public class ValidateLengthOfStay
    implements CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  @Override
  public boolean shouldExecute(IrfPricerContext calculationContext) {
    return calculationContext.matchesReturnCode(ResultCode.OK_00);
  }

  @Override
  public void calculate(IrfPricerContext calculationContext) {
    final IrfClaimData claimData = calculationContext.getClaimData();
    final IrfPaymentData paymentData = calculationContext.getPaymentData();

    // IF (B-LOS NUMERIC) AND (B-LOS > 0)
    if (claimData.getLengthOfStay() > 0) {
      // MOVE B-LOS TO H-LOS
      paymentData.setLengthOfStay(claimData.getLengthOfStay());
    } else {
      // ELSE
      //    IF B-LOS = 0
      if (claimData.getLengthOfStay() == 0) {
        // MOVE 1 TO H-LOS
        paymentData.setLengthOfStay(1);
      } else {
        // ELSE
        //    MOVE 56 TO PPS-RTC.
        calculationContext.applyResultCode(ResultCode.INVALID_LENGTH_OF_STAY_56);
      }
    }
  }
}
