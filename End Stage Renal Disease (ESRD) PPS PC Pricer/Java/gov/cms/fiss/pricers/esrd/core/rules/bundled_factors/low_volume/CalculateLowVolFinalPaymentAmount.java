package gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.low_volume;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.RoundingMode;

/**
 * Calculates the bundled wage-adjusted training amount.
 *
 * <p>Converted from {@code 3000-LOW-VOL-FULL-PPS-PAYMENT} in the COBOL code.
 *
 * @since 2020
 */
public class CalculateLowVolFinalPaymentAmount
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();

    //     IF (B-COND-CODE = '74')  AND
    //        (B-REV-CODE = '0841' OR '0851')  THEN
    //           COMPUTE H-LV-PPS-FINAL-PAY-AMT  ROUNDED  =
    //                           H-CC-74-PER-DIEM-AMT
    //     ELSE
    //        COMPUTE H-LV-PPS-FINAL-PAY-AMT  ROUNDED  =
    //                H-LV-BUN-ADJUST-BASE-WAGE-AMT +
    //                H-BUN-WAGE-ADJ-TRAINING-AMT
    //     END-IF.
    if (calculationContext.isPerDiemClaim()) {
      calculationContext.setLowVolumeFinalPaymentAmount(
          calculationContext.getConditionCode74PerDiemAmount());
    } else {
      calculationContext.setLowVolumeFinalPaymentAmount(
          calculationContext
              .getLowVolumeBundledAdjustedBaseWageAmount()
              .add(calculationContext.getBundledWageAdjustedTrainingAmount())
              .setScale(4, RoundingMode.HALF_UP));
    }
  }
}
