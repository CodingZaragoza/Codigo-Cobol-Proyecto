package gov.cms.fiss.pricers.esrd.core.rules.rules_2025.bundled_factors.outliers;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculatePatientLevelCaseMixAdjusterForPostTdapa
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {
  @Override
  public void calculate(EsrdPricerContext calculationContext) {

    calculationContext.setPatientLevelCaseMixAdjusterForPostTdapa(
        calculationContext
            .getBundledAgeAdjustmentFactor()
            .multiply(
                calculationContext
                    .getBundledBsaFactor()
                    .multiply(calculationContext.getBundledBmiFactor()))
            .multiply(
                calculationContext
                    .getBundledOnsetFactor()
                    .multiply(calculationContext.getBundledComorbidityMultiplier()))
            .setScale(4, RoundingMode.DOWN));
    if (calculationContext.isPediatricClaim()) {
      calculationContext.setPatientLevelCaseMixAdjusterForPostTdapa(
          calculationContext
              .getPatientLevelCaseMixAdjusterForPostTdapa()
              .multiply(BigDecimal.valueOf(1.3)));
    }
  }
}
