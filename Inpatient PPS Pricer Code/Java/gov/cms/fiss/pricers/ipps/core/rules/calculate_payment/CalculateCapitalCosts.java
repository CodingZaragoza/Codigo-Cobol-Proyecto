package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimData;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.commons.lang3.StringUtils;

/**
 * Determine the operating bill costs, operating outlier cost part amount, and the capital outlier
 * cost part amount.
 *
 * <p>Converted from {@code 3600-CALC-OUTLIER} in the COBOL code (continued).
 *
 * @since 2019
 */
public class CalculateCapitalCosts
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final IppsClaimData claimData = calculationContext.getClaimData();

    // ***********************************************************
    // ***  CAPITAL COST CALCULATION
    //     COMPUTE H-CAPI-BILL-COSTS ROUNDED =
    //             B-CHARGES-CLAIMED * H-CAPI-CSTCHG-RATIO
    //         ON SIZE ERROR MOVE 0 TO H-CAPI-BILL-COSTS.
    final BigDecimal capitalBillCosts =
        claimData
            .getCoveredCharges()
            .multiply(calculationContext.getCapitalOperatingCostToChargeRatio())
            .setScale(9, RoundingMode.HALF_UP);

    //     IF  H-CAPI-BILL-COSTS > H-CAPI-COST-OUTLIER
    //         COMPUTE H-CAPI-OUTCST-PART ROUNDED =
    //         H-CSTOUT-PCT * (H-CAPI-BILL-COSTS -
    //                         H-CAPI-COST-OUTLIER).
    if (BigDecimalUtils.isGreaterThan(
        capitalBillCosts, calculationContext.getCapitalCostOutlier())) {
      calculationContext.setCapitalOutlierCostPart(
          capitalBillCosts
              .subtract(calculationContext.getCapitalCostOutlier())
              .multiply(calculationContext.getCostOutlierPct())
              .setScale(9, RoundingMode.HALF_UP));
    }

    //     IF P-NEW-CAPI-PPS-PAY-CODE = 'C'
    //        COMPUTE H-CAPI-OUTCST-PART ROUNDED =
    //               (H-CAPI-OUTCST-PART * H-CAPI-PAYCDE-PCT1).
    if (StringUtils.equals(calculationContext.getProviderData().getCapitalPpsPaymentCode(), "C")) {
      calculationContext.setCapitalOutlierCostPart(
          calculationContext
              .getCapitalOutlierCostPart()
              .multiply(calculationContext.getCapitalPaycodePct1())
              .setScale(9, RoundingMode.HALF_UP));
    }

    //     IF (H-CAPI-BILL-COSTS   + H-OPER-BILL-COSTS) <
    //        (H-CAPI-COST-OUTLIER + H-OPER-COST-OUTLIER)
    //        MOVE 0 TO H-CAPI-OUTCST-PART
    //                  H-OPER-OUTCST-PART.
    if (BigDecimalUtils.isLessThan(
        capitalBillCosts.add(calculationContext.getOperatingBillCosts()),
        calculationContext
            .getCapitalCostOutlier()
            .add(calculationContext.getOperatingCostOutlier()))) {
      calculationContext.setCapitalOutlierCostPart(BigDecimal.ZERO);
      calculationContext.setOperatingOutlierCostPart(BigDecimal.ZERO);
    }

    //     IF PAY-WITHOUT-COST OR
    //        PAY-XFER-NO-COST OR
    //        PAY-XFER-SPEC-DRG-NO-COST
    //         MOVE 0 TO H-CAPI-OUTCST-PART.
    if (calculationContext.isPayWithoutCost()
        || calculationContext.isPayTransferNoCost()
        || calculationContext.isPayTransferSpecialDrugNoCost()) {
      calculationContext.setCapitalOutlierCostPart(BigDecimal.ZERO);
    }
  }
}
