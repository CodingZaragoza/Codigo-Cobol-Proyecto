package gov.cms.fiss.pricers.snf.core.rules.rate_selection;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingRequest;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingResponse;
import gov.cms.fiss.pricers.snf.core.SnfPricerContext;

public class SelectNonCaseMixRate
    implements CalculationRule<SnfClaimPricingRequest, SnfClaimPricingResponse, SnfPricerContext> {

  /**
   * NCM rate selection.
   *
   * <p>Converted from {@code 600-CAPTURE-RATE-COMPONENTS}, {@code 3500-GET-RURAL-NCM-RATES} and
   * {@code 2500-GET-URBAN-NCM-RATES} in the COBOL code.
   */
  @Override
  public void calculate(SnfPricerContext context) {

    // *-------------------------------------------------------*
    // *---------->>  LAST RATE COMPONENT (ALL HIPPS)<--------*
    // *--------->>   NON-CASE-MIXED                    -------*
    // *-------------------------------------------------------*

    if (context.hasRuralCbsa()) {
      // * >>>>>
      // * >>>>> DETERMINE WHICH OF 2 RATES TO ASSIGN
      // * >>>>> BASED ON QRP INDICATOR
      // * >>>>> RURAL-NCM-COMP-2020  -OR-  RURAL-QRP-NCM-COMP-2020
      // * >>>>>
      if (context.hasQualityAdjustment()) {
        context.setNonCaseMixRate(context.getRuralQualityAdjustedNonCaseMixRate());
      } else {
        context.setNonCaseMixRate(context.getRuralNonCaseMixRate());
      }
    } else {
      // * >>>>>
      // * >>>>> DETERMINE WHICH OF 2 RATES TO ASSIGN
      // * >>>>> BASED ON QRP INDICATOR
      // * >>>>> URBAN-NCM-COMP-2020  -OR-  URBAN-QRP-NCM-COMP-2020
      // * >>>>>
      if (context.hasQualityAdjustment()) {
        context.setNonCaseMixRate(context.getUrbanQualityAdjustedNonCaseMixRate());
      } else {
        context.setNonCaseMixRate(context.getUrbanNonCaseMixRate());
      }
    }
  }
}
