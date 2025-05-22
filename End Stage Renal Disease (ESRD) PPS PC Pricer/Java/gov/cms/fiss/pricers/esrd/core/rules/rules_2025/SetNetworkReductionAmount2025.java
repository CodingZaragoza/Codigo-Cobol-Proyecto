package gov.cms.fiss.pricers.esrd.core.rules.rules_2025;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;

/**
 * Determines network fee reduction.
 *
 * <pre>
 * ***************************************************************
 * Determine Network Fee Reduction for ESRD Claim
 * BR11871.2
 * $.21 for per-diem PPS
 * $.50 for full PPS
 * ***************************************************************
 * </pre>
 *
 * @since 2021
 */
public class SetNetworkReductionAmount2025
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();

    //     IF (B-COND-CODE = '74')  AND
    //        (B-REV-CODE = '0841' OR '0851')  THEN
    //     THEN MOVE 0.21 TO H-NETWORK-REDUCTION
    //     ELSE MOVE 0.50 TO H-NETWORK-REDUCTION.
    //     END-IF
    // CHANGED FOR 2025
    if (calculationContext.isPerDiemClaim()) {
      calculationContext.setNetworkReductionAmount(
          calculationContext.getNetworkReductionPerDiemAmount());
    } else {
      calculationContext.setNetworkReductionAmount(
          calculationContext.getNetworkReductionFullAmount());
    }
  }
}
