package gov.cms.fiss.pricers.hospice.core.rules.continuous_home_care;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.hospice.api.v2.BillingGroupData;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingRequest;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingResponse;
import gov.cms.fiss.pricers.hospice.core.HospicePricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ApplyContinuousHomeCareRateWithQualityReduction
    implements CalculationRule<
        HospiceClaimPricingRequest, HospiceClaimPricingResponse, HospicePricerContext> {
  /**
   * Calculates quality-reduced payment for continuous home care.
   *
   * <pre>
   * ***============================================================
   * *** CHC - APPLY QIP REDUCTION
   * ***============================================================
   * </pre>
   *
   * <p>Converted from @{code 2020-V200-CHC-0652} in the COBOL code (continuation).
   */
  @Override
  public void calculate(HospicePricerContext calculationContext) {
    final BillingGroupData billGroup =
        calculationContext.getBillGroup(HospicePricerContext.CONTINUOUS_HOME_CARE_REVENUE_CODE);

    // IF BILL-QIP-IND = '1'
    if (calculationContext.isReportingQualityData()) {
      // *---------------------------------------------------------------
      // *  PAY 1 DAY USING THE RHC RATE IF LESS THAN 32 UNITS/8 HOURS
      // *---------------------------------------------------------------
      // IF BILL-UNITS2 < 32
      if (billGroup.getUnits() < 32) {
        // *---------------------------------------------------------------
        // *  - USE HIGH RATE IF CHC DAY IS WITHIN 60 DAYS OF ADMISSION
        // *---------------------------------------------------------------
        // IF PRIOR-SVC-DAYS < 60
        if (calculationContext.getPriorServiceDays() < 60) {
          // COMPUTE WRK-PAY-RATE2 ROUNDED =
          //        ((2020-V200-HIGH-RHC-LS-RATE-Q *
          //          BILL-BENE-WAGE-INDEX) +
          //          2020-V200-HIGH-RHC-NLS-RATE-Q)
          calculationContext.setWorkPayRate2(
              calculationContext
                  .getHighRoutineHomeCareLsRateQuality()
                  .multiply(calculationContext.getPatientWageIndex())
                  .add(calculationContext.getHighRoutineHomeCareNlsRateQuality()));
        }
        // *---------------------------------------------------------------
        // *  - USE LOW RATE IF CHC DAY ISN'T WITHIN 60 DAYS OF ADMISSION
        // *---------------------------------------------------------------
        else {
          // COMPUTE WRK-PAY-RATE2 ROUNDED =
          //        ((2020-V200-LOW-RHC-LS-RATE-Q *
          //          BILL-BENE-WAGE-INDEX) +
          //          2020-V200-LOW-RHC-NLS-RATE-Q)
          calculationContext.setWorkPayRate2(
              calculationContext
                  .getLowRoutineHomeCareLsRateQuality()
                  .multiply(calculationContext.getPatientWageIndex())
                  .add(calculationContext.getLowRoutineHomeCareNlsRateQuality()));
        }
      } else {
        // *---------------------------------------------------------------
        // *  PAY USING CHC RATE FOR 32 OR MORE UNITS (8 - 24 HOURS)
        // *      - 1 UNIT = 15 MIN., DIVIDE BY 4 TO GET HOURS
        // *      - DIVIDE DAILY CHC RATE BY 24 TO GET HOURLY RATE
        // *---------------------------------------------------------------
        // COMPUTE WRK-PAY-RATE2 ROUNDED =
        //        (((2020-V200-CHC-LS-RATE-Q *
        //           BILL-BENE-WAGE-INDEX) +
        //           2020-V200-CHC-NLS-RATE-Q) / 24) *
        //           (BILL-UNITS2 / 4)
        calculationContext.setWorkPayRate2(
            calculationContext
                .getContinuousHomeCareLsRateQuality()
                .multiply(calculationContext.getPatientWageIndex())
                .add(calculationContext.getContinuousHomeCareNlsRateQuality())
                .divide(new BigDecimal("24"), 4, RoundingMode.DOWN)
                .multiply(
                    new BigDecimal(billGroup.getUnits())
                        .divide(new BigDecimal("4"), 2, RoundingMode.HALF_UP)));
      }
    }
  }
}
