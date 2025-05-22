package gov.cms.fiss.pricers.hospice.core;

import gov.cms.fiss.pricers.common.application.rules.CalculationEvaluator;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.RuleContextExecutor;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingRequest;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingResponse;
import gov.cms.fiss.pricers.hospice.core.tables.DataTables;
import java.util.List;

public abstract class HospiceRulePricer
    extends RuleContextExecutor<
        HospiceClaimPricingRequest, HospiceClaimPricingResponse, HospicePricerContext> {
  protected final DataTables dataTables;

  protected HospiceRulePricer(
      List<
              CalculationRule<
                  HospiceClaimPricingRequest, HospiceClaimPricingResponse, HospicePricerContext>>
          rules,
      DataTables dataTables) {
    super(new CalculationEvaluator<>(rules));
    this.dataTables = dataTables;
  }
}
