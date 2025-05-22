package gov.cms.fiss.pricers.ipps.core.rules.wage_index;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.CbsaReference;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.ResultCode;
import gov.cms.fiss.pricers.ipps.core.tables.CbsaWageIndexEntry;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;

/**
 * Determines the value of the wage index based on comparison to the rural CBSA wage index.
 *
 * <p>Converted from {@code 2300-2015-FWD-FLOOR-CBSA} in the COBOL code.
 *
 * @since 2019
 */
public class AdjustIndexForRuralFloor
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final CbsaReference cbsaReference = calculationContext.getCbsaReference();

    // **----------------------------------------------------------------
    // ** ENSURE THE CBSA WAGE INDEX IS A VALID VALUE, ELSE SET ERROR RTC
    // **----------------------------------------------------------------
    //     IF W-NEW-CBSA-WI NOT NUMERIC
    //        MOVE 0 TO W-NEW-CBSA-WI.
    //     IF W-NEW-CBSA-WI = 00.0000
    //        MOVE ALL '0' TO PPS-ADDITIONAL-VARIABLES
    //        MOVE 52 TO PPS-RTC
    //        GO TO 2300-2015-EXIT.
    if (cbsaReference.getWageIndex() != null
        && BigDecimalUtils.isZero(cbsaReference.getWageIndex())) {
      calculationContext.applyResultCode(ResultCode.RC_52_INVALID_WAGE_INDEX);

      return;
    }

    // **----------------------------------------------------------------
    // ** SET THE PROVIDER'S STATE RURAL CBSA
    // **----------------------------------------------------------------
    //     MOVE '   ' TO  H-CBSA-RURAL-BLANK.
    //     MOVE P-NEW-STATE-CODE-X TO H-CBSA-RURAL-STATE.
    //     IF H-CBSA-RURAL-STATE = '00'
    //        MOVE '03' TO H-CBSA-RURAL-STATE.
    final InpatientProviderData providerData = calculationContext.getProviderData();
    calculationContext.setStateCode(
        StringUtils.isNotEmpty(providerData.getStateCode())
            ? providerData.getStateCode().trim()
            : "00");
    if (calculationContext.getStateCode().equals("00")) {
      calculationContext.setStateCode("03");
    }

    adjustRuralFloor(calculationContext);
  }

  /**
   * Calculate the floor adjustment and apply it to the context.
   *
   * @param calculationContext the current calculation context
   */
  protected void adjustRuralFloor(IppsPricerContext calculationContext) {
    // *------------------------------------------------------------*
    // * SEARCH TABLE FOR RURAL IPPS CBSA & GET WAGE INDEX (FLOOR)  *
    // *------------------------------------------------------------*
    //     PERFORM 0175-GET-RURAL-CBSA THRU 0175-EXIT.
    //     IF PPS-RTC = 00
    //      IF W-RURAL-CBSA-EFF-DATE = WS-9S
    //        CONTINUE
    //      ELSE
    //        PERFORM 0660-GET-RURAL-CBSA-WAGE-INDX
    //         THRU   0660-EXIT VARYING MA2
    //                FROM MA1 BY 1 UNTIL
    //                T-CBSA (MA2) NOT = HOLD-RURAL-CBSA
    //      END-IF
    //     END-IF.
    // *    IF W-NEW-CBSA-WI NOT NUMERIC
    // *       MOVE 0 TO W-NEW-CBSA-WI.
    final CbsaWageIndexEntry ruralCbsaTableEntry =
        calculationContext.getCbsaWageIndexEntry(calculationContext.getStateCode());

    // *------------------------------------------------------------*
    // * IF NO RURAL WAGE INDEX FOUND, SET TO ZERO (VALID BECAUSE   *
    // * SOME STATES DO NOT HAVE A RURAL AREA)                      *
    // *------------------------------------------------------------*
    //     IF W-RURAL-CBSA-WI NOT NUMERIC
    //        MOVE 0 TO W-RURAL-CBSA-WI.
    BigDecimal ruralWi = BigDecimal.ZERO;

    // *------------------------------------------------------------*
    // * SEARCH TABLE FOR RURAL IPPS CBSA & GET WAGE INDEX (FLOOR)  *
    // *------------------------------------------------------------*
    //     PERFORM 0175-GET-RURAL-CBSA THRU 0175-EXIT.
    //     IF PPS-RTC = 00
    //      IF W-RURAL-CBSA-EFF-DATE = WS-9S
    //        CONTINUE
    //      ELSE
    //        PERFORM 0660-GET-RURAL-CBSA-WAGE-INDX
    //         THRU   0660-EXIT VARYING MA2
    //                FROM MA1 BY 1 UNTIL
    //                T-CBSA (MA2) NOT = HOLD-RURAL-CBSA
    //      END-IF
    //     END-IF.
    // *    IF W-NEW-CBSA-WI NOT NUMERIC
    // *       MOVE 0 TO W-NEW-CBSA-WI.
    if (ruralCbsaTableEntry != null) {
      final LocalDate ruralCbsaTableEntryEffectiveDate = ruralCbsaTableEntry.getEffectiveDate();

      if (LocalDateUtils.isAfterOrEqual(
              calculationContext.getDischargeDate(), ruralCbsaTableEntry.getEffectiveDate())
          && LocalDateUtils.inRange(
              ruralCbsaTableEntryEffectiveDate,
              calculationContext.fiscalYearStart(),
              calculationContext.fiscalYearEnd())) {
        ruralWi = ruralCbsaTableEntry.getGeographicWageIndex();
      }
    }

    final CbsaReference cbsaReference = calculationContext.getCbsaReference();

    // *------------------------------------------------------------*
    // * IF THE STATE'S RURAL FLOOR WAGE INDEX IS HIGHER THAN THE   *
    // * PROVIDER'S CBSA WAGE INDEX, REPLACE THE CBSA AND WAGE      *
    // * INDEX WITH STATE CODE AND RURAL FLOOR WAGE INDEX           *
    // *------------------------------------------------------------*
    //     IF W-RURAL-CBSA-WI > W-NEW-CBSA-WI
    //        MOVE WAGE-RURAL-CBSA-INDEX-RECORD TO
    //                   WAGE-NEW-CBSA-INDEX-RECORD
    //        MOVE 'N' TO P-NEW-CBSA-SPEC-PAY-IND
    //        MOVE HOLD-RURAL-CBSA TO HOLD-PROV-CBSA.
    if (BigDecimalUtils.isGreaterThan(ruralWi, cbsaReference.getWageIndex())) {
      if (ruralCbsaTableEntry == null) {
        cbsaReference.setCbsa(null);
        cbsaReference.setEffectiveDate(null);
        cbsaReference.setWageIndex(null);
      } else {
        cbsaReference.setCbsa(ruralCbsaTableEntry.getCbsa());
        cbsaReference.setEffectiveDate(ruralCbsaTableEntry.getEffectiveDate());
        cbsaReference.setWageIndex(ruralCbsaTableEntry.getGeographicWageIndex());
      }

      calculationContext.getProviderData().setSpecialPaymentIndicator("N");
      calculationContext.setCbsaLocation(calculationContext.getStateCode());
    }
  }
}
