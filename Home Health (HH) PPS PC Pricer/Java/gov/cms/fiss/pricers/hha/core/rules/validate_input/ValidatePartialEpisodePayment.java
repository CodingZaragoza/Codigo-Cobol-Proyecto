package gov.cms.fiss.pricers.hha.core.rules.validate_input;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimData;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingRequest;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingResponse;
import gov.cms.fiss.pricers.hha.core.HhaPricerContext;
import gov.cms.fiss.pricers.hha.core.codes.ReturnCode;

public class ValidatePartialEpisodePayment
    implements CalculationRule<HhaClaimPricingRequest, HhaClaimPricingResponse, HhaPricerContext> {
  @Override
  public void calculate(HhaPricerContext calculationContext) {
    final HhaClaimData claimData = calculationContext.getClaimData();
    if (calculationContext.isBillTypeClaim()
        && calculationContext.isPartialEpisodePaymentIndicator()
        && claimData.getHhrgNumberOfDays() == 0) {
      calculationContext.completeWithReturnCode(ReturnCode.NUMBER_OF_DAYS_IS_ZERO_15);
    }
  }
}
