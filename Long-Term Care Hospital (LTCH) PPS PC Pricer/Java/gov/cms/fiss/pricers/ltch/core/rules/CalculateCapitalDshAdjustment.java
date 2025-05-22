package gov.cms.fiss.pricers.ltch.core.rules;

import ch.obermuhlner.math.big.BigDecimalMath;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.core.LtchPricerContext;
import gov.cms.fiss.pricers.ltch.core.codes.CbsaProviderType;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class CalculateCapitalDshAdjustment
    implements CalculationRule<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {

  @Override
  public void calculate(LtchPricerContext calculationContext) {
    // *** -------------------------------------------------------
    // *** CAPITAL DSH ADJUSTMENT (2.7183 = E ROUNDED)
    // *** -------------------------------------------------------
    // IF URBAN-CBSA AND H-BED-SIZE >= 100
    final CbsaProviderType cbsaProviderType = calculationContext.getCbsaProviderType();
    if (cbsaProviderType.equals(CbsaProviderType.URBAN)
        && BigDecimalUtils.isGreaterThanOrEqualTo(
            calculationContext.getHoldBedSize(), new BigDecimal("100"))) {
      // COMPUTE H-CAPI-DSH ROUNDED =
      //          2.7183 ** (.2025 * H-OPER-DSH-PCT) - 1
      calculationContext.setHoldCapitalDsh(
          BigDecimalMath.pow(
                  new BigDecimal("2.7183"),
                  new BigDecimal("0.2025")
                      .multiply(calculationContext.getHoldOperatingDshPercent()),
                  new MathContext(10))
              .subtract(BigDecimal.ONE)
              .setScale(4, RoundingMode.HALF_UP));
    } else {
      calculationContext.setHoldCapitalDsh(BigDecimal.ZERO.setScale(4, RoundingMode.HALF_UP));
    }
  }
}
