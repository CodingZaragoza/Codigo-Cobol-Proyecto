package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Determines the cap for the calculation new technology add-on payment.
 *
 * <p>Converted from {@code 4000-CALC-TECH-ADDON} in the COBOL code (continued).
 *
 * @since 2019
 */
public class CapCalculatedTechAddOn
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  /**
   * Converted from {@code 5500-CAP-CALC-TECH-ADD-ON} in the COBOL code.
   *
   * @param calculationContext the current pricing context
   */
  @Override
  public void calculate(IppsPricerContext calculationContext) {
    //     MOVE 0 TO H-NEW-TECH-ADDON-CAP.
    //     MOVE 0 TO H-NEW-TECH-ADDON-CAPDIF.
    //     COMPUTE H-OPER-BILL-COSTS ROUNDED =
    //         B-CHARGES-CLAIMED * H-OPER-CSTCHG-RATIO
    //         ON SIZE ERROR MOVE 0 TO H-OPER-BILL-COSTS.
    calculationContext.setOperatingBillCosts(
        calculationContext
            .getClaimData()
            .getCoveredCharges()
            .multiply(calculationContext.getOperatingCostToChargeRatio())
            .setScale(9, RoundingMode.HALF_UP));

    //     COMPUTE H-NEW-TECH-ADDON-CAP ROUNDED =
    //                 (H-BASE-DRG-PAYMENT + H-NEW-TECH-PAY-ADD-ON).
    final BigDecimal newTechAddOnCap =
        calculationContext
            .getBaseDrgPayment()
            .add(calculationContext.getNewTechAddOnPayment())
            .setScale(2, RoundingMode.HALF_UP);

    //     COMPUTE H-NEW-TECH-ADDON-CAPDIF ROUNDED =
    //                 (H-OPER-BILL-COSTS - H-BASE-DRG-PAYMENT).
    final BigDecimal newTechAddOnCapDiff =
        calculationContext
            .getOperatingBillCosts()
            .subtract(calculationContext.getBaseDrgPayment())
            .setScale(2, RoundingMode.HALF_UP);

    //     IF (H-NEW-TECH-ADDON-CAP > H-OPER-BILL-COSTS) AND
    //         H-NEW-TECH-ADDON-CAPDIF  > 0
    //        COMPUTE H-NEW-TECH-PAY-ADD-ON  ROUNDED =
    //             (H-OPER-BILL-COSTS - H-BASE-DRG-PAYMENT).
    if (BigDecimalUtils.isLessThan(calculationContext.getOperatingBillCosts(), newTechAddOnCap)
        && BigDecimalUtils.isGreaterThanZero(newTechAddOnCapDiff)) {
      calculationContext.setNewTechAddOnPayment(
          calculationContext
              .getOperatingBillCosts()
              .subtract(calculationContext.getBaseDrgPayment())
              .setScale(2, RoundingMode.HALF_UP));
    }

    //     COMPUTE H-OPER-BASE-DRG-PAY ROUNDED =
    //             H-OPER-FSP-PART +
    //             H-NEW-TECH-PAY-ADD-ON.
    calculationContext.setOperatingBaseDrgPayment(
        calculationContext
            .getOperatingFederalSpecificPortionPart()
            .add(calculationContext.getNewTechAddOnPayment())
            .setScale(2, RoundingMode.HALF_UP));
  }
}
