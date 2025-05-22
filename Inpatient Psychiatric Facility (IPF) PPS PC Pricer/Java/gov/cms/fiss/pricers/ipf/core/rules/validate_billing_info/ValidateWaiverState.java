package gov.cms.fiss.pricers.ipf.core.rules.validate_billing_info;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;
import gov.cms.fiss.pricers.ipf.core.codes.ReturnCode;
import org.apache.commons.lang3.StringUtils;

/**
 * Validate the waiver code.
 *
 * <p>Converted from {@code 1000-EDIT-THE-BILL-INFO} in the COBOL code.
 */
public class ValidateWaiverState
    implements CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {
  @Override
  public void calculate(IpfPricerContext calculationContext) {
    // IF  P-NEW-WAIVER-STATE
    //     MOVE 53 TO IPF-RTC.
    if (StringUtils.equals("Y", calculationContext.getProviderData().getWaiverIndicator())) {
      calculationContext.completeWithReturnCode(ReturnCode.INVALID_WAIVER_STATE_53);
    }
  }
}
