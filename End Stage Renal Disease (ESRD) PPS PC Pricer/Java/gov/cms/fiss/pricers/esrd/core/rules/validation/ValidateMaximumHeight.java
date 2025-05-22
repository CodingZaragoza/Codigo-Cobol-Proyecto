package gov.cms.fiss.pricers.esrd.core.rules.validation;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import gov.cms.fiss.pricers.esrd.core.codes.ReturnCode;
import java.math.BigDecimal;

/**
 * Performs maximum height validation.
 *
 * <p>Converted from {@code 1000-VALIDATE-BILL-ELEMENTS} in the COBOL code.
 *
 * @since 2020
 */
public class ValidateMaximumHeight
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {
  private static final BigDecimal MAXIMUM_HEIGHT = new BigDecimal("300.00");

  @Override
  public boolean shouldExecute(EsrdPricerContext calculationContext) {
    //    IF PPS-RTC = 00  THEN
    //    END-IF.
    return calculationContext.matchesReturnCode(ReturnCode.CALCULATION_STARTED_00);
  }

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();

    //       IF B-COND-CODE NOT = '84' THEN
    //          IF B-PATIENT-HGT > 300.00
    //             MOVE 71                     TO PPS-RTC

    // 2025 REPLACED
    //    if !StringUtils.equals(
    //            EsrdPricerContext.CONDITION_CODE_AKI_MONTHLY_84, billingRecord.getConditionCode()

    if (!calculationContext.isAki84()
        && BigDecimalUtils.isGreaterThan(claimData.getPatientHeight(), MAXIMUM_HEIGHT)) {
      calculationContext.applyReturnCode(ReturnCode.HEIGHT_OUT_OF_RANGE_71);
    }
  }
}
