package gov.cms.fiss.pricers.ipps.core.rules.rules2022.calculate_payment.totals;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.api.v2.IppsPaymentData;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.totals.CalculateElectronicHealthRecordSavings;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.commons.lang3.StringUtils;

/**
 * Determine the electronic health record adjustment amount.
 *
 * <p>Converted from {@code 9000-CALC-EHR-SAVING} in the COBOL code.
 *
 * @since 2019
 */
public class CalculateElectronicHealthRecordSavings2022
    extends CalculateElectronicHealthRecordSavings
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    // ***********************************************************
    // *---------------------------------------------------------*
    // * (YEARCHANGE 2017.0)
    // * CASES INVOLVING EHR SAVINGS
    // *   + FY17: ANNUAL UPDATE TO BELOW VALUES
    // *   + EHR-FULL = FULL MB / NO EHR MB
    // *   + EHR-QUAL-FULL = NO QUAL MB / NO QUAL & NO EHR MB
    // *---------------------------------------------------------*
    //     MOVE 1.021930930 TO H-MB-RATIO-EHR-FULL.
    //     MOVE 1.022092433 TO H-MB-RATIO-EHR-QUAL-FULL.
    //     MOVE 0 TO H-EHR-SUBSAV-QUANT.
    //     MOVE 0 TO H-EHR-SUBSAV-LV.
    //     MOVE 0 TO H-EHR-SUBSAV-QUANT-INCLV.
    //     MOVE 0 TO H-EHR-RESTORE-FULL-QUANT.
    final BigDecimal marketBasketRatioEhrFull = calculationContext.getMarketBasketRatioEhrFull();
    final BigDecimal marketBasketRatioEhrQualFull =
        calculationContext.getMarketBasketRatioEhrQualifiedFull();
    final BigDecimal marketBasketRatioEhrFullPuertoRico =
        calculationContext.getMarketBasketRatioEhrFullPuertoRico();

    // * LOGIC TO IMPLEMENT EHR SAVINGS CALCULATION -
    // * ACTUAL EHR REDUCTIONS WILL BE BUILT INTO NEW RATE
    // * TABLES (5,6,7,&8) UP FRONT BUT OESS WANTS TO HAVE THE
    // * AMOUNT OF MONEY THE EHR POLICY 'SAVED' IN ITS OWN FIELD
    // * WHICH INVOLVES RESTORING THE FULL MARKET  BASKET
    // * TO THE PAYMENT TO GET THE 'WOULD'VE PAID' AND THEN
    // * TAKING THE DIFFERENCE BETWEEN ACTUAL PAID AND
    // * WOULD'VE PAID FOR THE SAVINGS.  OUTLIERS ARE TO BE
    // * LEFT OUT AT MOMENT SINCE OUTLIER SHOULD BE LOWER
    // * ON THE FULL RATE THAN IT WINDS UP BEING ON THE
    // * REDUCED RATE - LIKEWISE NEW TECH IS BEING LEFT
    // * OUT.
    // *
    // * FOR EHR NEED TO EXCLUDE NEW TECH AND OUTLIERS FROM
    // * SAVINGS CALCULATION SO CALCULATE AN OPERATING
    // * PAYMENT SUBTOTAL ON SO CALCULATE AN OPERATING
    // * PAYMENT SUBTOTAL ON EHR PAYMENTS THAT EXCLUDES
    // * OUTLIERS AND NEW TECH FOR CLAIMS WITH AN EHR FLAG
    //      COMPUTE H-EHR-SUBSAV-QUANT =
    //           (PPS-OPER-HSP-PART +
    //            PPS-OPER-FSP-PART +
    //            PPS-OPER-DSH-ADJ +
    //            PPS-OPER-IME-ADJ +
    //            H-READMIS-ADJUST-AMT +
    //            H-VAL-BASED-PURCH-ADJUST-AMT +
    //            H-BUNDLE-ADJUST-AMT).
    final IppsPaymentData paymentData = calculationContext.getPaymentData();
    final BigDecimal ehrSubtotalSavingsQuantity =
        BigDecimalUtils.decimalSum(
                paymentData.getOperatingHospitalSpecificPortionPart(),
                paymentData.getOperatingFederalSpecificPortionPart(),
                paymentData.getOperatingDisproportionateShareHospitalAdjustment(),
                paymentData.getOperatingIndirectMedicalEducationAdjustment(),
                calculationContext.getReadmissionAdjustmentAmount(),
                calculationContext.getValueBasedPurchasingAdjustmentAmount(),
                calculationContext.getBundledAdjustmentPayment())
            .setScale(2, RoundingMode.HALF_UP);

    // * NEED TO ENSURE THAT LOW VOLUME, IF APPLICABLE IS
    // * INCLUDED - CAN'T USE PRICER'S LOW VOLUME PAYMENT
    // * AS THAT INCLUDES NEW TECH OUTLIERS AND CAPITAL -
    // * READM VBP AND BUNDLE
    // * DON'T MULTIPLY BY LV ADJUSTMENT SO MAKE A NEW LV AMT
    // * FOR EHR SAVINGS FIELD
    //      MOVE 0 TO H-EHR-SUBSAV-LV.
    BigDecimal ehrSubtotalSavingsLowVolumePayment = BigDecimal.ZERO;

    //      IF P-NEW-TEMP-RELIEF-IND = 'Y'
    //         AND P-LV-ADJ-FACTOR > 0.00
    //         AND P-LV-ADJ-FACTOR <= 0.25
    //      COMPUTE H-EHR-SUBSAV-LV =
    //          (PPS-OPER-HSP-PART +
    //           PPS-OPER-FSP-PART +
    //           PPS-OPER-DSH-ADJ +
    //           PPS-OPER-IME-ADJ ) * P-LV-ADJ-FACTOR.
    final InpatientProviderData providerData = calculationContext.getProviderData();
    if (StringUtils.equals(providerData.getTemporaryReliefIndicator(), "Y")
        && BigDecimalUtils.isGreaterThanZero(providerData.getLowVolumeAdjustmentFactor())
        && BigDecimalUtils.isLessThanOrEqualTo(
            providerData.getLowVolumeAdjustmentFactor(), new BigDecimal("0.25"))) {
      ehrSubtotalSavingsLowVolumePayment =
          BigDecimalUtils.decimalSum(
                  paymentData.getOperatingHospitalSpecificPortionPart(),
                  paymentData.getOperatingFederalSpecificPortionPart(),
                  paymentData.getOperatingDisproportionateShareHospitalAdjustment(),
                  paymentData.getOperatingIndirectMedicalEducationAdjustment())
              .multiply(providerData.getLowVolumeAdjustmentFactor())
              .setScale(2, RoundingMode.DOWN);
    }

    //      COMPUTE H-EHR-SUBSAV-QUANT-INCLV =
    //           H-EHR-SUBSAV-QUANT + H-EHR-SUBSAV-LV.
    final BigDecimal ehrSubtotalSavingsQuantityIncludingLowValuePayments =
        ehrSubtotalSavingsQuantity
            .add(ehrSubtotalSavingsLowVolumePayment)
            .setScale(2, RoundingMode.DOWN);

    // * H-MB-RATIO-EHR-FULL IS THE RATIO OF THE FULL MARKET
    // * BASKET TO THE REDUCED EHR MB - NEED TO CARRY 2 RATIOS
    // * FOR PROVIDERS FAILING EHR AND FOR PROVIDERS FAILING EH
    // * AND QUALITY IN COMBINATION.  EHR SAVINGS REQUIRES
    // * BACKING OFF THE LOW UPDATE AND MULTIPLYING ON THE
    // * FULL UPDATE SO USING RATIO OF LOW/FULL AND LOW/QUALHIT
    // * OF .625 ONLY.
    //       COMPUTE  H-EHR-RESTORE-FULL-QUANT ROUNDED =
    //       H-EHR-SUBSAV-QUANT-INCLV * H-MB-RATIO-EHR-FULL.
    BigDecimal ehrRestoredFullQuantity;

    if (StringUtils.equals(providerData.getStateCode(), "40")) {
      ehrRestoredFullQuantity =
          ehrSubtotalSavingsQuantityIncludingLowValuePayments
              .multiply(marketBasketRatioEhrFullPuertoRico)
              .setScale(2, RoundingMode.HALF_UP);
    } else {
      ehrRestoredFullQuantity =
          ehrSubtotalSavingsQuantityIncludingLowValuePayments
              .multiply(marketBasketRatioEhrFull)
              .setScale(2, RoundingMode.HALF_UP);
    }

    //     IF P-NEW-CBSA-HOSP-QUAL-IND NOT = '1'
    //        COMPUTE  H-EHR-RESTORE-FULL-QUANT ROUNDED =
    //          H-EHR-SUBSAV-QUANT-INCLV * H-MB-RATIO-EHR-QUAL-FULL.
    if (!StringUtils.equals(providerData.getHospitalQualityIndicator(), "1")) {
      ehrRestoredFullQuantity =
          ehrSubtotalSavingsQuantityIncludingLowValuePayments
              .multiply(marketBasketRatioEhrQualFull)
              .setScale(2, RoundingMode.HALF_UP);
    }

    //        COMPUTE  H-EHR-ADJUST-AMT ROUNDED =
    //          H-EHR-RESTORE-FULL-QUANT - H-EHR-SUBSAV-QUANT-INCLV.
    calculationContext.setElectronicHealthRecordAdjustmentAmt(
        ehrRestoredFullQuantity
            .subtract(ehrSubtotalSavingsQuantityIncludingLowValuePayments)
            .setScale(2, RoundingMode.HALF_DOWN));
  }
}
