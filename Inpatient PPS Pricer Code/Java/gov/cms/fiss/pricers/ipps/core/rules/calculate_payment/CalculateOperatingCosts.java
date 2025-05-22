package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimData;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Determine the operating bill costs and the operating outlier cost part amount.
 *
 * <p>Converted from {@code 3600-CALC-OUTLIER} in the COBOL code (continued).
 *
 * @since 2019
 */
public class CalculateOperatingCosts
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final IppsClaimData claimData = calculationContext.getClaimData();

    // ***********************************************************
    // ***  OPERATING COST CALCULATION
    //     COMPUTE H-OPER-BILL-COSTS ROUNDED =
    //         B-CHARGES-CLAIMED * H-OPER-CSTCHG-RATIO
    //         ON SIZE ERROR MOVE 0 TO H-OPER-BILL-COSTS.
    calculationContext.setOperatingBillCosts(
        claimData
            .getCoveredCharges()
            .multiply(calculationContext.getOperatingCostToChargeRatio())
            .setScale(9, RoundingMode.HALF_UP));

    //     IF  H-OPER-BILL-COSTS > H-OPER-COST-OUTLIER
    //         COMPUTE H-OPER-OUTCST-PART ROUNDED =
    //         H-CSTOUT-PCT * (H-OPER-BILL-COSTS -
    //                         H-OPER-COST-OUTLIER).
    if (BigDecimalUtils.isGreaterThan(
        calculationContext.getOperatingBillCosts(), calculationContext.getOperatingCostOutlier())) {
      calculationContext.setOperatingOutlierCostPart(
          calculationContext
              .getOperatingBillCosts()
              .subtract(calculationContext.getOperatingCostOutlier())
              .multiply(calculationContext.getCostOutlierPct())
              .setScale(9, RoundingMode.HALF_UP));
    }

    //     IF PAY-WITHOUT-COST OR
    //        PAY-XFER-NO-COST OR
    //        PAY-XFER-SPEC-DRG-NO-COST
    //         MOVE 0 TO H-OPER-OUTCST-PART.
    if (calculationContext.isPayWithoutCost()
        || calculationContext.isPayTransferNoCost()
        || calculationContext.isPayTransferSpecialDrugNoCost()) {
      calculationContext.setOperatingOutlierCostPart(BigDecimal.ZERO);
    }
  }
}
