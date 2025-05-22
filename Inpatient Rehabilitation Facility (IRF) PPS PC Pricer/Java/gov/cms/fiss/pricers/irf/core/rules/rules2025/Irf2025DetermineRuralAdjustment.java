package gov.cms.fiss.pricers.irf.core.rules.rules2025;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.api.v2.IrfInpatientProviderData;
import gov.cms.fiss.pricers.irf.api.v2.IrfPaymentData;
import gov.cms.fiss.pricers.irf.core.IrfPricerContext;
import gov.cms.fiss.pricers.irf.core.ResultCode;
import gov.cms.fiss.pricers.irf.core.tables.CbsaWageIndexEntry;
import java.math.RoundingMode;
import org.apache.commons.lang3.StringUtils;

/**
 * Determines the rural adjustment.
 *
 * <p>Converted from {@code 3510-CHECK-RURAL-ADJ} in the COBOL code.
 */
public class Irf2025DetermineRuralAdjustment
    implements CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  @Override
  public boolean shouldExecute(IrfPricerContext calculationContext) {
    return calculationContext.matchesReturnCode(ResultCode.OK_00);
  }

  @Override
  public void calculate(IrfPricerContext calculationContext) {
    final IrfPaymentData paymentData = calculationContext.getPaymentData();
    final CbsaWageIndexEntry billingCbsa = calculationContext.getCbsaWageIndexEntry();
    final IrfInpatientProviderData providerData = calculationContext.getInput().getProviderData();

    // **************************************************************
    // ** FOR FY2025, IF PROVIDER HAS 3 in supplementalWageIndexIndicator use 1.0993
    // **************************************************************
    // ** RULE: A BILLING PROVIDER RECEIVES A PPS RURAL ADJUSTMENT
    // ** PERCENTAGE OF 1.0993 PERCENT IF THE PROVIDER IS A VALID RURAL
    // ** TO URBAN PROVIDER.  OTHERWISE USE THE DEFAULT RATE FOR RURAL PROVIDERS.
    // ** IF VALID RURAL TO URBAN PROVIDER, USE THE 2/3 ADJUSTMENT
    // **************************************************************
    // ** FOR FY2026, IF PROVIDER HAS 3 IN supplmentalWageIndexIndicator USE 1.0497
    // **************************************************************
    // ** RULE: A BILLING PROVIDER RECEIVES A PPS RURAL ADJUSTMENT
    // ** PERCENTAGE OF 1.0497 PERCENT IF THE PROVIDER IS A VALID RURAL
    // ** TO URBAN PROVIDER IN FY2027.  OTHERWISE USE THE DEFAULT RATE FOR RURAL PROVIDERS.
    // ** IF VALID RURAL TO URBAN PROVIDER, USE 1/3 ADJUSTMENT

    // MOVE 1.0000          TO PPS-RURAL-ADJUSTMENT
    paymentData.setRuralAdjustmentPercent(
        IrfPricerContext.RURAL_ADJUSTMENT_NONE.setScale(4, RoundingMode.HALF_UP));

    // IF W-NEW-CBSA (1:3) = '   '
    if (calculationContext.isRuralCbsa(billingCbsa.getCbsa())) {
      // MOVE DEFAULT RURAL ADJUSTMENT (1.1490) TO RURAL PROVIDERS
      paymentData.setRuralAdjustmentPercent(
          calculationContext.getRuralAdjustment().setScale(4, RoundingMode.HALF_UP));

    } else if (StringUtils.equals(providerData.getSupplementalWageIndexIndicator(), "3")) {
      // move 1.0993 for rural to urban transitions in FY2025
      paymentData.setRuralAdjustmentPercent(
          calculationContext.getTransitionRuralAdjustment().setScale(4, RoundingMode.HALF_UP));
    } else {
      // ELSE
      //    MOVE 1.0000 TO PPS-RURAL-ADJUSTMENT.
      paymentData.setRuralAdjustmentPercent(
          IrfPricerContext.RURAL_ADJUSTMENT_NONE.setScale(4, RoundingMode.HALF_UP));
    }
  }
}
