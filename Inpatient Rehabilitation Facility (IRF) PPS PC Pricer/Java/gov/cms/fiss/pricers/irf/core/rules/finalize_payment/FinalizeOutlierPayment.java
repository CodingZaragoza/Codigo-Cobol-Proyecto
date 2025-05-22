package gov.cms.fiss.pricers.irf.core.rules.finalize_payment;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.api.v2.IrfPaymentData;
import gov.cms.fiss.pricers.irf.core.IrfPricerContext;

/**
 * Finalizes outlier payment.
 *
 * <p>Converted from {@code 5000-FINAL-PAYMENTS} in the COBOL code.
 */
// TODO: DDS to evaluate renaming since this is not finalizing the outlier payment
public class FinalizeOutlierPayment
    implements CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  @Override
  public void calculate(IrfPricerContext calculationContext) {
    final IrfPaymentData paymentData = calculationContext.getPaymentData();

    // ** RULE: CLAIMS WITH NO SPECIAL OUTLIER PAYMENTS ARE ASSIGNED
    // ** ZERO DOLLARS FOR THE OUTLIER PAYMENT

    // IF B-SPEC-PAY-IND = '1' OR '3'
    if (calculationContext.isNotOutlier()) {
      // MOVE ZEROES TO PPS-OUTLIER-PAY-AMT.
      paymentData.setOutlierPayment(BigDecimalUtils.ZERO);
    }
  }
}
