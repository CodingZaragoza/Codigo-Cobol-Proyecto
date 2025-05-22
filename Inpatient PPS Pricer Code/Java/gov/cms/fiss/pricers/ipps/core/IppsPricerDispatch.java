package gov.cms.fiss.pricers.ipps.core;

import gov.cms.fiss.pricers.common.api.InternalPricerException;
import gov.cms.fiss.pricers.common.api.YearNotImplementedException;
import gov.cms.fiss.pricers.common.application.ClaimProcessor;
import gov.cms.fiss.pricers.common.application.PricerDispatch;
import gov.cms.fiss.pricers.ipps.IppsPricerConfiguration;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.tables.DataTables;

public class IppsPricerDispatch
    extends PricerDispatch<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerConfiguration> {

  public IppsPricerDispatch(IppsPricerConfiguration pricerConfiguration) {
    super(pricerConfiguration, o -> o.getReturnCodeData().getCode());
  }

  @Override
  protected void initializeReferences(IppsPricerConfiguration pricerConfiguration) {
    for (final int supportedYear : pricerConfiguration.getSupportedYears()) {
      switch (supportedYear) {
        case 2019:
          yearReference.register(
              supportedYear, Ipps2019RulePricer.class, DataTables.forYear(supportedYear));

          break;
        case 2020:
          yearReference.register(
              supportedYear, Ipps2020RulePricer.class, DataTables.forYear(supportedYear));

          break;
        case 2021:
          yearReference.register(
              supportedYear, Ipps2021RulePricer.class, DataTables.forYear(supportedYear));

          break;
        case 2022:
          yearReference.register(
              supportedYear, Ipps2022RulePricer.class, DataTables.forYear(supportedYear));

          break;
        case 2023:
          yearReference.register(
              supportedYear, Ipps2023RulePricer.class, DataTables.forYear(supportedYear));

          break;
        case 2024:
          yearReference.register(
              supportedYear, Ipps2024RulePricer.class, DataTables.forYear(supportedYear));

          break;
        case 2025:
          yearReference.register(
              supportedYear, Ipps2025RulePricer.class, DataTables.forYear(supportedYear));

          break;
        default:
          break;
      }
    }
  }

  @Override
  protected ClaimProcessor<IppsClaimPricingRequest, IppsClaimPricingResponse> getProcessor(
      IppsClaimPricingRequest input) throws YearNotImplementedException, InternalPricerException {
    return yearReference.fromFiscalYear(input.getClaimData().getDischargeDate(), "dischargeDate");
  }

  @Override
  protected boolean isErrorOutput(IppsClaimPricingResponse output) {
    return ResultCode.isErrorCode(Integer.parseInt(output.getReturnCodeData().getCode()));
  }
}
