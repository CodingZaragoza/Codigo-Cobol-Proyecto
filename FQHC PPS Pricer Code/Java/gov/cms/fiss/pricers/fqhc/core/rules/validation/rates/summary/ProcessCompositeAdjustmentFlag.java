package gov.cms.fiss.pricers.fqhc.core.rules.validation.rates.summary;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.fqhc.api.v2.IoceServiceLineData;
import gov.cms.fiss.pricers.fqhc.api.v2.ServiceLinePaymentData;
import gov.cms.fiss.pricers.fqhc.core.FqhcPricerContext;
import gov.cms.fiss.pricers.fqhc.core.ServiceLineContext;
import gov.cms.fiss.pricers.fqhc.core.daily.DaySummary;
import org.apache.commons.lang3.StringUtils;

public class ProcessCompositeAdjustmentFlag
    implements CalculationRule<IoceServiceLineData, ServiceLinePaymentData, ServiceLineContext> {

  /**
   * Updates the flag values for the day summary for the current service line.
   *
   * <pre>
   * ****************************************************************
   *
   * FOR THE CURRENT DAY SUMMARY TABLE RECORD, UPDATE THE LINE TYPE
   * PRESENT FLAGS AND ACCUMULATE CHARGES BASED ON THE CURRENT
   * SERVICE LINE'S TYPE.  MOVE THE DAY SUMMARY HOLD RECORD TO THE
   * DAY SUMMARY TABLE.
   *
   * ****************************************************************
   * </pre>
   *
   * <p>Converted from {@code 3550-UPDATE-FLAGS-CHARGES} in the COBOL code.
   */
  @Override
  public void calculate(ServiceLineContext calculationContext) {
    final IoceServiceLineData ioceServiceLine = calculationContext.getInput();
    final FqhcPricerContext fqhcPricerContext = calculationContext.getFqhcPricerContext();
    final DaySummary summary = fqhcPricerContext.getDaySummary(ioceServiceLine.getDateOfService());

    // Medical visit flags
    // *----------------------------------------------------------------*
    // * MEDICAL VISIT LINE                                             *
    // *----------------------------------------------------------------*
    //   IF MEDICAL-LINE
    //      COMPUTE HL-DS-TOT-MEDICAL-CHRGS =
    //              HL-DS-TOT-MEDICAL-CHRGS +
    //              HL-COV-CHARGES
    //      IF PAID-LINE
    //         SET MEDICAL-PAID-LINE-PRESENT TO TRUE.
    if (StringUtils.equals(
        ServiceLineContext.LINE_MEDICAL, ioceServiceLine.getCompositeAdjustmentFlag())) {
      summary.setTotalMedicalCharges(
          summary.getTotalMedicalCharges().add(ioceServiceLine.getCoveredCharges()));
      if (ServiceLineContext.isPaidLine(ioceServiceLine.getPaymentIndicator())) {
        summary.setMedicalPaidLinePresent(true);
      }
    }
    //  FQHC Intensive Outpatient Program (IOP) visit
    //  COMPOSITE ADJUSTMENT FLAG = '04'
    //  New for 2024
    if (StringUtils.equalsAny(
            ioceServiceLine.getCompositeAdjustmentFlag(),
            ServiceLineContext.LINE_IOP_LTE3,
            ServiceLineContext.LINE_IOP_GTE4)
        && !StringUtils.equals(
            ServiceLineContext.PACKAGE_ENCOUNTER, ioceServiceLine.getPackageFlag())) {
      summary.setTotalIopCharges(
          summary.getTotalIopCharges().add(ioceServiceLine.getCoveredCharges()));
      if (ServiceLineContext.isPaidLine(ioceServiceLine.getPaymentIndicator())) {
        summary.setIopChargesPresent(true);
      }
    }

    // Mental visit flags
    // *----------------------------------------------------------------*
    // * MENTAL VISIT LINE                                              *
    // *----------------------------------------------------------------*
    //   IF MENTAL-LINE
    //      COMPUTE HL-DS-TOT-MENTAL-CHRGS =
    //              HL-DS-TOT-MENTAL-CHRGS +
    //              HL-COV-CHARGES
    //      IF PAID-LINE
    //         SET MENTAL-PAID-LINE-PRESENT TO TRUE.
    if (StringUtils.equals(
        ServiceLineContext.LINE_MENTAL, ioceServiceLine.getCompositeAdjustmentFlag())) {
      summary.setTotalMentalCharges(
          summary.getTotalMentalCharges().add(ioceServiceLine.getCoveredCharges()));
      if (ServiceLineContext.isPaidLine(ioceServiceLine.getPaymentIndicator())) {
        summary.setMentalPaidLinePresent(true);
      }
    }

    // Medical or Mental with MOD 59 flags
    // *----------------------------------------------------------------*
    // * MEDICAL OR MENTAL VISIT LINE WITH MODIFIER '59'                *
    // *----------------------------------------------------------------*
    //   IF MOD59-LINE
    //      COMPUTE HL-DS-TOT-MOD59-CHRGS =
    //              HL-DS-TOT-MOD59-CHRGS +
    //              HL-COV-CHARGES
    //      IF PAID-LINE
    //         SET MOD59-PAID-LINE-PRESENT TO TRUE.
    if (StringUtils.equals(
        ServiceLineContext.LINE_MOD59, ioceServiceLine.getCompositeAdjustmentFlag())) {
      summary.setTotalMod59Charges(
          summary.getTotalMod59Charges().add(ioceServiceLine.getCoveredCharges()));
      if (ServiceLineContext.isPaidLine(ioceServiceLine.getPaymentIndicator())) {
        summary.setMod59PaidLinePresent(true);
      }
    }
  }
}
