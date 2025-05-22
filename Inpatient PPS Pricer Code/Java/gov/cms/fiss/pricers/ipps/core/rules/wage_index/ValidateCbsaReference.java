package gov.cms.fiss.pricers.ipps.core.rules.wage_index;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.CbsaReference;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.ResultCode;
import org.apache.commons.lang3.StringUtils;

/**
 * Validates the wage index obtained for claim processing.
 *
 * <p>Converted from {@code 0550-BYPASS} in the COBOL code.
 *
 * @since 2019
 */
public class ValidateCbsaReference
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public boolean shouldExecute(IppsPricerContext calculationContext) {
    return ResultCode.RC_52_INVALID_WAGE_INDEX != calculationContext.getResultCode();
  }

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final CbsaReference cbsaReference = calculationContext.getCbsaReference();

    //    IF W-NEW-CBSA-WI = 00.0000
    //       MOVE ALL '0' TO PPS-ADDITIONAL-VARIABLES
    //       MOVE 52 TO PPS-RTC
    //       GOBACK.
    if (cbsaReference.getWageIndex() != null
        && BigDecimalUtils.isZero(cbsaReference.getWageIndex())) {
      calculationContext.applyResultCode(ResultCode.RC_52_INVALID_WAGE_INDEX);

      return;
    }

    calculationContext.setCbsaLocation(
        StringUtils.trim(calculationContext.getProviderData().getCbsaStandardizedAmountLocation()));
  }
}
