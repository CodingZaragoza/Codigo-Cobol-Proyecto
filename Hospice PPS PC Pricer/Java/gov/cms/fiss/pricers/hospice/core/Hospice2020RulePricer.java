package gov.cms.fiss.pricers.hospice.core;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingRequest;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingResponse;
import gov.cms.fiss.pricers.hospice.api.v2.HospicePaymentData;
import gov.cms.fiss.pricers.hospice.core.rules.CalculateContinuousHomeCarePayment;
import gov.cms.fiss.pricers.hospice.core.rules.CalculateFinalPayments;
import gov.cms.fiss.pricers.hospice.core.rules.CalculateGeneralInpatientCarePayment;
import gov.cms.fiss.pricers.hospice.core.rules.CalculateInpatientRespiteCarePayment;
import gov.cms.fiss.pricers.hospice.core.rules.CalculateRoutineHomeCarePayment;
import gov.cms.fiss.pricers.hospice.core.rules.RetrieveWageIndexes;
import gov.cms.fiss.pricers.hospice.core.rules.ValidateBillUnits;
import gov.cms.fiss.pricers.hospice.core.rules.continuous_home_care.ApplyContinuousHomeCareRateWithQualityReduction;
import gov.cms.fiss.pricers.hospice.core.rules.continuous_home_care.ApplyContinuousHomeCareRateWithoutQualityReduction;
import gov.cms.fiss.pricers.hospice.core.rules.continuous_home_care.CalculateContinuousHomeCarePriorSvcDays;
import gov.cms.fiss.pricers.hospice.core.rules.routine_home_care.CalculateEndOfLifeServiceIntensityAddOn;
import gov.cms.fiss.pricers.hospice.core.rules.routine_home_care.CalculateRoutineHomeCarePriorSvcDays;
import gov.cms.fiss.pricers.hospice.core.rules.routine_home_care.EvaluateRoutineHomeCareDays;
import gov.cms.fiss.pricers.hospice.core.rules.routine_home_care.SumRoutineHomeCareRate;
import gov.cms.fiss.pricers.hospice.core.tables.DataTables;
import java.util.List;

public class Hospice2020RulePricer extends HospiceRulePricer {
  private static final List<
          CalculationRule<
              HospiceClaimPricingRequest, HospiceClaimPricingResponse, HospicePricerContext>>
      RULES = rules();

  public Hospice2020RulePricer(DataTables dataTables) {
    super(RULES, dataTables);
  }

  private static List<
          CalculationRule<
              HospiceClaimPricingRequest, HospiceClaimPricingResponse, HospicePricerContext>>
      rules() {
    return List.of(
        new RetrieveWageIndexes(),
        new ValidateBillUnits(),
        new CalculateRoutineHomeCarePayment(
            List.of(
                new CalculateRoutineHomeCarePriorSvcDays(),
                new EvaluateRoutineHomeCareDays(),
                new CalculateEndOfLifeServiceIntensityAddOn(),
                new SumRoutineHomeCareRate())),
        new CalculateContinuousHomeCarePayment(
            List.of(
                new CalculateContinuousHomeCarePriorSvcDays(),
                new ApplyContinuousHomeCareRateWithQualityReduction(),
                new ApplyContinuousHomeCareRateWithoutQualityReduction())),
        new CalculateInpatientRespiteCarePayment(),
        new CalculateGeneralInpatientCarePayment(),
        new CalculateFinalPayments());
  }

  @Override
  protected HospicePricerContext contextFor(HospiceClaimPricingRequest input) {
    final HospiceClaimPricingResponse hospiceOutput = new HospiceClaimPricingResponse();
    hospiceOutput.setPaymentData(new HospicePaymentData());
    return new Hospice2020PricerContext(input, hospiceOutput, dataTables);
  }
}
