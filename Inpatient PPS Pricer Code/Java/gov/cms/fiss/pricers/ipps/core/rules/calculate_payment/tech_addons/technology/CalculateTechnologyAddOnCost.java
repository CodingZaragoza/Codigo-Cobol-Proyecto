package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons.technology;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.tables.NewTechnologyAmountTableEntry;
import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class CalculateTechnologyAddOnCost
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  protected BigDecimal calculateBaseAdjustedCharges(IppsPricerContext calculationContext) {
    return calculationContext
        .getClaimData()
        .getCoveredCharges()
        .multiply(calculationContext.getProviderData().getOperatingCostToChargeRatio())
        .subtract(calculationContext.getBaseDrgPayment());
  }

  /**
   * Converted from {@code 4020-NEW-TECH-ADD-ON} in the COBOL code.
   *
   * @param calculationContext the current pricing context
   * @param technologyCostAmounts the technology cost amounts
   */
  protected void aggregateNewTechCost(
      IppsPricerContext calculationContext, NewTechnologyAmountTableEntry technologyCostAmounts) {
    if (null != technologyCostAmounts) {
      //     MOVE 0 TO H-NEW-TECH-ADDON
      //               H-LESSER-STOP-1
      //               H-LESSER-STOP-2.
      //     COMPUTE H-LESSER-STOP-1 ROUNDED =
      //                  H-CSTMED-STOP.
      //     COMPUTE H-LESSER-STOP-2 ROUNDED =
      //          (((B-CHARGES-CLAIMED * P-NEW-OPER-CSTCHG-RATIO) -
      //             H-BASE-DRG-PAYMENT)) * H-NEW-TECH-PCT.

      final BigDecimal adjustedCharges =
          calculateBaseAdjustedCharges(calculationContext)
              .multiply(technologyCostAmounts.getAdjustmentFactor());
      BigDecimal newTechAddOn = BigDecimal.ZERO;

      //     IF H-LESSER-STOP-2 > 0
      //        IF H-LESSER-STOP-1 < H-LESSER-STOP-2
      //         MOVE H-LESSER-STOP-1 TO
      //                                H-NEW-TECH-ADDON
      //        ELSE
      //         MOVE H-LESSER-STOP-2 TO
      //                                H-NEW-TECH-ADDON
      //     ELSE
      //        MOVE ZEROES          TO H-NEW-TECH-ADDON.
      if (BigDecimalUtils.isGreaterThanZero(adjustedCharges)) {
        if (BigDecimalUtils.isLessThan(technologyCostAmounts.getAmount(), adjustedCharges)) {
          newTechAddOn = technologyCostAmounts.getAmount();
        } else {
          newTechAddOn = adjustedCharges;
        }
      }

      //     COMPUTE H-NEW-TECH-PAY-ADD-ON ROUNDED =
      //             H-NEW-TECH-PAY-ADD-ON +
      //             H-NEW-TECH-ADDON.
      calculationContext.setNewTechAddOnPayment(
          calculationContext
              .getNewTechAddOnPayment()
              .add(newTechAddOn)
              .setScale(2, RoundingMode.HALF_UP));

      //     MOVE 0 TO H-NEW-TECH-ADDON
      //               H-LESSER-STOP-1
      //               H-LESSER-STOP-2
      //               H-CSTMED-STOP.
    }
  }
}
