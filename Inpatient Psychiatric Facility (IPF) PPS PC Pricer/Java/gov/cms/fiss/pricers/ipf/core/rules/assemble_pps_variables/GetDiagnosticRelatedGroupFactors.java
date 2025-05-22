package gov.cms.fiss.pricers.ipf.core.rules.assemble_pps_variables;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;
import gov.cms.fiss.pricers.ipf.core.tables.DrgTableEntry;

/**
 * Calculate diagnostic related group factors.
 *
 * <pre>
 * ***************************************************************
 * ***  GET THE DRG ADJUSTMENT FACTORES OR FIRST CODES
 * ***************************************************************
 * </pre>
 *
 * <p>Converted from {@code 2000-ASSEMBLE-PPS-VARIABLES} in the COBOL code.
 */
public class GetDiagnosticRelatedGroupFactors
    implements CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {

  @Override
  public void calculate(IpfPricerContext calculationContext) {
    // PERFORM 2600-GET-DRG-FACTORS THRU 2600-EXIT.
    final DrgTableEntry entry =
        calculationContext
            .getDataTables()
            .getDrgEntry(calculationContext.getClaimData().getDiagnosisRelatedGroup());
    if (entry != null) {
      calculationContext.getPaymentData().setDiagnosisRelatedGroupFactor(entry.getFactor());
    }
  }
}
