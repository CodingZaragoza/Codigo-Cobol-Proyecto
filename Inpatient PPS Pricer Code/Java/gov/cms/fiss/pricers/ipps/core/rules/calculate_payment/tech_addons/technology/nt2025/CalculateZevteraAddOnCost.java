package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2025;

import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.CalculateTechnologyAddOnCost;
import gov.cms.fiss.pricers.ipps.core.tables.ClaimCodeType;
import gov.cms.fiss.pricers.ipps.core.tables.DataTables;

/**
 * Applies add-on costs for ZEVTERA if the technology was indicated in the claim.
 *
 * @since 2025
 */
public class CalculateZevteraAddOnCost extends CalculateTechnologyAddOnCost {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final DataTables dataTables = calculationContext.getDataTables();

    if (dataTables.codesMatch(
        "ZEVTERA1", ClaimCodeType.PROC, calculationContext.getClaimData().getProcedureCodes())) {
      if (dataTables.codesMatch(
              "ZEVTERA2", ClaimCodeType.DIAG, calculationContext.getClaimData().getDiagnosisCodes())
          && dataTables.codesMatch(
              "ZEVTERA3",
              ClaimCodeType.DIAG,
              calculationContext.getClaimData().getDiagnosisCodes())) {
        aggregateNewTechCost(calculationContext, dataTables.getNewTechnologyAmount("ZEVTERAH"));
      } else {
        aggregateNewTechCost(calculationContext, dataTables.getNewTechnologyAmount("ZEVTERAL"));
      }
    }
  }
}
