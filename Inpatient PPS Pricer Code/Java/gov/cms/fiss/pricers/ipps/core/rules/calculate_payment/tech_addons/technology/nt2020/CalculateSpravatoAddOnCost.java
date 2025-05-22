package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2020;

import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.CalculateTechnologyAddOnCost;
import gov.cms.fiss.pricers.ipps.core.tables.ClaimCodeType;
import gov.cms.fiss.pricers.ipps.core.tables.DataTables;

/**
 * Applies add-on costs for Spravato if the technology was indicated in the claim.
 *
 * @since 2020
 */
public class CalculateSpravatoAddOnCost extends CalculateTechnologyAddOnCost {

  private static final String TECHNOLOGY_NAME = "SPRAVATO";

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    //     IF PROC-SPRAVATO-FLAG = 'Y'
    //       MOVE  $.$$ TO H-CSTMED-STOP.
    //       PERFORM 4020-NEW-TECH-ADD-ON THRU 4020-EXIT.
    final DataTables dataTables = calculationContext.getDataTables();

    if (dataTables.codesMatch(
        TECHNOLOGY_NAME,
        ClaimCodeType.PROC,
        calculationContext.getClaimData().getProcedureCodes())) {
      aggregateNewTechCost(calculationContext, dataTables.getNewTechnologyAmount(TECHNOLOGY_NAME));
    }
  }
}
