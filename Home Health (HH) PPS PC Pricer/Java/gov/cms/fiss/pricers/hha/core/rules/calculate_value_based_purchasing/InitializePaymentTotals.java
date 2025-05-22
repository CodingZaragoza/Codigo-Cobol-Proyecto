package gov.cms.fiss.pricers.hha.core.rules.calculate_value_based_purchasing;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingRequest;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingResponse;
import gov.cms.fiss.pricers.hha.api.v2.HhaPaymentData;
import gov.cms.fiss.pricers.hha.core.HhaPricerContext;

/**
 * Initializes the payment totals.
 *
 * <pre>
 * *******************************************************
 * CALCULATES THE VALUE BASED PURCHASING ADJUSTMENT AMOUNT
 * *******************************************************
 * </pre>
 *
 * <p>Converted from {@code 9100-VBP-CALC} in the COBOL code.
 */
public class InitializePaymentTotals
    implements CalculationRule<HhaClaimPricingRequest, HhaClaimPricingResponse, HhaPricerContext> {
  @Override
  public void calculate(HhaPricerContext calculationContext) {
    final HhaPaymentData output = calculationContext.getPaymentData();
    // MOVE H-HHA-TOTAL-PAYMENT TO WK-9100-TOTAL-PAYMENT.
    // MOVE 0 TO H-HHA-TOTAL-PAYMENT.
    calculationContext.setTemporaryPaymentTotal(output.getTotalPayment());
    output.setTotalPayment(BigDecimalUtils.ZERO);
  }
}
