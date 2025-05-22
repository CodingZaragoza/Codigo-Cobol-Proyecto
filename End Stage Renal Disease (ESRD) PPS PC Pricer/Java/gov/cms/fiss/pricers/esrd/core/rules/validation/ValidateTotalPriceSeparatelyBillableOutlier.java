package gov.cms.fiss.pricers.esrd.core.rules.validation;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import gov.cms.fiss.pricers.esrd.core.codes.ReturnCode;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Performs total price separately-billable outlier validation.
 *
 * <p>Converted from {@code 1000-VALIDATE-BILL-ELEMENTS} in the COBOL code.
 *
 * @since 2020
 */
public class ValidateTotalPriceSeparatelyBillableOutlier
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
    // The below COBOL is to check if the value is a number, which can't happen in Java.
    // Instead, DDS prefers defaulting to zero if the value is null.
    //       IF (B-TOT-PRICE-SB-OUTLIER NOT NUMERIC) THEN
    //           MOVE 76                     TO PPS-RTC
    //       END-IF
    if (null == calculationContext.getClaimData().getTotalPriceSeparatelyBillableOutlier()) {
      calculationContext
          .getClaimData()
          .setTotalPriceSeparatelyBillableOutlier(
              BigDecimal.ZERO.setScale(2, RoundingMode.UNNECESSARY));
    }
  }
}
