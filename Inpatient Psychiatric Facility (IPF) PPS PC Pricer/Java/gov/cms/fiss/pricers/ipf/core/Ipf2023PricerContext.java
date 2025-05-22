package gov.cms.fiss.pricers.ipf.core;

import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.tables.DataTables;
import java.math.BigDecimal;

public class Ipf2023PricerContext extends IpfPricerContext {
  public static final String CALCULATION_VERSION_2023 = "2023.1";

  private static final BigDecimal QUALITY_BUDGET_RATE_2023 = new BigDecimal("0865.63");
  private static final BigDecimal QUALITY_ECT_RATE_2023 = new BigDecimal("0372.67");
  private static final BigDecimal BUDGET_RATE_2023 = new BigDecimal("0848.95");
  private static final BigDecimal ECT_RATE_2023 = new BigDecimal("0365.49");
  private static final BigDecimal OUTLIER_THRESHOLD_2023 = new BigDecimal("24630.00");
  private static final BigDecimal LABOR_SHARE_2023 = new BigDecimal("0.77400");
  private static final BigDecimal NONLABOR_SHARE_2023 = new BigDecimal("0.22600");

  public Ipf2023PricerContext(
      IpfClaimPricingRequest input, IpfClaimPricingResponse output, DataTables dataTables) {
    super(input, output, dataTables);
  }

  @Override
  public String getCalculationVersion() {
    return CALCULATION_VERSION_2023;
  }

  @Override
  public BigDecimal getHighQualityBudgetRate() {
    return QUALITY_BUDGET_RATE_2023;
  }

  @Override
  public BigDecimal getHighQualityEctRate() {
    return QUALITY_ECT_RATE_2023;
  }

  @Override
  public BigDecimal getLowQualityBudgetRate() {
    return BUDGET_RATE_2023;
  }

  @Override
  public BigDecimal getLowQualityEctRate() {
    return ECT_RATE_2023;
  }

  @Override
  public BigDecimal getOutlierThreshold() {
    return OUTLIER_THRESHOLD_2023;
  }

  @Override
  public BigDecimal getLaborShare() {
    return LABOR_SHARE_2023;
  }

  @Override
  public BigDecimal getNonLaborShare() {
    return NONLABOR_SHARE_2023;
  }
}
