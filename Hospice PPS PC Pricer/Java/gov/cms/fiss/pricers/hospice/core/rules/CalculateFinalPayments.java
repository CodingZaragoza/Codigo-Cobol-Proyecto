package gov.cms.fiss.pricers.hospice.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.hospice.api.v2.BillPaymentData;
import gov.cms.fiss.pricers.hospice.api.v2.BillingGroupData;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingRequest;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingResponse;
import gov.cms.fiss.pricers.hospice.api.v2.HospicePaymentData;
import gov.cms.fiss.pricers.hospice.core.HospicePricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CalculateFinalPayments
    implements CalculationRule<
        HospiceClaimPricingRequest, HospiceClaimPricingResponse, HospicePricerContext> {
  @Override
  public void calculate(HospicePricerContext calculationContext) {
    final BigDecimal workPayRate1 = calculationContext.getWorkPayRate1();
    final BigDecimal workPayRate2 = calculationContext.getWorkPayRate2();
    final BigDecimal workPayRate3 = calculationContext.getWorkPayRate3();
    final BigDecimal workPayRate4 = calculationContext.getWorkPayRate4();

    final HospicePaymentData paymentData = calculationContext.getPaymentData();
    // *---------------------------------------------------------------
    // *  CALCULATE TOTAL CLAIM PAYMENT
    // *---------------------------------------------------------------
    //     COMPUTE BILL-PAY-AMT-TOTAL =
    //             WRK-PAY-RATE1 +
    //             WRK-PAY-RATE2 +
    //             WRK-PAY-RATE3 +
    //             WRK-PAY-RATE4 +
    //             SIA-PAY-AMT-TOTAL.
    paymentData.setTotalPayment(
        BigDecimalUtils.decimalSum(
                workPayRate1,
                workPayRate2,
                workPayRate3,
                workPayRate4,
                calculationContext.getSiaPayAmountTotal())
            .setScale(2, RoundingMode.HALF_UP));

    // *---------------------------------------------------------------
    // *  MOVE EACH LEVEL OF CARE'S PAYMENT TO THE OUTPUT RECORD
    // *---------------------------------------------------------------
    //     MOVE WRK-PAY-RATE1        TO  BILL-PAY-AMT1.
    //     MOVE WRK-PAY-RATE2        TO  BILL-PAY-AMT2.
    //     MOVE WRK-PAY-RATE3        TO  BILL-PAY-AMT3.
    //     MOVE WRK-PAY-RATE4        TO  BILL-PAY-AMT4.

    final List<String> revenueCodes = new ArrayList<>();
    revenueCodes.add(HospicePricerContext.ROUTINE_HOME_CARE_REVENUE_CODE);
    revenueCodes.add(HospicePricerContext.CONTINUOUS_HOME_CARE_REVENUE_CODE);
    revenueCodes.add(HospicePricerContext.INPATIENT_RESPITE_CARE_REVENUE_CODE);
    revenueCodes.add(HospicePricerContext.GENERAL_INPATIENT_CARE_REVENUE_CODE);

    calculationContext
        .getBillingGroups()
        .forEach(group -> revenueCodes.remove(group.getRevenueCode()));

    final BillPaymentData payRate1 = new BillPaymentData();
    payRate1.setAmount(workPayRate1.setScale(2, RoundingMode.HALF_UP));
    assignRevenueCode(calculationContext, payRate1, 0, revenueCodes);

    final BillPaymentData payRate2 = new BillPaymentData();
    payRate2.setAmount(workPayRate2.setScale(2, RoundingMode.HALF_UP));
    assignRevenueCode(calculationContext, payRate2, 1, revenueCodes);

    final BillPaymentData payRate3 = new BillPaymentData();
    payRate3.setAmount(workPayRate3.setScale(2, RoundingMode.HALF_UP));
    assignRevenueCode(calculationContext, payRate3, 2, revenueCodes);

    final BillPaymentData payRate4 = new BillPaymentData();
    payRate4.setAmount(workPayRate4.setScale(2, RoundingMode.HALF_UP));
    assignRevenueCode(calculationContext, payRate4, 3, revenueCodes);

    paymentData.setBillPayments(List.of(payRate1, payRate2, payRate3, payRate4));

    paymentData.setPatientWageIndex(calculationContext.getPatientWageIndex());
    paymentData.setProviderWageIndex(calculationContext.getProviderWageIndex());
  }

  /**
   * Assign revenue code to the bill payment data based on provided claim bill groups, filling in
   * remaining codes in ascending order.
   *
   * @param calculationContext the calculation context
   * @param billPaymentData the bill payment data to set revenue code on
   * @param index the expected index of the related claim bill group
   * @param availableCodes the codes not present in claim bill groups
   */
  private void assignRevenueCode(
      HospicePricerContext calculationContext,
      BillPaymentData billPaymentData,
      int index,
      List<String> availableCodes) {
    final List<BillingGroupData> billingGroups = calculationContext.getBillingGroups();
    final String revenueCode;
    if (billingGroups.size() > index) {
      revenueCode = billingGroups.get(index).getRevenueCode();
    } else {
      revenueCode = availableCodes.remove(0);
    }

    billPaymentData.setRevenueCode(revenueCode);
  }
}
