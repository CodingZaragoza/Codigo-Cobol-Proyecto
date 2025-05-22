package gov.cms.fiss.pricers.esrd.core.rules.rules_2025.wage_index;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import gov.cms.fiss.pricers.esrd.core.codes.ReturnCode;
import org.apache.commons.lang3.StringUtils;

/**
 * Applies the special wage index; this prevents other wage index lookups from occurring.
 *
 * <p>Emulates the functions from {@code 0800-FIND-BUNDLED-CBSA-WI} in the {@code ESDRV} COBOL code.
 *
 * @since 2020
 */
public class ApplySpecialWageIndex2025
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public boolean shouldExecute(EsrdPricerContext calculationContext) {
    //     IF P-SPEC-PYMT-IND = '1'  THEN
    //     END-IF.
    return StringUtils.equals(
        "1", calculationContext.getProviderData().getSpecialPaymentIndicator());
  }

  @Override
  public void calculate(EsrdPricerContext calculationContext) {

    // not using Special Wage Index in 2025

    calculationContext.applyReturnCode(ReturnCode.CBSA_NOT_FOUND_60);
    calculationContext.setCalculationCompleted();

    // the rest of these lines are for turning back on the Special Wage Index
    // final OutpatientProviderData providerData = calculationContext.getProviderData()

    //        MOVE P-SPEC-WAGE-INDX     TO BUN-CBSA-W-INDEX
    //        GO TO 0800-FIND-EXIT
    // final BigDecimal specialWageIndex = providerData.getSpecialWageIndex()

    // Halt processing if the special wage index was not provided when it needs to be used
    // if null == specialWageIndex
    //  calculationContext.applyReturnCode(ReturnCode.CBSA_NOT_FOUND_60)
    //  calculationContext.setCalculationCompleted()
    //  else
    //   calculationContext.setBundledWageIndex(specialWageIndex)
  }
}
