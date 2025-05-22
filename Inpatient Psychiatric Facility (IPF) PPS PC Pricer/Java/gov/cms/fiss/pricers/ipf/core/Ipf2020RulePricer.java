package gov.cms.fiss.pricers.ipf.core;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.api.v2.IpfPaymentData;
import gov.cms.fiss.pricers.ipf.core.codes.ReturnCode;
import gov.cms.fiss.pricers.ipf.core.rules.AssemblePpsVariables;
import gov.cms.fiss.pricers.ipf.core.rules.CalculatePayment;
import gov.cms.fiss.pricers.ipf.core.rules.PreProcessing;
import gov.cms.fiss.pricers.ipf.core.rules.ValidateBillingInfo;
import gov.cms.fiss.pricers.ipf.core.rules.assemble_pps_variables.CalculateDiagnosisComorbidity;
import gov.cms.fiss.pricers.ipf.core.rules.assemble_pps_variables.CalculateEctPaymentForOutput;
import gov.cms.fiss.pricers.ipf.core.rules.assemble_pps_variables.CalculateEmergencyAdjustmentForOutput;
import gov.cms.fiss.pricers.ipf.core.rules.assemble_pps_variables.CalculateRuralAdjustmentForOutput;
import gov.cms.fiss.pricers.ipf.core.rules.assemble_pps_variables.CalculateTeachingAdjustmentForOutput;
import gov.cms.fiss.pricers.ipf.core.rules.assemble_pps_variables.CopyContextValuesToOutput;
import gov.cms.fiss.pricers.ipf.core.rules.assemble_pps_variables.GetDiagnosticRelatedGroupFactors;
import gov.cms.fiss.pricers.ipf.core.rules.assemble_pps_variables.GetFirstCodes;
import gov.cms.fiss.pricers.ipf.core.rules.assemble_pps_variables.SetAgeAdjustmentInOutput;
import gov.cms.fiss.pricers.ipf.core.rules.assemble_pps_variables.SetCostOfLivingAdjustment;
import gov.cms.fiss.pricers.ipf.core.rules.assemble_pps_variables.SetHospitalQualityRates;
import gov.cms.fiss.pricers.ipf.core.rules.calculate_payment.CalculateFederalPayment;
import gov.cms.fiss.pricers.ipf.core.rules.calculate_payment.CalculateOutlier;
import gov.cms.fiss.pricers.ipf.core.rules.calculate_payment.CalculatePerDiemBasis;
import gov.cms.fiss.pricers.ipf.core.rules.calculate_payment.CalculatePerDiemOverStayForContext;
import gov.cms.fiss.pricers.ipf.core.rules.calculate_payment.CalculateTeachingAdjustedPerDiemBasis;
import gov.cms.fiss.pricers.ipf.core.rules.calculate_payment.CalculateTeachingPerDiemPortion;
import gov.cms.fiss.pricers.ipf.core.rules.calculate_payment.CalculateWageAdjustedRates;
import gov.cms.fiss.pricers.ipf.core.rules.calculate_payment.FinalizePayment;
import gov.cms.fiss.pricers.ipf.core.rules.calculate_payment.SetFederalPortionInContext;
import gov.cms.fiss.pricers.ipf.core.rules.calculate_payment.SetTeachingPortionInContext;
import gov.cms.fiss.pricers.ipf.core.rules.calculate_payment.calculate_outlier.CalculateOutlierPayment;
import gov.cms.fiss.pricers.ipf.core.rules.calculate_payment.calculate_outlier.CalculateOutlierPerDiem;
import gov.cms.fiss.pricers.ipf.core.rules.pre_processing.SetCbsaCode;
import gov.cms.fiss.pricers.ipf.core.rules.pre_processing.SetCbsaWageIndexInContext;
import gov.cms.fiss.pricers.ipf.core.rules.pre_processing.SetSpecialWageIndexInContext;
import gov.cms.fiss.pricers.ipf.core.rules.pre_processing.ValidateDischargeDate;
import gov.cms.fiss.pricers.ipf.core.rules.validate_billing_info.ValidateAge;
import gov.cms.fiss.pricers.ipf.core.rules.validate_billing_info.ValidateDiagnosticRelatedGroup;
import gov.cms.fiss.pricers.ipf.core.rules.validate_billing_info.ValidateLengthOfStay;
import gov.cms.fiss.pricers.ipf.core.rules.validate_billing_info.ValidateWaiverState;
import gov.cms.fiss.pricers.ipf.core.tables.DataTables;
import java.util.List;

public class Ipf2020RulePricer extends IpfRulePricer {

  private static final List<
          CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext>>
      YEAR_RULES = rules();

  public Ipf2020RulePricer(DataTables dataTables) {
    super(dataTables, YEAR_RULES);
  }

  private static List<
          CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext>>
      rules() {
    return List.of(
        new PreProcessing(
            List.of(
                new ValidateDischargeDate(),
                new SetCbsaCode(),
                new SetSpecialWageIndexInContext(),
                new SetCbsaWageIndexInContext())),
        new ValidateBillingInfo(
            List.of(
                new ValidateWaiverState(),
                new ValidateDiagnosticRelatedGroup(),
                new ValidateLengthOfStay(),
                new ValidateAge())),
        new AssemblePpsVariables(
            List.of(
                new CopyContextValuesToOutput(),
                new SetHospitalQualityRates(),
                new SetCostOfLivingAdjustment(),
                new GetDiagnosticRelatedGroupFactors(),
                new GetFirstCodes(),
                new CalculateDiagnosisComorbidity(),
                new SetAgeAdjustmentInOutput(),
                new CalculateTeachingAdjustmentForOutput(),
                new CalculateRuralAdjustmentForOutput(),
                new CalculateEmergencyAdjustmentForOutput(),
                new CalculateEctPaymentForOutput())),
        new CalculatePayment(
            List.of(
                new CalculateWageAdjustedRates(),
                new CalculatePerDiemBasis(),
                new CalculatePerDiemOverStayForContext(),
                new SetFederalPortionInContext(),
                new CalculateTeachingPerDiemPortion(
                    List.of(
                        new CalculateTeachingAdjustedPerDiemBasis(),
                        new CalculatePerDiemOverStayForContext(),
                        new SetTeachingPortionInContext())),
                new CalculateFederalPayment(),
                new CalculateOutlier(
                    List.of(new CalculateOutlierPerDiem(), new CalculateOutlierPayment())),
                new FinalizePayment())));
  }

  @Override
  protected IpfPricerContext contextFor(IpfClaimPricingRequest request) {
    final IpfClaimPricingResponse response = new IpfClaimPricingResponse();
    response.setPaymentData(new IpfPaymentData());
    response.setReturnCodeData(ReturnCode.NORMAL_PAYMENT_0.toReturnCodeData());
    return new Ipf2020PricerContext(request, response, dataTables);
  }
}
