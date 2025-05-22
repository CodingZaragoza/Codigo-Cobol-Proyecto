package gov.cms.fiss.pricers.snf.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingRequest;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingResponse;
import gov.cms.fiss.pricers.snf.core.ReturnCode;
import gov.cms.fiss.pricers.snf.core.SnfPricerContext;
import gov.cms.fiss.pricers.snf.core.tables.CbsaWageIndexEntry;
import java.math.BigDecimal;
import java.time.LocalDate;

public class DetermineWageIndex
    implements CalculationRule<SnfClaimPricingRequest, SnfClaimPricingResponse, SnfPricerContext> {

  /**
   * SELECT WAGE INDEX TO BE USED
   *
   * <p>Converted from {@code 1900-SELECT-CBSA-WAGE-INDEX} in the SNFDR COBOL code.
   */
  @Override
  public void calculate(SnfPricerContext context) {
    final BigDecimal specialWageIndex = context.getProviderData().getSpecialWageIndex();
    final String cbsa = context.getProviderData().getCbsaActualGeographicLocation();

    // The following has been omitted since value will always be numeric
    // VALIDATE SPECIAL WAGE INDEX CRITERIA (SNF-RTC 30)
    //      IF  SNF-SPEC-WI-IND = '1'
    //          AND SNF-SPEC-WI-X NOT NUMERIC
    //          MOVE '30'             TO SNF-RTC
    //          GO TO 1900-EXIT.

    // IF SPECIAL WAGE INDEX CRITERIA ARE GOOD APPLY SPECIAL WAGE INDEX "IN LIEU" OF CBSA WAGE INDEX
    if (context.hasSpecialWageIndexAdjustment()) {
      context.setWageIndex(specialWageIndex);
    } else {
      final LocalDate throughDate = context.getClaimData().getServiceThroughDate();
      final CbsaWageIndexEntry wageIndexEntry =
          context.getDataTables().getCbsaWageIndexEntry(cbsa, throughDate);

      if (wageIndexEntry == null) {
        context.applyReturnCodeAndTerminate(ReturnCode.INVALID_CBSA_OR_WAGE_INDEX_30);
        return;
      }

      context.setWageIndex(wageIndexEntry.getGeographicWageIndex());
    }
  }
}
