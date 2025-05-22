package gov.cms.fiss.pricers.ltch.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.api.v2.LtchPaymentData;
import gov.cms.fiss.pricers.ltch.core.LtchPricerContext;

public class FinalizeClaim
    implements CalculationRule<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {

  @Override
  public void calculate(LtchPricerContext calculationContext) {
    final LtchPaymentData paymentData = calculationContext.getPaymentData();
    // This is used to make sure the output contains the prospective payment record and the request
    // identifier
    calculationContext.getOutput().setPaymentData(paymentData);
    calculationContext
        .getOutput()
        .setCalculationVersion(calculationContext.getCalculationVersion());
  }
}
