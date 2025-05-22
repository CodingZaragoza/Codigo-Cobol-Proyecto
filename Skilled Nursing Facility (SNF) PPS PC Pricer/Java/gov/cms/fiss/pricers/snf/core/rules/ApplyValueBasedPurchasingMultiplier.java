package gov.cms.fiss.pricers.snf.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingRequest;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingResponse;
import gov.cms.fiss.pricers.snf.core.SnfPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ApplyValueBasedPurchasingMultiplier
    implements CalculationRule<SnfClaimPricingRequest, SnfClaimPricingResponse, SnfPricerContext> {

  /**
   * Applies the VBP factor.
   *
   * <p>Converted from {@code 1000-APPLY-VBP-MULTIPLIER} in the COBOL code.
   */
  @Override
  public void calculate(SnfPricerContext context) {

    // ****--------***-------------------*
    // ****--------*** SNF PAYMENT RATE -*
    // ****--------***-------------------*
    // ****--------*STEP # 11
    //
    //      COMPUTE SNF-PAYMENT-RATE ROUNDED =
    //              VBP-MULTIPLIER *  TOTAL-CALC-PAYMENT-RATE.

    final BigDecimal multiplier = context.getProviderData().getVbpAdjustment();
    context.setPaymentRate(
        multiplier
            .multiply(context.getTotalCalculatedPaymentRate())
            .setScale(2, RoundingMode.HALF_UP));

    // **-----------**---------------*
    // ****--------*** VBP PAY DIFF -*
    // ****--------***---------------*
    // ****--------*STEP # 12
    //
    //      COMPUTE VBP-PAY-DIFF ROUNDED =
    //              SNF-PAYMENT-RATE - TOTAL-CALC-PAYMENT-RATE.
    context.setPaymentDifference(
        context.getPaymentRate().subtract(context.getTotalCalculatedPaymentRate()));
  }
}
