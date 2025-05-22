package gov.cms.fiss.pricers.hha.core.rules.final_calculation;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingRequest;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingResponse;
import gov.cms.fiss.pricers.hha.api.v2.RevenueLineData;
import gov.cms.fiss.pricers.hha.api.v2.RevenuePaymentData;
import gov.cms.fiss.pricers.hha.core.HhaPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CalculateLupaPayment
    implements CalculationRule<HhaClaimPricingRequest, HhaClaimPricingResponse, HhaPricerContext> {
  @Override
  public boolean shouldExecute(HhaPricerContext calculationContext) {
    return !calculationContext.isPartialEpisodePaymentCalculated();
  }

  // 1050-LUPA
  @Override
  public void calculate(HhaPricerContext calculationContext) {
    final List<RevenueLineData> inputRevenue = calculationContext.getRevenueLines();
    final List<RevenuePaymentData> outputRevenueData = calculationContext.getRevenuePayments();
    for (int i = 0; i < inputRevenue.size(); i++) {
      final RevenueLineData inputData = inputRevenue.get(i);
      final RevenuePaymentData outputRevenueEntry = outputRevenueData.get(i);
      // COMPUTE FED-ADJi ROUNDED =
      //   (H-HHA-REVENUE-QTY-COV-VISITS (i) * H-HHA-REVENUE-DOLL-RATE (i)).
      final BigDecimal federalAdjustment =
          BigDecimal.valueOf(inputData.getQuantityOfCoveredVisits())
              .multiply(outputRevenueEntry.getDollarRate());
      // COMPUTE FED-LABOR-ADJi ROUNDED = WIR-CBSA-WAGEIND * LABOR-PERCENT * FED-ADJi.
      // COMPUTE FED-NON-LABOR-ADJi ROUNDED = NONLABOR-PERCENT * FED-ADJi.
      // COMPUTE H-HHA-REVENUE-COST (i) ROUNDED = (FED-LABOR-ADJi + FED-NON-LABOR-ADJi).
      final BigDecimal revenueCost = calculationContext.calculateAdjustedCost(federalAdjustment);

      outputRevenueEntry.setCost(revenueCost.setScale(2, RoundingMode.HALF_UP));

      // this section currently only applies to lines 1, 3, and 4, but aside from additional
      // calculation time shouldn't affect the output of the other lines even if addOnVisitAmount
      // isn't null
      // COMPUTE FED-LUPA-ADJi ROUNDED = H-HHA-REVENUE-ADD-ON-VISIT-AMT (i).
      final BigDecimal federalLupaAdjustment = outputRevenueEntry.getAddOnVisitAmount();
      if (federalLupaAdjustment != null) {
        // COMPUTE FED-LABOR-LUPA-ADJi ROUNDED = WIR-CBSA-WAGEIND * LABOR-PERCENT * FED-LUPA-ADJi.
        // COMPUTE FED-NON-LABOR-LUPA-ADJi ROUNDED = NONLABOR-PERCENT * FED-LUPA-ADJi.
        // COMPUTE H-HHA-REVENUE-ADD-ON-VISIT-AMT (i) ROUNDED =
        //   (FED-LABOR-LUPA-ADJi + FED-NON-LABOR-LUPA-ADJi).
        final BigDecimal revenueAddOn =
            calculationContext.calculateAdjustedCost(federalLupaAdjustment);
        outputRevenueEntry.setAddOnVisitAmount(revenueAddOn);
      }
    }
  }
}
