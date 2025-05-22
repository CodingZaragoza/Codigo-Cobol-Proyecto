package gov.cms.fiss.pricers.hha.core.rules.calculate_value_based_purchasing;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingRequest;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingResponse;
import gov.cms.fiss.pricers.hha.api.v2.HhaPaymentData;
import gov.cms.fiss.pricers.hha.api.v2.RevenuePaymentData;
import gov.cms.fiss.pricers.hha.core.HhaPricerContext;
import gov.cms.fiss.pricers.hha.core.codes.ReturnCode;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Stream;

/**
 * Adds revenue data, if applicable.
 *
 * <p>Converted from {@code 9120-VBP-REV-COST} in the COBOL code.
 */
public class AddRevenueData
    implements CalculationRule<HhaClaimPricingRequest, HhaClaimPricingResponse, HhaPricerContext> {

  @Override
  public boolean shouldExecute(HhaPricerContext calculationContext) {
    final HhaPaymentData paymentData = calculationContext.getPaymentData();

    // * ONLY DO THIS FOR LUPA PAYMENTS
    // IF H-HHA-HRG-PAY = 0
    //    IF H-HHA-PAY-RTC = '06' OR '14'
    return BigDecimalUtils.isZero(paymentData.getHhrgPayment())
        && isLupaPayment(
            Integer.parseInt(calculationContext.getOutput().getReturnCodeData().getCode()));
  }

  @Override
  public void calculate(HhaPricerContext calculationContext) {
    // COMPUTE H-HHA-REVENUE-COST (n) ROUNDED = H-HHA-REVENUE-COST (n) * H-HHA-PROV-VBP-ADJ-FAC
    // END-COMPUTE.
    //
    // COMPUTE H-HHA-TOTAL-PAYMENT ROUNDED = H-HHA-TOTAL-PAYMENT + H-HHA-REVENUE-COST (n)
    // END-COMPUTE.
    //
    // COMPUTE H-HHA-REVENUE-ADD-ON-VISIT-AMT (n) ROUNDED =
    //         H-HHA-REVENUE-ADD-ON-VISIT-AMT (n) * H-HHA-PROV-VBP-ADJ-FAC
    // END-COMPUTE.
    //
    // COMPUTE H-HHA-TOTAL-PAYMENT ROUNDED =
    //         H-HHA-TOTAL-PAYMENT + H-HHA-REVENUE-ADD-ON-VISIT-AMT (n)
    // END-COMPUTE.
    final BigDecimal vbpAdjustmentFactor =
        calculationContext.getInput().getProviderData().getVbpAdjustment();
    BigDecimal totalPayment = calculationContext.getPaymentData().getTotalPayment();

    for (final RevenuePaymentData outputRevenueEntry :
        calculationContext.getPaymentData().getRevenuePayments()) {
      final BigDecimal cost =
          outputRevenueEntry
              .getCost()
              .multiply(vbpAdjustmentFactor)
              .setScale(2, RoundingMode.HALF_UP);
      final BigDecimal addOnAmount =
          outputRevenueEntry
              .getAddOnVisitAmount()
              .multiply(vbpAdjustmentFactor)
              .setScale(2, RoundingMode.HALF_UP);
      totalPayment = totalPayment.add(cost).add(addOnAmount);
      outputRevenueEntry.setCost(cost);
      outputRevenueEntry.setAddOnVisitAmount(addOnAmount);
    }

    totalPayment = totalPayment.setScale(2, RoundingMode.HALF_UP);
    calculationContext.getPaymentData().setTotalPayment(totalPayment);
  }

  private boolean isLupaPayment(Integer returnCode) {
    return Stream.of(ReturnCode.LUPA_PAYMENT_6, ReturnCode.LUPA_PAYMENT_WITH_ADDON_14)
        .anyMatch(rtc -> rtc.is(returnCode));
  }
}
