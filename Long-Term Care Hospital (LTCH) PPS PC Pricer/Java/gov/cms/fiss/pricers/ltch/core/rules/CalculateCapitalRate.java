package gov.cms.fiss.pricers.ltch.core.rules;

import ch.obermuhlner.math.big.BigDecimalMath;
import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.core.LtchPricerContext;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class CalculateCapitalRate
    implements CalculationRule<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {

  @Override
  public void calculate(LtchPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();

    // *** -------------------------------------------------------
    // *** OPERATING PAYMENT (STANDARD AMOUNT)
    // *** -------------------------------------------------------
    final BigDecimal operatingCola;
    if ("02".equals(providerData.getStateCode()) || "12".equals(providerData.getStateCode())) {
      operatingCola = providerData.getCostOfLivingAdjustment();
    } else {
      operatingCola = BigDecimal.ONE;
    }

    // *** -------------------------------------------------------
    // *** OPERATING TEACHING ADJUSTMENT
    // *** -------------------------------------------------------
    // COMPUTE H-OPER-IME-TEACH ROUNDED =
    //        1.35 * ((1 + H-INTERN-RATIO) ** .405 - 1).
    final BigDecimal internRatio =
        BigDecimalMath.pow(
            providerData.getInternsToBedsRatio().add(BigDecimal.ONE),
            new BigDecimal("0.405"),
            new MathContext(10));
    final BigDecimal operatingTeachingAdjustment =
        new BigDecimal("1.35")
            .multiply(internRatio.subtract(BigDecimal.ONE))
            .setScale(9, RoundingMode.HALF_UP);

    // COMPUTE H-STAND-AMT-OPER-PMT ROUNDED =
    //       ( (H-IPPS-NAT-LABOR-SHR * H-IPPS-WAGE-INDEX) +
    //         (H-IPPS-NAT-NONLABOR-SHR * H-OPER-COLA) ) *
    //         H-IPPS-DRG-WGT * (1 + H-OPER-IME-TEACH + H-OPER-DSH ).
    final BigDecimal teachDsh =
        BigDecimal.ONE
            .add(operatingTeachingAdjustment)
            .add(calculationContext.getHoldOperatingDshAmount());

    calculationContext.setHoldStandardAmountOperatingPayment(
        calculationContext
            .getHoldIppsNationalLaborShare()
            .multiply(calculationContext.getHoldProvIppsCBSA().getGeographicWageIndex())
            .add(calculationContext.getHoldIppsNationalNonLaborShare().multiply(operatingCola))
            .multiply(calculationContext.getHoldIppsDrgWeight())
            .multiply(teachDsh)
            .setScale(2, RoundingMode.HALF_UP));

    // *** -------------------------------------------------------
    // *** CAPITAL PAYMENT (CAPITAL RATE)
    // *** -------------------------------------------------------
    // COMPUTE H-CAPI-COLA ROUNDED =
    //       (.3152 * (H-OPER-COLA - 1) + 1).
    calculationContext.setHoldCapitalCola(
        new BigDecimal("0.3152")
            .multiply(operatingCola.subtract(BigDecimal.ONE))
            .add(BigDecimal.ONE)
            .setScale(3, RoundingMode.HALF_UP));
  }
}
