package gov.cms.fiss.pricers.ipf.core.rules.assemble_pps_variables;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipf.api.v2.AdditionalVariableData;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.api.v2.IpfPaymentData;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;
import java.math.BigDecimal;

public class CopyContextValuesToOutput
    implements CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {

  @Override
  public void calculate(IpfPricerContext calculationContext) {
    final AdditionalVariableData additionalVariables = calculationContext.getAdditionalVariables();
    final IpfPaymentData paymentData = calculationContext.getPaymentData();
    additionalVariables.setOutlierBaseLaborAmount(BigDecimal.ZERO);
    additionalVariables.setOutlierBaseNonLaborAmount(BigDecimal.ZERO);
    additionalVariables.setOutlierCost(BigDecimal.ZERO);
    additionalVariables.setOutlierPayment(BigDecimal.ZERO);
    additionalVariables.setOutlierAdjustedCost(BigDecimal.ZERO);
    additionalVariables.setOutlierPerDiemAmount(BigDecimal.ZERO);
    additionalVariables.setOutlierThresholdAdjustedAmount(BigDecimal.ZERO);
    additionalVariables.setOutlierThresholdAmount(calculationContext.getOutlierThreshold());
    paymentData.setNationalLaborSharePercent(calculationContext.getLaborShare());
    paymentData.setNationalNonLaborSharePercent(calculationContext.getNonLaborShare());
    // MOVE W-CBSA-WAGE-INDEX TO IPF-WAGE-INDEX.
    paymentData.setFinalWageIndex(
        calculationContext.getCbsaWageIndexEntry().getGeographicWageIndex());
  }
}
