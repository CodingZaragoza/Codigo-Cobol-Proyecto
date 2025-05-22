package gov.cms.fiss.pricers.hospice.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.hospice.api.v2.BillingGroupData;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingRequest;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingResponse;
import gov.cms.fiss.pricers.hospice.core.HospicePricerContext;
import java.math.BigDecimal;

public class CalculateInpatientRespiteCarePayment
    implements CalculationRule<
        HospiceClaimPricingRequest, HospiceClaimPricingResponse, HospicePricerContext> {
  @Override
  public boolean shouldExecute(HospicePricerContext calculationContext) {
    final BillingGroupData billGroup =
        calculationContext.getBillGroup(HospicePricerContext.INPATIENT_RESPITE_CARE_REVENUE_CODE);

    return billGroup != null;
  }

  /**
   * Calculates payment for inpatient respite care.
   *
   * <pre>
   * ****************************************************************
   * **** V20.0 IRC - INPATIENT RESPITE CARE = REVENUE CODE = 0655
   * ****************************************************************
   * </pre>
   *
   * <p>Converted from @{code 2020-V200-IRC-0655} in the COBOL code.
   */
  @Override
  public void calculate(HospicePricerContext calculationContext) {
    // ****============================================================
    // **** CALCULATE IRC PAYMENT WITH QIP REDUCTION (1 UNIT = 1 DAY)
    // ****============================================================
    final BillingGroupData billGroup =
        calculationContext.getBillGroup(HospicePricerContext.INPATIENT_RESPITE_CARE_REVENUE_CODE);

    if (calculationContext.isReportingQualityData()) {
      // IF BILL-QIP-IND = '1'
      //    COMPUTE WRK-PAY-RATE3 ROUNDED =
      //       ((2020-V200-IRC-LS-RATE-Q * BILL-PROV-WAGE-INDEX)
      //         + 2020-V200-IRC-NLS-RATE-Q) *  BILL-UNITS3
      calculationContext.setWorkPayRate3(
          calculationContext
              .getInpatientRespiteLsRateQuality()
              .multiply(calculationContext.getProviderWageIndex())
              .add(calculationContext.getInpatientRespiteNlsRateQuality())
              .multiply(new BigDecimal(billGroup.getUnits())));
    } else {
      // ****============================================================
      // **** CALCULATE IRC PAYMENT W/OUT QIP REDUCTION (1 UNIT = 1 DAY)
      // ****============================================================
      // COMPUTE WRK-PAY-RATE3 ROUNDED =
      //       ((2020-V200-IRC-LS-RATE * BILL-PROV-WAGE-INDEX)
      //         + 2020-V200-IRC-NLS-RATE) *  BILL-UNITS3
      calculationContext.setWorkPayRate3(
          calculationContext
              .getInpatientRespiteLsRate()
              .multiply(calculationContext.getProviderWageIndex())
              .add(calculationContext.getInpatientRespiteNlsRate())
              .multiply(new BigDecimal(billGroup.getUnits())));
    }
  }
}
