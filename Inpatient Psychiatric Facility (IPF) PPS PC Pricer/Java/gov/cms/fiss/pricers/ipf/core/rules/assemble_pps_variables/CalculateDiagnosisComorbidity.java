package gov.cms.fiss.pricers.ipf.core.rules.assemble_pps_variables;

import gov.cms.fiss.pricers.common.api.annotations.FixedValue;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimData;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;
import gov.cms.fiss.pricers.ipf.core.tables.ComorbidityTableEntry;
import gov.cms.fiss.pricers.ipf.core.tables.ProcedureTableEntry;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/**
 * Calculate comorbidity values.
 *
 * <pre>
 * *******************************************************
 * ***  GET THE COMORBIDITY FACTORS
 * ***************************************************************
 * </pre>
 *
 * <p>Converted from {@code 2000-ASSEMBLE-PPS-VARIABLES} in the COBOL code.
 */
public class CalculateDiagnosisComorbidity
    implements CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {

  @Override
  public void calculate(IpfPricerContext calculationContext) {
    final IpfClaimData claimData = calculationContext.getClaimData();
    // PERFORM 3300-GET-COMORBIDITY THRU 3300-EXIT.
    @FixedValue BigDecimal comorbidityFactor = new BigDecimal("1.0000");
    final Map<String, Boolean> comorbidityCategories = new HashMap<>();

    final List<String> diagnosisCodes = calculationContext.getDiagnosisCodes();

    // Iterate over the 2nd and subsequent diagnosis codes
    for (int i = 1; i < diagnosisCodes.size(); i = i + 1) {
      final String diagnosticCode = diagnosisCodes.get(i);
      // Check for COVID emergency code
      if (StringUtils.equals(diagnosticCode, "U071")
          && LocalDateUtils.isBefore(claimData.getDischargeDate(), LocalDate.of(2020, 4, 1))) {
        // skips to the next iteration of the loop
        continue;
      }
      final ComorbidityTableEntry diagnosticComorbidity =
          calculationContext.getDataTables().getComorbidityTableEntry(diagnosticCode);
      if (diagnosticComorbidity != null) {
        final String category = diagnosticComorbidity.getCategory();
        // Check if diagnosis category has already been added to comorbidity factors
        if (!Boolean.TRUE.equals(comorbidityCategories.get(category))
            // If category is NOT 6 or the procedure code IS eligible then this is a valid code
            && (!StringUtils.equals(category, "6") || isProcedureEligible(calculationContext))) {
          // Add diagnosis category to comorbidity factors
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
    calculationContext.setComorbidityCategory(comorbidityCategories);
    calculationContext.getPaymentData().setComorbidityFactor(comorbidityFactor);
  }

  /**
   * Determine if a procedure is an eligible oncology procedure.
   *
   * @param calculationContext the calculation context
   * @return {@code true} if the procedure is eligible; {@code false} otherwise
   */
  protected boolean isProcedureEligible(IpfPricerContext calculationContext) {
    final IpfClaimData claimData = calculationContext.getClaimData();
    boolean isEligibleProcedure = false;
    for (final String procedureCode : claimData.getProcedureCodes()) {
      // fetch procedure code entry
      final ProcedureTableEntry procedureComorbidity =
          calculationContext.getDataTables().getProcedureTableEntry(procedureCode);
      // If procedure code exists and is not procedure code category 14
      if (procedureComorbidity != null) {
        final String category = procedureComorbidity.getCategory();
        final LocalDate date = procedureComorbidity.getEffectiveDate();
        if (!StringUtils.equals(category, "14")
            && (LocalDateUtils.isAfterOrEqual(claimData.getDischargeDate(), date))) {
          isEligibleProcedure = true;
          break;
        }
      }
    }
    return isEligibleProcedure;
  }
}
