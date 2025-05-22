package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.CbsaReference;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Determines the capital geographic adjustment factor, the capital cost of living adjustment and
 * the capital federal rate.
 *
 * <p>Converted from {@code 3000-CALC-PAYMENT} in the COBOL code (continued).
 *
 * @since 2019
 */
public class CalculateCapitalPaymentMethodB
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final CbsaReference cbsaReference = calculationContext.getCbsaReference();

    // ***********************************************************
    // *****YEARCHANGE 2010.0 ************************************
    // ***  CAPITAL PAYMENT METHOD B - YEARCHNG
    // ***  CAPITAL PAYMENT METHOD B
    //     IF W-CBSA-SIZE = 'L'
    //        MOVE 1.00 TO H-CAPI-LARG-URBAN
    //     ELSE
    //        MOVE 1.00 TO H-CAPI-LARG-URBAN.
    //     COMPUTE H-CAPI-GAF    ROUNDED = (H-WAGE-INDEX ** .6848).
    calculationContext.setCapitalGeographicAdjFactor(
        BigDecimalUtils.pow(cbsaReference.getWageIndex(), new BigDecimal("0.6848"), 100)
            .setScale(4, RoundingMode.HALF_UP));

    // *****YEARCHANGE 2018.0 ************************************
    //     COMPUTE H-FEDERAL-RATE ROUNDED =
    //                              (0459.41 * H-CAPI-GAF).
    final BigDecimal federalRate =
        calculationContext
            .getCapitalBaseRate()
            .multiply(calculationContext.getCapitalGeographicAdjFactor())
            .setScale(2, RoundingMode.HALF_UP);

    // *****YEARCHANGE 2015.1 ************************************
    //     COMPUTE H-CAPI-COLA ROUNDED =
    //                     (.3152 * (H-OPER-COLA - 1) + 1).
    //     MOVE H-FEDERAL-RATE TO H-CAPI-FED-RATE.
    calculationContext.setCapitalCostOfLivingAdjustment(
        new BigDecimal("0.3152")
            .multiply(
                calculationContext.getOperatingCostOfLivingAdjustment().subtract(BigDecimal.ONE))
            .add(BigDecimal.ONE)
            .setScale(3, RoundingMode.HALF_UP));
    calculationContext.setCapitalFederalRate(federalRate);
  }
}
