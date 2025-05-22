package gov.cms.fiss.pricers.esrd.core.rules.rules_2025;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import gov.cms.fiss.pricers.esrd.core.codes.ReturnCode;
import java.math.RoundingMode;

/**
 * Calculates the wage-adjusted rate. This is the only rule for the corresponding COBOL block.
 *
 * <pre>
 * *****************************************************************
 * **Calculate BUNDLED Wage Adjusted Rate for AKI                ***
 * *****************************************************************
 * </pre>
 */
public class CalculateBundledWageAdjustedRateAki2025
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public boolean shouldExecute(EsrdPricerContext calculationContext) {
    return calculationContext.isAki84()
        && calculationContext.matchesReturnCode(ReturnCode.CALCULATION_STARTED_00);
  }

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    //     COMPUTE H-BUN-NAT-LABOR-AMT ROUNDED =
    //        (BUNDLED-BASE-PMT-RATE * BUN-NAT-LABOR-PCT) *
    //         BUN-CBSA-W-INDEX.
    calculationContext.setBundledNationalLaborAmount(
        calculationContext
            .getBundledBasePaymentRateAki()
            .multiply(calculationContext.getBundledNationalLaborPercentage())
            .multiply(calculationContext.getBundledWageIndex())
            .setScale(2, RoundingMode.HALF_DOWN));

    //     COMPUTE H-BUN-NAT-NONLABOR-AMT ROUNDED =
    //        BUNDLED-BASE-PMT-RATE * BUN-NAT-NONLABOR-PCT
    calculationContext.setBundledNationalNonLaborAmount(
        calculationContext
            .getBundledBasePaymentRateAki()
            .multiply(calculationContext.getBundledNationalNonLaborPercent())
            .setScale(2, RoundingMode.HALF_DOWN));

    //     COMPUTE H-BUN-BASE-WAGE-AMT ROUNDED =
    //        H-BUN-NAT-LABOR-AMT + H-BUN-NAT-NONLABOR-AMT.
    calculationContext.setBundledBaseWageAmount(
        calculationContext
            .getBundledNationalLaborAmount()
            .add(calculationContext.getBundledNationalNonLaborAmount())
            .setScale(4, RoundingMode.HALF_DOWN));
  }
}
