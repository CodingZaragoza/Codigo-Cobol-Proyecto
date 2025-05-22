package gov.cms.fiss.pricers.ipps.core.rules.rules2022.assemble_variables;

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
public class DetermineNewCovid19TreatmentsAddOnPayment2022
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  private static final String CODE_PAXLOVID1 = "PAXLOVID1";
  private static final String CODE_PAXLOVID2 = "PAXLOVID2";
  private static final String CODE_PAXLOVID3 = "PAXLOVID3";
  private static final String CODE_MOLNUPIRAVIR = "MOLNUPIRAVIR";
  /**
   * Converted from {@code 3650-NEW-COVID19-ADD-ON-PAY} in the COBOL code.
   *
   * @param calculationContext the current pricing context
   */
  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final DataTables dataTables = calculationContext.getDataTables();
    final IppsClaimData claimData = calculationContext.getClaimData();

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

    final boolean isNctapEuas =
        dataTables.codesMatch(CODE_PAXLOVID1, ClaimCodeType.NDC, claimData.getNationalDrugCodes())
                && LocalDateUtils.isAfterOrEqual(
                    calculationContext.getDischargeDate(), LocalDate.of(2021, 12, 22))
            || dataTables.codesMatch(
                    CODE_PAXLOVID2, ClaimCodeType.NDC, claimData.getNationalDrugCodes())
                && LocalDateUtils.isAfterOrEqual(
                    calculationContext.getDischargeDate(), LocalDate.of(2022, 3, 17))
            || dataTables.codesMatch(
                    CODE_PAXLOVID3, ClaimCodeType.NDC, claimData.getNationalDrugCodes())
                && LocalDateUtils.isAfterOrEqual(
                    calculationContext.getDischargeDate(), LocalDate.of(2022, 4, 18))
            || dataTables.codesMatch(
                    CODE_MOLNUPIRAVIR, ClaimCodeType.NDC, claimData.getNationalDrugCodes())
                && LocalDateUtils.isAfterOrEqual(
                    calculationContext.getDischargeDate(), LocalDate.of(2021, 12, 23));

    if (dataTables.codesMatch(
            IppsPricerContext.CODE_COVID2, ClaimCodeType.DIAG, claimData.getDiagnosisCodes())
        && !dataTables.codesMatch(
            IppsPricerContext.CODE_COVID1, ClaimCodeType.COND, claimData.getConditionCodes())
        && (isNctap2021 || isNctapEuas)) {
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
    final DataTables dataTables = calculationContext.getDataTables();
    final IppsClaimData claimData = calculationContext.getClaimData();

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

    final BigDecimal lesserNCTAP2;

    if (dataTables.codesMatch("VEKLURY", ClaimCodeType.PROC, claimData.getProcedureCodes())) {
      lesserNCTAP2 =
          calculationContext
              .getOperatingBillCosts()
              .subtract(
                  calculationContext
                      .getOperatingCostOutlier()
                      .subtract(calculationContext.getOperatingDollarThreshold())
                      .subtract(new BigDecimal("2028.00")))
              .multiply(new BigDecimal("0.65"));
    } else {
      lesserNCTAP2 =
          calculationContext
              .getOperatingBillCosts()
              .subtract(
                  calculationContext
                      .getOperatingCostOutlier()
                      .subtract(calculationContext.getOperatingDollarThreshold()))
              .multiply(new BigDecimal("0.65"));
    }

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

    if (dataTables.codesMatch("VEKLURY", ClaimCodeType.PROC, claimData.getProcedureCodes())) {
      calculationContext.setNewCovid19TreatmentsAddOnPayment(
          calculationContext
              .getNewCovid19TreatmentsAddOnPayment()
              .subtract(new BigDecimal("2028.00")));
    }

    if (BigDecimalUtils.isGreaterThan(
        calculationContext.getNewCovid19TreatmentsAddOnPayment(), BigDecimal.ZERO)) {
      calculationContext.setNewCovid19TreatmentsAddOnPayment(
          calculationContext.getNewCovid19TreatmentsAddOnPayment());
    } else {
      calculationContext.setNewCovid19TreatmentsAddOnPayment(BigDecimal.ZERO);
    }
  }
}
