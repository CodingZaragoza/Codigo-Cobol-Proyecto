package gov.cms.fiss.pricers.ipps.core.rules.cost_factor_determination.ratex;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import java.math.BigDecimal;

/**
 * Determines the blend percentages for the claim.
 *
 * <p>Converted from {@code 2050-RATES-TB} in the COBOL code (continued).
 *
 * @since 2019
 */
public class DetermineBlendPercents
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    // ***************************************************************
    // * GET THE HSP & FSP BLEND PERCENTS FOR THIS BILL              *
    // ***************************************************************
    //     MOVE 0.00  TO H-OPER-HSP-PCT.
    //     MOVE 1.00  TO H-OPER-FSP-PCT.
    calculationContext.setOperatingHospitalSpecificPortionPct(BigDecimal.ZERO);
    calculationContext.setOperatingFederalSpecificPortionPct(BigDecimal.ONE);

    // ***************************************************************
    // *  GET THE NATIONAL & REGIONAL BLEND PERCENTS FOR THIS BILL   *
    // ***************************************************************
    //      MOVE 1.00 TO H-NAT-PCT.
    //      MOVE 0.00 TO H-REG-PCT.
    //     IF  P-N-SCH-REBASED-FY90 OR
    //         P-N-EACH OR
    //         P-N-MDH-REBASED-FY90
    //         MOVE 1.00 TO H-OPER-HSP-PCT.
    calculationContext.setNationalPct(BigDecimal.ONE);
    if (calculationContext.isSchRebasedFy90ProviderType()
        || calculationContext.isEachProviderType()
        || calculationContext.isInvalidProviderType()) {
      calculationContext.setOperatingHospitalSpecificPortionPct(BigDecimal.ONE);
    }
  }
}
