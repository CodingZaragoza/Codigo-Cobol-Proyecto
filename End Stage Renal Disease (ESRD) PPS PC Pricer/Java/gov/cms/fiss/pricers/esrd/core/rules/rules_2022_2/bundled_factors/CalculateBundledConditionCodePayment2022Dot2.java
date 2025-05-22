package gov.cms.fiss.pricers.esrd.core.rules.rules_2022_2.bundled_factors;

import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimData;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import gov.cms.fiss.pricers.esrd.core.rules.rules_2022.bundled_factors.CalculateBundledConditionCodePayment2022;
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
public class CalculateBundledConditionCodePayment2022Dot2
    extends CalculateBundledConditionCodePayment2022 {

  @Override
  protected void calculateInHomePerDiemAmount(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();

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
    //        END-IF
    //     END-IF.
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
