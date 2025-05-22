package gov.cms.fiss.pricers.ipf.core.rules.validate_billing_info;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;
import gov.cms.fiss.pricers.ipf.core.codes.ReturnCode;

/**
 * Validate the patient's length of stay.
 *
 * <p>Converted from {@code 1000-EDIT-THE-BILL-INFO} in the COBOL code.
 */
public class ValidateLengthOfStay
    implements CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {
  @Override
  public void calculate(IpfPricerContext calculationContext) {
    // IF  BILL-LOS NOT NUMERIC OR
    //     BILL-LOS = ZERO
    //     MOVE 56 TO IPF-RTC.
    if (calculationContext.getClaimData().getLengthOfStay() == 0) {
      calculationContext.completeWithReturnCode(ReturnCode.INVALID_LENGTH_OF_STAY_56);
    }
  }
}
