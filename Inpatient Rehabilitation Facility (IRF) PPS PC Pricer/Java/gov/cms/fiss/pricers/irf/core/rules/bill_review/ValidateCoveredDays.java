package gov.cms.fiss.pricers.irf.core.rules.bill_review;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimData;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.api.v2.IrfPaymentData;
import gov.cms.fiss.pricers.irf.core.IrfPricerContext;
import gov.cms.fiss.pricers.irf.core.ResultCode;

/**
 * Validate covered days.
 *
 * <pre>
 * ** REQUIREMENT: THE SYSTEM MUST RETURN AN ERROR CODE FOR AN
 * ** INVALID NUMBER OF COVERED DAYS (CODE = 62) IF THE NUMBER OF
 * ** BILLED DAYS IS NOT NUMERIC
 * ** REQUIREMENT: THE SYSTEM MUST RETURN AN ERROR CODE FOR AN
 * ** INVALID NUMBER OF COVERED DAYS (CODE = 62) IF THE NUMBER OF
 * ** BILLED DAYS IS EQUAL TO ZERO BUT THE LENGTH OF STAY IS GREATER
 * ** THAN ZERO DAYS
 * </pre>
 *
 * Converted from {@code 1000-EDIT-THE-BILL-INFO} in the COBOL code.
 */
public class ValidateCoveredDays
    implements CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  @Override
  public boolean shouldExecute(IrfPricerContext calculationContext) {
    return calculationContext.matchesReturnCode(ResultCode.OK_00);
  }

  @Override
  public void calculate(IrfPricerContext calculationContext) {
    final IrfClaimData claimData = calculationContext.getClaimData();
    final IrfPaymentData paymentData = calculationContext.getPaymentData();

    // MOVE B-LTR-DAYS TO PPS-LTR-DAYS-USED.
    paymentData.setLifetimeReserveDaysUsed(claimData.getLifetimeReserveDays());

    // IF B-COV-DAYS = 0 AND H-LOS > 0
    if (claimData.getCoveredDays() == 0 && paymentData.getLengthOfStay() > 0) {
      //   MOVE 62 TO PPS-RTC.
      calculationContext.applyResultCode(
          ResultCode.INVALID_NBR_COVERED_DAYS_62,
          ResultCode.INVALID_NBR_COVERED_DAYS_62.getDescription(),
          "Number of billed days must be numeric and not equal to zero if length of stay is "
              + "greater than zero");
    }
  }
}
