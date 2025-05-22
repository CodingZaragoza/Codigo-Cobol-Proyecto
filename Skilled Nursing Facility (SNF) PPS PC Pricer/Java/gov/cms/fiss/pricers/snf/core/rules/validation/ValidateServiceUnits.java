package gov.cms.fiss.pricers.snf.core.rules.validation;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingRequest;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingResponse;
import gov.cms.fiss.pricers.snf.core.ReturnCode;
import gov.cms.fiss.pricers.snf.core.SnfPricerContext;

public class ValidateServiceUnits
    implements CalculationRule<SnfClaimPricingRequest, SnfClaimPricingResponse, SnfPricerContext> {

  /** Converted from {@code 400-VALIDATE-SNF-INPUT} in the COBOL code. */
  @Override
  public void calculate(SnfPricerContext context) {
    // THE PDPM-UNITS MUST NOT BE = 0
    if (context.getClaimData().getServiceUnits() == 0) {
      context.applyReturnCodeAndTerminate(ReturnCode.PDPM_UNITS_IS_ZERO_80);
    }
  }
}
