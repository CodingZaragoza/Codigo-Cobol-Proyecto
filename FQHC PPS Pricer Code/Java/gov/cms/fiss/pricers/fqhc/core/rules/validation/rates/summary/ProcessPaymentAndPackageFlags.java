package gov.cms.fiss.pricers.fqhc.core.rules.validation.rates.summary;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.fqhc.api.v2.IoceServiceLineData;
import gov.cms.fiss.pricers.fqhc.api.v2.ServiceLinePaymentData;
import gov.cms.fiss.pricers.fqhc.core.FqhcPricerContext;
import gov.cms.fiss.pricers.fqhc.core.ServiceLineContext;
import gov.cms.fiss.pricers.fqhc.core.daily.DaySummary;
import org.apache.commons.lang3.StringUtils;

public class ProcessPaymentAndPackageFlags
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

    // **************************************************************************************
    // 06/12/2020 - CLG - COVID19 UPDATES
    // *----------------------------------------------------------------*
    // * QUALIFYING VISIT OR ANCILLARY SERVICE LINE                     *
    // * (EXCLUDES COVID-19/COINSURANCE N/A SERVICES)                   *
    // *----------------------------------------------------------------*
    //        IF NO-ADDTNL-PYMT AND PKG-ENCOUNTER AND
    //           NOT FQHC-COINNA-SERVICE
    //           SET OTHER-PKG-LINE-PRESENT TO TRUE.
    //
    if (!calculationContext.isCoinsuranceNAPaymentFlag()
        && StringUtils.equalsAny(
            ioceServiceLine.getPackageFlag(), ServiceLineContext.PACKAGE_ENCOUNTER)
        && StringUtils.equalsAny(
            ioceServiceLine.getPaymentIndicator(), ServiceLineContext.PI_NO_ADDITIONAL_PAYMENT)) {
      summary.setOtherPackageLinePresent(true);
    }

    // *----------------------------------------------------------------*
    // * NEW Statement for COVID-19                                     *
    // * QUALIFYING VISIT OR ANCILLARY SERVICE LINE - COINSURANCE N/A   *
    // * (APPLIES TO COVID-19/COINSURANCE N/A SERVICES)                 *
    // *----------------------------------------------------------------*
    //       IF NO-ADDTNL-PYMT AND PKG-ENCOUNTER AND FQHC-COINNA-SERVICE
    //          SET COINNA-PKG-LINE-PRESENT TO TRUE
    //          COMPUTE HL-DS-TOT-COINNA-PKG-CHRGS =
    //                  HL-DS-TOT-COINNA-PKG-CHRGS +
    //                  HL-COV-CHARGES.
    //
    if (calculationContext.isCoinsuranceNAPaymentFlag()
        && StringUtils.equalsAny(
            ioceServiceLine.getPackageFlag(), ServiceLineContext.PACKAGE_ENCOUNTER)
        && StringUtils.equalsAny(
            ioceServiceLine.getPaymentIndicator(), ServiceLineContext.PI_NO_ADDITIONAL_PAYMENT)) {
      summary.setCoinsuranceNotApplicablePackageLinePresent(true);
      summary.setTotalCoinsuranceNotApplicablePackageCharges(
          summary
              .getTotalCoinsuranceNotApplicablePackageCharges()
              .add(ioceServiceLine.getCoveredCharges()));
    }

    // *----------------------------------------------------------------*
    // * PREVENTIVE SERVICE LINE - COINSURANCE NOT APPLICABLE           *
    // *----------------------------------------------------------------*
    //       IF NO-ADDTNL-PYMT AND PKG-PREVENTIVE
    //          SET PREVENTIVE-LINE-PRESENT TO TRUE
    //          SET COINNA-PKG-LINE-PRESENT TO TRUE
    //          COMPUTE HL-DS-TOT-COINNA-PKG-CHRGS =
    //                  HL-DS-TOT-COINNA-PKG-CHRGS +
    //                  HL-COV-CHARGES.
    //
    if (StringUtils.equals(ioceServiceLine.getPackageFlag(), ServiceLineContext.PACKAGE_PREVENTIVE)
        && StringUtils.equalsAny(
            ioceServiceLine.getPaymentIndicator(), ServiceLineContext.PI_NO_ADDITIONAL_PAYMENT)) {
      summary.setPreventivePresent(true);
      summary.setCoinsuranceNotApplicablePackageLinePresent(true);
      summary.setTotalCoinsuranceNotApplicablePackageCharges(
          summary
              .getTotalCoinsuranceNotApplicablePackageCharges()
              .add(ioceServiceLine.getCoveredCharges()));
    }
  }
}
