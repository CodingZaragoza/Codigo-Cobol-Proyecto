package gov.cms.fiss.pricers.esrd.core.rules.rules_2025;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.BundledPaymentData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import gov.cms.fiss.pricers.esrd.core.codes.ReturnCode;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.commons.lang3.StringUtils;

/**
 * Calculates the AKI claim payment.
 *
 * <p>Converted from {@code 0000-START-TO-FINISH} in the COBOL code.
 *
 * @since 2020
 */
public class CalculateAkiPayment2025
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public boolean shouldExecute(EsrdPricerContext calculationContext) {

    //        IF B-COND-CODE  = '84' THEN

    return calculationContext.isAki84()
        && calculationContext.matchesReturnCode(ReturnCode.CALCULATION_STARTED_00);
  }

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();
    // * Calculate payment for AKI claim
    //           MOVE H-BUN-BASE-WAGE-AMT TO
    //                H-PPS-FINAL-PAY-AMT

    // at facility
    if (calculationContext.isAkiFacility84()) {
      calculationContext.setFinalPaymentAmount(
          calculationContext.getBundledBaseWageAmount().setScale(2, RoundingMode.HALF_UP));
      //           MOVE '02' TO PPS-RTC
      calculationContext.applyReturnCode(ReturnCode.PAYMENT_NO_ADJUSTMENTS_02);
    }

    // at home
    if (calculationContext.isAkiHome8474()) {
      calculationContext.applyReturnCode(ReturnCode.PAYMENT_NO_ADJUSTMENTS_02);
      calculationContext.setFinalPaymentAmount(
          calculationContext.getBundledBaseWageAmount().setScale(2, RoundingMode.HALF_UP));
      // daily if applicable
      if (StringUtils.equalsAny(
          claimData.getRevenueCode(),
          EsrdPricerContext.REVENUE_CODE_CONTINUOUS_AMBULATORY_PERITONEAL_DIALYSIS_0841,
          EsrdPricerContext.REVENUE_CODE_CONTINUOUS_CYCLING_PERITONEAL_DIALYSIS_0851)) {
        calculationContext.setFinalPaymentAmount(
            calculationContext
                .getBundledBaseWageAmount()
                .multiply(new BigDecimal(3))
                .divide(new BigDecimal(7), 4, RoundingMode.DOWN));
        calculationContext.setHemoEquivalentDialysisSessions(
            new BigDecimal(claimData.getDialysisSessionCount())
                .multiply(new BigDecimal(3))
                .divide(new BigDecimal(7), 4, RoundingMode.HALF_UP));
      }
    }
    // apply training if applicable
    else if (calculationContext.isAkiTraining8473() || calculationContext.isAkiRetraining8487()) {
      calculationContext.setBundledWageAdjustedTrainingAmount(
          calculationContext
              .getTrainingAddOnPaymentAmount()
              .multiply(calculationContext.getBundledWageIndex())
              .setScale(4, RoundingMode.HALF_UP));
      calculationContext.setTrainingClaim(true);
      calculationContext.setFinalPaymentAmount(
          calculationContext
              .getBundledBaseWageAmount()
              .add(calculationContext.getBundledWageAdjustedTrainingAmount())
              .setScale(2, RoundingMode.HALF_UP));
      calculationContext.applyReturnCode(ReturnCode.TRAINING_PAYMENT_11);
    }
    //           MOVE '10' TO PPS-2011-COMORBID-PAY
    final BundledPaymentData bundledData = calculationContext.getBundledData();
    bundledData.setComorbidityPaymentCode(EsrdPricerContext.COMORBIDITY_RETURN_CODE_DEFAULT_10);
  }
}
