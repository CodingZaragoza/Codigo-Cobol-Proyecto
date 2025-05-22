package gov.cms.fiss.pricers.hha.core.rules;

import gov.cms.fiss.pricers.common.api.annotations.FixedValue;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingRequest;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingResponse;
import gov.cms.fiss.pricers.hha.api.v2.HhaPaymentData;
import gov.cms.fiss.pricers.hha.core.HhaPricerContext;
import gov.cms.fiss.pricers.hha.core.codes.ReturnCode;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculateInitialPayment
    implements CalculationRule<HhaClaimPricingRequest, HhaClaimPricingResponse, HhaPricerContext> {
  @Override
  public void calculate(HhaPricerContext calculationContext) {
    final HhaPaymentData paymentData = calculationContext.getPaymentData();

    if (calculationContext.isBillTypeRap()) {
      if (!calculationContext.isValidPaymentIndicator()) {
        calculationContext.completeWithReturnCode(ReturnCode.INVALID_PAYMENT_INDICATOR_35);
        return;
      }

      if (!calculationContext.isUsablePaymentIndicator()) {
        calculationContext.completeWithReturnCode(ReturnCode.NOT_USED_INDICATOR_3);
        return;
      }

      // FOR PDGM, ALL RAPS SHOULD BE PAID 20%
      final @FixedValue BigDecimal payment =
          calculationContext
              .calculatePaymentBasis()
              .multiply(HhaPricerContext.USABLE_RAP_MODIFIER)
              .setScale(2, RoundingMode.HALF_UP);

      paymentData.setTotalPayment(payment);
      paymentData.setHhrgPayment(payment);
      calculationContext.applyReturnCode(ReturnCode.NOT_USED_INDICATOR_4);
    }
  }
}
