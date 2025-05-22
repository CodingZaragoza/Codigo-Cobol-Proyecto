package gov.cms.fiss.pricers.fqhc.core;

import gov.cms.fiss.pricers.common.application.ClaimProcessor;
import gov.cms.fiss.pricers.common.application.PricerDispatch;
import gov.cms.fiss.pricers.fqhc.FqhcPricerConfiguration;
import gov.cms.fiss.pricers.fqhc.api.v2.FqhcClaimPricingRequest;
import gov.cms.fiss.pricers.fqhc.api.v2.FqhcClaimPricingResponse;
import gov.cms.fiss.pricers.fqhc.core.tables.DataTables;

public class FqhcPricerDispatch
    extends PricerDispatch<
        FqhcClaimPricingRequest, FqhcClaimPricingResponse, FqhcPricerConfiguration> {
  private FqhcRulePricer fqhcRulePricer;

  public FqhcPricerDispatch(FqhcPricerConfiguration pricerConfiguration) {
    super(pricerConfiguration, o -> o.getReturnCodeData().getCode());
  }

  @Override
  protected void initializeReferences(FqhcPricerConfiguration pricerConfiguration) {
    fqhcRulePricer = new FqhcRulePricer(DataTables.loadDataTables(pricerConfiguration));
  }

  @Override
  protected ClaimProcessor<FqhcClaimPricingRequest, FqhcClaimPricingResponse> getProcessor(
      FqhcClaimPricingRequest input) {
    return fqhcRulePricer;
  }

  @Override
  protected boolean isErrorOutput(FqhcClaimPricingResponse output) {
    return false;
  }
}
