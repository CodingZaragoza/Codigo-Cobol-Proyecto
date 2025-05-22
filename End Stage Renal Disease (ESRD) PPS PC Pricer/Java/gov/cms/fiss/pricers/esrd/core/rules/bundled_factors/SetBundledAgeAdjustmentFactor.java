package gov.cms.fiss.pricers.esrd.core.rules.bundled_factors;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import org.apache.commons.lang3.StringUtils;

/**
 * Determines the age adjustment factor.
 *
 * <pre>
 * *****************************************************************
 * **  Set BUNDLED age adjustment factor                         ***
 * *****************************************************************
 * </pre>
 *
 * <p>Converted from {@code 2000-CALCULATE-BUNDLED-FACTORS} (continued) in the COBOL code.
 *
 * @since 2020
 */
public class SetBundledAgeAdjustmentFactor
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();

    //     IF H-PATIENT-AGE < 13  THEN
    //        IF B-REV-CODE = '0821' OR '0881' THEN
    //           MOVE EB-AGE-LT-13-HEMO-MODE TO H-BUN-AGE-FACTOR
    //        ELSE
    //           MOVE EB-AGE-LT-13-PD-MODE   TO H-BUN-AGE-FACTOR
    //        END-IF
    //     ELSE
    //        IF H-PATIENT-AGE < 18 THEN
    //           IF B-REV-CODE = '0821' OR '0881' THEN
    //              MOVE EB-AGE-13-17-HEMO-MODE
    //                                       TO H-BUN-AGE-FACTOR
    //           ELSE
    //              MOVE EB-AGE-13-17-PD-MODE
    //                                       TO H-BUN-AGE-FACTOR
    //           END-IF
    //        ELSE
    //           IF H-PATIENT-AGE < 45  THEN
    //              MOVE CM-AGE-18-44        TO H-BUN-AGE-FACTOR
    //           ELSE
    //              IF H-PATIENT-AGE < 60  THEN
    //                 MOVE CM-AGE-45-59     TO H-BUN-AGE-FACTOR
    //              ELSE
    //                 IF H-PATIENT-AGE < 70  THEN
    //                    MOVE CM-AGE-60-69  TO H-BUN-AGE-FACTOR
    //                 ELSE
    //                    IF H-PATIENT-AGE < 80  THEN
    //                       MOVE CM-AGE-70-79
    //                                       TO H-BUN-AGE-FACTOR
    //                    ELSE
    //                       MOVE CM-AGE-80-PLUS
    //                                       TO H-BUN-AGE-FACTOR
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
        calculationContext.setBundledAgeAdjustmentFactor(
            calculationContext.getExpandedBundleUnder13HemodialysisModePaymentMultiplier());
      } else {
        calculationContext.setBundledAgeAdjustmentFactor(
            calculationContext.getExpandedBundleUnder13PeritonealDialysisModePaymentMultiplier());
      }

      return;
    }

    if (calculationContext.getPatientAge() < 18) {
      if (StringUtils.equalsAny(
          claimData.getRevenueCode(),
          EsrdPricerContext.REVENUE_CODE_HEMODIALYSIS_0821,
          EsrdPricerContext.REVENUE_CODE_MISCELLANEOUS_DIALYSIS_0881)) {
        calculationContext.setBundledAgeAdjustmentFactor(
            calculationContext.getExpandedBundle13To17HemodialysisModePaymentMultiplier());
      } else {
        calculationContext.setBundledAgeAdjustmentFactor(
            calculationContext.getExpandedBundle13To17PeritonealDialysisModePaymentMultiplier());
      }

      return;
    }

    if (calculationContext.getPatientAge() < 45) {
      calculationContext.setBundledAgeAdjustmentFactor(
          calculationContext.getCaseMixAge18To44Multiplier());

      return;
    }

    if (calculationContext.getPatientAge() < 60) {
      calculationContext.setBundledAgeAdjustmentFactor(
          calculationContext.getCaseMixAge45To59Multiplier());

      return;
    }

    if (calculationContext.getPatientAge() < 70) {
      calculationContext.setBundledAgeAdjustmentFactor(
          calculationContext.getCaseMixAge60To69Multiplier());

      return;
    }

    if (calculationContext.getPatientAge() < 80) {
      calculationContext.setBundledAgeAdjustmentFactor(
          calculationContext.getCaseMixAge70To79Multiplier());

      return;
    }

    calculationContext.setBundledAgeAdjustmentFactor(
        calculationContext.getCaseMixAge80PlusMultiplier());
  }
}
