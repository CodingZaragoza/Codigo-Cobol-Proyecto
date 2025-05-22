package gov.cms.fiss.pricers.ltch.core;

import gov.cms.fiss.pricers.common.application.rules.CalculationEvaluator;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.RuleContextExecutor;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.core.tables.DataTables;
import java.util.List;

public abstract class LtchRulePricer
    extends RuleContextExecutor<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {
  protected final DataTables dataTables;

  protected LtchRulePricer(
      DataTables dataTables,
      List<CalculationRule<LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext>>
          rules) {
    super(new CalculationEvaluator<>(rules));
    this.dataTables = dataTables;
  }
}
