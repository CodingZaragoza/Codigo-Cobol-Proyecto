package gov.cms.fiss.pricers.ipps.core.rules.rules2022.cost_factor_determination.ratex;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.rules.cost_factor_determination.ratex.DetermineRateTableWithoutReduction;
import org.apache.commons.lang3.StringUtils;

/**
 * Determines the rate table to utilize for a claim when there is no EHR reduction.
 *
 * <p>Converted from {@code 2050-RATES-TB} in the COBOL code (continued).
 *
 * @since 2019
 */
public class DetermineRateTableWithoutReduction2022 extends DetermineRateTableWithoutReduction
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    super.calculate(calculationContext);

    final InpatientProviderData providerData = calculationContext.getProviderData();

    if (StringUtils.equals(providerData.getStateCode(), "40")
        && StringUtils.isBlank(providerData.getEhrReductionIndicator())) {
      calculationContext.setRatexTable("tab9");
    }
  }
}
