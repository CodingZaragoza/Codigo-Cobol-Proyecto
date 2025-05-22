package gov.cms.fiss.pricers.ipf.core.rules.pre_processing;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;

/**
 * Set Special Wage Index, if applicable.
 *
 * <p>Converted from {@code 0550-GET-CBSA} in the COBOL code.
 */
public class SetCbsaCode
    implements CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {

  @Override
  public void calculate(IpfPricerContext calculationContext) {
    // MOVE P-NEW-CBSA-GEO-LOC TO HOLD-PROV-CBSA
    //                                 IPF-CBSA.
    calculationContext
        .getPaymentData()
        .setFinalCbsa(calculationContext.getProviderData().getCbsaActualGeographicLocation());
  }
}
