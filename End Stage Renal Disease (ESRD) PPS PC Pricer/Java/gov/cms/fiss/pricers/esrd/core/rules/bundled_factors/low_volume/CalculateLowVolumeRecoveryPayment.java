package gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.low_volume;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.EvaluatingCalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.util.List;

/**
 * Calculates low-volume payments.
 *
 * <pre>
 * *****************************************************************
 * **  Calculate Low Volume payment for recovery purposes        ***
 * *****************************************************************
 * </pre>
 *
 * <p>Converted from {@code 2000-CALCULATE-BUNDLED-FACTORS} (continued) in the COBOL code.
 *
 * @since 2020
 */
public class CalculateLowVolumeRecoveryPayment
    extends EvaluatingCalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  public CalculateLowVolumeRecoveryPayment(
      List<CalculationRule<EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext>>
          calculationRules) {
    super(calculationRules);
  }

  @Override
  public boolean shouldExecute(EsrdPricerContext calculationContext) {
    //     IF LOW-VOLUME-TRACK = "Y"  THEN
    //     ...
    //     END-IF.
    return calculationContext.isLowVolumeClaim();
  }
}
