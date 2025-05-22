package gov.cms.fiss.pricers.esrd.core.rules.rules_2025.bundled_factors.outliers;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class AdjustPredictedOutlierSvcsMapPerTrtByPostTdapa2025
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();
    if (LocalDateUtils.isAfterOrEqual(
        claimData.getServiceThroughDate(), LocalDate.of(2025, 1, 1))) {
      if (LocalDateUtils.isBeforeOrEqual(
          claimData.getServiceThroughDate(), LocalDate.of(2025, 9, 30)))
        calculationContext.setPostTdapaPayment(new BigDecimal("0.4601"));
      else if (LocalDateUtils.isBeforeOrEqual(
          claimData.getServiceThroughDate(), LocalDate.of(2025, 12, 31)))
        calculationContext.setPostTdapaPayment(new BigDecimal("0.4697"));
      calculationContext.setOutlierCaseMixAdjPredictedMapTrt(
          calculationContext
              .getPatientLevelCaseMixAdjusterForPostTdapa()
              .multiply(calculationContext.getPostTdapaPayment())
              .add(
                  calculationContext
                      .getOutlierCaseMixAdjPredictedMapTrt()
                      .setScale(4, RoundingMode.HALF_UP)));
    }
  }
}
