package gov.cms.fiss.pricers.ipf.core;

import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.tables.DataTables;
import java.math.BigDecimal;

public class Ipf2024PricerContext extends IpfPricerContext {
  public static final String CALCULATION_VERSION_2024 = "2024.1";

  private static final BigDecimal QUALITY_BUDGET_RATE_2024 = new BigDecimal("0895.63");
  private static final BigDecimal QUALITY_ECT_RATE_2024 = new BigDecimal("0385.58");
  private static final BigDecimal BUDGET_RATE_2024 = new BigDecimal("0878.29");
  private static final BigDecimal ECT_RATE_2024 = new BigDecimal("0378.12");
  private static final BigDecimal OUTLIER_THRESHOLD_2024 = new BigDecimal("33470.00");
  private static final BigDecimal LABOR_SHARE_2024 = new BigDecimal("0.78700");
  private static final BigDecimal NONLABOR_SHARE_2024 = new BigDecimal("0.21300");

  public Ipf2024PricerContext(
      IpfClaimPricingRequest input, IpfClaimPricingResponse output, DataTables dataTables) {
    super(input, output, dataTables);
  }

  @Override
  public String getCalculationVersion() {
    return CALCULATION_VERSION_2024;
  }

  @Override
  public BigDecimal getHighQualityBudgetRate() {
    return QUALITY_BUDGET_RATE_2024;
  }

  @Override
  public BigDecimal getHighQualityEctRate() {
    return QUALITY_ECT_RATE_2024;
  }

  @Override
  public BigDecimal getLowQualityBudgetRate() {
    return BUDGET_RATE_2024;
  }

  @Override
  public BigDecimal getLowQualityEctRate() {
    return ECT_RATE_2024;
  }

  @Override
  public BigDecimal getOutlierThreshold() {
    return OUTLIER_THRESHOLD_2024;
  }

  @Override
  public BigDecimal getLaborShare() {
    return LABOR_SHARE_2024;
  }

  @Override
  public BigDecimal getNonLaborShare() {
    return NONLABOR_SHARE_2024;
  }
}
