package gov.cms.fiss.pricers.esrd.core.rules.rules_2022.move_results;

import gov.cms.fiss.pricers.esrd.api.v2.EsrdPaymentData;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import gov.cms.fiss.pricers.esrd.core.rules.rules_2021.move_results.MoveBaseResults2021;

/**
 * Applies the calculation results to the bundled data.
 *
 * <p>Converted from {@code 9100-MOVE-RESULTS} in the COBOL code.
 *
 * @since 2020
 */
public class MoveBaseResults2022 extends MoveBaseResults2021 {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    super.calculate(calculationContext);

    final EsrdPaymentData paymentData = calculationContext.getPaymentData();

    //     MOVE H-CRA-TPNIES-PAYMENT             TO CRA-TPNIES-RETURN.
    paymentData.setTpniesCraPaymentAdjustmentAmount(calculationContext.getCraTpniesPayment());
  }
}
