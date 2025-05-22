package gov.cms.fiss.pricers.esrd.core.rules.rules_2025.wage_index;

import gov.cms.fiss.pricers.common.api.OutpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import gov.cms.fiss.pricers.esrd.core.codes.ReturnCode;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;

/**
 * Conditionally applies the supplemental wage index to that referenced by CBSA.
 *
 * <p>Emulates the functions from {@code 0800-FIND-BUNDLED-CBSA-WI} in the {@code ESDRV} COBOL code.
 *
 * @since 2021, not used in 22, used again in 23 but changed date limit to after 12-31-22
 */
public class ApplySupplementalWageIndex2025
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public boolean shouldExecute(EsrdPricerContext calculationContext) {
    return !StringUtils.equals(
        "1", calculationContext.getProviderData().getSpecialPaymentIndicator());
  }

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();
    final OutpatientProviderData providerData = calculationContext.getProviderData();
    // changed for 2025 according to instructions from policy to apply the current wage index cap
    // logic
    // when the supplemental wage index indicator is 2
    if (LocalDateUtils.isAfter(claimData.getServiceThroughDate(), LocalDate.of(2024, 12, 31))
        && (StringUtils.equalsAny(providerData.getSupplementalWageIndexIndicator(), "1", "2"))) {
      if (BigDecimalUtils.isGreaterThanZero(providerData.getSupplementalWageIndex())) {
        final BigDecimal supplementalWageIndexRatio =
            calculationContext
                .getBundledWageIndex()
                .subtract(providerData.getSupplementalWageIndex())
                .divide(providerData.getSupplementalWageIndex(), RoundingMode.DOWN);
        if (BigDecimalUtils.isLessThan(supplementalWageIndexRatio, new BigDecimal("-0.05"))) {
          calculationContext.setBundledWageIndex(
              providerData
                  .getSupplementalWageIndex()
                  .multiply(new BigDecimal("0.95"))
                  .setScale(4, RoundingMode.HALF_UP));
        }
      } else {
        calculationContext.applyReturnCode(ReturnCode.CBSA_NOT_FOUND_60);
        calculationContext.setCalculationCompleted();
      }
    }
  }
}
