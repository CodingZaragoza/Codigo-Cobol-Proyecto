package gov.cms.fiss.pricers.hospice.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.hospice.api.v2.BillingGroupData;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingRequest;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingResponse;
import gov.cms.fiss.pricers.hospice.core.HospicePricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculateGeneralInpatientCarePayment
    implements CalculationRule<
        HospiceClaimPricingRequest, HospiceClaimPricingResponse, HospicePricerContext> {
  @Override
  public boolean shouldExecute(HospicePricerContext calculationContext) {
    final BillingGroupData billGroup =
        calculationContext.getBillGroup(HospicePricerContext.GENERAL_INPATIENT_CARE_REVENUE_CODE);

    return billGroup != null;
  }

  /**
   * Calculates payment for general inpatient care.
   *
   * <pre>
   * ***************************************************************
   * ***    GIC - GENERAL INPATIENT CARE = REVENUE CODE = 0656
   * ***************************************************************
   * </pre>
   *
   * <p>Converted from @{code 2020-V200-IRC-0655} in the COBOL code.
   */
  @Override
  public void calculate(HospicePricerContext calculationContext) {
    final BillingGroupData billGroup =
        calculationContext.getBillGroup(HospicePricerContext.GENERAL_INPATIENT_CARE_REVENUE_CODE);

    if (calculationContext.isReportingQualityData()) {
      // ****============================================================
      // **** CALCULATE GIC PAYMENT WITH QIP REDUCTION (1 UNIT = 1 DAY)
      // ****============================================================
      //     IF BILL-QIP-IND = '1'
      //        COMPUTE WRK-PAY-RATE4 ROUNDED =
      //               ((2020-V200-GIC-LS-RATE-Q * BILL-PROV-WAGE-INDEX)
      //                 + 2020-V200-GIC-NLS-RATE-Q) *  BILL-UNITS4
      calculationContext.setWorkPayRate4(
          calculationContext
              .getGeneralInpatientLsRateQuality()
              .multiply(calculationContext.getProviderWageIndex())
              .add(calculationContext.getGeneralInpatientNlsRateQuality())
              .multiply(new BigDecimal(billGroup.getUnits()))
              .setScale(2, RoundingMode.HALF_UP));
    } else {
      // ****============================================================
      // **** CALCULATE GIC PAYMENT W/OUT QIP REDUCTION (1 UNIT = 1 DAY)
      // ****============================================================
      //     ELSE
      //        COMPUTE WRK-PAY-RATE4 ROUNDED =
      //               ((2020-V200-GIC-LS-RATE * BILL-PROV-WAGE-INDEX)
      //                 + 2020-V200-GIC-NLS-RATE) *  BILL-UNITS4
      calculationContext.setWorkPayRate4(
          calculationContext
              .getGeneralInpatientLsRate()
              .multiply(calculationContext.getProviderWageIndex())
              .add(calculationContext.getGeneralInpatientNlsRate())
              .multiply(new BigDecimal(billGroup.getUnits()))
              .setScale(2, RoundingMode.HALF_UP));
      //     END-IF.
    }
  }
}
