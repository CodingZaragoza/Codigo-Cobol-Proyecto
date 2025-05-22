package gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.low_volume;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.RoundingMode;

/**
 * Calculates the low-volume bundled adjusted base wage amount.
 *
 * <p>Converted from {@code 3000-LOW-VOL-FULL-PPS-PAYMENT} in the COBOL code.
 *
 * @since 2020
 */
public class CalculateBundledAdjustedBaseWageAmount
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    //     COMPUTE H-LV-BUN-ADJUST-BASE-WAGE-AMT  ROUNDED  =
    //        (H-BUN-BASE-WAGE-AMT * H-BUN-AGE-FACTOR)     *
    //        (H-BUN-BSA-FACTOR    * H-BUN-BMI-FACTOR)     *
    //        (H-BUN-ONSET-FACTOR  * H-BUN-COMORBID-MULTIPLIER) *
    //         H-BUN-RURAL-MULTIPLIER.
    calculationContext.setLowVolumeBundledAdjustedBaseWageAmount(
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
            .multiply(calculationContext.getBundledRuralMultiplier())
            .setScale(4, RoundingMode.HALF_UP));
  }
}
