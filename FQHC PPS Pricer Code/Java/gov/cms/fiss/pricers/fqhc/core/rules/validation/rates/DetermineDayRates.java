package gov.cms.fiss.pricers.fqhc.core.rules.validation.rates;

import gov.cms.fiss.pricers.common.api.annotations.FixedValue;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.fqhc.api.v2.IoceServiceLineData;
import gov.cms.fiss.pricers.fqhc.api.v2.ServiceLinePaymentData;
import gov.cms.fiss.pricers.fqhc.core.FqhcPricerContext;
import gov.cms.fiss.pricers.fqhc.core.ReturnCode;
import gov.cms.fiss.pricers.fqhc.core.ServiceLineContext;
import gov.cms.fiss.pricers.fqhc.core.daily.DailyRates;
import gov.cms.fiss.pricers.fqhc.core.tables.DataTables;
import gov.cms.fiss.pricers.fqhc.core.tables.GAFRateTableEntry.GAFRateKey;
import java.math.BigDecimal;

/**
 * Per-day rate extraction from the service line validation.
 *
 * <p>Converted from {@code 3000-VALIDATE-LINE} in the COBOL code (continuation).
 */
public class DetermineDayRates
    implements CalculationRule<IoceServiceLineData, ServiceLinePaymentData, ServiceLineContext> {

  @Override
  public void calculate(ServiceLineContext calculationContext) {
    final IoceServiceLineData ioceServiceLine = calculationContext.getInput();
    final FqhcPricerContext fqhcPricerContext = calculationContext.getFqhcPricerContext();
    final DataTables dataTables = calculationContext.getDataTables();

    final @FixedValue BigDecimal dailyBaseRate;
    final @FixedValue BigDecimal dailyAddRate;
    final @FixedValue BigDecimal dailyGafRate;
    if (fqhcPricerContext.isGftfClaim()) {
      // *----------------------------------------------------------------*
      // *  FOR GRANDFATHERED TRIBAL FQHCS (GFTF) ONLY:                   *
      // *  - GET GFTF RATE TO BE USED FOR THE LINE'S DATE OF SERVICE     *
      // *----------------------------------------------------------------*
      //   IF GFTF-CLAIM
      //      SET GFTF-INDX TO 1
      //      PERFORM 3220-GET-GFTF-PMT-RATE
      //         THRU 3220-GET-GFTF-PMT-RATE-EXIT
      //         VARYING GFTF-INDX FROM 1 BY 1
      //         UNTIL (GFTF-INDX > GFTF-MAX) OR
      //               (GFTF-EFFDATE (GFTF-INDX) > HL-LINE-SRVC-DATE)
      dailyBaseRate = dataTables.getTribalRate(ioceServiceLine.getDateOfService());
    } else {
      // *----------------------------------------------------------------*
      // *  FOR ALL OTHER FQHCS (NON-GFTF):                               *
      // *  - GET BASE RATE TO BE USED FOR THE LINE'S DATE OF SERVICE     *
      // *----------------------------------------------------------------*
      //   ELSE
      //      SET BASE-INDX TO 1
      //      PERFORM 3200-GET-BASE-PMT-RATE
      //         THRU 3200-GET-BASE-PMT-RATE-EXIT
      //         VARYING BASE-INDX FROM 1 BY 1
      //         UNTIL (BASE-INDX > BASE-MAX) OR
      //               (BASE-EFFDATE (BASE-INDX) > HL-LINE-SRVC-DATE)
      //   END-IF.
      dailyBaseRate = dataTables.getBaseRate(ioceServiceLine.getDateOfService());
    }

    // IF HL-DS-BASE-PMT-RATE = 0
    //    MOVE 18 TO O-LITEM-RETURN-CODE (LN-SUB)
    //    INITIALIZE HL-SERVICE-LINE
    //    GO TO 3000-VALIDATE-LINE-EXIT.
    if (dailyBaseRate == null || dailyBaseRate.compareTo(BigDecimal.ZERO) == 0) {
      // Don't validate anymore as this line has finished processing
      calculationContext.completeWithReturnCode(ReturnCode.LINE_NO_EFF_BASE_RATE_18);
      return;
    }

    // *----------------------------------------------------------------*
    // *  GET ADD-ON FACTOR TO BE USED FOR THE LINE'S DATE OF SERVICE   *
    // *----------------------------------------------------------------*
    //   IF GFTF-CLAIM
    //      MOVE 1 TO HL-DS-ADD-ON-PMT-RATE
    //   ELSE
    //      SET ADD-INDX TO 1
    //      PERFORM 3300-GET-ADD-ON-PMT-RATE
    //         THRU 3300-GET-ADD-ON-PMT-RATE-EXIT
    //         VARYING ADD-INDX FROM 1 BY 1
    //         UNTIL (ADD-INDX > ADD-MAX) OR
    //               (ADD-EFFDATE (ADD-INDX) > HL-LINE-SRVC-DATE)
    //   END-IF.
    if (fqhcPricerContext.isGftfClaim()) {
      dailyAddRate = BigDecimal.ONE;
    } else {
      dailyAddRate = dataTables.getAddOnRate(ioceServiceLine.getDateOfService());
    }

    //   IF HL-DS-ADD-ON-PMT-RATE = 0
    //      MOVE 20 TO O-LITEM-RETURN-CODE (LN-SUB)
    //      INITIALIZE HL-SERVICE-LINE
    //      GO TO 3000-VALIDATE-LINE-EXIT.
    if (dailyAddRate == null || dailyAddRate.compareTo(BigDecimal.ZERO) == 0) {
      // Don't validate anymore as this line has finished processing
      calculationContext.completeWithReturnCode(ReturnCode.LINE_NO_EFF_ADD_ON_RATE_20);
      return;
    }

    // *----------------------------------------------------------------*
    // *  GET GEOGRAPHIC ADJUSTMENT FACTOR (GAF) TO BE USED FOR THE     *
    // *  LINE'S DATE OF SERVICE                                        *
    // *----------------------------------------------------------------*
    //   IF GFTF-CLAIM
    //      MOVE 1 TO HL-DS-GAF
    //
    //   ELSE
    //      SET GAF-INDX TO 1
    //      PERFORM 3400-GET-GAF
    //         THRU 3400-GET-GAF-EXIT
    //   END-IF.
    if (fqhcPricerContext.isGftfClaim()) {
      dailyGafRate = BigDecimal.ONE;
    } else {
      dailyGafRate =
          dataTables.getGafRate(
              new GAFRateKey(
                  fqhcPricerContext.getClaimData().getCarrierCode()
                      + fqhcPricerContext.getClaimData().getLocalityCode(),
                  ioceServiceLine.getDateOfService()));
    }

    //   IF HL-DS-GAF = 0
    //      MOVE 19 TO O-LITEM-RETURN-CODE (LN-SUB)
    //      INITIALIZE HL-SERVICE-LINE
    //      GO TO 3000-VALIDATE-LINE-EXIT.
    if (dailyGafRate == null || dailyGafRate.compareTo(BigDecimal.ZERO) == 0) {
      // Don't validate anymore as this line has finished processing
      calculationContext.completeWithReturnCode(ReturnCode.LINE_NO_EFF_GAF_19);
      return;
    }

    // New for 2024
    // If grandfather tribal move tribal rate to IOP daily rate otherwise IOP
    BigDecimal dailyIopRateLte3 = BigDecimal.ZERO;
    if (LocalDateUtils.isAfterOrEqual(
        fqhcPricerContext.getClaimData().getServiceFromDate(),
        calculationContext.getFqhcPricerContext().getIopStartDate())) {
      if (fqhcPricerContext.isGftfClaim()) {
        dailyIopRateLte3 = dataTables.getTribalRate(ioceServiceLine.getDateOfService());
      } else {
        dailyIopRateLte3 = dataTables.getIopRateLte3(ioceServiceLine.getDateOfService());
      }

      // check if daily IOP Rate is null or zero and set return code
      if (dailyIopRateLte3 == null || dailyIopRateLte3.compareTo(BigDecimal.ZERO) == 0) {
        // Don't validate anymore as this line has finished processing
        calculationContext.completeWithReturnCode(ReturnCode.LINE_NO_EFF_BASE_RATE_18);
        return;
      }
    }

    // New for 2025
    // Validate Services Count via CAF for IOP daily rate set
    BigDecimal dailyIopRateGte4 = BigDecimal.ZERO;
    if (LocalDateUtils.isAfterOrEqual(
        fqhcPricerContext.getClaimData().getServiceFromDate(),
        calculationContext.getFqhcPricerContext().getIopSplitStartDate())) {
      if (fqhcPricerContext.isGftfClaim()) {
        dailyIopRateGte4 = dataTables.getTribalRate(ioceServiceLine.getDateOfService());
      } else {
        dailyIopRateGte4 = dataTables.getIopRateGte4(ioceServiceLine.getDateOfService());
      }

      // check if daily IOP Rate is null or zero and set return code
      if (dailyIopRateGte4 == null || dailyIopRateGte4.compareTo(BigDecimal.ZERO) == 0) {
        // Don't validate anymore as this line has finished processing
        calculationContext.completeWithReturnCode(ReturnCode.LINE_NO_EFF_BASE_RATE_18);
        return;
      }
    }

    calculationContext.setDailyRates(
        new DailyRates(
            dailyBaseRate, dailyAddRate, dailyIopRateLte3, dailyIopRateGte4, dailyGafRate));
  }
}
