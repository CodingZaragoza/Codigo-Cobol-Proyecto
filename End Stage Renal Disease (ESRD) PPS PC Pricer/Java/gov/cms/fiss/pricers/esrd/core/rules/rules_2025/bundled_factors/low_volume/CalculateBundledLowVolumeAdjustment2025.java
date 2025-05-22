package gov.cms.fiss.pricers.esrd.core.rules.rules_2025.bundled_factors.low_volume;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;

/**
 * Calculates the bundled low-volume adjustment.
 *
 * <pre>
 * *****************************************************************
 * **  Calculate BUNDLED Low Volume adjustment                   ***
 * *****************************************************************
 * </pre>
 *
 * <p>Converted from {@code 2000-CALCULATE-BUNDLED-FACTORS} (continued) in the COBOL code.
 *
 * @since 2020
 */
public class CalculateBundledLowVolumeAdjustment2025
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    //     IF P-PROV-LOW-VOLUME-INDIC = 'Y'  THEN
    //        IF H-PATIENT-AGE > 17  THEN
    //           MOVE CM-LOW-VOL-ADJ-LT-4000 TO
    //                                       H-BUN-LOW-VOL-MULTIPLIER
    //           MOVE "Y"                    TO  LOW-VOLUME-TRACK
    //        ELSE
    //           MOVE 1.000                  TO
    //                                       H-BUN-LOW-VOL-MULTIPLIER
    //        END-IF
    //     ELSE
    //        MOVE 1.000                     TO
    //                                       H-BUN-LOW-VOL-MULTIPLIER
    //     END-IF.
    if (calculationContext.isLowVolume()) {
      if (calculationContext.isAdultPatient()) {
        if (calculationContext.isLowVolumeClaimTierOne()) {
          calculationContext.setBundledLowVolumeMultiplier(
              calculationContext.getCaseMixLowVolumeAdjustmentLessThan3000Multiplier());
          calculationContext.setLowVolumeClaim(true);
        }
        if (calculationContext.isLowVolumeClaimTierTwo()) {
          calculationContext.setBundledLowVolumeMultiplier(
              calculationContext.getCaseMixLowVolumeAdjustment3001To3999Multiplier());
          calculationContext.setLowVolumeClaim(true);
        }
      } else calculationContext.setBundledLowVolumeMultiplier(EsrdPricerContext.DEFAULT_MULTIPLIER);
    } else {
      calculationContext.setBundledLowVolumeMultiplier(EsrdPricerContext.DEFAULT_MULTIPLIER);
    }
  }
}
