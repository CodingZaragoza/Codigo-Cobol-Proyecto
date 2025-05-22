package gov.cms.fiss.pricers.ipf.core.rules.calculate_payment;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipf.api.v2.AdditionalVariableData;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimData;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.api.v2.IpfPaymentData;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;
import gov.cms.fiss.pricers.ipf.core.codes.ReturnCode;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Finalize the payment data.
 *
 * <pre>
 * **************************************************************
 * **  RETURN 100% PPS AMOUNT  FOR STOP LOSS PROVISION
 * **  NOT BLENDED
 * **************************************************************
 * </pre>
 *
 * <p>Converted from {@code 3000-CALC-PAYMENT} in the COBOL code.
 */
public class FinalizePayment
    implements CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {
  @Override
  public void calculate(IpfPricerContext calculationContext) {
    final IpfClaimData claimData = calculationContext.getClaimData();
    final AdditionalVariableData additionalVariables = calculationContext.getAdditionalVariables();
    final IpfPaymentData paymentData = calculationContext.getPaymentData();

    final BigDecimal federalPortion = calculationContext.getFederalPortion();
    final BigDecimal teachingPortion = calculationContext.getTeachingPortion();
    final BigDecimal outlierPayment =
        BigDecimalUtils.defaultValue(additionalVariables.getOutlierPayment());
    final BigDecimal ectPayment = additionalVariables.getElectroConvulsiveTherapyPayment();

    //       COMPUTE IPF-100PCT-STOPLOS-AMT ROUNDED =
    //               WK-FED-PORTION + IPF-OUTLIER-PAYMENT +
    //               IPF-ECT-PAYMENT + WK-TEACH-PORTION.
    additionalVariables.setStopLossAmount(
        federalPortion.add(outlierPayment).add(ectPayment).add(teachingPortion));

    //      COMPUTE IPF-FED-PAYMENT ROUNDED = WK-FED-PORTION * 1.00
    final BigDecimal federalPayment = federalPortion.setScale(2, RoundingMode.HALF_UP);
    additionalVariables.setFederalPayment(federalPayment);

    //      COMPUTE IPF-ECT-PAYMENT ROUNDED = IPF-ECT-PAYMENT * 1.00
    // This looks like it equates to a no-op in Java

    //      COMPUTE IPF-TEACH-PAYMENT ROUNDED = WK-TEACH-PORTION * 1.00
    additionalVariables.setTeachingPayment(teachingPortion);

    //      COMPUTE IPF-OUTLIER-PAYMENT ROUNDED = IPF-OUTLIER-PAYMENT * 1.00
    // This looks like it equates to a no-op in Java

    //      COMPUTE IPF-FAC-PAYMENT ROUNDED = P-NEW-FAC-SPEC-RATE * .0.
    final BigDecimal factorPayment = BigDecimal.ZERO.setScale(2, RoundingMode.UNNECESSARY);
    additionalVariables.setFactorPayment(factorPayment);

    //      COMPUTE IPF-TOT-PAYMENT ROUNDED =
    //              IPF-FED-PAYMENT + IPF-FAC-PAYMENT +
    //              IPF-ECT-PAYMENT + IPF-TEACH-PAYMENT +
    //              IPF-OUTLIER-PAYMENT.
    paymentData.setTotalPayment(
        federalPayment.add(factorPayment).add(ectPayment).add(teachingPortion).add(outlierPayment));

    //      IF IPF-RTC = 00
    //         IF BILL-PRIOR-DAYS IS GREATER THAN 0
    //            MOVE 03 TO IPF-RTC.
    //      IF IPF-RTC = 02
    //         IF BILL-PRIOR-DAYS IS GREATER THAN 0
    //            MOVE 04 TO IPF-RTC.
    if (claimData.getPriorDays() > 0) {
      if (calculationContext.matchesReturnCode(ReturnCode.NORMAL_PAYMENT_0)) {
        calculationContext.applyReturnCode(ReturnCode.WITH_PRIOR_DAYS_3);
      } else if (calculationContext.matchesReturnCode(ReturnCode.OUTLIER_2)) {
        calculationContext.applyReturnCode(ReturnCode.OUTLIER_WITH_PRIOR_DAYS_4);
      }
    }
  }
}
