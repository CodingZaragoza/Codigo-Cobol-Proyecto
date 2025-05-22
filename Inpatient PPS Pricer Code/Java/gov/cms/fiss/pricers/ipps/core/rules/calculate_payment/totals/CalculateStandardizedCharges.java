package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.totals;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimData;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.CbsaReference;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Determine operating and capital standardized costs and thresholds.
 *
 * <p>Converted from {@code 9010-CALC-STANDARD-CHG} in the COBOL code.
 *
 * @since 2019
 */
public class CalculateStandardizedCharges
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final BigDecimal operBillStdzCosts = calculateOperatingBillStdCost(calculationContext);
    final BigDecimal capiBillStdzCosts = calculateCapitalBillStdCost(calculationContext);
    final BigDecimal operStdzDollarThreshold = calculateOperatingStdThreshold(calculationContext);

    final BigDecimal capiStdzDollarThreshold = calculateCapitalStdThreshold(calculationContext);

    final BigDecimal stdzCosts = operBillStdzCosts.add(capiBillStdzCosts);
    final BigDecimal stdzThreshold = operStdzDollarThreshold.add(capiStdzDollarThreshold);
    BigDecimal operStdzCostOutlier = BigDecimal.ZERO;
    BigDecimal capiStdzCostOutlier = BigDecimal.ZERO;

    if (BigDecimalUtils.isGreaterThan(stdzCosts, stdzThreshold)) {
      // *****************************************************
      // **[CM-P3] STANDARDIZED OPERATING OUTLIER CALCULATION
      //     IF (H-OPER-BILL-STDZ-COSTS + H-CAPI-BILL-STDZ-COSTS) >
      //        (H-OPER-STDZ-DOLLAR-THRESHOLD +
      //                           H-CAPI-STDZ-DOLLAR-THRESHOLD)
      //                          AND
      //         H-OPER-BILL-STDZ-COSTS > H-OPER-STDZ-DOLLAR-THRESHOLD
      //       COMPUTE  H-OPER-STDZ-COST-OUTLIER ROUNDED =
      //        (H-CSTOUT-PCT  *
      //        (H-OPER-BILL-STDZ-COSTS - H-OPER-STDZ-DOLLAR-THRESHOLD))
      //     ELSE
      //       MOVE 0 TO H-OPER-STDZ-COST-OUTLIER.
      if (BigDecimalUtils.isGreaterThan(operBillStdzCosts, operStdzDollarThreshold)) {
        operStdzCostOutlier =
            operBillStdzCosts
                .subtract(operStdzDollarThreshold)
                .multiply(calculationContext.getCostOutlierPct())
                .setScale(9, RoundingMode.HALF_UP);
      }

      // *****************************************************
      // **[CM-P3] STANDARDIZED CAPITAL OUTLIER CALCULATION
      //     IF (H-OPER-BILL-STDZ-COSTS + H-CAPI-BILL-STDZ-COSTS) >
      //        (H-OPER-STDZ-DOLLAR-THRESHOLD +
      //                           H-CAPI-STDZ-DOLLAR-THRESHOLD)
      //                          AND
      //         H-CAPI-BILL-STDZ-COSTS > H-CAPI-STDZ-DOLLAR-THRESHOLD
      //      COMPUTE  H-CAPI-STDZ-COST-OUTLIER ROUNDED =
      //      (H-CSTOUT-PCT  *
      //      (H-CAPI-BILL-STDZ-COSTS - H-CAPI-STDZ-DOLLAR-THRESHOLD))
      //     ELSE
      //      MOVE 0 TO H-CAPI-STDZ-COST-OUTLIER.
      if (BigDecimalUtils.isGreaterThan(capiBillStdzCosts, capiStdzDollarThreshold)) {
        capiStdzCostOutlier =
            calculationContext
                .getCostOutlierPct()
                .multiply(capiBillStdzCosts.subtract(capiStdzDollarThreshold))
                .setScale(9, RoundingMode.HALF_UP);
      }
    }

    // ******************************************************
    // **[CM-P3] STANDARDIZED ALLOWED AMOUNT CALCULATION
    //      COMPUTE H-STANDARD-ALLOWED-AMOUNT ROUNDED =
    //       (H-OPER-BASE + H-CAPI-BASE)
    //                 *
    //       H-DRG-WT-FRCTN
    //                 +
    //       H-OPER-STDZ-COST-OUTLIER
    //                 +
    //       H-CAPI-STDZ-COST-OUTLIER
    //                 +
    //       H-NEW-TECH-PAY-ADD-ON.
    final BigDecimal baseCalc =
        calculationContext
            .getOperatingBaseRate()
            .add(calculationContext.getCapitalBaseRate())
            .multiply(calculationContext.getDrgWeightFraction());
    calculationContext.setStandardAllowedAmount(
        baseCalc
            .add(
                operStdzCostOutlier.add(
                    capiStdzCostOutlier.add(calculationContext.getNewTechAddOnPayment())))
            .setScale(2, RoundingMode.HALF_UP));
  }

  protected BigDecimal calculateOperatingBillStdCost(IppsPricerContext calculationContext) {
    final CbsaReference cbsaReference = calculationContext.getCbsaReference();
    final IppsClaimData claimData = calculationContext.getClaimData();

    // **********************************************************
    // **[CM-P3] STANDARDIZED OPERATING COST CALCULATION
    //     IF ((H-LABOR-PCT * H-WAGE-INDEX) +
    //               (H-NONLABOR-PCT * H-OPER-COLA)) > 0
    //        COMPUTE  H-OPER-BILL-STDZ-COSTS ROUNDED =
    //        (B-CHARGES-CLAIMED * H-OPER-CSTCHG-RATIO) /
    //        ((H-LABOR-PCT * H-WAGE-INDEX) +
    //               (H-NONLABOR-PCT * H-OPER-COLA))
    //     ELSE MOVE 0 TO H-OPER-BILL-STDZ-COSTS.
    if (BigDecimalUtils.isGreaterThanZero(
        calculationContext
            .getNationalLaborPct()
            .multiply(cbsaReference.getWageIndex())
            .add(
                calculationContext
                    .getNationalNonLaborPct()
                    .multiply(calculationContext.getOperatingCostOfLivingAdjustment())))) {
      return claimData
          .getCoveredCharges()
          .multiply(calculationContext.getOperatingCostToChargeRatio())
          .divide(
              calculationContext
                  .getNationalLaborPct()
                  .multiply(cbsaReference.getWageIndex())
                  .add(
                      calculationContext
                          .getNationalNonLaborPct()
                          .multiply(calculationContext.getOperatingCostOfLivingAdjustment())),
              2,
              RoundingMode.HALF_UP);
    }

    return BigDecimal.ZERO;
  }

  protected BigDecimal calculateCapitalBillStdCost(IppsPricerContext calculationContext) {
    // **********************************************************
    // **[CM-P3] STANDARDIZED CAPITAL COST CALCULATION
    //     IF (H-CAPI-GAF * H-CAPI-COLA) > 0
    //       COMPUTE  H-CAPI-BILL-STDZ-COSTS ROUNDED =
    //        (B-CHARGES-CLAIMED * H-CAPI-CSTCHG-RATIO) /
    //               (H-CAPI-GAF * H-CAPI-COLA)
    //     ELSE MOVE 0 TO H-CAPI-BILL-STDZ-COSTS.
    if (BigDecimalUtils.isGreaterThanZero(
        calculationContext
            .getCapitalGeographicAdjFactor()
            .multiply(calculationContext.getCapitalCostOfLivingAdjustment()))) {
      return calculationContext
          .getClaimData()
          .getCoveredCharges()
          .multiply(calculationContext.getCapitalOperatingCostToChargeRatio())
          .divide(
              calculationContext
                  .getCapitalGeographicAdjFactor()
                  .multiply(calculationContext.getCapitalCostOfLivingAdjustment()),
              2,
              RoundingMode.HALF_UP);
    }

    return BigDecimal.ZERO;
  }

  protected BigDecimal calculateOperatingStdThreshold(IppsPricerContext calculationContext) {
    // **********************************************************
    // **[CM-P3] STANDARDIZED OPERATING THRESHOLD
    //     MOVE 5646.08 TO H-OPER-BASE.
    //     COMPUTE   H-OPER-STDZ-DOLLAR-THRESHOLD ROUNDED =
    //      (H-CST-THRESH * H-OPER-SHARE-DOLL-THRESHOLD)  +
    //                        +
    //           (H-OPER-BASE * H-DRG-WT-FRCTN)
    //                        +
    //              H-NEW-TECH-PAY-ADD-ON.
    final BigDecimal threshold =
        calculationContext
            .getOperatingShareDollarThreshold()
            .multiply(calculationContext.getCostThreshold());
    final BigDecimal baseDRG =
        calculationContext
            .getOperatingBaseRate()
            .multiply(calculationContext.getDrgWeightFraction());

    return threshold
        .add(baseDRG)
        .add(calculationContext.getNewTechAddOnPayment())
        .setScale(9, RoundingMode.HALF_UP);
  }

  protected BigDecimal calculateCapitalStdThreshold(IppsPricerContext calculationContext) {
    // *****************************************************
    // **[CM-P3] STANDARDIZED CAPITAL THRESHOLD
    //     MOVE 459.41 TO H-CAPI-BASE.
    //     COMPUTE   H-CAPI-STDZ-DOLLAR-THRESHOLD ROUNDED =
    //     (H-CST-THRESH * H-CAPI-SHARE-DOLL-THRESHOLD)
    //                     +
    //     (H-CAPI-BASE * H-DRG-WT-FRCTN).
    return calculationContext
        .getCapitalShareDollarThreshold()
        .multiply(calculationContext.getCostThreshold())
        .add(
            calculationContext
                .getCapitalBaseRate()
                .multiply(calculationContext.getDrgWeightFraction()))
        .setScale(2, RoundingMode.HALF_UP);
  }
}
