package gov.cms.fiss.pricers.irf.core;

import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.core.tables.DataTables;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Irf2020PricerContext extends IrfPricerContext {
  public static final String CALCULATION_VERSION_2020 = "2020.1";
  private static final BigDecimal NATIONAL_LABOR_PERCENTAGE_2020 = new BigDecimal("0.72700");
  private static final BigDecimal NATIONAL_NON_LABOR_PERCENTAGE_2020 = new BigDecimal("0.27300");
  private static final BigDecimal NATIONAL_THRESHOLD_ADJUSTMENT_2020 = new BigDecimal("9300.00");
  private static final BigDecimal BUDGET_NEUTRAL_CONVERSION_AMOUNT_2020 =
      new BigDecimal("16489.00");
  private static final BigDecimal BUDGET_NEUTRAL_CONVERSION_AMOUNT2_2020 =
      new BigDecimal("16167.00");

  public Irf2020PricerContext(
      IrfClaimPricingRequest input, IrfClaimPricingResponse output, DataTables dataTables) {
    super(input, output, dataTables);
  }

  @Override
  public String getCalculationVersion() {
    return CALCULATION_VERSION_2020;
  }

  @Override
  public BigDecimal getBudgetNeutralConversionAmount() {
    return BUDGET_NEUTRAL_CONVERSION_AMOUNT_2020;
  }

  @Override
  public BigDecimal getBudgetNeutralConversionAmount2() {
    return BUDGET_NEUTRAL_CONVERSION_AMOUNT2_2020;
  }

  @Override
  public LocalDate getFiscalYearEnd() {
    return LocalDateUtils.fiscalYearEnd(LocalDate.of(2020, 1, 1));
  }

  @Override
  public LocalDate getFiscalYearStart() {
    return LocalDateUtils.fiscalYearStart(LocalDate.of(2020, 1, 1));
  }

  @Override
  public BigDecimal getNationalLaborPercentage() {
    return NATIONAL_LABOR_PERCENTAGE_2020;
  }

  @Override
  public BigDecimal getNationalNonLaborPercentage() {
    return NATIONAL_NON_LABOR_PERCENTAGE_2020;
  }

  @Override
  public BigDecimal getNationalThresholdAdjustment() {
    return NATIONAL_THRESHOLD_ADJUSTMENT_2020;
  }
}
