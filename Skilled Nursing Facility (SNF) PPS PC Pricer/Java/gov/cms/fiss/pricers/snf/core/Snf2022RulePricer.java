package gov.cms.fiss.pricers.snf.core;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingRequest;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingResponse;
import gov.cms.fiss.pricers.snf.core.rules.ApplyValueBasedPurchasingMultiplier;
import gov.cms.fiss.pricers.snf.core.rules.CalculateTotalPaymentRate;
import gov.cms.fiss.pricers.snf.core.rules.CalculateUtilizationDays;
import gov.cms.fiss.pricers.snf.core.rules.DetermineWageIndex;
import gov.cms.fiss.pricers.snf.core.rules.FinalizeClaimOutput;
import gov.cms.fiss.pricers.snf.core.rules.SetIndicators;
import gov.cms.fiss.pricers.snf.core.rules.rate_selection.PdpmRateComponentSelection;
import gov.cms.fiss.pricers.snf.core.rules.rate_selection.SelectNonCaseMixRate;
import gov.cms.fiss.pricers.snf.core.rules.rate_selection.SelectNonTherapyAncillaryRate;
import gov.cms.fiss.pricers.snf.core.rules.rate_selection.SelectNursingRate;
import gov.cms.fiss.pricers.snf.core.rules.rate_selection.SelectPhysicalAndOccupationalTherapyRates;
import gov.cms.fiss.pricers.snf.core.rules.rate_selection.SelectSpeechLanguagePathologyRate;
import gov.cms.fiss.pricers.snf.core.rules.validation.ClaimValidation;
import gov.cms.fiss.pricers.snf.core.rules.validation.ValidateDaysInStay;
import gov.cms.fiss.pricers.snf.core.rules.validation.ValidateHippsCode;
import gov.cms.fiss.pricers.snf.core.rules.validation.ValidateServiceUnits;
import gov.cms.fiss.pricers.snf.core.rules.validation.ValidateValueBasedPurchasingMultiplier;
import gov.cms.fiss.pricers.snf.core.tables.DataTables;
import java.util.List;

/** 2022 implementation of the SNF pricer. */
public class Snf2022RulePricer extends SnfRulePricer {

  public Snf2022RulePricer(DataTables dataTables) {
    super(dataTables, rules());
  }

  /** Returns a list of rules and rule sets to be executed sequentially. */
  private static List<
          CalculationRule<SnfClaimPricingRequest, SnfClaimPricingResponse, SnfPricerContext>>
      rules() {
    return List.of(
        new DetermineWageIndex(),
        new ClaimValidation(
            List.of(
                new ValidateHippsCode(),
                new ValidateValueBasedPurchasingMultiplier(),
                new ValidateServiceUnits(),
                new ValidateDaysInStay())),
        new SetIndicators(),
        new PdpmRateComponentSelection(
            List.of(
                new SelectPhysicalAndOccupationalTherapyRates(),
                new SelectSpeechLanguagePathologyRate(),
                new SelectNursingRate(),
                new SelectNonTherapyAncillaryRate(),
                new SelectNonCaseMixRate())),
        new CalculateUtilizationDays(),
        new CalculateTotalPaymentRate(),
        new ApplyValueBasedPurchasingMultiplier(),
        new FinalizeClaimOutput());
  }

  @Override
  protected SnfPricerContext contextFor(SnfClaimPricingRequest input) {
    return new Snf2022PricerContext(input, new SnfClaimPricingResponse(), dataTables);
  }
}
