package gov.cms.fiss.pricers.ltch.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.api.v2.LtchPaymentData;
import gov.cms.fiss.pricers.ltch.core.LtchPricerContext;
import gov.cms.fiss.pricers.ltch.core.codes.ErrorCode;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Used when there is an error code. Initializes API output, LtchPaymentData, to keep the return
 * code but initialize all other fields if there was an error
 */
public class SetErrorPaymentData
    implements CalculationRule<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {

  @Override
  public void calculate(LtchPricerContext calculationContext) {
    final BigDecimal chargeThreshold =
        calculationContext.getPaymentData().getChargeThresholdAmount();
    if (ErrorCode.isErrorCode(calculationContext.getReturnCode())) {
      final LtchPaymentData paymentData = new LtchPaymentData();
      paymentData.setTotalPayment(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
      paymentData.setChargeThresholdAmount(chargeThreshold);
      paymentData.setBlendYear(1);
      paymentData.setBudgetNeutralityRate(BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP));
      paymentData.setCostOfLivingAdjustmentPercent(
          BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP));
      paymentData.setLengthOfStay(0);
      paymentData.setLifetimeReserveDaysUsed(0);
      paymentData.setNationalLaborPercent(BigDecimal.ZERO.setScale(5, RoundingMode.HALF_UP));
      paymentData.setNationalNonLaborPercent(BigDecimal.ZERO.setScale(5, RoundingMode.HALF_UP));
      paymentData.setFinalWageIndex(BigDecimal.ZERO.setScale(5, RoundingMode.HALF_UP));
      calculationContext.setPaymentData(paymentData);
    }
  }
}
