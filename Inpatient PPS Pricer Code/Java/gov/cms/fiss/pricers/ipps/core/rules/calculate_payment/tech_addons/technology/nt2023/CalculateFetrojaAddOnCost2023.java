package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2023;

import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.CalculateTechnologyAddOnCost;
import gov.cms.fiss.pricers.ipps.core.tables.ClaimCodeType;
import gov.cms.fiss.pricers.ipps.core.tables.DataTables;

/**
 * Applies add-on costs for Fetroja if the technology was indicated in the claim.
 *
 * @since 2021
 */
public class CalculateFetrojaAddOnCost2023 extends CalculateTechnologyAddOnCost {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final DataTables dataTables = calculationContext.getDataTables();

    if (dataTables.codesMatch(
                "FETROJA1",
                ClaimCodeType.PROC,
                calculationContext.getClaimData().getProcedureCodes())
            && dataTables.codesMatch(
                "FETROJA2",
                ClaimCodeType.DIAG,
                calculationContext.getClaimData().getDiagnosisCodes())
            && dataTables.codesMatch(
                "FETROJA3",
                ClaimCodeType.DIAG,
                calculationContext.getClaimData().getDiagnosisCodes())
        || dataTables.codesMatch(
                "FETROJA1",
                ClaimCodeType.PROC,
                calculationContext.getClaimData().getProcedureCodes())
            && dataTables.codesMatch(
                "FETROJA4",
                ClaimCodeType.DIAG,
                calculationContext.getClaimData().getDiagnosisCodes())
            && dataTables.codesMatch(
                "FETROJA5",
                ClaimCodeType.DIAG,
                calculationContext.getClaimData().getDiagnosisCodes())) {
      aggregateNewTechCost(calculationContext, dataTables.getNewTechnologyAmount("FETROJA"));
    }
  }
}
