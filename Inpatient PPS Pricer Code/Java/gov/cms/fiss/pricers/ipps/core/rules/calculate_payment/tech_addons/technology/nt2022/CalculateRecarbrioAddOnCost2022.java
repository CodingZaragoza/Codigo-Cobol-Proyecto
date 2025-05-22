package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2022;

import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.CalculateTechnologyAddOnCost;
import gov.cms.fiss.pricers.ipps.core.tables.ClaimCodeType;
import gov.cms.fiss.pricers.ipps.core.tables.DataTables;

/**
 * Applies add-on costs for Recarbrio if the technology was indicated in the claim.
 *
 * @since 2021
 */
public class CalculateRecarbrioAddOnCost2022 extends CalculateTechnologyAddOnCost {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final DataTables dataTables = calculationContext.getDataTables();

    if (dataTables.codesMatch(
        "RECARBRIO1", ClaimCodeType.PROC, calculationContext.getClaimData().getProcedureCodes())) {
      if (dataTables.codesMatch(
                  "RECARBRIO2",
                  ClaimCodeType.DIAG,
                  calculationContext.getClaimData().getDiagnosisCodes())
              && dataTables.codesMatch(
                  "RECARBRIO3",
                  ClaimCodeType.DIAG,
                  calculationContext.getClaimData().getDiagnosisCodes())
          || dataTables.codesMatch(
                  "RECARBRIO4",
                  ClaimCodeType.DIAG,
                  calculationContext.getClaimData().getDiagnosisCodes())
              && dataTables.codesMatch(
                  "RECARBRIO5",
                  ClaimCodeType.DIAG,
                  calculationContext.getClaimData().getDiagnosisCodes())) {
        aggregateNewTechCost(calculationContext, dataTables.getNewTechnologyAmount("RECARBRIOH"));
      } else {
        aggregateNewTechCost(calculationContext, dataTables.getNewTechnologyAmount("RECARBRIOL"));
      }
    }
  }
}
