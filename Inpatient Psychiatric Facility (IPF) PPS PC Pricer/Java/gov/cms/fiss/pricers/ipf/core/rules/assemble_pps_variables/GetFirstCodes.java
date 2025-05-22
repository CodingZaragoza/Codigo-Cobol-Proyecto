package gov.cms.fiss.pricers.ipf.core.rules.assemble_pps_variables;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.api.v2.IpfPaymentData;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;
import gov.cms.fiss.pricers.ipf.core.tables.CodeFirstTableEntry;
import java.math.BigDecimal;
import java.util.List;

/**
 * Calculate first codes values.
 *
 * <p>Converted from {@code 2000-ASSEMBLE-PPS-VARIABLES} in the COBOL code.
 */
public class GetFirstCodes
    implements CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {

  @Override
  public boolean shouldExecute(IpfPricerContext calculationContext) {
    return calculationContext.getPaymentData().getDiagnosisRelatedGroupFactor() == null;
  }

  @Override
  public void calculate(IpfPricerContext calculationContext) {
    final IpfPaymentData paymentData = calculationContext.getPaymentData();

    //    PERFORM 2700-GET-FIRST-CODES THRU 2700-EXIT.
    CodeFirstTableEntry entry = null;
    final List<String> diagnosticCodes = calculationContext.getDiagnosisCodes();
    // Check there are at least 2 entries in the diagnostic code
    if (diagnosticCodes.size() > 1) {
      // Get entry for second diagnostic code. Java is zero-indexed, so 1 is the second entry.
      entry = calculationContext.getDataTables().getCodeFirstEntry(diagnosticCodes.get(1));
    }

    if (entry != null) {
      paymentData.setDiagnosisRelatedGroupFactor(entry.getFactor());
    } else {
      paymentData.setDiagnosisRelatedGroupFactor(new BigDecimal("1.00"));
    }
  }
}
