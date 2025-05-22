package gov.cms.fiss.pricers.irf.core;

import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.core.tables.DataTables;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Irf2022PricerContext extends IrfPricerContext {
  public static final String CALCULATION_VERSION_2022 = "2022.0";
  private static final BigDecimal NATIONAL_LABOR_PERCENTAGE_2022 = new BigDecimal("0.72900");
  private static final BigDecimal NATIONAL_NON_LABOR_PERCENTAGE_2022 = new BigDecimal("0.27100");
  private static final BigDecimal NATIONAL_THRESHOLD_ADJUSTMENT_2022 = new BigDecimal("9491.00");
  private static final BigDecimal BUDGET_NEUTRAL_CONVERSION_AMOUNT_2022 =
      new BigDecimal("17240.00");
  private static final BigDecimal BUDGET_NEUTRAL_CONVERSION_AMOUNT2_2022 =
      new BigDecimal("16901.00");

  public Irf2022PricerContext(
      IrfClaimPricingRequest input, IrfClaimPricingResponse output, DataTables dataTables) {
    super(input, output, dataTables);
  }

  @Override
  public String getCalculationVersion() {
    return CALCULATION_VERSION_2022;
  }

  @Override
  public BigDecimal getBudgetNeutralConversionAmount() {
    return BUDGET_NEUTRAL_CONVERSION_AMOUNT_2022;
  }

  @Override
  public BigDecimal getBudgetNeutralConversionAmount2() {
    return BUDGET_NEUTRAL_CONVERSION_AMOUNT2_2022;
  }

  @Override
  public LocalDate getFiscalYearEnd() {
    return LocalDateUtils.fiscalYearEnd(LocalDate.of(2022, 1, 1));
  }

  @Override
  public LocalDate getFiscalYearStart() {
    return LocalDateUtils.fiscalYearStart(LocalDate.of(2022, 1, 1));
  }

  @Override
  public BigDecimal getNationalLaborPercentage() {
    return NATIONAL_LABOR_PERCENTAGE_2022;
  }

  @Override
  public BigDecimal getNationalNonLaborPercentage() {
    return NATIONAL_NON_LABOR_PERCENTAGE_2022;
  }

  @Override
  public BigDecimal getNationalThresholdAdjustment() {
    return NATIONAL_THRESHOLD_ADJUSTMENT_2022;
  }
}
