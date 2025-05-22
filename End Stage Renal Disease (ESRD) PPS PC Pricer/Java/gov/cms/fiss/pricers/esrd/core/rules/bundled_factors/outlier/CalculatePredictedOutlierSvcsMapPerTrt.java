package gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.outlier;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.RoundingMode;

/**
 * Calculate the predicted outlier MAP per treatment.
 *
 * <pre>
 * *****************************************************************
 * **  Calculate predicted OUTLIER services MAP per treatment    ***
 * *****************************************************************
 * </pre>
 *
 * <p>Converted from {@code 2500-CALC-OUTLIER-FACTORS} (continued) in the COBOL code.
 *
 * @since 2020
 */
public class CalculatePredictedOutlierSvcsMapPerTrt
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    //     COMPUTE H-OUT-PREDICTED-SERVICES-MAP  ROUNDED =
    //        (H-OUT-AGE-FACTOR             *
    //         H-OUT-BSA-FACTOR             *
    //         H-OUT-BMI-FACTOR             *
    //         H-OUT-ONSET-FACTOR           *
    //         H-OUT-COMORBID-MULTIPLIER    *
    //         H-OUT-RURAL-MULTIPLIER       *
    //         H-OUT-LOW-VOL-MULTIPLIER).
    calculationContext.setOutlierPredictedSvcsMapAmount(
        calculationContext
            .getOutlierAgeAdjustmentFactor()
            .multiply(calculationContext.getOutlierBsaFactor())
            .multiply(calculationContext.getOutlierBmiFactor())
            .multiply(calculationContext.getOutlierOnsetFactor())
            .multiply(calculationContext.getOutlierComorbidityMultiplier())
            .multiply(calculationContext.getOutlierRuralMultiplier())
            .multiply(calculationContext.getOutlierLowVolumeMultiplier())
            .setScale(4, RoundingMode.HALF_UP));
  }
}
