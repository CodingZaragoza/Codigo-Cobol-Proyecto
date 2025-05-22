package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2019;

import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.CalculateTechnologyAddOnCost;
import gov.cms.fiss.pricers.ipps.core.tables.ClaimCodeType;
import gov.cms.fiss.pricers.ipps.core.tables.DataTables;

/**
 * Applies add-on costs for Remede if the technology was indicated in the claim.
 *
 * @since 2019
 */
public class CalculateRemedeAddOnCost extends CalculateTechnologyAddOnCost {

  private static final String TECHNOLOGY_NAME = "REMEDE";

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    //     IF PROC-REMEDE1-FLAG = 'Y' AND PROC-REMEDE2-FLAG = 'Y'
    //       AND PROC-REMEDE3-FLAG = 'Y'
    //       MOVE  $.$$ TO H-CSTMED-STOP.
    //       PERFORM 4020-NEW-TECH-ADD-ON THRU 4020-EXIT.
    final DataTables dataTables = calculationContext.getDataTables();

    if (dataTables.codesMatchAll(
        TECHNOLOGY_NAME,
        ClaimCodeType.PROC,
        calculationContext.getClaimData().getProcedureCodes())) {
      aggregateNewTechCost(calculationContext, dataTables.getNewTechnologyAmount(TECHNOLOGY_NAME));
    }
  }
}
