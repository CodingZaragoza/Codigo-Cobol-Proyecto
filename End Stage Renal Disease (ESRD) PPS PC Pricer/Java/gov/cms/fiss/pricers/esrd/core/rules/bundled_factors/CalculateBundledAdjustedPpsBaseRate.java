package gov.cms.fiss.pricers.esrd.core.rules.bundled_factors;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.RoundingMode;

/**
 * Calculates the bundled adjusted PPS base rate.
 *
 * <pre>
 * *****************************************************************
 * **  Calculate BUNDLED Adjusted PPS Base Rate                  ***
 * *****************************************************************
 * </pre>
 *
 * <p>Converted from {@code 2000-CALCULATE-BUNDLED-FACTORS} (continued) in the COBOL code.
 *
 * @since 2020
 */
public class CalculateBundledAdjustedPpsBaseRate
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    //     COMPUTE H-BUN-ADJUSTED-BASE-WAGE-AMT  ROUNDED  =
    //        (H-BUN-BASE-WAGE-AMT * H-BUN-AGE-FACTOR)    *
    //        (H-BUN-BSA-FACTOR    * H-BUN-BMI-FACTOR)    *
    //        (H-BUN-ONSET-FACTOR  * H-BUN-COMORBID-MULTIPLIER) *
    //        H-BUN-LOW-VOL-MULTIPLIER * H-BUN-RURAL-MULTIPLIER.
    calculationContext.setBundledAdjustedBaseWageAmount(
        calculationContext
            .getBundledBaseWageAmount()
            .multiply(calculationContext.getBundledAgeAdjustmentFactor())
            .multiply(
                calculationContext
                    .getBundledBsaFactor()
                    .multiply(calculationContext.getBundledBmiFactor()))
            .multiply(
                calculationContext
                    .getBundledOnsetFactor()
                    .multiply(calculationContext.getBundledComorbidityMultiplier()))
            .multiply(calculationContext.getBundledLowVolumeMultiplier())
            .multiply(calculationContext.getBundledRuralMultiplier())
            .setScale(4, RoundingMode.HALF_UP));
  }
}
