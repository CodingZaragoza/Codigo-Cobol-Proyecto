package gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.outlier;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;

/**
 * Calculates the separately-billable outlier onset factor.
 *
 * <pre>
 * *****************************************************************
 * **  Calculate separately billable OUTLIER ONSET factor        ***
 * *****************************************************************
 * </pre>
 *
 * <p>Converted from {@code 2500-CALC-OUTLIER-FACTORS} (continued) in the COBOL code.
 *
 * @since 2020
 */
public class CalculateSeparatelyBillableOutlierOnsetFactor
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();

    //     IF B-DIALYSIS-START-DATE > ZERO  THEN
    //        IF H-PATIENT-AGE > 17  THEN
    //           IF ONSET-DATE > 120  THEN
    //              MOVE 1                   TO H-OUT-ONSET-FACTOR
    //           ELSE
    //              MOVE SB-ONSET-LE-120     TO H-OUT-ONSET-FACTOR
    //           END-IF
    //        ELSE
    //           MOVE 1                      TO H-OUT-ONSET-FACTOR
    //        END-IF
    //     ELSE
    //        MOVE 1.000                     TO H-OUT-ONSET-FACTOR
    //     END-IF.
    if (null != claimData.getDialysisStartDate()) {
      if (calculationContext.isAdultPatient()) {
        final long onsetPeriod =
            ChronoUnit.DAYS.between(claimData.getDialysisStartDate(), claimData.getServiceDate())
                + 1;
        if (onsetPeriod > 120) {
          calculationContext.setOutlierOnsetFactor(EsrdPricerContext.DEFAULT_FACTOR);
        } else {
          calculationContext.setOutlierOnsetFactor(
              calculationContext
                  .getSeparatelyBillableOnsetLessThanOrEqualTo120Multiplier()
                  .setScale(4, RoundingMode.HALF_UP));
        }
      } else {
        calculationContext.setOutlierOnsetFactor(EsrdPricerContext.DEFAULT_FACTOR);
      }
    } else {
      calculationContext.setOutlierOnsetFactor(EsrdPricerContext.DEFAULT_FACTOR);
    }
  }
}
