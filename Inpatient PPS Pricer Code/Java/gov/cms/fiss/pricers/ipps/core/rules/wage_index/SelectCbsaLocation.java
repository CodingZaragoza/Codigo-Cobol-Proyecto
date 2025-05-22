package gov.cms.fiss.pricers.ipps.core.rules.wage_index;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import org.apache.commons.lang3.StringUtils;

/**
 * Selects the CBSA location to use.
 *
 * <p>Converted from {@code 0550-GET-CBSA} in the COBOL code (continued).
 *
 * @since 2019
 */
public class SelectCbsaLocation
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {
  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();

    // **----------------------------------------------------------------
    // ** HOLD THE PROVIDER'S CBSA FROM PSF
    // ** (EQUIVALENT TO GEO LOCATION CBSA IF NO STAND AMT LOC CBSA)
    // **----------------------------------------------------------------
    //     MOVE P-NEW-CBSA-STAND-AMT-LOC TO HOLD-PROV-CBSA.
    calculationContext.setCbsaLocation(
        StringUtils.trim(providerData.getCbsaStandardizedAmountLocation()));

    // **----------------------------------------------------------------
    // ** HOLD THE PROVIDER'S RECLASS CBSA IF RECLASS STATUS INDICATED
    // **----------------------------------------------------------------
    //     IF P-NEW-CBSA-WI-RECLASS OR P-NEW-CBSA-WI-DUAL
    //        MOVE P-NEW-CBSA-RECLASS-LOC TO HOLD-PROV-CBSA.
    if (calculationContext.isCbsaSpecialPaymentIndicatorReclassified()
        || calculationContext.isCbsaSpecialPaymentIndicatorDual()) {
      calculationContext.setCbsaLocation(StringUtils.trim(providerData.getCbsaWageIndexLocation()));
    }
  }
}
