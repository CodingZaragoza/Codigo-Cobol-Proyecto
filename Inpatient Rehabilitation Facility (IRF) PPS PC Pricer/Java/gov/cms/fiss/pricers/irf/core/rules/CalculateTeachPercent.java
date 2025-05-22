package gov.cms.fiss.pricers.irf.core.rules;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.core.IrfPricerContext;
import gov.cms.fiss.pricers.irf.core.ResultCode;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Calculates the teach percent.
 *
 * <p>Converted from {@code 3000-CALC-PAYMENT} in the COBOL code.
 */
public class CalculateTeachPercent
    implements CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  @Override
  public boolean shouldExecute(IrfPricerContext calculationContext) {
    return calculationContext.matchesReturnCode(ResultCode.OK_00);
  }

  @Override
  public void calculate(IrfPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();

    // ** RULE: WHEN PRICING A CLAIM, THE TEACH PERCENTAGE IS CALCULATED
    // ** AS (((1 + THE PROVIDERS NEW CAPI) RAISED TO THE POWER OF
    // ** 1.0163) MINUS 1) ROUNDED

    // COMPUTE H-TEACH-PCT ROUNDED =
    //         ((1 + P-NEW-CAPI-IME) ** 1.0163) - 1.

    BigDecimal capitalIndirectMedicalEducationRatio =
        providerData.getCapitalIndirectMedicalEducationRatio();

    // NOTE: Default to zero if value is not set in provider data.
    if (capitalIndirectMedicalEducationRatio == null) {
      capitalIndirectMedicalEducationRatio = BigDecimal.ZERO;
    }

    calculationContext.setTeachPercent(
        BigDecimalUtils.pow(
                capitalIndirectMedicalEducationRatio.add(BigDecimal.ONE),
                calculationContext.getCalcTeach(),
                10)
            .subtract(BigDecimal.ONE)
            .setScale(4, RoundingMode.HALF_UP));
  }
}
