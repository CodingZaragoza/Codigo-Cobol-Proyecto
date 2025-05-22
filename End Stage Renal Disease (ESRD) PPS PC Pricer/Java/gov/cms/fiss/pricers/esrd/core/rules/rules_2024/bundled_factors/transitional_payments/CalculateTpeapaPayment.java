package gov.cms.fiss.pricers.esrd.core.rules.rules_2024.bundled_factors.transitional_payments;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculateTpeapaPayment
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();

    //      The transitional pediatric payment adjustment (TPEAPA) begins CY 2024,
    //      and will adjust payment for pediatric ESRD patients by 30 percent.
    //      It will be applied in CY 2024, 2025, and 2026 only.

    if (calculationContext.isPediatricClaim()) {
      calculationContext.setTpeapaPayment(
          calculationContext.getBundledAdjustedBaseWageAmount().multiply(new BigDecimal("0.30")));

      //     if (calculationContext.isPerDiemClaim()) {
      //       calculationContext.setTpeapaPayment(
      //           calculationContext
      //              .getTpeapaPayment()
      //              .multiply(BigDecimal.valueOf(3).divide(BigDecimal.valueOf(7))));

      if (calculationContext.isPerDiemClaim()) {
        calculationContext.setTpeapaPayment(
            calculationContext
                .getTpeapaPayment()
                .multiply(new BigDecimal(3))
                .divide(new BigDecimal(7), 4, RoundingMode.DOWN));
      }
    }
  }
}
