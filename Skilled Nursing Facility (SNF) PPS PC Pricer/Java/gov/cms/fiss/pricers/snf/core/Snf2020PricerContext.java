package gov.cms.fiss.pricers.snf.core;

import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingRequest;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingResponse;
import gov.cms.fiss.pricers.snf.core.tables.DataTables;
import java.math.BigDecimal;

public class Snf2020PricerContext extends SnfPricerContext {

  public static final String CALCULATION_VERSION_2020 = "2020.6";
  private static final BigDecimal URBAN_NCM_RATE_2020 = new BigDecimal("94.84");
  private static final BigDecimal URBAN_QUALITY_ADJUSTED_NCM_RATE_2020 = new BigDecimal("92.99");
  private static final BigDecimal RURAL_NCM_RATE_2020 = new BigDecimal("96.59");
  private static final BigDecimal RURAL_QUALITY_ADJUSTED_NCM_RATE_2020 = new BigDecimal("94.71");
  private static final BigDecimal LABOR_RATE_2020 = new BigDecimal("0.7090");
  private static final BigDecimal NON_LABOR_RATE_2020 = new BigDecimal("0.2910");

  public Snf2020PricerContext(
      SnfClaimPricingRequest input, SnfClaimPricingResponse output, DataTables dataTables) {
    super(input, output, dataTables);
  }

  @Override
  public String getCalculationVersion() {
    return CALCULATION_VERSION_2020;
  }

  @Override
  public BigDecimal getWageIndexDecreaseCap() {
    return null;
  }

  @Override
  public BigDecimal getUrbanNonCaseMixRate() {
    return URBAN_NCM_RATE_2020;
  }

  @Override
  public BigDecimal getUrbanQualityAdjustedNonCaseMixRate() {
    return URBAN_QUALITY_ADJUSTED_NCM_RATE_2020;
  }

  @Override
  public BigDecimal getRuralNonCaseMixRate() {
    return RURAL_NCM_RATE_2020;
  }

  @Override
  public BigDecimal getRuralQualityAdjustedNonCaseMixRate() {
    return RURAL_QUALITY_ADJUSTED_NCM_RATE_2020;
  }

  @Override
  public BigDecimal getLaborRate() {
    return LABOR_RATE_2020;
  }

  @Override
  public BigDecimal getNonLaborRate() {
    return NON_LABOR_RATE_2020;
  }
}
