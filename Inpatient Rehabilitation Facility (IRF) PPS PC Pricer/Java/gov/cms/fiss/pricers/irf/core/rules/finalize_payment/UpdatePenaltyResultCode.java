package gov.cms.fiss.pricers.irf.core.rules.finalize_payment;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.api.v2.IrfPaymentData;
import gov.cms.fiss.pricers.irf.core.IrfPricerContext;
import gov.cms.fiss.pricers.irf.core.ResultCode;
import org.apache.commons.lang3.StringUtils;

/**
 * Updates result code to include penalty.
 *
 * <p>Converted from {@code 5000-FINAL-PAYMENTS} in the COBOL code.
 */
public class UpdatePenaltyResultCode
    implements CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  @Override
  public void calculate(IrfPricerContext calculationContext) {
    final IrfPaymentData paymentData = calculationContext.getPaymentData();

    // ** REQUIREMENT: THE SYSTEM MUST PROVIDE RETURN CODES FOR
    // ** PROCESSING PAYMENTS WITH PENALTIES THAT ARE DIFFERENT THAN THE
    // ** RETURN CODE FOR PAYMENTS PROCESSED WITHOUT PENALTIES. PENALTY
    // ** RETURN CODES ARE ALWAYS 10 MORE THAN (ADD 10 TO) THE
    // ** CORRESPONDING NO PENALTY RETURN CODE

    // IF B-SPEC-PAY-IND = '2' OR '3'
    if (calculationContext.hasPenalties()) {
      // COMPUTE PPS-RTC = PPS-RTC + 10.
      calculationContext.applyResultCode(calculationContext.getResultCode().adjustForPenalty());
    }

    // ** REQUIREMENT:THE SYSTEM MUST RETURN AN ERROR (CODE 67) WHEN THE
    // ** BILLED LENGTH OF STAY EXCEEDS THE ALLOWED (PPS REGULAR DAYS
    // ** USED PLUS THE PPS LIFETIME RESERVED DAYS) OR THIS IS A COST
    // ** OUTLIER THRESHOLD CALCULATION. IN OTHER WORDS, FLAG AN ERROR
    // ** WHEN THE PATIENT HAS EXCEEDED THEIR MAXIMUM ALLOWABLE DAYS OF
    // ** COVERAGE OR THIS IS A COST OUTLIER THRESHOLD CALCULATION.

    // IF PPS-RTC = (01 OR 03 OR 05 OR 07
    //                 OR 11 OR 13 OR 15 OR 17)
    //    IF ((PPS-REG-DAYS-USED + PPS-LTR-DAYS-USED) < H-LOS)
    //            OR PPS-COT-IND = 'Y'
    if (calculationContext.getResultCode().isOutlier()
        && (paymentData.getLengthOfStay()
                > paymentData.getRegularDaysUsed() + paymentData.getLifetimeReserveDaysUsed()
            || StringUtils.equals(
                paymentData.getCostOutlierThresholdIdentifier(), IrfPricerContext.YES_INDICATOR))) {
      //     MOVE 67 TO PPS-RTC.
      calculationContext.applyResultCode(ResultCode.OUTLIER_LOS_GT_COVERED_DAYS_67);
    }
  }
}
