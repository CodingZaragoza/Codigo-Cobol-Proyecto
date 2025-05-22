package gov.cms.fiss.pricers.snf.core;

import gov.cms.fiss.pricers.common.application.rules.CalculationEvaluator;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.RuleContextExecutor;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingRequest;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingResponse;
import gov.cms.fiss.pricers.snf.core.tables.DataTables;
import java.util.List;

/** Base class for the business rules implementation of the SNF pricer. */
public abstract class SnfRulePricer
    extends RuleContextExecutor<SnfClaimPricingRequest, SnfClaimPricingResponse, SnfPricerContext> {

  protected final DataTables dataTables;

  protected SnfRulePricer(
      DataTables dataTables,
      List<CalculationRule<SnfClaimPricingRequest, SnfClaimPricingResponse, SnfPricerContext>>
          rules) {
    super(new CalculationEvaluator<>(rules));
    this.dataTables = dataTables;
  }
}
