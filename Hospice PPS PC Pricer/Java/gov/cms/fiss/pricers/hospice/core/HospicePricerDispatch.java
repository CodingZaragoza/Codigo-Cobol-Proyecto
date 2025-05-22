package gov.cms.fiss.pricers.hospice.core;

import gov.cms.fiss.pricers.common.api.InternalPricerException;
import gov.cms.fiss.pricers.common.api.YearNotImplementedException;
import gov.cms.fiss.pricers.common.application.ClaimProcessor;
import gov.cms.fiss.pricers.common.application.PricerDispatch;
import gov.cms.fiss.pricers.hospice.HospicePricerConfiguration;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingRequest;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingResponse;
import gov.cms.fiss.pricers.hospice.core.codes.ReturnCode;
import gov.cms.fiss.pricers.hospice.core.tables.DataTables;
import java.util.Optional;

public class HospicePricerDispatch
    extends PricerDispatch<
        HospiceClaimPricingRequest, HospiceClaimPricingResponse, HospicePricerConfiguration> {

  public HospicePricerDispatch(HospicePricerConfiguration pricerConfiguration) {
    super(pricerConfiguration, o -> o.getReturnCodeData().getCode());
  }

  @Override
  protected void initializeReferences(HospicePricerConfiguration pricerConfiguration) {
    for (final int supportedYear : pricerConfiguration.getSupportedYears()) {
      switch (supportedYear) {
        case 2020:
          yearReference.register(
              supportedYear, Hospice2020RulePricer.class, DataTables.forYear(supportedYear));
          break;
        case 2021:
          yearReference.register(
              supportedYear, Hospice2021RulePricer.class, DataTables.forYear(supportedYear));
          break;
        case 2022:
          yearReference.register(
              supportedYear, Hospice2022RulePricer.class, DataTables.forYear(supportedYear));
          break;
        case 2023:
          yearReference.register(
              supportedYear, Hospice2023RulePricer.class, DataTables.forYear(supportedYear));
          break;
        case 2024:
          yearReference.register(
              supportedYear, Hospice2024RulePricer.class, DataTables.forYear(supportedYear));
          break;
        case 2025:
          yearReference.register(
              supportedYear, Hospice2025RulePricer.class, DataTables.forYear(supportedYear));
          break;
        default:
          break;
      }
    }
  }

  @Override
  protected ClaimProcessor<HospiceClaimPricingRequest, HospiceClaimPricingResponse> getProcessor(
      HospiceClaimPricingRequest input)
      throws YearNotImplementedException, InternalPricerException {
    return yearReference.fromFiscalYear(
        input.getClaimData().getServiceFromDate(), "serviceFromDate");
  }

  @Override
  protected boolean isErrorOutput(HospiceClaimPricingResponse output) {
    return Optional.ofNullable(ReturnCode.fromCode(output.getReturnCodeData().getCode()))
        .map(ReturnCode::isErrorCode)
        .orElse(true);
  }
}
