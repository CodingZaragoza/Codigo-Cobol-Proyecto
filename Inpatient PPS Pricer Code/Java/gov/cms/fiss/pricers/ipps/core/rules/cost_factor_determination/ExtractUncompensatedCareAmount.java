package gov.cms.fiss.pricers.ipps.core.rules.cost_factor_determination;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;

/**
 * Captures the uncompensated care amount. If this rule is not executed, the default value will be
 * zero. The uncompensated care value in the calculations and results must be zero for all
 * non-default result codes, so the holding variable in the context is required.
 *
 * <p>Converted from {@code 2000-ASSEMBLE-PPS-VARIABLES} in the COBOL code (continued).
 *
 * @since 2019
 */
public class ExtractUncompensatedCareAmount
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();

    //        COMPUTE WK-UNCOMP-CARE-AMOUNT = P-UNCOMP-CARE-AMOUNT.
    //        COMPUTE H-UNCOMP-CARE-AMOUNT = P-UNCOMP-CARE-AMOUNT.
    // This ensures that the value will be zero for any non-default cases
    calculationContext.setUncompensatedCareAmount(providerData.getUncompensatedCareAmount());
  }
}
