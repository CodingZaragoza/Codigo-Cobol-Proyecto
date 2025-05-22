package gov.cms.fiss.pricers.esrd.core.rules.bundled_factors;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Calculates the bundled body-mass index (BMI) factor.
 *
 * <pre>
 * *****************************************************************
 * **  Calculate BUNDLED BMI factor                              ***
 * *****************************************************************
 * </pre>
 *
 * <p>Converted from {@code 2000-CALCULATE-BUNDLED-FACTORS} (continued) in the COBOL code.
 *
 * @since 2020
 */
public class CalculateBundledBmiFactor
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    //     COMPUTE H-BUN-BMI  ROUNDED = (B-PATIENT-WGT /
    //         (B-PATIENT-HGT ** 2)) * 10000.
    calculationContext.setBundledBmi(
        calculationContext.calculateBodyMassIndex().setScale(4, RoundingMode.HALF_UP));

    //     IF (H-PATIENT-AGE > 17) AND (H-BUN-BMI < 18.5)  THEN
    //        MOVE CM-BMI-LT-18-5            TO H-BUN-BMI-FACTOR
    //        MOVE "Y"                       TO LOW-BMI-TRACK
    //     ELSE
    //        MOVE 1.000                     TO H-BUN-BMI-FACTOR
    //     END-IF.
    if (calculationContext.isAdultPatient()
        && BigDecimalUtils.isLessThan(
            calculationContext.getBundledBmi(), EsrdPricerContext.BMI_CUTOFF_18_POINT_5)) {
      calculationContext.setBundledBmiFactor(
          calculationContext
              .getCaseMixBmiUnderEighteenPointFiveMultiplier()
              .setScale(4, RoundingMode.HALF_UP));

      calculationContext.setLowBmiClaim(true);
    } else {
      calculationContext.setBundledBmiFactor(new BigDecimal("1.0000"));
    }
  }
}
