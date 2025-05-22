package gov.cms.fiss.pricers.ltch.core;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.api.v2.LtchPaymentData;
import gov.cms.fiss.pricers.ltch.core.rules.AdjustCapitalGeographicAreaFactor;
import gov.cms.fiss.pricers.ltch.core.rules.AssemblePpsVariables;
import gov.cms.fiss.pricers.ltch.core.rules.AssignDates;
import gov.cms.fiss.pricers.ltch.core.rules.AssignFloorCBSA;
import gov.cms.fiss.pricers.ltch.core.rules.CalcFinalPayment;
import gov.cms.fiss.pricers.ltch.core.rules.CalculateCapitalDshAdjustment;
import gov.cms.fiss.pricers.ltch.core.rules.CalculateCapitalRate;
import gov.cms.fiss.pricers.ltch.core.rules.CalculateChargeThreshold;
import gov.cms.fiss.pricers.ltch.core.rules.CalculateHighCostOutlier;
import gov.cms.fiss.pricers.ltch.core.rules.CalculateHighCostOutlierBlend;
import gov.cms.fiss.pricers.ltch.core.rules.CalculateHoldValues;
import gov.cms.fiss.pricers.ltch.core.rules.CalculateIppsComparablePayment;
import gov.cms.fiss.pricers.ltch.core.rules.CalculateOperatingDshAmount;
import gov.cms.fiss.pricers.ltch.core.rules.CalculatePerDiem;
import gov.cms.fiss.pricers.ltch.core.rules.CalculateShortStay;
import gov.cms.fiss.pricers.ltch.core.rules.CalculateShortStayBlendedPayment;
import gov.cms.fiss.pricers.ltch.core.rules.CalculateSiteNeutralPayment;
import gov.cms.fiss.pricers.ltch.core.rules.CalculateStandardPayment;
import gov.cms.fiss.pricers.ltch.core.rules.CapLtchWageIndexDecrease;
import gov.cms.fiss.pricers.ltch.core.rules.CleanupPaymentSetting;
import gov.cms.fiss.pricers.ltch.core.rules.CleanupShortStay;
import gov.cms.fiss.pricers.ltch.core.rules.CleanupShortStayBlendedPayment;
import gov.cms.fiss.pricers.ltch.core.rules.CleanupStandardPayment;
import gov.cms.fiss.pricers.ltch.core.rules.CreateNewProspectivePaymentRecord;
import gov.cms.fiss.pricers.ltch.core.rules.DetermineLtchWageIndex;
import gov.cms.fiss.pricers.ltch.core.rules.DeterminePaymentType;
import gov.cms.fiss.pricers.ltch.core.rules.EditDrgCode;
import gov.cms.fiss.pricers.ltch.core.rules.EditInputData;
import gov.cms.fiss.pricers.ltch.core.rules.EditIppsDrgCode;
import gov.cms.fiss.pricers.ltch.core.rules.FinalizeClaim;
import gov.cms.fiss.pricers.ltch.core.rules.LookupFloorCBSA;
import gov.cms.fiss.pricers.ltch.core.rules.RecalculateLaborSharesForSupplementWageIndex;
import gov.cms.fiss.pricers.ltch.core.rules.SetErrorPaymentData;
import gov.cms.fiss.pricers.ltch.core.rules.SetFinalReturnCodes;
import gov.cms.fiss.pricers.ltch.core.rules.SetNewReturnCodes;
import gov.cms.fiss.pricers.ltch.core.rules.SetOldReturnCodes;
import gov.cms.fiss.pricers.ltch.core.rules.SetSiteNeutralCodes;
import gov.cms.fiss.pricers.ltch.core.rules.SkipOnErrorCode;
import gov.cms.fiss.pricers.ltch.core.rules.SupplementalWageIndexEdit;
import gov.cms.fiss.pricers.ltch.core.rules.SupplementalWageIndexToIppsWageIndex;
import gov.cms.fiss.pricers.ltch.core.rules.rules2021.DetermineIppsWageIndex2021;
import gov.cms.fiss.pricers.ltch.core.tables.DataTables;
import java.util.List;

public class Ltch2021RulePricer extends LtchRulePricer {

  private static final List<
          CalculationRule<LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext>>
      RULES = rules();

  public Ltch2021RulePricer(DataTables dataTables) {
    super(dataTables, RULES);
  }

  private static List<
          CalculationRule<LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext>>
      rules() {
    return List.of(
        new AssignDates(),
        new CreateNewProspectivePaymentRecord(),
        new DetermineLtchWageIndex(),
        new DetermineIppsWageIndex2021(),
        new SkipOnErrorCode(
            List.of(
                new EditInputData(),
                new EditDrgCode(),
                new EditIppsDrgCode(),
                new LookupFloorCBSA())),
        new AssignFloorCBSA(),
        new SkipOnErrorCode(new AssemblePpsVariables()),
        new SupplementalWageIndexEdit(),
        new CapLtchWageIndexDecrease(),
        new SupplementalWageIndexToIppsWageIndex(),
        new RecalculateLaborSharesForSupplementWageIndex(),
        new AdjustCapitalGeographicAreaFactor(),
        new SkipOnErrorCode(
            List.of(
                new DeterminePaymentType(),
                new CalculateStandardPayment(),
                new CalculateShortStay(),
                new CalculateShortStayBlendedPayment())),
        new CalculateIppsComparablePayment(
            List.of(
                new CalculateHoldValues(),
                new CalculateOperatingDshAmount(),
                new CalculateCapitalDshAdjustment(),
                new CalculateCapitalRate(),
                new CalculatePerDiem())),
        new SkipOnErrorCode(
            List.of(
                new CleanupShortStayBlendedPayment(),
                new CleanupShortStay(),
                new CleanupPaymentSetting(),
                new CleanupStandardPayment())),
        new CalculateIppsComparablePayment(
            List.of(
                new CalculateHoldValues(),
                new CalculateOperatingDshAmount(),
                new CalculateCapitalDshAdjustment(),
                new CalculateCapitalRate(),
                new CalculatePerDiem())),
        new SetSiteNeutralCodes(),
        new CalculateHighCostOutlier(),
        new CalculateHighCostOutlierBlend(),
        new CalculateChargeThreshold(),
        new SkipOnErrorCode(new CalculateSiteNeutralPayment()),
        new CalculateHighCostOutlier(),
        new CalculateHighCostOutlierBlend(),
        new CalculateChargeThreshold(),
        new SetFinalReturnCodes(List.of(new SetOldReturnCodes(), new SetNewReturnCodes())),
        new CalcFinalPayment(),
        new SetErrorPaymentData(),
        new FinalizeClaim());
  }

  @Override
  protected LtchPricerContext contextFor(LtchClaimPricingRequest input) {
    final LtchClaimPricingResponse output = new LtchClaimPricingResponse();
    output.setPaymentData(new LtchPaymentData());
    return new Ltch2021PricerContext(input, output, dataTables);
  }
}
