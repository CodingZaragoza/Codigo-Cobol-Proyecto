package gov.cms.fiss.pricers.esrd.core;

import gov.cms.fiss.pricers.common.api.InternalPricerException;
import gov.cms.fiss.pricers.common.api.YearNotImplementedException;
import gov.cms.fiss.pricers.common.application.ClaimProcessor;
import gov.cms.fiss.pricers.common.application.PricerDispatch;
import gov.cms.fiss.pricers.esrd.EsrdPricerConfiguration;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.tables.DataTables;
import java.time.LocalDate;

public class EsrdPricerDispatch
    extends PricerDispatch<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerConfiguration> {

  public EsrdPricerDispatch(EsrdPricerConfiguration pricerConfiguration) {
    super(pricerConfiguration, o -> o.getReturnCodeData().getCode());
  }

  @Override
  protected void initializeReferences(EsrdPricerConfiguration pricerConfiguration) {
    DataTables.loadDataTables(pricerConfiguration);

    for (final int supportedYear : pricerConfiguration.getSupportedYears()) {
      switch (supportedYear) {
        case 2020:
          yearReference.register(
              supportedYear, EsrdRulePricer2020.class, DataTables.forYear(supportedYear));
          yearReference.register(
              supportedYear,
              LocalDate.of(2020, 7, 1),
              EsrdRulePricer2020Dot2.class,
              DataTables.forYear(supportedYear));

          break;
        case 2021:
          yearReference.register(
              supportedYear, EsrdRulePricer2021.class, DataTables.forYear(supportedYear));

          break;
        case 2022:
          yearReference.register(
              supportedYear, EsrdRulePricer2022.class, DataTables.forYear(supportedYear));
          yearReference.register(
              supportedYear,
              LocalDate.of(2022, 7, 1),
              EsrdRulePricer2022Dot2.class,
              DataTables.forYear(supportedYear));

          break;
        case 2023:
          yearReference.register(
              supportedYear, EsrdRulePricer2023.class, DataTables.forYear(supportedYear));

          break;
        case 2024:
          yearReference.register(
              supportedYear, EsrdRulePricer2024.class, DataTables.forYear(supportedYear));

          break;
        case 2025:
          yearReference.register(
              supportedYear, EsrdRulePricer2025.class, DataTables.forYear(supportedYear));

          break;

        default:
          break;
      }
    }
  }

  @Override
  protected ClaimProcessor<EsrdClaimPricingRequest, EsrdClaimPricingResponse> getProcessor(
      EsrdClaimPricingRequest input) throws YearNotImplementedException, InternalPricerException {
    return yearReference.fromCalendarYear(
        input.getClaimData().getServiceThroughDate(), "serviceThroughDate");
  }

  @Override
  protected boolean isErrorOutput(EsrdClaimPricingResponse output) {
    return Integer.parseInt(output.getReturnCodeData().getCode()) >= 50;
  }
}
