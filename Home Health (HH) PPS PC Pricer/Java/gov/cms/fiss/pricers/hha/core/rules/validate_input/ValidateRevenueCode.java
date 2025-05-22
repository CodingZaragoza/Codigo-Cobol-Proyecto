package gov.cms.fiss.pricers.hha.core.rules.validate_input;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingRequest;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingResponse;
import gov.cms.fiss.pricers.hha.api.v2.RevenueLineData;
import gov.cms.fiss.pricers.hha.core.HhaPricerContext;
import gov.cms.fiss.pricers.hha.core.codes.ReturnCode;
import org.apache.commons.lang3.StringUtils;

// HHDRV validates all HHA-REVENUE-CODE entries
public class ValidateRevenueCode
    implements CalculationRule<HhaClaimPricingRequest, HhaClaimPricingResponse, HhaPricerContext> {
  @Override
  public boolean shouldExecute(HhaPricerContext calculationContext) {
    return calculationContext.isBillTypeClaim();
  }

  @Override
  public void calculate(HhaPricerContext calculationContext) {
    for (final RevenueLineData entry : calculationContext.getRevenueLines()) {
      if (StringUtils.isBlank(entry.getRevenueCode())) {
        calculationContext.completeWithReturnCode(ReturnCode.REVENUE_CODE_NOT_SET_85);
        break;
      }
    }
  }
}
