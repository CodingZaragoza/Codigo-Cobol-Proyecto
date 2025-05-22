package gov.cms.fiss.pricers.ipf.core.rules.assemble_pps_variables;

import gov.cms.fiss.pricers.common.api.annotations.FixedValue;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;
import gov.cms.fiss.pricers.ipf.core.tables.ProcedureTableEntry;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class CalculateProcedureComorbidity
    implements CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {

  // Eligible procedure category 14 codes can receive comorbidity adjustment (eff 10/01/24)
  @Override
  public void calculate(IpfPricerContext calculationContext) {

    @FixedValue
    BigDecimal comorbidityFactor = calculationContext.getPaymentData().getComorbidityFactor();
    final Map<String, Boolean> comorbidityCategories = calculationContext.getComorbidityCategory();
    final List<String> procedureCodes = calculationContext.getProcedureCodes();

    // Iterate over procedure codes
    for (final String procedureCode : procedureCodes) {

      final ProcedureTableEntry procedureComorbidity =
          calculationContext.getDataTables().getProcedureTableEntry(procedureCode);
      if (procedureComorbidity != null) {
        final String category = procedureComorbidity.getCategory();
        // If procedure category has not been added to comorbidity factors
        // and if procedure code category is 14
        if (!Boolean.TRUE.equals(comorbidityCategories.get(category))
            && (StringUtils.equals(category, "14"))) {
          // add procedure category to comorbidity factors
          comorbidityCategories.put(category, true);
          comorbidityFactor =
              comorbidityFactor
                  .multiply(
                      BigDecimalUtils.defaultValue(
                          calculationContext.getDiagnosticCodeAdjustment(category), BigDecimal.ONE))
                  .setScale(4, RoundingMode.HALF_UP);
        }
      }
    }
    calculationContext.getPaymentData().setComorbidityFactor(comorbidityFactor);
  }
}
