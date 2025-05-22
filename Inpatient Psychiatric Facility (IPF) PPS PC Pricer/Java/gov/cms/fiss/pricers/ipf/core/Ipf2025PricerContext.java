package gov.cms.fiss.pricers.ipf.core;

import gov.cms.fiss.pricers.common.api.annotations.FixedValue;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.tables.DataTables;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Ipf2025PricerContext extends IpfPricerContext {
  public static final String CALCULATION_VERSION_2025 = "2025.1";

  private static final BigDecimal QUALITY_BUDGET_RATE_2025 = new BigDecimal("0876.53");
  private static final BigDecimal QUALITY_ECT_RATE_2025 = new BigDecimal("0661.52");
  private static final BigDecimal BUDGET_RATE_2025 = new BigDecimal("0859.48");
  private static final BigDecimal ECT_RATE_2025 = new BigDecimal("0648.65");
  private static final BigDecimal OUTLIER_THRESHOLD_2025 = new BigDecimal("38110.00");
  private static final BigDecimal LABOR_SHARE_2025 = new BigDecimal("0.78800");
  private static final BigDecimal NONLABOR_SHARE_2025 = new BigDecimal("0.21200");
  private static final BigDecimal TRANSITIONAL_RURAL_ADJUSTMENT_2025 = new BigDecimal("1.113");
  private static final BigDecimal DEFAULT_EMERGENCY_ADJUSTMENT_2025 = new BigDecimal("1.28");
  private static final BigDecimal TEMPORARY_RELIEF_EMERGENCY_ADJUSTMENT_2025 =
      new BigDecimal("1.54");
  private static final BigDecimal SOURCE_OF_ADMISSION_EMERGENCY_ADJUSTMENT_2025 =
      new BigDecimal("1.28");
  private @FixedValue BigDecimal day1Value2025 = new BigDecimal("0.00");
  private static final List<BigDecimal> dayValues_2025 =
      List.of(
          new BigDecimal("1.20"),
          new BigDecimal("1.15"),
          new BigDecimal("1.12"),
          new BigDecimal("1.08"),
          new BigDecimal("1.06"),
          new BigDecimal("1.03"),
          new BigDecimal("1.02"),
          new BigDecimal("1.01"),
          new BigDecimal("1.00"),
          new BigDecimal("1.00"));

  private static final Map<String, BigDecimal> diagnosticCodeAdjustment_2025 =
      Map.ofEntries(
          Map.entry("1", new BigDecimal("1.04")),
          Map.entry("2", new BigDecimal("1.00")),
          Map.entry("3", new BigDecimal("1.09")),
          Map.entry("4", new BigDecimal("1.06")),
          Map.entry("5", new BigDecimal("1.08")),
          Map.entry("6", new BigDecimal("1.44")),
          Map.entry("7", new BigDecimal("1.05")),
          Map.entry("8", new BigDecimal("1.17")),
          Map.entry("9", new BigDecimal("1.09")),
          Map.entry("10", new BigDecimal("1.00")),
          Map.entry("11", new BigDecimal("1.00")),
          Map.entry("12", new BigDecimal("1.04")),
          Map.entry("13", new BigDecimal("1.12")),
          Map.entry("14", new BigDecimal("1.09")),
          Map.entry("15", new BigDecimal("1.07")),
          Map.entry("16", new BigDecimal("1.05")),
          Map.entry("17", new BigDecimal("1.16")),
          Map.entry("18", new BigDecimal("1.07")));

  private static final NavigableMap<Integer, BigDecimal> ageAdjustmentMap_2025 =
      new TreeMap<>(
          Map.of(
              0,
              new BigDecimal("1.00"),
              45,
              new BigDecimal("1.02"),
              55,
              new BigDecimal("1.05"),
              60,
              new BigDecimal("1.06"),
              65,
              new BigDecimal("1.09"),
              70,
              new BigDecimal("1.11"),
              80,
              new BigDecimal("1.13")));

  public Ipf2025PricerContext(
      IpfClaimPricingRequest input, IpfClaimPricingResponse output, DataTables dataTables) {
    super(input, output, dataTables);
  }

  @Override
  public String getCalculationVersion() {
    return CALCULATION_VERSION_2025;
  }

  @Override
  public BigDecimal getHighQualityBudgetRate() {
    return QUALITY_BUDGET_RATE_2025;
  }

  @Override
  public BigDecimal getHighQualityEctRate() {
    return QUALITY_ECT_RATE_2025;
  }

  @Override
  public BigDecimal getLowQualityBudgetRate() {
    return BUDGET_RATE_2025;
  }

  @Override
  public BigDecimal getLowQualityEctRate() {
    return ECT_RATE_2025;
  }

  @Override
  public BigDecimal getOutlierThreshold() {
    return OUTLIER_THRESHOLD_2025;
  }

  @Override
  public BigDecimal getLaborShare() {
    return LABOR_SHARE_2025;
  }

  @Override
  public BigDecimal getNonLaborShare() {
    return NONLABOR_SHARE_2025;
  }

  @Override
  public @FixedValue BigDecimal getTransitionalRuralAdjustment() {
    return TRANSITIONAL_RURAL_ADJUSTMENT_2025;
  }

  @Override
  public @FixedValue BigDecimal getDefaultEmergencyAdjustment() {
    return DEFAULT_EMERGENCY_ADJUSTMENT_2025;
  }

  @Override
  public @FixedValue BigDecimal getTemporaryReliefEmergencyAdjustment() {
    return TEMPORARY_RELIEF_EMERGENCY_ADJUSTMENT_2025;
  }

  @Override
  public @FixedValue BigDecimal getSourceOfAdmissionEmergencyAdjustment() {
    return SOURCE_OF_ADMISSION_EMERGENCY_ADJUSTMENT_2025;
  }

  @Override
  public void setDay1Value(BigDecimal value) {
    this.day1Value2025 = value;
  }

  @Override
  public @FixedValue BigDecimal getDayValue(int day) {
    // day == 0 is equivalent to day 1
    if (day == 0) {
      return day1Value2025;
    } else {
      return dayValues_2025.get(day - 1);
    }
  }

  @Override
  public @FixedValue BigDecimal getDiagnosticCodeAdjustment(String category) {
    return diagnosticCodeAdjustment_2025.get(category);
  }

  @Override
  public @FixedValue BigDecimal getAgeAdjustment(int age) {
    return ageAdjustmentMap_2025.floorEntry(age).getValue();
  }
}
