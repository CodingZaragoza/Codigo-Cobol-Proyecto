package gov.cms.fiss.pricers.ipps.core.rules.rules2021.calculate_payment;

import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.CalculateOperatingFederalSpecificPortionAmount;
import java.math.RoundingMode;

/**
 * Determines the operating federal specific portion amount.
 *
 * <p>Converted from {@code 3300-CALC-OPER-FSP-AMT} in the COBOL code.
 *
 * @since 2019
 */
public class CalculateOperatingFederalSpecificPortionAmount2021
    extends CalculateOperatingFederalSpecificPortionAmount {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    super.calculate(calculationContext);

    // ***********************************************************
    // *  OPERATING FSP CALCULATION                              *
    // ***********************************************************
    //     COMPUTE H-OPER-FSP-PART ROUNDED =
    //       ((H-NAT-PCT * (H-NAT-LABOR * H-WAGE-INDEX +
    //        H-NAT-NONLABOR * H-OPER-COLA)) * H-DRG-WT *
    //        HLD-MID-ADJ-FACT * COVID-ADJ * NO-COST-PRODUCT)
    //           ON SIZE ERROR MOVE 0 TO H-OPER-FSP-PART.
    calculationContext.setOperatingFederalSpecificPortionPart(
        calculationContext
            .getOperatingFederalSpecificPortionPart()
            .multiply(calculationContext.getCovidAdjustmentFactor())
            .multiply(calculationContext.getNoCostProductAdjustmentFactor())
            .setScale(9, RoundingMode.HALF_UP));
  }
}
