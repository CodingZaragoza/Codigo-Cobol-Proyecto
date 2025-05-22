package gov.cms.fiss.pricers.ipps.core.rules.cost_factor_determination;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.tables.DataTables;
import gov.cms.fiss.pricers.ipps.core.tables.MidnightTableEntry;

/**
 * Determine the claim's eligibility for an adjustment (MidnightAdjustmentFactor) based on a
 * two-midnight stay. The applicable factor is associated with a qualifying value located within the
 * co-opted MSA geolocation field.
 *
 * <p>Converted from {@code 2100-MIDNIGHT-FACTORS} in the COBOL code.
 *
 * @since 2019
 */
public class AdjustForMidnightFactors
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public boolean shouldExecute(IppsPricerContext calculationContext) {
    return calculationContext.getGeolocationAsInt() >= 9400
        && calculationContext.getGeolocationAsInt() <= 9900;
  }

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final DataTables dataTables = calculationContext.getDataTables();

    //    IF P-NEW-GEO-LOC-MSA9 >= 9400 AND
    //       P-NEW-GEO-LOC-MSA9 <= 9900
    //       PERFORM 2100-MIDNIGHT-FACTORS THRU 2100-EXIT
    //    ELSE
    //       MOVE 1 TO HLD-MID-ADJ-FACT
    //       GO TO 2000-EXIT.

    // The midnight adjustment factor became effective as of 10/01/2016.
    final MidnightTableEntry entry =
        dataTables.getMidnightEntry(calculationContext.getMidnightAdjustmentGeolocation());
    if (entry != null) {
      calculationContext.setMidnightAdjustmentFactor(entry.getAdjustmentFactor());
    }
  }
}
