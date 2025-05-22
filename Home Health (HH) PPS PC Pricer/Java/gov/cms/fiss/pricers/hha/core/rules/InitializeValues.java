package gov.cms.fiss.pricers.hha.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingRequest;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingResponse;
import gov.cms.fiss.pricers.hha.core.HhaPricerContext;
import org.apache.commons.lang3.StringUtils;

public class InitializeValues
    implements CalculationRule<HhaClaimPricingRequest, HhaClaimPricingResponse, HhaPricerContext> {
  @Override
  public void calculate(HhaPricerContext calculationContext) {
    final String initialPaymentIndicator =
        calculationContext.getClaimData().getInitialPaymentQualityReportingProgramIndicator();
    if (StringUtils.equalsAny(initialPaymentIndicator, "0", "1")) {
      calculationContext.setFederalEpisodeRateAmount(calculationContext.getRateAmountChecked());
      calculationContext.setOutlierThresholdAmount(calculationContext.getThresholdAmountChecked());
    } else if (StringUtils.equalsAny(initialPaymentIndicator, "2", "3")) {
      calculationContext.setFederalEpisodeRateAmount(calculationContext.getRateAmountUnchecked());
      calculationContext.setOutlierThresholdAmount(
          calculationContext.getThresholdAmountUnchecked());
    }
  }
}
