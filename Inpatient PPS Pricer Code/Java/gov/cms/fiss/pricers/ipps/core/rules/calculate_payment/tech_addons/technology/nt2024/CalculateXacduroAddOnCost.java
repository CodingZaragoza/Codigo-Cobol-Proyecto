package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2024;

import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.CalculateTechnologyAddOnCost;
import gov.cms.fiss.pricers.ipps.core.tables.ClaimCodeType;
import gov.cms.fiss.pricers.ipps.core.tables.DataTables;

/**
 * Applies add-on costs for Xacduro if the technology was indicated in the claim.
 *
 * @since 2024
 */
public class CalculateXacduroAddOnCost extends CalculateTechnologyAddOnCost {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final DataTables dataTables = calculationContext.getDataTables();

    if (dataTables.codesMatch(
                "XACDURO1",
                ClaimCodeType.PROC,
                calculationContext.getClaimData().getProcedureCodes())
            && dataTables.codesMatch(
                "XACDURO2",
                ClaimCodeType.DIAG,
                calculationContext.getClaimData().getDiagnosisCodes())
            && dataTables.codesMatch(
                "XACDURO3",
                ClaimCodeType.DIAG,
                calculationContext.getClaimData().getDiagnosisCodes())
        || dataTables.codesMatch(
                "XACDURO1",
                ClaimCodeType.PROC,
                calculationContext.getClaimData().getProcedureCodes())
            && dataTables.codesMatch(
                "XACDURO4",
                ClaimCodeType.DIAG,
                calculationContext.getClaimData().getDiagnosisCodes())
            && dataTables.codesMatch(
                "XACDURO5",
                ClaimCodeType.DIAG,
                calculationContext.getClaimData().getDiagnosisCodes())) {
      aggregateNewTechCost(calculationContext, dataTables.getNewTechnologyAmount("XACDURO"));
    }
  }
}
