package gov.cms.fiss.pricers.snf.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingRequest;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingResponse;
import gov.cms.fiss.pricers.snf.core.ReturnCode;
import gov.cms.fiss.pricers.snf.core.SnfPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.commons.lang3.StringUtils;

public class ApplySupplementalWageIndex
    implements CalculationRule<SnfClaimPricingRequest, SnfClaimPricingResponse, SnfPricerContext> {

  @Override
  public boolean shouldExecute(SnfPricerContext context) {
    // Only execute if the special wage index is not enabled and supplemental wage index is enabled
    return !context.hasSpecialWageIndexAdjustment()
        && StringUtils.equals("1", context.getProviderData().getSupplementalWageIndexIndicator());
  }

  /**
   * SUPPLEMENTAL WAGE INDEX VALIDATION CHECK FOR FY 2021 AND LATER
   *
   * <p>Converted from {@code 2020-CAP-WAGE-INDEX-DECREASE} in the SNFDR COBOL code.
   */
  @Override
  public void calculate(SnfPricerContext context) {
    final BigDecimal supplementalWageIndex = context.getProviderData().getSupplementalWageIndex();

    // IF THE INDICATOR IS '1' AND THE SUPP WI IS 0 OR SPACES, RTC=30
    if (BigDecimalUtils.isZero(supplementalWageIndex)) {
      context.applyReturnCodeAndTerminate(ReturnCode.INVALID_CBSA_OR_WAGE_INDEX_30);
      return;
    }

    // APPLY 5% CAP ON WAGE INDEX DECREASES STARTING FY 2021
    // IF WAGE INDEX DECREASED MORE THAN 5%, WI = 95% 0F PRIOR YR WI (PRIOR YEAR WI IS THE
    // SUPPLEMENTAL WI)
    if (BigDecimalUtils.isLessThan(
        context
            .getWageIndex()
            .subtract(supplementalWageIndex)
            .divide(supplementalWageIndex, RoundingMode.DOWN),
        context.getWageIndexDecreaseCap())) {

      // WS-WI-PCT-ADJ-FY2021
      final BigDecimal wageIndexPercentAdjustment =
          BigDecimal.ONE.add(context.getWageIndexDecreaseCap());

      // COMPUTE CBSA-WIR-AREA-WAGEIND ROUNDED = (SNF-SUPP-WI *  WS-WI-PCT-ADJ-FY2021)
      context.setWageIndex(
          supplementalWageIndex
              .multiply(wageIndexPercentAdjustment)
              .setScale(4, RoundingMode.HALF_UP));
    }
  }
}
