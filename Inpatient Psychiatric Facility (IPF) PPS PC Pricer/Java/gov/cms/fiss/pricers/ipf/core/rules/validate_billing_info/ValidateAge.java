package gov.cms.fiss.pricers.ipf.core.rules.validate_billing_info;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;
import gov.cms.fiss.pricers.ipf.core.codes.ReturnCode;

/**
 * Validate the patient's age.
 *
 * <p>Converted from {@code 1000-EDIT-THE-BILL-INFO} in the COBOL code.
 */
public class ValidateAge
    implements CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {
  @Override
  public void calculate(IpfPricerContext calculationContext) {
    // IF  BILL-AGE NOT NUMERIC OR
    //     BILL-AGE = ZERO
    //     MOVE 57 TO IPF-RTC.
    if (calculationContext.getClaimData().getPatientAge() == 0) {
      calculationContext.completeWithReturnCode(ReturnCode.INVALID_AGE_57);
    }
  }
}
