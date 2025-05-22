package gov.cms.fiss.pricers.irf.core.rules.rules2020;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.api.v2.IrfPaymentData;
import gov.cms.fiss.pricers.irf.core.IrfPricerContext;
import gov.cms.fiss.pricers.irf.core.ResultCode;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Calculates the standard payment.
 *
 * <p>Converted from {@code 3500-CONTINUE-CALC} in the COBOL code.
 */
public class Irf2020CalculateStandardPayment
    implements CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  @Override
  public boolean shouldExecute(IrfPricerContext calculationContext) {
    return calculationContext.matchesReturnCode(ResultCode.OK_00);
  }

  @Override
  public void calculate(IrfPricerContext calculationContext) {
    final IrfPaymentData paymentData = calculationContext.getPaymentData();

    // ****************************************************************
    // *  IF A TRANSFER CASE, CALCULATE THE STANDARD PAYMENT USING    *
    // *  THE PER DIEM IN THE CALCULATION.                            *
    // ****************************************************************
    if (calculationContext.isTransferCase()) {
      // COMPUTE PPS-STANDARD-PAY-AMT =
      //    (PPS-BDGT-NEUT-CONV-AMT / PPS-AVG-LOS)
      //    * (H-LOS + .5)
      //    * PPS-RELATIVE-WGT
      paymentData.setStandardPayment(
          BigDecimalUtils.truncateDecimals(
              paymentData
                  .getBudgetNeutralityConversionAmount()
                  .divide(
                      BigDecimal.valueOf(paymentData.getAverageLengthOfStay()),
                      4,
                      RoundingMode.DOWN)
                  .multiply(BigDecimal.valueOf(paymentData.getLengthOfStay() + .5))
                  .multiply(paymentData.getCaseMixGroupRelativeWeight())));
    } else {

      // ****************************************************************
      // *  IF A NON-TRANSFER CASE, CALCULATE THE STANDARD PAYMENT      *
      // *  AS DONE NORMALLY.                                           *
      // ****************************************************************

      // COMPUTE PPS-STANDARD-PAY-AMT =
      //      PPS-RELATIVE-WGT *
      //      PPS-BDGT-NEUT-CONV-AMT
      paymentData.setStandardPayment(
          BigDecimalUtils.truncateDecimals(
              paymentData
                  .getCaseMixGroupRelativeWeight()
                  .multiply(paymentData.getBudgetNeutralityConversionAmount())));
    }
  }
}
