package gov.cms.fiss.pricers.ipps.core.rules.wage_index;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.tables.OutMigrationTableEntry;
import org.apache.commons.lang3.StringUtils;

/**
 * Determines the outmigration adjustment factor.
 *
 * <p>Converted from {@code 0900-GET-COUNTY-CODE}, {@code 0950-GET-OUTM-ADJ} in the COBOL code.
 *
 * @since 2019
 */
public class ValidateOutMigration
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    // ****************************
    // * BEGIN OUTMIGRATION CHECK *
    // ****************************
    //     IF (P-NEW-CBSA-RECLASS-LOC = '     ' OR
    //         P-NEW-CBSA-RECLASS-LOC = '00000') AND
    //        (P-NEW-CBSA-STAND-AMT-LOC = '     ' OR
    //         P-NEW-CBSA-STAND-AMT-LOC = '00000') AND
    //         P-NEW-CBSA-WI-BLANK
    //       PERFORM 0900-GET-COUNTY-CODE THRU 0900-EXIT
    //     END-IF.
    //     IF OUTM-IND = 1
    //       PERFORM 0950-GET-OUTM-ADJ THRU 0950-EXIT
    //         VARYING OUTM-IDX2 FROM OUTM-IDX BY 1 UNTIL
    //         OUTM-CNTY(OUTM-IDX2) NOT = P-NEW-COUNTY-CODE-X
    //     END-IF.
    // **************************
    // * END OUTMIGRATION CHECK *
    // **************************
    //
    // 0900-GET-COUNTY-CODE.
    //     SET OUTM-IDX TO 1.
    //     SEARCH OUTM-TAB VARYING OUTM-IDX
    //     AT END
    //          GO TO 0900-EXIT
    //     WHEN OUTM-CNTY(OUTM-IDX) = P-NEW-COUNTY-CODE-X
    //        SET OUTM-IDX2 TO OUTM-IDX
    //        MOVE 1 TO OUTM-IND.
    // 0900-EXIT.  EXIT.

    // 0950-GET-OUTM-ADJ.
    //     IF OUTM-EFF-DATE(OUTM-IDX2) <= B-N-DISCHARGE-DATE AND
    //        OUTM-EFF-DATE(OUTM-IDX2) >= W-FY-BEGIN-DATE AND
    //        OUTM-EFF-DATE(OUTM-IDX2) <= W-FY-END-DATE
    //          MOVE OUTM-ADJ-FACT(OUTM-IDX2) TO HLD-OUTM-ADJ.
    // 0950-EXIT.  EXIT.
    final InpatientProviderData providerData = calculationContext.getProviderData();
    final String county = calculationContext.getProviderData().getCountyCode();
    final OutMigrationTableEntry outMigrationTableEntry =
        calculationContext
            .getDataTables()
            .getOutMigrationEntry(
                county,
                calculationContext.getDischargeDate(),
                calculationContext.fiscalYearStart());

    if ((StringUtils.isBlank(providerData.getCbsaWageIndexLocation())
            || providerData.getCbsaWageIndexLocation().equals(IppsPricerContext.ZEROS))
        && (StringUtils.isBlank(providerData.getCbsaStandardizedAmountLocation())
            || providerData.getCbsaStandardizedAmountLocation().equals(IppsPricerContext.ZEROS))
        && calculationContext.isCbsaSpecialPaymentIndicatorEmpty()
        && outMigrationTableEntry != null) {
      calculationContext.setOutMigrationAdjustment(outMigrationTableEntry.getAdjustmentFactor());
    }
  }
}
