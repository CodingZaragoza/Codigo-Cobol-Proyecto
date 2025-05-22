package gov.cms.fiss.pricers.snf.core.rules.rate_selection;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingRequest;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingResponse;
import gov.cms.fiss.pricers.snf.core.ReturnCode;
import gov.cms.fiss.pricers.snf.core.SnfPricerContext;
import gov.cms.fiss.pricers.snf.core.tables.SpeechLanguagePathologyRateEntry;

public class SelectSpeechLanguagePathologyRate
    implements CalculationRule<SnfClaimPricingRequest, SnfClaimPricingResponse, SnfPricerContext> {

  /**
   * SLP rate selection.
   *
   * <p>Converted from {@code 600-CAPTURE-RATE-COMPONENTS}, {@code 2200-GET-URBAN-SLP-RATES} and
   * {@code 3200-GET-RURAL-SLP-RATES} in the COBOL code.
   */
  @Override
  public void calculate(SnfPricerContext context) {

    // *-------------------------------------------------------*
    // *---------->>  HIPPS CHARACTER 2              <<--------*
    // *--------->>       SLP RATES                  <<--------*
    // *-------------------------------------------------------*

    // HIPPS-CHAR-2
    final String group = Character.toString(context.getHippsCode().charAt(1));
    final SpeechLanguagePathologyRateEntry entry =
        context.getDataTables().getSpeechLanguagePathologyRate(context.hasRuralCbsa(), group);

    if (entry == null) {
      context.applyReturnCodeAndTerminate(ReturnCode.INVALID_RATE_COMPONENT_20);
      return;
    }

    // ****----------------------------------------------
    // * >>>>> DETERMINE WHICH OF 2 RATES TO ASSIGN
    // * >>>>> BASED ON QRP INDICATOR
    // * >>>>> PDPM-RATE-SLP-U  -OR-  PDPM-QRATE-SLP-U
    // ****----------------------------------------------
    // * >>>>> PDPM-RATE-SLP-R  -OR-  PDPM-QRATE-SLP-R
    // ****----------------------------------------------

    // Converted from 2210-PICK-URBAN-SLP / 3210-PICK-RURAL-SLP
    if (context.hasQualityAdjustment()) {
      context.setSpeechLanguagePathologyRate(entry.getQualityAdjustedRate());
    } else {
      context.setSpeechLanguagePathologyRate(entry.getRate());
    }
  }
}
