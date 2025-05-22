package gov.cms.fiss.pricers.snf.core.rules.rate_selection;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingRequest;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingResponse;
import gov.cms.fiss.pricers.snf.core.ReturnCode;
import gov.cms.fiss.pricers.snf.core.SnfPricerContext;
import gov.cms.fiss.pricers.snf.core.tables.NonTherapyAncillaryRateEntry;

public class SelectNonTherapyAncillaryRate
    implements CalculationRule<SnfClaimPricingRequest, SnfClaimPricingResponse, SnfPricerContext> {

  /**
   * NTA rate selection.
   *
   * <p>Converted from {@code 600-CAPTURE-RATE-COMPONENTS}, {@code 2400-GET-URBAN-NTA-RATES} and
   * {@code 3400-GET-RURAL-NTA-RATES} in the COBOL code.
   */
  @Override
  public void calculate(SnfPricerContext context) {

    // *-------------------------------------------------------*
    // *---------->>  HIPPS CHARACTER 4              <<--------*
    // *--------->>   NTA (NON-THERAPUTIC ANCILLARY)    -------*
    // *-------------------------------------------------------*

    // HIPPS-CHAR-4
    String group = Character.toString(context.getHippsCode().charAt(3));

    if (context.hasAidsAdjustment()) {
      if (group.matches("[A-D]")) {
        group = "A";
      } else if (group.equals("E")) {
        group = "B";
      } else if (group.equals("F")) {
        group = "C";
      }
    }

    final NonTherapyAncillaryRateEntry entry =
        context.getDataTables().getNonTherapyAncillaryRate(context.hasRuralCbsa(), group);

    if (entry == null) {
      context.applyReturnCodeAndTerminate(ReturnCode.INVALID_RATE_COMPONENT_20);
      return;
    }

    // *----------------------------------------------------*
    // * >>>>> DETERMINE WHICH OF 2 RATES TO ASSIGN
    // * >>>>> BASED ON QRP INDICATOR
    // * >>>>> PDPM-RATE-NTA-U  -OR-  PDPM-QRATE-NTA-U
    // *----------------------------------------------------*
    // * >>>>> PDPM-RATE-NTA-R  -OR-  PDPM-QRATE-NTA-R
    // *----------------------------------------------------*

    // Converted from 2410-PICK-URBAN-NTA / 3410-PICK-RURAL-NTA
    if (context.hasQualityAdjustment()) {
      context.setNonTherapyAncillaryRate(entry.getQualityAdjustedRate());
    } else {
      context.setNonTherapyAncillaryRate(entry.getRate());
    }
  }
}
