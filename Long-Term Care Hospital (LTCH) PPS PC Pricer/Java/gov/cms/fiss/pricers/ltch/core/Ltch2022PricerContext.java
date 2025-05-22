package gov.cms.fiss.pricers.ltch.core;

import gov.cms.fiss.pricers.common.api.annotations.FixedValue;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.core.tables.DataTables;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Ltch2022PricerContext extends LtchPricerContext {

  public static final String CALCULATION_VERSION_2022 = "2022.0";
  private static final BigDecimal STANDARD_QUALITY_FED_RATE_2022 = new BigDecimal("44713.67");
  private static final BigDecimal STANDARD_FED_RATE_2022 = new BigDecimal("43836.08");
  private static final BigDecimal NATIONAL_LABOR_PERCENT_2022 = new BigDecimal(".67900");
  private static final BigDecimal NATIONAL_NON_LABOR_PERCENT_2022 = new BigDecimal(".32100");
  private static final BigDecimal HIGH_IPPS_NATIONAL_LABOR_SHARE_2022 = new BigDecimal("4138.24");
  private static final BigDecimal LOW_IPPS_NATIONAL_LABOR_SHARE_2022 = new BigDecimal("3795.42");
  private static final BigDecimal HIGH_IPPS_NATIONAL_NON_LABOR_SHARE_2022 =
      new BigDecimal("1983.41");
  private static final BigDecimal LOW_IPPS_NATIONAL_NON_LABOR_SHARE_2022 =
      new BigDecimal("2326.23");
  private static final BigDecimal FIXED_LOSS_AMOUNT_STANDARD_2022 =
      new BigDecimal("33015.00").setScale(2, RoundingMode.HALF_UP); // H-FIXED-LOSS-AMT-STD
  private static final BigDecimal FIXED_LOSS_AMOUNT_SITE_NEUTRAL_2022 =
      new BigDecimal("30988.00").setScale(2, RoundingMode.HALF_UP); // H-FIXED-LOSS-AMT-SNT
  private static final BigDecimal OPERATING_DSH_REDUCTION_FACTOR_2022 =
      new BigDecimal("0.7643").setScale(4, RoundingMode.HALF_UP); // H-OPER-DSH-REDUCTION-FACTOR
  private static final BigDecimal IPPS_CAPITAL_STANDARD_FED_RATE_2022 =
      new BigDecimal("472.59").setScale(2, RoundingMode.HALF_UP); // H-IPPS-CAPI-STD-FED-RATE

  public Ltch2022PricerContext(
      LtchClaimPricingRequest input, LtchClaimPricingResponse output, DataTables dataTables) {
    super(input, output, dataTables);
    this.calculationVersion = CALCULATION_VERSION_2022;
  }

  /** Getters for annual constants, often called 'rates,' that may change each year */
  @Override
  public @FixedValue BigDecimal getStandardQualityFedRate() {
    return STANDARD_QUALITY_FED_RATE_2022;
  }

  @Override
  public @FixedValue BigDecimal getStandardFedRate() {
    return STANDARD_FED_RATE_2022;
  }

  @Override
  public @FixedValue BigDecimal getNationalLaborPercent() {
    return NATIONAL_LABOR_PERCENT_2022;
  }

  @Override
  public @FixedValue BigDecimal getNationalNonLaborPercent() {
    return NATIONAL_NON_LABOR_PERCENT_2022;
  }

  @Override
  public @FixedValue BigDecimal getHighIppsNationalLaborShare() {
    return HIGH_IPPS_NATIONAL_LABOR_SHARE_2022;
  }

  @Override
  public @FixedValue BigDecimal getLowIppsNationalLaborShare() {
    return LOW_IPPS_NATIONAL_LABOR_SHARE_2022;
  }

  @Override
  public @FixedValue BigDecimal getHighIppsNationalNonLaborShare() {
    return HIGH_IPPS_NATIONAL_NON_LABOR_SHARE_2022;
  }

  @Override
  public @FixedValue BigDecimal getLowIppsNationalNonLaborShare() {
    return LOW_IPPS_NATIONAL_NON_LABOR_SHARE_2022;
  }

  @Override
  public @FixedValue BigDecimal getFixedLossAmountStandard() {
    return FIXED_LOSS_AMOUNT_STANDARD_2022;
  }

  @Override
  public @FixedValue BigDecimal getFixedLossAmountSiteNeutral() {
    return FIXED_LOSS_AMOUNT_SITE_NEUTRAL_2022;
  }

  @Override
  public @FixedValue BigDecimal getOperatingDshReductionFactor() {
    return OPERATING_DSH_REDUCTION_FACTOR_2022;
  }

  @Override
  public @FixedValue BigDecimal getIppsCapitalStandardFedRate() {
    return IPPS_CAPITAL_STANDARD_FED_RATE_2022;
  }
}
