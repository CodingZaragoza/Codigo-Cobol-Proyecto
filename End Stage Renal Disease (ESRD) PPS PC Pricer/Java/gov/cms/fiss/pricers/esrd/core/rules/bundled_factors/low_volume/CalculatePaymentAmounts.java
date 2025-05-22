package gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.low_volume;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdPaymentData;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.RoundingMode;

/**
 * Calculates the payment amounts for low-volume recovery.
 *
 * <p>Converted from {@code 2000-CALCULATE-BUNDLED-FACTORS} (continued) in the COBOL code.
 *
 * @since 2020
 */
public class CalculatePaymentAmounts
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    //        COMPUTE H-LV-PPS-FINAL-PAY-AMT = H-LV-PPS-FINAL-PAY-AMT -
    //           H-PPS-FINAL-PAY-AMT
    calculationContext.setLowVolumeFinalPaymentAmount(
        calculationContext
            .getLowVolumeFinalPaymentAmount()
            .subtract(calculationContext.getFinalPaymentAmount())
            .setScale(4, RoundingMode.HALF_UP));

    //        COMPUTE H-LV-OUT-PAYMENT       = H-LV-OUT-PAYMENT       -
    //           H-OUT-PAYMENT
    calculationContext.setLowVolumeOutlierPayment(
        calculationContext
            .getLowVolumeOutlierPayment()
            .subtract(calculationContext.getOutlierPayment()));

    //        COMPUTE H-LV-PPS-FINAL-PAY-AMT = H-LV-PPS-FINAL-PAY-AMT +
    //           H-LV-OUT-PAYMENT
    calculationContext.setLowVolumeFinalPaymentAmount(
        calculationContext
            .getLowVolumeFinalPaymentAmount()
            .add(calculationContext.getLowVolumeOutlierPayment()));

    //        IF P-PROV-WAIVE-BLEND-PAY-INDIC = 'N'  THEN
    // ************************************************************************
    // The following block will never be executed, as the flag in question is
    // hard-coded to the equivalent of 'Y'
    // ************************************************************************
    //           COMPUTE PPS-LOW-VOL-AMT  ROUNDED =
    //              H-LV-PPS-FINAL-PAY-AMT  *  BUN-CBSA-BLEND-PCT
    //        ELSE
    // ************************************************************************
    // This block is converted below
    // ************************************************************************
    //           MOVE H-LV-PPS-FINAL-PAY-AMT TO PPS-LOW-VOL-AMT
    //        END-IF
    final EsrdPaymentData paymentData = calculationContext.getPaymentData();

    paymentData.setLowVolumeAmount(
        calculationContext.getLowVolumeFinalPaymentAmount().abs().setScale(2, RoundingMode.DOWN));
  }
}
