package gov.cms.fiss.pricers.snf.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingRequest;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingResponse;
import gov.cms.fiss.pricers.snf.core.SnfPricerContext;
import gov.cms.fiss.pricers.snf.core.tables.VariablePerDiemEntry;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculateUtilizationDays
    implements CalculationRule<SnfClaimPricingRequest, SnfClaimPricingResponse, SnfPricerContext> {

  /**
   * Calculates the per diem utilization days for PT/OT and Nursing components.
   *
   * <p>Converted from {@code 6000-DAYS-LOOP-ROUTINE} in the COBOL code.
   */
  @Override
  public void calculate(SnfPricerContext context) {

    // PRIOR-DAYS
    final int priorDays = context.getClaimData().getPdpmPriorDays();
    // TOTAL-DAYS
    final int totalDays = context.getTotalDays();

    BigDecimal ptOtUtilization = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    BigDecimal ntaUtilization = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

    for (int i = priorDays + 1; i <= totalDays; i++) {
      final VariablePerDiemEntry entry =
          context.getDataTables().getVariablePerDiemAdjustmentFactors(i);

      //      COMPUTE WS-PT-OT-UTIL ROUNDED =
      //              WS-PT-OT-UTIL + VPD-PT-OT-FACT (VX1).
      ptOtUtilization = ptOtUtilization.add(entry.getPhysicalAndOccupationalAdjFactor());

      //      COMPUTE WS-NTA-UTIL ROUNDED =
      //              WS-NTA-UTIL + VPD-NTA-FACT (VX1).
      ntaUtilization = ntaUtilization.add(entry.getNonTherapyAncillaryAdjFactor());
    }

    context.setPhysicalAndOccupationalTherapyUtilization(ptOtUtilization);
    context.setNonTherapyAncillaryUtilization(ntaUtilization);
  }
}
