package gov.cms.fiss.pricers.esrd.core.rules.rules_2025;

import gov.cms.fiss.pricers.esrd.api.v2.AdditionalPricingData;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import gov.cms.fiss.pricers.esrd.core.rules.move_results.MoveAdditionalDataResults;

/**
 * Applies the calculation results to the additional data used by the web pricer.
 *
 * <p>Converted from {@code 9100-MOVE-RESULTS} in the COBOL code.
 *
 * @since 2020
 */
public class MoveAdditionalDataResults2025 extends MoveAdditionalDataResults {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    super.calculate(calculationContext);

    final AdditionalPricingData additionalPricingData =
        calculationContext.getAdditionalPricingData();

    //        MOVE BUNDLED-BASE-PMT-RATE     TO PPS-BUN-BASE-PMT-RATE
    if (calculationContext.isAki84()) {
      additionalPricingData.setBundledBasePaymentRate(
          calculationContext.getBundledBasePaymentRateAki());
    } else {
      additionalPricingData.setBundledBasePaymentRate(
          calculationContext.getBundledBasePaymentRate());
    }
  }
}
