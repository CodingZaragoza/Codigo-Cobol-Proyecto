package gov.cms.fiss.pricers.esrd.core.rules;

import gov.cms.fiss.pricers.common.api.OutpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdPaymentData;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;

/** Copies default values into the output to prevent downstream errors. */
public class SetDefaultValues
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final OutpatientProviderData providerData = calculationContext.getProviderData();
    final EsrdPaymentData paymentData = calculationContext.getPaymentData();

    paymentData.setFinalCbsa(providerData.getCbsaActualGeographicLocation());
  }
}
