package gov.cms.fiss.pricers.esrd.core.rules.validation;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import gov.cms.fiss.pricers.esrd.core.codes.ReturnCode;
import java.math.BigDecimal;

/**
 * Performs maximum body mass index (BMI) validation.
 *
 * @since 2020
 */
public class ValidateMaximumBodyMassIndex
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public boolean shouldExecute(EsrdPricerContext calculationContext) {
    return calculationContext.matchesReturnCode(ReturnCode.CALCULATION_STARTED_00);
  }

  @Override
  public void calculate(EsrdPricerContext calculationContext) {

    //    2025 REPLACED
    //    if !StringUtils.equals(
    //            EsrdPricerContext.CONDITION_CODE_AKI_MONTHLY_84, billingRecord.getConditionCode()

    if (!calculationContext.isAki84()) {
      final BigDecimal bodyMassIndex = calculationContext.calculateBodyMassIndex();

      if (BigDecimalUtils.isGreaterThan(bodyMassIndex, EsrdPricerContext.MAX_BMI_AMOUNT)) {
        calculationContext.applyReturnCode(ReturnCode.HEIGHT_OUT_OF_RANGE_71);
      }
    }
  }
}
