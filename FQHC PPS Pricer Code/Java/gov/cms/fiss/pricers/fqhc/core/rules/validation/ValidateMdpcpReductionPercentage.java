package gov.cms.fiss.pricers.fqhc.core.rules.validation;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.fqhc.api.v2.FqhcClaimPricingRequest;
import gov.cms.fiss.pricers.fqhc.api.v2.FqhcClaimPricingResponse;
import gov.cms.fiss.pricers.fqhc.core.FqhcPricerContext;
import gov.cms.fiss.pricers.fqhc.core.ReturnCode;
import java.math.BigDecimal;

public class ValidateMdpcpReductionPercentage
    implements CalculationRule<
        FqhcClaimPricingRequest, FqhcClaimPricingResponse, FqhcPricerContext> {

  @Override
  public boolean shouldExecute(FqhcPricerContext calculationContext) {
    return calculationContext.containsMdpcpDemoCode();
  }

  @Override
  public void calculate(FqhcPricerContext calculationContext) {
    final BigDecimal mdpcpReductionPercentage =
        calculationContext.getClaimData().getMdpcpReductionPercent();

    if (mdpcpReductionPercentage == null || BigDecimalUtils.isZero(mdpcpReductionPercentage)) {
      calculationContext.applyClaimReturnCode(ReturnCode.MDPCP_REDUCTION_PERCENTAGE_IS_ZERO_24);
      return;
    }

    if (!calculationContext.isValidMdpcpReductionPercentage()) {
      calculationContext.applyClaimReturnCode(ReturnCode.INVALID_MDPCP_REDUCTION_PERCENTAGE_23);
    }
  }
}
