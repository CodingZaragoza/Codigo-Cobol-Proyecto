package gov.cms.fiss.pricers.hha.core.rules.final_calculation;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimData;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingRequest;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingResponse;
import gov.cms.fiss.pricers.hha.api.v2.RevenuePaymentData;
import gov.cms.fiss.pricers.hha.core.HhaPricerContext;
import java.util.List;

public class CheckLupaPaymentValues
    implements CalculationRule<HhaClaimPricingRequest, HhaClaimPricingResponse, HhaPricerContext> {
  @Override
  public boolean shouldExecute(HhaPricerContext calculationContext) {
    return !calculationContext.isPartialEpisodePaymentCalculated();
  }

  @Override
  public void calculate(HhaPricerContext calculationContext) {
    final HhaClaimData input = calculationContext.getClaimData();
    final List<RevenuePaymentData> revenueData = calculationContext.getRevenuePayments();
    if (!input.getAdmissionDate().isEqual(input.getServiceFromDate())
        || input.getLupaSourceAdmissionIndicator().equals("B")
        || input.getAdjustmentIndicator().equals("2")
        || calculationContext.getPaymentData().getTotalQuantityOfCoveredVisits() == 0) {
      for (int i = 0; i < 5; i++) {
        revenueData.get(i).setAddOnVisitAmount(BigDecimalUtils.ZERO);
      }
    }
  }
}
