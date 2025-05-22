package gov.cms.fiss.pricers.ipps.core.rules.rules2021.calculate_payment;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import java.math.RoundingMode;

/**
 * Determines the capital federal-specific portion amount; includes the no-cost product adjustment
 * factor.
 *
 * <p>Converted from {@code 3000-CALC-PAYMENT} in the COBOL code (continued).
 *
 * @since 2019
 */
public class CalculateCapitalFederalSpecificPortionAmount2021
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    // ***********************************************************
    // * CAPITAL FSP CALCULATION                                 *
    // ***********************************************************
    //     COMPUTE H-CAPI-FSP-PART ROUNDED =
    //                               H-DRG-WT       *
    //                               H-CAPI-FED-RATE *
    //                               H-CAPI-COLA *
    //                               H-CAPI-LARG-URBAN *
    //                               HLD-MID-ADJ-FACT *
    //                               NO-COST-PRODUCT.

    calculationContext.setCapitalFederalSpecificPortionPart(
        calculationContext
            .getDrgsTableEntry()
            .getWeight()
            .multiply(calculationContext.getCapitalFederalRate())
            .multiply(calculationContext.getCapitalCostOfLivingAdjustment())
            .multiply(calculationContext.getCapitalLargeUrbanFactor())
            .multiply(calculationContext.getMidnightAdjustmentFactor())
            .multiply(calculationContext.getNoCostProductAdjustmentFactor())
            .setScale(9, RoundingMode.HALF_UP));
  }
}
