package gov.cms.fiss.pricers.hha.core.rules.calculate_value_based_purchasing;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingRequest;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingResponse;
import gov.cms.fiss.pricers.hha.api.v2.HhaPaymentData;
import gov.cms.fiss.pricers.hha.core.HhaPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Calculates outlier payment, final total, and value-based purchasing adjustment.
 *
 * <pre>
 * *******************************************************
 * CALCULATES THE VALUE BASED PURCHASING ADJUSTMENT AMOUNT
 * *******************************************************
 * </pre>
 *
 * <p>Converted from {@code 9100-VBP-CALC} in the COBOL code.
 */
public class FinalizeTotalPayment
    implements CalculationRule<HhaClaimPricingRequest, HhaClaimPricingResponse, HhaPricerContext> {
  @Override
  public void calculate(HhaPricerContext calculationContext) {
    // COMPUTE H-HHA-OUTLIER-PAYMENT ROUNDED = H-HHA-OUTLIER-PAYMENT * H-HHA-PROV-VBP-ADJ-FAC
    // END-COMPUTE.
    //
    // COMPUTE H-HHA-TOTAL-PAYMENT ROUNDED = H-HHA-TOTAL-PAYMENT + H-HHA-OUTLIER-PAYMENT
    // END-COMPUTE.
    //
    // COMPUTE H-HHA-VBP-ADJ-AMT ROUNDED = H-HHA-TOTAL-PAYMENT - WK-9100-TOTAL-PAYMENT
    // END-COMPUTE.
    final HhaPaymentData output = calculationContext.getPaymentData();
    final BigDecimal vbpAdjustmentFactor =
        calculationContext.getInput().getProviderData().getVbpAdjustment();
    final BigDecimal outlierPayment =
        output.getOutlierPayment().multiply(vbpAdjustmentFactor).setScale(2, RoundingMode.HALF_UP);
    output.setOutlierPayment(outlierPayment);
    final BigDecimal totalPayment =
        output.getTotalPayment().add(outlierPayment).setScale(2, RoundingMode.HALF_UP);
    output.setTotalPayment(totalPayment);
    output.setValueBasedPurchasingAdjustmentAmount(
        totalPayment.subtract(calculationContext.getTemporaryPaymentTotal()));
  }
}
