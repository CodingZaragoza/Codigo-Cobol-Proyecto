package gov.cms.fiss.pricers.esrd.core.rules.rules_2021.move_results;

import gov.cms.fiss.pricers.esrd.api.v2.EsrdPaymentData;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import gov.cms.fiss.pricers.esrd.core.rules.rules_2020_2.move_results.MoveBaseResults2020Dot2;
import java.math.RoundingMode;

/**
 * Applies the calculation results to the bundled data.
 *
 * <p>Converted from {@code 9100-MOVE-RESULTS} in the COBOL code.
 *
 * @since 2020
 */
public class MoveBaseResults2021 extends MoveBaseResults2020Dot2 {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    super.calculate(calculationContext);

    final EsrdPaymentData paymentData = calculationContext.getPaymentData();

    //     MOVE H-TPNIES-PAYMENT             TO TPNIES-RETURN.
    paymentData.setTpniesPaymentAdjustmentAmount(calculationContext.getTpniesPayment());
    //
    //     MOVE H-NETWORK-REDUCTION          TO
    //                                    NETWORK-REDUCTION-RETURN.
    paymentData.setNetworkReductionAmount(calculationContext.getNetworkReductionAmount());
  }

  @Override
  protected void applyHdpaEtcAdjustment(EsrdPricerContext calculationContext) {
    // ********************************************************
    // * NEW FOR VERSION 21.0: APPLY THE NETWORK REDUCTION ***
    // ********************************************************
    //
    //     COMPUTE H-FINAL-AMT-WITHOUT-HDPA ROUNDED =
    //              H-FINAL-AMT-WITHOUT-HDPA - H-NETWORK-REDUCTION.
    calculationContext.setFinalAmountWithoutHdpa(
        calculationContext
            .getFinalAmountWithoutHdpa()
            .subtract(calculationContext.getNetworkReductionAmount())
            .setScale(2, RoundingMode.HALF_UP));

    //     COMPUTE H-FINAL-AMT-WITH-HDPA ROUNDED =
    //              H-FINAL-AMT-WITH-HDPA - H-NETWORK-REDUCTION.
    calculationContext.setFinalAmountWithHdpa(
        calculationContext
            .getFinalAmountWithHdpa()
            .subtract(calculationContext.getNetworkReductionAmount())
            .setScale(2, RoundingMode.HALF_UP));

    super.applyHdpaEtcAdjustment(calculationContext);
  }
}
