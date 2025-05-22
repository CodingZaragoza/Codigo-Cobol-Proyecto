package gov.cms.fiss.pricers.ipf.core.rules.calculate_payment.calculate_outlier;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipf.api.v2.AdditionalVariableData;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimData;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.api.v2.IpfPaymentData;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;
import gov.cms.fiss.pricers.ipf.core.codes.ReturnCode;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Calculate the outlier data.
 *
 * <p>Converted from {@code 3050-GET-OUTLIER} in the COBOL code.
 */
public class CalculateOutlierPerDiem
    implements CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {

  @Override
  public void calculate(IpfPricerContext calculationContext) {
    final IpfClaimData claimData = calculationContext.getClaimData();
    final AdditionalVariableData additionalVariables = calculationContext.getAdditionalVariables();
    final IpfPaymentData paymentData = calculationContext.getPaymentData();

    final BigDecimal outlierThreshold =
        BigDecimalUtils.defaultValue(
            additionalVariables.getOutlierThresholdAmount(), BigDecimal.ZERO);
    //      COMPUTE IPF-OUTL-LABOR-BASE-AMT ROUNDED =
    //                 ((IPF-OUTL-THRES-AMT * IPF-LABOR-SHARE) *
    //                      W-CBSA-WAGE-INDEX).
    final BigDecimal outlierLabor =
        outlierThreshold
            .multiply(calculationContext.getLaborShare())
            .multiply(calculationContext.getCbsaWageIndexEntry().getGeographicWageIndex())
            .setScale(5, RoundingMode.HALF_UP);
    additionalVariables.setOutlierBaseLaborAmount(outlierLabor);

    //      COMPUTE IPF-OUTL-NLABOR-BASE-AMT ROUNDED =
    //                 ((IPF-OUTL-THRES-AMT * IPF-NLABOR-SHARE) *
    //                      IPF-COLA).
    final BigDecimal outlierNonLabor =
        outlierThreshold
            .multiply(calculationContext.getNonLaborShare())
            .multiply(paymentData.getCostOfLivingAdjustmentPercent())
            .setScale(5, RoundingMode.HALF_UP);
    additionalVariables.setOutlierBaseNonLaborAmount(outlierNonLabor);

    //      COMPUTE IPF-OUTL-THRES-ADJ-AMT ROUNDED =
    //            ((IPF-OUTL-LABOR-BASE-AMT +
    //              IPF-OUTL-NLABOR-BASE-AMT) *
    //              WS-IPF-GEO-RURAL-ADJ *
    //              IPF-TEACH-ADJ) +
    //              IPF-FED-PAYMENT +
    //              IPF-ECT-PAYMENT.
    //
    // **  NOTE> IPF-FED-PAYMENT CONTAINS NO ECT OR OUTL PAYMENT
    // **           AT THIS POINT IN THE PROGRAM LOGIC
    final BigDecimal outlierThresholdAdjustedAmount =
        outlierLabor
            .add(outlierNonLabor)
            .multiply(paymentData.getRuralAdjustmentPercent())
            .multiply(paymentData.getTeachingAdjustmentPercent())
            .add(additionalVariables.getFederalPayment())
            .add(additionalVariables.getElectroConvulsiveTherapyPayment())
            .setScale(2, RoundingMode.HALF_UP);
    additionalVariables.setOutlierThresholdAdjustedAmount(outlierThresholdAdjustedAmount);

    // ************************************
    // ** CALCULATE ELIGIBLE OUTLIER COSTS
    // ************************************
    //      MOVE P-NEW-OPER-CSTCHG-RATIO TO IPF-CSTCHG-RATIO.
    //      COMPUTE IPF-OUTL-COST ROUNDED =
    //              (BILL-CHARGES-CLAIMED * P-NEW-OPER-CSTCHG-RATIO).
    final BigDecimal costToChargeRatio =
        calculationContext.getProviderData().getOperatingCostToChargeRatio();

    paymentData.setCostToChargeRatio(costToChargeRatio);

    final BigDecimal outlierCost =
        claimData.getCoveredCharges().multiply(costToChargeRatio).setScale(2, RoundingMode.HALF_UP);
    additionalVariables.setOutlierCost(outlierCost);

    //      MOVE '02' TO IPF-RTC.
    calculationContext.applyReturnCode(ReturnCode.OUTLIER_2);

    //      IF IPF-OUTL-COST < IPF-OUTL-THRES-ADJ-AMT
    //         MOVE '00' TO IPF-RTC
    //         MOVE ZEROES TO IPF-OUTLIER-PAYMENT
    //         GO TO 3050-EXIT.
    if (BigDecimalUtils.isLessThan(outlierCost, outlierThresholdAdjustedAmount)) {
      additionalVariables.setOutlierPayment(BigDecimal.ZERO.setScale(2, RoundingMode.UNNECESSARY));
      calculationContext.applyReturnCode(ReturnCode.NORMAL_PAYMENT_0);
      return;
    }

    //      COMPUTE IPF-OUTL-ADJ-COST ROUNDED =
    //              (IPF-OUTL-COST - IPF-OUTL-THRES-ADJ-AMT).
    // TODO: for DDS - check if it is ever POSSIBLE for this subtraction to be negative
    final BigDecimal outlierAdjustedCost =
        outlierCost.subtract(outlierThresholdAdjustedAmount).abs();
    additionalVariables.setOutlierAdjustedCost(outlierAdjustedCost);

    //      COMPUTE IPF-OUTL-PER-DIEM-AMT ROUNDED =
    //             (IPF-OUTL-ADJ-COST / BILL-LOS).
    final BigDecimal outlierPerDiem =
        outlierAdjustedCost.divide(
            new BigDecimal(claimData.getLengthOfStay()), 2, RoundingMode.HALF_UP);
    additionalVariables.setOutlierPerDiemAmount(outlierPerDiem);
  }
}
