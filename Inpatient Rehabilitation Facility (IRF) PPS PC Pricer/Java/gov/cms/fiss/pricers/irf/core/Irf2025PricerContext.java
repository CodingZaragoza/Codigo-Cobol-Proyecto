package gov.cms.fiss.pricers.irf.core;

import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.core.tables.DataTables;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Irf2025PricerContext extends IrfPricerContext {
  public static final String CALCULATION_VERSION_2025 = "2025.0";
  private static final BigDecimal NATIONAL_LABOR_PERCENTAGE_2025 = new BigDecimal("0.74400");
  private static final BigDecimal NATIONAL_NON_LABOR_PERCENTAGE_2025 = new BigDecimal("0.25600");
  private static final BigDecimal NATIONAL_THRESHOLD_ADJUSTMENT_2025 = new BigDecimal("12043.00");
  private static final BigDecimal BUDGET_NEUTRAL_CONVERSION_AMOUNT_2025 =
      new BigDecimal("18907.00");
  private static final BigDecimal BUDGET_NEUTRAL_CONVERSION_AMOUNT2_2025 =
      new BigDecimal("18539.00");

  // SupplementalWageIndex 5% Cap and adjustment fields
  private static final BigDecimal WAGEINDEX_PERCENT_ADJUSTMENT_2025 = new BigDecimal("0.95");
  private static final BigDecimal WAGEINDEX_PERCENT_REDUCTION_CAP_2025 = new BigDecimal("-0.05");

  // Rural to urban transition modified rate for 3 year period, 1st year (fy2025) rate is 1.0993
  private static final BigDecimal TRANSITION_RURAL_ADJUSTMENT_2025 = new BigDecimal("1.0993");

  public Irf2025PricerContext(
      IrfClaimPricingRequest input, IrfClaimPricingResponse output, DataTables dataTables) {
    super(input, output, dataTables);
  }

  @Override
  public String getCalculationVersion() {
    return CALCULATION_VERSION_2025;
  }

  @Override
  public BigDecimal getBudgetNeutralConversionAmount() {
    return BUDGET_NEUTRAL_CONVERSION_AMOUNT_2025;
  }

  @Override
  public BigDecimal getBudgetNeutralConversionAmount2() {
    return BUDGET_NEUTRAL_CONVERSION_AMOUNT2_2025;
  }

  @Override
  public LocalDate getFiscalYearEnd() {
    return LocalDateUtils.fiscalYearEnd(LocalDate.of(2025, 1, 1));
  }

  @Override
  public LocalDate getFiscalYearStart() {
    return LocalDateUtils.fiscalYearStart(LocalDate.of(2025, 1, 1));
  }

  @Override
  public BigDecimal getNationalLaborPercentage() {
    return NATIONAL_LABOR_PERCENTAGE_2025;
  }

  @Override
  public BigDecimal getNationalNonLaborPercentage() {
    return NATIONAL_NON_LABOR_PERCENTAGE_2025;
  }

  @Override
  public BigDecimal getNationalThresholdAdjustment() {
    return NATIONAL_THRESHOLD_ADJUSTMENT_2025;
  }

  @Override
  public BigDecimal getWageIndexPercentAdjustment() {
    return WAGEINDEX_PERCENT_ADJUSTMENT_2025;
  }

  @Override
  public BigDecimal getWageIndexPercentReductionCap() {
    return WAGEINDEX_PERCENT_REDUCTION_CAP_2025;
  }

  @Override
  public BigDecimal getTransitionRuralAdjustment() {
    return TRANSITION_RURAL_ADJUSTMENT_2025;
  }
}
