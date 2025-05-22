package gov.cms.fiss.pricers.ipps.core.rules;

import gov.cms.fiss.pricers.common.application.request.ClaimMetadata;
import gov.cms.fiss.pricers.common.application.request.RequestContentExtractor;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import java.util.List;

/** Evaluates web pricer parameters. */
public class EvaluateWebPricerParameters
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  private static final List<String> HMO_FLAG_VALUES = List.of("true", "y");
  private static final List<String> COT_FLAG_VALUES = List.of("true", "y");

  @Override
  public boolean shouldExecute(IppsPricerContext calculationContext) {
    return calculationContext.isWebPricerRequest();
  }

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final ClaimMetadata claimQueryParams = RequestContentExtractor.requestQueryParameters();

    // Apply HMO flag if present
    calculationContext.setHmoClaim(
        claimQueryParams.getClaimMetadata(IppsPricerContext.HMO_FLAG_KEY).stream()
            .map(String::toLowerCase)
            .anyMatch(HMO_FLAG_VALUES::contains));

    // Apply Cost Outlier Threshold (COT) flag if present
    calculationContext.setCostOutlierThresholdClaim(
        claimQueryParams.getClaimMetadata(IppsPricerContext.COT_FLAG_KEY).stream()
            .map(String::toLowerCase)
            .anyMatch(COT_FLAG_VALUES::contains));
  }
}
