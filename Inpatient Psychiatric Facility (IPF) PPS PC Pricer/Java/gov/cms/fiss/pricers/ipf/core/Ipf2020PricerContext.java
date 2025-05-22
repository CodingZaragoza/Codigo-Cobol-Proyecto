package gov.cms.fiss.pricers.ipf.core;

import gov.cms.fiss.pricers.common.api.annotations.FixedValue;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.tables.DataTables;
import java.math.BigDecimal;

public class Ipf2020PricerContext extends IpfPricerContext {
  public static final String CALCULATION_VERSION_2020 = "2020.2";
  private static final BigDecimal QUALITY_BUDGET_RATE_2020 = new BigDecimal("0798.55");
  private static final BigDecimal QUALITY_ECT_RATE_2020 = new BigDecimal("0343.79");
  private static final BigDecimal BUDGET_RATE_2020 = new BigDecimal("0782.85");
  private static final BigDecimal ECT_RATE_2020 = new BigDecimal("0337.03");
  private static final BigDecimal OUTLIER_THRESHOLD_2020 = new BigDecimal("14960.00");
  private static final BigDecimal LABOR_SHARE_2020 = new BigDecimal("0.76900");
  private static final BigDecimal NONLABOR_SHARE_2020 = new BigDecimal("0.23100");

  public Ipf2020PricerContext(
      IpfClaimPricingRequest input, IpfClaimPricingResponse output, DataTables dataTables) {
    super(input, output, dataTables);
  }

  @Override
  public String getCalculationVersion() {
    return CALCULATION_VERSION_2020;
  }

  @Override
  public @FixedValue BigDecimal getHighQualityBudgetRate() {
    return QUALITY_BUDGET_RATE_2020;
  }

  @Override
  public @FixedValue BigDecimal getHighQualityEctRate() {
    return QUALITY_ECT_RATE_2020;
  }

  @Override
  public @FixedValue BigDecimal getLowQualityBudgetRate() {
    return BUDGET_RATE_2020;
  }

  @Override
  public @FixedValue BigDecimal getLowQualityEctRate() {
    return ECT_RATE_2020;
  }

  @Override
  public @FixedValue BigDecimal getOutlierThreshold() {
    return OUTLIER_THRESHOLD_2020;
  }

  @Override
  public @FixedValue BigDecimal getLaborShare() {
    return LABOR_SHARE_2020;
  }

  @Override
  public @FixedValue BigDecimal getNonLaborShare() {
    return NONLABOR_SHARE_2020;
  }
}
