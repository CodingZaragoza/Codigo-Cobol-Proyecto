package gov.cms.fiss.pricers.ltch.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.api.v2.LtchPaymentData;
import gov.cms.fiss.pricers.ltch.core.LtchPricerContext;
import gov.cms.fiss.pricers.ltch.core.codes.SecondaryPaymentTypeSiteNeutral;

public class SetSiteNeutralCodes
    implements CalculationRule<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {

  @Override
  public void calculate(LtchPricerContext calculationContext) {
    final LtchPaymentData paymentData = calculationContext.getPaymentData();
    if (calculationContext.isPaymentBlendOrSiteNeutral()) {
      calculationContext.setSecondaryPaymentTypeSiteNeutral(SecondaryPaymentTypeSiteNeutral.IPPS);
      paymentData.setSiteNeutralIppsPayment(calculationContext.getHoldIppsPerDiem());
      calculationContext.setCalculateOutliers(true);
    }
  }
}
