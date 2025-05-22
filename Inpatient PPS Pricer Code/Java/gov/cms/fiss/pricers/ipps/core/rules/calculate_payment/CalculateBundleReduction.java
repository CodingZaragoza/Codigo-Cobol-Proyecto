package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
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
 * Determine the bundle reduction model 1 amount.
 *
 * <p>Converted from {@code 8000-CALC-BUNDLE-REDU}, {@code 8000-COMPUTE-BUNDLE} in the COBOL code.
 *
 * @since 2019
 */
public class CalculateBundleReduction
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
    final InpatientProviderData providerData = calculationContext.getProviderData();

    // ***********************************************************
    // ***** CASES INVOLVING BUNDLE PROCESS ADJUSTMENTS
    // ***********************************************************
    //     MOVE 0 TO H-BUNDLE-ADJUST-AMT.
    calculationContext.setBundledAdjustmentPayment(BigDecimal.ZERO);

    //     MOVE 0 TO WK-MODEL1-BUNDLE-DISPRCNT.
    //     IF '61' =  B-DEMO-CODE1  OR
    //                B-DEMO-CODE2  OR
    //                B-DEMO-CODE3  OR
    //                B-DEMO-CODE4
    //         NEXT SENTENCE
    //     ELSE
    //         MOVE ZEROES TO H-BUNDLE-ADJUST-AMT
    //           GO TO 8000-EXIT.
    //     IF P-MODEL1-BUNDLE-DISPRCNT > .00
    //           GO TO 8000-COMPUTE-BUNDLE
    //     ELSE
    //           NEXT SENTENCE.
    //     MOVE ZEROES TO H-BUNDLE-ADJUST-AMT
    //           GO TO 8000-EXIT.
    if (calculationContext.getProvidedDemoCodes() != null
            && !calculationContext.getProvidedDemoCodes().contains("61")
        || BigDecimalUtils.isLessThanOrEqualToZero(providerData.getBundleModel1Discount())) {
      calculationContext.setBundledAdjustmentPayment(BigDecimal.ZERO);
      return;
    }

    calculateBundleUsingModel1(calculationContext);
  }

  protected void calculateBundleUsingModel1(IppsPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();
    // 8000-COMPUTE-BUNDLE.
    //     IF  B-DISCHARGE-DATE < 20140401 AND
    //         P-MODEL1-BUNDLE-DISPRCNT = .01
    //         COMPUTE WK-MODEL1-BUNDLE-DISPRCNT =
    //          (1 - (P-MODEL1-BUNDLE-DISPRCNT * .5))
    //     ELSE
    //         COMPUTE WK-MODEL1-BUNDLE-DISPRCNT =
    //          (1 - (P-MODEL1-BUNDLE-DISPRCNT * 1)).
    //        COMPUTE H-BUNDLE-ADJUST-AMT      ROUNDED =
    //              ((WK-MODEL1-BUNDLE-DISPRCNT *
    //                                     H-OPER-BASE-DRG-PAY) -
    //                H-OPER-BASE-DRG-PAY).
    //        COMPUTE H-BUNDLE-ADJUST-AMT ROUNDED = H-BUNDLE-ADJUST-AMT.
    final BigDecimal model1BundleDisprcnt =
        BigDecimal.ONE.subtract(providerData.getBundleModel1Discount());
    calculationContext.setBundledAdjustmentPayment(
        model1BundleDisprcnt
            .multiply(calculationContext.getOperatingBaseDrgPayment())
            .subtract(calculationContext.getOperatingBaseDrgPayment())
            .setScale(2, RoundingMode.HALF_UP));
  }
}
