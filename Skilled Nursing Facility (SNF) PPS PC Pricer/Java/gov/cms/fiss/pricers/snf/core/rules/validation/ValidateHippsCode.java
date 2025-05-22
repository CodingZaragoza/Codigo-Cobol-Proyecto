package gov.cms.fiss.pricers.snf.core.rules.validation;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingRequest;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingResponse;
import gov.cms.fiss.pricers.snf.core.ReturnCode;
import gov.cms.fiss.pricers.snf.core.SnfPricerContext;
import org.apache.commons.lang3.StringUtils;

public class ValidateHippsCode
    implements CalculationRule<SnfClaimPricingRequest, SnfClaimPricingResponse, SnfPricerContext> {

  /**
   * DEFAULT HIPPS CODE CHECK
   *
   * <p>Converted from {@code 300-DEFAULT-HIPPS-CHECK} in the COBOL code.
   */
  @Override
  public void calculate(SnfPricerContext context) {
    context.setHippsCode(context.getClaimData().getHippsCode());

    // *   IF WK-HIPPS-CODE = 'ZZZZZ'                        *
    // *      REPLACE WITH 'PAYF1'                           *
    // *              TO SELECT RATING COMPONENTS            *

    if (StringUtils.equals(SnfPricerContext.DEFAULT_HIPPS_CODE, context.getHippsCode())) {
      context.setHippsCode(SnfPricerContext.REPLACEMENT_HIPPS_CODE);
      return;
    }

    // *   VALIDATE HIPPS CODE                               *
    // *       ALL 5 CHARACTERS HAVE PRESET VALUES           *
    // *       IF ANY CHARACTER DOES NOT MEET CRITERIA       *
    // *       RETURN TO DRIVER WITH RTC = 90                *
    if (!context.getHippsCode().matches("[A-P][A-L][A-Y][A-F][01]")) {
      context.applyReturnCodeAndTerminate(ReturnCode.INVALID_HIPPS_CODE_90);
    }
  }
}
