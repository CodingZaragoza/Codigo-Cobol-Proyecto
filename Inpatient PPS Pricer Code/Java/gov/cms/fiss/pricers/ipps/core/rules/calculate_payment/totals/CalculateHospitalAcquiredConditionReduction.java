package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.totals;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.api.v2.IppsPaymentData;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.ResultCode;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.commons.lang3.StringUtils;

/**
 * Determine hospital acquired condition reduction amount.
 *
 * <p>Converted from {@code 3800-CALC-TOT-AMT} in the COBOL code.
 *
 * @since 2019
 */
public class CalculateHospitalAcquiredConditionReduction
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final IppsPaymentData paymentData = calculationContext.getPaymentData();

    // ***********************************************************
    // * HOSPITAL ACQUIRED CONDITION (HAC) PENALTY & REDUCTION FACTOR
    // ***********************************************************
    // *---------------------------------------------------------*
    // * (YEARCHANGE 2016.0)
    // * HOSPITAL ACQUIRED CONDITION (HAC) REDUCTION FACTOR
    // *   + FOR FY 2015 AN ADJUSTMENT OF 0.01 TO CALCULATE
    // *     HOSPITAL ACQUIRED CONDITION (HAC) PENALTY
    // *   + BASED ON INDICATOR FROM THE PPS FILE
    // *   + NOT VALID IN PUERTO RICO
    // *   + TOTAL PAYMENT NOW INCLUDES UNCOMPENSATED CARE AMOUNT
    // *---------------------------------------------------------*
    //     COMPUTE WK-HAC-TOTAL-PAYMENT ROUNDED =
    //        PPS-OPER-HSP-PART +
    //        PPS-OPER-FSP-PART +
    //        PPS-OPER-IME-ADJ +
    //        PPS-OPER-DSH-ADJ +
    //        PPS-OPER-OUTLIER-PART +
    //        H-CAPI-TOTAL-PAY +
    //        WK-UNCOMP-CARE-AMOUNT +
    //        PPS-NEW-TECH-PAY-ADD-ON +
    //        WK-LOW-VOL-ADDON +
    //        H-READMIS-ADJUST-AMT +
    //        H-VAL-BASED-PURCH-ADJUST-AMT.
    final BigDecimal hacTotalPayment =
        BigDecimalUtils.decimalSum(
                paymentData.getOperatingHospitalSpecificPortionPart(),
                paymentData.getOperatingFederalSpecificPortionPart(),
                paymentData.getOperatingIndirectMedicalEducationAdjustment(),
                paymentData.getOperatingDisproportionateShareHospitalAdjustment(),
                paymentData.getOperatingOutlierPaymentPart(),
                calculationContext.getCapitalTotalPayment(),
                calculationContext.getUncompensatedCareAmount(),
                calculationContext.getNewTechAddOnPayment(),
                calculationContext.getLowVolumePayment(),
                calculationContext.getReadmissionAdjustmentAmount(),
                calculationContext.getValueBasedPurchasingAdjustmentAmount())
            .setScale(2, RoundingMode.HALF_UP);

    //     MOVE ZERO TO WK-HAC-AMOUNT.
    calculationContext.setHospitalAcquiredConditionAmount(BigDecimal.ZERO);

    final InpatientProviderData providerData = calculationContext.getProviderData();

    //     IF P-PR-NEW-STATE AND
    //        P-HAC-REDUC-IND = 'Y'
    //           MOVE 53 TO PPS-RTC
    //           GO TO 3800-EXIT.
    if (calculationContext.isStatePuertoRico()
        && StringUtils.equals(providerData.getHacReductionParticipantIndicator(), "Y")) {
      calculationContext.applyResultCode(ResultCode.RC_53_WAIVER_STATE_NOT_CALC);
      calculationContext.clearAdditionalVariablesContextState();
    } else {
      //     IF  P-HAC-REDUC-IND = 'Y'
      //         COMPUTE   WK-HAC-AMOUNT     ROUNDED =
      //                   WK-HAC-TOTAL-PAYMENT * -0.01
      //     ELSE
      //         COMPUTE   WK-HAC-AMOUNT     ROUNDED = 0.
      if (StringUtils.equals(providerData.getHacReductionParticipantIndicator(), "Y")) {
        calculationContext.setHospitalAcquiredConditionAmount(
            hacTotalPayment.multiply(new BigDecimal("-0.01")).setScale(2, RoundingMode.HALF_UP));
      }

      // ***********************************************************
      // ***  TOTAL PAYMENT NOW INCLUDES HAC PENALTY AMOUNT
      // ************************************************
      //     COMPUTE   PPS-TOTAL-PAYMENT ROUNDED =
      //                 WK-HAC-TOTAL-PAYMENT
      //                           +
      //                 H-WK-PASS-AMT-PLUS-MISC
      //                           +
      //                 H-BUNDLE-ADJUST-AMT
      //                           +
      //                 WK-HAC-AMOUNT
      //                           +
      //                 H-NEW-TECH-ADDON-ISLET.
      paymentData.setTotalPayment(
          BigDecimalUtils.decimalSum(
                  hacTotalPayment,
                  calculationContext.getPassthroughAmountPlusMisc(),
                  calculationContext.getBundledAdjustmentPayment(),
                  calculationContext.getHospitalAcquiredConditionAmount(),
                  calculationContext.getIsletIsolationPaymentAddOn())
              .setScale(2, RoundingMode.HALF_UP));

      //     MOVE     P-VAL-BASED-PURCH-PARTIPNT TO
      //              H-VAL-BASED-PURCH-PARTIPNT.
      calculationContext.setValueBasedPurchasingParticipant(
          providerData.getVbpParticipantIndicator());

      //     MOVE     P-VAL-BASED-PURCH-ADJUST   TO
      //              H-VAL-BASED-PURCH-ADJUST.
      calculationContext.setValueBasedPurchasingAdjustment(providerData.getVbpAdjustment());

      //     MOVE     P-HOSP-READMISSION-REDU    TO
      //              H-HOSP-READMISSION-REDU.
      calculationContext.setHrrParticipantIndicator(providerData.getHrrParticipantIndicator());

      //     MOVE     P-HOSP-HRR-ADJUSTMT        TO
      //              H-HOSP-HRR-ADJUSTMT.
      calculationContext.setHospitalReadmissionReductionAdjustment(
          providerData.getHrrAdjustment() == null
              ? BigDecimal.ZERO
              : providerData.getHrrAdjustment());
    }
  }
}
