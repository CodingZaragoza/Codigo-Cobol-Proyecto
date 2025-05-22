package gov.cms.fiss.pricers.irf.core;

import gov.cms.fiss.pricers.common.application.rules.CalculationEvaluator;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.RuleContextExecutor;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.core.tables.DataTables;
import java.util.List;

public abstract class IrfRulePricer
    extends RuleContextExecutor<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {
  protected final DataTables dataTables;

  protected IrfRulePricer(
      DataTables dataTables,
      List<CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext>>
          rules) {
    super(new CalculationEvaluator<>(rules));
    this.dataTables = dataTables;
  }
}
