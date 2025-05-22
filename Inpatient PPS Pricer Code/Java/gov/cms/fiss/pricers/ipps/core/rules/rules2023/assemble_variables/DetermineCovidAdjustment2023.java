package gov.cms.fiss.pricers.ipps.core.rules.rules2023.assemble_variables;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimData;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.tables.ClaimCodeType;
import gov.cms.fiss.pricers.ipps.core.tables.DataTables;
import java.time.LocalDate;

/**
 * Determine the claim's eligibility for an adjustment based on a COVID-19 diagnosis / condition
 * code.
 *
 * <p>Converted from {@code 2700-COVID-DRG-ADJ} in the COBOL code.
 *
 * @since 2020
 */
public class DetermineCovidAdjustment2023
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final DataTables dataTables = calculationContext.getDataTables();
    final IppsClaimData claimData = calculationContext.getClaimData();

    // * ADJUSTMENT TO DRG WEIGHT PER COVID-19 DIAGNOSIS
    // *   + 20% INCREASE TO OPERATING DRG PAYMENTS
    // *----------------------------------------------------------------*
    //     MOVE 1 TO IDX-COVID.
    //     MOVE 1 TO IDX-COVID-COND.
    //     MOVE 1.0 TO COVID-ADJ.
    //
    //     PERFORM 10000-COVID19-FLAG THRU 10000-EXIT
    //      VARYING IDX-COVID FROM 1 BY 1 UNTIL IDX-COVID > 25.
    //
    //     PERFORM 10100-COVID19-COND-FLAG THRU 10100-EXIT
    //      VARYING IDX-COVID-COND FROM 1 BY 1 UNTIL IDX-COVID-COND > 5
    //
    //     IF B-DISCHARGE-DATE > 20200331
    //        IF DIAG-COVID2-FLAG = 'Y'
    //           IF COND-COVID1-FLAG = 'Y'
    //           GO TO 2700-EXIT
    //        ELSE
    //           MOVE 1.2 TO COVID-ADJ.

    // A factor of 1.2 is effective through 04/01/2022
    if (dataTables.codesMatch(
            IppsPricerContext.CODE_COVID2, ClaimCodeType.DIAG, claimData.getDiagnosisCodes())
        && !dataTables.codesMatch(
            IppsPricerContext.CODE_COVID1, ClaimCodeType.COND, claimData.getConditionCodes())
        && LocalDateUtils.isBeforeOrEqual(
            calculationContext.getDischargeDate(), LocalDate.of(2023, 5, 11))) {
      calculationContext.setCovidAdjustmentFactor(
          calculationContext.getCovidAdjustmentFactorValue());
    }
  }
}
