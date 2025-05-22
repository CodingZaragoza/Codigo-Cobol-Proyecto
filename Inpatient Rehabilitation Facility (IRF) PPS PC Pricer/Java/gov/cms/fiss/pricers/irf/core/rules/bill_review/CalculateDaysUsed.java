package gov.cms.fiss.pricers.irf.core.rules.bill_review;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimData;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.api.v2.IrfPaymentData;
import gov.cms.fiss.pricers.irf.core.IrfPricerContext;
import gov.cms.fiss.pricers.irf.core.ResultCode;

/**
 * Calculate days used.
 *
 * <pre>
 * ** RULE: A BILL IS NOT PAID IF THE PATIENT HAS EXCEEDED THEIR
 * ** ALLOWABLE LIFETIME DAYS OF COVERAGE
 * ** REQUIREMENT: THE SYSTEM MUST RETURN AN ERROR CODE FOR AN
 * ** INVALID NUMBER OF COVERED DAYS (CODE = 62) IF THE PATIENT HAS
 * ** EXCEEDED THEIR LIFETIME COVERAGE DAYS
 * ** RULE: THE PPS REGULAR DAYS USED IS SET TO THE BILL'S COVERED
 * ** DAYS MINUS THE BILL'S LIFETIME RESERVE DAYS WHEN THE PATIENT
 * ** HAS LIFETIME RESERVE DAYS LEFT
 * </pre>
 *
 * Converted from {@code 1000-EDIT-THE-BILL-INFO} in the COBOL code.
 */
public class CalculateDaysUsed
    implements CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  @Override
  public boolean shouldExecute(IrfPricerContext calculationContext) {
    return calculationContext.matchesReturnCode(ResultCode.OK_00);
  }

  @Override
  public void calculate(IrfPricerContext calculationContext) {
    final IrfClaimData claimData = calculationContext.getClaimData();
    final IrfPaymentData paymentData = calculationContext.getPaymentData();

    // IF B-LTR-DAYS  > B-COV-DAYS
    if (claimData.getLifetimeReserveDays() > claimData.getCoveredDays()) {
      //    MOVE 62 TO PPS-RTC
      calculationContext.applyResultCode(ResultCode.INVALID_NBR_COVERED_DAYS_62);
      return;
    } else {
      // ELSE
      //    COMPUTE PPS-REG-DAYS-USED = B-COV-DAYS - B-LTR-DAYS.
      paymentData.setRegularDaysUsed(
          claimData.getCoveredDays() - claimData.getLifetimeReserveDays());
    }

    // ** RULE: THE PPS REGULAR DAYS USED IS SET TO WHICHEVER IS THE
    // ** LOWER VALUE, THE BILL'S LENGTH OF STAY OR THE PPS REGULAR DAYS
    // ** USED.

    // IF PPS-REG-DAYS-USED > 0
    //    IF PPS-REG-DAYS-USED > H-LOS
    if (paymentData.getRegularDaysUsed() > 0
        && paymentData.getRegularDaysUsed() > paymentData.getLengthOfStay()) {
      //       MOVE H-LOS TO PPS-REG-DAYS-USED
      paymentData.setRegularDaysUsed(paymentData.getLengthOfStay());
    } else if (claimData.getLifetimeReserveDays() > paymentData.getLengthOfStay()) {
      // ELSE
      //    IF B-LTR-DAYS > H-LOS
      //       MOVE H-LOS TO PPS-LTR-DAYS-USED
      paymentData.setLifetimeReserveDaysUsed(paymentData.getLengthOfStay());
    } else {
      // ELSE
      //    MOVE B-LTR-DAYS TO PPS-LTR-DAYS-USED.
      paymentData.setLifetimeReserveDaysUsed(claimData.getLifetimeReserveDays());
    }
  }
}
