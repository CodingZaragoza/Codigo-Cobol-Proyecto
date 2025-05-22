package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.CbsaReference;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import java.math.BigDecimal;

/**
 * Determines the national labor and non-labor percentages as well as the operating and capital
 * cost-to-charge ratios.
 *
 * <p>Converted from {@code 3000-CALC-PAYMENT} in the COBOL code (continued).
 *
 * @since 2019
 */
public class CalculateNationalPercentages
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();

    // *****YEARCHANGE 2018.0 *******************************************
    // * NATIONAL PERCENTAGE                                            *
    // ******************************************************************
    //       MOVE 0.6830 TO H-LABOR-PCT.
    //       MOVE 0.3170 TO H-NONLABOR-PCT.
    calculationContext.setNationalLaborPct(calculationContext.getNationalLaborPctWageIndexGtOne());
    calculationContext.setNationalNonLaborPct(
        calculationContext.getNationalNonLaborPctWageIndexGtOne());

    //     IF (H-WAGE-INDEX < 01.0000 OR H-WAGE-INDEX = 01.0000)
    //       MOVE 0.6200 TO H-LABOR-PCT
    //       MOVE 0.3800 TO H-NONLABOR-PCT.
    final CbsaReference cbsaReference = calculationContext.getCbsaReference();
    if (BigDecimalUtils.isLessThanOrEqualTo(cbsaReference.getWageIndex(), BigDecimal.ONE)) {
      calculationContext.setNationalLaborPct(
          calculationContext.getNationalLaborPctWageIndexLtEqOne());
      calculationContext.setNationalNonLaborPct(
          calculationContext.getNationalNonLaborPctWageIndexLtEqOne());
    }

    //     IF  P-NEW-OPER-CSTCHG-RATIO NUMERIC
    //             MOVE P-NEW-OPER-CSTCHG-RATIO TO H-OPER-CSTCHG-RATIO
    //     ELSE
    //             MOVE 0.000 TO H-OPER-CSTCHG-RATIO.
    if (BigDecimalUtils.isGreaterThanZero(providerData.getOperatingCostToChargeRatio())) {
      calculationContext.setOperatingCostToChargeRatio(
          providerData.getOperatingCostToChargeRatio());
    } else {
      calculationContext.setOperatingCostToChargeRatio(BigDecimal.ZERO);
    }

    //     IF P-NEW-CAPI-CSTCHG-RATIO NUMERIC
    //             MOVE P-NEW-CAPI-CSTCHG-RATIO TO H-CAPI-CSTCHG-RATIO
    //     ELSE
    //             MOVE 0.000 TO H-CAPI-CSTCHG-RATIO.
    if (BigDecimalUtils.isGreaterThanZero(providerData.getCapitalCostToChargeRatio())) {
      calculationContext.setCapitalOperatingCostToChargeRatio(
          providerData.getCapitalCostToChargeRatio());
    } else {
      calculationContext.setCapitalOperatingCostToChargeRatio(BigDecimal.ZERO);
    }
  }
}
