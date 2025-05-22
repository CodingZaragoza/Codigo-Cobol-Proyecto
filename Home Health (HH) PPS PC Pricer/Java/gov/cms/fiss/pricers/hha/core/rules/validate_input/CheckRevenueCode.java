package gov.cms.fiss.pricers.hha.core.rules.validate_input;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingRequest;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingResponse;
import gov.cms.fiss.pricers.hha.api.v2.RevenueLineData;
import gov.cms.fiss.pricers.hha.api.v2.RevenuePaymentData;
import gov.cms.fiss.pricers.hha.core.HhaPricerContext;
import gov.cms.fiss.pricers.hha.core.codes.ReturnCode;
import gov.cms.fiss.pricers.hha.core.models.RevenueRateEntry;
import java.util.List;

// Validate and set Revenue data
public class CheckRevenueCode
    implements CalculationRule<HhaClaimPricingRequest, HhaClaimPricingResponse, HhaPricerContext> {
  @Override
  public boolean shouldExecute(HhaPricerContext calculationContext) {
    return calculationContext.hasReturnCode(ReturnCode.PAYMENT_WITHOUT_OUTLIER_0)
        && calculationContext.isBillTypeClaim();
  }

  @Override
  public void calculate(HhaPricerContext calculationContext) {
    // This and the related code in calculationContext are not in-line for the COBOL pricer so
    // this is a potential point of failure
    final List<RevenueLineData> inputRevenueData = calculationContext.getRevenueLines();
    final List<RevenueRateEntry> revenueRateData = calculationContext.getRevenueData();
    final List<RevenuePaymentData> outputRevenueData = calculationContext.getRevenuePayments();
    for (int i = 0; i < inputRevenueData.size(); i++) {
      final String revenueCode = inputRevenueData.get(i).getRevenueCode();
      final RevenueRateEntry revenueRateEntry = calculationContext.getRateEntry(revenueCode);
      if (revenueRateEntry == null) {
        calculationContext.completeWithReturnCode(ReturnCode.REVENUE_CODE_NOT_FOUND_80);
        return;
      }
      revenueRateData.add(revenueRateEntry);
      outputRevenueData.get(i).setDollarRate(revenueRateEntry.getDollarRate());
    }
  }
}
