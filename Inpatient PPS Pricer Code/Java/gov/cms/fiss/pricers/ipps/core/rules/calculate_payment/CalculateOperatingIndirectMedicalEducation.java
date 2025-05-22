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
 * Determines the operating indirect medical education amount.
 *
 * <p>Converted from {@code 3000-CALC-PAYMENT} in the COBOL code (continued).
 *
 * @since 2019
 */
public class CalculateOperatingIndirectMedicalEducation
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();

    // ***********************************************************
    // ***  OPERATING IME CALCULATION
    //     COMPUTE H-OPER-IME-TEACH ROUNDED =
    //            1.35 * ((1 + H-INTERN-RATIO) ** .405  - 1).
    calculationContext.setOperatingIndirectMedicalEducation(
        new BigDecimal("1.35")
            .multiply(
                BigDecimalUtils.pow(
                        BigDecimal.ONE.add(providerData.getInternsToBedsRatio()),
                        new BigDecimal("0.405"),
                        100)
                    .subtract(BigDecimal.ONE))
            .setScale(9, RoundingMode.HALF_UP));
  }
}
