package gov.cms.fiss.pricers.esrd.core.rules.rules_2024;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.BundledPaymentData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import gov.cms.fiss.pricers.esrd.core.codes.ReturnCode;
import java.math.RoundingMode;

/**
 * Calculates the AKI claim payment.
 *
 * <p>Converted from {@code 0000-START-TO-FINISH} in the COBOL code.
 *
 * @since 2020
 */
public class CalculateAkiPayment2024AndEarlier
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public boolean shouldExecute(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();

    //        IF B-COND-CODE  = '84' THEN
    return calculationContext.isAki84()
        && calculationContext.matchesReturnCode(ReturnCode.CALCULATION_STARTED_00);
  }

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    // * Calculate payment for AKI claim
    //           MOVE H-BUN-BASE-WAGE-AMT TO
    //                H-PPS-FINAL-PAY-AMT
    calculationContext.setFinalPaymentAmount(
        calculationContext.getBundledBaseWageAmount().setScale(2, RoundingMode.HALF_UP));

    //           MOVE '02' TO PPS-RTC
    calculationContext.applyReturnCode(ReturnCode.PAYMENT_NO_ADJUSTMENTS_02);

    //           MOVE '10' TO PPS-2011-COMORBID-PAY
    final BundledPaymentData bundledData = calculationContext.getBundledData();
    bundledData.setComorbidityPaymentCode(EsrdPricerContext.COMORBIDITY_RETURN_CODE_DEFAULT_10);
  }
}
