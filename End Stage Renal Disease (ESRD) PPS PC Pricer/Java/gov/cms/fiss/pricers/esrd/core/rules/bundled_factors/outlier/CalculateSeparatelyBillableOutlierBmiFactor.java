package gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.outlier;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Calculates the separately-billable outlier BSA factor.
 *
 * <pre>
 * *****************************************************************
 * **  Calculate separately billable OUTLIER BMI factor          ***
 * *****************************************************************
 * </pre>
 *
 * <p>Converted from {@code 2500-CALC-OUTLIER-FACTORS} (continued) in the COBOL code.
 *
 * @since 2020
 */
public class CalculateSeparatelyBillableOutlierBmiFactor
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    //     COMPUTE H-OUT-BMI  ROUNDED = (B-PATIENT-WGT /
    //         (B-PATIENT-HGT ** 2)) * 10000.
    calculationContext.setOutlierBmi(
        calculationContext.calculateBodyMassIndex().setScale(4, RoundingMode.HALF_UP));

    //     IF (H-PATIENT-AGE > 17) AND (H-OUT-BMI < 18.5)  THEN
    //        MOVE SB-BMI-LT-18-5            TO H-OUT-BMI-FACTOR
    //     ELSE
    //        MOVE 1.000                     TO H-OUT-BMI-FACTOR
    //     END-IF.
    if (calculationContext.isAdultPatient()
        && BigDecimalUtils.isLessThan(
            calculationContext.getOutlierBmi(), EsrdPricerContext.BMI_CUTOFF_18_POINT_5)) {
      calculationContext.setOutlierBmiFactor(
          calculationContext
              .getSeparatelyBillableBmiUnderCutoffMultiplier()
              .setScale(4, RoundingMode.HALF_UP));
    } else {
      calculationContext.setOutlierBmiFactor(new BigDecimal("1.0000"));
    }
  }
}
