package gov.cms.fiss.pricers.esrd.core.rules.rules_2025;

import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimData;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import gov.cms.fiss.pricers.esrd.core.rules.rules_2022_2.bundled_factors.CalculateBundledConditionCodePayment2022Dot2;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.commons.lang3.StringUtils;

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
public class CalculateBundledConditionCodePayment2025
    extends CalculateBundledConditionCodePayment2022Dot2 {
  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();

    // * Self-care in Training add-on
    //  for 2022 Policy said that Pricer should pay the training add-on in these scenarios:
    //  condition codes 73 or 87 AND
    //  revenue codes 0821, 0831, 0841, or 0851
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
    if ((calculationContext.isEsrdTraining73() || calculationContext.isEsrdRetraining87())
        && StringUtils.equalsAny(
            claimData.getRevenueCode(),
            EsrdPricerContext.REVENUE_CODE_HEMODIALYSIS_0821,
            EsrdPricerContext.REVENUE_CODE_PERITONEAL_DIALYSIS_0831,
            EsrdPricerContext.REVENUE_CODE_CONTINUOUS_AMBULATORY_PERITONEAL_DIALYSIS_0841,
            EsrdPricerContext.REVENUE_CODE_CONTINUOUS_CYCLING_PERITONEAL_DIALYSIS_0851)) {
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

  @Override
  protected void calculateInHomePerDiemAmount(EsrdPricerContext calculationContext) {

    // * Dialysis in Home and (CAPD or CCPD) Per-Diem calculation
    //        IF (B-COND-CODE = '74')  AND
    //           (B-REV-CODE = '0841' OR '0851')  THEN
    // * ESCAL201 for ETC HDPA Bonus - changed code to calculate payment
    // * of both With and Without Bonus at the same time
    //              COMPUTE H-PER-DIEM-AMT-WITHOUT-HDPA ROUNDED =
    //                 (H-BUN-ADJUSTED-BASE-WAGE-AMT * 3) / 7
    //              COMPUTE H-PER-DIEM-AMT-WITH-HDPA ROUNDED =
    //                 ((H-BUN-ADJUSTED-BASE-WAGE-AMT * 3) / 7) *
    //                         ETC-HDPA-PCT
    //        ELSE
    //           MOVE ZERO                   TO
    //                                    H-BUN-WAGE-ADJ-TRAINING-AMT
    //                                    H-PER-DIEM-AMT-WITHOUT-HDPA
    //                                    H-PER-DIEM-AMT-WITH-HDPA

    // CHANGED FOR 2025
    //    if StringUtils.equals(
    //           EsrdPricerContext.CONDITION_CODE_HOME_SERVICES_74, claimData.getConditionCode())
    // if calculationContext.IsEsrdHome74()
    // && StringUtils.equalsAny(
    //        claimData.getRevenueCode(),
    //        EsrdPricerContext.REVENUE_CODE_CONTINUOUS_AMBULATORY_PERITONEAL_DIALYSIS_0841,
    //        EsrdPricerContext.REVENUE_CODE_CONTINUOUS_CYCLING_PERITONEAL_DIALYSIS_0851

    if (calculationContext.isPerDiemClaim()) {
      calculationContext.setPerDiemAmountWithoutEtc(
          calculationContext
              .getBundledAdjustedBaseWageAmount()
              .multiply(new BigDecimal(3))
              .divide(new BigDecimal(7), 4, RoundingMode.DOWN));
    } else {
      calculationContext.setBundledWageAdjustedTrainingAmount(BigDecimal.ZERO);
    }
  }
}
