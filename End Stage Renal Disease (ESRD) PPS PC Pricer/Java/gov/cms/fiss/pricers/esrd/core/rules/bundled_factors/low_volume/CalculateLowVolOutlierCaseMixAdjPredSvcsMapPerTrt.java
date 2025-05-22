package gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.low_volume;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.RoundingMode;

/**
 * Calculates the low-volume outlier case-mix adjusted predicted services MAP / treatment amount.
 *
 * <p>Converted from {@code 3100-LOW-VOL-OUT-PPS-PAYMENT} in the COBOL code.
 *
 * @since 2020
 */
public class CalculateLowVolOutlierCaseMixAdjPredSvcsMapPerTrt
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    // ************************************************************************
    // The following block will never be executed, as pediatric claims can
    // never be low-volume
    // ************************************************************************
    //     IF H-PATIENT-AGE < 18  THEN
    //        COMPUTE H-LV-OUT-CM-ADJ-PREDICT-M-TRT  ROUNDED  =
    //           (H-LV-OUT-PREDICT-SERVICES-MAP * ADJ-AVG-MAP-AMT-LT-18)
    //        MOVE ADJ-AVG-MAP-AMT-LT-18     TO  H-OUT-ADJ-AVG-MAP-AMT
    //     ELSE
    // ************************************************************************
    // This block is converted below
    // ************************************************************************
    //        COMPUTE H-LV-OUT-CM-ADJ-PREDICT-M-TRT  ROUNDED  =
    //           (H-LV-OUT-PREDICT-SERVICES-MAP * ADJ-AVG-MAP-AMT-GT-17)
    //        MOVE ADJ-AVG-MAP-AMT-GT-17     TO  H-OUT-ADJ-AVG-MAP-AMT
    //     END-IF.
    calculationContext.setLowVolumeOutlierCaseMixAdjustedPredictedSvcsMapPerTrt(
        calculationContext
            .getLowVolumeOutlierPredictedSvcsMap()
            .multiply(calculationContext.getAdjustedAverageMapAmountOver17())
            .setScale(4, RoundingMode.HALF_UP));

    calculationContext.setOutlierAdjustedAverageMapAmount(
        calculationContext.getAdjustedAverageMapAmountOver17());
  }
}
