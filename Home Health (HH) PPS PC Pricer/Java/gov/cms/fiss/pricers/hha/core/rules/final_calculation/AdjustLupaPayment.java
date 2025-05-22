package gov.cms.fiss.pricers.hha.core.rules.final_calculation;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingRequest;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingResponse;
import gov.cms.fiss.pricers.hha.api.v2.RevenuePaymentData;
import gov.cms.fiss.pricers.hha.core.HhaPricerContext;
import gov.cms.fiss.pricers.hha.core.codes.ReturnCode;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class AdjustLupaPayment
    implements CalculationRule<HhaClaimPricingRequest, HhaClaimPricingResponse, HhaPricerContext> {
  @Override
  public boolean shouldExecute(HhaPricerContext calculationContext) {
    return !calculationContext.isPartialEpisodePaymentCalculated();
  }

  @Override
  public void calculate(HhaPricerContext calculationContext) {
    final List<RevenuePaymentData> revenueData = calculationContext.getRevenuePayments();
    for (int i = 0; i < 5; i++) {
      if (BigDecimalUtils.isGreaterThanZero(revenueData.get(i).getAddOnVisitAmount())) {
        calculationContext.applyReturnCode(ReturnCode.LUPA_PAYMENT_WITH_ADDON_14);
        break;
      }
    }
    if (!calculationContext.checkReturnCode(ReturnCode.LUPA_PAYMENT_WITH_ADDON_14)) {
      calculationContext.applyReturnCode(ReturnCode.LUPA_PAYMENT_6);
    }

    BigDecimal totalPayment = BigDecimalUtils.ZERO;
    for (final RevenuePaymentData rateEntry : revenueData) {
      totalPayment = totalPayment.add(rateEntry.getCost().add(rateEntry.getAddOnVisitAmount()));
    }

    totalPayment = totalPayment.setScale(2, RoundingMode.HALF_UP);
    calculationContext.getPaymentData().setTotalPayment(totalPayment);
  }
}
