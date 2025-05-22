package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.totals;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.api.v2.IppsPaymentData;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import java.math.BigDecimal;

/**
 * Determine the operating outlier part amount.
 *
 * <p>Converted from {@code 3800-CALC-TOT-AMT} in the COBOL code.
 *
 * @since 2019
 */
public class CalculateOperatingTotals
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final IppsPaymentData paymentData = calculationContext.getPaymentData();

    // ***********************************************************
    // ***  CALCULATE FINAL TOTALS FOR OPERATING
    //     IF (H-CAPI-OUTLIER > 0 AND
    //         PPS-OPER-OUTLIER-PART = 0)
    //            COMPUTE PPS-OPER-OUTLIER-PART =
    //                    PPS-OPER-OUTLIER-PART + .01.
    if (BigDecimalUtils.isGreaterThanZero(calculationContext.getCapitalOutlierCost())
        && BigDecimalUtils.isZero(paymentData.getOperatingOutlierPaymentPart())) {
      paymentData.setOperatingOutlierPaymentPart(
          paymentData.getOperatingOutlierPaymentPart().add(new BigDecimal("0.01")));
    }
  }
}
