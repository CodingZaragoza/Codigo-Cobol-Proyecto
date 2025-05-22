package gov.cms.fiss.pricers.irf.core.rules.bill_review;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimData;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.core.IrfPricerContext;
import gov.cms.fiss.pricers.irf.core.ResultCode;

/**
 * Validate lifetime reserve days.
 *
 * <pre>
 * ** REQUIREMENT: THE SYSTEM MUST RETURN AN ERROR CODE (58) IF THE
 * ** BILL'S COVERED CHARGES ARE NOT NUMERIC VALUES
 *
 * ** REQUIREMENT: THE SYSTEM MUST RETURN AN ERROR CODE IF THE
 * ** BILL'S LIFETIME RESERVE DAYS IS NOT NUMERIC OR IS GREATER
 * ** THAN 60
 * ** RULE: A BILL'S LIFETIME RESERVE DAYS USED MUST BE INCLUDED
 * ** WHEN CALCULATING A PPS LIFETIME RESERVED DAYS USED
 * </pre>
 *
 * Converted from {@code 1000-EDIT-THE-BILL-INFO} in the COBOL code.
 */
public class ValidateLifetimeReserveDays
    implements CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  @Override
  public boolean shouldExecute(IrfPricerContext calculationContext) {
    return calculationContext.matchesReturnCode(ResultCode.OK_00);
  }

  @Override
  public void calculate(IrfPricerContext calculationContext) {
    final IrfClaimData claimData = calculationContext.getClaimData();

    // IF B-LTR-DAYS NOT NUMERIC OR B-LTR-DAYS > 60
    if (claimData.getLifetimeReserveDays() > calculationContext.getMaxLifetimeReserveDays()) {
      //    MOVE 61 TO PPS-RTC
      calculationContext.applyResultCode(ResultCode.LTR_NOT_NUM_OR_GT60_61);
    }
  }
}
