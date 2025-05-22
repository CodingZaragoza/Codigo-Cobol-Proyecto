package gov.cms.fiss.pricers.ipps.core.rules.rules2020.wage_index;

import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.core.CbsaReference;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.rules.wage_index.AdjustIndexForRuralFloor;
import gov.cms.fiss.pricers.ipps.core.tables.RuralFloorTableEntry;
import java.math.BigDecimal;

/**
 * Determines the value of the wage index based on comparison to the rural floor lookup.
 *
 * <p>Converted from {@code 2300-2015-FWD-FLOOR-CBSA} in the COBOL code.
 *
 * @since 2019
 */
public class AdjustIndexForRuralFloor2020 extends AdjustIndexForRuralFloor {

  /**
   * Calculate the floor adjustment and apply it to the context.
   *
   * @param calculationContext the current calculation context
   */
  @Override
  protected void adjustRuralFloor(IppsPricerContext calculationContext) {
    // *------------------------------------------------------------*
    // * SEARCH TABLE FOR RURAL IPPS CBSA & GET WAGE INDEX (FLOOR)  *
    // *------------------------------------------------------------*
    //     PERFORM 0175-GET-RURAL-CBSA THRU 0175-EXIT.
    //     IF PPS-RTC = 00
    //      IF W-RURAL-CBSA-EFF-DATE NOT = WS-9S
    //       IF B-N-DISCHARGE-DATE > 20190930
    //        IF H-CBSA-PROV-BLANK = '   '
    //          GO TO 0690-BYPASS
    //        ELSE
    //          PERFORM 0690-GET-RURAL-FLOOR-WAGE-INDX THRU 0690-EXIT
    //          GO TO 0690-BYPASS
    //        END-IF
    //       END-IF
    //      END-IF
    //     END-IF.
    //     IF PPS-RTC = 00
    //       IF W-RURAL-CBSA-EFF-DATE NOT = WS-9S
    //         PERFORM 0660-GET-RURAL-CBSA-WAGE-INDX
    //          THRU   0660-EXIT VARYING MA2
    //                 FROM MA1 BY 1 UNTIL
    //                 T-CBSA (MA2) NOT = HOLD-RURAL-CBSA
    //       END-IF
    //     END-IF.
    // 0690-BYPASS.
    // *    IF W-NEW-CBSA-WI NOT NUMERIC
    // *       MOVE 0 TO W-NEW-CBSA-WI.
    if (calculationContext.getCbsaLocation().length() > 2) {
      final RuralFloorTableEntry ruralFloorTableEntry =
          calculationContext.getRuralFloor(calculationContext.getStateCode());
      // *------------------------------------------------------------*
      // * IF NO RURAL WAGE INDEX FOUND, SET TO ZERO (VALID BECAUSE   *
      // * SOME STATES DO NOT HAVE A RURAL AREA)                      *
      // *------------------------------------------------------------*
      //     IF W-RURAL-CBSA-WI NOT NUMERIC
      //        MOVE 0 TO W-RURAL-CBSA-WI.
      BigDecimal ruralWi = BigDecimal.ZERO;

      if (ruralFloorTableEntry != null) {
        ruralWi = ruralFloorTableEntry.getWageIndex();
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
        if (ruralFloorTableEntry == null) {
          cbsaReference.setCbsa(null);
          cbsaReference.setWageIndex(null);
        } else {
          cbsaReference.setCbsa(ruralFloorTableEntry.getCbsa());
          cbsaReference.setWageIndex(ruralFloorTableEntry.getWageIndex());
        }

        calculationContext.getProviderData().setSpecialPaymentIndicator("N");
        calculationContext.setCbsaLocation(calculationContext.getStateCode());
      }
    }
  }
}
