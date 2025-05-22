package gov.cms.fiss.pricers.hha.core;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingRequest;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingResponse;
import gov.cms.fiss.pricers.hha.api.v2.HhaPaymentData;
import gov.cms.fiss.pricers.hha.core.codes.ReturnCode;
import gov.cms.fiss.pricers.hha.core.rules.AdjustRevenueDollarRate;
import gov.cms.fiss.pricers.hha.core.rules.ApplyRuralAddOn;
import gov.cms.fiss.pricers.hha.core.rules.CalculateFinalPayment;
import gov.cms.fiss.pricers.hha.core.rules.CalculateStandardValue;
import gov.cms.fiss.pricers.hha.core.rules.CalculateValueBasedPurchasing;
import gov.cms.fiss.pricers.hha.core.rules.InitializeCalculationVersion;
import gov.cms.fiss.pricers.hha.core.rules.InitializeValues;
import gov.cms.fiss.pricers.hha.core.rules.LateSubmissionPenalty;
import gov.cms.fiss.pricers.hha.core.rules.ValidateInput;
import gov.cms.fiss.pricers.hha.core.rules.calculate_value_based_purchasing.AddRevenueData;
import gov.cms.fiss.pricers.hha.core.rules.calculate_value_based_purchasing.CalculateHhrgPayment;
import gov.cms.fiss.pricers.hha.core.rules.calculate_value_based_purchasing.FinalizeTotalPayment;
import gov.cms.fiss.pricers.hha.core.rules.calculate_value_based_purchasing.InitializePaymentTotals;
import gov.cms.fiss.pricers.hha.core.rules.final_calculation.AdjustLupaPayment;
import gov.cms.fiss.pricers.hha.core.rules.final_calculation.CalculateAddOnVisit;
import gov.cms.fiss.pricers.hha.core.rules.final_calculation.CalculateLupaPayment;
import gov.cms.fiss.pricers.hha.core.rules.final_calculation.CalculatePartialEpisodePayment;
import gov.cms.fiss.pricers.hha.core.rules.final_calculation.CheckLupaPaymentValues;
import gov.cms.fiss.pricers.hha.core.rules.validate_input.CalculateTotalQuantityOfCoveredVisits;
import gov.cms.fiss.pricers.hha.core.rules.validate_input.CheckInputCode;
import gov.cms.fiss.pricers.hha.core.rules.validate_input.CheckRevenueCode;
import gov.cms.fiss.pricers.hha.core.rules.validate_input.SetCbsaWageIndex;
import gov.cms.fiss.pricers.hha.core.rules.validate_input.ValidateAdmissionDate;
import gov.cms.fiss.pricers.hha.core.rules.validate_input.ValidateCountyCode;
import gov.cms.fiss.pricers.hha.core.rules.validate_input.ValidateExtraCountyCode;
import gov.cms.fiss.pricers.hha.core.rules.validate_input.ValidateInputCode;
import gov.cms.fiss.pricers.hha.core.rules.validate_input.ValidateNotRap;
import gov.cms.fiss.pricers.hha.core.rules.validate_input.ValidatePartialEpisodePayment;
import gov.cms.fiss.pricers.hha.core.rules.validate_input.ValidateRevenueCode;
import gov.cms.fiss.pricers.hha.core.rules.validate_input.ValidateTypeOfBill;
import gov.cms.fiss.pricers.hha.core.tables.DataTables;
import java.util.List;

public class Hha2021RulePricer extends HhaRulePricer {

  public Hha2021RulePricer(DataTables dataTables) {
    super(dataTables, rules());
  }

  /** Returns a list of rules and rule sets to be executed sequentially. */
  private static List<
          CalculationRule<HhaClaimPricingRequest, HhaClaimPricingResponse, HhaPricerContext>>
      rules() {
    return List.of(
        new ValidateInput(
            List.of(
                new InitializeCalculationVersion(),
                new ValidateTypeOfBill(),
                new ValidateAdmissionDate(),
                new CalculateTotalQuantityOfCoveredVisits(),
                new ValidateInputCode(),
                new ValidateRevenueCode(),
                new SetCbsaWageIndex(),
                new CheckInputCode(),
                new CheckRevenueCode(),
                new ValidateNotRap(),
                new ValidateCountyCode(),
                new ValidateExtraCountyCode(),
                new ValidatePartialEpisodePayment())),
        new InitializeValues(),
        new ApplyRuralAddOn(),
        new AdjustRevenueDollarRate(),
        new CalculateFinalPayment(
            List.of(
                new CalculatePartialEpisodePayment(),
                new CalculateAddOnVisit(),
                new CheckLupaPaymentValues(),
                new CalculateLupaPayment(),
                new AdjustLupaPayment())),
        new LateSubmissionPenalty(),
        new CalculateValueBasedPurchasing(
            List.of(
                new InitializePaymentTotals(),
                new CalculateHhrgPayment(),
                new AddRevenueData(),
                new FinalizeTotalPayment())),
        new CalculateStandardValue());
  }

  @Override
  protected HhaPricerContext contextFor(HhaClaimPricingRequest input) {
    final HhaClaimPricingResponse output = new HhaClaimPricingResponse();
    output.setPaymentData(new HhaPaymentData());
    output.getPaymentData().setTotalPayment(BigDecimalUtils.ZERO);
    output.setReturnCodeData(ReturnCode.PAYMENT_WITHOUT_OUTLIER_0.toReturnCodeData());

    return new Hha2021PricerContext(input, output, dataTables);
  }
}
