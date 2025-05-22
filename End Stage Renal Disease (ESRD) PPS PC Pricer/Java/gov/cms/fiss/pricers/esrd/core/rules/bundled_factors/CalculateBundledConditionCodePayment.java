package gov.cms.fiss.pricers.esrd.core.rules.bundled_factors;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Calculates the bundled condition code payment.
 *
 * <pre>
 * *****************************************************************
 * **  Calculate BUNDLED Condition Code payment                  ***
 * *****************************************************************
 * </pre>
 *
 * <p>Converted from {@code 2000-CALCULATE-BUNDLED-FACTORS} (continued) in the COBOL code.
 *
 * @since 2020
 */
public class CalculateBundledConditionCodePayment
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {

    // * Self-care in Training add-on
    //     IF B-COND-CODE = '73' OR '87' THEN
    // * no add-on when onset is present
    //        IF H-BUN-ONSET-FACTOR  =  CM-ONSET-LE-120  THEN
    //           MOVE ZERO TO H-BUN-WAGE-ADJ-TRAINING-AMT
    //        ELSE
    // * use new PPS training add-on amount times wage-index
    //           COMPUTE H-BUN-WAGE-ADJ-TRAINING-AMT ROUNDED  =
    //             TRAINING-ADD-ON-PMT-AMT * BUN-CBSA-W-INDEX
    //           MOVE "Y" TO TRAINING-TRACK
    //        END-IF
    //     ELSE
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
      calculateInHomePerDiemAmount(calculationContext);
    }
  }

  protected void calculateInHomePerDiemAmount(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();

    // * Dialysis in Home and (CAPD or CCPD) Per-Diem calculation
    //        IF (B-COND-CODE = '74')  AND
    //           (B-REV-CODE = '0841' OR '0851')  THEN
    //            COMPUTE H-CC-74-PER-DIEM-AMT  ROUNDED =
    //                 ((H-BUN-ADJUSTED-BASE-WAGE-AMT * 3) / 7)
    //        ELSE
    //           MOVE ZERO                   TO
    //                                    H-BUN-WAGE-ADJ-TRAINING-AMT
    //                                    H-CC-74-PER-DIEM-AMT
    //        END-IF

    if (calculationContext.isPerDiemClaim()) {

      calculationContext.setConditionCode74PerDiemAmount(
          calculationContext
              .getBundledAdjustedBaseWageAmount()
              .multiply(new BigDecimal(3))
              .divide(new BigDecimal(7), 4, RoundingMode.DOWN));
    } else {
      calculationContext.setBundledWageAdjustedTrainingAmount(BigDecimal.ZERO);

      calculationContext.setConditionCode74PerDiemAmount(BigDecimal.ZERO);
    }
  }
}
