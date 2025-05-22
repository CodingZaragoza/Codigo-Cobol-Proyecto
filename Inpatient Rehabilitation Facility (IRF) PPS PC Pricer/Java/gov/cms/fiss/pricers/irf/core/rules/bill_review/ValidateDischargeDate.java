package gov.cms.fiss.pricers.irf.core.rules.bill_review;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.core.IrfPricerContext;
import gov.cms.fiss.pricers.irf.core.ResultCode;
import gov.cms.fiss.pricers.irf.core.tables.CbsaWageIndexEntry;

/**
 * Validate discharge date.
 *
 * <pre>
 * ** RULE: PAYMENTS WILL NOT BE MADE WHEN THE CLAIM'S DISCHARGE
 * ** DATE IS LESS THAN THE PROVIDER'S PSF RECORD'S EFFECTIVE DATE
 * ** OR THE WAGE INDEX EFFECTIVE DATE
 * ** REQUIREMENT: THE SYSTEM MUST RETURN AN ERROR CODE (55) TO THE
 * ** CALLING PROGRAM WHEN THE CLAIM'S DISCHARGE DATE IS BEFORE/LESS
 * ** THAN THE PROVIDER'S PSF RECORD'S EFFECTIVE DATE OR WAGE INDEX
 * ** EFFECTIVE DATE
 * </pre>
 *
 * Converted from {@code 1000-EDIT-THE-BILL-INFO} in the COBOL code.
 */
public class ValidateDischargeDate
    implements CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  @Override
  public boolean shouldExecute(IrfPricerContext calculationContext) {
    return calculationContext.matchesReturnCode(ResultCode.OK_00);
  }

  @Override
  public void calculate(IrfPricerContext calculationContext) {
    final CbsaWageIndexEntry billingCbsa = calculationContext.getCbsaWageIndexEntry();

    // IF ((B-DISCHARGE-DATE < P-NEW-EFF-DATE) OR
    //    (B-DISCHARGE-DATE < W-NEW-EFF-DATE-C))
    if (LocalDateUtils.isBefore(
            calculationContext.getClaimDischargeDate(),
            calculationContext.getProviderEffectiveDate())
        || LocalDateUtils.isBefore(
            calculationContext.getClaimDischargeDate(), billingCbsa.getEffectiveDate())) {
      //    MOVE 55 TO PPS-RTC.
      calculationContext.applyResultCode(ResultCode.DISCHRG_DT_LT_EFF_START_DT_55);
    }
  }
}
