package gov.cms.fiss.pricers.irf.core;

import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.core.tables.DataTables;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Irf2016PricerContext extends IrfPricerContext {
  public static final String CALCULATION_VERSION_2016 = "2016.0";
  private static final BigDecimal NATIONAL_LABOR_PERCENTAGE_2016 = new BigDecimal("0.71000");
  private static final BigDecimal NATIONAL_NON_LABOR_PERCENTAGE_2016 = new BigDecimal("0.29000");
  private static final BigDecimal NATIONAL_THRESHOLD_ADJUSTMENT_2016 = new BigDecimal("8658.00");
  private static final BigDecimal BUDGET_NEUTRAL_CONVERSION_AMOUNT_2016 =
      new BigDecimal("15478.00");
  private static final BigDecimal BUDGET_NEUTRAL_CONVERSION_AMOUNT2_2016 =
      new BigDecimal("15174.00");
  private static final BigDecimal TRANSITION_RURAL_ADJUSTMENT_2016 = new BigDecimal("1.0993");

  public Irf2016PricerContext(
      IrfClaimPricingRequest input, IrfClaimPricingResponse output, DataTables dataTables) {
    super(input, output, dataTables);
  }

  @Override
  public String getCalculationVersion() {
    return CALCULATION_VERSION_2016;
  }

  @Override
  public BigDecimal getBudgetNeutralConversionAmount() {
    return BUDGET_NEUTRAL_CONVERSION_AMOUNT_2016;
  }

  @Override
  public BigDecimal getBudgetNeutralConversionAmount2() {
    return BUDGET_NEUTRAL_CONVERSION_AMOUNT2_2016;
  }

  @Override
  public LocalDate getFiscalYearEnd() {
    return LocalDateUtils.fiscalYearEnd(LocalDate.of(2016, 1, 1));
  }

  @Override
  public LocalDate getFiscalYearStart() {
    return LocalDateUtils.fiscalYearStart(LocalDate.of(2016, 1, 1));
  }

  @Override
  public BigDecimal getNationalLaborPercentage() {
    return NATIONAL_LABOR_PERCENTAGE_2016;
  }

  @Override
  public BigDecimal getNationalNonLaborPercentage() {
    return NATIONAL_NON_LABOR_PERCENTAGE_2016;
  }

  @Override
  public BigDecimal getNationalThresholdAdjustment() {
    return NATIONAL_THRESHOLD_ADJUSTMENT_2016;
  }

  @Override
  public BigDecimal getTransitionRuralAdjustment() {
    return TRANSITION_RURAL_ADJUSTMENT_2016;
  }
}
