package gov.cms.fiss.pricers.ipps.core;

import gov.cms.fiss.pricers.common.application.rules.CalculationEvaluator;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.RuleContextExecutor;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.tables.DataTables;
import java.util.List;

/** Provides the framework integration for pricing IPPS claims. */
public abstract class IppsRulePricer
    extends RuleContextExecutor<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {
  /** Provides access to the static data. */
  protected final DataTables dataTables;

  protected IppsRulePricer(
      DataTables dataTables,
      List<CalculationRule<IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext>>
          rules) {
    super(new CalculationEvaluator<>(rules));
    this.dataTables = dataTables;
  }
}
