package gov.cms.fiss.pricers.ipps.core.rules.rules2020;

import gov.cms.fiss.pricers.ipps.api.v1.DrgsTableEntry;
import gov.cms.fiss.pricers.ipps.api.v2.AdditionalCalculationVariableData;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.rules.DefaultAdditionalVariableUpdate;
import java.math.RoundingMode;
import java.util.Optional;

/**
 * Initializes secondary variables for the claim response, including COVID modifications.
 *
 * <p>Converted from the {@code PPCAL} module in the COBOL code.
 *
 * @since 2019
 */
public class AdditionalVariableUpdate2020 extends DefaultAdditionalVariableUpdate {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    super.calculate(calculationContext);
    final AdditionalCalculationVariableData additionalVariables =
        calculationContext.getAdditionalVariables();

    // COMPUTE H-DRG-WT ROUNDED = H-DRG-WT * COVID-ADJ.

    additionalVariables.setDrgRelativeWeight(
        Optional.ofNullable(calculationContext.getDrgsTableEntry())
            .orElse(DrgsTableEntry.ZERO_WEIGHT)
            .getWeight()
            .multiply(calculationContext.getCovidAdjustmentFactor())
            .setScale(4, RoundingMode.HALF_UP));

    // COMPUTE H-DRG-WT-FRCTN ROUNDED = H-DRG-WT-FRCTN * COVID-ADJ.

    additionalVariables.setDrgRelativeWeightFraction(
        calculationContext
            .getDrgWeightFraction()
            .multiply(calculationContext.getCovidAdjustmentFactor())
            .setScale(4, RoundingMode.HALF_UP));
  }
}
