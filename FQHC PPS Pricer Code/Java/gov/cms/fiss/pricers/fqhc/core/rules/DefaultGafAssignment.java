package gov.cms.fiss.pricers.fqhc.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.fqhc.api.v2.FqhcClaimPricingRequest;
import gov.cms.fiss.pricers.fqhc.api.v2.FqhcClaimPricingResponse;
import gov.cms.fiss.pricers.fqhc.api.v2.FqhcPaymentData;
import gov.cms.fiss.pricers.fqhc.core.FqhcPricerContext;
import gov.cms.fiss.pricers.fqhc.core.daily.DaySummary;
import java.math.RoundingMode;

/**
 * Determines effective GAF rate based on the latest service date.
 *
 * <p>Converted from {@code 2000-PROCESS-CLAIM} in the COBOL code (continued).
 */
public class DefaultGafAssignment
    implements CalculationRule<
        FqhcClaimPricingRequest, FqhcClaimPricingResponse, FqhcPricerContext> {

  @Override
  public void calculate(FqhcPricerContext calculationContext) {
    // Set the latest effective GAF to the claims GAF
    final DaySummary latestDaySummary = calculationContext.getLastDaySummary();

    if (null != latestDaySummary) {
      // Need to set the scale to 4 to match the output required even though the CSV file only
      // has 3 decimal places
      // *----------------------------------------------------------------*
      // *   ASSIGN LATEST EFFECTIVE GAF AS CLAIM GAF                     *
      // *----------------------------------------------------------------*
      //   SET W-DS-INDX TO W-DAY-SUM-MAX.
      //   MOVE W-DS-GAF (W-DS-INDX) TO HC-GEO-ADJ-FACT.
      final FqhcPaymentData outputRecord = calculationContext.getPaymentData();
      outputRecord.setGeographicAdjustmentFactor(
          latestDaySummary.getGafRate().setScale(4, RoundingMode.HALF_UP));
    }
  }
}
