package gov.cms.fiss.pricers.ipps.core.rules.billing_validation;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.ResultCode;

/**
 * Validates the claim's discharge date relative to the effective date.
 *
 * <p>Converted from {@code 1000-EDIT-THE-BILL-INFO} in the COBOL code (continued).
 *
 * @since 2019
 */
public class ValidateDischargeDateVsEffectiveDate
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public boolean shouldExecute(IppsPricerContext calculationContext) {
    return ResultCode.RC_00_OK.equals(calculationContext.getResultCode());
  }

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    //     IF  PPS-RTC = 00
    //            IF  ((B-DISCHARGE-DATE < P-NEW-EFF-DATE) OR
    //                 (B-DISCHARGE-DATE < W-CBSA-EFF-DATE))
    //                MOVE 55 TO PPS-RTC.
    if (LocalDateUtils.isBefore(
            calculationContext.getDischargeDate(), calculationContext.getEffectiveDate())
        || LocalDateUtils.isBefore(
            calculationContext.getDischargeDate(),
            calculationContext.getCbsaReference().getEffectiveDate())) {
      calculationContext.applyResultCode(ResultCode.RC_55_DISCHRG_DT_LT_EFF_START_DT);
    }
  }
}
