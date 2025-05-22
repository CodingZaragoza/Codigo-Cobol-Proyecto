package gov.cms.fiss.pricers.ltch.core;

import gov.cms.fiss.pricers.common.api.annotations.FixedValue;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.core.tables.DataTables;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Ltch2020PricerContext extends LtchPricerContext {

  public static final String CALCULATION_VERSION_2020 = "2020.2";
  private static final BigDecimal STANDARD_QUALITY_FED_RATE_2020 = new BigDecimal("42677.64");
  private static final BigDecimal STANDARD_FED_RATE_2020 = new BigDecimal("41844.90");
  private static final BigDecimal NATIONAL_LABOR_PERCENT_2020 = new BigDecimal(".66300");
  private static final BigDecimal NATIONAL_NON_LABOR_PERCENT_2020 = new BigDecimal(".33700");
  private static final BigDecimal HIGH_IPPS_NATIONAL_LABOR_SHARE_2020 = new BigDecimal("3959.10");
  private static final BigDecimal LOW_IPPS_NATIONAL_LABOR_SHARE_2020 = new BigDecimal("3593.91");
  private static final BigDecimal HIGH_IPPS_NATIONAL_NON_LABOR_SHARE_2020 =
      new BigDecimal("1837.53");
  private static final BigDecimal LOW_IPPS_NATIONAL_NON_LABOR_SHARE_2020 =
      new BigDecimal("2202.72");
  private static final BigDecimal FIXED_LOSS_AMOUNT_STANDARD_2020 =
      new BigDecimal("26778.00").setScale(2, RoundingMode.HALF_UP); // H-FIXED-LOSS-AMT-STD
  private static final BigDecimal FIXED_LOSS_AMOUNT_SITE_NEUTRAL_2020 =
      new BigDecimal("26552.00").setScale(2, RoundingMode.HALF_UP); // H-FIXED-LOSS-AMT-SNT
  private static final BigDecimal OPERATING_DSH_REDUCTION_FACTOR_2020 =
      new BigDecimal("0.7536").setScale(4, RoundingMode.HALF_UP); // H-OPER-DSH-REDUCTION-FACTOR
  private static final BigDecimal IPPS_CAPITAL_STANDARD_FED_RATE_2020 =
      new BigDecimal("462.33").setScale(2, RoundingMode.HALF_UP); // H-IPPS-CAPI-STD-FED-RATE

  public Ltch2020PricerContext(
      LtchClaimPricingRequest input, LtchClaimPricingResponse output, DataTables dataTables) {
    super(input, output, dataTables);
    this.calculationVersion = CALCULATION_VERSION_2020;
  }

  @Override
  public @FixedValue BigDecimal getStandardQualityFedRate() {
    return STANDARD_QUALITY_FED_RATE_2020;
  }

  @Override
  public @FixedValue BigDecimal getStandardFedRate() {
    return STANDARD_FED_RATE_2020;
  }

  @Override
  public @FixedValue BigDecimal getNationalLaborPercent() {
    return NATIONAL_LABOR_PERCENT_2020;
  }

  @Override
  public @FixedValue BigDecimal getNationalNonLaborPercent() {
    return NATIONAL_NON_LABOR_PERCENT_2020;
  }

  @Override
  public @FixedValue BigDecimal getHighIppsNationalLaborShare() {
    return HIGH_IPPS_NATIONAL_LABOR_SHARE_2020;
  }

  @Override
  public @FixedValue BigDecimal getLowIppsNationalLaborShare() {
    return LOW_IPPS_NATIONAL_LABOR_SHARE_2020;
  }

  @Override
  public @FixedValue BigDecimal getHighIppsNationalNonLaborShare() {
    return HIGH_IPPS_NATIONAL_NON_LABOR_SHARE_2020;
  }

  @Override
  public @FixedValue BigDecimal getLowIppsNationalNonLaborShare() {
    return LOW_IPPS_NATIONAL_NON_LABOR_SHARE_2020;
  }

  @Override
  public @FixedValue BigDecimal getFixedLossAmountStandard() {
    return FIXED_LOSS_AMOUNT_STANDARD_2020;
  }

  @Override
  public @FixedValue BigDecimal getFixedLossAmountSiteNeutral() {
    return FIXED_LOSS_AMOUNT_SITE_NEUTRAL_2020;
  }

  @Override
  public @FixedValue BigDecimal getOperatingDshReductionFactor() {
    return OPERATING_DSH_REDUCTION_FACTOR_2020;
  }

  @Override
  public @FixedValue BigDecimal getIppsCapitalStandardFedRate() {
    return IPPS_CAPITAL_STANDARD_FED_RATE_2020;
  }
}
