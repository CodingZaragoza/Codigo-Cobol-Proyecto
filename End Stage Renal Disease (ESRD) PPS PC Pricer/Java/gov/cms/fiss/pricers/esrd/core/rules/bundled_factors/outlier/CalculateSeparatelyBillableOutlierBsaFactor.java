package gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.outlier;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Calculates the separately-billable outlier BSA factor.
 *
 * <pre>
 * *****************************************************************
 * *Calculate separately billable OUTLIER BSA factor (superscript)**
 * *****************************************************************
 * </pre>
 *
 * <p>Converted from {@code 2500-CALC-OUTLIER-FACTORS} (continued) in the COBOL code.
 *
 * @since 2020
 */
public class CalculateSeparatelyBillableOutlierBsaFactor
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    //     COMPUTE H-OUT-BSA  ROUNDED = (.007184 *
    //         (B-PATIENT-HGT ** .725) * (B-PATIENT-WGT ** .425))
    calculationContext.setOutlierBsa(calculationContext.calculateBodySurfaceArea());

    //     IF H-PATIENT-AGE > 17  THEN
    //        COMPUTE H-OUT-BSA-FACTOR  ROUNDED =
    //             SB-BSA ** ((H-OUT-BSA - BSA-NATIONAL-AVERAGE) / .1)
    //     ELSE
    //        MOVE 1.000                     TO H-OUT-BSA-FACTOR
    //     END-IF.
    if (calculationContext.isAdultPatient()) {
      calculationContext.setOutlierBsaFactor(
          BigDecimalUtils.pow(
                  calculationContext.getSeparatelyBillableBsa(),
                  calculationContext
                      .getOutlierBsa()
                      .subtract(calculationContext.getBsaNationalAverage())
                      .divide(new BigDecimal(".1"), RoundingMode.DOWN),
                  9)
              .setScale(4, RoundingMode.HALF_UP));
    } else {
      calculationContext.setOutlierBsaFactor(new BigDecimal("1.0000"));
    }
  }
}
