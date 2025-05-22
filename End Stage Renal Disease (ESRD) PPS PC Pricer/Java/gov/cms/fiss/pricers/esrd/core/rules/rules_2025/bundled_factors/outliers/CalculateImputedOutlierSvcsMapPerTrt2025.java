package gov.cms.fiss.pricers.esrd.core.rules.rules_2025.bundled_factors.outliers;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Calculate the case mix-adjusted predicted outlier MAP per treatment.
 *
 * <pre>
 * *****************************************************************
 * ** Calculate imputed OUTLIER services MAP amount per treatment***
 * *****************************************************************
 * </pre>
 *
 * <p>Converted from {@code 2500-CALC-OUTLIER-FACTORS} (continued) in the COBOL code.
 *
 * @since 2020
 */
public class CalculateImputedOutlierSvcsMapPerTrt2025
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();

    //     IF (B-COND-CODE = '74')  AND
    //        (B-REV-CODE = '0841' OR '0851')  THEN
    //         COMPUTE H-HEMO-EQUIV-DIAL-SESSIONS  ROUNDED  =
    //            ((B-CLAIM-NUM-DIALYSIS-SESSIONS * 3) / 7)
    //         COMPUTE H-OUT-IMPUTED-MAP  ROUNDED =
    //         (B-TOT-PRICE-SB-OUTLIER / H-HEMO-EQUIV-DIAL-SESSIONS)
    //     ELSE
    //        COMPUTE H-OUT-IMPUTED-MAP  ROUNDED =
    //        (B-TOT-PRICE-SB-OUTLIER / B-CLAIM-NUM-DIALYSIS-SESSIONS)
    //     END-IF.

    // CHANGED FOR 2025
    // REPLACED
    //   if StringUtils.equals(
    //           EsrdPricerContext.CONDITION_CODE_HOME_SERVICES_74, claimData.getConditionCode()

    if (calculationContext.isPerDiemClaim()) {
      calculationContext.setHemoEquivalentDialysisSessions(
          new BigDecimal(claimData.getDialysisSessionCount())
              .multiply(new BigDecimal(3))
              .divide(new BigDecimal(7), 4, RoundingMode.HALF_UP));

      // test case workbook line 364
      calculationContext.setOutlierImputedMapAmount(
          claimData
              .getTotalPriceSeparatelyBillableOutlier()
              .divide(
                  calculationContext.getHemoEquivalentDialysisSessions(), 4, RoundingMode.HALF_UP));
    } else {
      calculationContext.setOutlierImputedMapAmount(
          claimData
              .getTotalPriceSeparatelyBillableOutlier()
              .divide(
                  new BigDecimal(claimData.getDialysisSessionCount()), 4, RoundingMode.HALF_UP));
    }
  }
}
