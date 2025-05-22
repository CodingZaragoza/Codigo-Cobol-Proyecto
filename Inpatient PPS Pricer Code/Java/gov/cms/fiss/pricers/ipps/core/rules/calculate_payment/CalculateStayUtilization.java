package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimData;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.api.v2.IppsPaymentData;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;

/**
 * Determines the regular or lifetime reserve days utilized during the stay.
 *
 * <p>Converted from {@code 3100-CALC-STAY-UTILIZATION} in the COBOL code.
 *
 * @since 2019
 */
public class CalculateStayUtilization
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final IppsClaimData claimData = calculationContext.getClaimData();
    final IppsPaymentData paymentData = calculationContext.getPaymentData();

    //     MOVE 0 TO PPS-REG-DAYS-USED.
    //     MOVE 0 TO PPS-LTR-DAYS-USED.
    //
    //     IF H-REG-DAYS > 0
    //        IF H-REG-DAYS > B-LOS
    //           MOVE B-LOS TO PPS-REG-DAYS-USED
    //        ELSE
    //           MOVE H-REG-DAYS TO PPS-REG-DAYS-USED
    //     ELSE
    //        IF H-LTR-DAYS > B-LOS
    //           MOVE B-LOS TO PPS-LTR-DAYS-USED
    //        ELSE
    //           MOVE H-LTR-DAYS TO PPS-LTR-DAYS-USED.
    if (calculationContext.getRegularDays() > 0) {
      if (calculationContext.getRegularDays() > claimData.getLengthOfStay()) {
        paymentData.setRegularDaysUsed(claimData.getLengthOfStay());
      } else {
        paymentData.setRegularDaysUsed(calculationContext.getRegularDays());
      }
    } else if (claimData.getLifetimeReserveDays() > claimData.getLengthOfStay()) {
      paymentData.setLifetimeReserveDaysUsed(claimData.getLengthOfStay());
    } else {
      paymentData.setLifetimeReserveDaysUsed(claimData.getLifetimeReserveDays());
    }
  }
}
