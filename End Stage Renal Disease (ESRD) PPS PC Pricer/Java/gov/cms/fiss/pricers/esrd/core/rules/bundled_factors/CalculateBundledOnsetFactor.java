package gov.cms.fiss.pricers.esrd.core.rules.bundled_factors;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;

/**
 * Calculates the bundled onset factor.
 *
 * <pre>
 * *****************************************************************
 * **  Calculate BUNDLED ONSET factor                            ***
 * *****************************************************************
 * </pre>
 *
 * <p>Converted from {@code 2000-CALCULATE-BUNDLED-FACTORS} (continued) in the COBOL code.
 *
 * @since 2020
 */
public class CalculateBundledOnsetFactor
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();

    //     IF B-DIALYSIS-START-DATE > ZERO  THEN
    //        MOVE B-LINE-ITEM-DATE-SERVICE  TO THE-DATE
    //        COMPUTE INTEGER-LINE-ITEM-DATE =
    //            FUNCTION INTEGER-OF-DATE(THE-DATE)
    //        MOVE B-DIALYSIS-START-DATE     TO THE-DATE
    //        COMPUTE INTEGER-DIALYSIS-DATE  =
    //            FUNCTION INTEGER-OF-DATE(THE-DATE)
    // * Need to add one to onset-date because the start date should
    // * be included in the count of days.  fix made 9/6/2011
    //        COMPUTE ONSET-DATE = (INTEGER-LINE-ITEM-DATE -
    //                              INTEGER-DIALYSIS-DATE) + 1
    //        IF H-PATIENT-AGE > 17  THEN
    //           IF ONSET-DATE > 120  THEN
    //              MOVE 1                   TO H-BUN-ONSET-FACTOR
    //           ELSE
    //              MOVE CM-ONSET-LE-120     TO H-BUN-ONSET-FACTOR
    //              MOVE "Y"                 TO ONSET-TRACK
    //           END-IF
    //        ELSE
    //           MOVE 1                      TO H-BUN-ONSET-FACTOR
    //        END-IF
    //     ELSE
    //        MOVE 1.000                     TO H-BUN-ONSET-FACTOR
    //     END-IF.
    if (null != claimData.getDialysisStartDate()) {
      final long onsetPeriod =
          ChronoUnit.DAYS.between(claimData.getDialysisStartDate(), claimData.getServiceDate()) + 1;
      if (calculationContext.isAdultPatient()) {
        if (onsetPeriod > 120) {
          calculationContext.setBundledOnsetFactor(EsrdPricerContext.DEFAULT_FACTOR);
        } else {
          calculationContext.setBundledOnsetFactor(
              calculationContext
                  .getCaseMixOnsetLessThanOrEqualTo120Multiplier()
                  .setScale(4, RoundingMode.HALF_UP));

          calculationContext.setOnsetRecent(true);
        }
      } else {
        calculationContext.setBundledOnsetFactor(EsrdPricerContext.DEFAULT_FACTOR);
      }
    } else {
      calculationContext.setBundledOnsetFactor(EsrdPricerContext.DEFAULT_FACTOR);
    }
  }
}
