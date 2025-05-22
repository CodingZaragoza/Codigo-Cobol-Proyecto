package gov.cms.fiss.pricers.ipf.core.rules.assemble_pps_variables;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;
import java.math.BigDecimal;

/**
 * Initialize cost of living adjustment value.
 *
 * <p>Converted from {@code 2000-ASSEMBLE-PPS-VARIABLES} in the COBOL code.
 */
public class SetCostOfLivingAdjustment
    implements CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {

  @Override
  public void calculate(IpfPricerContext calculationContext) {
    final BigDecimal costOfLivingAdjustment;

    // IF P-NEW-STATE = 02 OR 12
    //    MOVE P-NEW-COLA TO IPF-COLA
    // ELSE
    //    MOVE 1.000 TO IPF-COLA. (set this as the default instead of an else case)
    if (calculationContext.hasCostOfLivingAdjustment()) {
      costOfLivingAdjustment = calculationContext.getProviderData().getCostOfLivingAdjustment();
    } else {
      costOfLivingAdjustment = IpfPricerContext.DEFAULT_COST_OF_LIVING_ADJUSTMENT;
    }
    calculationContext.getPaymentData().setCostOfLivingAdjustmentPercent(costOfLivingAdjustment);
  }
}
