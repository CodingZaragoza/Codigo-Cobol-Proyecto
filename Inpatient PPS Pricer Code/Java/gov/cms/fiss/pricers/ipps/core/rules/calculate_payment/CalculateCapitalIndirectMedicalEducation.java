package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Determines the capital indirect medical education amount.
 *
 * <p>Converted from {@code 3000-CALC-PAYMENT} in the COBOL code (continued).
 *
 * @since 2019
 */
public class CalculateCapitalIndirectMedicalEducation
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();

    // ***********************************************************
    // ***  CAPITAL IME TEACH CALCULATION
    //     MOVE 0 TO H-WK-CAPI-IME-TEACH.
    BigDecimal capitalIndirectMedicalEducationRatio = BigDecimal.ZERO;

    //     IF P-NEW-CAPI-IME NUMERIC
    //        IF P-NEW-CAPI-IME > 1.5000
    //           MOVE 1.5000 TO P-NEW-CAPI-IME.
    if (providerData.getCapitalIndirectMedicalEducationRatio() != null) {
      capitalIndirectMedicalEducationRatio = providerData.getCapitalIndirectMedicalEducationRatio();
    }

    if (BigDecimalUtils.isGreaterThan(
        capitalIndirectMedicalEducationRatio, new BigDecimal("1.5"))) {
      capitalIndirectMedicalEducationRatio = new BigDecimal("1.5");
    }

    // *****YEARCHANGE 2009.5 ****************************************
    // ***
    // ***  PER POLICY, WE REMOVED THE .5 MULTIPLER
    // ***
    // ***********************************************************
    //     IF P-NEW-CAPI-IME NUMERIC
    //        COMPUTE H-WK-CAPI-IME-TEACH ROUNDED =
    //         ((2.7183 ** (.2822 * P-NEW-CAPI-IME)) - 1).
    calculationContext.setCapitalIndirectMedicalEducation(
        BigDecimalUtils.pow(
                new BigDecimal("2.7183"),
                new BigDecimal("0.2822").multiply(capitalIndirectMedicalEducationRatio),
                100)
            .subtract(BigDecimal.ONE)
            .setScale(9, RoundingMode.HALF_UP));
  }
}
