package gov.cms.fiss.pricers.esrd.core.rules.wage_index;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.EvaluatingCalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.util.List;

/**
 * Extracts the wage index information for the claim.
 *
 * <p>Emulates the functions from {@code 0800-FIND-BUNDLED-CBSA-WI} in the {@code ESDRV} COBOL code.
 *
 * @since 2020
 */
public class ExtractWageIndex
    extends EvaluatingCalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  public ExtractWageIndex(
      List<CalculationRule<EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext>>
          calculationRules) {
    super(calculationRules);
  }
}
