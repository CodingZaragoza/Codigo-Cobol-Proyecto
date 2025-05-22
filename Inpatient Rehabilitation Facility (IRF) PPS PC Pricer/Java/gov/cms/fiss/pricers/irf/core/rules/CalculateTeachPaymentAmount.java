package gov.cms.fiss.pricers.irf.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.api.v2.IrfPaymentData;
import gov.cms.fiss.pricers.irf.core.IrfPricerContext;
import gov.cms.fiss.pricers.irf.core.ResultCode;
import java.math.RoundingMode;

/**
 * Calculates the teach payment amount.
 *
 * <p>Converted from {@code 3500-CONTINUE-CALC} in the COBOL code.
 */
public class CalculateTeachPaymentAmount
    implements CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  @Override
  public boolean shouldExecute(IrfPricerContext calculationContext) {
    return calculationContext.matchesReturnCode(ResultCode.OK_00);
  }

  @Override
  public void calculate(IrfPricerContext calculationContext) {
    final IrfPaymentData paymentData = calculationContext.getPaymentData();

    // ** RULE: THE PPS TEACH PAY AMOUNT IS COMPUTED AS THE PPS FEDERAL
    // ** PAY AMOUNT TIMES THE PROVIDERS TEACH PERCENTAGE) ROUNDED

    // COMPUTE PPS-TEACH-PAY-AMT ROUNDED =
    //         (PPS-FED-PAY-AMT * H-TEACH-PCT).
    paymentData.setTeachingPayment(
        paymentData
            .getFederalPaymentAmount()
            .multiply(calculationContext.getTeachPercent())
            .setScale(2, RoundingMode.HALF_UP));
  }
}
