package gov.cms.fiss.pricers.ipps.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;

/**
 * Clears the output state if the claim processing has resulted in an error.
 *
 * <p>Converted from {@code 3000-CALC-PAYMENT} in the COBOL code (continued).
 *
 * @since 2019
 */
public class DefaultClearOutputOnError
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public boolean shouldExecute(IppsPricerContext calculationContext) {
    return calculationContext.isErrorResult();
  }

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    //             MOVE ALL '0' TO PPS-OPER-HSP-PART
    //                             PPS-OPER-FSP-PART
    //                             PPS-OPER-OUTLIER-PART
    //                             PPS-OUTLIER-DAYS
    //                             PPS-REG-DAYS-USED
    //                             PPS-LTR-DAYS-USED
    //                             PPS-TOTAL-PAYMENT
    //                             WK-HAC-TOTAL-PAYMENT
    //                             PPS-OPER-DSH-ADJ
    //                             PPS-OPER-IME-ADJ
    //                             H-DSCHG-FRCTN
    //                             H-DRG-WT-FRCTN
    //                             HOLD-ADDITIONAL-VARIABLES
    //                             HOLD-CAPITAL-VARIABLES
    //                             HOLD-CAPITAL2-VARIABLES
    //                             HOLD-OTHER-VARIABLES
    //                             HOLD-PC-OTH-VARIABLES
    //                             H-ADDITIONAL-PAY-INFO-DATA
    //                             H-ADDITIONAL-PAY-INFO-DATA2.
    calculationContext.zeroResponse();
  }
}
