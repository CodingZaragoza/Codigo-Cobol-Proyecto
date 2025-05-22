package gov.cms.fiss.pricers.hha.core;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingRequest;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingResponse;
import gov.cms.fiss.pricers.hha.api.v2.HhaPaymentData;
import gov.cms.fiss.pricers.hha.core.codes.ReturnCode;
import gov.cms.fiss.pricers.hha.core.rules.*;
import gov.cms.fiss.pricers.hha.core.rules.calculate_value_based_purchasing.AddRevenueData;
import gov.cms.fiss.pricers.hha.core.rules.calculate_value_based_purchasing.CalculateHhrgPayment;
import gov.cms.fiss.pricers.hha.core.rules.calculate_value_based_purchasing.FinalizeTotalPayment;
import gov.cms.fiss.pricers.hha.core.rules.calculate_value_based_purchasing.InitializePaymentTotals;
import gov.cms.fiss.pricers.hha.core.rules.final_calculation.*;
import gov.cms.fiss.pricers.hha.core.rules.validate_input.*;
import gov.cms.fiss.pricers.hha.core.tables.DataTables;
import java.util.List;

public class Hha2025RulePricer extends HhaRulePricer {

  public Hha2025RulePricer(DataTables dataTables) {
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
                new ValidatePartialEpisodePayment())),
        new InitializeValues(),
        new ApplyRuralAddOn(),
        new AdjustRevenueDollarRate(),
        new CalculateFinalPayment(
            List.of(
                new CalculatePartialEpisodePayment(),
                new CalculateAddOnVisit2022(),
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

    return new Hha2025PricerContext(input, output, dataTables);
  }
}
