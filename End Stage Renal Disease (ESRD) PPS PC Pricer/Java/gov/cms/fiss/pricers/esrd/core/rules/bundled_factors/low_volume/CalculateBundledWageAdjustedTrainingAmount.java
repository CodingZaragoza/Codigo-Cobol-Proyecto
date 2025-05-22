package gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.low_volume;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Calculates the bundled wage-adjusted training amount.
 *
 * <p>Converted from {@code 3000-LOW-VOL-FULL-PPS-PAYMENT} in the COBOL code.
 *
 * @since 2020
 */
public class CalculateBundledWageAdjustedTrainingAmount
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {

    // * Self-care in Training add-on
    //     IF B-COND-CODE = '73' OR '87' THEN
    // * no add-on when onset is present
    //        IF H-BUN-ONSET-FACTOR  =  CM-ONSET-LE-120  THEN
    //           MOVE ZERO                   TO
    //                                    H-BUN-WAGE-ADJ-TRAINING-AMT
    //        ELSE
    // * use new PPS training add-on amount times wage-index
    //           COMPUTE H-BUN-WAGE-ADJ-TRAINING-AMT  ROUNDED  =
    //             TRAINING-ADD-ON-PMT-AMT * BUN-CBSA-W-INDEX
    //           MOVE "Y"                    TO TRAINING-TRACK
    //        END-IF
    //     ELSE
    // * Dialysis in Home and (CAPD or CCPD) Per-Diem calculation
    //        IF (B-COND-CODE = '74')  AND
    //           (B-REV-CODE = '0841' OR '0851')  THEN
    //              COMPUTE H-CC-74-PER-DIEM-AMT  ROUNDED =
    //                 (H-LV-BUN-ADJUST-BASE-WAGE-AMT * 3) / 7
    //        ELSE
    //           MOVE ZERO                   TO
    //                                    H-BUN-WAGE-ADJ-TRAINING-AMT
    //                                    H-CC-74-PER-DIEM-AMT
    //        END-IF
    //     END-IF.
    if (calculationContext.isEsrdTraining73() || calculationContext.isEsrdRetraining87()) {
      if (BigDecimalUtils.equals(
          calculationContext.getBundledOnsetFactor(),
          calculationContext.getCaseMixOnsetLessThanOrEqualTo120Multiplier())) {
        calculationContext.setBundledWageAdjustedTrainingAmount(BigDecimal.ZERO);
      } else {
        calculationContext.setBundledWageAdjustedTrainingAmount(
            calculationContext
                .getTrainingAddOnPaymentAmount()
                .multiply(calculationContext.getBundledWageIndex())
                .setScale(4, RoundingMode.HALF_UP));

        calculationContext.setTrainingClaim(true);
      }
    } else {
      if (calculationContext.isPerDiemClaim()) {
        calculationContext.setConditionCode74PerDiemAmount(
            calculationContext
                .getLowVolumeBundledAdjustedBaseWageAmount()
                .multiply(new BigDecimal(3))
                .divide(new BigDecimal(7), 4, RoundingMode.DOWN));
      } else {
        calculationContext.setBundledWageAdjustedTrainingAmount(BigDecimal.ZERO);
        calculationContext.setConditionCode74PerDiemAmount(BigDecimal.ZERO);
      }
    }
  }
}
