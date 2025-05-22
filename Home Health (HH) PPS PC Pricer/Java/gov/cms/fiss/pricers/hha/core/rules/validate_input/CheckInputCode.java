package gov.cms.fiss.pricers.hha.core.rules.validate_input;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimData;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingRequest;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingResponse;
import gov.cms.fiss.pricers.hha.core.HhaPricerContext;
import gov.cms.fiss.pricers.hha.core.codes.ReturnCode;
import java.math.BigDecimal;

// Validate and set HRG data
public class CheckInputCode
    implements CalculationRule<HhaClaimPricingRequest, HhaClaimPricingResponse, HhaPricerContext> {
  @Override
  public void calculate(HhaPricerContext calculationContext) {
    final HhaClaimData input = calculationContext.getClaimData();
    final BigDecimal weight =
        calculationContext.getDataTables().getHrgWeight(input.getHhrgInputCode());
    final Integer threshold =
        calculationContext.getDataTables().getHrgThreshold(input.getHhrgInputCode());
    if (weight == null || threshold == null) {
      calculationContext.completeWithReturnCode(ReturnCode.INVALID_INPUT_CODE_70);
      return;
    }
    calculationContext.getPaymentData().setHhrgWeight(weight);
    calculationContext.setHrgThreshold(threshold);
  }
}
