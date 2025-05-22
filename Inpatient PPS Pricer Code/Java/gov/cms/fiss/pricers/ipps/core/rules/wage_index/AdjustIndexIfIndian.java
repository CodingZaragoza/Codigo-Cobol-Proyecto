package gov.cms.fiss.pricers.ipps.core.rules.wage_index;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.EvaluatingCalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.ResultCode;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * Encapsulates the wage index adjutments for Indian-based provider types as a sub-sequence of
 * rules.
 *
 * <p>Converted from {@code 0850-N-GET-CBSA-INDIAN-WI} in the COBOL code.
 *
 * @since 2019
 */
public class AdjustIndexIfIndian
    extends EvaluatingCalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  public AdjustIndexIfIndian(
      List<CalculationRule<IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext>>
          calculationRules) {
    super(calculationRules);
  }

  @Override
  public boolean shouldExecute(IppsPricerContext calculationContext) {
    return StringUtils.equals(
        calculationContext.getProviderData().getProviderType(),
        IppsPricerContext.INDIAN_PROVIDER_TYPE);
  }

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    calculationContext.applyResultCode(ResultCode.RC_00_OK);

    //     IF  P-NEW-STATE = 02
    //             MOVE 98 TO H-CBSA-PROV-STATE
    //     ELSE
    //             MOVE 99 TO H-CBSA-PROV-STATE.
    if (calculationContext.getStateCode().equals(IppsPricerContext.INDIAN_SPECIAL_STATE_CODE)) {
      calculationContext.setCbsaLocation(IppsPricerContext.INDIAN_SPECIAL_CBSA_LOCATION);
    } else {
      calculationContext.setCbsaLocation(IppsPricerContext.INDIAN_DEFAULT_CBSA_LOCATION);
    }

    super.calculate(calculationContext);
  }
}
