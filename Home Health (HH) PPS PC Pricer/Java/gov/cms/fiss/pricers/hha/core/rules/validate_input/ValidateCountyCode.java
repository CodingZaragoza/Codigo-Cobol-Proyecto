package gov.cms.fiss.pricers.hha.core.rules.validate_input;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingRequest;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingResponse;
import gov.cms.fiss.pricers.hha.core.HhaPricerContext;
import gov.cms.fiss.pricers.hha.core.codes.ReturnCode;
import gov.cms.fiss.pricers.hha.resources.HhaCbsaUtil;

// From 1/1/2019 through 12/31/2023, eligible FIPS county codes and 2-digit state CBSAs
// received a rural adjustment according to rural category. Eligible rural county
// codes are in add-on-table.csv and eligible urban county codes are in extra-add-on-table.csv
public class ValidateCountyCode
    implements CalculationRule<HhaClaimPricingRequest, HhaClaimPricingResponse, HhaPricerContext> {
  @Override
  public boolean shouldExecute(HhaPricerContext calculationContext) {
    final String cbsa =
        calculationContext.getInput().getProviderData().getCbsaActualGeographicLocation();

    return HhaCbsaUtil.sanitizeCbsa(cbsa).length() == 2;
  }

  @Override
  public void calculate(HhaPricerContext calculationContext) {
    final String ruralCategory =
        calculationContext
            .getDataTables()
            .getAddOnCategory(calculationContext.getProviderData().getCountyCode());
    if (ruralCategory == null) {
      calculationContext.completeWithReturnCode(ReturnCode.INVALID_COUNTY_CODE_31);
      return;
    }
    calculationContext.setRuralCategory(ruralCategory);
  }
}
