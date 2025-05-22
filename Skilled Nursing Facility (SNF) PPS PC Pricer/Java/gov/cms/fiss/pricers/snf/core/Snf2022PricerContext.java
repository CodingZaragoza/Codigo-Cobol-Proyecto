package gov.cms.fiss.pricers.snf.core;

import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingRequest;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingResponse;
import gov.cms.fiss.pricers.snf.core.tables.DataTables;
import java.math.BigDecimal;

public class Snf2022PricerContext extends SnfPricerContext {

  public static final String CALCULATION_VERSION_2022 = "2022.1";
  private static final BigDecimal URBAN_NCM_RATE_2022 = new BigDecimal("98.07");
  private static final BigDecimal URBAN_QUALITY_ADJUSTED_NCM_RATE_2022 = new BigDecimal("96.13");
  private static final BigDecimal RURAL_NCM_RATE_2022 = new BigDecimal("99.88");
  private static final BigDecimal RURAL_QUALITY_ADJUSTED_NCM_RATE_2022 = new BigDecimal("97.91");
  private static final BigDecimal LABOR_RATE_2022 = new BigDecimal("0.7040");
  private static final BigDecimal NON_LABOR_RATE_2022 = new BigDecimal("0.2960");

  public Snf2022PricerContext(
      SnfClaimPricingRequest input, SnfClaimPricingResponse output, DataTables dataTables) {
    super(input, output, dataTables);
  }

  @Override
  public String getCalculationVersion() {
    return CALCULATION_VERSION_2022;
  }

  @Override
  public BigDecimal getWageIndexDecreaseCap() {
    return null;
  }

  @Override
  public BigDecimal getUrbanNonCaseMixRate() {
    return URBAN_NCM_RATE_2022;
  }

  @Override
  public BigDecimal getUrbanQualityAdjustedNonCaseMixRate() {
    return URBAN_QUALITY_ADJUSTED_NCM_RATE_2022;
  }

  @Override
  public BigDecimal getRuralNonCaseMixRate() {
    return RURAL_NCM_RATE_2022;
  }

  @Override
  public BigDecimal getRuralQualityAdjustedNonCaseMixRate() {
    return RURAL_QUALITY_ADJUSTED_NCM_RATE_2022;
  }

  @Override
  public BigDecimal getLaborRate() {
    return LABOR_RATE_2022;
  }

  @Override
  public BigDecimal getNonLaborRate() {
    return NON_LABOR_RATE_2022;
  }
}
