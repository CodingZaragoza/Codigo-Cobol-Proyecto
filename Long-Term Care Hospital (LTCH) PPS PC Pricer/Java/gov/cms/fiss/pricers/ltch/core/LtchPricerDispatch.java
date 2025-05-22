package gov.cms.fiss.pricers.ltch.core;

import gov.cms.fiss.pricers.common.api.InternalPricerException;
import gov.cms.fiss.pricers.common.api.YearNotImplementedException;
import gov.cms.fiss.pricers.common.application.ClaimProcessor;
import gov.cms.fiss.pricers.common.application.PricerDispatch;
import gov.cms.fiss.pricers.ltch.LtchPricerConfiguration;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.core.codes.ErrorCode;
import gov.cms.fiss.pricers.ltch.core.tables.DataTables;

public class LtchPricerDispatch
    extends PricerDispatch<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerConfiguration> {

  public LtchPricerDispatch(LtchPricerConfiguration pricerConfiguration) {
    super(pricerConfiguration, o -> o.getReturnCodeData().getCode());
  }

  @Override
  protected void initializeReferences(LtchPricerConfiguration pricerConfiguration) {
    DataTables.loadDataTables(pricerConfiguration);

    for (final int supportedYear : pricerConfiguration.getSupportedYears()) {
      switch (supportedYear) {
        case 2020:
          yearReference.register(
              supportedYear, Ltch2020RulePricer.class, DataTables.forYear(supportedYear));
          break;
        case 2021:
          yearReference.register(
              supportedYear, Ltch2021RulePricer.class, DataTables.forYear(supportedYear));
          break;
        case 2022:
          yearReference.register(
              supportedYear, Ltch2022RulePricer.class, DataTables.forYear(supportedYear));
          break;
        case 2023:
          yearReference.register(
              supportedYear, Ltch2023RulePricer.class, DataTables.forYear(supportedYear));
          break;
        case 2024:
          yearReference.register(
              supportedYear, Ltch2024RulePricer.class, DataTables.forYear(supportedYear));
          break;
        case 2025:
          yearReference.register(
              supportedYear, Ltch2025RulePricer.class, DataTables.forYear(supportedYear));
          break;
        default:
          break;
      }
    }
  }

  @Override
  protected ClaimProcessor<LtchClaimPricingRequest, LtchClaimPricingResponse> getProcessor(
      LtchClaimPricingRequest input) throws YearNotImplementedException, InternalPricerException {
    return yearReference.fromFiscalYear(input.getClaimData().getDischargeDate(), "dischargeDate");
  }

  @Override
  protected boolean isErrorOutput(LtchClaimPricingResponse output) {
    return ErrorCode.isErrorCode(output.getReturnCodeData().getCode());
  }
}
