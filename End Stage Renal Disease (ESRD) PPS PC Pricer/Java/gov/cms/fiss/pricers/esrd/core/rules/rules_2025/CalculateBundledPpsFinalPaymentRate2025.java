package gov.cms.fiss.pricers.esrd.core.rules.rules_2025;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Calculates the bundled PPS final payment rate.
 *
 * <pre>
 * *****************************************************************
 * **  Calculate BUNDLED ESRD PPS Final Payment Rate             ***
 * *****************************************************************
 * </pre>
 *
 * <p>Converted from {@code 2000-CALCULATE-BUNDLED-FACTORS} (continued) in the COBOL code.
 *
 * @since 2020
 */
public class CalculateBundledPpsFinalPaymentRate2025
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();

    // CHANGED FOR 2025
    //   if StringUtils.equals(
    //           EsrdPricerContext.CONDITION_CODE_HOME_SERVICES_74, claimData.getConditionCode()

    if (calculationContext.isPerDiemClaim()) {
      calculationContext.setFinalAmountWithoutEtc(
          calculationContext.getPerDiemAmountWithoutEtc().setScale(4, RoundingMode.HALF_UP));

      calculationContext.setFullClaimAmount(
          calculationContext
              .getBundledAdjustedBaseWageAmount()
              .multiply(
                  new BigDecimal(claimData.getDialysisSessionCount()).multiply(new BigDecimal(3)))
              .divide(new BigDecimal(7), 2, RoundingMode.HALF_UP));
    } else {
      calculationContext.setFinalAmountWithoutEtc(
          calculationContext
              .getBundledAdjustedBaseWageAmount()
              .add(calculationContext.getBundledWageAdjustedTrainingAmount())
              .setScale(4, RoundingMode.HALF_UP));
    }
  }
}
