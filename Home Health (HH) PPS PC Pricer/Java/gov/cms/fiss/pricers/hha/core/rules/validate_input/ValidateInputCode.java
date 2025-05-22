package gov.cms.fiss.pricers.hha.core.rules.validate_input;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimData;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingRequest;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingResponse;
import gov.cms.fiss.pricers.hha.core.HhaPricerContext;
import gov.cms.fiss.pricers.hha.core.codes.ReturnCode;
import org.apache.commons.lang3.StringUtils;

// HHDRV includes HHA-VALID-TOB-CLAIM
public class ValidateInputCode
    implements CalculationRule<HhaClaimPricingRequest, HhaClaimPricingResponse, HhaPricerContext> {
  @Override
  public void calculate(HhaPricerContext calculationContext) {
    final HhaClaimData claimData = calculationContext.getClaimData();
    if (calculationContext.isBillTypeClaim() && StringUtils.isBlank(claimData.getHhrgInputCode())) {
      calculationContext.completeWithReturnCode(ReturnCode.INVALID_INPUT_CODE_70);
    }
  }
}
