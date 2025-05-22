package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.totals;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.commons.lang3.StringUtils;

/**
 * Determine the value for capital intermediate variables.
 *
 * <p>Converted from {@code 3800-CALC-TOT-AMT} in the COBOL code.
 *
 * @since 2019
 */
public class CalculateCapitalIntermediateVariables
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();

    // ***********************************************************
    // ***  CALCULATE TOTALS FOR CAPITAL
    //     MOVE P-NEW-CAPI-PPS-PAY-CODE  TO H-CAPI2-PAY-CODE.
    calculationContext.setCapital2PayCode(providerData.getCapitalPpsPaymentCode());

    capitalPpsPayCodeBlock(calculationContext);

    //     COMPUTE H-CAPI-FSP ROUNDED =
    //         H-CAPI-FSP-PCT * H-CAPI-FSP-PART.
    calculationContext.setCapitalFederalSpecificPortion(
        calculationContext
            .getCapitalFederalSpecificPortionPct()
            .multiply(calculationContext.getCapitalFederalSpecificPortionPart())
            .setScale(2, RoundingMode.HALF_UP));

    //     MOVE P-NEW-CAPI-EXCEPTIONS TO H-CAPI-EXCEPTIONS.
    calculationContext.setCapitalExceptionPaymentRate(
        providerData.getCapitalExceptionPaymentRate());

    //     COMPUTE H-CAPI-DSH-ADJ ROUNDED =
    //             H-CAPI-FSP
    //              * H-CAPI-DSH.
    calculationContext.setCapitalDisproportionateShareHospitalAdjustment(
        calculationContext
            .getCapitalFederalSpecificPortion()
            .multiply(calculationContext.getCapitalDisproportionateShareHospital())
            .setScale(2, RoundingMode.HALF_UP));

    //     COMPUTE H-CAPI-IME-ADJ ROUNDED =
    //          H-CAPI-FSP *
    //                 H-WK-CAPI-IME-TEACH.
    calculationContext.setCapitalIndirectMedicalEducationAdj(
        calculationContext
            .getCapitalFederalSpecificPortion()
            .multiply(calculationContext.getCapitalIndirectMedicalEducation())
            .setScale(2, RoundingMode.HALF_UP));

    //     COMPUTE H-CAPI-OUTLIER ROUNDED =
    //             1.00 * H-CAPI-OUTLIER-PART.
    calculationContext.setCapitalOutlierCost(
        calculationContext
            .getCapitalOutlierPart()
            .multiply(BigDecimal.ONE)
            .setScale(2, RoundingMode.HALF_UP));

    // ***********************************************************
    // ***  IF CAPITAL IS NOT IN EFFECT FOR GIVEN PROVIDER
    // ***        THIS ZEROES OUT ALL CAPITAL DATA
    //     IF (P-NEW-CAPI-NEW-HOSP = 'Y')
    //        MOVE ALL '0' TO HOLD-CAPITAL-VARIABLES.
    if (StringUtils.equals(providerData.getNewHospital(), "Y")) {
      calculationContext.getPaymentData().setTotalPayment(BigDecimal.ZERO);
      calculationContext.setCapitalFederalSpecificPortion(BigDecimal.ZERO);
      calculationContext.setCapitalExceptionPaymentRate(BigDecimal.ZERO);
      calculationContext.setCapitalDisproportionateShareHospitalAdjustment(BigDecimal.ZERO);
      calculationContext.setCapitalIndirectMedicalEducationAdj(BigDecimal.ZERO);
      calculationContext.setCapitalOutlierCost(BigDecimal.ZERO);
    }
  }

  /*
   *  **************************************************************
   *  **  TRANSITIONAL PAYMENT FOR FORMER MDHS                     *
   *  **************************************************************
   *  **  HSP PAYMENT FOR CLAIMS BETWEEN 10/01/2016 - 09/30/2017
   *      IF  B-FORMER-MDH-PROVIDERS       AND
   *         (B-DISCHARGE-DATE &gt; 20160930  AND
   *          B-DISCHARGE-DATE &lt; 20171001)
   *        IF  H-HSP-RATE &gt; (H-FSP-RATE + WK-UNCOMP-CARE-AMOUNT)
   *          COMPUTE H-OPER-HSP-PART ROUNDED =
   *            ((H-HSP-RATE - (H-FSP-RATE +
   *                WK-UNCOMP-CARE-AMOUNT))* 0.75)*(1 / 3)
   *              ON SIZE ERROR MOVE 0 TO H-OPER-HSP-PART
   *        END-IF
   *      END-IF.
   */
  private void capitalPpsPayCodeBlock(IppsPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();

    //     IF P-NEW-CAPI-PPS-PAY-CODE = 'B'
    //        MOVE 0    TO H-CAPI-OLD-HARMLESS
    //        MOVE 1.00 TO H-CAPI-FSP-PCT
    //        MOVE 0.00 TO H-CAPI-HSP-PCT.
    if (StringUtils.equals(providerData.getCapitalPpsPaymentCode(), "B")) {
      calculationContext.setCapitalOldHoldHarmless(BigDecimal.ZERO);
      calculationContext.setCapitalFederalSpecificPortionPct(BigDecimal.ONE);
    }

    //     IF P-NEW-CAPI-PPS-PAY-CODE = 'C'
    //        MOVE 0    TO H-CAPI-OLD-HARMLESS
    //        MOVE H-CAPI-PAYCDE-PCT1 TO H-CAPI-FSP-PCT
    //        MOVE H-CAPI-PAYCDE-PCT2 TO H-CAPI-HSP-PCT.
    if (StringUtils.equals(providerData.getCapitalPpsPaymentCode(), "C")) {
      calculationContext.setCapitalOldHoldHarmless(BigDecimal.ZERO);
      calculationContext.setCapitalFederalSpecificPortionPct(
          calculationContext.getCapitalPaycodePct1());
    }
  }
}
