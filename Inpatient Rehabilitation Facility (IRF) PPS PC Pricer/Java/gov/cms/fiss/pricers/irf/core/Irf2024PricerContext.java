package gov.cms.fiss.pricers.irf.core;

import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.core.tables.DataTables;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Irf2024PricerContext extends IrfPricerContext {
  public static final String CALCULATION_VERSION_2024 = "2024.2";
  private static final BigDecimal NATIONAL_LABOR_PERCENTAGE_2024 = new BigDecimal("0.74100");
  private static final BigDecimal NATIONAL_NON_LABOR_PERCENTAGE_2024 = new BigDecimal("0.25900");
  private static final BigDecimal NATIONAL_THRESHOLD_ADJUSTMENT_2024 = new BigDecimal("10423.00");
  private static final BigDecimal BUDGET_NEUTRAL_CONVERSION_AMOUNT_2024 =
      new BigDecimal("18541.00");
  private static final BigDecimal BUDGET_NEUTRAL_CONVERSION_AMOUNT2_2024 =
      new BigDecimal("18183.00");

  // SupplementalWageIndex 5% Cap and adjustment fields
  private static final BigDecimal WAGEINDEX_PERCENT_ADJUSTMENT_2024 = new BigDecimal("0.95");
  private static final BigDecimal WAGEINDEX_PERCENT_REDUCTION_CAP_2024 = new BigDecimal("-0.05");

  public Irf2024PricerContext(
      IrfClaimPricingRequest input, IrfClaimPricingResponse output, DataTables dataTables) {
    super(input, output, dataTables);
  }

  @Override
  public String getCalculationVersion() {
    return CALCULATION_VERSION_2024;
  }

  @Override
  public BigDecimal getBudgetNeutralConversionAmount() {
    return BUDGET_NEUTRAL_CONVERSION_AMOUNT_2024;
  }

  @Override
  public BigDecimal getBudgetNeutralConversionAmount2() {
    return BUDGET_NEUTRAL_CONVERSION_AMOUNT2_2024;
  }

  @Override
  public LocalDate getFiscalYearEnd() {
    return LocalDateUtils.fiscalYearEnd(LocalDate.of(2024, 1, 1));
  }

  @Override
  public LocalDate getFiscalYearStart() {
    return LocalDateUtils.fiscalYearStart(LocalDate.of(2024, 1, 1));
  }

  @Override
  public BigDecimal getNationalLaborPercentage() {
    return NATIONAL_LABOR_PERCENTAGE_2024;
  }

  @Override
  public BigDecimal getNationalNonLaborPercentage() {
    return NATIONAL_NON_LABOR_PERCENTAGE_2024;
  }

  @Override
  public BigDecimal getNationalThresholdAdjustment() {
    return NATIONAL_THRESHOLD_ADJUSTMENT_2024;
  }

  @Override
  public BigDecimal getWageIndexPercentAdjustment() {
    return WAGEINDEX_PERCENT_ADJUSTMENT_2024;
  }

  @Override
  public BigDecimal getWageIndexPercentReductionCap() {
    return WAGEINDEX_PERCENT_REDUCTION_CAP_2024;
  }
}
