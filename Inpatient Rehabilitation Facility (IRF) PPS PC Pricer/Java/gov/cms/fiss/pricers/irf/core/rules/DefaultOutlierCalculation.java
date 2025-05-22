package gov.cms.fiss.pricers.irf.core.rules;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimData;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.api.v2.IrfPaymentData;
import gov.cms.fiss.pricers.irf.core.IrfPricerContext;
import gov.cms.fiss.pricers.irf.core.ResultCode;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Calculates the outlier amount.
 *
 * <p>Converted from {@code 4000-CALC-OUTLIER} in the COBOL code.
 */
public class DefaultOutlierCalculation
    implements CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  @Override
  public boolean shouldExecute(IrfPricerContext calculationContext) {
    return calculationContext.matchesReturnCode(ResultCode.OK_00);
  }

  @Override
  public void calculate(IrfPricerContext calculationContext) {
    final IrfClaimData claimData = calculationContext.getClaimData();
    final InpatientProviderData providerData = calculationContext.getProviderData();
    final IrfPaymentData paymentData = calculationContext.getPaymentData();

    // *******************
    // ** RULE: THE PPS FACILITIES COSTS ARE COMPUTED AS (THE COVERED
    // ** CHARGES ON THE BILL TIMES THE PROVIDER'S NEW OPERATING COST TO
    // ** CHARGE RATIO) ROUNDED

    // COMPUTE PPS-FAC-COSTS ROUNDED =
    //          (B-COV-CHARGES * P-NEW-OPER-CSTCHG-RATIO).
    paymentData.setFacilityCosts(
        claimData
            .getCoveredCharges()
            .multiply(providerData.getOperatingCostToChargeRatio())
            .setScale(2, RoundingMode.HALF_UP));

    // ** RULE: THE OUTLIER LABOR PORTION IS CALCULATED AS THE (PPS
    // ** NATIONAL THRESHHOLD ADJUSMENT FACTOR TIMES PPS NATIONAL LABOR
    // ** PERCENTAGE) TIMES THE PPS WAGE INDEX

    // COMPUTE H-OUTLIER-LABOR-PORTION =
    //         (PPS-NAT-THRESHOLD-ADJ * PPS-NAT-LABOR-PCT)
    //               * PPS-WAGE-INDEX.
    final BigDecimal outlierLaborPortion =
        paymentData
            .getNationalThresholdAdjustmentAmount()
            .multiply(paymentData.getNationalLaborPercent())
            .multiply(paymentData.getFinalWageIndex())
            .setScale(6, RoundingMode.HALF_UP);

    // ** RULE: THE OUTLIER NON LABOR PORTION IS CALCULATED AS THE PPS
    // ** NATIONAL THRESHHOLD ADJUSMENT FACTOR TIMES PPS NATIONAL NON
    // ** LABOR PERCENTAGE

    // COMPUTE H-OUTLIER-NONLABOR-PORTION =
    //         (PPS-NAT-THRESHOLD-ADJ * PPS-NAT-NONLABOR-PCT).
    final BigDecimal outlierNonLaborPortion =
        paymentData
            .getNationalThresholdAdjustmentAmount()
            .multiply(paymentData.getNationalNonLaborPercent())
            .setScale(6, RoundingMode.HALF_UP);

    // ** RULE: THE FP OUTLIER THRESHHOLD IS COMPUTED AS THE ((PAYMENT
    // ** OUTLIER LABOR PORTION PLUS THE PAYMENT OUTLIER NON LABOR
    // ** PORTION) TIMES THE PPS RURAL ADJUSTMENT TIMES THE (PPS LOW
    // ** INCOME PATIENT PERCENT PLUS THE TEACH PERCENT + 1)) ROUNDED.

    // COMPUTE H-FP-OUTLIER-THRESHOLD ROUNDED =
    //         ((H-OUTLIER-LABOR-PORTION + H-OUTLIER-NONLABOR-PORTION) *
    //          PPS-RURAL-ADJUSTMENT * (PPS-LIP-PCT + H-TEACH-PCT + 1)).
    final BigDecimal fpOutlierThreshold =
        outlierLaborPortion
            .add(outlierNonLaborPortion)
            .multiply(paymentData.getRuralAdjustmentPercent())
            .multiply(
                paymentData
                    .getLowIncomePaymentPercent()
                    .add(calculationContext.getTeachPercent())
                    .add(BigDecimal.ONE))
            .setScale(6, RoundingMode.HALF_UP);

    // ** RULE: THE OUTLIER THRESHHOLD IS COMPUTED AS THE (PPS FEDERAL
    // ** PAYMENT AMOUNT PLUS THE FP OUTLIER THRESHHOLD PLUS THE PPS LOW
    // ** INCOME PATIENT PAYMENT AMOUNT PLUS THE TEACH PPS PAYMENT
    // ** AMOUNT) ROUNDED.

    // COMPUTE H-OUTLIER-THRESHOLD ROUNDED =
    //         (PPS-FED-PAY-AMT + H-FP-OUTLIER-THRESHOLD +
    //          PPS-LIP-PAY-AMT + PPS-TEACH-PAY-AMT).
    final BigDecimal outlierThreshold =
        paymentData
            .getFederalPaymentAmount()
            .add(fpOutlierThreshold)
            .add(paymentData.getLowIncomePayment())
            .add(paymentData.getTeachingPayment())
            .setScale(6, RoundingMode.HALF_UP);

    // Truncate to match the scale of the output outlier threshold field (scale = 2)
    paymentData.setOutlierThresholdAmount(BigDecimalUtils.truncateDecimals(outlierThreshold));

    // ** RULE: THE PPS OUTLIER PAYMENT AMOUNT IS COMPUTED AS 80% OF
    // ** (THE PPS FACILITY COSTS MINUS THE OUTLIER THRESHHOLD) WHEN THE
    // ** PPS FACILITY COSTS ARE GREATER THAN THE OUTLIER THRESHHOLD. IN
    // ** OTHER WORDS, ONLY 80% OF THE COST DIFFERENTIAL IS PAID.

    // IF PPS-FAC-COSTS > H-OUTLIER-THRESHOLD
    if (BigDecimalUtils.isGreaterThan(paymentData.getFacilityCosts(), outlierThreshold)) {
      // COMPUTE PPS-OUTLIER-PAY-AMT ROUNDED =
      //            ((PPS-FAC-COSTS - H-OUTLIER-THRESHOLD) * .8).
      paymentData.setOutlierPayment(
          paymentData
              .getFacilityCosts()
              .subtract(outlierThreshold)
              .multiply(IrfPricerContext.EIGHTY_PERCENT)
              .setScale(2, RoundingMode.HALF_UP));
    }

    // ** RULE: THE CHARGE OUTLIER THRESHHOLD IS COMPUTED AS THE
    // ** (OUTLIER THRESHHOLD DIVIDED BY THE PROVIDER'S NEW OPERATING
    // ** COST TO CHARGE RATIO) ROUNDED.

    // COMPUTE H-CHG-OUTLIER-THRESHOLD ROUNDED =
    //          H-OUTLIER-THRESHOLD / P-NEW-OPER-CSTCHG-RATIO.
    final BigDecimal chargeOutlierThreshold =
        outlierThreshold
            .divide(providerData.getOperatingCostToChargeRatio(), RoundingMode.HALF_UP)
            .setScale(4, RoundingMode.HALF_UP);
    // Truncating since PPS-CHG-OUTLIER-THRESHOLD has scale = 2
    paymentData.setChargeOutlierThresholdAmount(
        BigDecimalUtils.truncateDecimals(chargeOutlierThreshold));
  }
}
