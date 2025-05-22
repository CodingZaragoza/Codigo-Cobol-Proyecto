package gov.cms.fiss.pricers.ipf.core;

import gov.cms.fiss.pricers.common.api.annotations.FixedValue;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.tables.DataTables;
import java.math.BigDecimal;

public class Ipf2022PricerContext extends IpfPricerContext {
  public static final String CALCULATION_VERSION_2022 = "2022.1";

  private static final BigDecimal QUALITY_BUDGET_RATE_2022 = new BigDecimal("0832.94");
  private static final BigDecimal QUALITY_ECT_RATE_2022 = new BigDecimal("0358.60");
  private static final BigDecimal BUDGET_RATE_2022 = new BigDecimal("0816.61");
  private static final BigDecimal ECT_RATE_2022 = new BigDecimal("0351.57");
  private static final BigDecimal OUTLIER_THRESHOLD_2022 = new BigDecimal("16040.00");
  private static final BigDecimal LABOR_SHARE_2022 = new BigDecimal("0.77200");
  private static final BigDecimal NONLABOR_SHARE_2022 = new BigDecimal("0.22800");

  public Ipf2022PricerContext(
      IpfClaimPricingRequest input, IpfClaimPricingResponse output, DataTables dataTables) {
    super(input, output, dataTables);
  }

  @Override
  public String getCalculationVersion() {
    return CALCULATION_VERSION_2022;
  }

  @Override
  public @FixedValue BigDecimal getHighQualityBudgetRate() {
    return QUALITY_BUDGET_RATE_2022;
  }

  @Override
  public @FixedValue BigDecimal getHighQualityEctRate() {
    return QUALITY_ECT_RATE_2022;
  }

  @Override
  public @FixedValue BigDecimal getLowQualityBudgetRate() {
    return BUDGET_RATE_2022;
  }

  @Override
  public @FixedValue BigDecimal getLowQualityEctRate() {
    return ECT_RATE_2022;
  }

  @Override
  public @FixedValue BigDecimal getOutlierThreshold() {
    return OUTLIER_THRESHOLD_2022;
  }

  @Override
  public @FixedValue BigDecimal getLaborShare() {
    return LABOR_SHARE_2022;
  }

  @Override
  public @FixedValue BigDecimal getNonLaborShare() {
    return NONLABOR_SHARE_2022;
  }
}
