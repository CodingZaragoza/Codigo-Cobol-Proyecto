package gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.outlier;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;

/**
 * Calculate the outlier rural adjustment multiplier.
 *
 * <pre>
 * **************************************************************
 * Calculate OUTLIER Rural Adjustment multiplier
 * **************************************************************
 * </pre>
 *
 * <p>Converted from {@code 2500-CALC-OUTLIER-FACTORS} (continued) in the COBOL code.
 *
 * @since 2020
 */
public class CalculateOutlierRuralAdjustmentMultiplier
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    //     IF (P-GEO-CBSA < 100) AND (H-PATIENT-AGE > 17) THEN
    //        MOVE SB-RURAL TO H-OUT-RURAL-MULTIPLIER
    //     ELSE
    //        MOVE 1.000 TO H-OUT-RURAL-MULTIPLIER.
    if (calculationContext.isCbsaRural() && calculationContext.isAdultPatient()) {
      calculationContext.setOutlierRuralMultiplier(
          calculationContext.getSeparatelyBillableRuralMultiplier());
    } else {
      calculationContext.setOutlierRuralMultiplier(EsrdPricerContext.DEFAULT_MULTIPLIER);
    }
  }
}
