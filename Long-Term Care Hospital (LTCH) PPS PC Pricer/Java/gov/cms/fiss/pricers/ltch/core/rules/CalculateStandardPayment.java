package gov.cms.fiss.pricers.ltch.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.api.v2.LtchPaymentData;
import gov.cms.fiss.pricers.ltch.core.LtchPricerContext;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class CalculateStandardPayment
    implements CalculationRule<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {

  @Override
  public boolean shouldExecute(LtchPricerContext calculationContext) {
    return calculationContext.isPaymentStandardOrBlend();
  }

  /**
   *
   *
   * <pre>
   * *-------------------------------------------------------------*
   * * CALCULATE FULL STANDARD DRG ADJUSTED PAYMENT                *
   * *-------------------------------------------------------------*
   * </pre>
   *
   * <p>Converted from {@code 3200-CALC-STANDARD-PMT}.
   */
  @Override
  public void calculate(LtchPricerContext calculationContext) {
    final LtchPaymentData paymentData = calculationContext.getPaymentData();

    calculationContext.setIppsComparablePayment(Boolean.TRUE);
    // COMPUTE H-LABOR-PORTION ROUNDED =
    //         (PPS-STD-FED-RATE * PPS-NAT-LABOR-PCT)
    //          * PPS-WAGE-INDEX.
    calculationContext.setHoldLaborPortionRounded(
        paymentData
            .getFederalRatePercent()
            .multiply(paymentData.getNationalLaborPercent())
            .multiply(paymentData.getFinalWageIndex())
            .setScale(6, RoundingMode.HALF_UP));

    // COMPUTE H-NONLABOR-PORTION ROUNDED =
    //         (PPS-STD-FED-RATE * PPS-NAT-NONLABOR-PCT)
    //          * PPS-COLA.
    calculationContext.setHoldNonLaborPortionRounded(
        paymentData
            .getFederalRatePercent()
            .multiply(paymentData.getNationalNonLaborPercent())
            .multiply(paymentData.getCostOfLivingAdjustmentPercent())
            .setScale(6, RoundingMode.HALF_UP));

    // COMPUTE PPS-FED-PAY-AMT ROUNDED =
    //         (H-LABOR-PORTION + H-NONLABOR-PORTION).
    paymentData.setFederalPayment(
        calculationContext
            .getHoldLaborPortionRounded()
            .add(calculationContext.getHoldNonLaborPortionRounded())
            .setScale(2, RoundingMode.HALF_UP));

    // COMPUTE PPS-DRG-ADJ-PAY-AMT ROUNDED =
    //         (PPS-FED-PAY-AMT * PPS-RELATIVE-WGT).
    paymentData.setAdjustedPayment(
        paymentData
            .getFederalPayment()
            .multiply(paymentData.getDrgRelativeWeight())
            .setScale(2, RoundingMode.HALF_UP));
    // COMPUTE H-SSOT ROUNDED = (PPS-AVG-LOS / 6) * 5.
    calculationContext.setHoldShortStayOutlierThreshold(
        paymentData
            .getAverageLengthOfStay()
            .divide(new BigDecimal("6"), new MathContext(10))
            .multiply(new BigDecimal("5"))
            .setScale(1, RoundingMode.HALF_UP));
    // TODO: Comment 1.5yr out-of-date. add the following for Web pricer?
    // * FOR WEB PRICER: RETAIN DRG UNADJUSTED PMT AMT FOR DISPLAY
    //     MOVE PPS-DRG-ADJ-PAY-AMT TO H-PPS-DRG-UNADJ-PAY-AMT.
  }
}
