package gov.cms.fiss.pricers.fqhc.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.EvaluatingCalculationRule;
import gov.cms.fiss.pricers.fqhc.api.v2.FqhcClaimPricingRequest;
import gov.cms.fiss.pricers.fqhc.api.v2.FqhcClaimPricingResponse;
import gov.cms.fiss.pricers.fqhc.core.FqhcPricerContext;
import java.util.List;

/**
 * Contains the actual logic to process the passed in claims record.
 *
 * <pre>
 * ****************************************************************
 *
 *                 FQHC CLAIM PROCESSING OVERVIEW
 *                 ------------------------------
 *
 *  1. VALIDATE EVERY LINE &amp; COLLECT INFORMATION FOR EACH DAY
 *  2. CALCULATE PAYMENT FOR PAID LINES &amp; ACCUMULATE CLAIM TOTALS
 *  3. SEND CLAIM RESULTS TO OUTPUT
 *
 * ****************************************************************
 * </pre>
 *
 * <p>Converted from {@code 2000-PROCESS-CLAIM} in the COBOL code.
 */
public class DefaultClaimPricing
    extends EvaluatingCalculationRule<
        FqhcClaimPricingRequest, FqhcClaimPricingResponse, FqhcPricerContext> {

  public DefaultClaimPricing(
      List<CalculationRule<FqhcClaimPricingRequest, FqhcClaimPricingResponse, FqhcPricerContext>>
          calculationRules) {
    super(calculationRules);
  }
}
