package gov.cms.fiss.pricers.snf.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingRequest;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingResponse;
import gov.cms.fiss.pricers.snf.core.SnfPricerContext;
import org.apache.commons.lang3.BooleanUtils;

public class SetIndicators
    implements CalculationRule<SnfClaimPricingRequest, SnfClaimPricingResponse, SnfPricerContext> {

  /** Apply indicator values to the output record. */
  @Override
  public void calculate(SnfPricerContext snfPricerContext) {
    snfPricerContext
        .getPaymentData()
        .setAidsAddOnIndicator(
            BooleanUtils.toString(snfPricerContext.hasAidsAdjustment(), "Y", "N"));

    snfPricerContext
        .getPaymentData()
        .setQualityReportingProgramIndicator(
            BooleanUtils.toString(snfPricerContext.hasQualityAdjustment(), "Y", "N"));
  }
}
