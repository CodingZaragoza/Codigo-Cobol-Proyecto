package gov.cms.fiss.pricers.ipps.core;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.rules.DefaultCheckWaiverCode;
import gov.cms.fiss.pricers.ipps.core.rules.DefaultClaimPricing;
import gov.cms.fiss.pricers.ipps.core.rules.DefaultClaimProcessing;
import gov.cms.fiss.pricers.ipps.core.rules.DefaultClearOutputOnError;
import gov.cms.fiss.pricers.ipps.core.rules.DefaultElectronicHealthRecordValidation;
import gov.cms.fiss.pricers.ipps.core.rules.DefaultOutlierPricing;
import gov.cms.fiss.pricers.ipps.core.rules.DefaultPaymentCalculation;
import gov.cms.fiss.pricers.ipps.core.rules.DetermineCostsAndFactors;
import gov.cms.fiss.pricers.ipps.core.rules.EvaluateWebPricerParameters;
import gov.cms.fiss.pricers.ipps.core.rules.SetDrgCostAdditionalVariables;
import gov.cms.fiss.pricers.ipps.core.rules.ValidateClaimBilling;
import gov.cms.fiss.pricers.ipps.core.rules.billing_validation.CalculateRegularDays;
import gov.cms.fiss.pricers.ipps.core.rules.billing_validation.ValidateCapitalPpsPaymentCode;
import gov.cms.fiss.pricers.ipps.core.rules.billing_validation.ValidateCoveredDays;
import gov.cms.fiss.pricers.ipps.core.rules.billing_validation.ValidateDischargeDateVsEffectiveDate;
import gov.cms.fiss.pricers.ipps.core.rules.billing_validation.ValidateDischargeDateVsTerminationDate;
import gov.cms.fiss.pricers.ipps.core.rules.billing_validation.ValidateLengthOfStay;
import gov.cms.fiss.pricers.ipps.core.rules.billing_validation.ValidateLifetimeReserveDays;
import gov.cms.fiss.pricers.ipps.core.rules.billing_validation.ValidateReviewCode;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.AnalyzeOutlierCalculationResults;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.CalculateBundleReduction;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.CalculateBurnRelatedDrgs;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.CalculateCapital2BFederalSpecificPortionPart;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.CalculateCapitalCosts;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.CalculateCapitalDisproportionateShareHospital;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.CalculateCapitalFederalSpecificPortionAmount;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.CalculateCapitalIndirectMedicalEducation;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.CalculateCapitalOldHoldHarmlessAmount;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.CalculateCapitalPaymentMethodA;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.CalculateCapitalPaymentMethodB;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.CalculateFractionalAdjustments;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.CalculateNationalPercentages;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.CalculateOperAndCapitalCostOutliers;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.CalculateOperatingCosts;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.CalculateOperatingDisproportionateShare;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.CalculateOperatingIndirectMedicalEducation;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.CalculateOutlierThresholdAmounts;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.CalculateOutliers;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.CalculatePerDiemAmounts;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.CalculateReadmissionReduction;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.CalculateStayUtilization;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.CalculateTechAddOns;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.CalculateTotals;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.CalculateValueBasedPurchasingAdjustments;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.DetermineOutlierCosts;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.InitializeCalculationFields;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.PostProcessOutlierCosts;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.disproportionate_share.CalculateDisproportionateShareForHospitalsUnder100Beds;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.disproportionate_share.CalculateDisproportionateShareForHospitalsWithAtLeast100Beds;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.disproportionate_share.CalculateDisproportionateShareForRuralHospitalsUnder500Beds;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.disproportionate_share.CalculateDisproportionateShareForRuralHospitalsWithAtLeast500Beds;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.disproportionate_share.CalculateDisproportionateShareForRuralRrcProviders;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.disproportionate_share.CalculateDisproportionateShareForRuralSchProviders;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.AddIsletIsolationCosts;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.AddNewTechnologyCosts;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.CapCalculatedTechAddOn;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.InitializeOperatingCosts;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2019.CalculateAndexaxaAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2019.CalculateAquabeamAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2019.CalculateGiaprezaAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2019.CalculateKymriahAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2019.CalculateRemedeAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2019.CalculateSentinelAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2019.CalculateVabomereAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2019.CalculateVyxeosAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2019.CalculateZemdriAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2020.CalculateAzedraAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2020.CalculateBalversaAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2020.CalculateCabliviAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2020.CalculateElzonrisAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2020.CalculateErleadaAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2020.CalculateJakafiAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2020.CalculateSpravatoAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2020.CalculateT2BacteriaAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2020.CalculateXospataAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.totals.CalculateCapitalFinalTotal;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.totals.CalculateCapitalIntermediateVariables;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.totals.CalculateElectronicHealthRecordSavings;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.totals.CalculateHmoTotals;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.totals.CalculateHospitalAcquiredConditionReduction;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.totals.CalculateLowVolumeTotals;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.totals.CalculateOperatingTotals;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.totals.CalculateStandardizedCharges;
import gov.cms.fiss.pricers.ipps.core.rules.cost_factor_determination.AdjustForMidnightFactors;
import gov.cms.fiss.pricers.ipps.core.rules.cost_factor_determination.DetermineCostOfLivingAdjustment;
import gov.cms.fiss.pricers.ipps.core.rules.cost_factor_determination.DetermineDiagnosticRelatedGroup;
import gov.cms.fiss.pricers.ipps.core.rules.cost_factor_determination.ExtractUncompensatedCareAmount;
import gov.cms.fiss.pricers.ipps.core.rules.cost_factor_determination.RetrieveLaborCosts;
import gov.cms.fiss.pricers.ipps.core.rules.cost_factor_determination.ratex.DetermineBlendPercents;
import gov.cms.fiss.pricers.ipps.core.rules.cost_factor_determination.ratex.DetermineLaborCosts;
import gov.cms.fiss.pricers.ipps.core.rules.cost_factor_determination.ratex.DetermineRateTableWithReduction;
import gov.cms.fiss.pricers.ipps.core.rules.cost_factor_determination.ratex.DetermineRateTableWithoutReduction;
import gov.cms.fiss.pricers.ipps.core.rules.rules2020.AdditionalVariableUpdate2020;
import gov.cms.fiss.pricers.ipps.core.rules.rules2020.assemble_variables.DetermineCovidAdjustment;
import gov.cms.fiss.pricers.ipps.core.rules.rules2020.calculate_payment.CalculateAdditionalHospitalSpecificPortion2020;
import gov.cms.fiss.pricers.ipps.core.rules.rules2020.calculate_payment.CalculateOperatingFederalSpecificPortionAmount2020;
import gov.cms.fiss.pricers.ipps.core.rules.rules2020.wage_index.AdjustIndexForPreviousYear2020;
import gov.cms.fiss.pricers.ipps.core.rules.rules2020.wage_index.AdjustIndexForQuartile2020;
import gov.cms.fiss.pricers.ipps.core.rules.rules2020.wage_index.AdjustIndexForRuralFloor2020;
import gov.cms.fiss.pricers.ipps.core.rules.wage_index.AdjustForOutMigration;
import gov.cms.fiss.pricers.ipps.core.rules.wage_index.AdjustIndexIfIndian;
import gov.cms.fiss.pricers.ipps.core.rules.wage_index.AdjustWageIndex;
import gov.cms.fiss.pricers.ipps.core.rules.wage_index.DeriveCbsaSize;
import gov.cms.fiss.pricers.ipps.core.rules.wage_index.DetermineCbsaWageIndex;
import gov.cms.fiss.pricers.ipps.core.rules.wage_index.PopulateCbsaLocations;
import gov.cms.fiss.pricers.ipps.core.rules.wage_index.SelectCbsaLocation;
import gov.cms.fiss.pricers.ipps.core.rules.wage_index.UpdateCbsaReference;
import gov.cms.fiss.pricers.ipps.core.rules.wage_index.ValidateCbsaReference;
import gov.cms.fiss.pricers.ipps.core.rules.wage_index.ValidateOutMigration;
import gov.cms.fiss.pricers.ipps.core.rules.wage_index.ValidateSpecialWageIndex;
import gov.cms.fiss.pricers.ipps.core.rules.wage_index.WageIndexDerivation;
import gov.cms.fiss.pricers.ipps.core.tables.DataTables;
import java.util.List;

public class Ipps2020RulePricer extends IppsRulePricer {
  private static final List<
          CalculationRule<IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext>>
      YEAR_RULES = rules();

  public Ipps2020RulePricer(DataTables dataTables) {
    super(dataTables, YEAR_RULES);
  }

  private static List<
          CalculationRule<IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext>>
      rules() {
    final List<
            CalculationRule<IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext>>
        mainPricingRules =
            List.of(
                new ValidateClaimBilling(
                    List.of(
                        new ValidateDischargeDateVsEffectiveDate(),
                        new ValidateDischargeDateVsTerminationDate(),
                        new ValidateLengthOfStay(),
                        new ValidateLifetimeReserveDays(),
                        new ValidateCoveredDays(),
                        new CalculateRegularDays(),
                        new ValidateReviewCode(),
                        new ValidateCapitalPpsPaymentCode())),
                new DetermineCostsAndFactors(
                    List.of(
                        new DetermineCostOfLivingAdjustment(),
                        new ExtractUncompensatedCareAmount(),
                        new DetermineDiagnosticRelatedGroup(),
                        new DetermineCovidAdjustment(),
                        new RetrieveLaborCosts(
                            List.of(
                                new DetermineRateTableWithoutReduction(),
                                new DetermineRateTableWithReduction(),
                                new DetermineLaborCosts(),
                                new DetermineBlendPercents())),
                        new AdjustForMidnightFactors())),
                new DefaultPaymentCalculation(
                    List.of(
                        new CalculateStayUtilization(),
                        new CalculateOperatingFederalSpecificPortionAmount2020(),
                        new CalculateOperatingDisproportionateShare(
                            List.of(
                                new CalculateDisproportionateShareForHospitalsUnder100Beds(),
                                new CalculateDisproportionateShareForHospitalsWithAtLeast100Beds(),
                                new CalculateDisproportionateShareForRuralHospitalsUnder500Beds(),
                                new CalculateDisproportionateShareForRuralHospitalsWithAtLeast500Beds(),
                                new CalculateDisproportionateShareForRuralSchProviders(),
                                new CalculateDisproportionateShareForRuralRrcProviders())),
                        new CalculateOperatingIndirectMedicalEducation(),
                        new InitializeCalculationFields(),
                        new CalculateFractionalAdjustments(),
                        new CalculateCapitalDisproportionateShareHospital(),
                        new CalculateCapitalIndirectMedicalEducation(),
                        new CalculateBurnRelatedDrgs(),
                        new CalculateNationalPercentages(),
                        new CalculateCapitalPaymentMethodB(),
                        new CalculateCapitalFederalSpecificPortionAmount(),
                        new CalculateCapitalPaymentMethodA(),
                        new CalculateCapitalOldHoldHarmlessAmount(),
                        new CalculatePerDiemAmounts(),
                        new CalculateTechAddOns(
                            List.of(
                                new InitializeOperatingCosts(),
                                new AddIsletIsolationCosts(),
                                new AddNewTechnologyCosts(
                                    List.of(
                                        new CalculateVabomereAddOnCost(),
                                        new CalculateAndexaxaAddOnCost(),
                                        new CalculateAquabeamAddOnCost(),
                                        new CalculateAzedraAddOnCost(),
                                        new CalculateBalversaAddOnCost(),
                                        new CalculateCabliviAddOnCost(),
                                        new CalculateElzonrisAddOnCost(),
                                        new CalculateErleadaAddOnCost(),
                                        new CalculateGiaprezaAddOnCost(),
                                        new CalculateJakafiAddOnCost(),
                                        new CalculateKymriahAddOnCost(),
                                        new CalculateRemedeAddOnCost(),
                                        new CalculateSentinelAddOnCost(),
                                        new CalculateSpravatoAddOnCost(),
                                        new CalculateT2BacteriaAddOnCost(),
                                        new CalculateVyxeosAddOnCost(),
                                        new CalculateXospataAddOnCost(),
                                        new CalculateZemdriAddOnCost())),
                                new CapCalculatedTechAddOn())),
                        new CalculateReadmissionReduction(),
                        new CalculateValueBasedPurchasingAdjustments(),
                        new CalculateBundleReduction(),
                        new CalculateOutliers(
                            List.of(
                                new CalculateOperAndCapitalCostOutliers(),
                                new CalculateOutlierThresholdAmounts(),
                                new CalculateOperatingCosts(),
                                new CalculateCapitalCosts(),
                                new DetermineOutlierCosts())),
                        new PostProcessOutlierCosts(
                            List.of(
                                new AnalyzeOutlierCalculationResults(),
                                new CalculateAdditionalHospitalSpecificPortion2020(),
                                new CalculateCapital2BFederalSpecificPortionPart(),
                                new CalculateTotals(
                                    List.of(
                                        new CalculateCapitalIntermediateVariables(),
                                        new CalculateOperatingTotals(),
                                        new CalculateHmoTotals(),
                                        new CalculateLowVolumeTotals(),
                                        new CalculateCapitalFinalTotal(),
                                        new CalculateElectronicHealthRecordSavings(),
                                        new CalculateStandardizedCharges(),
                                        new CalculateHospitalAcquiredConditionReduction())))))));

    return List.of(
        new EvaluateWebPricerParameters(),
        new DefaultElectronicHealthRecordValidation(),
        new WageIndexDerivation(
            List.of(
                new ValidateOutMigration(),
                new PopulateCbsaLocations(),
                new SelectCbsaLocation(),
                new ValidateSpecialWageIndex(),
                new DetermineCbsaWageIndex(),
                new AdjustWageIndex(
                    List.of(
                        new UpdateCbsaReference(),
                        new AdjustIndexForRuralFloor2020(),
                        new AdjustForOutMigration(),
                        new AdjustIndexForQuartile2020(),
                        new AdjustIndexForPreviousYear2020())),
                new AdjustIndexIfIndian(List.of(new UpdateCbsaReference())),
                new ValidateCbsaReference(),
                new DeriveCbsaSize())),
        new DefaultClaimProcessing(
            List.of(
                // First repeat of sub-sequence
                new DefaultClaimPricing(mainPricingRules),
                new AdditionalVariableUpdate2020(),
                new SetDrgCostAdditionalVariables(),
                // Last repeat of sub-sequence
                new DefaultOutlierPricing(mainPricingRules),
                new DefaultCheckWaiverCode(),
                new DefaultClearOutputOnError())));
  }

  @Override
  protected IppsPricerContext contextFor(IppsClaimPricingRequest input) {
    final IppsClaimPricingResponse output = new IppsClaimPricingResponse();

    return new Ipps2020PricerContext(input, output, dataTables);
  }
}
