package gov.cms.fiss.pricers.irf.core;

import gov.cms.fiss.pricers.common.api.ReturnCodeData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.api.v2.IrfPaymentData;
import gov.cms.fiss.pricers.irf.core.rules.ApplySupplementalWageIndex;
import gov.cms.fiss.pricers.irf.core.rules.CalculateFederalPaymentAmount;
import gov.cms.fiss.pricers.irf.core.rules.CalculateLowIncomePaymentAmount;
import gov.cms.fiss.pricers.irf.core.rules.CalculateLowIncomePaymentPercent;
import gov.cms.fiss.pricers.irf.core.rules.CalculateTeachPaymentAmount;
import gov.cms.fiss.pricers.irf.core.rules.CalculateTeachPercent;
import gov.cms.fiss.pricers.irf.core.rules.DefaultBillReview;
import gov.cms.fiss.pricers.irf.core.rules.DefaultCmgReview;
import gov.cms.fiss.pricers.irf.core.rules.DefaultInitialization;
import gov.cms.fiss.pricers.irf.core.rules.DefaultOutlierCalculation;
import gov.cms.fiss.pricers.irf.core.rules.DefaultPaymentFinalization;
import gov.cms.fiss.pricers.irf.core.rules.DefaultResultFinalization;
import gov.cms.fiss.pricers.irf.core.rules.DefaultVariableExtraction;
import gov.cms.fiss.pricers.irf.core.rules.DefaultWageIndexRetrieval;
import gov.cms.fiss.pricers.irf.core.rules.DeterminePricedCaseMixGroupCodeAndRelativeWeight;
import gov.cms.fiss.pricers.irf.core.rules.PaymentCalculation;
import gov.cms.fiss.pricers.irf.core.rules.PaymentComponentPercent;
import gov.cms.fiss.pricers.irf.core.rules.bill_review.AdjustBlendIndicator;
import gov.cms.fiss.pricers.irf.core.rules.bill_review.AdjustBlendedRates;
import gov.cms.fiss.pricers.irf.core.rules.bill_review.CalculateDaysUsed;
import gov.cms.fiss.pricers.irf.core.rules.bill_review.InitializeNationalValues;
import gov.cms.fiss.pricers.irf.core.rules.bill_review.ReviewWaiverStatus;
import gov.cms.fiss.pricers.irf.core.rules.bill_review.ValidateCoveredDays;
import gov.cms.fiss.pricers.irf.core.rules.bill_review.ValidateDischargeDate;
import gov.cms.fiss.pricers.irf.core.rules.bill_review.ValidateLengthOfStay;
import gov.cms.fiss.pricers.irf.core.rules.bill_review.ValidateLifetimeReserveDays;
import gov.cms.fiss.pricers.irf.core.rules.bill_review.ValidateOpCostToChargeRatio;
import gov.cms.fiss.pricers.irf.core.rules.bill_review.ValidateProviderStatus;
import gov.cms.fiss.pricers.irf.core.rules.finalize_payment.CalculatePaymentAmounts;
import gov.cms.fiss.pricers.irf.core.rules.finalize_payment.CalculateTotalPayment;
import gov.cms.fiss.pricers.irf.core.rules.finalize_payment.FinalizeOutlierPayment;
import gov.cms.fiss.pricers.irf.core.rules.finalize_payment.UpdatePenaltyResultCode;
import gov.cms.fiss.pricers.irf.core.rules.rules2018.Irf2018DetermineRuralAdjustment;
import gov.cms.fiss.pricers.irf.core.rules.rules2020.Irf2020CalculateStandardPayment;
import gov.cms.fiss.pricers.irf.core.rules.rules2020.finalize_payment.Irf2020UpdateResultCode;
import gov.cms.fiss.pricers.irf.core.tables.DataTables;
import java.util.Arrays;
import java.util.List;

public class Irf2023RulePricer extends IrfRulePricer {

  public Irf2023RulePricer(DataTables dataTables) {
    super(dataTables, rules());
  }

  @Override
  protected IrfPricerContext contextFor(IrfClaimPricingRequest input) {
    final IrfClaimPricingResponse output = new IrfClaimPricingResponse();
    output.setPaymentData(new IrfPaymentData());
    output.setReturnCodeData(new ReturnCodeData());

    return new Irf2023PricerContext(input, output, dataTables);
  }

  private static List<
          CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext>>
      rules() {
    return Arrays.asList(
        new DefaultInitialization(),
        new DefaultWageIndexRetrieval(),
        new ApplySupplementalWageIndex(),
        new DefaultBillReview(
            List.of(
                new InitializeNationalValues(),
                new ValidateLengthOfStay(),
                new AdjustBlendIndicator(),
                new AdjustBlendedRates(),
                new ReviewWaiverStatus(),
                new ValidateDischargeDate(),
                new ValidateOpCostToChargeRatio(),
                new ValidateProviderStatus(),
                new ValidateLifetimeReserveDays(),
                new ValidateCoveredDays(),
                new CalculateDaysUsed())),
        new DefaultCmgReview(),
        new DefaultVariableExtraction(),
        new PaymentComponentPercent(
            List.of(
                new CalculateLowIncomePaymentPercent(),
                new CalculateTeachPercent(),
                new DeterminePricedCaseMixGroupCodeAndRelativeWeight())),
        new PaymentCalculation(
            List.of(
                new Irf2020CalculateStandardPayment(),
                new Irf2018DetermineRuralAdjustment(),
                new CalculateFederalPaymentAmount(),
                new CalculateLowIncomePaymentAmount(),
                new CalculateTeachPaymentAmount())),
        new DefaultOutlierCalculation(),
        new DefaultPaymentFinalization(
            List.of(
                new FinalizeOutlierPayment(),
                new CalculatePaymentAmounts(),
                new CalculateTotalPayment(),
                new Irf2020UpdateResultCode(),
                new UpdatePenaltyResultCode())),
        new DefaultResultFinalization());
  }
}
