package gov.cms.fiss.pricers.ipps.core.rules.rules2021.calculate_payment;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.CalculateBundleReduction;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Determine the bundle reduction model 1 amount.
 *
 * <p>Converted from {@code 8000-CALC-BUNDLE-REDU}, {@code 8000-COMPUTE-BUNDLE} in the COBOL code.
 *
 * @since 2019
 */
public class CalculateBundleReduction2021 extends CalculateBundleReduction {

  @Override
  protected void calculateBundleUsingModel1(IppsPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();
    //    8000-COMPUTE-BUNDLE.
    //        IF B-DISCHARGE-DATE < 20140401 AND
    //           P-MODEL1-BUNDLE-DISPRCNT = .01
    //             COMPUTE WK-MODEL1-BUNDLE-DISPRCNT =
    //                (1 - (P-MODEL1-BUNDLE-DISPRCNT * .5))
    //        IF B-DISCHARGE-DATE > 20140331 AND
    //           B-DISCHARGE-DATE < 20170101
    //             COMPUTE WK-MODEL1-BUNDLE-DISPRCNT =
    //                (1 - (P-MODEL1-BUNDLE-DISPRCNT * 1)).
    //        IF B-DISCHARGE-DATE > 20161231
    //             COMPUTE WK-MODEL1-BUNDLE-DISPRCNT =
    //               (1 - (P-MODEL1-BUNDLE-DISPRCNT * 0)).
    //        COMPUTE H-BUNDLE-ADJUST-AMT ROUNDED =
    //           ((WK-MODEL1-BUNDLE-DISPRCNT * H-OPER-BASE-DRG-PAY) -
    //             H-OPER-BASE-DRG-PAY).
    //        COMPUTE H-BUNDLE-ADJUST-AMT ROUNDED = H-BUNDLE-ADJUST-AMT.
    final BigDecimal model1BundleDisprcnt =
        BigDecimal.ONE.subtract(providerData.getBundleModel1Discount().multiply(BigDecimal.ZERO));
    calculationContext.setBundledAdjustmentPayment(
        model1BundleDisprcnt
            .multiply(calculationContext.getOperatingBaseDrgPayment())
            .subtract(calculationContext.getOperatingBaseDrgPayment())
            .setScale(2, RoundingMode.HALF_UP));
  }
}
