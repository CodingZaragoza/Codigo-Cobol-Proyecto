package gov.cms.fiss.pricers.esrd.core.rules.rules_2025.bundled_factors.outliers;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdPaymentData;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Dialysis in Home and (CAPD or CCPD) Per-Diem calculation.
 *
 * <p>Converted from {@code 2500-CALC-OUTLIER-FACTORS} (continued) in the COBOL code.
 *
 * @since 2020
 */
public class CalculateOutlierPerDiem2025
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final EsrdPaymentData paymentData = calculationContext.getPaymentData();
    final EsrdClaimData claimData = calculationContext.getClaimData();

    //     MOVE H-OUT-PAYMENT                TO OUT-NON-PER-DIEM-PAYMENT
    paymentData.setOutlierNonPerDiemPaymentAmount(calculationContext.getOutlierPayment());

    //      IF (B-COND-CODE = '74')  AND
    //        (B-REV-CODE = '0841' OR '0851')  THEN
    // CHANGED FOR 2025
    // REPLACED
    //   if StringUtils.equals(
    //           EsrdPricerContext.CONDITION_CODE_HOME_SERVICES_74, claimData.getConditionCode()

    if (calculationContext.isPerDiemClaim()) {
      calculationContext.setOutlierPayment(
          calculationContext
              .getOutlierPayment()
              .multiply(
                  new BigDecimal(claimData.getDialysisSessionCount()).multiply(new BigDecimal(3)))
              .divide(new BigDecimal(7), 9, RoundingMode.DOWN));
    }
  }
}
