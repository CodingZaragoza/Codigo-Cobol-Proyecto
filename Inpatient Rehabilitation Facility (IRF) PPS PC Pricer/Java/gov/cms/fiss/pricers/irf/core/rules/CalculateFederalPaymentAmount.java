package gov.cms.fiss.pricers.irf.core.rules;

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
 * Calculates the federal payment amount.
 *
 * <p>Converted from {@code 3500-CONTINUE-CALC} in the COBOL code.
 */
public class CalculateFederalPaymentAmount
    implements CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  @Override
  public boolean shouldExecute(IrfPricerContext calculationContext) {
    return calculationContext.matchesReturnCode(ResultCode.OK_00);
  }

  @Override
  public void calculate(IrfPricerContext calculationContext) {
    final IrfPaymentData paymentData = calculationContext.getPaymentData();

    // ** RULE: THE LABOR PORTION OF THE PAYMENT IS COMPUTED AS THE PPS
    // ** STANDARD PAY AMOUNT TIMES THE PPS NATIONAL LABOR PERCENTAGE
    // ** TIMES THE PPS WAGE INDEX

    // COMPUTE H-LABOR-PORTION =
    //         (PPS-STANDARD-PAY-AMT * PPS-NAT-LABOR-PCT)
    //           * PPS-WAGE-INDEX.
    final BigDecimal laborPortion =
        BigDecimalUtils.truncateDecimals(
                paymentData
                    .getStandardPayment()
                    .multiply(paymentData.getNationalLaborPercent())
                    .multiply(paymentData.getFinalWageIndex()),
                6)
            .setScale(6, RoundingMode.HALF_UP);

    // ** RULE: THE NON LABOR PORTION OF THE PAYMENT IS COMPUTED AS THE
    // ** PPS STANDARD PAY AMOUNT TIMES THE PPS NATIONAL NON LABOR
    // ** PERCENTAGE

    // COMPUTE H-NONLABOR-PORTION =
    //         (PPS-STANDARD-PAY-AMT * PPS-NAT-NONLABOR-PCT).
    final BigDecimal nonLaborPortion =
        BigDecimalUtils.truncateDecimals(
                paymentData.getStandardPayment().multiply(paymentData.getNationalNonLaborPercent()),
                6)
            .setScale(6, RoundingMode.HALF_UP);

    // ** RULE: THE PPS FEDERAL PAY AMOUNT IS COMPUTED AS THE ((LABOR
    // ** PORTION OF THE PAYMENT PLUS THE NON-LABOR PORTION OF THE
    // ** PAYMENT) TIMES THE PPS RURAL ADJUSTMENT FACTOR) ROUNDED.

    // COMPUTE PPS-FED-PAY-AMT ROUNDED =
    //         ((H-LABOR-PORTION + H-NONLABOR-PORTION) *
    //          PPS-RURAL-ADJUSTMENT).
    paymentData.setFederalPaymentAmount(
        laborPortion
            .add(nonLaborPortion)
            .multiply(paymentData.getRuralAdjustmentPercent())
            .setScale(2, RoundingMode.HALF_UP));
  }
}
