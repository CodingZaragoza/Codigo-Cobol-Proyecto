package gov.cms.fiss.pricers.irf.core.rules.bill_review;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.core.IrfPricerContext;
import gov.cms.fiss.pricers.irf.core.ResultCode;
import org.apache.commons.lang3.StringUtils;

/**
 * Validate waiver code.
 *
 * <pre>
 * ** RULE: WAIVER STATE PRICES ARE NOT CALCULATED BY THE PRICER
 * ** PROGRAMS
 * ** REQUIREMENT: THE SYSTEM MUST RETURN AN ERROR CODE (53) TO THE
 * ** CALLING PROGRAM IF THE PRICER PROGRAM IS ASKED TO PRICE A
 * ** PAYMENT FROM A WAIVER STATE
 * ** CHECK PPS-RTC
 * ** FOR ERRORS, IN SUCCESSION
 * </pre>
 *
 * Converted from {@code 1000-EDIT-THE-BILL-INFO} in the COBOL code.
 */
public class ReviewWaiverStatus
    implements CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  @Override
  public boolean shouldExecute(IrfPricerContext calculationContext) {
    return calculationContext.matchesReturnCode(ResultCode.OK_00);
  }

  @Override
  public void calculate(IrfPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();

    // IF P-NEW-WAIVER-STATE
    if (StringUtils.equals(providerData.getWaiverIndicator(), IrfPricerContext.PROV_WAIVER_STATE)) {
      // MOVE 53 TO PPS-RTC.
      calculationContext.applyResultCode(ResultCode.WAIVER_STATE_NOT_CALC_53);
    }
  }
}
