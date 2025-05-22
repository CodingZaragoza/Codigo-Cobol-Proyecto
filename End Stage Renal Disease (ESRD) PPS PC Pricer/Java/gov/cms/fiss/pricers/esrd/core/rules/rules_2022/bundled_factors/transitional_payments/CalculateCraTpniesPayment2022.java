package gov.cms.fiss.pricers.esrd.core.rules.rules_2022.bundled_factors.transitional_payments;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Calculates CRA TPNIES payment.
 *
 * <pre>
 * ***************************************************************
 * **  Compute CRA-TPNIES Payment                              ***
 * ***************************************************************
 * </pre>
 *
 * <p>Converted from {@code 2000-CALCULATE-BUNDLED-FACTORS} (continued) in the COBOL code.
 *
 * @since 2021
 */
public class CalculateCraTpniesPayment2022
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();

    //      IF B-PAYER-ONLY-VC-QH-AMT NUMERIC
    //      COMPUTE H-CRA-TPNIES-PAYMENT =
    //           (0.65 * B-PAYER-ONLY-VC-QH-AMT) /
    //            B-CLAIM-NUM-DIALYSIS-SESSIONS.
    //
    if (null != claimData.getTotalTpniesCraAmountQh() && claimData.getDialysisSessionCount() != 0) {
      calculationContext.setCraTpniesPayment(
          claimData
              .getTotalTpniesCraAmountQh()
              .divide(new BigDecimal(claimData.getDialysisSessionCount()), 4, RoundingMode.DOWN)
              .subtract(calculationContext.getCraTpniesOffset())
              .multiply(new BigDecimal("0.65")));
    }
  }
}
