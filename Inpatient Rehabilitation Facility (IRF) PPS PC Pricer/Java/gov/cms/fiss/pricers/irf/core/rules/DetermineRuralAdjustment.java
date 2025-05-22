package gov.cms.fiss.pricers.irf.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimData;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.api.v2.IrfPaymentData;
import gov.cms.fiss.pricers.irf.core.IrfPricerContext;
import gov.cms.fiss.pricers.irf.core.ResultCode;
import gov.cms.fiss.pricers.irf.core.tables.CbsaWageIndexEntry;
import java.math.RoundingMode;

public class DetermineRuralAdjustment
    implements CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  @Override
  public boolean shouldExecute(IrfPricerContext calculationContext) {
    return calculationContext.matchesReturnCode(ResultCode.OK_00);
  }

  @Override
  public void calculate(IrfPricerContext calculationContext) {
    final IrfClaimData claimData = calculationContext.getClaimData();
    final IrfPaymentData paymentData = calculationContext.getPaymentData();
    final CbsaWageIndexEntry billingCbsa = calculationContext.getCbsaWageIndexEntry();

    // MOVE 1.0000          TO PPS-RURAL-ADJUSTMENT
    paymentData.setRuralAdjustmentPercent(
        IrfPricerContext.RURAL_ADJUSTMENT_NONE.setScale(4, RoundingMode.HALF_UP));

    // IF VALID-PROV-NUM
    if (IrfPricerContext.getUrbanToRuralProviders().contains(claimData.getProviderCcn())) {
      // MOVE 1.0497       TO PPS-RURAL-ADJUSTMENT
      paymentData.setRuralAdjustmentPercent(
          calculationContext.getTransitionRuralAdjustment().setScale(4, RoundingMode.HALF_UP));
    } else if (calculationContext.isRuralCbsa(billingCbsa.getCbsa())) {
      // ELSE
      //    IF W-NEW-CBSA (1:3) = '   '
      //       MOVE 1.1490 TO PPS-RURAL-ADJUSTMENT
      paymentData.setRuralAdjustmentPercent(
          calculationContext.getRuralAdjustment().setScale(4, RoundingMode.HALF_UP));
    } else {
      //    ELSE
      //       MOVE 1.0000 TO PPS-RURAL-ADJUSTMENT.
      paymentData.setRuralAdjustmentPercent(
          IrfPricerContext.RURAL_ADJUSTMENT_NONE.setScale(4, RoundingMode.HALF_UP));
    }
  }
}
