package gov.cms.fiss.pricers.esrd.core.rules.bundled_factors;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;

/**
 * Calculates the rural adjustment multiplier.
 *
 * <pre>
 * *****************************************************************
 * Calculate Rural Adjustment Multiplier ADDED CY 2016
 * *****************************************************************
 * </pre>
 *
 * <p>Converted from {@code 2000-CALCULATE-BUNDLED-FACTORS} (continued) in the COBOL code.
 *
 * @since 2020
 */
public class CalculateRuralAdjustmentMultiplier
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    //     IF (P-GEO-CBSA < 100) AND (H-PATIENT-AGE > 17) THEN
    //        MOVE CM-RURAL TO H-BUN-RURAL-MULTIPLIER
    //     ELSE
    //        MOVE 1.000 TO H-BUN-RURAL-MULTIPLIER.
    if (calculationContext.isCbsaRural() && calculationContext.isAdultPatient()) {
      calculationContext.setBundledRuralMultiplier(calculationContext.getCaseMixRuralMultiplier());
    } else {
      calculationContext.setBundledRuralMultiplier(EsrdPricerContext.DEFAULT_MULTIPLIER);
    }
  }
}
