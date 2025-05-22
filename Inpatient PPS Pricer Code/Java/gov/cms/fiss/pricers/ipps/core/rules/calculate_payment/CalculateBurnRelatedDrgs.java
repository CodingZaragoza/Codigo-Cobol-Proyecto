package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimData;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import java.math.BigDecimal;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Determines the cost outlier percentage based on presence of burn related diagnostic-related
 * groups (DRGs).
 *
 * <p>Converted from {@code 3000-CALC-PAYMENT} in the COBOL code (continued).
 *
 * @since 2019
 */
public class CalculateBurnRelatedDrgs
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final IppsClaimData claimData = calculationContext.getClaimData();

    calculationContext.setCostOutlierPct(new BigDecimal("0.8"));

    // *****************************************************************
    // **
    // ** BURN DRGS FOR FY14 ARE 927, 928, 929, 933, 934 AND 935.
    // **
    // *****************************************************************
    //     IF  B-DRG = 927 OR 928 OR 929 OR 933 OR 934 OR 935
    //             MOVE 0.90 TO H-CSTOUT-PCT.
    if (ArrayUtils.contains(
        calculationContext.getBurnRelatedDrgs(), claimData.getDiagnosisRelatedGroup())) {
      calculationContext.setCostOutlierPct(new BigDecimal("0.9"));
    }
  }
}
