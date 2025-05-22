package gov.cms.fiss.pricers.irf.core.rules.bill_review;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.core.IrfPricerContext;
import gov.cms.fiss.pricers.irf.core.ResultCode;

/**
 * Validate provider status.
 *
 * <pre>
 * ** RULE: A CLAIM WILL NOT BE PAID IF THE CLAIM'S DISCHARGE DATE
 * ** IS ON OR AFTER THE DATE WHEN A PROVIDER TERMINATED COVERAGE
 * ** REQUIREMENT: THE SYSTEM MUST RETURN AN ERROR CODE (51) WHEN
 * ** THE BILL'S DISCHARGE DATE IS ON/AFTER THE PROVIDER TERMINATION
 * ** DATE
 * </pre>
 *
 * Converted from {@code 1000-EDIT-THE-BILL-INFO} in the COBOL code.
 */
public class ValidateProviderStatus
    implements CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  @Override
  public boolean shouldExecute(IrfPricerContext calculationContext) {
    return calculationContext.matchesReturnCode(ResultCode.OK_00);
  }

  @Override
  public void calculate(IrfPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();

    // For the below COBOL, the termination date cannot be "00000000". If it's not null, it
    // IF P-NEW-TERMINATION-DATE > 00000000
    //    IF B-DISCHARGE-DATE >= P-NEW-TERMINATION-DATE
    if (null != providerData.getTerminationDate()
        && LocalDateUtils.isAfterOrEqual(
            calculationContext.getClaimDischargeDate(), providerData.getTerminationDate())) {
      //    MOVE 51 TO PPS-RTC.
      calculationContext.applyResultCode(ResultCode.PROV_REC_TERMINATED_51);
    }
  }
}
