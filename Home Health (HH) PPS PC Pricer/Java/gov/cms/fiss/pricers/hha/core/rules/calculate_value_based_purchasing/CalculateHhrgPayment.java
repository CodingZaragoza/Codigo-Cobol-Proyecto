package gov.cms.fiss.pricers.hha.core.rules.calculate_value_based_purchasing;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingRequest;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingResponse;
import gov.cms.fiss.pricers.hha.api.v2.HhaPaymentData;
import gov.cms.fiss.pricers.hha.core.HhaPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Calculates the HRG Payment, if applicable.
 *
 * <pre>
 * *******************************************************
 * CALCULATES THE VALUE BASED PURCHASING ADJUSTMENT AMOUNT
 * *******************************************************
 * </pre>
 *
 * <p>Converted from {@code 9100-VBP-CALC} in the COBOL code.
 */
public class CalculateHhrgPayment
    implements CalculationRule<HhaClaimPricingRequest, HhaClaimPricingResponse, HhaPricerContext> {

  @Override
  public boolean shouldExecute(HhaPricerContext calculationContext) {
    // IF H-HHA-HRG-PAY > 0
    return BigDecimalUtils.isGreaterThanZero(calculationContext.getPaymentData().getHhrgPayment());
  }

  @Override
  public void calculate(HhaPricerContext calculationContext) {
    // COMPUTE H-HHA-HRG-PAY ROUNDED = H-HHA-HRG-PAY * H-HHA-PROV-VBP-ADJ-FAC
    // END-COMPUTE
    // COMPUTE H-HHA-TOTAL-PAYMENT ROUNDED = H-HHA-TOTAL-PAYMENT + H-HHA-HRG-PAY
    // END-COMPUTE
    final HhaPaymentData output = calculationContext.getPaymentData();
    final BigDecimal vbpAdjustmentFactor =
        calculationContext.getInput().getProviderData().getVbpAdjustment();
    final BigDecimal hrgPayment =
        output.getHhrgPayment().multiply(vbpAdjustmentFactor).setScale(2, RoundingMode.HALF_UP);
    output.setHhrgPayment(hrgPayment);
    output.setTotalPayment(output.getTotalPayment().add(hrgPayment));
  }
}
