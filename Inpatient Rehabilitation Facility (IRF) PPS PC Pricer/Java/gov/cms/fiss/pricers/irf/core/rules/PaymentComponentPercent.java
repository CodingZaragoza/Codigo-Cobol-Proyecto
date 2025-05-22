package gov.cms.fiss.pricers.irf.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.EvaluatingCalculationRule;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.core.IrfPricerContext;
import gov.cms.fiss.pricers.irf.core.ResultCode;
import java.util.List;

/**
 * Performs payment percent calculations.
 *
 * <p>Converted from {@code 3000-CALC-PAYMENT} in the COBOL code.
 */
public class PaymentComponentPercent
    extends EvaluatingCalculationRule<
        IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  public PaymentComponentPercent(
      List<CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext>>
          calculationRules) {
    super(calculationRules);
  }

  @Override
  public boolean shouldExecute(IrfPricerContext calculationContext) {
    return calculationContext.matchesReturnCode(ResultCode.OK_00);
  }
}
