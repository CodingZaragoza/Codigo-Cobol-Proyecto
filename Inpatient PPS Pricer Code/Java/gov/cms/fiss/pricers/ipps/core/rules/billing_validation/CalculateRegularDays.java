package gov.cms.fiss.pricers.ipps.core.rules.billing_validation;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimData;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.ResultCode;

/**
 * Calculates the regular days for the claim.
 *
 * <p>Converted from {@code 1000-EDIT-THE-BILL-INFO} in the COBOL code (continued).
 *
 * @since 2019
 */
public class CalculateRegularDays
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
    //         IF  H-LTR-DAYS  > H-COV-DAYS
    //             MOVE 62 TO PPS-RTC
    //         ELSE
    //             COMPUTE H-REG-DAYS = H-COV-DAYS - H-LTR-DAYS.
    if (claimData.getLifetimeReserveDays() > claimData.getCoveredDays()) {
      calculationContext.applyResultCode(ResultCode.RC_62_INVALID_NBR_COVERED_DAYS);
    } else {
      calculationContext.setRegularDays(
          claimData.getCoveredDays() - claimData.getLifetimeReserveDays());
    }
  }
}
