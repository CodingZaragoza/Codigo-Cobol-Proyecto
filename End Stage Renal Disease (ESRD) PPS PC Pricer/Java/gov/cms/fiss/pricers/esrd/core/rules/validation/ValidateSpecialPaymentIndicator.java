package gov.cms.fiss.pricers.esrd.core.rules.validation;

import gov.cms.fiss.pricers.common.api.OutpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import gov.cms.fiss.pricers.esrd.core.codes.ReturnCode;
import org.apache.commons.lang3.StringUtils;

/**
 * Performs special payment indicator validation.
 *
 * <p>Converted from {@code 1000-VALIDATE-BILL-ELEMENTS} in the COBOL code.
 *
 * @since 2020
 */
public class ValidateSpecialPaymentIndicator
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public boolean shouldExecute(EsrdPricerContext calculationContext) {
    //    IF PPS-RTC = 00  THEN
    //    END-IF.
    return calculationContext.matchesReturnCode(ReturnCode.CALCULATION_STARTED_00);
  }

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final OutpatientProviderData providerData = calculationContext.getProviderData();

    //       IF P-SPEC-PYMT-IND NOT = '1' AND ' '  THEN
    //          MOVE 53                  TO PPS-RTC
    //       END-IF
    if (StringUtils.isNotEmpty(providerData.getSpecialPaymentIndicator())
        && !StringUtils.equals("1", providerData.getSpecialPaymentIndicator())) {
      calculationContext.applyReturnCode(ReturnCode.INVALID_SPECIAL_PAYMENT_INDICATOR_53);
    }
  }
}
