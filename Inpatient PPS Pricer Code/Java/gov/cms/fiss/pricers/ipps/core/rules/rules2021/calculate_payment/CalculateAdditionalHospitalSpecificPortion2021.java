package gov.cms.fiss.pricers.ipps.core.rules.rules2021.calculate_payment;

import gov.cms.fiss.pricers.ipps.core.CbsaReference;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.rules.rules2020.calculate_payment.CalculateAdditionalHospitalSpecificPortion2020;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Calculate the additional hospital specific portion amount.
 *
 * <p>Converted from {@code 3450-CALC-ADDITIONAL-HSP} in the COBOL code.
 *
 * @since 2019
 */
public class CalculateAdditionalHospitalSpecificPortion2021
    extends CalculateAdditionalHospitalSpecificPortion2020 {

  @Override
  protected BigDecimal getHspRate(IppsPricerContext calculationContext, BigDecimal updateFactor) {
    //     COMPUTE H-HSP-RATE ROUNDED =
    //         H-FAC-SPEC-RATE * H-UPDATE-FACTOR * H-DRG-WT * COVID-ADJ
    //         * NO-COST-PRODUCT.
    return super.getHspRate(calculationContext, updateFactor)
        .multiply(calculationContext.getNoCostProductAdjustmentFactor())
        .setScale(9, RoundingMode.HALF_UP);
  }

  @Override
  protected BigDecimal getFspRate(IppsPricerContext calculationContext) {
    final CbsaReference cbsaReference = calculationContext.getCbsaReference();

    //     COMPUTE H-FSP-RATE ROUNDED =
    //        ((H-NAT-PCT * (H-NAT-LABOR * H-WAGE-INDEX +
    //         H-NAT-NONLABOR * H-OPER-COLA)) * H-DRG-WT-FRCTN *
    //         HLD-MID-ADJ-FACT * COVID-ADJ * NO-COST-PRODUCT) *
    //             (1 + H-OPER-IME-TEACH + (H-OPER-DSH * .25))
    //                               +
    //                         H-OPER-OUTLIER-PART
    //                   ON SIZE ERROR MOVE 0 TO H-FSP-RATE.
    return calculationContext
        .getNationalPct()
        .multiply(
            calculationContext
                .getNationalLabor()
                .multiply(cbsaReference.getWageIndex())
                .add(
                    calculationContext
                        .getNationalNonLabor()
                        .multiply(calculationContext.getOperatingCostOfLivingAdjustment())))
        .multiply(calculationContext.getDrgWeightFraction())
        .multiply(calculationContext.getMidnightAdjustmentFactor())
        .multiply(calculationContext.getCovidAdjustmentFactor())
        .multiply(calculationContext.getNoCostProductAdjustmentFactor())
        .multiply(
            BigDecimal.ONE
                .add(calculationContext.getOperatingIndirectMedicalEducation())
                .add(
                    calculationContext
                        .getOperatingDisproportionateShare()
                        .multiply(new BigDecimal(".25"))))
        .add(calculationContext.getOperatingOutlierPart())
        .setScale(9, RoundingMode.HALF_UP);
  }
}
