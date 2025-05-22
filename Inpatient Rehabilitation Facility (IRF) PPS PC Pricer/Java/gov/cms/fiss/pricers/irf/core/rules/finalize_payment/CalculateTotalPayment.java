package gov.cms.fiss.pricers.irf.core.rules.finalize_payment;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.api.v2.IrfPaymentData;
import gov.cms.fiss.pricers.irf.core.IrfPricerContext;
import java.math.RoundingMode;

/**
 * Calculates total payment.
 *
 * <p>Converted from {@code 5000-FINAL-PAYMENTS} in the COBOL code.
 */
public class CalculateTotalPayment
    implements CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  @Override
  public void calculate(IrfPricerContext calculationContext) {
    final IrfPaymentData paymentData = calculationContext.getPaymentData();

    // ** RULE: THE PPS TOTAL PAY AMOUNT IS COMPUTED AS THE PPS FEDERAL
    // ** PAY AMOUNT PLUS THE PPS FACILITY SPECIFIC PAY AMOUNT PLUS THE
    // ** PPS OUTLIER PAY AMOUNT PLUS THE PPS LOW INCOME PATIENT PAY
    // ** AMOUNT + PPS TEACH PAY AMOUNT

    // COMPUTE PPS-TOTAL-PAY-AMT ROUNDED =
    //         (PPS-FED-PAY-AMT + PPS-FAC-SPEC-PAY-AMT
    //          + PPS-OUTLIER-PAY-AMT + PPS-LIP-PAY-AMT +
    //          PPS-TEACH-PAY-AMT).
    paymentData.setTotalPayment(
        paymentData
            .getFederalPaymentAmount()
            .add(paymentData.getFacilitySpecificPayment())
            .add(paymentData.getOutlierPayment())
            .add(paymentData.getLowIncomePayment())
            .add(paymentData.getTeachingPayment())
            .setScale(2, RoundingMode.HALF_UP));
  }
}
