package gov.cms.fiss.pricers.ipf.core.rules.assemble_pps_variables;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;
import java.math.BigDecimal;
import org.apache.commons.lang3.StringUtils;

/**
 * Set rural adjustment based on the CBSA geolocation.
 *
 * <pre>
 * ***************************************************************
 * ***  GET THE RURAL ADJUSTMENT
 * ***************************************************************
 * </pre>
 *
 * <p>Converted from {@code 2100-CHECK-RURAL-ADJ} in the COBOL code.
 */
public class CalculateRuralAdjustmentForOutput
    implements CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {

  @Override
  public void calculate(IpfPricerContext calculationContext) {
    final BigDecimal ruralAdjustment;
    final InpatientProviderData providerData = calculationContext.getProviderData();
    final String geolocation =
        calculationContext.getProviderData().getCbsaActualGeographicLocation();

    // IF P-NEW-CBSA-GEO-RURAL-CHECK
    //   MOVE 1.17 TO IPF-GEO-RURAL-ADJ (Set as rural adjustment)
    //   MOVE 1.17 TO WS-IPF-GEO-RURAL-ADJ (Java uses local variables instead of workspace)
    // ELSE IF
    //   MOVE 1.113 TO IPF-GEO-RURAL-ADJ (Set as transitional rural adjustment)
    //   MOVE 1.113 TO WS-IPF-GEO-RURAL-ADJ (Java uses local variables instead of workspace)
    // ELSE
    //   MOVE 1.00 TO IPF-GEO-RURAL-ADJ (Set as default)
    //   MOVE 1.00 TO WS-IPF-GEO-RURAL-ADJ. (Java uses local variables instead of workspace)

    if (geolocation.length() < 3 || geolocation.substring(0, geolocation.length() - 2).isBlank()) {
      ruralAdjustment = calculationContext.getRuralAdjustment();
    } else if ((geolocation.length() == 5
        && StringUtils.equals(providerData.getSupplementalWageIndexIndicator(), "3"))) {
      ruralAdjustment = calculationContext.getTransitionalRuralAdjustment();
    } else {
      ruralAdjustment = calculationContext.getDefaultRuralAdjustment();
    }

    calculationContext.getPaymentData().setRuralAdjustmentPercent(ruralAdjustment);
  }
}
