package gov.cms.fiss.pricers.ipps.core.rules.rules2021.calculate_payment;

import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.ResultCode;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.DetermineOutlierCosts;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Determine cost outlier status and applicable review codes.
 *
 * <p>Converted from {@code 3600-CALC-OUTLIER} in the COBOL code (continued).
 *
 * @since 2019
 */
public class DetermineOutlierCosts2021 extends DetermineOutlierCosts {

  @Override
  protected void dollarThresholdRTCBlock(IppsPricerContext calculationContext) {
    //     MOVE 0 TO H-OPER-CHARGE-THRESHOLD.
    calculationContext.setOperatingChargeThreshold(BigDecimal.ZERO);

    //     IF PPS-RTC = 02
    //       IF H-CAPI-CSTCHG-RATIO > 0 OR
    //          H-OPER-CSTCHG-RATIO > 0
    //             COMPUTE H-OPER-CHARGE-THRESHOLD ROUNDED =
    //                     (H-CAPI-COST-OUTLIER  +
    //                      H-OPER-COST-OUTLIER)
    //                             /
    //                    (H-CAPI-CSTCHG-RATIO  +
    //                     H-OPER-CSTCHG-RATIO)
    //             ON SIZE ERROR MOVE 0 TO H-OPER-CHARGE-THRESHOLD
    //       ELSE MOVE 0 TO H-OPER-CHARGE-THRESHOLD.
    if (calculationContext.getResultCode() == ResultCode.RC_02_TRANSFER_PAID_AS_OUTLIER) {
      if (BigDecimalUtils.isGreaterThanZero(
              calculationContext.getCapitalOperatingCostToChargeRatio())
          || BigDecimalUtils.isGreaterThanZero(
              calculationContext.getOperatingCostToChargeRatio())) {
        calculationContext.setOperatingChargeThreshold(
            calculationContext
                .getCapitalCostOutlier()
                .add(calculationContext.getOperatingCostOutlier())
                .divide(
                    calculationContext
                        .getCapitalOperatingCostToChargeRatio()
                        .add(calculationContext.getOperatingCostToChargeRatio()),
                    9,
                    RoundingMode.HALF_UP));
      } else {
        calculationContext.setOperatingChargeThreshold(BigDecimal.ZERO);
      }
    }
  }
}
