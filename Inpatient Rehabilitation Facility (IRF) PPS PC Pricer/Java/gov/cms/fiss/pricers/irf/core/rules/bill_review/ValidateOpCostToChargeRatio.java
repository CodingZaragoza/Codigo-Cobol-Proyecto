package gov.cms.fiss.pricers.irf.core.rules.bill_review;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.core.IrfPricerContext;
import gov.cms.fiss.pricers.irf.core.ResultCode;

/**
 * Validate operatingCostToChargeRatio
 *
 * <pre>
 * **  operatingCostToChargeRatio can't be zero per IRF Policy.  Also, the
 * **  value is used as a divisor in the Outlier calculation and would result in
 * **  a divide by zero error
 * </pre>
 */
public class ValidateOpCostToChargeRatio
    implements CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  @Override
  public boolean shouldExecute(IrfPricerContext calculationContext) {
    return calculationContext.matchesReturnCode(ResultCode.OK_00);
  }

  @Override
  public void calculate(IrfPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();

    // OperatingCostToCharge
    if (BigDecimalUtils.isZero(providerData.getOperatingCostToChargeRatio())) {
      calculationContext.applyResultCode(ResultCode.COST_TO_CHRG_NOT_NUM_65);
    }
  }
}
