package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.ResultCode;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Determine the operating cost to charge ratio, operating share dollar threshold amount, and the
 * capital share dollar threshold amount.
 *
 * <p>Converted from {@code 3600-CALC-OUTLIER} in the COBOL code (continued).
 *
 * @since 2019
 */
public class CalculateOperAndCapitalCostOutliers
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public boolean shouldExecute(IppsPricerContext calculationContext) {
    return !ArrayUtils.contains(
        new ResultCode[] {
          ResultCode.RC_65_PAY_CODE_NOT_ABC,
          ResultCode.RC_67_OUTLIER_LOS_GT_COVERED_DAYS,
          ResultCode.RC_68_INVALID_VBPF_IN_PSF
        },
        calculationContext.getResultCode());
  }

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    //     IF OUTLIER-RECON-FLAG = 'Y'
    //        COMPUTE H-OPER-CSTCHG-RATIO ROUNDED =
    //               (H-OPER-CSTCHG-RATIO + .2).
    if (calculationContext.isOutlierReconciliation()) {
      calculationContext.setOperatingCostToChargeRatio(
          calculationContext
              .getOperatingCostToChargeRatio()
              .add(new BigDecimal("0.2"))
              .setScale(3, RoundingMode.HALF_UP));
    }

    //     IF H-CAPI-CSTCHG-RATIO > 0 OR
    //        H-OPER-CSTCHG-RATIO > 0
    //        COMPUTE H-OPER-SHARE-DOLL-THRESHOLD ROUNDED =
    //                H-OPER-CSTCHG-RATIO /
    //               (H-OPER-CSTCHG-RATIO + H-CAPI-CSTCHG-RATIO)
    //        COMPUTE H-CAPI-SHARE-DOLL-THRESHOLD ROUNDED =
    //                H-CAPI-CSTCHG-RATIO /
    //               (H-OPER-CSTCHG-RATIO + H-CAPI-CSTCHG-RATIO)
    //     ELSE
    //        MOVE 0 TO H-OPER-SHARE-DOLL-THRESHOLD
    //                  H-CAPI-SHARE-DOLL-THRESHOLD.
    if (BigDecimalUtils.isGreaterThanZero(calculationContext.getCapitalOperatingCostToChargeRatio())
        || BigDecimalUtils.isGreaterThanZero(calculationContext.getOperatingCostToChargeRatio())) {
      calculationContext.setOperatingShareDollarThreshold(
          calculationContext
              .getOperatingCostToChargeRatio()
              .divide(
                  calculationContext
                      .getOperatingCostToChargeRatio()
                      .add(calculationContext.getCapitalOperatingCostToChargeRatio()),
                  9,
                  RoundingMode.HALF_UP));
      calculationContext.setCapitalShareDollarThreshold(
          calculationContext
              .getCapitalOperatingCostToChargeRatio()
              .divide(
                  calculationContext
                      .getOperatingCostToChargeRatio()
                      .add(calculationContext.getCapitalOperatingCostToChargeRatio()),
                  9,
                  RoundingMode.HALF_UP));
    }
  }
}
