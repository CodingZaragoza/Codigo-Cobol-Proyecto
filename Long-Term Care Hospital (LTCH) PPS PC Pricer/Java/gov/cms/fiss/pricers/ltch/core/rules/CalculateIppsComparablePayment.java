package gov.cms.fiss.pricers.ltch.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.EvaluatingCalculationRule;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.core.LtchPricerContext;
import gov.cms.fiss.pricers.ltch.core.codes.ErrorCode;
import java.util.List;

/**
 *
 *
 * <pre>
 * ***************************************************************
 * *   CALCULATE THE IPPS COMPARABLE PAYMENT COMPONENTS AND      *
 * *   PER DIEM PAYMENT AMOUNT                                   *
 * ***************************************************************
 * </pre>
 *
 * <p>Converted from {@code 3650-SS-IPPS-COMP-PMT}.
 */
public class CalculateIppsComparablePayment
    extends EvaluatingCalculationRule<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {

  /** 7000-SET-FINAL-RETURN-CODES. SET FINAL RETURN CODES FOR PRICED CLAIMS */
  public CalculateIppsComparablePayment(
      List<CalculationRule<LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext>>
          calculationRules) {
    super(calculationRules);
  }

  @Override
  public boolean shouldExecute(LtchPricerContext calculationContext) {
    return calculationContext.isPaymentBlendOrSiteNeutral()
            && !ErrorCode.isErrorCode(calculationContext.getReturnCode())
        || Boolean.TRUE.equals(calculationContext.getIppsComparablePayment());
  }
}
