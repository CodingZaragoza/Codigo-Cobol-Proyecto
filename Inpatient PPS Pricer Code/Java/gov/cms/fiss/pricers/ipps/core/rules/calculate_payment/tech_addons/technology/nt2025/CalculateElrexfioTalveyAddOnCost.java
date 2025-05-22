package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2025;

import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.CalculateTechnologyAddOnCost;
import gov.cms.fiss.pricers.ipps.core.tables.ClaimCodeType;
import gov.cms.fiss.pricers.ipps.core.tables.DataTables;

/**
 * Applies add-on costs for Elrexfio / Talvey if the technology was indicated in the claim.
 *
 * @since 2025
 */
public class CalculateElrexfioTalveyAddOnCost extends CalculateTechnologyAddOnCost {

  private static final String TECHNOLOGY_NAME = "ELREXFIOTALVEY";

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final DataTables dataTables = calculationContext.getDataTables();

    if (dataTables.codesMatch(
        TECHNOLOGY_NAME,
        ClaimCodeType.PROC,
        calculationContext.getClaimData().getProcedureCodes())) {
      aggregateNewTechCost(calculationContext, dataTables.getNewTechnologyAmount(TECHNOLOGY_NAME));
    }
  }
}
