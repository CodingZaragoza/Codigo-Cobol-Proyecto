package gov.cms.fiss.pricers.hha.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingRequest;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingResponse;
import gov.cms.fiss.pricers.hha.api.v2.RevenuePaymentData;
import gov.cms.fiss.pricers.hha.core.HhaPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class AdjustRevenueDollarRate
    implements CalculationRule<HhaClaimPricingRequest, HhaClaimPricingResponse, HhaPricerContext> {
  @Override
  public void calculate(HhaPricerContext calculationContext) {
    final BigDecimal ruralAddOn = calculationContext.getRuralAddon();
    final List<RevenuePaymentData> outputRevenueData = calculationContext.getRevenuePayments();
    for (final RevenuePaymentData outputRevenueEntry : outputRevenueData) {
      outputRevenueEntry.setDollarRate(
          outputRevenueEntry
              .getDollarRate()
              .multiply(ruralAddOn)
              .setScale(2, RoundingMode.HALF_UP));
    }
  }
}
