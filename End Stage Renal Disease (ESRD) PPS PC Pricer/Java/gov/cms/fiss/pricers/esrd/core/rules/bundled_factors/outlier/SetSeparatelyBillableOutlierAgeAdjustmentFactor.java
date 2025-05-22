package gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.outlier;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import org.apache.commons.lang3.StringUtils;

/**
 * Sets the separately-billable outlier age adjustment factor.
 *
 * <pre>
 * *****************************************************************
 * **  Set separately billable OUTLIER age adjustment factor     ***
 * *****************************************************************
 * </pre>
 *
 * <p>Converted from {@code 2500-CALC-OUTLIER-FACTORS} (continued) in the COBOL code.
 *
 * @since 2020
 */
public class SetSeparatelyBillableOutlierAgeAdjustmentFactor
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();

    //     IF H-PATIENT-AGE < 13  THEN
    //        IF B-REV-CODE = '0821' OR '0881' THEN
    //           MOVE SB-AGE-LT-13-HEMO-MODE TO H-OUT-AGE-FACTOR
    //        ELSE
    //           MOVE SB-AGE-LT-13-PD-MODE   TO H-OUT-AGE-FACTOR
    //        END-IF
    //     ELSE
    //        IF H-PATIENT-AGE < 18 THEN
    //           IF B-REV-CODE = '0821' OR '0881'  THEN
    //              MOVE SB-AGE-13-17-HEMO-MODE
    //                                       TO H-OUT-AGE-FACTOR
    //           ELSE
    //              MOVE SB-AGE-13-17-PD-MODE
    //                                       TO H-OUT-AGE-FACTOR
    //           END-IF
    //        ELSE
    //           IF H-PATIENT-AGE < 45  THEN
    //              MOVE SB-AGE-18-44        TO H-OUT-AGE-FACTOR
    //           ELSE
    //              IF H-PATIENT-AGE < 60  THEN
    //                 MOVE SB-AGE-45-59     TO H-OUT-AGE-FACTOR
    //              ELSE
    //                 IF H-PATIENT-AGE < 70  THEN
    //                    MOVE SB-AGE-60-69  TO H-OUT-AGE-FACTOR
    //                 ELSE
    //                    IF H-PATIENT-AGE < 80  THEN
    //                       MOVE SB-AGE-70-79
    //                                       TO H-OUT-AGE-FACTOR
    //                    ELSE
    //                       MOVE SB-AGE-80-PLUS
    //                                       TO H-OUT-AGE-FACTOR
    //                    END-IF
    //                 END-IF
    //              END-IF
    //           END-IF
    //        END-IF
    //     END-IF.
    if (calculationContext.getPatientAge() < 13) {
      if (StringUtils.equalsAny(
          claimData.getRevenueCode(),
          EsrdPricerContext.REVENUE_CODE_HEMODIALYSIS_0821,
          EsrdPricerContext.REVENUE_CODE_MISCELLANEOUS_DIALYSIS_0881)) {
        calculationContext.setOutlierAgeAdjustmentFactor(
            calculationContext.getSeparatelyBillableUnder13HemodialysisModePaymentMultiplier());
      } else {
        calculationContext.setOutlierAgeAdjustmentFactor(
            calculationContext
                .getSeparatelyBillableUnder13PeritonealDialysisModePaymentMultiplier());
      }

      return;
    }

    if (calculationContext.getPatientAge() < 18) {
      if (StringUtils.equalsAny(
          claimData.getRevenueCode(),
          EsrdPricerContext.REVENUE_CODE_HEMODIALYSIS_0821,
          EsrdPricerContext.REVENUE_CODE_MISCELLANEOUS_DIALYSIS_0881)) {
        calculationContext.setOutlierAgeAdjustmentFactor(
            calculationContext.getSeparatelyBillable13To17HemodialysisModePaymentMultiplier());
      } else {
        calculationContext.setOutlierAgeAdjustmentFactor(
            calculationContext
                .getSeparatelyBillable13To17PeritonealDialysisModePaymentMultiplier());
      }

      return;
    }

    if (calculationContext.getPatientAge() < 45) {
      calculationContext.setOutlierAgeAdjustmentFactor(
          calculationContext.getSeparatelyBillableAge18To44Multiplier());

      return;
    }

    if (calculationContext.getPatientAge() < 60) {
      calculationContext.setOutlierAgeAdjustmentFactor(
          calculationContext.getSeparatelyBillableAge45To59Multiplier());

      return;
    }

    if (calculationContext.getPatientAge() < 70) {
      calculationContext.setOutlierAgeAdjustmentFactor(
          calculationContext.getSeparatelyBillableAge60To69Multiplier());

      return;
    }

    if (calculationContext.getPatientAge() < 80) {
      calculationContext.setOutlierAgeAdjustmentFactor(
          calculationContext.getSeparatelyBillableAge70To79Multiplier());

      return;
    }

    calculationContext.setOutlierAgeAdjustmentFactor(
        calculationContext.getSeparatelyBillableAge80PlusMultiplier());
  }
}
