package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculateCapitalOldHoldHarmlessAmount
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();

    // ***********************************************************
    // ***********  CAPITAL OLD-HOLD-HARMLESS CALCULATION ***********
    // ***********  CAPITAL OLD-HOLD-HARMLESS CALCULATION ***********
    //     COMPUTE H-CAPI-OLD-HARMLESS ROUNDED =
    //                    (P-NEW-CAPI-OLD-HARM-RATE *
    //                    H-CAPI-SCH).
    if (providerData.getOldCapitalHoldHarmlessRate() != null) {
      calculationContext.setCapitalOldHoldHarmless(
          providerData
              .getOldCapitalHoldHarmlessRate()
              .multiply(calculationContext.getCapitalSch())
              .setScale(2, RoundingMode.HALF_UP));
    } else {
      calculationContext.setCapitalOldHoldHarmless(BigDecimal.ZERO);
    }
    calculationContext.setCapitalOldHoldHarmlessRate(
        calculationContext.getCapitalOldHoldHarmless());
  }
}
