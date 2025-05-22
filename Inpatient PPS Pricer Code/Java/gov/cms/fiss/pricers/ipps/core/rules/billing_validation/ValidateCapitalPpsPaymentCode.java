package gov.cms.fiss.pricers.ipps.core.rules.billing_validation;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.ResultCode;
import org.apache.commons.lang3.StringUtils;

/**
 * Validates the capital PPS pay code.
 *
 * <p>Converted from {@code 1000-EDIT-THE-BILL-INFO} in the COBOL code (continued).
 *
 * @since 2019
 */
public class ValidateCapitalPpsPaymentCode
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public boolean shouldExecute(IppsPricerContext calculationContext) {
    return ResultCode.RC_00_OK == calculationContext.getResultCode();
  }

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();

    //     IF PPS-RTC = 00
    //           IF P-NEW-CAPI-NEW-HOSP NOT = 'Y'
    //                 IF P-NEW-CAPI-PPS-PAY-CODE NOT = 'B' AND
    //                                            NOT = 'C'
    //                 MOVE 65 TO PPS-RTC.
    if (!StringUtils.equals(providerData.getNewHospital(), "Y")
        && !StringUtils.equalsAny(providerData.getCapitalPpsPaymentCode(), "B", "C")) {
      calculationContext.applyResultCode(ResultCode.RC_65_PAY_CODE_NOT_ABC);
    }
  }
}
