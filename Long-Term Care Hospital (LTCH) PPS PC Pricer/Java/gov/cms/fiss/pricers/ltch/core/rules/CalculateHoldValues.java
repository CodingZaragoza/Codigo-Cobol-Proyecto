package gov.cms.fiss.pricers.ltch.core.rules;

import ch.obermuhlner.math.big.BigDecimalMath;
import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.core.LtchPricerContext;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class CalculateHoldValues
    implements CalculationRule<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {

  @Override
  public void calculate(LtchPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();

    calculationContext.setIppsComparablePayment(false);

    // *** -------------------------------------------------------
    // *** CAPITAL TEACHING ADJUSTMENT (2.7183 = E ROUNDED)
    // *** STARTING FY 2009 - REDUCE H-CAPI-IME-TEACH ROUNDED 50%
    // *** 02/17/2009 - 50% REDUCTION REMOVED DUE TO STIMULUS BILL
    // ***              THIS CHANGE IS RETROACTIVE TO 10/01/2008
    // *** -------------------------------------------------------
    // IF H-CAPI-IME-RATIO > 1.5000
    //   MOVE 1.5000 TO H-CAPI-IME-RATIO.
    if (BigDecimalUtils.isGreaterThan(
        calculationContext.getHoldCapitalTeachingAdjustmentRatio(), new BigDecimal("1.5"))) {
      calculationContext.setHoldCapitalTeachingAdjustmentRatio(
          new BigDecimal("1.5").setScale(4, RoundingMode.HALF_UP));
    }

    // COMPUTE H-CAPI-IME-TEACH ROUNDED =
    //        ((2.7183 ** (.2822 * H-CAPI-IME-RATIO)) - 1).
    calculationContext.setHoldCapitalImeTeach(
        BigDecimalMath.pow(
                new BigDecimal("2.7183"),
                new BigDecimal("0.2822")
                    .multiply(calculationContext.getHoldCapitalTeachingAdjustmentRatio()),
                new MathContext(10))
            .subtract(BigDecimal.ONE)
            .setScale(9, RoundingMode.HALF_UP));

    // *** -------------------------------------------------------
    // *** OPERATING DSH ADJUSTMENT
    // *** -------------------------------------------------------
    // *1) DETERMINE WHETHER THE PROVIDER IS URBAN OR RURAL
    // *---------------------------------------------------

    // *2) CALCULATE THE OPERATING DSH PERCENT
    // *--------------------------------------
    // COMPUTE H-OPER-DSH-PCT ROUNDED =
    //         P-NEW-SSI-RATIO + P-NEW-MEDICAID-RATIO.
    calculationContext.setHoldOperatingDshPercent(
        providerData
            .getSupplementalSecurityIncomeRatio()
            .add(providerData.getMedicaidRatio())
            .setScale(4, RoundingMode.HALF_UP));
  }
}
