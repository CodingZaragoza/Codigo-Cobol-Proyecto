package gov.cms.fiss.pricers.irf.core;

import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.core.tables.DataTables;
import java.time.LocalDate;

public class Irf2015PricerContext extends IrfPricerContext {
  public static final String CALCULATION_VERSION_2015 = "2015.0";

  public Irf2015PricerContext(
      IrfClaimPricingRequest input, IrfClaimPricingResponse output, DataTables dataTables) {
    super(input, output, dataTables);
  }

  @Override
  public String getCalculationVersion() {
    return CALCULATION_VERSION_2015;
  }

  @Override
  public LocalDate getFiscalYearEnd() {
    return LocalDateUtils.fiscalYearEnd(LocalDate.of(2015, 1, 1));
  }

  @Override
  public LocalDate getFiscalYearStart() {
    return LocalDateUtils.fiscalYearStart(LocalDate.of(2015, 1, 1));
  }
}
