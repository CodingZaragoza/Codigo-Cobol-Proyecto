package gov.cms.fiss.pricers.hha.core.rules.final_calculation;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingRequest;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingResponse;
import gov.cms.fiss.pricers.hha.api.v2.RevenueLineData;
import gov.cms.fiss.pricers.hha.api.v2.RevenuePaymentData;
import gov.cms.fiss.pricers.hha.core.HhaPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class CalculateAddOnVisit
    implements CalculationRule<HhaClaimPricingRequest, HhaClaimPricingResponse, HhaPricerContext> {
  public static final int PHYSICAL_THERAPY = 0;
  public static final int OCCUPATIONAL_THERAPY = 1;
  public static final int SPEECH_LANGUAGE_PATHOLOGY = 2;
  public static final int SKILLED_NURSING = 3;
  public static final int MEDICAL_SOCIAL_SERVICES = 4;
  public static final int HOME_HEALTH_AIDE = 5;
  /** Priority of each revenue code for receiving an add-on amount. */
  private static final Map<String, Integer> CODE_PRIORITY =
      Map.of("0420", 2, "0430", 3, "0440", 4, "0550", 1, "0560", 5, "0570", 6);
  /** Comparison method based on earliest date followed by priority. */
  private static final Comparator<RevenueLineData> COMPARE_BY_EARLIEST_DATE_AND_PRIORITY =
      (line1, line2) -> {
        final LocalDate line1Date =
            ObjectUtils.defaultIfNull(line1.getEarliestLineItemDate(), LocalDate.MAX);
        final LocalDate line2Date =
            ObjectUtils.defaultIfNull(line2.getEarliestLineItemDate(), LocalDate.MAX);

        if (line1Date.isEqual(line2Date)) {
          return NumberUtils.compare(
              CODE_PRIORITY.get(line1.getRevenueCode()), CODE_PRIORITY.get(line2.getRevenueCode()));
        }

        return line1Date.compareTo(line2Date);
      };

  @Override
  public boolean shouldExecute(HhaPricerContext calculationContext) {
    return !calculationContext.isPartialEpisodePaymentCalculated();
  }

  /**
   * Calculate HHA-REVENUE-ADD-ON-VISIT-AMT and WS-STDV-LUPA-ADDON-FAC.
   *
   * <p>Converted from {@code 1000-FINAL-PAYMENT} in the COBOL code.
   */
  @Override
  public void calculate(HhaPricerContext calculationContext) {
    final List<RevenueLineData> inputRevenueData =
        getAddOnRevenueData(calculationContext.getRevenueLines());
    final List<RevenuePaymentData> outputRevenueData = calculationContext.getRevenuePayments();

    inputRevenueData.sort(COMPARE_BY_EARLIEST_DATE_AND_PRIORITY);

    // IF REVENUE EARLIEST DATES = ALL 9'S [NULL] THEN LUPA ADD ON DOES NOT CALCULATE
    if (containsMaxDatesOnly(inputRevenueData)) {
      for (int i = 0; i < 5; i++) {
        outputRevenueData.get(i).setAddOnVisitAmount(BigDecimalUtils.ZERO);
      }
      return;
    }

    final String earliestRevenueCode = inputRevenueData.get(0).getRevenueCode();
    RevenuePaymentData outputRevenueEntry = outputRevenueData.get(SKILLED_NURSING);
    BigDecimal lupaAddOn = calculationContext.getLupaAddOnSkilledNursing();

    switch (earliestRevenueCode) {
      case "0420":
        outputRevenueEntry = outputRevenueData.get(PHYSICAL_THERAPY);
        lupaAddOn = calculationContext.getLupaAddOnPhysicalTherapy();
        break;
      case "0430":
        outputRevenueEntry = outputRevenueData.get(OCCUPATIONAL_THERAPY);
        lupaAddOn = calculationContext.getLupaAddOnOccupationalTherapy();
        break;
      case "0440":
        outputRevenueEntry = outputRevenueData.get(SPEECH_LANGUAGE_PATHOLOGY);
        lupaAddOn = calculationContext.getLupaAddOnSpeechLanguagePathology();
        break;
      case "0550":
        outputRevenueEntry = outputRevenueData.get(SKILLED_NURSING);
        lupaAddOn = calculationContext.getLupaAddOnSkilledNursing();
        break;
      case "0560":
        // This code currently is not subject to an add-on amount
        outputRevenueEntry = outputRevenueData.get(MEDICAL_SOCIAL_SERVICES);
        lupaAddOn = BigDecimal.ZERO;
        break;
      case "0570":
        // This code currently is not subject to an add-on amount
        outputRevenueEntry = outputRevenueData.get(HOME_HEALTH_AIDE);
        lupaAddOn = BigDecimal.ZERO;
        break;
      default:
        break;
    }

    final BigDecimal amount =
        outputRevenueEntry.getDollarRate().multiply(lupaAddOn).setScale(2, RoundingMode.HALF_UP);
    outputRevenueEntry.setAddOnVisitAmount(amount);
    calculationContext.setWsStdvLupaAddonFac(lupaAddOn);
  }

  /**
   * Determines if the revenue data only contains maximum earliest line date values.
   *
   * @param revenueData the data to evaluate
   * @return {@code true} if all earliest line dates have the maximum value
   */
  private boolean containsMaxDatesOnly(List<RevenueLineData> revenueData) {
    for (final RevenueLineData revenueLineData : revenueData) {
      if (revenueLineData.getEarliestLineItemDate() != null) {
        return false;
      }
    }

    return true;
  }

  /**
   * Returns the revenue lines subject to an add-on amount.
   *
   * @param revenueData input revenue data
   * @return list of revenue lines subject to an add-on amount
   */
  protected List<RevenueLineData> getAddOnRevenueData(List<RevenueLineData> revenueData) {
    return Stream.of(
            revenueData.get(PHYSICAL_THERAPY),
            revenueData.get(SPEECH_LANGUAGE_PATHOLOGY),
            revenueData.get(SKILLED_NURSING))
        .collect(Collectors.toList());
  }
}
