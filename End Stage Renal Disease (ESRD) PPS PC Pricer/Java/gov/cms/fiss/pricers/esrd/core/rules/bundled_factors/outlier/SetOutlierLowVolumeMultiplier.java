package gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.outlier;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;

/**
 * Sets the outlier low-volume multiplier.
 *
 * <pre>
 * *****************************************************************
 * **  Set OUTLIER low-volume-multiplier                         ***
 * *****************************************************************
 * </pre>
 *
 * <p>Converted from {@code 2500-CALC-OUTLIER-FACTORS} (continued) in the COBOL code.
 *
 * @since 2020
 */
public class SetOutlierLowVolumeMultiplier
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    //     IF P-PROV-LOW-VOLUME-INDIC = "N"  THEN
    //        MOVE 1                         TO H-OUT-LOW-VOL-MULTIPLIER
    //     ELSE
    //        IF H-PATIENT-AGE < 18  THEN
    //           MOVE 1                      TO H-OUT-LOW-VOL-MULTIPLIER
    //        ELSE
    //           MOVE SB-LOW-VOL-ADJ-LT-4000 TO H-OUT-LOW-VOL-MULTIPLIER
    //           MOVE "Y"                    TO LOW-VOLUME-TRACK
    //        END-IF
    //     END-IF.
    if (!calculationContext.isLowVolume()) {
      calculationContext.setOutlierLowVolumeMultiplier(EsrdPricerContext.DEFAULT_MULTIPLIER);
    } else {
      if (!calculationContext.isAdultPatient()) {
        calculationContext.setOutlierLowVolumeMultiplier(EsrdPricerContext.DEFAULT_MULTIPLIER);
      } else {
        calculationContext.setOutlierLowVolumeMultiplier(
            calculationContext.getSeparatelyBillableLowVolumeAdjustmentLessThan4000Multiplier());

        calculationContext.setLowVolumeClaim(true);
      }
    }
  }
}
