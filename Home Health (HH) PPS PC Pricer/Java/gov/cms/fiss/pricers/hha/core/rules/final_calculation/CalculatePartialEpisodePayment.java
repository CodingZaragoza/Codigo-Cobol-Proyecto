package gov.cms.fiss.pricers.hha.core.rules.final_calculation;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimData;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingRequest;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingResponse;
import gov.cms.fiss.pricers.hha.api.v2.HhaPaymentData;
import gov.cms.fiss.pricers.hha.api.v2.RevenueLineData;
import gov.cms.fiss.pricers.hha.api.v2.RevenuePaymentData;
import gov.cms.fiss.pricers.hha.core.HhaPricerContext;
import gov.cms.fiss.pricers.hha.core.codes.ReturnCode;
import gov.cms.fiss.pricers.hha.core.models.RevenueRateEntry;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CalculatePartialEpisodePayment
    implements CalculationRule<HhaClaimPricingRequest, HhaClaimPricingResponse, HhaPricerContext> {
  @Override
  public void calculate(HhaPricerContext calculationContext) {
    if (calculationContext.getPaymentData().getTotalQuantityOfCoveredVisits()
        >= calculationContext.getHrgThreshold()) {
      calculationContext.setPartialEpisodePaymentCalculated();

      final HhaClaimData claimData = calculationContext.getClaimData();
      final Boolean partialEpisodePaymentIndicator =
          calculationContext.isPartialEpisodePaymentIndicator();
      // TODO: Determine if we need to check this on our end or if the pricer client is handling it
      // if (partialEpisodePaymentIndicator == null) {
      //   calculationContext.setReturnCode(ReturnCode.INVALID_PEP_INDICATOR_20);
      //   return;
      // }
      if (claimData.getHhrgNumberOfDays() > 30) {
        calculationContext.applyReturnCode(ReturnCode.NUMBER_OF_DAYS_EXCEEDS_LIMIT_16);
        return;
      }
      BigDecimal payment = calculationContext.calculatePaymentBasis();
      if (Boolean.TRUE.equals(partialEpisodePaymentIndicator)) {
        final BigDecimal percentOfDays =
            BigDecimal.valueOf(claimData.getHhrgNumberOfDays() / 30.0)
                .setScale(2, RoundingMode.HALF_UP);
        payment = payment.multiply(percentOfDays).setScale(2, RoundingMode.HALF_UP);

        calculationContext.setReturnCodeAdjustmentIndicator(
            HhaPricerContext.RTC_ADJUSTMENT_INDICATOR_2);
      }

      payment = payment.setScale(2, RoundingMode.HALF_UP);
      calculationContext.setPretotalPepPayment(
          calculationContext.getPretotalPepPayment().add(payment));
      calculationContext.getPaymentData().setHhrgPayment(payment);
      calculateOutlierPayment(calculationContext);
    }
  }

  /** Converted from {@code 7000-OUTLIER-PAYMENT} in the COBOL code. */
  private void calculateOutlierPayment(HhaPricerContext calculationContext) {
    final HhaPaymentData paymentData = calculationContext.getPaymentData();
    final BigDecimal outlierThresholdAmountAdjustment =
        calculationContext.calculateAdjustedCost(calculationContext.getOutlierThresholdAmount());

    BigDecimal totalPayment = calculationContext.getPretotalPepPayment();
    final BigDecimal outlierValueA = totalPayment.add(outlierThresholdAmountAdjustment);
    final BigDecimal outlierValueB = calculateOutlierValueB(calculationContext);
    final BigDecimal outlierDifference = outlierValueB.subtract(outlierValueA);

    if (BigDecimalUtils.isGreaterThanZero(outlierDifference)) {
      final BigDecimal outlierPayment =
          calculationContext
              .getOutlierLossSharingRatio()
              .multiply(outlierDifference)
              .setScale(2, RoundingMode.HALF_UP);
      paymentData.setOutlierPayment(outlierPayment);
      calculateOutlierCap(calculationContext);
      totalPayment = totalPayment.add(paymentData.getOutlierPayment());

      // 9000-WHICH-RTC-OUTLIER
      switch (calculationContext.getReturnCodeAdjustmentIndicator()) {
        case HhaPricerContext.RTC_ADJUSTMENT_INDICATOR_2:
          calculationContext.applyReturnCode(ReturnCode.PAYMENT_PARTIAL_PERIOD_OUTLIER_11);
          break;
        case HhaPricerContext.RTC_ADJUSTMENT_INDICATOR_4:
          calculationContext.applyReturnCode(ReturnCode.PAYMENT_WITH_OUTLIER_LIMITATION_2);
          break;
        default:
          calculationContext.applyReturnCode(ReturnCode.PAYMENT_WITH_OUTLIER_1);
      }
    } else {
      // 9050-WHICH-RTC-NO-OUTLIER
      if (calculationContext.getReturnCodeAdjustmentIndicator()
          == HhaPricerContext.RTC_ADJUSTMENT_INDICATOR_2) {
        calculationContext.applyReturnCode(ReturnCode.PAYMENT_PARTIAL_PERIOD_9);
      } else {
        calculationContext.applyReturnCode(ReturnCode.PAYMENT_WITHOUT_OUTLIER_0);
      }
    }

    totalPayment = totalPayment.setScale(2, RoundingMode.HALF_UP);
    paymentData.setTotalPayment(totalPayment);
  }

  /** Converted from {@code 8000-ADD-REV-DOLL} in the COBOL code. */
  private BigDecimal calculateOutlierValueB(HhaPricerContext calculationContext) {
    BigDecimal outlierValueB = BigDecimalUtils.ZERO;
    final List<RevenueLineData> inputRevenueData = calculationContext.getRevenueLines();
    final List<RevenuePaymentData> outputRevenueData = calculationContext.getRevenuePayments();
    final List<RevenueRateEntry> revenueRateData = calculationContext.getRevenueData();
    for (int i = 0; i < revenueRateData.size(); i++) {
      final RevenueRateEntry revenueRateEntry = revenueRateData.get(i);
      if (revenueRateEntry.getRevenueCode().isBlank()) {
        break;
      }

      final BigDecimal revenueDollarRateUnits =
          revenueRateEntry
              .getDollarRateUnits()
              .multiply(calculationContext.getRuralAddon())
              .setScale(2, RoundingMode.HALF_UP);
      final BigDecimal federalAdjustment =
          revenueDollarRateUnits
              .multiply(BigDecimal.valueOf(inputRevenueData.get(i).getQuantityOfOutlierUnits()))
              .setScale(2, RoundingMode.HALF_UP);
      final BigDecimal payment = calculationContext.calculateAdjustedCost(federalAdjustment);
      outputRevenueData.get(i).setCost(payment.setScale(2, RoundingMode.HALF_UP));
      outlierValueB = outlierValueB.add(payment);
    }
    return outlierValueB;
  }

  /** Converted from {@code 10000-OUTLIER-CAP-CALC} in the COBOL code. */
  private void calculateOutlierCap(HhaPricerContext calculationContext) {
    final HhaClaimData claimData = calculationContext.getClaimData();
    final HhaPaymentData paymentData = calculationContext.getPaymentData();

    // HHA-PROV-PAYMENT-TOTAL
    final BigDecimal paymentTotal = claimData.getPriorPaymentTotal();
    // HHA-PROV-OUTLIER-PAY-TOTAL
    final BigDecimal outlierPaymentTotal = claimData.getPriorOutlierTotal();

    if (BigDecimalUtils.isZero(paymentTotal) || BigDecimalUtils.isZero(outlierPaymentTotal)) {
      return;
    }

    // COMPUTE WK-10000-OUTLIER-POOL-PERCENT ROUNDED = HHA-PROV-PAYMENT-TOTAL * .1
    final BigDecimal outlierPoolPercent =
        paymentTotal.multiply(BigDecimal.valueOf(.1)).setScale(2, RoundingMode.HALF_UP);

    // COMPUTE WK-10000-OUTLIER-AVAIL-POOL ROUNDED = WK-10000-OUTLIER-POOL-PERCENT -
    //    HHA-PROV-OUTLIER-PAY-TOTAL
    final BigDecimal outlierAvailablePool = outlierPoolPercent.subtract(outlierPaymentTotal);

    // COMPUTE WK-10000-OUTLIER-POOL-DIF ROUNDED = WK-10000-OUTLIER-AVAIL-POOL - WK-7000-CALC
    final BigDecimal outlierPoolDiff =
        outlierAvailablePool.subtract(paymentData.getOutlierPayment());

    if (BigDecimalUtils.isGreaterThanZero(outlierPoolDiff)) {
      return;
    }

    if (BigDecimalUtils.isLessThanZero(outlierPoolDiff)
        || BigDecimalUtils.isLessThanZero(outlierPaymentTotal)) {
      paymentData.setOutlierPayment(BigDecimalUtils.ZERO);
      calculationContext.setReturnCodeAdjustmentIndicator(
          HhaPricerContext.RTC_ADJUSTMENT_INDICATOR_4);
    }
  }
}
