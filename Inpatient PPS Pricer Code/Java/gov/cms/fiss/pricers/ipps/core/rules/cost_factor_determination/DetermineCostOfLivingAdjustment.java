package gov.cms.fiss.pricers.ipps.core.rules.cost_factor_determination;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import org.apache.commons.lang3.StringUtils;

/**
 * Determines the cost-of-living adjustment for the claim.
 *
 * <p>Converted from {@code 2000-ASSEMBLE-PPS-VARIABLES} in the COBOL code (continued).
 *
 * @since 2019
 */
public class DetermineCostOfLivingAdjustment
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();

    //     IF (P-NEW-STATE = 02 OR 12)
    //        MOVE P-NEW-COLA TO H-OPER-COLA
    //     ELSE
    //        MOVE 1.000 TO H-OPER-COLA.
    if (StringUtils.equalsAny(calculationContext.getStateCode(), "02", "12")) {
      calculationContext.setOperatingCostOfLivingAdjustment(
          providerData.getCostOfLivingAdjustment());
    }
  }
}
