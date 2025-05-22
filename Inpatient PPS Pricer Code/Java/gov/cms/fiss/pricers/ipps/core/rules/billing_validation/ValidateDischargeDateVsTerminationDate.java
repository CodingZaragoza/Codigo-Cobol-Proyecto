package gov.cms.fiss.pricers.ipps.core.rules.billing_validation;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.ResultCode;
import java.time.LocalDate;

/**
 * Validates the claim's discharge date relative to the termination date.
 *
 * <p>Converted from {@code 1000-EDIT-THE-BILL-INFO} in the COBOL code (continued).
 *
 * @since 2019
 */
public class ValidateDischargeDateVsTerminationDate
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public boolean shouldExecute(IppsPricerContext calculationContext) {
    return ResultCode.RC_00_OK.equals(calculationContext.getResultCode());
  }

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    //     IF  PPS-RTC = 00
    //         IF P-NEW-TERMINATION-DATE > 00000000
    //            IF  ((B-DISCHARGE-DATE = P-NEW-TERMINATION-DATE) OR
    //                 (B-DISCHARGE-DATE > P-NEW-TERMINATION-DATE))
    //                  MOVE 55 TO PPS-RTC.
    final LocalDate terminationDate = calculationContext.getTerminationDate();
    if (null != terminationDate
        && LocalDateUtils.isAfterOrEqual(calculationContext.getDischargeDate(), terminationDate)) {
      calculationContext.applyResultCode(ResultCode.RC_55_DISCHRG_DT_LT_EFF_START_DT);
    }
  }
}
