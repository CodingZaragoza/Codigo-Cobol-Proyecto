package gov.cms.fiss.pricers.hha.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingRequest;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingResponse;
import gov.cms.fiss.pricers.hha.api.v2.HhaPaymentData;
import gov.cms.fiss.pricers.hha.api.v2.RevenueLineData;
import gov.cms.fiss.pricers.hha.api.v2.RevenuePaymentData;
import gov.cms.fiss.pricers.hha.core.HhaPricerContext;
import gov.cms.fiss.pricers.hha.core.models.RevenueRateEntry;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;

public class CalculateStandardValue
    implements CalculationRule<HhaClaimPricingRequest, HhaClaimPricingResponse, HhaPricerContext> {
  private static final BigDecimal THIRTY_DAYS = new BigDecimal("30.00000000");
  private static final BigDecimal ONE = new BigDecimal("1.00");

  // TODO: Good candidate to split into seperate rules once testing is set up/confirmed
  @Override
  public void calculate(HhaPricerContext calculationContext) {
    final HhaPaymentData paymentData = calculationContext.getPaymentData();
    // MOVE 0 TO H-HHA-PPS-STD-VALUE.
    paymentData.setStandardizedPayment(BigDecimalUtils.ZERO);

    // IF H-HHA-REVENUE-SUM1-6-QTY-ALL < 5 AND H-HHA-TOB IS NOT EQUAL TO 322
    if (paymentData.getTotalQuantityOfCoveredVisits() < 5 && !calculationContext.isBillTypeRap()) {
      // PERFORM 9210-CALC-STD-VALUE-LUPA THRU 9210-EXIT
      calculateLupaStandardValue(calculationContext);
    }

    // IF H-HHA-REVENUE-SUM1-6-QTY-ALL > 4 OR H-HHA-TOB IS EQUAL TO 322
    if (paymentData.getTotalQuantityOfCoveredVisits() > 4 || calculationContext.isBillTypeRap()) {
      // PERFORM 9220-CALC-STD-VALUE-NLUPA THRU 9220-EXIT
      calculateNonLupaStandardValue(calculationContext);
    }
  }

  private void calculateLupaStandardValue(HhaPricerContext calculationContext) {
    final HhaPaymentData paymentData = calculationContext.getPaymentData();
    BigDecimal standardizedPaymentAmount = paymentData.getStandardizedPayment();

    // MOVE 0 TO SS-QCV.
    // PERFORM 6 TIMES
    // ADD 1 TO SS-QCV
    final List<RevenueLineData> inputRevenueData = calculationContext.getRevenueLines();
    final List<RevenueRateEntry> revenueRateData = calculationContext.getRevenueData();
    final List<RevenuePaymentData> outputRevenueData = paymentData.getRevenuePayments();

    for (int i = 0; i < inputRevenueData.size(); i++) {
      final RevenueRateEntry revenueRateEntry = revenueRateData.get(i);
      final RevenuePaymentData outputRevenueEntry = outputRevenueData.get(i);
      final int quantityOfCoveredVisits =
          ObjectUtils.defaultIfNull(inputRevenueData.get(i).getQuantityOfCoveredVisits(), 0);
      final BigDecimal dollarRate = revenueRateEntry.getStandardValueDollarRate();
      if (quantityOfCoveredVisits > 0) {
        //   COMPUTE H-HHA-PPS-STD-VALUE ROUNDED =
        //     H-HHA-PPS-STD-VALUE +
        //     (H-HHA-REVENUE-QTY-COV-VISITS (SS-QCV) *
        //     TB-STDV-REV-DOLL-RATE (SS-QCV))
        standardizedPaymentAmount =
            BigDecimal.valueOf(quantityOfCoveredVisits)
                .multiply(dollarRate)
                .add(standardizedPaymentAmount)
                .setScale(2, RoundingMode.HALF_UP);
      }

      if (BigDecimalUtils.isGreaterThanZero(outputRevenueEntry.getAddOnVisitAmount())) {
        //   COMPUTE H-HHA-PPS-STD-VALUE ROUNDED =
        //     H-HHA-PPS-STD-VALUE +
        //     (TB-STDV-REV-DOLL-RATE (SS-QCV) *
        //     WS-STDV-LUPA-ADDON-FAC)
        standardizedPaymentAmount =
            dollarRate
                .multiply(calculationContext.getWsStdvLupaAddonFac())
                .add(standardizedPaymentAmount)
                .setScale(2, RoundingMode.HALF_UP);
      }
    }

    // COMPUTE H-HHA-PPS-STD-VALUE ROUNDED =
    //   H-HHA-PPS-STD-VALUE *
    //   WS-STDV-RURAL-FAC
    standardizedPaymentAmount =
        standardizedPaymentAmount
            .multiply(calculationContext.getStandardizedRuralMultiplier())
            .setScale(2, RoundingMode.HALF_UP);

    paymentData.setStandardizedPayment(standardizedPaymentAmount);
  }

  private void calculateNonLupaStandardValue(HhaPricerContext calculationContext) {
    final HhaPaymentData paymentData = calculationContext.getPaymentData();
    final BigDecimal laborPercent = calculationContext.getLaborPercent();
    // Intermediate calculation of H-HHA-HRG-NO-OF-DAYS / 30
    final BigDecimal percentOfDays =
        BigDecimal.valueOf(calculationContext.getClaimData().getHhrgNumberOfDays())
            .divide(THIRTY_DAYS, 8, RoundingMode.DOWN);

    // COMPUTE WS-STD-VALUE-NLUPA-AMT ROUNDED =
    //   ( ( H-HHA-HRG-WGTS * WS-STDV-EPISODE-AMT ) + 0 ) * H-HHA-HRG-NO-OF-DAYS / 30 *
    //   WS-STDV-RURAL-FAC * 1
    final BigDecimal amount =
        paymentData
            .getHhrgWeight()
            .multiply(calculationContext.getStandardValueAmount())
            .multiply(percentOfDays)
            .multiply(calculationContext.getStandardizedRuralMultiplier())
            .setScale(2, RoundingMode.HALF_UP);

    // COMPUTE WS-STD-VALUE-NLUPA-OUTL ROUNDED =
    //   H-HHA-OUTLIER-PAYMENT / ( (LABOR-PERCENT * WIR-CBSA-WAGEIND) + (1 - LABOR-PERCENT) )
    final BigDecimal outlier =
        paymentData
            .getOutlierPayment()
            .divide(
                laborPercent
                    .multiply(calculationContext.getCbsaWageIndex())
                    .add(ONE.subtract(laborPercent)),
                2,
                RoundingMode.HALF_UP);

    // COMPUTE H-HHA-PPS-STD-VALUE ROUNDED =
    // WS-STD-VALUE-NLUPA-AMT +
    // WS-STD-VALUE-NLUPA-OUTL
    BigDecimal standardizedPaymentAmount = amount.add(outlier);

    // IF H-HHA-TOB = 322
    if (calculationContext.isBillTypeRap()) {
      //   IF H-HHA-INIT-PAY-QRP-IND = '1' OR '3'
      if (!calculationContext.isUsablePaymentIndicator()) {
        //     COMPUTE H-HHA-PPS-STD-VALUE ROUNDED =
        //       H-HHA-PPS-STD-VALUE * 0
        // Zero times anything is always Zero. There is no need to calculate this
        standardizedPaymentAmount = BigDecimalUtils.ZERO;
      } else {
        //   COMPUTE H-HHA-PPS-STD-VALUE ROUNDED =
        //     H-HHA-PPS-STD-VALUE * .20
        standardizedPaymentAmount =
            standardizedPaymentAmount
                .multiply(HhaPricerContext.USABLE_RAP_MODIFIER)
                .setScale(2, RoundingMode.HALF_UP);
      }
    }

    paymentData.setStandardizedPayment(standardizedPaymentAmount);
  }
}
