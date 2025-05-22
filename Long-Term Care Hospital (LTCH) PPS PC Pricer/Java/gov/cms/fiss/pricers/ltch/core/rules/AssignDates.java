package gov.cms.fiss.pricers.ltch.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimData;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.core.LtchPricerContext;

/**
 * Extracting fiscal year information from the discharge date.
 *
 * <pre>
 * *----------------------------------------------------------*
 * * SET FY BEGIN AND END DATES USING BILL DISCHARGE DATE     *
 * *----------------------------------------------------------*
 * </pre>
 *
 * <p>Converted from {@code LTDRV212 PROCEDURE DIVISION} in the COBOL code.
 */
public class AssignDates
    implements CalculationRule<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {

  @Override
  public void calculate(LtchPricerContext calculationContext) {
    final LtchClaimData claimData = calculationContext.getClaimData();
    // Check to make sure pricer fiscal year is valid
    calculationContext.setFyBegin(LocalDateUtils.fiscalYearStart(claimData.getDischargeDate()));
    calculationContext.setFyEnd(LocalDateUtils.fiscalYearEnd(claimData.getDischargeDate()));
  }
}
