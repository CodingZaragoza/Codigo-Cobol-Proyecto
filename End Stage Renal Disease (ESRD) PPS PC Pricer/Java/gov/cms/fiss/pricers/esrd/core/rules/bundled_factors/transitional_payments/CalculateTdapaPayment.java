package gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.transitional_payments;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Calculates TDAPA payment.
 *
 * <pre>
 * ***************************************************************
 * **  Compute TDAPA Payment                                   ***
 * ***************************************************************
 * </pre>
 *
 * <p>Converted from {@code 2000-CALCULATE-BUNDLED-FACTORS} (continued) in the COBOL code.
 *
 * @since 2020
 */
public class CalculateTdapaPayment
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();

    //     COMPUTE H-TDAPA-PAYMENT = B-PAYER-ONLY-VC-Q8 /
    //                               B-CLAIM-NUM-DIALYSIS-SESSIONS.
    if (null != claimData.getTotalTdapaAmountQ8() && claimData.getDialysisSessionCount() != 0) {
      calculationContext.setTdapaPayment(
          claimData
              .getTotalTdapaAmountQ8()
              .divide(new BigDecimal(claimData.getDialysisSessionCount()), 4, RoundingMode.DOWN));
    }
  }
}
