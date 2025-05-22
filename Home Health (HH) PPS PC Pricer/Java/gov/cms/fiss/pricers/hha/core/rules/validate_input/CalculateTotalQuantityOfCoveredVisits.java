package gov.cms.fiss.pricers.hha.core.rules.validate_input;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingRequest;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingResponse;
import gov.cms.fiss.pricers.hha.api.v2.RevenueLineData;
import gov.cms.fiss.pricers.hha.core.HhaPricerContext;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;

public class CalculateTotalQuantityOfCoveredVisits
    implements CalculationRule<HhaClaimPricingRequest, HhaClaimPricingResponse, HhaPricerContext> {

  /**
   * Calculates the total covered visits.
   *
   * <p>Converted from {@code 1000-FINAL-PAYMENT} in the COBOL code.
   *
   * <p>Converted from {@code 0100-PROCESS-RECORDS} in the HHDRV COBOL code.
   */
  @Override
  public void calculate(HhaPricerContext calculationContext) {
    final List<RevenueLineData> inputRevenueData = calculationContext.getRevenueLines();

    // COMPUTE H-HHA-REVENUE-SUM1-6-QTY-ALL ROUNDED =
    //                   H-HHA-REVENUE-QTY-COV-VISITS (1) +
    //                   H-HHA-REVENUE-QTY-COV-VISITS (2) +
    //                   H-HHA-REVENUE-QTY-COV-VISITS (3) +
    //                   H-HHA-REVENUE-QTY-COV-VISITS (4) +
    //                   H-HHA-REVENUE-QTY-COV-VISITS (5) +
    //                   H-HHA-REVENUE-QTY-COV-VISITS (6).
    int totalCoveredVisits = 0;
    for (final RevenueLineData inputRevenueEntry : inputRevenueData) {
      totalCoveredVisits +=
          ObjectUtils.defaultIfNull(inputRevenueEntry.getQuantityOfCoveredVisits(), 0);
    }
    calculationContext.getPaymentData().setTotalQuantityOfCoveredVisits(totalCoveredVisits);
  }
}
