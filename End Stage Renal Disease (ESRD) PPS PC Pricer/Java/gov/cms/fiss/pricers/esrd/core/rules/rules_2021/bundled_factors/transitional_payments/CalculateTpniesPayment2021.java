package gov.cms.fiss.pricers.esrd.core.rules.rules_2021.bundled_factors.transitional_payments;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Calculates TPNIES payment.
 *
 * <pre>
 * ***************************************************************
 * **  Compute TPNIES Payment                                  ***
 * ***************************************************************
 * </pre>
 *
 * <p>Converted from {@code 2000-CALCULATE-BUNDLED-FACTORS} (continued) in the COBOL code.
 *
 * @since 2020
 */
public class CalculateTpniesPayment2021
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();

    // ****************************************************************
    // ***  Compute TPNIES Payment                                  ***
    // ****************************************************************
    //      IF B-PAYER-ONLY-VC-QG-AMT NUMERIC
    //      COMPUTE H-TPNIES-PAYMENT =
    //           (0.65 * B-PAYER-ONLY-VC-QG-AMT) /
    //            B-CLAIM-NUM-DIALYSIS-SESSIONS.
    //
    if (null != claimData.getTotalTpniesAmountQg() && claimData.getDialysisSessionCount() != 0) {
      calculationContext.setTpniesPayment(
          new BigDecimal("0.65")
              .multiply(claimData.getTotalTpniesAmountQg())
              .divide(new BigDecimal(claimData.getDialysisSessionCount()), 4, RoundingMode.DOWN));
    }
  }
}
