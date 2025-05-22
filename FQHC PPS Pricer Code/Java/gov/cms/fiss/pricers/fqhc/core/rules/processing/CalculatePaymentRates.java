package gov.cms.fiss.pricers.fqhc.core.rules.processing;

import gov.cms.fiss.pricers.common.api.annotations.FixedValue;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.fqhc.api.v2.IoceServiceLineData;
import gov.cms.fiss.pricers.fqhc.api.v2.ServiceLinePaymentData;
import gov.cms.fiss.pricers.fqhc.core.FqhcPricerContext;
import gov.cms.fiss.pricers.fqhc.core.ReturnCode;
import gov.cms.fiss.pricers.fqhc.core.ServiceLineContext;
import gov.cms.fiss.pricers.fqhc.core.daily.DaySummary;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;

/**
 * Calculates the service line payment amounts.
 *
 * <pre>
 * ----------------------------------------------------------------
 *  PAID LINE: FIND DAY SUMMARY RECORD, CALCULATE PAYMENT,
 *  CALCULATE COINSURANCE &amp; REIMBURSEMENT, AND UPDATE CLAIM TOTALS
 *    (FQHC PER DIEM VISIT)
 * ----------------------------------------------------------------
 *
 * ****************************************************************
 *
 * FOR EACH PAID LINE, CALCULATE THE LINE PAYMENT BASED ON THE
 * LINE TYPE, SET LINE RETURN CODE
 *
 * ****************************************************************
 * </pre>
 *
 * <p>Converted from {@code 4200-CALC-LINE-PYMT} in the COBOL code.
 */
public class CalculatePaymentRates
        implements CalculationRule<IoceServiceLineData, ServiceLinePaymentData, ServiceLineContext> {

    @Override
    public void calculate(ServiceLineContext calculationContext) {
        final IoceServiceLineData ioceServiceLine = calculationContext.getInput();
        final ServiceLinePaymentData serviceLinePayment = calculationContext.getOutput();
        final DaySummary daySummary = calculationContext.getDaySummary();
        final FqhcPricerContext fqhcPricerContext = calculationContext.getFqhcPricerContext();

        // Calc PPS rate with no add-on payment
        // *----------------------------------------------------------------*
        // * CALCULATE PPS RATE FOR LINE WITH NO ADD-ON PAYMENT             *
        // *----------------------------------------------------------------*
        //   IF PAID-ENCOUNTER OR PAID-GFTF
        //      COMPUTE HL-PPS-RATE ROUNDED =
        //              HL-DS-BASE-PMT-RATE * HL-DS-GAF.
        if (StringUtils.equalsAny(
                ioceServiceLine.getPaymentIndicator(),
                ServiceLineContext.PI_PAID_GFTF,
                ServiceLineContext.PI_PAID_ENCOUNTER)) {
            calculationContext.setPpsRate(
                    daySummary
                            .getBaseRate()
                            .multiply(daySummary.getGafRate())
                            .setScale(2, RoundingMode.HALF_UP));
        }

        // Calculate PPS rate with add-on payment
        // *----------------------------------------------------------------*
        // * CALCULATE PPS RATE FOR LINE WITH ADD-ON PAYMENT                *
        // *----------------------------------------------------------------*
        //   IF PAID-WITH-ADD-ON
        //      COMPUTE HL-PPS-RATE-PRE-ADD-ON ROUNDED =
        //              HL-DS-BASE-PMT-RATE * HL-DS-GAF
        //      COMPUTE HL-PPS-RATE ROUNDED =
        //              HL-PPS-RATE-PRE-ADD-ON * HL-DS-ADD-ON-PMT-RATE
        //      COMPUTE HL-LITEM-ADD-ON-PYMT =
        //              HL-PPS-RATE-PRE-ADD-ON - HL-PPS-RATE.
        if (StringUtils.equals(
                ioceServiceLine.getPaymentIndicator(), ServiceLineContext.PI_PAID_WITH_ADD_ON)) {
            final @FixedValue BigDecimal ppsPreAddon =
                    daySummary
                            .getBaseRate()
                            .multiply(daySummary.getGafRate())
                            .setScale(2, RoundingMode.HALF_UP);
            calculationContext.setPpsRate(
                    ppsPreAddon.multiply(daySummary.getAddRate()).setScale(2, RoundingMode.HALF_UP));
            // Use the absolute value as we don't care about the sign here.
            serviceLinePayment.setAddOnPayment(
                    ppsPreAddon.subtract(calculationContext.getPpsRate()).abs());
        }

        // If Payment Indicator 15 and 16, set Iop rate to ppsRate for 2024 and after
        if (LocalDateUtils.inRange(
                fqhcPricerContext.getClaimData().getServiceFromDate(),
                calculationContext.getFqhcPricerContext().getIopStartDate(),
                LocalDate.of(2024, 12, 31))
                && StringUtils.equalsAny(
                ioceServiceLine.getPaymentIndicator(),
                ServiceLineContext.PI_MA_SERVICE_IOP_WRAP_AROUND_16,
                ServiceLineContext.PI_IOP_SERVICE_15)) {
            calculationContext.setPpsRate(daySummary.getIopRateLte3());
        }

        if (LocalDateUtils.isAfterOrEqual(
                fqhcPricerContext.getClaimData().getServiceFromDate(),
                calculationContext.getFqhcPricerContext().getIopSplitStartDate())
                && StringUtils.equalsAny(
                ioceServiceLine.getPaymentIndicator(),
                ServiceLineContext.PI_MA_SERVICE_IOP_WRAP_AROUND_16,
                ServiceLineContext.PI_IOP_SERVICE_15)) {
            if (StringUtils.equals(
                    ioceServiceLine.getCompositeAdjustmentFlag(), ServiceLineContext.LINE_IOP_LTE3)) {
                calculationContext.setPpsRate(daySummary.getIopRateLte3());
            } else if (StringUtils.equals(
                    ioceServiceLine.getCompositeAdjustmentFlag(), ServiceLineContext.LINE_IOP_GTE4)) {
                calculationContext.setPpsRate(daySummary.getIopRateGte4());
            }
        }
    }
}
