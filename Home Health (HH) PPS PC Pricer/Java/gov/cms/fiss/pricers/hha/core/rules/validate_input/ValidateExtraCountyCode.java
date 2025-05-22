package gov.cms.fiss.pricers.hha.core.rules.validate_input;

import gov.cms.fiss.pricers.hha.core.HhaPricerContext;

// From 1/1/2019 through 12/31/2023, eligible FIPS county codes and 2-digit state CBSAs
// received a rural adjustment according to rural category. Eligible rural county
// codes are in add-on-table.csv and eligible urban county codes are in extra-add-on-table.csv
public class ValidateExtraCountyCode extends ValidateCountyCode {
  @Override
  public boolean shouldExecute(HhaPricerContext calculationContext) {
    return calculationContext.getRuralCategory().isEmpty();
  }

  @Override
  public void calculate(HhaPricerContext calculationContext) {
    final String ruralCategory =
        calculationContext
            .getDataTables()
            .getExtraAddOnCategory(calculationContext.getInput().getProviderData().getCountyCode());
    if (ruralCategory != null) {
      calculationContext.setRuralCategory(ruralCategory);
    }
  }
}
