package gov.cms.fiss.pricers.esrd.core.rules.rules_2024.bundled_factors.transitional_payments;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.RoundingMode;

// Calculates the patient-level adjustment factors

public class CalculateCaseMixAdjuster
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    //     Case Mix Adjuster  =
    //      Age Factor *
    //      BSA Factor *
    //      BMI Factor *
    //      Comorbidity Factor *
    //      Date Onset Factor

    calculationContext.setCaseMixAdjuster(
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
  }
}
