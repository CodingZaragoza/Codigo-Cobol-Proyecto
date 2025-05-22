package gov.cms.fiss.pricers.ltch.core.rules;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimData;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.api.v2.LtchPaymentData;
import gov.cms.fiss.pricers.ltch.core.LtchPricerContext;
import java.math.RoundingMode;

public class CalculateFacilityCosts
    implements CalculationRule<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {

  /** Converted from {@code 3000-CALC-PAYMENT}. */
  @Override
  public void calculate(LtchPricerContext calculationContext) {
    final LtchClaimData claimData = calculationContext.getClaimData();
    final LtchPaymentData paymentData = calculationContext.getPaymentData();
    final InpatientProviderData providerData = calculationContext.getProviderData();
    // *-------------------------------------------------------------*
    // * CALCULATE CLAIM COST FOR ALL CLAIMS                         *
    // *-------------------------------------------------------------*
    // COMPUTE PPS-FAC-COSTS ROUNDED =
    //     P-NEW-OPER-CSTCHG-RATIO * B-COV-CHARGES.
    paymentData.setFacilityCosts(
        providerData
            .getOperatingCostToChargeRatio()
            .multiply(claimData.getCoveredCharges())
            .setScale(2, RoundingMode.HALF_UP));
  }
}
