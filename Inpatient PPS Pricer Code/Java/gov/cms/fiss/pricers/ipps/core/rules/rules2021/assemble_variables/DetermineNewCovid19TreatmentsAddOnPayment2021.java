package gov.cms.fiss.pricers.ipps.core.rules.rules2021.assemble_variables;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimData;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.tables.ClaimCodeType;
import gov.cms.fiss.pricers.ipps.core.tables.DataTables;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

/**
 * Determine the claim's eligibility for an adjustment to the Operating Base DRG Payment and the New
 * Tech Add-On Payment based on a COVID-19 diagnosis/procedure/condition code. A unique calculated
 * factor is effective as of 11/02/2020, 11/19/2020, or 01/01/2021.
 *
 * <p>Converted from {@code 3650-NEW-COVID19-ADD-ON-PAY} in the COBOL code.
 *
 * @since 2021
 */
public class DetermineNewCovid19TreatmentsAddOnPayment2021
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  /**
   * Converted from {@code 3650-NEW-COVID19-ADD-ON-PAY} in the COBOL code.
   *
   * @param calculationContext the current pricing context
   */
  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final DataTables dataTables = calculationContext.getDataTables();
    final IppsClaimData claimData = calculationContext.getClaimData();

    //    NEW COVID-19 TREATMENTS ADD-ON PAYMENT (NCTAP)
    //        ---------------------------------------------------------------*
    //
    //    MOVE 'N' TO NCTAP-ADD-ON-FLAG.
    //    MOVE 1 TO IDX-COVID-DIAG.
    //    MOVE 1 TO IDX-COVID-PROC.
    //    MOVE 1 TO IDX-COVID-COND.
    //    MOVE ZEROES TO NCTAP-ADD-ON.
    //
    //    PERFORM 10000-COVID19-DIAG-FLAG THRU 10000-EXIT
    //    VARYING IDX-COVID-DIAG FROM 1 BY 1 UNTIL IDX-COVID-DIAG > 25.
    //
    //    PERFORM 10050-COVID19-PROC-FLAG THRU 10050-EXIT
    //    VARYING IDX-COVID-PROC FROM 1 BY 1 UNTIL IDX-COVID-PROC > 25.
    //
    //    PERFORM 10100-COVID19-COND-FLAG THRU 10100-EXIT
    //    VARYING IDX-COVID-COND FROM 1 BY 1 UNTIL IDX-COVID-COND > 5.
    //
    //    IF B-DISCHARGE-DATE > 20201101 AND
    //       B-DISCHARGE-DATE < 20201119
    //       IF DIAG-COVID2-FLAG = 'Y' AND
    //          PROC-COVID1-FLAG = 'Y' AND
    //          COND-COVID1-FLAG NOT = 'Y'
    //             MOVE 'Y' TO NCTAP-ADD-ON-FLAG.
    //
    //    IF B-DISCHARGE-DATE > 20201118 AND
    //       B-DISCHARGE-DATE < 20210101
    //       IF DIAG-COVID2-FLAG = 'Y' AND
    //          (PROC-COVID1-FLAG = 'Y' OR
    //           PROC-COVID2-FLAG = 'Y') AND
    //          COND-COVID1-FLAG NOT = 'Y'
    //             MOVE 'Y' TO NCTAP-ADD-ON-FLAG.
    //
    //    IF B-DISCHARGE-DATE > 20201231
    //       IF DIAG-COVID2-FLAG = 'Y' AND
    //          (PROC-COVID1-FLAG = 'Y' OR
    //           PROC-COVID3-FLAG = 'Y') AND
    //          COND-COVID1-FLAG NOT = 'Y'
    //             MOVE 'Y' TO NCTAP-ADD-ON-FLAG.
    //
    //    IF NCTAP-ADD-ON-FLAG = 'Y'
    //       PERFORM 10400-NCTAP-ADD-ON THRU 10400-EXIT.

    final boolean isNctapEarlyNov2020 =
        dataTables.codesMatch(
                IppsPricerContext.CODE_COVID1, ClaimCodeType.PROC, claimData.getProcedureCodes())
            && LocalDateUtils.inRange(
                calculationContext.getDischargeDate(),
                LocalDate.of(2020, 11, 2),
                LocalDate.of(2020, 11, 18));

    final boolean isNctapRemainder2020 =
        (dataTables.codesMatch(
                    IppsPricerContext.CODE_COVID1,
                    ClaimCodeType.PROC,
                    claimData.getProcedureCodes())
                || dataTables.codesMatch(
                    IppsPricerContext.CODE_COVID2,
                    ClaimCodeType.PROC,
                    claimData.getProcedureCodes()))
            && LocalDateUtils.inRange(
                calculationContext.getDischargeDate(),
                LocalDate.of(2020, 11, 19),
                LocalDate.of(2020, 12, 31));

    final boolean isNctap2021 =
        (dataTables.codesMatch(
                    IppsPricerContext.CODE_COVID1,
                    ClaimCodeType.PROC,
                    claimData.getProcedureCodes())
                || dataTables.codesMatch(
                    IppsPricerContext.CODE_COVID3,
                    ClaimCodeType.PROC,
                    claimData.getProcedureCodes()))
            && LocalDateUtils.isAfterOrEqual(
                calculationContext.getDischargeDate(), LocalDate.of(2021, 1, 1));

    if (dataTables.codesMatch(
            IppsPricerContext.CODE_COVID2, ClaimCodeType.DIAG, claimData.getDiagnosisCodes())
        && !dataTables.codesMatch(
            IppsPricerContext.CODE_COVID1, ClaimCodeType.COND, claimData.getConditionCodes())
        && (isNctapEarlyNov2020 || isNctapRemainder2020 || isNctap2021)) {
      newCovid19TreatmentsAddOnPaymentValue(calculationContext);
    }

    //    COMPUTE H-OPER-BASE-DRG-PAY ROUNDED =
    //        H-OPER-BASE-DRG-PAY + NCTAP-ADD-ON.
    calculationContext.setOperatingBaseDrgPayment(
        calculationContext
            .getOperatingBaseDrgPayment()
            .add(calculationContext.getNewCovid19TreatmentsAddOnPayment())
            .setScale(2, RoundingMode.HALF_UP));

    //    COMPUTE H-NEW-TECH-PAY-ADD-ON ROUNDED =
    //        H-NEW-TECH-PAY-ADD-ON + NCTAP-ADD-ON.
    calculationContext.setNewTechAddOnPayment(
        calculationContext
            .getNewTechAddOnPayment()
            .add(calculationContext.getNewCovid19TreatmentsAddOnPayment())
            .setScale(2, RoundingMode.HALF_UP));
  }

  protected void newCovid19TreatmentsAddOnPaymentValue(IppsPricerContext calculationContext) {

    //      MOVE 0 TO H-LESSER-STOP-1
    //                H-LESSER-STOP-2.
    //
    //      COMPUTE H-LESSER-STOP-1 ROUNDED =
    //          H-OPER-DOLLAR-THRESHOLD * 0.65.
    //
    //      COMPUTE H-LESSER-STOP-2 ROUNDED =
    //          (H-OPER-BILL-COSTS - (H-OPER-COST-OUTLIER -
    //              H-OPER-DOLLAR-THRESHOLD)) * 0.65.

    final BigDecimal lesserNCTAP1 =
        calculationContext.getOperatingDollarThreshold().multiply(new BigDecimal("0.65"));

    final BigDecimal lesserNCTAP2 =
        calculationContext
            .getOperatingBillCosts()
            .subtract(
                calculationContext
                    .getOperatingCostOutlier()
                    .subtract(calculationContext.getOperatingDollarThreshold()))
            .multiply(new BigDecimal("0.65"));

    //      IF H-OPER-BILL-COSTS >
    //          (H-OPER-COST-OUTLIER - H-OPER-DOLLAR-THRESHOLD)
    //          IF H-LESSER-STOP-1 < H-LESSER-STOP-2
    //              MOVE H-LESSER-STOP-1 TO NCTAP-ADD-ON
    //          ELSE
    //              MOVE H-LESSER-STOP-2 TO NCTAP-ADD-ON
    //      ELSE
    //          MOVE ZEROES TO NCTAP-ADD-ON.
    //
    //      MOVE 0 TO H-LESSER-STOP-1
    //                H-LESSER-STOP-2.

    if (BigDecimalUtils.isGreaterThan(
        calculationContext.getOperatingBillCosts(),
        calculationContext
            .getOperatingCostOutlier()
            .subtract(calculationContext.getOperatingDollarThreshold()))) {
      if (BigDecimalUtils.isLessThan(lesserNCTAP1, lesserNCTAP2)) {
        calculationContext.setNewCovid19TreatmentsAddOnPayment(lesserNCTAP1);
      } else {
        calculationContext.setNewCovid19TreatmentsAddOnPayment(lesserNCTAP2);
      }
    }
  }
}
