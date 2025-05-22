package gov.cms.fiss.pricers.ltch.core;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.api.v2.LtchPaymentData;
import gov.cms.fiss.pricers.ltch.core.rules.*;
import gov.cms.fiss.pricers.ltch.core.rules.RecalculateLaborSharesForSupplementWageIndex;
import gov.cms.fiss.pricers.ltch.core.rules.rules2023.DetermineIppsWageIndex2023;
import gov.cms.fiss.pricers.ltch.core.tables.DataTables;
import java.util.List;

public class Ltch2023RulePricer extends LtchRulePricer {

  private static final List<
          CalculationRule<LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext>>
      RULES = rules();

  public Ltch2023RulePricer(DataTables dataTables) {
    super(dataTables, RULES);
  }

  private static List<
          CalculationRule<LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext>>
      rules() {
    return List.of(
        new AssignDates(),
        new CreateNewProspectivePaymentRecord(),
        new DetermineLtchWageIndex(),
        new DetermineIppsWageIndex2023(),
        new SkipOnErrorCode(
            List.of(
                new EditInputData(),
                new EditDrgCode(),
                new EditIppsDrgCode(),
                new LookupFloorCBSA())),
        new AssignFloorCBSA(),
        new SkipOnErrorCode(new AssemblePpsVariables()),
        new SupplementalWageIndexEdit(),
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
    return new Ltch2023PricerContext(input, output, dataTables);
  }
}
