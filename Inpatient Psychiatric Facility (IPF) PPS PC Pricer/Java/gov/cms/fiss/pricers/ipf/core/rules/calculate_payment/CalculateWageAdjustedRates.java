package gov.cms.fiss.pricers.ipf.core.rules.calculate_payment;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipf.api.v2.AdditionalVariableData;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.api.v2.IpfPaymentData;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Calculate the wage adjusted rates.
 *
 * <pre>
 * **************************************************************
 * **  CALCULATE THE WAGE ADJ RATES
 * **************************************************************
 * </pre>
 *
 * <p>Converted from {@code 3000-CALC-PAYMENT} in the COBOL code.
 */
public class CalculateWageAdjustedRates
    implements CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {
  @Override
  public void calculate(IpfPricerContext calculationContext) {
    final AdditionalVariableData additionalVariables = calculationContext.getAdditionalVariables();
    final IpfPaymentData paymentData = calculationContext.getPaymentData();

    final BigDecimal budgetRate = calculationContext.getAdditionalVariables().getBudgetRateAmount();
    //      COMPUTE IPF-LABOR-BASE-AMT ROUNDED =
    //                 ((IPF-BUDGNUT-RATE-AMT * IPF-LABOR-SHARE) *
    //                      W-CBSA-WAGE-INDEX).
    final BigDecimal laborBase =
        budgetRate
            .multiply(calculationContext.getLaborShare())
            .multiply(paymentData.getFinalWageIndex())
            .setScale(5, RoundingMode.HALF_UP);
    additionalVariables.setBaseLaborAmount(laborBase);

    //      COMPUTE IPF-NLABOR-BASE-AMT ROUNDED =
    //                 ((IPF-BUDGNUT-RATE-AMT * IPF-NLABOR-SHARE) *
    //                      IPF-COLA).
    final BigDecimal nonLaborBase =
        budgetRate
            .multiply(calculationContext.getNonLaborShare())
            .multiply(paymentData.getCostOfLivingAdjustmentPercent())
            .setScale(5, RoundingMode.HALF_UP);
    additionalVariables.setBaseNonLaborAmount(nonLaborBase);

    //      COMPUTE IPF-WAGE-ADJ-AMT ROUNDED =
    //                 (IPF-LABOR-BASE-AMT + IPF-NLABOR-BASE-AMT).
    additionalVariables.setWageAdjustedAmount(
        laborBase.add(nonLaborBase).setScale(2, RoundingMode.HALF_UP));
  }
}
