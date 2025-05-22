package gov.cms.fiss.pricers.esrd.core;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.rules.CalculateBundledWageAdjustedRate;
import gov.cms.fiss.pricers.esrd.core.rules.InitializeData;
import gov.cms.fiss.pricers.esrd.core.rules.MoveResults;
import gov.cms.fiss.pricers.esrd.core.rules.ProcessClaim;
import gov.cms.fiss.pricers.esrd.core.rules.SetConditionCode;
import gov.cms.fiss.pricers.esrd.core.rules.SetDefaultValues;
import gov.cms.fiss.pricers.esrd.core.rules.SetReturnCode;
import gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.CalculateBundledAdjustedPpsBaseRate;
import gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.CalculateBundledBmiFactor;
import gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.CalculateBundledBsaFactor;
import gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.CalculateBundledFactors;
import gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.CalculateBundledLowVolumeAdjustment;
import gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.CalculateBundledOnsetFactor;
import gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.CalculateRuralAdjustmentMultiplier;
import gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.ComputePatientAge;
import gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.ConvertComorbidityCwfReturnCode;
import gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.DetermineQipReduction;
import gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.SetBundledAgeAdjustmentFactor;
import gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.SetBundledComorbiditiesAdjustment;
import gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.low_volume.CalculateBundledAdjustedBaseWageAmount;
import gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.low_volume.CalculateBundledWageAdjustedTrainingAmount;
import gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.low_volume.CalculateLowVolFinalPaymentAmount;
import gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.low_volume.CalculateLowVolOutlierCaseMixAdjPredSvcsMapPerTrt;
import gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.low_volume.CalculateLowVolOutlierPredSvcsMap;
import gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.low_volume.CalculateLowVolumeRecoveryPayment;
import gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.low_volume.CalculatePaymentAmounts;
import gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.low_volume.CompareLowVolPredictedToImputedOutlierSvcsMapPerTrt;
import gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.outlier.CalculateBundledOutliers;
import gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.outlier.CalculateCaseMixAdjOutlierPredSvcsMapPerTrt;
import gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.outlier.CalculateOutlierRuralAdjustmentMultiplier;
import gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.outlier.CalculatePredictedOutlierSvcsMapPerTrt;
import gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.outlier.CalculateSeparatelyBillableOutlierBmiFactor;
import gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.outlier.CalculateSeparatelyBillableOutlierBsaFactor;
import gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.outlier.CalculateSeparatelyBillableOutlierOnsetFactor;
import gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.outlier.ComparePredictedToImputedOutlierSvcsMapPerTrt;
import gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.outlier.SetOutlierLowVolumeMultiplier;
import gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.outlier.SetSeparatelyBillableOutlierAgeAdjustmentFactor;
import gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.outlier.SetSeparatelyBillableOutlierComorbiditiesMultiplier;
import gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.transitional_payments.CalculateTdapaPayment;
import gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.transitional_payments.CalculateTransitionalPayments;
import gov.cms.fiss.pricers.esrd.core.rules.move_results.MoveAdditionalDataResults;
import gov.cms.fiss.pricers.esrd.core.rules.rules_2020_2.bundled_factors.CalculateBundledPpsFinalPaymentRate2020Dot2;
import gov.cms.fiss.pricers.esrd.core.rules.rules_2021.bundled_factors.transitional_payments.CalculateTpniesPayment2021;
import gov.cms.fiss.pricers.esrd.core.rules.rules_2022.bundled_factors.CalculateBundledConditionCodePayment2022;
import gov.cms.fiss.pricers.esrd.core.rules.rules_2022.bundled_factors.transitional_payments.AdjustFinalPayment2022;
import gov.cms.fiss.pricers.esrd.core.rules.rules_2022.bundled_factors.transitional_payments.CalculateCraTpniesPayment2022;
import gov.cms.fiss.pricers.esrd.core.rules.rules_2022.move_results.MoveBaseResults2022;
import gov.cms.fiss.pricers.esrd.core.rules.rules_2024.CalculateAkiPayment2024AndEarlier;
import gov.cms.fiss.pricers.esrd.core.rules.rules_2025.CalculateEsrdPayment2025;
import gov.cms.fiss.pricers.esrd.core.rules.rules_2025.SetNetworkReductionAmount2025;
import gov.cms.fiss.pricers.esrd.core.rules.rules_2025.bundled_factors.outliers.CalculateImputedOutlierSvcsMapPerTrt2025;
import gov.cms.fiss.pricers.esrd.core.rules.rules_2025.bundled_factors.outliers.CalculateOutlierPerDiem2025;
import gov.cms.fiss.pricers.esrd.core.rules.validation.BillValidation;
import gov.cms.fiss.pricers.esrd.core.rules.validation.ValidateCraTpnies;
import gov.cms.fiss.pricers.esrd.core.rules.validation.ValidateCwfComorbidityReturnCode;
import gov.cms.fiss.pricers.esrd.core.rules.validation.ValidateDateOfBirth;
import gov.cms.fiss.pricers.esrd.core.rules.validation.ValidateDialysisSessionCount;
import gov.cms.fiss.pricers.esrd.core.rules.validation.ValidateMaximumBodyMassIndex;
import gov.cms.fiss.pricers.esrd.core.rules.validation.ValidateMaximumHeight;
import gov.cms.fiss.pricers.esrd.core.rules.validation.ValidateMaximumWeight;
import gov.cms.fiss.pricers.esrd.core.rules.validation.ValidatePPAPercentage;
import gov.cms.fiss.pricers.esrd.core.rules.validation.ValidatePatientHeight;
import gov.cms.fiss.pricers.esrd.core.rules.validation.ValidatePatientWeight;
import gov.cms.fiss.pricers.esrd.core.rules.validation.ValidateProviderType;
import gov.cms.fiss.pricers.esrd.core.rules.validation.ValidateQualityIncentiveProgramReduction;
import gov.cms.fiss.pricers.esrd.core.rules.validation.ValidateRevenueCode;
import gov.cms.fiss.pricers.esrd.core.rules.validation.ValidateSpecialPaymentIndicator;
import gov.cms.fiss.pricers.esrd.core.rules.validation.ValidateTotalPriceSeparatelyBillableOutlier;
import gov.cms.fiss.pricers.esrd.core.rules.wage_index.ApplySpecialWageIndex;
import gov.cms.fiss.pricers.esrd.core.rules.wage_index.ExtractWageIndex;
import gov.cms.fiss.pricers.esrd.core.rules.wage_index.RetrieveCbsaWageIndex;
import gov.cms.fiss.pricers.esrd.core.tables.DataTables;
import java.util.List;

public class EsrdRulePricer2022 extends EsrdRulePricer {

  private static final List<
          CalculationRule<EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext>>
      RULES = rules();

  public EsrdRulePricer2022(DataTables dataTables) {
    super(dataTables, RULES);
  }

  private static List<
          CalculationRule<EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext>>
      rules() {
    return List.of(
        new SetDefaultValues(),
        new ProcessClaim(
            List.of(
                new InitializeData(),
                new SetConditionCode(),
                new ExtractWageIndex(
                    List.of(new ApplySpecialWageIndex(), new RetrieveCbsaWageIndex())),
                new BillValidation(
                    List.of(
                        new ValidateCraTpnies(),
                        new ValidatePPAPercentage(),
                        new ValidateProviderType(),
                        new ValidateSpecialPaymentIndicator(),
                        new ValidateDateOfBirth(),
                        new ValidatePatientWeight(),
                        new ValidatePatientHeight(),
                        new ValidateRevenueCode(),
                        new ValidateQualityIncentiveProgramReduction(),
                        new ValidateMaximumHeight(),
                        new ValidateMaximumWeight(),
                        new ValidateMaximumBodyMassIndex(),
                        new ValidateDialysisSessionCount(),
                        new ValidateTotalPriceSeparatelyBillableOutlier(),
                        new ValidateCwfComorbidityReturnCode())),
                new CalculateBundledWageAdjustedRate(),
                new CalculateAkiPayment2024AndEarlier(),
                new CalculateEsrdPayment2025(
                    List.of(
                        new CalculateBundledFactors(
                            List.of(
                                new ComputePatientAge(),
                                new DetermineQipReduction(),
                                new ConvertComorbidityCwfReturnCode(),
                                new SetBundledAgeAdjustmentFactor(),
                                new CalculateBundledBsaFactor(),
                                new CalculateBundledBmiFactor(),
                                new CalculateBundledOnsetFactor(),
                                new SetBundledComorbiditiesAdjustment(),
                                new CalculateBundledLowVolumeAdjustment(),
                                new CalculateRuralAdjustmentMultiplier(),
                                new CalculateBundledAdjustedPpsBaseRate(),
                                new CalculateBundledConditionCodePayment2022(),
                                new CalculateBundledPpsFinalPaymentRate2020Dot2(),
                                new CalculateTransitionalPayments(
                                    List.of(
                                        new CalculateTdapaPayment(),
                                        new CalculateTpniesPayment2021(),
                                        new CalculateCraTpniesPayment2022(),
                                        new AdjustFinalPayment2022())),
                                new SetNetworkReductionAmount2025(),
                                new CalculateBundledOutliers(
                                    List.of(
                                        new SetSeparatelyBillableOutlierAgeAdjustmentFactor(),
                                        new CalculateSeparatelyBillableOutlierBsaFactor(),
                                        new CalculateSeparatelyBillableOutlierBmiFactor(),
                                        new CalculateSeparatelyBillableOutlierOnsetFactor(),
                                        new SetSeparatelyBillableOutlierComorbiditiesMultiplier(),
                                        new SetOutlierLowVolumeMultiplier(),
                                        new CalculateOutlierRuralAdjustmentMultiplier(),
                                        new CalculatePredictedOutlierSvcsMapPerTrt(),
                                        new CalculateCaseMixAdjOutlierPredSvcsMapPerTrt(),
                                        new CalculateImputedOutlierSvcsMapPerTrt2025(),
                                        new ComparePredictedToImputedOutlierSvcsMapPerTrt(),
                                        new CalculateOutlierPerDiem2025())),
                                new CalculateLowVolumeRecoveryPayment(
                                    List.of(
                                        new CalculateBundledAdjustedBaseWageAmount(),
                                        new CalculateBundledWageAdjustedTrainingAmount(),
                                        new CalculateLowVolFinalPaymentAmount(),
                                        new CalculateLowVolOutlierPredSvcsMap(),
                                        new CalculateLowVolOutlierCaseMixAdjPredSvcsMapPerTrt(),
                                        new CompareLowVolPredictedToImputedOutlierSvcsMapPerTrt(),
                                        new CalculatePaymentAmounts())),
                                new SetReturnCode())))),
                new MoveResults(
                    List.of(new MoveBaseResults2022(), new MoveAdditionalDataResults())))));
  }

  @Override
  protected EsrdPricerContext contextFor(EsrdClaimPricingRequest input) {
    return new EsrdPricerContext2022(input, createDefaultResponse(), dataTables);
  }
}
