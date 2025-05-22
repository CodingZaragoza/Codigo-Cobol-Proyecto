package gov.cms.fiss.pricers.ltch.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.core.LtchPricerContext;

/**
 * Converted from 0580-FY2015-LATER-FLOOR-CBSA in COBOL. Gets the wage index floor values for
 * calculation.
 */
public class AssignFloorCBSA
    implements CalculationRule<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {

  @Override
  public void calculate(LtchPricerContext calculationContext) {
    calculationContext.setWageIndexFloor(calculationContext.getHoldProvIppsCbsaRural());
  }
}
