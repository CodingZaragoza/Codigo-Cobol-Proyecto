package gov.cms.fiss.pricers.ipps.core.rules.wage_index;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import org.apache.commons.lang3.StringUtils;

/**
 * Ensures the CBSA locations are populated with valid content.
 *
 * <p>Converted from {@code 0030-GET-WAGE-INDEX} in the COBOL code (continued).
 *
 * @since 2019
 */
public class PopulateCbsaLocations
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();

    //     IF P-NEW-EFF-DATE > 20040930
    //        IF (P-NEW-CBSA-RECLASS-LOC = '     ' OR
    //            P-NEW-CBSA-RECLASS-LOC = '00000')
    //            MOVE P-NEW-CBSA-GEO-LOC9 TO P-NEW-CBSA-RECLASS-LOC.
    if (StringUtils.isBlank(providerData.getCbsaWageIndexLocation())
        || providerData.getCbsaWageIndexLocation().equals(IppsPricerContext.ZEROS)) {
      providerData.setCbsaWageIndexLocation(
          StringUtils.trim(providerData.getCbsaActualGeographicLocation()));
    }

    //     IF P-NEW-EFF-DATE > 20040930
    //        IF (P-NEW-CBSA-STAND-AMT-LOC = '     ' OR
    //            P-NEW-CBSA-STAND-AMT-LOC = '00000')
    //            MOVE P-NEW-CBSA-GEO-LOC9 TO P-NEW-CBSA-STAND-AMT-LOC.
    if (StringUtils.isBlank(providerData.getCbsaStandardizedAmountLocation())
        || providerData.getCbsaStandardizedAmountLocation().equals(IppsPricerContext.ZEROS)) {
      providerData.setCbsaStandardizedAmountLocation(
          StringUtils.trim(providerData.getCbsaActualGeographicLocation()));
    }
  }
}
