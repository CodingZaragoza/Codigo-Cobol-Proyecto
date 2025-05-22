package gov.cms.fiss.pricers.snf.core.rules.rate_selection;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingRequest;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingResponse;
import gov.cms.fiss.pricers.snf.core.ReturnCode;
import gov.cms.fiss.pricers.snf.core.SnfPricerContext;
import gov.cms.fiss.pricers.snf.core.tables.NursingRateEntry;

public class SelectNursingRate
    implements CalculationRule<SnfClaimPricingRequest, SnfClaimPricingResponse, SnfPricerContext> {

  /**
   * Nursing rate selection.
   *
   * <p>Converted from {@code 600-CAPTURE-RATE-COMPONENTS}, {@code 2300-GET-URBAN-NUR-RATES} and
   * {@code 3300-GET-RURAL-NUR-RATES} in the COBOL code.
   */
  @Override
  public void calculate(SnfPricerContext context) {

    // *-------------------------------------------------------*
    // *---------->>  HIPPS CHARACTER 3              <<--------*
    // *--------->>   NURSING RATES                  <<--------*
    // *-------------------------------------------------------*

    // HIPPS-CHAR-3
    final String group = Character.toString(context.getHippsCode().charAt(2));
    final NursingRateEntry entry =
        context.getDataTables().getNursingRate(context.hasRuralCbsa(), group);

    if (entry == null) {
      context.applyReturnCodeAndTerminate(ReturnCode.INVALID_RATE_COMPONENT_20);
      return;
    }

    // *----------------------------------------------------*
    // * >>>>> DETERMINE WHICH OF 4 RATES TO ASSIGN
    // * >>>>> BASED ON QRP INDICATOR
    // * >>>>> BASED ON QRP INDICATOR  & AIDS-IN OR COMBO
    // * >>>>> PDPM-RATE-NUR-U     -OR-
    // * >>>>> PDPM-ARATE-NUR-U    -OR-
    // * >>>>> PDPM-QRATE-NUR-U    -OR-
    // * >>>>> PDPM-AQRATE-NUR-U    -OR-
    // *----------------------------------------------------*
    // * >>>>> PDPM-RATE-NUR-R     -OR-
    // * >>>>> PDPM-ARATE-NUR-R    -OR-
    // * >>>>> PDPM-QRATE-NUR-R    -OR-
    // * >>>>> PDPM-AQRATE-NUR-R
    // *----------------------------------------------------*

    // Converted from 2310-PICK-URBAN-NURS / 3310-PICK-RURAL-NURS
    if (context.hasQualityAdjustment()) {
      if (context.hasAidsAdjustment()) {
        context.setNursingRate(entry.getAidsAndQualityAdjustedRate());
      } else {
        context.setNursingRate(entry.getQualityAdjustedRate());
      }
    } else {
      if (context.hasAidsAdjustment()) {
        context.setNursingRate(entry.getAidsAdjustedRate());
      } else {
        context.setNursingRate(entry.getRate());
      }
    }
  }
}
