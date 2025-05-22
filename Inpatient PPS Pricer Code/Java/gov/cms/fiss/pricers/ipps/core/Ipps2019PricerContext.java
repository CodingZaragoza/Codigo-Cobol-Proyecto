package gov.cms.fiss.pricers.ipps.core;

import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.tables.DataTables;

public class Ipps2019PricerContext extends IppsPricerContext {

  public static final String CALCULATION_VERSION = "2019.2";

  public Ipps2019PricerContext(
      IppsClaimPricingRequest input, IppsClaimPricingResponse output, DataTables dataTables) {
    super(input, output, dataTables);
  }

  @Override
  public String getCalculationVersion() {
    return CALCULATION_VERSION;
  }
}
