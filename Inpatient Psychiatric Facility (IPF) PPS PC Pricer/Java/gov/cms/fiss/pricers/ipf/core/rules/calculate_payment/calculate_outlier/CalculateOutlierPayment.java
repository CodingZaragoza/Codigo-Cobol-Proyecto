package gov.cms.fiss.pricers.ipf.core.rules.calculate_payment.calculate_outlier;

import gov.cms.fiss.pricers.common.api.annotations.FixedValue;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipf.api.v2.AdditionalVariableData;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimData;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;
import gov.cms.fiss.pricers.ipf.core.codes.ReturnCode;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Calculate the outlier data.
 *
 * <p>Converted from {@code 3050-GET-OUTLIER} in the COBOL code.
 */
public class CalculateOutlierPayment
    implements CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {

  @Override
  public void calculate(IpfPricerContext calculationContext) {
    final IpfClaimData claimData = calculationContext.getClaimData();
    //      IF BILL-LOS > 9
    //         COMPUTE DAYS-OVER-9 = (BILL-LOS - 9)
    //         MOVE 9 TO DAYS-UPTO-9
    //      ELSE
    //         MOVE BILL-LOS TO DAYS-UPTO-9.
    final int lengthOfStay = claimData.getLengthOfStay();
    final int daysUpTo9 = Math.min(9, lengthOfStay);
    final int daysOver9 = Math.max(0, lengthOfStay - 9);

    setOutlierPayment(calculationContext, daysUpTo9, daysOver9);
  }

  /**
   * Calculates and sets the outlier payment based on a "number of days" calculation that varies on
   * the basis of a bug prior to 2022.
   *
   * @param calculationContext The calculation context.
   * @param daysUpTo9 Number of days up to 9 in the current stay.
   * @param daysOver9 Number of days over 9 in the current stay.
   */
  protected void setOutlierPayment(
      IpfPricerContext calculationContext, @FixedValue int daysUpTo9, int daysOver9) {
    final AdditionalVariableData additionalVariables = calculationContext.getAdditionalVariables();
    final BigDecimal outlierPerDiem = additionalVariables.getOutlierPerDiemAmount();

    //      COMPUTE IPF-OUTLIER-PAYMENT ROUNDED =
    //             (DAYS-UPTO-9 * (IPF-OUTL-PER-DIEM-AMT * .80)).
    BigDecimal outlierPayment =
        outlierPerDiem
            .multiply(new BigDecimal(daysUpTo9))
            .multiply(new BigDecimal("0.80"))
            .setScale(2, RoundingMode.HALF_UP);

    //
    //      IF BILL-LOS > 9
    //         COMPUTE IPF-OUTLIER-PAYMENT ROUNDED =
    //                 IPF-OUTLIER-PAYMENT +
    //        (DAYS-OVER-9 * (IPF-OUTL-PER-DIEM-AMT * .60)).
    outlierPayment =
        outlierPayment
            .add(
                outlierPerDiem.multiply(new BigDecimal(daysOver9)).multiply(new BigDecimal("0.60")))
            .setScale(2, RoundingMode.HALF_UP);

    //      IF IPF-OUTLIER-PAYMENT = ZEROES
    //         MOVE '00' TO IPF-RTC.
    if (BigDecimalUtils.isZero(outlierPayment)) {
      calculationContext.applyReturnCode(ReturnCode.NORMAL_PAYMENT_0);
    }

    additionalVariables.setOutlierPayment(outlierPayment);
  }
}
