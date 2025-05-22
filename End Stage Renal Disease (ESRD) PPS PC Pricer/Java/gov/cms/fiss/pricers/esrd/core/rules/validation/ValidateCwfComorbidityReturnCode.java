package gov.cms.fiss.pricers.esrd.core.rules.validation;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.ComorbidityData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import gov.cms.fiss.pricers.esrd.core.codes.ReturnCode;
import org.apache.commons.lang3.StringUtils;

/**
 * Performs common working file comorbidity return code validation.
 *
 * <p>Converted from {@code 1000-VALIDATE-BILL-ELEMENTS} in the COBOL code.
 *
 * @since 2020
 */
public class ValidateCwfComorbidityReturnCode
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
    final ComorbidityData comorbidities = calculationContext.getComorbidities();

    calculationContext.setCwfReturnCode(comorbidities.getCwfReturnCode());

    //        IF B-COND-CODE NOT = '84' THEN
    //           IF COMORBID-CWF-RETURN-CODE = SPACES OR
    //               "10" OR "20" OR "40" OR "50" OR "60" THEN
    //              NEXT SENTENCE
    //           ELSE
    //              MOVE 81                     TO PPS-RTC

    //    2025 REFORMATTED
    //    if !StringUtils.equals(
    //      EsrdPricerContext.CONDITION_CODE_AKI_MONTHLY_84, billingRecord.getConditionCode()

    if (!calculationContext.isAki84()
        && StringUtils.isNotEmpty(comorbidities.getCwfReturnCode())
        && calculationContext.isComorbidityReturnCodeInvalid()) {
      calculationContext.applyReturnCode(ReturnCode.INVALID_COMORBID_CWF_RETURN_CODE_81);
    }
  }
}
