package gov.cms.fiss.pricers.ipps.core.rules.rules2022.calculate_payment;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.rules.rules2021.calculate_payment.CalculateAdditionalHospitalSpecificPortion2021;
import java.math.BigDecimal;
import org.apache.commons.lang3.StringUtils;

/**
 * Calculate the additional hospital specific portion amount.
 *
 * <p>Converted from {@code 3450-CALC-ADDITIONAL-HSP} in the COBOL code.
 *
 * @since 2019
 */
public class CalculateAdditionalHospitalSpecificPortion2022
    extends CalculateAdditionalHospitalSpecificPortion2021 {

  @Override
  protected BigDecimal calculateCurrentYearUpdateFactor(IppsPricerContext calculationContext) {
    BigDecimal currentYearUpdateFactor = super.calculateCurrentYearUpdateFactor(calculationContext);

    final InpatientProviderData providerData = calculationContext.getProviderData();

    // Puerto Rico provider with no EHR reduction (FY 2022.0)
    if (StringUtils.equals(providerData.getStateCode(), "40")
        && StringUtils.isBlank(providerData.getEhrReductionIndicator())) {
      currentYearUpdateFactor =
          calculationContext.getHospitalSpecificPortionUpdateFactorWithNoEhrReductionPuertoRico();
    }

    // Puerto Rico provider with EHR reduction (FY 2022.0)
    if (StringUtils.equals(providerData.getStateCode(), "40")
        && StringUtils.equals(providerData.getEhrReductionIndicator(), "Y")) {
      currentYearUpdateFactor =
          calculationContext.getHospitalSpecificPortionUpdateFactorWithEhrReductionPuertoRico();
    }

    return currentYearUpdateFactor;
  }
}
