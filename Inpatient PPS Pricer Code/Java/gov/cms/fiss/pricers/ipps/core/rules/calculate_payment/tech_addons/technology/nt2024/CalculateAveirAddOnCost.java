package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2024;

import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.CalculateTechnologyAddOnCost;
import gov.cms.fiss.pricers.ipps.core.tables.ClaimCodeType;
import gov.cms.fiss.pricers.ipps.core.tables.DataTables;

/**
 * Applies add-on costs for Aveir if the technology was indicated in the claim.
 *
 * @since 2024
 */
public class CalculateAveirAddOnCost extends CalculateTechnologyAddOnCost {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final DataTables dataTables = calculationContext.getDataTables();

    if (dataTables.codesMatch(
        "AVEIR1", ClaimCodeType.PROC, calculationContext.getClaimData().getProcedureCodes())) {
      if (dataTables.codesMatch(
          "AVEIR2", ClaimCodeType.PROC, calculationContext.getClaimData().getProcedureCodes())) {
        aggregateNewTechCost(calculationContext, dataTables.getNewTechnologyAmount("AVEIRH"));
      } else {
        aggregateNewTechCost(calculationContext, dataTables.getNewTechnologyAmount("AVEIRL"));
      }
    }
  }
}
