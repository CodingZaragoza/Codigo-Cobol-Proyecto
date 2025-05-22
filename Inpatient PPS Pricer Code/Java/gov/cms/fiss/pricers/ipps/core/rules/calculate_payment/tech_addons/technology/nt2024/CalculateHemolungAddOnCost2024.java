package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2024;

import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.CalculateTechnologyAddOnCost;
import gov.cms.fiss.pricers.ipps.core.tables.ClaimCodeType;
import gov.cms.fiss.pricers.ipps.core.tables.DataTables;

/**
 * Applies add-on costs for Hemolung if the technology was indicated in the claim.
 *
 * @since 2024
 */
public class CalculateHemolungAddOnCost2024 extends CalculateTechnologyAddOnCost {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final DataTables dataTables = calculationContext.getDataTables();

    if (dataTables.codesMatch(
            "HEMOLUNG", ClaimCodeType.PROC, calculationContext.getClaimData().getProcedureCodes())
        && !dataTables.codesMatch(
            "COVID", ClaimCodeType.DIAG, calculationContext.getClaimData().getDiagnosisCodes())) {
      aggregateNewTechCost(calculationContext, dataTables.getNewTechnologyAmount("HEMOLUNG"));
    }
  }
}
