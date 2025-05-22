package gov.cms.fiss.pricers.esrd.core.rules.rules_2025.bundled_factors;

import gov.cms.fiss.pricers.common.api.OutpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;

public class CalculateRuralAdjustmentMultiplier2025
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final OutpatientProviderData providerData = calculationContext.getProviderData();
    final EsrdClaimData claimData = calculationContext.getClaimData();
    if (calculationContext.isAdultPatient()) {
      if (calculationContext.isCbsaRural()) {
        calculationContext.setBundledRuralMultiplier(
            calculationContext.getCaseMixRuralMultiplier());
      } else if (calculationContext.isCbsaUrban()
          && StringUtils.equals("2", providerData.getSupplementalWageIndexIndicator())) {
        if (LocalDateUtils.isAfterOrEqual(
                claimData.getServiceThroughDate(), LocalDate.of(2025, 1, 1))
            && LocalDateUtils.isBeforeOrEqual(
                claimData.getServiceThroughDate(), LocalDate.of(2025, 12, 31))) {
          calculationContext.setBundledRuralMultiplier((new BigDecimal("1.0053")));
        } else if (LocalDateUtils.isAfterOrEqual(
                claimData.getServiceThroughDate(), LocalDate.of(2026, 1, 1))
            && LocalDateUtils.isBeforeOrEqual(
                claimData.getServiceThroughDate(), LocalDate.of(2026, 12, 31))) {
          calculationContext.setBundledRuralMultiplier((new BigDecimal("1.0027")));
        }
      } else {
        calculationContext.setBundledRuralMultiplier(EsrdPricerContext.DEFAULT_MULTIPLIER);
      }
    } else {
      calculationContext.setBundledRuralMultiplier(EsrdPricerContext.DEFAULT_MULTIPLIER);
    }
  }
}
