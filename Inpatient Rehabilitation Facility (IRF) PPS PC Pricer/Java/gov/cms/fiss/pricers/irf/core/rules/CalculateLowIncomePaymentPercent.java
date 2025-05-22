package gov.cms.fiss.pricers.irf.core.rules;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
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
 * Calculates the low income payment (LIP) percent.
 *
 * <p>Converted from {@code 3000-CALC-PAYMENT} in the COBOL code.
 */
public class CalculateLowIncomePaymentPercent
    implements CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  @Override
  public boolean shouldExecute(IrfPricerContext calculationContext) {
    return calculationContext.matchesReturnCode(ResultCode.OK_00);
  }

  @Override
  public void calculate(IrfPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();
    final IrfPaymentData paymentData = calculationContext.getPaymentData();

    // *******************
    // ** RULE: WHEN PRICING A CLAIM, THE PPS LOW INCOME PATIENT
    // ** ADJUSTMENT PERCENTAGE IS CALCULATED AS ((1 + (THE PROVIDERS
    // ** SSI RATIO + THE PROVIDERS MEDICAID RATIO) RAISED TO THE POWER
    // ** OF .3177) - 1) ROUNDED

    // COMPUTE H-WK-DSH = (P-NEW-SSI-RATIO
    //                      + P-NEW-MEDICAID-RATIO).
    calculationContext.setDisproportionateShareAdjustment(
        providerData
            .getSupplementalSecurityIncomeRatio()
            .add(providerData.getMedicaidRatio())
            .setScale(4, RoundingMode.HALF_UP));

    // These two calculations below are done as doubles instead of as BigDecimal because BigDecimal
    // doesn't have a pow() function that accepts another BigDecimal as the power -- it only takes
    // integers as powers. There are some implementations out there that we could use if we find
    // that this is an issue, but for now it doesn't seem to make a difference and doesn't seem
    // worth the effort since this should be very close

    // COMPUTE PPS-LIP-PCT ROUNDED =
    //       ((1 + H-WK-DSH) ** .3177) - 1.
    paymentData.setLowIncomePaymentPercent(
        BigDecimalUtils.pow(
                calculationContext.getDisproportionateShareAdjustment().add(BigDecimal.ONE),
                calculationContext.getCalcLowIncomePercentage(),
                10)
            .subtract(BigDecimal.ONE)
            .setScale(4, RoundingMode.HALF_UP));
  }
}
