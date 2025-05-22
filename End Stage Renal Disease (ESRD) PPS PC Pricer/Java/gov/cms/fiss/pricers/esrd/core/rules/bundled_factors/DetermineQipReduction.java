package gov.cms.fiss.pricers.esrd.core.rules.bundled_factors;

import gov.cms.fiss.pricers.common.api.OutpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import org.apache.commons.lang3.StringUtils;

/**
 * Determines the Quality Incentive Program (QIP) reduction.
 *
 * <p>Converted from {@code 2000-CALCULATE-BUNDLED-FACTORS} (continued) in the COBOL code.
 *
 * @since 2020
 */
public class DetermineQipReduction
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final OutpatientProviderData providerData = calculationContext.getProviderData();

    //    IF P-QIP-REDUCTION = ' '  THEN
    // no reduction
    //       MOVE 1.000 TO QIP-REDUCTION
    //    ELSE
    //       IF P-QIP-REDUCTION = '1'  THEN
    // one-half percent reduction
    //          MOVE 0.995 TO QIP-REDUCTION
    //       ELSE
    //          IF P-QIP-REDUCTION = '2'  THEN
    // one percent reduction
    //             MOVE 0.990 TO QIP-REDUCTION
    //          ELSE
    //             IF P-QIP-REDUCTION = '3'  THEN
    // one and one-half percent reduction
    //                MOVE 0.985 TO QIP-REDUCTION
    //             ELSE
    // two percent reduction
    //                MOVE 0.980 TO QIP-REDUCTION
    //             END-IF
    //          END-IF
    //       END-IF
    //    END-IF.
    if (StringUtils.isNotEmpty(providerData.getHospitalQualityIndicator())) {
      switch (providerData.getHospitalQualityIndicator()) {
        case "1":
          calculationContext.setQipReduction(EsrdPricerContext.QIP_REDUCTION_HALF_PCT);

          break;
        case "2":
          calculationContext.setQipReduction(EsrdPricerContext.QIP_REDUCTION_ONE_PCT);

          break;
        case "3":
          calculationContext.setQipReduction(EsrdPricerContext.QIP_REDUCTION_ONE_AND_HALF_PCT);

          break;
        case "4":
          calculationContext.setQipReduction(EsrdPricerContext.QIP_REDUCTION_TWO_PCT);

          break;
        default:
          calculationContext.setQipReduction(EsrdPricerContext.QIP_REDUCTION_NONE);

          break;
      }
    }
  }
}
