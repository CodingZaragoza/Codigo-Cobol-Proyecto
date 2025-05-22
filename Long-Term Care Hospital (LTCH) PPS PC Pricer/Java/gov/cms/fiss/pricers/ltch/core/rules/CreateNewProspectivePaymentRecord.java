package gov.cms.fiss.pricers.ltch.core.rules;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.api.v2.LtchPaymentData;
import gov.cms.fiss.pricers.ltch.core.LtchPricerContext;

public class CreateNewProspectivePaymentRecord
    implements CalculationRule<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {

  @Override
  public void calculate(LtchPricerContext calculationContext) {
    final LtchPaymentData paymentData = new LtchPaymentData();
    final InpatientProviderData providerData = calculationContext.getProviderData();

    if (providerData.getCbsaActualGeographicLocation() != null) {
      paymentData.setFinalCbsa(providerData.getCbsaActualGeographicLocation().trim());
    }

    paymentData.setNationalLaborPercent(calculationContext.getNationalLaborPercent());
    paymentData.setNationalNonLaborPercent(calculationContext.getNationalNonLaborPercent());
    paymentData.setFederalRatePercent(
        "1".equals(providerData.getHospitalQualityIndicator())
            ? calculationContext.getStandardQualityFedRate()
            : calculationContext.getStandardFedRate());
    calculationContext.setPaymentData(paymentData);
  }
}
