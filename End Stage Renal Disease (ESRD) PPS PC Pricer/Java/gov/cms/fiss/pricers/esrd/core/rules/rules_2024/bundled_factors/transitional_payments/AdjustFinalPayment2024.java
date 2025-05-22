package gov.cms.fiss.pricers.esrd.core.rules.rules_2024.bundled_factors.transitional_payments;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.RoundingMode;

public class AdjustFinalPayment2024
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    // ****************************************************************
    // ***  Compute Without ETC but no QIP yet     ***
    // ****************************************************************

    calculationContext.setFinalAmountWithoutEtc(
        calculationContext
            .getFinalAmountWithoutEtc()
            .add(calculationContext.getTdapaPayment())
            .add(calculationContext.getTpniesPayment())
            .add(calculationContext.getCraTpniesPayment())
            .add(calculationContext.getTpeapaPayment())
            .add(calculationContext.getPostTdapaPayment())
            .setScale(4, RoundingMode.HALF_UP));

    calculationContext.setFinalPaymentAmount(calculationContext.getFinalAmountWithoutEtc());
  }
}
