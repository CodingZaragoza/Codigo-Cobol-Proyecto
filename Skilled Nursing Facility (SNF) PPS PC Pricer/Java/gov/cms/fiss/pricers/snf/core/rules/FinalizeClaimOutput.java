package gov.cms.fiss.pricers.snf.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingRequest;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingResponse;
import gov.cms.fiss.pricers.snf.api.v2.SnfPaymentData;
import gov.cms.fiss.pricers.snf.core.SnfPricerContext;

public class FinalizeClaimOutput
    implements CalculationRule<SnfClaimPricingRequest, SnfClaimPricingResponse, SnfPricerContext> {

  /** Applies pricing values to the output. */
  @Override
  public void calculate(SnfPricerContext context) {
    final SnfPaymentData outputRecord = context.getPaymentData();
    outputRecord.setTotalPayment(context.getPaymentRate());
    outputRecord.setValueBasedPurchasingPaymentDifference(context.getPaymentDifference());
    outputRecord.setFinalWageIndex(context.getWageIndex());
  }
}
