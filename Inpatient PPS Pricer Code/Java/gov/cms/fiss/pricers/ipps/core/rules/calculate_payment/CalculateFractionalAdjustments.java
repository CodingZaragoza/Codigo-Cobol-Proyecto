package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.api.v1.DrgsTableEntry;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.api.v2.IppsPaymentData;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Determines the DRG weight fraction, discharge fraction, and transfer adjustment amounts.
 *
 * <p>Converted from {@code 3000-CALC-PAYMENT} in the COBOL code (continued).
 *
 * @since 2019
 */
public class CalculateFractionalAdjustments
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final DrgsTableEntry drgsTableEntry = calculationContext.getDrgsTableEntry();
    calculationContext.setDischargeFraction(BigDecimal.ONE);

    //     COMPUTE H-DRG-WT-FRCTN ROUNDED = H-DSCHG-FRCTN * H-DRG-WT.
    calculationContext.setDrgWeightFraction(
        calculationContext
            .getDischargeFraction()
            .multiply(drgsTableEntry.getWeight())
            .setScale(4, RoundingMode.HALF_UP));

    final IppsPaymentData paymentData = calculationContext.getPaymentData();
    final BigDecimal perDiemDays = calculationContext.getPerDiemDays();

    //     IF (PAY-PERDIEM-DAYS  OR
    //         PAY-XFER-NO-COST) OR
    //        (PAY-XFER-SPEC-DRG AND
    //         D-DRG-POSTACUTE-PERDIEM)
    //       IF H-ALOS > 0
    //         COMPUTE H-TRANSFER-ADJ ROUNDED = H-PERDIEM-DAYS / H-ALOS
    //         COMPUTE H-DSCHG-FRCTN  ROUNDED = H-PERDIEM-DAYS / H-ALOS
    //         IF H-DSCHG-FRCTN > 1
    //              MOVE 1 TO H-DSCHG-FRCTN
    //              MOVE 1 TO H-TRANSFER-ADJ
    //         ELSE
    //              COMPUTE H-DRG-WT-FRCTN ROUNDED =
    //                  H-TRANSFER-ADJ * H-DRG-WT
    //         END-IF
    //        END-IF
    //     END-IF.
    if ((calculationContext.isPayPerDiemDays()
            || calculationContext.isPayTransferNoCost()
            || calculationContext.isPayTransferSpecialDrug()
                && calculationContext.isDrgPostacutePerDiem())
        && BigDecimalUtils.isGreaterThanZero(paymentData.getAverageLengthOfStay())) {
      calculationContext.setTransferAdjustment(
          perDiemDays.divide(paymentData.getAverageLengthOfStay(), 4, RoundingMode.HALF_UP));
      calculationContext.setDischargeFraction(
          perDiemDays.divide(paymentData.getAverageLengthOfStay(), 4, RoundingMode.HALF_UP));

      if (BigDecimalUtils.isGreaterThan(
          calculationContext.getDischargeFraction(), BigDecimal.ONE)) {
        calculationContext.setDischargeFraction(BigDecimal.ONE);
        calculationContext.setTransferAdjustment(BigDecimal.ONE);
      } else {
        calculationContext.setDrgWeightFraction(
            calculationContext
                .getTransferAdjustment()
                .multiply(drgsTableEntry.getWeight())
                .setScale(4, RoundingMode.HALF_UP));
      }
    }

    //     IF (PAY-XFER-SPEC-DRG AND
    //         D-DRG-POSTACUTE-50-50) AND
    //         H-ALOS > 0
    //         COMPUTE H-TRANSFER-ADJ ROUNDED = H-PERDIEM-DAYS / H-ALOS
    //         COMPUTE H-DSCHG-FRCTN  ROUNDED =
    //                        .5 + ((.5 * H-PERDIEM-DAYS) / H-ALOS)
    //         IF H-DSCHG-FRCTN > 1
    //              MOVE 1 TO H-DSCHG-FRCTN
    //              MOVE 1 TO H-TRANSFER-ADJ
    //         ELSE
    //              COMPUTE H-DRG-WT-FRCTN ROUNDED =
    //            (.5 + ((.5 * H-PERDIEM-DAYS) / H-ALOS)) * H-DRG-WT.
    if (calculationContext.isPayTransferSpecialDrug()
        && calculationContext.isDrgPostacute5050()
        && BigDecimalUtils.isGreaterThanZero(paymentData.getAverageLengthOfStay())) {
      calculationContext.setTransferAdjustment(
          perDiemDays.divide(paymentData.getAverageLengthOfStay(), 4, RoundingMode.HALF_UP));
      calculationContext.setDischargeFraction(
          new BigDecimal("0.5")
              .add(
                  perDiemDays
                      .multiply(new BigDecimal("0.5"))
                      .divide(paymentData.getAverageLengthOfStay(), 4, RoundingMode.HALF_UP)));

      if (BigDecimalUtils.isGreaterThan(
          calculationContext.getDischargeFraction(), BigDecimal.ONE)) {
        calculationContext.setDischargeFraction(BigDecimal.ONE);
        calculationContext.setTransferAdjustment(BigDecimal.ONE);
      } else {
        calculationContext.setDrgWeightFraction(
            perDiemDays
                .multiply(new BigDecimal("0.5"))
                .divide(paymentData.getAverageLengthOfStay(), 5, RoundingMode.DOWN)
                .add(new BigDecimal("0.5"))
                .multiply(drgsTableEntry.getWeight())
                .setScale(4, RoundingMode.HALF_UP));
      }
    }
  }
}
