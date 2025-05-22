package gov.cms.fiss.pricers.esrd.core.rules.rules_2025.bundled_factors.transitional_payments;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class CalculatePostTdapaAddOn2025
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();

    //     The post-TDAPA add-on payment adjustment (PTDAPAAPA) is
    //     a fixed dollar amount applied to every ESRD PPS treatment.
    //
    //     This amount can be different for each quarter of the
    //     year, but the payment amount for all 4 quarters will be known by
    //     the time of the final rule.  The post-TDAPA payment on a claim is
    //     adjusted by the patient-level adjustment factors.
    //
    //     CY 2025 Quarterly Post-TDAPA Add-on Payment Amounts:
    //         Q1 (January 1 – March 31)	 $0.4601
    //         Q2 (April 1 – June 30)	     $0.4601
    //         Q3 (July 1 – September 30)	 $0.4601
    //         Q4 (October 1 – December 31)  $0.4601 + $0.0096 = $0.4697

    if (LocalDateUtils.isAfterOrEqual(claimData.getServiceThroughDate(), LocalDate.of(2025, 1, 1))
        && LocalDateUtils.isBeforeOrEqual(
            claimData.getServiceThroughDate(), LocalDate.of(2025, 12, 31)))
      determinePostTdapaAmount(calculationContext);
  }

  protected void determinePostTdapaAmount(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();
    if (LocalDateUtils.isBeforeOrEqual(
        claimData.getServiceThroughDate(), LocalDate.of(2025, 9, 30)))
      calculationContext.setPostTdapaPayment(new BigDecimal("0.4601"));
    else calculationContext.setPostTdapaPayment(new BigDecimal("0.4697"));
    calculationContext.setPostTdapaPayment(
        calculationContext.getPostTdapaPayment().multiply(calculationContext.getCaseMixAdjuster()));
    if (calculationContext.isPediatricClaim())
      calculationContext.setPostTdapaPayment(
          calculationContext.getPostTdapaPayment().multiply(BigDecimal.valueOf(1.3)));
    if (calculationContext.isPerDiemClaim())
      calculationContext.setPostTdapaPayment(
          calculationContext
              .getPostTdapaPayment()
              .multiply(new BigDecimal(3))
              .divide(new BigDecimal(7), 4, RoundingMode.HALF_UP));
  }
}
