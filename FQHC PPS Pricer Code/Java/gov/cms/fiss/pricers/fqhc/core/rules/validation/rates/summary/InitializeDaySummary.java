package gov.cms.fiss.pricers.fqhc.core.rules.validation.rates.summary;

import gov.cms.fiss.pricers.common.api.annotations.FixedValue;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.fqhc.api.v2.IoceServiceLineData;
import gov.cms.fiss.pricers.fqhc.api.v2.ServiceLinePaymentData;
import gov.cms.fiss.pricers.fqhc.core.FqhcPricerContext;
import gov.cms.fiss.pricers.fqhc.core.ServiceLineContext;
import gov.cms.fiss.pricers.fqhc.core.daily.DailyRates;
import gov.cms.fiss.pricers.fqhc.core.daily.DaySummary;

public class InitializeDaySummary
    implements CalculationRule<IoceServiceLineData, ServiceLinePaymentData, ServiceLineContext> {

  @Override
  public void calculate(ServiceLineContext calculationContext) {
    final IoceServiceLineData ioceServiceLine = calculationContext.getInput();
    final FqhcPricerContext fqhcPricerContext = calculationContext.getFqhcPricerContext();

    // Add a new Day Summary if that day has not already been started
    // *----------------------------------------------------------------*
    // * SEARCH DAY SUMMARY TABLE STARTING AT ENTRY #1                  *
    // *----------------------------------------------------------------*
    //   SET W-DS-INDX TO 1.
    //     SEARCH W-DAY-SUM-ENTRY VARYING W-DS-INDX
    @FixedValue
    DaySummary summary = fqhcPricerContext.getDaySummary(ioceServiceLine.getDateOfService());
    // *----------------------------------------------------------------*
    // *  UPDATE DAY SUMMARY TABLE USING THE LINE'S INFORMATION         *
    // *----------------------------------------------------------------*
    //   PERFORM 3500-LOAD-DAY-SUMMARY-TBL
    //      THRU 3500-LOAD-DAY-SUMMARY-TBL-EXIT.
    if (summary == null) {
      final DailyRates dailyRates = calculationContext.getDailyRates();
      summary =
          new DaySummary(
              ioceServiceLine.getDateOfService(),
              dailyRates.getDailyBaseRate(),
              dailyRates.getDailyIopRateLte3(),
              dailyRates.getDailyIopRateGte4(),
              dailyRates.getDailyGafRate(),
              dailyRates.getDailyAddRate());
    }

    //  *----------------------------------------------------------------*
    //  * IF THE SERVICE LINE'S DATE IS NOT ALREADY IN THE TABLE,        *
    //  * ADD A NEW RECORD FOR THAT DAY                                  *
    //  *----------------------------------------------------------------*
    //    AT END
    //       PERFORM 3520-ADD-ENTRY
    //          THRU 3520-ADD-ENTRY-EXIT
    // *----------------------------------------------------------------*
    // * IF THE SERVICE LINE'S DATE IS ALREADY IN THE TABLE,            *
    // * UPDATE THE EXISTING RECORD FOR THAT DAY                        *
    // *----------------------------------------------------------------*
    //   WHEN W-DS-DATE (W-DS-INDX) = HL-LINE-SRVC-DATE
    //     PERFORM 3530-UPDATE-ENTRY
    //       THRU 3530-UPDATE-ENTRY-EXIT.
    fqhcPricerContext.applyDaySummary(ioceServiceLine.getDateOfService(), summary);
  }
}
