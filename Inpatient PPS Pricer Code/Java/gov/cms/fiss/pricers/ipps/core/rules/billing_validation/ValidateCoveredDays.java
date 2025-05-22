package gov.cms.fiss.pricers.ipps.core.rules.billing_validation;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimData;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.ResultCode;

/**
 * Validates the claim's covered days.
 *
 * <p>Converted from {@code 1000-EDIT-THE-BILL-INFO} in the COBOL code (continued).
 *
 * @since 2019
 */
public class ValidateCoveredDays
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
    //         IF  B-COVERED-DAYS NOT NUMERIC
    //             MOVE 62 TO PPS-RTC
    //         ELSE
    //         IF  B-COVERED-DAYS = 0 AND B-LOS > 0
    //             MOVE 62 TO PPS-RTC
    //         ELSE
    //             MOVE B-COVERED-DAYS TO H-COV-DAYS.
    if (claimData.getCoveredDays() == 0 && claimData.getLengthOfStay() > 0) {
      calculationContext.applyResultCode(ResultCode.RC_62_INVALID_NBR_COVERED_DAYS);
    }
  }
}
