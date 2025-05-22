package gov.cms.fiss.pricers.irf.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.api.v2.IrfPaymentData;
import gov.cms.fiss.pricers.irf.core.IrfPricerContext;
import gov.cms.fiss.pricers.irf.core.ResultCode;

/**
 * Calculates the standard payment.
 *
 * <p>Converted from {@code 3500-CONTINUE-CALC} in the COBOL code.
 */
public class CalculateStandardPayment
    implements CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  @Override
  public boolean shouldExecute(IrfPricerContext calculationContext) {
    return calculationContext.matchesReturnCode(ResultCode.OK_00);
  }

  @Override
  public void calculate(IrfPricerContext calculationContext) {
    final IrfPaymentData paymentData = calculationContext.getPaymentData();

    // COMPUTE PPS-STANDARD-PAY-AMT =
    //             (PPS-TRANSFER-PCT * PPS-RELATIVE-WGT
    //                       * PPS-BDGT-NEUT-CONV-AMT).
    paymentData.setStandardPayment(
        BigDecimalUtils.truncateDecimals(
            paymentData
                .getTransferPercent()
                .multiply(paymentData.getCaseMixGroupRelativeWeight())
                .multiply(paymentData.getBudgetNeutralityConversionAmount())));
  }
}
