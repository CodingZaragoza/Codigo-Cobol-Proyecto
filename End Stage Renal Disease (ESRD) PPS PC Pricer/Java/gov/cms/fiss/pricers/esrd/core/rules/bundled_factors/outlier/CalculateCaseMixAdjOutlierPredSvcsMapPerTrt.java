package gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.outlier;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.RoundingMode;

/**
 * Calculate the case mix-adjusted predicted outlier MAP per treatment.
 *
 * <pre>
 * *****************************************************************
 * **  Calculate case mix adjusted predicted OUTLIER serv MAP/trt***
 * *****************************************************************
 * </pre>
 *
 * <p>Converted from {@code 2500-CALC-OUTLIER-FACTORS} (continued) in the COBOL code.
 *
 * @since 2020
 */
public class CalculateCaseMixAdjOutlierPredSvcsMapPerTrt
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    //     IF H-PATIENT-AGE < 18  THEN
    //        COMPUTE H-OUT-CM-ADJ-PREDICT-MAP-TRT  ROUNDED  =
    //           (H-OUT-PREDICTED-SERVICES-MAP * ADJ-AVG-MAP-AMT-LT-18)
    //        MOVE ADJ-AVG-MAP-AMT-LT-18     TO  H-OUT-ADJ-AVG-MAP-AMT
    //     ELSE
    //        COMPUTE H-OUT-CM-ADJ-PREDICT-MAP-TRT  ROUNDED  =
    //           (H-OUT-PREDICTED-SERVICES-MAP * ADJ-AVG-MAP-AMT-GT-17)
    //        MOVE ADJ-AVG-MAP-AMT-GT-17     TO  H-OUT-ADJ-AVG-MAP-AMT
    //     END-IF.
    if (!calculationContext.isAdultPatient()) {
      calculationContext.setOutlierCaseMixAdjPredictedMapTrt(
          calculationContext
              .getOutlierPredictedSvcsMapAmount()
              .multiply(calculationContext.getAdjustedAverageMapAmountUnder18())
              .setScale(4, RoundingMode.HALF_UP));

      calculationContext.setOutlierAdjustedAverageMapAmount(
          calculationContext.getAdjustedAverageMapAmountUnder18());
    } else {
      calculationContext.setOutlierCaseMixAdjPredictedMapTrt(
          calculationContext
              .getOutlierPredictedSvcsMapAmount()
              .multiply(calculationContext.getAdjustedAverageMapAmountOver17())
              .setScale(4, RoundingMode.HALF_UP));

      calculationContext.setOutlierAdjustedAverageMapAmount(
          calculationContext.getAdjustedAverageMapAmountOver17());
    }
  }
}
