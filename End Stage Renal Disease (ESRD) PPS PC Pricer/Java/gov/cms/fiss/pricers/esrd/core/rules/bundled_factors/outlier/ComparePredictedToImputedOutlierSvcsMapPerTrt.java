package gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.outlier;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Compares imputed vs. predicted outlier services MAP per treatment.
 *
 * <pre>
 * *****************************************************************
 * ** Comparison of predicted to the imputed OUTLIER svc MAP/trt ***
 * *****************************************************************
 * </pre>
 *
 * <p>Converted from {@code 2500-CALC-OUTLIER-FACTORS} (continued) in the COBOL code.
 *
 * @since 2020
 */
public class ComparePredictedToImputedOutlierSvcsMapPerTrt
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    //     IF H-PATIENT-AGE < 18   THEN
    //        COMPUTE H-OUT-PREDICTED-MAP  ROUNDED  =
    //           H-OUT-CM-ADJ-PREDICT-MAP-TRT + FIX-DOLLAR-LOSS-LT-18
    //        MOVE FIX-DOLLAR-LOSS-LT-18     TO H-OUT-FIX-DOLLAR-LOSS
    //        IF H-OUT-IMPUTED-MAP  >  H-OUT-PREDICTED-MAP  THEN
    //           COMPUTE H-OUT-PAYMENT  ROUNDED  =
    //            (H-OUT-IMPUTED-MAP  -  H-OUT-PREDICTED-MAP)  *
    //                                         LOSS-SHARING-PCT-LT-18
    //           MOVE LOSS-SHARING-PCT-LT-18 TO H-OUT-LOSS-SHARING-PCT
    //           MOVE "Y"                    TO OUTLIER-TRACK
    //        ELSE
    //           MOVE ZERO                   TO H-OUT-PAYMENT
    //           MOVE ZERO                   TO H-OUT-LOSS-SHARING-PCT
    //        END-IF
    //     ELSE
    //        COMPUTE H-OUT-PREDICTED-MAP  ROUNDED =
    //           H-OUT-CM-ADJ-PREDICT-MAP-TRT + FIX-DOLLAR-LOSS-GT-17
    //           MOVE FIX-DOLLAR-LOSS-GT-17  TO H-OUT-FIX-DOLLAR-LOSS
    //        IF H-OUT-IMPUTED-MAP  >  H-OUT-PREDICTED-MAP  THEN
    //           COMPUTE H-OUT-PAYMENT  ROUNDED  =
    //            (H-OUT-IMPUTED-MAP  -  H-OUT-PREDICTED-MAP)  *
    //                                         LOSS-SHARING-PCT-GT-17
    //           MOVE LOSS-SHARING-PCT-GT-17 TO H-OUT-LOSS-SHARING-PCT
    //           MOVE "Y"                    TO OUTLIER-TRACK
    //        ELSE
    //           MOVE ZERO                   TO H-OUT-PAYMENT
    //        END-IF
    //     END-IF.
    // 2022 test case spreadsheet line 369
    if (!calculationContext.isAdultPatient()) {
      calculationContext.setOutlierPredictedMapAmount(
          calculationContext
              .getOutlierCaseMixAdjPredictedMapTrt()
              .add(calculationContext.getFixedDollarLossUnder18())
              .setScale(4, RoundingMode.HALF_UP));

      calculationContext.setOutlierFixedDollarLoss(calculationContext.getFixedDollarLossUnder18());

      // 2022 test case spreadsheet line 371
      if (BigDecimalUtils.isGreaterThan(
          calculationContext.getOutlierImputedMapAmount(),
          calculationContext.getOutlierPredictedMapAmount())) {
        calculationContext.setOutlierPayment(
            calculationContext
                .getOutlierImputedMapAmount()
                .subtract(calculationContext.getOutlierPredictedMapAmount())
                .multiply(calculationContext.getLossSharingPercentageUnder18())
                .setScale(4, RoundingMode.HALF_UP));

        calculationContext.setOutlierLossSharingPercentage(
            calculationContext.getLossSharingPercentageUnder18());

        calculationContext.setOutlierClaim(true);
      } else {
        calculationContext.setOutlierPayment(BigDecimal.ZERO);

        calculationContext.setOutlierLossSharingPercentage(BigDecimal.ZERO);
      }
    } else {
      calculationContext.setOutlierPredictedMapAmount(
          calculationContext
              .getOutlierCaseMixAdjPredictedMapTrt()
              .add(calculationContext.getFixedDollarLossOver17())
              .setScale(4, RoundingMode.HALF_UP));

      calculationContext.setOutlierFixedDollarLoss(calculationContext.getFixedDollarLossOver17());

      if (BigDecimalUtils.isGreaterThan(
          calculationContext.getOutlierImputedMapAmount(),
          calculationContext.getOutlierPredictedMapAmount())) {
        calculationContext.setOutlierPayment(
            calculationContext
                .getOutlierImputedMapAmount()
                .subtract(calculationContext.getOutlierPredictedMapAmount())
                .multiply(calculationContext.getLossSharingPercentageOver17())
                .setScale(4, RoundingMode.HALF_UP));

        calculationContext.setOutlierLossSharingPercentage(
            calculationContext.getLossSharingPercentageOver17());

        calculationContext.setOutlierClaim(true);
      } else {
        calculationContext.setOutlierPayment(BigDecimal.ZERO);
      }
    }
  }
}
