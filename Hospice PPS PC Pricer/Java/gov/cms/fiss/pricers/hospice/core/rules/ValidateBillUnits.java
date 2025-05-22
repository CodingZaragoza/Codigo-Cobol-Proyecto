package gov.cms.fiss.pricers.hospice.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.hospice.api.v2.BillingGroupData;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingRequest;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingResponse;
import gov.cms.fiss.pricers.hospice.core.HospicePricerContext;
import gov.cms.fiss.pricers.hospice.core.codes.ReturnCode;
import java.util.List;

public class ValidateBillUnits
    implements CalculationRule<
        HospiceClaimPricingRequest, HospiceClaimPricingResponse, HospicePricerContext> {
  /**
   * Ensures billing units are within limits.
   *
   * <p>Converted from {@code 2020-V200-PROCESS-DATA} in the COBOL code.
   */
  @Override
  public void calculate(HospicePricerContext calculationContext) {
    // *---------------------------------------------------------------
    // *  VALIDATE BILL INPUT DATA - UNITS
    // *---------------------------------------------------------------
    //     IF BILL-UNITS1 > 1000 OR
    //        BILL-UNITS2 > 1000 OR
    //        BILL-UNITS3 > 1000 OR
    //        BILL-UNITS4 > 1000
    //        MOVE '10' TO BILL-RTC
    //        GO TO 2020-V200-PROCESS-EXIT
    //     END-IF.
    final List<BillingGroupData> billGroups = calculationContext.getBillingGroups();
    if (billGroups.stream().anyMatch(billGroup -> billGroup.getUnits() > 1000)) {
      calculationContext.applyReturnCodeAndComplete(ReturnCode.INVALID_UNITS_10);
    }
  }
}
