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
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.CalculateBurnRelatedDrgs;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.CalculateCapital2BFederalSpecificPortionPart;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.CalculateCapitalCosts;
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
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2023.CalculateCeramentGAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2023.CalculateGoreTagAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2023.CalculateIFuseAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2023.CalculateThoraflexAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2023.CalculateViviStimAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2024.CalculateAveirAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2024.CalculateCeribellAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2024.CalculateCytaluxLungAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2024.CalculateCytaluxOvarianAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2024.CalculateDefenCathAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2024.CalculateDetourSystemAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2024.CalculateEchoGoAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2024.CalculateEpkinlyColumviAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2024.CalculateLunsumioAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2024.CalculatePhagenyxSystemAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2024.CalculateRebyotaVowstAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2024.CalculateRezzayoAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2024.CalculateSaintNeuromodAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2024.CalculateSpevigoAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2024.CalculateTecvayliAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2024.CalculateTerlivazAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2024.CalculateTopsSystemAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2024.CalculateXacduroAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2025.CalculateAnnaliseAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2025.CalculateAstarAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2025.CalculateCasgevyAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2025.CalculateElrexfioTalveyAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2025.CalculateEvoqueAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2025.CalculateHepzatoKitAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2025.CalculateLimFlowAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2025.CalculateLyfgeniaAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2025.CalculateParadiseUltrasoundAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2025.CalculatePulseSelectAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2025.CalculateSymplicitySpyralAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2025.CalculateTambeAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2025.CalculateTriclipAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2025.CalculateVaderAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2025.CalculateZevteraAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.totals.CalculateCapitalFinalTotal;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.totals.CalculateCapitalIntermediateVariables;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.totals.CalculateHmoTotals;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.totals.CalculateHospitalAcquiredConditionReduction;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.totals.CalculateLowVolumeTotals;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.totals.CalculateOperatingTotals;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.totals.CalculateStandardizedCharges;
import gov.cms.fiss.pricers.ipps.core.rules.cost_factor_determination.DetermineCostOfLivingAdjustment;
import gov.cms.fiss.pricers.ipps.core.rules.cost_factor_determination.DetermineDiagnosticRelatedGroup;
import gov.cms.fiss.pricers.ipps.core.rules.cost_factor_determination.ExtractUncompensatedCareAmount;
import gov.cms.fiss.pricers.ipps.core.rules.cost_factor_determination.RetrieveLaborCosts;
import gov.cms.fiss.pricers.ipps.core.rules.cost_factor_determination.ratex.DetermineBlendPercents;
import gov.cms.fiss.pricers.ipps.core.rules.cost_factor_determination.ratex.DetermineLaborCosts;
import gov.cms.fiss.pricers.ipps.core.rules.rules2021.AdditionalVariableUpdate2021;
import gov.cms.fiss.pricers.ipps.core.rules.rules2021.assemble_variables.DetermineNoCostProductAdjustment;
import gov.cms.fiss.pricers.ipps.core.rules.rules2021.calculate_payment.CalculateBundleReduction2021;
import gov.cms.fiss.pricers.ipps.core.rules.rules2021.calculate_payment.CalculateCapitalFederalSpecificPortionAmount2021;
import gov.cms.fiss.pricers.ipps.core.rules.rules2021.calculate_payment.CalculateOperatingFederalSpecificPortionAmount2021;
import gov.cms.fiss.pricers.ipps.core.rules.rules2021.calculate_payment.DetermineOutlierCosts2021;
import gov.cms.fiss.pricers.ipps.core.rules.rules2022.calculate_payment.CalculateAdditionalHospitalSpecificPortion2022;
import gov.cms.fiss.pricers.ipps.core.rules.rules2022.calculate_payment.totals.CalculateElectronicHealthRecordSavings2022;
import gov.cms.fiss.pricers.ipps.core.rules.rules2022.cost_factor_determination.ratex.DetermineRateTableWithReduction2022;
import gov.cms.fiss.pricers.ipps.core.rules.rules2022.cost_factor_determination.ratex.DetermineRateTableWithoutReduction2022;
import gov.cms.fiss.pricers.ipps.core.rules.rules2022.wage_index.AdjustIndexForImputedFloor2022;
import gov.cms.fiss.pricers.ipps.core.rules.rules2023.wage_index.AdjustIndexForPreviousYear2023;
import gov.cms.fiss.pricers.ipps.core.rules.rules2023.wage_index.AdjustIndexForRuralFloor2023;
import gov.cms.fiss.pricers.ipps.core.rules.rules2024.calculate_payment.CalculateCapitalDisproportionateShareHospital2024;
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

/** Defines the processing flow for 2024 claims. */
public class Ipps2025RulePricer extends IppsRulePricer {

  /** Defines the rule set for processing claims for 2024. */
  private static final List<
          CalculationRule<IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext>>
      YEAR_RULES = rules();

  public Ipps2025RulePricer(DataTables dataTables) {
    super(dataTables, YEAR_RULES);
  }

  private static List<
          CalculationRule<IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext>>
      rules() {
    // Defines the repeated sub-sequence of rules
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
                        new DetermineNoCostProductAdjustment(),
                        new RetrieveLaborCosts(
                            List.of(
                                new DetermineRateTableWithoutReduction2022(),
                                new DetermineRateTableWithReduction2022(),
                                new DetermineLaborCosts(),
                                new DetermineBlendPercents())))),
                new DefaultPaymentCalculation(
                    List.of(
                        new CalculateStayUtilization(),
                        new CalculateOperatingFederalSpecificPortionAmount2021(),
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
                        new CalculateCapitalDisproportionateShareHospital2024(),
                        new CalculateCapitalIndirectMedicalEducation(),
                        new CalculateBurnRelatedDrgs(),
                        new CalculateNationalPercentages(),
                        new CalculateCapitalPaymentMethodB(),
                        new CalculateCapitalFederalSpecificPortionAmount2021(),
                        new CalculateCapitalPaymentMethodA(),
                        new CalculateCapitalOldHoldHarmlessAmount(),
                        new CalculatePerDiemAmounts(),
                        new CalculateTechAddOns(
                            List.of(
                                new InitializeOperatingCosts(),
                                new AddIsletIsolationCosts(),
                                new AddNewTechnologyCosts(
                                    List.of(
                                        new CalculateAnnaliseAddOnCost(),
                                        new CalculateAstarAddOnCost(),
                                        new CalculateAveirAddOnCost(),
                                        new CalculateCasgevyAddOnCost(),
                                        new CalculateCeramentGAddOnCost(),
                                        new CalculateCeribellAddOnCost(),
                                        new CalculateCytaluxLungAddOnCost(),
                                        new CalculateCytaluxOvarianAddOnCost(),
                                        new CalculateDefenCathAddOnCost(),
                                        new CalculateDetourSystemAddOnCost(),
                                        new CalculateEchoGoAddOnCost(),
                                        new CalculateElrexfioTalveyAddOnCost(),
                                        new CalculateEpkinlyColumviAddOnCost(),
                                        new CalculateEvoqueAddOnCost(),
                                        new CalculateGoreTagAddOnCost(),
                                        new CalculateHepzatoKitAddOnCost(),
                                        new CalculateIFuseAddOnCost(),
                                        new CalculateLimFlowAddOnCost(),
                                        new CalculateLunsumioAddOnCost(),
                                        new CalculateLyfgeniaAddOnCost(),
                                        new CalculateParadiseUltrasoundAddOnCost(),
                                        new CalculatePhagenyxSystemAddOnCost(),
                                        new CalculatePulseSelectAddOnCost(),
                                        new CalculateRebyotaVowstAddOnCost(),
                                        new CalculateRezzayoAddOnCost(),
                                        new CalculateSaintNeuromodAddOnCost(),
                                        new CalculateSpevigoAddOnCost(),
                                        new CalculateSymplicitySpyralAddOnCost(),
                                        new CalculateTambeAddOnCost(),
                                        new CalculateTecvayliAddOnCost(),
                                        new CalculateTerlivazAddOnCost(),
                                        new CalculateThoraflexAddOnCost(),
                                        new CalculateTopsSystemAddOnCost(),
                                        new CalculateTriclipAddOnCost(),
                                        new CalculateVaderAddOnCost(),
                                        new CalculateViviStimAddOnCost(),
                                        new CalculateXacduroAddOnCost(),
                                        new CalculateZevteraAddOnCost())),
                                new CapCalculatedTechAddOn())),
                        new CalculateOutliers(
                            List.of(
                                new CalculateOperAndCapitalCostOutliers(),
                                new CalculateOutlierThresholdAmounts(),
                                new CalculateOperatingCosts(),
                                new CalculateCapitalCosts(),
                                new DetermineOutlierCosts2021())),
                        new CalculateReadmissionReduction(),
                        new CalculateValueBasedPurchasingAdjustments(),
                        new CalculateBundleReduction2021(),
                        new PostProcessOutlierCosts(
                            List.of(
                                new AnalyzeOutlierCalculationResults(),
                                new CalculateAdditionalHospitalSpecificPortion2022(),
                                new CalculateCapital2BFederalSpecificPortionPart(),
                                new CalculateTotals(
                                    List.of(
                                        new CalculateCapitalIntermediateVariables(),
                                        new CalculateOperatingTotals(),
                                        new CalculateHmoTotals(),
                                        new CalculateLowVolumeTotals(),
                                        new CalculateCapitalFinalTotal(),
                                        new CalculateElectronicHealthRecordSavings2022(),
                                        new CalculateStandardizedCharges(),
                                        new CalculateHospitalAcquiredConditionReduction())))))));

    // Defines overall processing sequence
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
                        new AdjustIndexForRuralFloor2023(),
                        new AdjustIndexForImputedFloor2022(),
                        new AdjustForOutMigration(),
                        new AdjustIndexForPreviousYear2023())),
                new AdjustIndexIfIndian(List.of(new UpdateCbsaReference())),
                new ValidateCbsaReference(),
                new DeriveCbsaSize())),
        new DefaultClaimProcessing(
            List.of(
                // First repeat of sub-sequence
                new DefaultClaimPricing(mainPricingRules),
                new AdditionalVariableUpdate2021(),
                new SetDrgCostAdditionalVariables(),
                // Last repeat of sub-sequence
                new DefaultOutlierPricing(mainPricingRules),
                new DefaultCheckWaiverCode(),
                new DefaultClearOutputOnError())));
  }

  @Override
  protected IppsPricerContext contextFor(IppsClaimPricingRequest input) {
    final IppsClaimPricingResponse output = new IppsClaimPricingResponse();

    return new Ipps2025PricerContext(input, output, dataTables);
  }
}
