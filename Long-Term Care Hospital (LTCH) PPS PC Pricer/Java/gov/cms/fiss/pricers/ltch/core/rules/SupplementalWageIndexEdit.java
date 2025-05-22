package gov.cms.fiss.pricers.ltch.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.core.LtchPricerContext;
import gov.cms.fiss.pricers.ltch.core.codes.ReturnCode;
import java.math.BigDecimal;
import java.time.LocalDate;

public class SupplementalWageIndexEdit
    implements CalculationRule<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {

  @Override
  public boolean shouldExecute(LtchPricerContext calculationContext) {
    // Only execute if the special wage index is not selected
    return !"1".equals(calculationContext.getProviderData().getSpecialPaymentIndicator());
  }

  /**
   *
   *
   * <pre>
   * *----------------------------------------------------------*
   * * SUPPLEMENTAL WAGE INDEX EDIT                             *
   * *----------------------------------------------------------*
   * </pre>
   */
  @Override
  public void calculate(LtchPricerContext calculationContext) {
    final String supplementalWageIndexIndicator =
        calculationContext.getProviderData().getSupplementalWageIndexIndicator();
    final BigDecimal supplementalWageIndex =
        calculationContext.getProviderData().getSupplementalWageIndex();
    if (("1".equals(supplementalWageIndexIndicator) || "2".equals(supplementalWageIndexIndicator))
        && BigDecimalUtils.isLessThanOrEqualToZero(supplementalWageIndex)) {
      calculationContext.applyReturnCode(ReturnCode.INVALID_WAGE_INDEX_52);
      return;
    }
    final LocalDate effectiveDate = calculationContext.getProviderData().getEffectiveDate();
    if (("1".equals(supplementalWageIndexIndicator) || "2".equals(supplementalWageIndexIndicator))
        && (LocalDateUtils.isBefore(effectiveDate, calculationContext.getFyBegin())
            || LocalDateUtils.isAfter(effectiveDate, calculationContext.getFyEnd()))) {
      calculationContext.applyReturnCode(ReturnCode.INVALID_WAGE_INDEX_52);
    }
  }
}
