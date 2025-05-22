package gov.cms.fiss.pricers.hospice.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingRequest;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingResponse;
import gov.cms.fiss.pricers.hospice.core.HospicePricerContext;
import java.time.temporal.ChronoUnit;

public abstract class CalculatePriorSvcDays
    implements CalculationRule<
        HospiceClaimPricingRequest, HospiceClaimPricingResponse, HospicePricerContext> {
  /**
   * Calculates total days before service date and since admission.
   *
   * <p>Converted from {@code V200-CALC-PRIOR-SVC-DAYS} in the COBOL code.
   */
  @Override
  public void calculate(HospicePricerContext calculationContext) {
    applyDateOfService(calculationContext);
    // *---------------------------------------------------------------
    // * GET ADMISSION DATE (SERVICE DATE SET PRIOR TO THIS PARAGRAPH)
    // *---------------------------------------------------------------
    //     MOVE BILL-ADMISSION-DATE TO DATE-1-ADM.
    // *---------------------------------------------------------------
    // * CONVERT ADMISSION AND SERVICE DATES INTO INTEGERS
    // *---------------------------------------------------------------
    //     COMPUTE DATE-1-ADM-INTEGER =
    //             FUNCTION INTEGER-OF-DATE (DATE-1-ADM).
    //     COMPUTE DATE-2-DOS-INTEGER =
    //             FUNCTION INTEGER-OF-DATE (DATE-2-DOS).
    // *---------------------------------------------------------------
    // * CALCULATE DAYS ELAPSED BETWEEN ADMISSION AND SERVICE DATES
    // *---------------------------------------------------------------
    //     COMPUTE DAYS-BETWEEN-DATES =
    //             DATE-2-DOS-INTEGER - DATE-1-ADM-INTEGER.
    final int daysBetweenDates =
        Math.toIntExact(
            ChronoUnit.DAYS.between(
                calculationContext.getClaimData().getAdmissionDate(),
                calculationContext.getDateOfService()));

    // *---------------------------------------------------------------
    // * DETERMINE THE NUMBER OF PRIOR BENEFIT DAYS
    // *---------------------------------------------------------------
    int priorBenefitDays = 0;
    if (calculationContext.getClaimData().getPriorBenefitDayUnits() > 0) {
      //     IF BILL-NA-ADD-ON-DAY1-UNITS > ZEROES
      //         COMPUTE PRIOR-BENEFIT-DAYS = BILL-NA-ADD-ON-DAY1-UNITS
      priorBenefitDays = calculationContext.getClaimData().getPriorBenefitDayUnits();
      //     ELSE
      //         MOVE ZERO TO PRIOR-BENEFIT-DAYS
      //     END-IF.
    }

    // *---------------------------------------------------------------
    // * CALCULATE TOTAL DAYS SINCE ADMISSION & BEFORE SERVICE DATE
    // *---------------------------------------------------------------
    //     COMPUTE PRIOR-SVC-DAYS =
    //             DAYS-BETWEEN-DATES + PRIOR-BENEFIT-DAYS.
    calculationContext.setPriorServiceDays(daysBetweenDates + priorBenefitDays);
  }

  /**
   * Used to inject the correct date of service for prior service day calculation.
   *
   * @param calculationContext the current calculation context
   */
  protected abstract void applyDateOfService(HospicePricerContext calculationContext);
}
