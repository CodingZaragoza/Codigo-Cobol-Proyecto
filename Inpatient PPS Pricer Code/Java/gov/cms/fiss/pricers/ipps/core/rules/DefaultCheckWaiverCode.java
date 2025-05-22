package gov.cms.fiss.pricers.ipps.core.rules;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.ResultCode;
import org.apache.commons.lang3.StringUtils;

/**
 * Validates the waiver code provided.
 *
 * <p>Converted from {@code 1000-EDIT-THE-BILL-INFO} in the COBOL code (continued).
 *
 * @since 2019
 */
public class DefaultCheckWaiverCode
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();

    //     IF  PPS-RTC < 50
    //         IF  P-NEW-WAIVER-STATE
    //             MOVE 53 TO PPS-RTC
    if (!calculationContext.isErrorResult()
        && StringUtils.equals(providerData.getWaiverIndicator(), "Y")) {
      calculationContext.applyResultCode(ResultCode.RC_53_WAIVER_STATE_NOT_CALC);
      calculationContext.clearAdditionalVariablesContextState();
    }
  }
}
