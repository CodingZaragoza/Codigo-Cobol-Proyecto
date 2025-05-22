package gov.cms.fiss.pricers.snf.core.rules.rate_selection;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingRequest;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingResponse;
import gov.cms.fiss.pricers.snf.core.ReturnCode;
import gov.cms.fiss.pricers.snf.core.SnfPricerContext;
import gov.cms.fiss.pricers.snf.core.tables.PhysicalAndOccupationalTherapyRateEntry;

public class SelectPhysicalAndOccupationalTherapyRates
    implements CalculationRule<SnfClaimPricingRequest, SnfClaimPricingResponse, SnfPricerContext> {

  /**
   * PT/OT rate selection.
   *
   * <p>Converted from {@code 600-CAPTURE-RATE-COMPONENTS}, {@code 2000-GET-URBAN-PT-OT-RATES} and
   * {@code 3000-GET-RURAL-PT-OT-RATES} in the COBOL code.
   */
  @Override
  public void calculate(SnfPricerContext context) {

    // *-------------------------------------------------------*
    // *---------->>  HIPPS CHARACTER 1              <<--------*
    // *--------->>   PT & OT RATES                  <<--------*
    // *-------------------------------------------------------*

    // HIPPS-CHAR-1
    final String group = Character.toString(context.getHippsCode().charAt(0));
    final PhysicalAndOccupationalTherapyRateEntry entry =
        context
            .getDataTables()
            .getPhysicalAndOccupationalTherapyRate(context.hasRuralCbsa(), group);

    if (entry == null) {
      context.applyReturnCodeAndTerminate(ReturnCode.INVALID_RATE_COMPONENT_20);
      return;
    }

    // *-------------------------------------------------------*
    // * >>>>> DETERMINE WHICH 2 OF 4 RATES TO ASSIGN
    // * >>>>> BASED ON QRP INDICATOR
    // * >>>>> PDPM-RATE-PT-U   &  PDPM-RATE-OT-U
    // * >>>>>                 -OR-
    // * >>>>> PDPM-QRATE-PT-U  &  PDPM-QRATE-OT-U
    // *-------------------------------------------------------*
    // * >>>>> PDPM-RATE-PT-R   &  PDPM-RATE-OT-R
    // * >>>>>                 -OR-
    // * >>>>> PDPM-QRATE-PT-R  &  PDPM-QRATE-OT-R
    // *-------------------------------------------------------*

    // Converted from 2010-PICK-URBAN-PT-OT / 3010-PICK-RURAL-PT-OT
    if (context.hasQualityAdjustment()) {
      context.setPhysicalTherapyRate(entry.getQualityAdjustedPhysicalTherapyRate());
      context.setOccupationalTherapyRate(entry.getQualityAdjustedOccupationalTherapyRate());
    } else {
      context.setPhysicalTherapyRate(entry.getPhysicalTherapyRate());
      context.setOccupationalTherapyRate(entry.getOccupationalTherapyRate());
    }
  }
}
