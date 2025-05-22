package gov.cms.fiss.pricers.esrd.core.rules.bundled_factors;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Calculates the bundled PPS final payment rate.
 *
 * <pre>
 * *****************************************************************
 * **  Calculate BUNDLED ESRD PPS Final Payment Rate             ***
 * *****************************************************************
 * </pre>
 *
 * <p>Converted from {@code 2000-CALCULATE-BUNDLED-FACTORS} (continued) in the COBOL code.
 *
 * @since 2020
 */
public class CalculateBundledPpsFinalPaymentRate
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();

    //     IF (B-COND-CODE = '74')  AND
    //        (B-REV-CODE = '0841' OR '0851')  THEN
    //           COMPUTE H-PPS-FINAL-PAY-AMT  ROUNDED  =
    //                           H-CC-74-PER-DIEM-AMT
    //           COMPUTE H-FULL-CLAIM-AMT  ROUNDED  =
    //              (H-BUN-ADJUSTED-BASE-WAGE-AMT *
    //              ((B-CLAIM-NUM-DIALYSIS-SESSIONS) * 3) / 7)
    //     ELSE COMPUTE H-PPS-FINAL-PAY-AMT  ROUNDED  =
    //                  H-BUN-ADJUSTED-BASE-WAGE-AMT  +
    //                  H-BUN-WAGE-ADJ-TRAINING-AMT
    //     END-IF.
    if (calculationContext.isPerDiemClaim()) {
      calculationContext.setFinalPaymentAmount(
          calculationContext.getConditionCode74PerDiemAmount().setScale(2, RoundingMode.HALF_UP));

      calculationContext.setFullClaimAmount(
          calculationContext
              .getBundledAdjustedBaseWageAmount()
              .multiply(
                  new BigDecimal(claimData.getDialysisSessionCount()).multiply(new BigDecimal(3)))
              .divide(new BigDecimal(7), 2, RoundingMode.DOWN));
    } else {
      calculationContext.setFinalPaymentAmount(
          calculationContext
              .getBundledAdjustedBaseWageAmount()
              .add(calculationContext.getBundledWageAdjustedTrainingAmount())
              .setScale(2, RoundingMode.HALF_UP));
    }
  }
}
