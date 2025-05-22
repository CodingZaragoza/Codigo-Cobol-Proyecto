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
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.CalculateCapitalDisproportionateShareHospital;
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
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2019.CalculateAndexaxaAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2019.CalculateZemdriAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2020.CalculateAzedraAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2020.CalculateBalversaAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2020.CalculateCabliviAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2020.CalculateElzonrisAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2020.CalculateJakafiAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2020.CalculateSpravatoAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2020.CalculateT2BacteriaAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2020.CalculateXospataAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2021.CalculateBarostimAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2021.CalculateContactAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2021.CalculateEluviaAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2021.CalculateFetrojaAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2021.CalculateHemosprayAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2021.CalculateImfinziAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2021.CalculateNuzyraAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2021.CalculateOptimizerAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2021.CalculateRecarbrioAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2021.CalculateSolirisAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2021.CalculateSpinejackAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2021.CalculateTecentriqAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2021.CalculateXenletaAddOnCost;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology.nt2021.CalculateZerbaxaAddOnCost;
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
import gov.cms.fiss.pricers.ipps.core.rules.rules2021.AdditionalVariableUpdate2021;
import gov.cms.fiss.pricers.ipps.core.rules.rules2021.assemble_variables.DetermineCovidAdjustment2021;
import gov.cms.fiss.pricers.ipps.core.rules.rules2021.assemble_variables.DetermineNewCovid19TreatmentsAddOnPayment2021;
import gov.cms.fiss.pricers.ipps.core.rules.rules2021.assemble_variables.DetermineNoCostProductAdjustment;
import gov.cms.fiss.pricers.ipps.core.rules.rules2021.calculate_payment.CalculateAdditionalHospitalSpecificPortion2021;
import gov.cms.fiss.pricers.ipps.core.rules.rules2021.calculate_payment.CalculateBundleReduction2021;
import gov.cms.fiss.pricers.ipps.core.rules.rules2021.calculate_payment.CalculateCapitalFederalSpecificPortionAmount2021;
import gov.cms.fiss.pricers.ipps.core.rules.rules2021.calculate_payment.CalculateOperatingFederalSpecificPortionAmount2021;
import gov.cms.fiss.pricers.ipps.core.rules.rules2021.calculate_payment.DetermineOutlierCosts2021;
import gov.cms.fiss.pricers.ipps.core.rules.rules2021.wage_index.AdjustIndexForPreviousYear2021;
import gov.cms.fiss.pricers.ipps.core.rules.rules2021.wage_index.AdjustIndexForQuartile2021;
import gov.cms.fiss.pricers.ipps.core.rules.rules2021.wage_index.AdjustIndexForRuralFloor2021;
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

/** Defines the processing flow for 2021 claims. */
public class Ipps2021RulePricer extends IppsRulePricer {
  /** Defines the rule set for processing claims for 2021. */
  private static final List<
          CalculationRule<IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext>>
      YEAR_RULES = rules();

  public Ipps2021RulePricer(DataTables dataTables) {
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
                        new DetermineCovidAdjustment2021(),
                        new DetermineNoCostProductAdjustment(),
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
                        new CalculateCapitalDisproportionateShareHospital(),
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
                                        new CalculateAndexaxaAddOnCost(),
                                        new CalculateAzedraAddOnCost(),
                                        new CalculateBalversaAddOnCost(),
                                        new CalculateBarostimAddOnCost(),
                                        new CalculateCabliviAddOnCost(),
                                        new CalculateContactAddOnCost(),
                                        new CalculateEluviaAddOnCost(),
                                        new CalculateElzonrisAddOnCost(),
                                        new CalculateFetrojaAddOnCost(),
                                        new CalculateHemosprayAddOnCost(),
                                        new CalculateImfinziAddOnCost(),
                                        new CalculateJakafiAddOnCost(),
                                        new CalculateNuzyraAddOnCost(),
                                        new CalculateOptimizerAddOnCost(),
                                        new CalculateRecarbrioAddOnCost(),
                                        new CalculateSolirisAddOnCost(),
                                        new CalculateSpinejackAddOnCost(),
                                        new CalculateSpravatoAddOnCost(),
                                        new CalculateT2BacteriaAddOnCost(),
                                        new CalculateTecentriqAddOnCost(),
                                        new CalculateXenletaAddOnCost(),
                                        new CalculateXospataAddOnCost(),
                                        new CalculateZemdriAddOnCost(),
                                        new CalculateZerbaxaAddOnCost())),
                                new CapCalculatedTechAddOn())),
                        new CalculateOutliers(
                            List.of(
                                new CalculateOperAndCapitalCostOutliers(),
                                new CalculateOutlierThresholdAmounts(),
                                new CalculateOperatingCosts(),
                                new CalculateCapitalCosts(),
                                new DetermineOutlierCosts2021())),
                        new DetermineNewCovid19TreatmentsAddOnPayment2021(),
                        new CalculateReadmissionReduction(),
                        new CalculateValueBasedPurchasingAdjustments(),
                        new CalculateBundleReduction2021(),
                        new PostProcessOutlierCosts(
                            List.of(
                                new AnalyzeOutlierCalculationResults(),
                                new CalculateAdditionalHospitalSpecificPortion2021(),
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
                        new AdjustIndexForRuralFloor2021(),
                        new AdjustForOutMigration(),
                        new AdjustIndexForQuartile2021(),
                        new AdjustIndexForPreviousYear2021())),
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

    return new Ipps2021PricerContext(input, output, dataTables);
  }
}
