package gov.cms.fiss.pricers.ipps.core.rules.wage_index;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.ResultCode;

/**
 * Performs wage index lookup from the provided CBSA location.
 *
 * <p>Converted from {@code 0550-GET-CBSA} in the COBOL code (continued).
 *
 * @since 2019
 */
public class DetermineCbsaWageIndex
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    //     IF P-NEW-EFF-DATE < 20041001
    //        PERFORM 0500-GET-MSA THRU 0500-EXIT
    //     ELSE
    //        PERFORM 0550-GET-CBSA THRU 0550-EXIT.
    // ***     RTC = 52  --  WAGE-INDEX NOT FOUND
    //     IF PPS-RTC = 52
    //          MOVE ALL '0' TO  PPS-ADDITIONAL-VARIABLES
    //          GOBACK.
    calculationContext.setCbsaWageIndexEntry(
        calculationContext.getCbsaWageIndexEntry(calculationContext.getCbsaLocation()));
    if (calculationContext.getCbsaWageIndexEntry() == null) {
      // No entry found in the table for this combination of CBSA (geolocation) and effective date.
      calculationContext.applyResultCode(ResultCode.RC_52_INVALID_WAGE_INDEX);
      calculationContext.setCalculationCompleted();
    }
  }
}
