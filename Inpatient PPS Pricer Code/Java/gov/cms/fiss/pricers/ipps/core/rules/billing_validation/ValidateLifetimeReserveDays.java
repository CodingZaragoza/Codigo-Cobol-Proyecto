package gov.cms.fiss.pricers.ipps.core.rules.billing_validation;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimData;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.ResultCode;

/**
 * Validates the claim's lifetime reserve days.
 *
 * <p>Converted from {@code 1000-EDIT-THE-BILL-INFO} in the COBOL code (continued).
 *
 * @since 2019
 */
public class ValidateLifetimeReserveDays
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public boolean shouldExecute(IppsPricerContext calculationContext) {
    return ResultCode.RC_00_OK == calculationContext.getResultCode();
  }

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final IppsClaimData claimData = calculationContext.getClaimData();

    //     IF  PPS-RTC = 00
    //         IF  B-LTR-DAYS NOT NUMERIC OR B-LTR-DAYS > 60
    //             MOVE 61 TO PPS-RTC
    //         ELSE
    //             MOVE B-LTR-DAYS TO H-LTR-DAYS.
    if (claimData.getLifetimeReserveDays() > 60) {
      calculationContext.applyResultCode(ResultCode.RC_61_LTR_NOT_NUM_OR_GT60);
    }
  }
}
