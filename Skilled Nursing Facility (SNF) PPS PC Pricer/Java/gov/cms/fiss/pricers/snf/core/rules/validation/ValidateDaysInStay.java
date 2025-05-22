package gov.cms.fiss.pricers.snf.core.rules.validation;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimData;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingRequest;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingResponse;
import gov.cms.fiss.pricers.snf.core.SnfPricerContext;

public class ValidateDaysInStay
    implements CalculationRule<SnfClaimPricingRequest, SnfClaimPricingResponse, SnfPricerContext> {

  /** Converted from {@code 500-DAYS-IN-STAY-LOGIC} in the COBOL code. */
  @Override
  public void calculate(SnfPricerContext context) {
    final SnfClaimData claimData = context.getClaimData();
    int currentDays = claimData.getServiceUnits();
    int totalDays = currentDays + claimData.getPdpmPriorDays();

    // Cap total days at 100
    if (totalDays > 100) {
      currentDays = Math.max(0, currentDays + 100 - totalDays);
      totalDays = 100;
    }

    context.setCurrentDays(currentDays);
    context.setTotalDays(totalDays);
  }
}
