package gov.cms.fiss.pricers.ipps.core.rules.rules2021;

import gov.cms.fiss.pricers.ipps.api.v1.DrgsTableEntry;
import gov.cms.fiss.pricers.ipps.api.v2.AdditionalCalculationVariableData;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.rules.DefaultAdditionalVariableUpdate;
import java.math.RoundingMode;
import java.util.Optional;

/**
 * Initializes secondary variables for the claim response, including COVID and other modifications.
 *
 * <p>Converted from the {@code PPCAL} module in the COBOL code.
 *
 * @since 2019
 */
public class AdditionalVariableUpdate2021 extends DefaultAdditionalVariableUpdate {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    super.calculate(calculationContext);
    final AdditionalCalculationVariableData additionalVariables =
        calculationContext.getAdditionalVariables();

    // COMPUTE H-DRG-WT ROUNDED = H-DRG-WT * COVID-ADJ * NO-COST-PRODUCT.

    additionalVariables.setDrgRelativeWeight(
        Optional.ofNullable(calculationContext.getDrgsTableEntry())
            .orElse(DrgsTableEntry.ZERO_WEIGHT)
            .getWeight()
            .multiply(calculationContext.getCovidAdjustmentFactor())
            .multiply(calculationContext.getNoCostProductAdjustmentFactor())
            .setScale(4, RoundingMode.HALF_UP));

    // COMPUTE H-DRG-WT-FRCTN ROUNDED = H-DRG-WT-FRCTN * COVID-ADJ * NO-COST-PRODUCT.

    additionalVariables.setDrgRelativeWeightFraction(
        calculationContext
            .getDrgWeightFraction()
            .multiply(calculationContext.getCovidAdjustmentFactor())
            .multiply(calculationContext.getNoCostProductAdjustmentFactor())
            .setScale(4, RoundingMode.HALF_UP));

    additionalVariables
        .getAdditionalOperatingVariables()
        .setOperatingDollarThreshold(
            calculationContext.getOperatingChargeThreshold().setScale(9, RoundingMode.HALF_UP));
  }
}
