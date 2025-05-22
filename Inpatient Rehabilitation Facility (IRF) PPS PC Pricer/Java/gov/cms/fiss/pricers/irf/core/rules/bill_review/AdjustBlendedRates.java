package gov.cms.fiss.pricers.irf.core.rules.bill_review;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.api.v2.IrfPaymentData;
import gov.cms.fiss.pricers.irf.core.IrfPricerContext;
import gov.cms.fiss.pricers.irf.core.ResultCode;
import java.math.RoundingMode;
import org.apache.commons.lang3.StringUtils;

/**
 * Adjusts blended rates.
 *
 * <p>Converted from {@code 1000-EDIT-THE-BILL-INFO} in the COBOL code.
 */
public class AdjustBlendedRates
    implements CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  @Override
  public boolean shouldExecute(IrfPricerContext calculationContext) {
    return calculationContext.matchesReturnCode(ResultCode.OK_00);
  }

  @Override
  public void calculate(IrfPricerContext calculationContext) {
    final IrfPaymentData paymentData = calculationContext.getPaymentData();

    // IF (P-NEW-FY-BEGIN-DATE <= B-DISCHARGE-DATE)
    //    * code located below
    // ELSE
    if (LocalDateUtils.isAfter(
        calculationContext.getProviderFiscalYearBegin(),
        calculationContext.getClaimDischargeDate())) {
      //    MOVE 73 TO PPS-RTC
      calculationContext.applyResultCode(ResultCode.DISCHRG_PRIOR_TO_PROV_FY_73);

      return;
    }

    // IF P-NEW-FED-PPS-BLEND-IND = '4'
    if (StringUtils.equals(
        calculationContext.getPpsBlendIndicator(), IrfPricerContext.PROV_BLEND_INDICATOR_4)) {
      //    MOVE 1.0000 TO PPS-FED-RATE-PCT
      paymentData.setFederalRatePercent(
          IrfPricerContext.FEDERAL_RATE_FULL.setScale(4, RoundingMode.HALF_UP));
      //    MOVE 0.0000 TO PPS-FAC-RATE-PCT
      paymentData.setFacilityRatePercent(
          IrfPricerContext.FACILITY_RATE_0.setScale(4, RoundingMode.HALF_UP));
    } else if (StringUtils.equals(
        calculationContext.getPpsBlendIndicator(), IrfPricerContext.PROV_BLEND_INDICATOR_3)) {
      // ** RULE: PROVIDERS WITH A NEW FISCAL YEAR BEGIN DATE IN 2002 OR
      // ** GREATER AND LESS THAN THE BILL'S DISCHARGE DATE AND HAVING
      // ** THEIR BLEND RATE INDICATING THAT THE THERE IS A SPLIT BETWEEN
      // ** THE GOVERNMENT AND THE PROVIDER SHALL HAVE THEIR FEDERAL RATE
      // ** PERCENTAGE SET TO .6667% AND THEIR PROVIDER RATE SET TO .3333%

      // ELSE IF P-NEW-FED-PPS-BLEND-IND = '3'
      //    MOVE .6667 TO PPS-FED-RATE-PCT
      paymentData.setFederalRatePercent(
          IrfPricerContext.FEDERAL_RATE_TWO_THIRDS.setScale(4, RoundingMode.HALF_UP));
      //    MOVE .3333 TO PPS-FAC-RATE-PCT
      paymentData.setFacilityRatePercent(
          IrfPricerContext.FACILITY_RATE_ONE_THIRD.setScale(4, RoundingMode.HALF_UP));
    } else {
      // ** RULE: PAYMENTS CAN ONLY BE 100% FEDERAL REIMBURSED (BLEND
      // ** INDICATOR = 4) OR SPLIT BETWEEN THE PROVIDER AND THE FEDERAL
      // ** GOVERNMENT (BLEND INDICATOR = 3)
      // ** REQUIREMENT: THE SYSTEM MUST RETURN AN ERROR CODE (72) TO THE
      // ** CALLING PROGRAM IF THE PPS BLEND INDICATOR NOT A 3 (SPLIT
      // ** PAYMENT) OR A 4 (100% GOVERNMENT PAID)

      // ELSE
      //    MOVE 72 TO PPS-RTC
      calculationContext.applyResultCode(ResultCode.INVALID_BLEND_IND_72);
    }
  }
}
