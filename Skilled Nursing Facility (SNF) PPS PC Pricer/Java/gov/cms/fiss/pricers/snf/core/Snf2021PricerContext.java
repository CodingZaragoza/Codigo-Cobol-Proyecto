package gov.cms.fiss.pricers.snf.core;

import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingRequest;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingResponse;
import gov.cms.fiss.pricers.snf.core.tables.DataTables;
import java.math.BigDecimal;

public class Snf2021PricerContext extends SnfPricerContext {

  public static final String CALCULATION_VERSION_2021 = "2021.1";
  private static final BigDecimal WAGE_INDEX_DECREASE_CAP_2021 = new BigDecimal("-0.05");
  private static final BigDecimal URBAN_NCM_RATE_2021 = new BigDecimal("96.85");
  private static final BigDecimal URBAN_QUALITY_ADJUSTED_NCM_RATE_2021 = new BigDecimal("94.96");
  private static final BigDecimal RURAL_NCM_RATE_2021 = new BigDecimal("98.64");
  private static final BigDecimal RURAL_QUALITY_ADJUSTED_NCM_RATE_2021 = new BigDecimal("96.72");
  private static final BigDecimal LABOR_RATE_2021 = new BigDecimal("0.7130");
  private static final BigDecimal NON_LABOR_RATE_2021 = new BigDecimal("0.2870");

  public Snf2021PricerContext(
      SnfClaimPricingRequest input, SnfClaimPricingResponse output, DataTables dataTables) {
    super(input, output, dataTables);
  }

  @Override
  public String getCalculationVersion() {
    return CALCULATION_VERSION_2021;
  }

  @Override
  public BigDecimal getWageIndexDecreaseCap() {
    return WAGE_INDEX_DECREASE_CAP_2021;
  }

  @Override
  public BigDecimal getUrbanNonCaseMixRate() {
    return URBAN_NCM_RATE_2021;
  }

  @Override
  public BigDecimal getUrbanQualityAdjustedNonCaseMixRate() {
    return URBAN_QUALITY_ADJUSTED_NCM_RATE_2021;
  }

  @Override
  public BigDecimal getRuralNonCaseMixRate() {
    return RURAL_NCM_RATE_2021;
  }

  @Override
  public BigDecimal getRuralQualityAdjustedNonCaseMixRate() {
    return RURAL_QUALITY_ADJUSTED_NCM_RATE_2021;
  }

  @Override
  public BigDecimal getLaborRate() {
    return LABOR_RATE_2021;
  }

  @Override
  public BigDecimal getNonLaborRate() {
    return NON_LABOR_RATE_2021;
  }
}
