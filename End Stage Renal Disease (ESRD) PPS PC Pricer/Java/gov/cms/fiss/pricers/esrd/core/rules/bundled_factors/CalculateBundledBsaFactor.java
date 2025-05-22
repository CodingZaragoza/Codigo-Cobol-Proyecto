package gov.cms.fiss.pricers.esrd.core.rules.bundled_factors;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Calculates the bundled body surface area (BSA) factor.
 *
 * <pre>
 * *****************************************************************
 * **  Calculate BUNDLED BSA factor (note NEW formula)           ***
 * *****************************************************************
 * </pre>
 *
 * <p>Converted from {@code 2000-CALCULATE-BUNDLED-FACTORS} (continued) in the COBOL code.
 *
 * @since 2020
 */
public class CalculateBundledBsaFactor
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    //     COMPUTE H-BUN-BSA  ROUNDED = (.007184 *
    //         (B-PATIENT-HGT ** .725) * (B-PATIENT-WGT ** .425))
    calculationContext.setBundledBsa(calculationContext.calculateBodySurfaceArea());

    //     IF H-PATIENT-AGE > 17  THEN
    //        COMPUTE H-BUN-BSA-FACTOR  ROUNDED =
    //             CM-BSA ** ((H-BUN-BSA - BSA-NATIONAL-AVERAGE) / .1)
    //     ELSE
    //        MOVE 1.000                     TO H-BUN-BSA-FACTOR
    //     END-IF.
    if (calculationContext.isAdultPatient()) {
      calculationContext.setBundledBsaFactor(
          BigDecimalUtils.pow(
                  calculationContext.getCaseMixBsaMultiplier(),
                  calculationContext
                      .getBundledBsa()
                      .subtract(calculationContext.getBsaNationalAverage())
                      .divide(new BigDecimal(".1"), RoundingMode.DOWN),
                  9)
              .setScale(4, RoundingMode.HALF_UP));
    } else {
      calculationContext.setBundledBsaFactor(new BigDecimal("1.0000"));
    }
  }
}
