package gov.cms.fiss.pricers.hospice.core.rules.routine_home_care;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingRequest;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingResponse;
import gov.cms.fiss.pricers.hospice.core.HospicePricerContext;
import gov.cms.fiss.pricers.hospice.core.codes.ReturnCode;

public class SumRoutineHomeCareRate
    implements CalculationRule<
        HospiceClaimPricingRequest, HospiceClaimPricingResponse, HospicePricerContext> {
  /**
   * Sets the work pay rate and return codes for routine home care.
   *
   * <pre>
   * ****************************************************************
   * **** V19.B   RHC - SUM RHC '0651' COMPONENTS
   * ****************************************************************
   * </pre>
   *
   * <p>Converted from {@code V200-SUM-RHC-0651-RATE} in the COBOL code.
   */
  @Override
  public void calculate(HospicePricerContext calculationContext) {
    // ****============================================================
    // **** CALCULATE TOTAL RHC PAYMENT
    // ****============================================================
    // COMPUTE WRK-PAY-RATE1 =
    //        HR-BILL-PAY-AMT1 + LR-BILL-PAY-AMT1.
    calculationContext.setWorkPayRate1(
        calculationContext
            .getHighRateBillPayAmount()
            .add(calculationContext.getLowRateBillPayAmount()));
    // ****============================================================
    // **** ASSIGN RHC RETURN CODE (RTC)
    // ****============================================================
    // *---------------------------------------------------------------
    // * END-OF-LIFE (EOL) SERVICE INTENSITY (SIA) ADD-ON PRESENT
    // *---------------------------------------------------------------
    // IF SIA-UNITS-IND = 'Y'
    if (calculationContext.isSiaUnitsIndicator()) {
      // *---------------------------------------------------------------
      // *     HIGH RHC RATE (APPLIES TO SOME OR ALL RHC) & EOL SIA
      // *---------------------------------------------------------------
      // IF RHC-HIGH-DAY-IND = 'Y'
      if (calculationContext.isRoutineHomeCareHighDayIndicator()) {
        // MOVE '77' TO BILL-RTC
        calculationContext.applyReturnCode(ReturnCode.HIGH_RATE_WITH_EOL_SIA_77);
      } else {
        // *---------------------------------------------------------------
        // *     LOW RHC RATE APPLIES TO ALL RHC & EOL SIA
        // *---------------------------------------------------------------
        // ELSE
        // IF RHC-LOW-DAY-IND = 'Y'
        if (calculationContext.isRoutineHomeCareLowDayIndicator()) {
          // MOVE '74' TO BILL-RTC
          calculationContext.applyReturnCode(ReturnCode.LOW_RATE_WITH_EOL_SIA_74);
        }
        // END-IF
      }
      // END-IF
    }
    // END-IF
    // *---------------------------------------------------------------
    // * END-OF-LIFE (EOL) SERVICE INTENSITY ADD-ON (SIA) NOT PRESENT
    // *---------------------------------------------------------------
    // IF SIA-UNITS-IND = 'N'
    if (!calculationContext.isSiaUnitsIndicator()) {
      // *---------------------------------------------------------------
      // *     HIGH RHC RATE (APPLIES TO SOME OR ALL RHC) & NO EOL SIA
      // *---------------------------------------------------------------
      // IF RHC-HIGH-DAY-IND = 'Y'
      if (calculationContext.isRoutineHomeCareHighDayIndicator()) {
        // MOVE '75' TO BILL-RTC
        calculationContext.applyReturnCode(ReturnCode.HIGH_RATE_APPLIED_75);
      } else {
        // *---------------------------------------------------------------
        // *     LOW RHC RATE APPLIES TO ALL RHC & NO EOL SIA
        // *---------------------------------------------------------------
        // ELSE
        // IF RHC-LOW-DAY-IND = 'Y'
        if (calculationContext.isRoutineHomeCareLowDayIndicator()) {
          // MOVE '73' TO BILL-RTC
          calculationContext.applyReturnCode(ReturnCode.LOW_RATE_APPLIED_73);
        }
        // END-IF
      }
      // END-IF
    }
    // END-IF
  }
}
