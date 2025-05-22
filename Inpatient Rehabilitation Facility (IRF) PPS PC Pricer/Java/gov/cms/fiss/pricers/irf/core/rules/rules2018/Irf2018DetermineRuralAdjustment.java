package gov.cms.fiss.pricers.irf.core.rules.rules2018;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.api.v2.IrfPaymentData;
import gov.cms.fiss.pricers.irf.core.IrfPricerContext;
import gov.cms.fiss.pricers.irf.core.ResultCode;
import gov.cms.fiss.pricers.irf.core.tables.CbsaWageIndexEntry;
import java.math.RoundingMode;

/**
 * Determines the rural adjustment.
 *
 * <p>Converted from {@code 3510-CHECK-RURAL-ADJ} in the COBOL code.
 */
public class Irf2018DetermineRuralAdjustment
    implements CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  @Override
  public boolean shouldExecute(IrfPricerContext calculationContext) {
    return calculationContext.matchesReturnCode(ResultCode.OK_00);
  }

  @Override
  public void calculate(IrfPricerContext calculationContext) {
    final IrfPaymentData paymentData = calculationContext.getPaymentData();
    final CbsaWageIndexEntry billingCbsa = calculationContext.getCbsaWageIndexEntry();

    // **************************************************************
    // ** FOR FY17, IF PROVIDER IS FOUND ON TABLE, USE 1.0497 *
    // **************************************************************
    // ** RULE: A BILLING PROVIDER RECEIVES A PPS RURAL ADJUSTMENT
    // ** PERCENTAGE OF 1.0497 PERCENT IF THE PROVIDER IS A VALID RURAL
    // ** TO URBAN PROVIDER OR 1.1490 PERCENT IF THE THE PROVIDER HAS NO
    // ** CORE BASED STATISTICAL AREA ASSIGNED
    // ** IF VALID RURAL TO URBAN PROVIDER, USE THE 1/3 ADJUSTMENT
    // **************************************************************
    // ** FOR FY16, IF PROVIDER IS FOUND ON TABLE, USE 1.0993 *
    // **************************************************************
    // ** RULE: A BILLING PROVIDER RECEIVES A PPS RURAL ADJUSTMENT
    // ** PERCENTAGE OF 1.0993 PERCENT IF THE PROVIDER IS A VALID RURAL
    // ** TO URBAN PROVIDER OR 1.1490 PERCENT IF THE THE PROVIDER HAS NO
    // ** CORE BASED STATISTICAL AREA ASSIGNED
    // ** IF VALID RURAL TO URBAN PROVIDER, USE THE 2/3 ADJUSTMENT

    // MOVE 1.0000          TO PPS-RURAL-ADJUSTMENT
    paymentData.setRuralAdjustmentPercent(
        IrfPricerContext.RURAL_ADJUSTMENT_NONE.setScale(4, RoundingMode.HALF_UP));

    // IF W-NEW-CBSA (1:3) = '   '
    if (calculationContext.isRuralCbsa(billingCbsa.getCbsa())) {
      // MOVE 1.1490 TO PPS-RURAL-ADJUSTMENT
      paymentData.setRuralAdjustmentPercent(
          calculationContext.getRuralAdjustment().setScale(4, RoundingMode.HALF_UP));
    } else {
      // ELSE
      //    MOVE 1.0000 TO PPS-RURAL-ADJUSTMENT.
      paymentData.setRuralAdjustmentPercent(
          IrfPricerContext.RURAL_ADJUSTMENT_NONE.setScale(4, RoundingMode.HALF_UP));
    }
  }
}
