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
import gov.cms.fiss.pricers.ipf.core.rules.assemble_pps_variables.*;
import gov.cms.fiss.pricers.ipf.core.rules.calculate_payment.*;
import gov.cms.fiss.pricers.ipf.core.rules.calculate_payment.calculate_outlier.CalculateOutlierPerDiem;
import gov.cms.fiss.pricers.ipf.core.rules.pre_processing.*;
import gov.cms.fiss.pricers.ipf.core.rules.rules2022.calculate_payment.calculate_outlier.CalculateOutlierPayment2022;
import gov.cms.fiss.pricers.ipf.core.rules.validate_billing_info.ValidateAge;
import gov.cms.fiss.pricers.ipf.core.rules.validate_billing_info.ValidateDiagnosticRelatedGroup;
import gov.cms.fiss.pricers.ipf.core.rules.validate_billing_info.ValidateLengthOfStay;
import gov.cms.fiss.pricers.ipf.core.rules.validate_billing_info.ValidateWaiverState;
import gov.cms.fiss.pricers.ipf.core.tables.DataTables;
import java.util.List;

public class Ipf2025RulePricer extends IpfRulePricer {

  private static final List<
          CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext>>
      YEAR_RULES = rules();

  public Ipf2025RulePricer(DataTables dataTables) {
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
                new ValidateSupplementalWageIndex(),
                new SetCbsaWageIndexInContext(),
                new SetWageIndexCap())),
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
                new CalculateProcedureComorbidity(),
                new SetAgeAdjustmentInOutput(),
                new CalculateTeachingAdjustmentForOutput(),
                new CalculateRuralAdjustmentForOutput(),
                new CalculateEmergencyAdjustmentForOutput(),
                new CalculateEctPaymentForOutput())),
        new CalculatePayment(
            List.of(
                new CalculateWageAdjustedRates(),
                new CalculatePerDiemBasis(),
                new CalculatePerDiemOverStayForContext2025(),
                new SetFederalPortionInContext(),
                new CalculateTeachingPerDiemPortion(
                    List.of(
                        new CalculateTeachingAdjustedPerDiemBasis(),
                        new CalculatePerDiemOverStayForContext2025(),
                        new SetTeachingPortionInContext())),
                new CalculateFederalPayment(),
                new CalculateOutlier(
                    List.of(new CalculateOutlierPerDiem(), new CalculateOutlierPayment2022())),
                new FinalizePayment())));
  }

  @Override
  protected IpfPricerContext contextFor(IpfClaimPricingRequest request) {
    final IpfClaimPricingResponse response = new IpfClaimPricingResponse();
    response.setPaymentData(new IpfPaymentData());
    response.setReturnCodeData(ReturnCode.NORMAL_PAYMENT_0.toReturnCodeData());
    return new Ipf2025PricerContext(request, response, dataTables);
  }
}
