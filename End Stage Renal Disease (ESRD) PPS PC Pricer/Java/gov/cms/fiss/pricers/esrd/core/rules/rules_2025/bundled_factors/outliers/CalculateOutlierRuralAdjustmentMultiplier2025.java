package gov.cms.fiss.pricers.esrd.core.rules.rules_2025.bundled_factors.outliers;

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

/**
 * Calculate the outlier rural adjustment multiplier.
 *
 * <pre>
 * **************************************************************
 * Calculate OUTLIER Rural Adjustment multiplier
 * **************************************************************
 * </pre>
 *
 * <p>Converted from {@code 2500-CALC-OUTLIER-FACTORS} (continued) in the COBOL code.
 *
 * @since 2020
 */
public class CalculateOutlierRuralAdjustmentMultiplier2025
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final OutpatientProviderData providerData = calculationContext.getProviderData();
    final EsrdClaimData claimData = calculationContext.getClaimData();
    if (calculationContext.isAdultPatient()) {
      if (calculationContext.isCbsaRural()) {
        calculationContext.setOutlierRuralMultiplier(
            calculationContext.getSeparatelyBillableRuralMultiplier());
      } else if (calculationContext.isCbsaUrban()
          && StringUtils.equals("2", providerData.getSupplementalWageIndexIndicator())) {
        if (LocalDateUtils.isAfterOrEqual(
                claimData.getServiceThroughDate(), LocalDate.of(2025, 1, 1))
            && LocalDateUtils.isBeforeOrEqual(
                claimData.getServiceThroughDate(), LocalDate.of(2025, 12, 31))) {
          calculationContext.setOutlierRuralMultiplier((new BigDecimal("0.9853")));
        } else if (LocalDateUtils.isAfterOrEqual(
                claimData.getServiceThroughDate(), LocalDate.of(2026, 1, 1))
            && LocalDateUtils.isBeforeOrEqual(
                claimData.getServiceThroughDate(), LocalDate.of(2026, 12, 31))) {
          calculationContext.setOutlierRuralMultiplier((new BigDecimal("0.9927")));
        }
      } else {
        calculationContext.setOutlierRuralMultiplier(EsrdPricerContext.DEFAULT_MULTIPLIER);
      }
    } else {
      calculationContext.setOutlierRuralMultiplier(EsrdPricerContext.DEFAULT_MULTIPLIER);
    }
  }
}
