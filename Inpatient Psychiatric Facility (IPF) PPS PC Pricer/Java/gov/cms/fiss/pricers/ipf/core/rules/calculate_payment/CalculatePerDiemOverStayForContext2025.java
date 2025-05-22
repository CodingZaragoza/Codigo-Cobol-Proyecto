package gov.cms.fiss.pricers.ipf.core.rules.calculate_payment;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimData;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Do the per diem calculation adjusted over the total stay, which is used in more than one place.
 *
 * <pre>
 * **************************************************************
 * **  CALCULATE THE DAY LOS FOR TOTAL PAYMENT W/O  TEACH-ADJ
 * **************************************************************
 * </pre>
 *
 * <p>Converted from {@code 3000-CALC-PAYMENT} in the COBOL code.
 */
public class CalculatePerDiemOverStayForContext2025
    implements CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {
  @Override
  public void calculate(IpfPricerContext calculationContext) {
    // Per diem adjustment decreased from up to 21 days to up to 10 days (eff 10/01/2024)
    final IpfClaimData claimData = calculationContext.getClaimData();
    BigDecimal federalPayment = BigDecimal.ZERO;
    final BigDecimal perDiemAmount = calculationContext.getPerDiemAmount();
    final int lengthOfStay = claimData.getLengthOfStay();
    final int totalLengthOfStay = lengthOfStay + claimData.getPriorDays();

    final int daysUpTo10 = Math.min(10, totalLengthOfStay);
    int daysOver10 = Math.max(0, totalLengthOfStay - 10);

    for (int day = claimData.getPriorDays(); day < daysUpTo10; day++) {

      federalPayment =
          federalPayment
              .add(perDiemAmount.multiply(calculationContext.getDayValue(day)))
              .setScale(2, RoundingMode.HALF_UP);
    }

    if (totalLengthOfStay > 10) {
      if (lengthOfStay > 0 && daysOver10 > lengthOfStay) {
        daysOver10 = lengthOfStay;
      }
      final BigDecimal paymentAdjustment =
          calculationContext
              .getPerDiemAmount()
              .multiply(calculationContext.getDayValue(10))
              .multiply(new BigDecimal(daysOver10))
              .setScale(2, RoundingMode.HALF_UP);
      federalPayment = federalPayment.add(paymentAdjustment);
    }
    calculationContext.setFederalPayment(federalPayment);
  }
}
