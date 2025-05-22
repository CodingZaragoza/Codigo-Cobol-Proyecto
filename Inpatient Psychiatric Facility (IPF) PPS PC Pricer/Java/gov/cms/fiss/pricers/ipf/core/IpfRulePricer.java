package gov.cms.fiss.pricers.ipf.core;

import gov.cms.fiss.pricers.common.application.ClaimProcessor;
import gov.cms.fiss.pricers.common.application.rules.CalculationEvaluator;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.RuleContextExecutor;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.tables.DataTables;
import java.util.List;

public abstract class IpfRulePricer
    extends RuleContextExecutor<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext>
    implements ClaimProcessor<IpfClaimPricingRequest, IpfClaimPricingResponse> {
  protected final DataTables dataTables;

  protected IpfRulePricer(
      DataTables dataTables,
      List<CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext>>
          rules) {
    super(new CalculationEvaluator<>(rules));
    this.dataTables = dataTables;
  }
}
