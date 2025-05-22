package gov.cms.fiss.pricers.ltch.core.rules;

import ch.obermuhlner.math.big.BigDecimalMath;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.core.LtchPricerContext;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class CalculatePerDiem
    implements CalculationRule<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {

  @Override
  public void calculate(LtchPricerContext calculationContext) {
    // *--------------------------------------------------------------*
    // *   LARGE-URBAN ADD-ON ELIMINATED FOR VERSIONS 2008.1 &        *
    // *   LATER (CHANGED FROM 1.03 TO 1.00)                          *
    // *--------------------------------------------------------------*
    calculationContext.setHoldLargeUrbanAddon(BigDecimal.ONE);

    // COMPUTE H-CAPI-GAF ROUNDED =
    //       (H-IPPS-WAGE-INDEX ** .6848).
    calculationContext.setHoldCapitalGeographicAreaFactor(
        BigDecimalMath.pow(
                calculationContext.getHoldProvIppsCBSA().getGeographicWageIndex(),
                new BigDecimal("0.6848"),
                new MathContext(10))
            .setScale(4, RoundingMode.HALF_UP));

    // COMPUTE H-CAPI-PMT ROUNDED =
    //       H-IPPS-CAPI-STD-FED-RATE * H-IPPS-DRG-WGT * H-CAPI-GAF *
    //       H-LRGURB-ADD-ON *  H-CAPI-COLA *
    //       (1 + H-CAPI-IME-TEACH + H-CAPI-DSH).
    calculationContext.setHoldCapitalPayment(
        calculationContext
            .getIppsCapitalStandardFedRate()
            .multiply(calculationContext.getHoldIppsDrgWeight())
            .multiply(calculationContext.getHoldCapitalGeographicAreaFactor())
            .multiply(calculationContext.getHoldLargeUrbanAddon())
            .multiply(calculationContext.getHoldCapitalCola())
            .multiply(
                BigDecimal.ONE
                    .add(calculationContext.getHoldCapitalImeTeach())
                    .add(calculationContext.getHoldCapitalDsh()))
            .setScale(2, RoundingMode.HALF_UP));

    // *** -------------------------------------------------------
    // *** IPPS COMPARABLE TOTAL PAYMENT (OPERATING + CAPITAL)
    // *** -------------------------------------------------------
    // COMPUTE H-IPPS-PAY-AMT ROUNDED =
    //       H-STAND-AMT-OPER-PMT + H-CAPI-PMT.
    calculationContext.setHoldIppsPayAmount(
        calculationContext
            .getHoldStandardAmountOperatingPayment()
            .add(calculationContext.getHoldCapitalPayment())
            .setScale(2, RoundingMode.HALF_UP));

    // *** -------------------------------------------------------
    // *** IPPS COMPARABLE PER DIEM PAYMENT
    // *** -------------------------------------------------------
    // COMPUTE H-IPPS-PER-DIEM ROUNDED =
    //       (H-IPPS-PAY-AMT / H-IPPS-DRG-ALOS) * H-LOS.
    calculationContext.setHoldIppsPerDiem(
        calculationContext
            .getHoldIppsPayAmount()
            .divide(calculationContext.getHoldIppsDrgALengthOfStay(), 3, RoundingMode.DOWN)
            .multiply(calculationContext.getHoldLengthOfStay())
            .setScale(2, RoundingMode.HALF_UP));

    // IF H-IPPS-PER-DIEM > H-IPPS-PAY-AMT
    if (BigDecimalUtils.isGreaterThan(
        calculationContext.getHoldIppsPerDiem(), calculationContext.getHoldIppsPayAmount())) {
      // MOVE H-IPPS-PAY-AMT TO H-IPPS-PER-DIEM
      calculationContext.setHoldIppsPerDiem(
          calculationContext.getHoldIppsPayAmount().setScale(2, RoundingMode.HALF_UP));
    }
  }
}
