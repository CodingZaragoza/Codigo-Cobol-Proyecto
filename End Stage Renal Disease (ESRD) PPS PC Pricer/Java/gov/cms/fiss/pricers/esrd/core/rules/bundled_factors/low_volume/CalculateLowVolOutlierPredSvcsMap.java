package gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.low_volume;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.RoundingMode;

/**
 * Calculates the low-volume outlier predicted services MAP amount.
 *
 * <p>Converted from {@code 3100-LOW-VOL-OUT-PPS-PAYMENT} in the COBOL code.
 *
 * @since 2020
 */
public class CalculateLowVolOutlierPredSvcsMap
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    //     COMPUTE H-LV-OUT-PREDICT-SERVICES-MAP  ROUNDED =
    //        (H-OUT-AGE-FACTOR             *
    //         H-OUT-BSA-FACTOR             *
    //         H-OUT-BMI-FACTOR             *
    //         H-OUT-ONSET-FACTOR           *
    //         H-OUT-COMORBID-MULTIPLIER    *
    //         H-OUT-RURAL-MULTIPLIER).
    calculationContext.setLowVolumeOutlierPredictedSvcsMap(
        calculationContext
            .getOutlierAgeAdjustmentFactor()
            .multiply(calculationContext.getOutlierBsaFactor())
            .multiply(calculationContext.getOutlierBmiFactor())
            .multiply(calculationContext.getOutlierOnsetFactor())
            .multiply(calculationContext.getOutlierComorbidityMultiplier())
            .multiply(calculationContext.getOutlierRuralMultiplier())
            // NOTE: Added at the request of CMS Policy, as H-OUT-LOW-VOL-MULTIPLIER must be
            // included in this value. The COBOL does not have this addition, which will lead to
            // differences in the outlier results.
            .multiply(calculationContext.getOutlierLowVolumeMultiplier())
            .setScale(4, RoundingMode.HALF_UP));
  }
}
