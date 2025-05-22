package gov.cms.fiss.pricers.ltch.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.core.LtchPricerContext;
import gov.cms.fiss.pricers.ltch.core.codes.ReturnCode;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CapLtchWageIndexDecrease
    implements CalculationRule<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {

  /**
   *
   *
   * <pre>
   * *** SUPPLEMENTAL WAGE INDEX ***
   * * WHEN P-SUPP-WI-IND = '1' THE SUPPLEMENTAL WAGE INDEX FIELD
   * * CONTAINS THE PRIOR YEAR'S LTCH WAGE INDEX.
   * * THIS CALCULATION CAPS THE LTCH WAGE INDEX DECREASE AT 5%.
   * </pre>
   */
  @Override
  public void calculate(LtchPricerContext calculationContext) {
    // IF P-SUPP-WI-IND = '1'
    if ("1".equals(calculationContext.getSupplementalWageIndexIndicator())) {
      // IF P-SUPP-WI > 0
      if (BigDecimalUtils.isGreaterThanZero(calculationContext.getSupplementalWageIndex())) {
        // COMPUTE H-LTCH-SUPP-WI-RATIO =
        // (W-NEW-INDEX3-RECORD-C - P-SUPP-WI) / P-SUPP-WI
        final BigDecimal supplementalWageIndexRatio =
            calculationContext
                .getLtchWageIndexTableEntry()
                .getLtchWageIndex3()
                .subtract(calculationContext.getSupplementalWageIndex())
                .divide(calculationContext.getSupplementalWageIndex(), RoundingMode.HALF_UP);
        // IF H-LTCH-SUPP-WI-RATIO <  -0.05
        if (BigDecimalUtils.isLessThan(supplementalWageIndexRatio, new BigDecimal("-0.05"))) {
          // COMPUTE W-NEW-INDEX3-RECORD-C ROUNDED =
          //        P-SUPP-WI * 0.95
          final BigDecimal ltchWageIndexCap =
              calculationContext
                  .getProviderData()
                  .getSupplementalWageIndex()
                  .multiply(new BigDecimal("0.95"))
                  .setScale(4, RoundingMode.HALF_UP);
          calculationContext.getLtchWageIndexTableEntry().setLtchWageIndex3(ltchWageIndexCap);
          calculationContext.getPaymentData().setFinalWageIndex(ltchWageIndexCap);
        } else {
          calculationContext.applyReturnCode(ReturnCode.INVALID_WAGE_INDEX_52);
        }
      } else {
        calculationContext.applyReturnCode(ReturnCode.INVALID_WAGE_INDEX_52);
      }
    }
  }

  @Override
  public boolean shouldExecute(LtchPricerContext calculationContext) {
    return !"1".equals(calculationContext.getProviderData().getSpecialPaymentIndicator());
  }
}
