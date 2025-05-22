package gov.cms.fiss.pricers.hha.core;

import gov.cms.fiss.pricers.common.application.rules.CalculationEvaluator;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.RuleContextExecutor;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingRequest;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingResponse;
import gov.cms.fiss.pricers.hha.core.tables.DataTables;
import java.util.List;

/** Base class for the business rules implementation of the HHA pricer. */
public abstract class HhaRulePricer
    extends RuleContextExecutor<HhaClaimPricingRequest, HhaClaimPricingResponse, HhaPricerContext> {

  protected final DataTables dataTables;

  protected HhaRulePricer(
      DataTables dataTables,
      List<CalculationRule<HhaClaimPricingRequest, HhaClaimPricingResponse, HhaPricerContext>>
          rules) {
    super(new CalculationEvaluator<>(rules));
    this.dataTables = dataTables;
  }

  /** Returns the context initialized and configured for a specific year. */
  protected abstract HhaPricerContext contextFor(HhaClaimPricingRequest input);
}
