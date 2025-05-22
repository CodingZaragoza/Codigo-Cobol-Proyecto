package gov.cms.fiss.pricers.ipps.core.rules.rules2024.calculate_payment;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.CbsaReference;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.commons.lang3.StringUtils;

/**
 * Determines the capital disproportionate share hospital amount.
 *
 * <p>Converted from {@code 3000-CALC-PAYMENT} in the COBOL code (continued).
 *
 * @since 2019
 */
public class CalculateCapitalDisproportionateShareHospital2024
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final CbsaReference cbsaReference = calculationContext.getCbsaReference();
    final InpatientProviderData providerData = calculationContext.getProviderData();

    // ***********************************************************
    // ***  CAPITAL DSH CALCULATION
    //     MOVE 0 TO H-CAPI-DSH.
    //     IF P-NEW-BED-SIZE NOT NUMERIC
    //         MOVE 0 TO P-NEW-BED-SIZE.
    //     IF (W-CBSA-SIZE = 'O' OR 'L') AND P-NEW-BED-SIZE > 99
    //         COMPUTE H-CAPI-DSH ROUNDED = 2.7183 **
    //                  (.2025 * (P-NEW-SSI-RATIO
    //                          + P-NEW-MEDICAID-RATIO)) - 1.
    if ((StringUtils.equalsAny(cbsaReference.getSize(), "O", "L")
            || ((StringUtils.trim(providerData.getCbsaActualGeographicLocation()).length()) > 2))
        && providerData.getBedSize() > 99) {
      calculationContext.setCapitalDisproportionateShareHospital(
          BigDecimalUtils.pow(
                  new BigDecimal("2.7183"),
                  new BigDecimal("0.2025")
                      .multiply(
                          providerData
                              .getSupplementalSecurityIncomeRatio()
                              .add(providerData.getMedicaidRatio())),
                  5)
              .subtract(BigDecimal.ONE)
              .setScale(4, RoundingMode.HALF_UP));
    }
  }
}
