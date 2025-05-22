package gov.cms.fiss.pricers.esrd.core.rules.rules_2025.bundled_factors.low_volume;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Compares the low-volume predicted to the imputed outlier services MAP per treatment.
 *
 * <p>Converted from {@code 3100-LOW-VOL-OUT-PPS-PAYMENT} in the COBOL code.
 *
 * @since 2020
 */
public class CompareLowVolPredictedToImputedOutlierSvcsMapPerTrt2025
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    // ************************************************************************
    // The following block will never be executed, as pediatric claims can
    // never be low-volume
    // ************************************************************************
    //    IF H-PATIENT-AGE < 18   THEN
    //       COMPUTE H-LV-OUT-PREDICTED-MAP  ROUNDED  =
    //          H-LV-OUT-CM-ADJ-PREDICT-M-TRT + FIX-DOLLAR-LOSS-LT-18
    //       MOVE FIX-DOLLAR-LOSS-LT-18     TO H-OUT-FIX-DOLLAR-LOSS
    //       IF H-OUT-IMPUTED-MAP  >  H-LV-OUT-PREDICTED-MAP  THEN
    //          COMPUTE H-LV-OUT-PAYMENT  ROUNDED  =
    //           (H-OUT-IMPUTED-MAP  -  H-LV-OUT-PREDICTED-MAP)  *
    //                                        LOSS-SHARING-PCT-LT-18
    //          MOVE LOSS-SHARING-PCT-LT-18 TO H-OUT-LOSS-SHARING-PCT
    //       ELSE
    //          MOVE ZERO                   TO H-LV-OUT-PAYMENT
    //          MOVE ZERO                   TO H-OUT-LOSS-SHARING-PCT
    //       END-IF
    //
    // ************************************************************************
    // This block is converted below
    // ************************************************************************
    //    ELSE
    //       COMPUTE H-LV-OUT-PREDICTED-MAP  ROUNDED =
    //          H-LV-OUT-CM-ADJ-PREDICT-M-TRT + FIX-DOLLAR-LOSS-GT-17
    //          MOVE FIX-DOLLAR-LOSS-GT-17  TO H-OUT-FIX-DOLLAR-LOSS
    //       IF H-OUT-IMPUTED-MAP  >  H-LV-OUT-PREDICTED-MAP  THEN
    //          COMPUTE H-LV-OUT-PAYMENT  ROUNDED  =
    //           (H-OUT-IMPUTED-MAP  -  H-LV-OUT-PREDICTED-MAP)  *
    //                                        LOSS-SHARING-PCT-GT-17
    //          MOVE LOSS-SHARING-PCT-GT-17 TO H-OUT-LOSS-SHARING-PCT
    //       ELSE
    //          MOVE ZERO                   TO H-LV-OUT-PAYMENT
    //       END-IF
    //    END-IF.
    calculationContext.setLowVolumeOutlierPredictedMap(
        calculationContext
            .getLowVolumeOutlierCaseMixAdjustedPredictedSvcsMapPerTrt()
            .add(calculationContext.getFixedDollarLossOver17())
            .setScale(4, RoundingMode.HALF_UP));

    calculationContext.setOutlierFixedDollarLoss(calculationContext.getFixedDollarLossOver17());

    if (BigDecimalUtils.isGreaterThan(
        calculationContext.getOutlierImputedMapAmount(),
        calculationContext.getLowVolumeOutlierPredictedMap())) {
      calculationContext.setLowVolumeOutlierPayment(
          calculationContext
              .getOutlierImputedMapAmount()
              .subtract(calculationContext.getLowVolumeOutlierPredictedMap())
              .multiply(calculationContext.getLossSharingPercentageOver17())
              .setScale(4, RoundingMode.HALF_UP));

      calculationContext.setOutlierLossSharingPercentage(
          calculationContext.getLossSharingPercentageOver17());
    } else {
      calculationContext.setLowVolumeOutlierPayment(BigDecimal.ZERO);
    }

    //    MOVE H-LV-OUT-PAYMENT             TO OUT-NON-PER-DIEM-PAYMENT
    calculationContext
        .getPaymentData()
        .setOutlierNonPerDiemPaymentAmount(calculationContext.getLowVolumeOutlierPayment());

    // Dialysis in Home and (CAPD or CCPD) Per-Diem calculation
    //    IF (B-COND-CODE = '74')  AND
    //       (B-REV-CODE = '0841' OR '0851')  THEN
    //          COMPUTE H-LV-OUT-PAYMENT ROUNDED = H-LV-OUT-PAYMENT *
    //            (((B-CLAIM-NUM-DIALYSIS-SESSIONS) * 3) / 7)
    //    END-IF.
    // CHANGED FOR 2025
    // REPLACED
    //   if StringUtils.equals(
    //           EsrdPricerContext.CONDITION_CODE_HOME_SERVICES_74, claimData.getConditionCode()

    if (calculationContext.isPerDiemClaim()) {
      calculationContext.setLowVolumeOutlierPayment(
          calculationContext
              .getLowVolumeOutlierPayment()
              .multiply(
                  new BigDecimal(calculationContext.getClaimData().getDialysisSessionCount())
                      .multiply(new BigDecimal(3)))
              .divide(new BigDecimal(7), 4, RoundingMode.DOWN));
    }
  }
}
