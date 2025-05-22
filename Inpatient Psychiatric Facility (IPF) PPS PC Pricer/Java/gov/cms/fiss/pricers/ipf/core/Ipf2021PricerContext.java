package gov.cms.fiss.pricers.ipf.core;

import gov.cms.fiss.pricers.common.api.annotations.FixedValue;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.tables.DataTables;
import java.math.BigDecimal;

public class Ipf2021PricerContext extends IpfPricerContext {
  public static final String CALCULATION_VERSION_2021 = "2021.0";

  private static final BigDecimal QUALITY_BUDGET_RATE_2021 = new BigDecimal("0815.22");
  private static final BigDecimal QUALITY_ECT_RATE_2021 = new BigDecimal("0350.97");
  private static final BigDecimal BUDGET_RATE_2021 = new BigDecimal("0799.27");
  private static final BigDecimal ECT_RATE_2021 = new BigDecimal("0344.10");
  private static final BigDecimal OUTLIER_THRESHOLD_2021 = new BigDecimal("14630.00");
  private static final BigDecimal LABOR_SHARE_2021 = new BigDecimal("0.77300");
  private static final BigDecimal NONLABOR_SHARE_2021 = new BigDecimal("0.22700");

  public Ipf2021PricerContext(
      IpfClaimPricingRequest input, IpfClaimPricingResponse output, DataTables dataTables) {
    super(input, output, dataTables);
  }

  @Override
  public String getCalculationVersion() {
    return CALCULATION_VERSION_2021;
  }

  @Override
  public @FixedValue BigDecimal getHighQualityBudgetRate() {
    return QUALITY_BUDGET_RATE_2021;
  }

  @Override
  public @FixedValue BigDecimal getHighQualityEctRate() {
    return QUALITY_ECT_RATE_2021;
  }

  @Override
  public @FixedValue BigDecimal getLowQualityBudgetRate() {
    return BUDGET_RATE_2021;
  }

  @Override
  public @FixedValue BigDecimal getLowQualityEctRate() {
    return ECT_RATE_2021;
  }

  @Override
  public @FixedValue BigDecimal getOutlierThreshold() {
    return OUTLIER_THRESHOLD_2021;
  }

  @Override
  public @FixedValue BigDecimal getLaborShare() {
    return LABOR_SHARE_2021;
  }

  @Override
  public @FixedValue BigDecimal getNonLaborShare() {
    return NONLABOR_SHARE_2021;
  }
}
