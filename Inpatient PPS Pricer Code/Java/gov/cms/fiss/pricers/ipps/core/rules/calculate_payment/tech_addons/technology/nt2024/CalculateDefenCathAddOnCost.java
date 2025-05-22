package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2024;

import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.CalculateTechnologyAddOnCost;
import gov.cms.fiss.pricers.ipps.core.tables.ClaimCodeType;
import gov.cms.fiss.pricers.ipps.core.tables.DataTables;
import java.time.LocalDate;

/**
 * Applies add-on costs for DefenCath if the technology was indicated in the claim.
 *
 * @since 2024
 */
public class CalculateDefenCathAddOnCost extends CalculateTechnologyAddOnCost {

  private static final String TECHNOLOGY_NAME = "DEFENCATH";

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final DataTables dataTables = calculationContext.getDataTables();

    if (LocalDateUtils.isAfterOrEqual(
            calculationContext.getDischargeDate(), LocalDate.of(2024, 01, 01))
        && (dataTables.codesMatch(
            TECHNOLOGY_NAME,
            ClaimCodeType.PROC,
            calculationContext.getClaimData().getProcedureCodes()))) {
      aggregateNewTechCost(calculationContext, dataTables.getNewTechnologyAmount(TECHNOLOGY_NAME));
    }
  }
}
