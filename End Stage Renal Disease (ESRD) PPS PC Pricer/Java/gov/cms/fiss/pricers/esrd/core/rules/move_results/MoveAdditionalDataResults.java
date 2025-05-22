package gov.cms.fiss.pricers.esrd.core.rules.move_results;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.esrd.api.v2.AdditionalPricingData;
import gov.cms.fiss.pricers.esrd.api.v2.BundledBillingData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdPaymentData;
import gov.cms.fiss.pricers.esrd.api.v2.OutlierBillingData;
import gov.cms.fiss.pricers.esrd.api.v2.PaymentBillingData;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.BigDecimal;

/**
 * Applies the calculation results to the additional data used by the web pricer.
 *
 * <p>Converted from {@code 9100-MOVE-RESULTS} in the COBOL code.
 *
 * @since 2020
 */
public class MoveAdditionalDataResults
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  // TODO: Remove once max value is handled automatically
  private static final BigDecimal MAX_OUTLIER_IMPUTED_MAP_AMOUNT = new BigDecimal("9999.9999");

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final EsrdPaymentData paymentData = calculationContext.getPaymentData();
    final PaymentBillingData paymentBillingData = calculationContext.getPaymentBillingData();
    // ************************************************************************
    // The following block will always be executed, as the data is required
    // for integration with the web pricer.
    // ************************************************************************
    //     IF BUNDLED-TEST   THEN
    //        MOVE DRUG-ADDON                TO DRUG-ADD-ON-RETURN
    paymentBillingData.setDrugAddOnAmount(calculationContext.getDrugAddOn());
    //        MOVE 0.0                       TO MSA-WAGE-ADJ            <--- WILL ALWAYS BE ZERO
    //        MOVE H-WAGE-ADJ-PYMT-AMT       TO CBSA-WAGE-ADJ           <--- NOT USED / INITIALIZED
    //        MOVE BASE-PAYMENT-RATE         TO CBSA-WAGE-PMT-RATE
    paymentBillingData.setCbsaWagePaymentRate(calculationContext.getBasePaymentRate());
    //        MOVE H-PATIENT-AGE             TO AGE-RETURN
    paymentBillingData.setPatientAge(calculationContext.getPatientAge());
    //        MOVE 0.0                       TO MSA-WAGE-AMT            <--- WILL ALWAYS BE ZERO
    //        MOVE COM-CBSA-W-INDEX          TO CBSA-WAGE-INDEX         <--- NOT USED / INITIALIZED
    //        MOVE H-BMI                     TO PPS-BMI                 <--- NOT USED / INITIALIZED
    //        MOVE H-BSA                     TO PPS-BSA                 <--- NOT USED / INITIALIZED
    //        MOVE MSA-BLEND-PCT             TO MSA-PCT                 <--- WILL ALWAYS BE ZERO
    //        MOVE CBSA-BLEND-PCT            TO CBSA-PCT
    paymentBillingData.setCbsaPercent(BigDecimal.ONE);
    // ************************************************************************
    // No implementation provided for the following block, as the value for
    // P-PROV-WAIVE-BLEND-PAY-INDIC is always true, and the fields default to
    // zero. These fields were also discontinued as of 2014.
    // ************************************************************************
    //        IF P-PROV-WAIVE-BLEND-PAY-INDIC = 'N'  THEN
    //           MOVE COM-CBSA-BLEND-PCT     TO COM-CBSA-PCT-BLEND
    //           MOVE BUN-CBSA-BLEND-PCT     TO BUN-CBSA-PCT-BLEND
    //        ELSE
    //           MOVE ZERO                   TO COM-CBSA-PCT-BLEND      <--- NOT USED / INITIALIZED
    //           MOVE WAIVE-CBSA-BLEND-PCT   TO BUN-CBSA-PCT-BLEND      <--- NOT USED / INITIALIZED
    //        END-IF
    paymentBillingData.setCompositeCbsaBlendPercent(BigDecimal.ZERO);
    paymentBillingData.setBundledCbsaBlendPercent(BigDecimal.ONE);

    final BundledBillingData bundledBillingData = calculationContext.getBundledBillingData();
    //        MOVE H-BUN-BSA                 TO BUN-BSA
    bundledBillingData.setBundledBodySurfaceAreaFactor(calculationContext.getBundledBsa());
    //        MOVE H-BUN-BMI                 TO BUN-BMI
    bundledBillingData.setBundledBodyMassIndexFactor(calculationContext.getBundledBmi());
    //        MOVE H-BUN-ONSET-FACTOR        TO BUN-ONSET-FACTOR
    bundledBillingData.setBundledOnsetFactor(calculationContext.getBundledOnsetFactor());
    //        MOVE H-BUN-COMORBID-MULTIPLIER TO BUN-COMORBID-MULTIPLIER
    bundledBillingData.setBundledComorbidityMultiplier(
        calculationContext.getBundledComorbidityMultiplier());
    //        MOVE H-BUN-LOW-VOL-MULTIPLIER  TO BUN-LOW-VOL-MULTIPLIER
    bundledBillingData.setBundledLowVolumeFacilityAdjustmentPercent(
        calculationContext.getBundledLowVolumeMultiplier());

    final OutlierBillingData outlierBillingData = calculationContext.getOutlierBillingData();
    final AdditionalPricingData additionalPricingData =
        calculationContext.getAdditionalPricingData();
    //        MOVE H-OUT-AGE-FACTOR          TO OUT-AGE-FACTOR
    outlierBillingData.setOutlierAgeAdjustmentFactor(
        calculationContext.getOutlierAgeAdjustmentFactor());
    //        MOVE H-OUT-BSA                 TO OUT-BSA
    outlierBillingData.setOutlierBodySurfaceArea(calculationContext.getOutlierBsa());
    //        MOVE SB-BSA                    TO OUT-SB-BSA
    additionalPricingData.setOutlierSeparatelyBillableBsaAmount(
        calculationContext.getSeparatelyBillableBsa());
    //        MOVE H-OUT-BSA-FACTOR          TO OUT-BSA-FACTOR
    outlierBillingData.setOutlierBodySurfaceAreaFactor(calculationContext.getOutlierBsaFactor());
    //        MOVE H-OUT-BMI                 TO OUT-BMI
    outlierBillingData.setOutlierBodyMassIndex(calculationContext.getOutlierBmi());
    //        MOVE H-OUT-BMI-FACTOR          TO OUT-BMI-FACTOR
    outlierBillingData.setOutlierBodyMassIndexFactor(calculationContext.getOutlierBmiFactor());
    //        MOVE H-OUT-ONSET-FACTOR        TO OUT-ONSET-FACTOR
    outlierBillingData.setOutlierOnsetFactor(calculationContext.getOutlierOnsetFactor());
    //        MOVE H-OUT-COMORBID-MULTIPLIER TO
    //                                    OUT-COMORBID-MULTIPLIER
    outlierBillingData.setOutlierComorbidityMultiplier(
        calculationContext.getOutlierComorbidityMultiplier());
    //        MOVE H-OUT-PREDICTED-SERVICES-MAP  TO
    //                                    OUT-PREDICTED-SERVICES-MAP
    outlierBillingData.setOutlierPredictedServicesMapAmount(
        calculationContext.getOutlierPredictedSvcsMapAmount());
    //        MOVE H-OUT-CM-ADJ-PREDICT-MAP-TRT  TO
    //                                    OUT-CASE-MIX-PREDICTED-MAP
    additionalPricingData.setOutlierCaseMixPredictedMapAmount(
        calculationContext.getOutlierCaseMixAdjPredictedMapTrt());
    //        MOVE H-HEMO-EQUIV-DIAL-SESSIONS    TO
    //                                    OUT-HEMO-EQUIV-DIAL-SESSIONS
    additionalPricingData.setOutlierHemoEquivalentDialysisSessionsAmount(
        calculationContext.getHemoEquivalentDialysisSessions());
    //        MOVE H-OUT-LOW-VOL-MULTIPLIER  TO OUT-LOW-VOL-MULTIPLIER
    outlierBillingData.setOutlierLowVolumeMultiplier(
        calculationContext.getOutlierLowVolumeMultiplier());
    //        MOVE H-OUT-ADJ-AVG-MAP-AMT     TO OUT-ADJ-AVG-MAP-AMT
    outlierBillingData.setOutlierAverageAdjustedMapAmount(
        calculationContext.getOutlierAdjustedAverageMapAmount());
    //        MOVE H-OUT-IMPUTED-MAP         TO OUT-IMPUTED-MAP
    // TODO: Remove once max value computation is automatic
    BigDecimal outlierImputedMapAmount = calculationContext.getOutlierImputedMapAmount();
    if (BigDecimalUtils.isGreaterThan(outlierImputedMapAmount, MAX_OUTLIER_IMPUTED_MAP_AMOUNT)) {
      outlierImputedMapAmount = MAX_OUTLIER_IMPUTED_MAP_AMOUNT;
    }
    outlierBillingData.setOutlierImputedMapAmount(outlierImputedMapAmount);
    //        MOVE H-OUT-FIX-DOLLAR-LOSS     TO OUT-FIX-DOLLAR-LOSS
    outlierBillingData.setOutlierFixedDollarLossAmount(
        calculationContext.getOutlierFixedDollarLoss());
    //        MOVE H-OUT-LOSS-SHARING-PCT    TO OUT-LOSS-SHARING-PCT
    outlierBillingData.setOutlierLossSharingPercent(
        calculationContext.getOutlierLossSharingPercentage());
    //        MOVE H-OUT-PREDICTED-MAP       TO OUT-PREDICTED-MAP
    outlierBillingData.setOutlierPredictedMapAmount(
        calculationContext.getOutlierPredictedMapAmount());
    //        MOVE CR-BSA                    TO CR-BSA-MULTIPLIER
    additionalPricingData.setCompositeRateBsaMultiplier(
        EsrdPricerContext.COMPOSITE_RATE_BSA_MULTIPLIER);
    //        MOVE CR-BMI-LT-18-5            TO CR-BMI-MULTIPLIER
    additionalPricingData.setCompositeRateBmiMultiplier(
        EsrdPricerContext.COMPOSITE_RATE_BMI_UNDER_CUTOFF_MULTIPLIER);
    //        MOVE A-49-CENT-PART-D-DRUG-ADJ TO A-49-CENT-DRUG-ADJ      <-- 2011 only
    additionalPricingData.setDrugAndBiologicalAdjustmentAmount(new BigDecimal("0.49"));
    //        MOVE CM-BSA                    TO PPS-CM-BSA
    additionalPricingData.setCaseMixBsaMultiplier(calculationContext.getCaseMixBsaMultiplier());
    //        MOVE CM-BMI-LT-18-5            TO PPS-CM-BMI-LT-18-5
    additionalPricingData.setCaseMixBmiUnderEighteenPointFiveMultiplier(
        calculationContext.getCaseMixBmiUnderEighteenPointFiveMultiplier());
    //        MOVE BUNDLED-BASE-PMT-RATE     TO PPS-BUN-BASE-PMT-RATE
    additionalPricingData.setBundledBasePaymentRate(calculationContext.getBundledBasePaymentRate());
    //        MOVE BUN-CBSA-W-INDEX          TO PPS-BUN-CBSA-W-INDEX
    paymentData.setFinalWageIndex(calculationContext.getBundledWageIndex());
    //        MOVE H-BUN-ADJUSTED-BASE-WAGE-AMT  TO
    //                                    BUN-ADJUSTED-BASE-WAGE-AMT
    additionalPricingData.setBundledAdjustedBaseWageAmount(
        calculationContext.getBundledAdjustedBaseWageAmount());
    //        MOVE H-BUN-WAGE-ADJ-TRAINING-AMT   TO
    //                                    PPS-BUN-WAGE-ADJ-TRAIN-AMT
    additionalPricingData.setBundledWageAdjustmentTrainingAmount(
        calculationContext.getBundledWageAdjustedTrainingAmount());
    //        MOVE TRAINING-ADD-ON-PMT-AMT   TO
    //                                    PPS-TRAINING-ADD-ON-PMT-AMT
    additionalPricingData.setTrainingAddOnPaymentAmount(
        calculationContext.getTrainingAddOnPaymentAmount());
    //        MOVE H-PAYMENT-RATE            TO COM-PAYMENT-RATE
    additionalPricingData.setCompositePaymentRate(BigDecimal.ZERO);
    //     END-IF.
  }
}
