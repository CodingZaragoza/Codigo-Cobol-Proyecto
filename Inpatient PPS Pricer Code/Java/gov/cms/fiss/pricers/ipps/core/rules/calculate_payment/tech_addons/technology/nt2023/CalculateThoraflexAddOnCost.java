package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2023;

import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.CalculateTechnologyAddOnCost;
import gov.cms.fiss.pricers.ipps.core.tables.ClaimCodeType;
import gov.cms.fiss.pricers.ipps.core.tables.DataTables;

/**
 * Applies add-on costs for Thoraflex if the technology was indicated in the claim.
 *
 * @since 2023
 */
public class CalculateThoraflexAddOnCost extends CalculateTechnologyAddOnCost {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final DataTables dataTables = calculationContext.getDataTables();

    if (dataTables.codesMatch(
            "THORAFLEX1", ClaimCodeType.PROC, calculationContext.getClaimData().getProcedureCodes())
        && dataTables.codesMatch(
            "THORAFLEX2",
            ClaimCodeType.PROC,
            calculationContext.getClaimData().getProcedureCodes())) {
      aggregateNewTechCost(calculationContext, dataTables.getNewTechnologyAmount("THORAFLEX"));
    }
  }
}
