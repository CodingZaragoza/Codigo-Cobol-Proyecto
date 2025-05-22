package gov.cms.fiss.pricers.esrd.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import gov.cms.fiss.pricers.esrd.core.codes.ReturnCode;

public class InitializeData
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  /**
   * Initializes the claim data.
   *
   * <p>Converted from {@code 0000-START-TO-FINISH} in the COBOL code.
   *
   * @since 2020
   */
  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    // TO MAKE SURE THAT ALL BILLS ARE 100% PPS
    //    MOVE 'Y' TO P-PROV-WAIVE-BLEND-PAY-INDIC.
    calculationContext.setBlendedPaymentWaived(true);

    //     MOVE CAL-VERSION                  TO PPS-CALC-VERS-CD.
    calculationContext
        .getOutput()
        .setCalculationVersion(calculationContext.getCalculationVersion());

    //     MOVE ZEROS                        TO PPS-RTC.
    calculationContext.applyReturnCode(ReturnCode.CALCULATION_STARTED_00);
  }
}
