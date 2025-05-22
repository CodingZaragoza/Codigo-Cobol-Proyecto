package gov.cms.fiss.pricers.esrd.core.rules.validation;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import gov.cms.fiss.pricers.esrd.core.codes.ReturnCode;
import org.apache.commons.lang3.StringUtils;

/**
 * Performs condition code validation.
 *
 * <p>Converted from {@code 1000-VALIDATE-BILL-ELEMENTS} in the COBOL code.
 *
 * @since 2020
 */
public class ValidatePPAPercentage
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public boolean shouldExecute(EsrdPricerContext calculationContext) {
    //    IF PPS-RTC = 00  THEN
    //    END-IF.
    return calculationContext.matchesReturnCode(ReturnCode.CALCULATION_STARTED_00);
  }

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();

    //       IF DemoCode = 94
    //          IF ETCIndicator = P OR B
    //             IF PPAPercentage IS null
    //                MOVE 99 TO PPS-RTC

    if (calculationContext.hasDemoCode(EsrdPricerContext.DEMO_CODE_ETC_PARTICIPANT)
        && StringUtils.equalsAny(
            claimData.getTreatmentChoicesIndicator(),
            EsrdPricerContext.ETC_INDICATOR_PPA,
            EsrdPricerContext.ETC_INDICATOR_BOTH_HDPA_AND_PPA)
        && (claimData.getPpaAdjustmentPercent() == null ||
        !calculationContext.isValidPpaAdjustmentPercent())) {
      calculationContext.applyReturnCode(ReturnCode.UNSPECIFIED_ERROR_OCCURRED_99);
      calculationContext.setCalculationCompleted();
    }
  }
}
