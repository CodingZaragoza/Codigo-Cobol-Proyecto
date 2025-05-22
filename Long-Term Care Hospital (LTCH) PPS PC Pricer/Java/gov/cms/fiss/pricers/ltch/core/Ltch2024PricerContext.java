package gov.cms.fiss.pricers.ltch.core;

import gov.cms.fiss.pricers.common.api.annotations.FixedValue;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.core.tables.DataTables;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Ltch2024PricerContext extends LtchPricerContext {

  public static final String CALCULATION_VERSION_2024 = "2024.0";
  private static final BigDecimal STANDARD_QUALITY_FED_RATE_2024 = new BigDecimal("48116.62");
  private static final BigDecimal STANDARD_FED_RATE_2024 = new BigDecimal("47185.03");
  private static final BigDecimal NATIONAL_LABOR_PERCENT_2024 = new BigDecimal(".68500");
  private static final BigDecimal NATIONAL_NON_LABOR_PERCENT_2024 = new BigDecimal(".31500");
  private static final BigDecimal HIGH_IPPS_NATIONAL_LABOR_SHARE_2024 = new BigDecimal("4392.49");
  private static final BigDecimal LOW_IPPS_NATIONAL_LABOR_SHARE_2024 = new BigDecimal("4028.62");
  private static final BigDecimal HIGH_IPPS_NATIONAL_NON_LABOR_SHARE_2024 =
      new BigDecimal("2105.28");
  private static final BigDecimal LOW_IPPS_NATIONAL_NON_LABOR_SHARE_2024 =
      new BigDecimal("2469.15");
  private static final BigDecimal FIXED_LOSS_AMOUNT_STANDARD_2024 =
      new BigDecimal("59873.00").setScale(2, RoundingMode.HALF_UP); // H-FIXED-LOSS-AMT-STD
  private static final BigDecimal FIXED_LOSS_AMOUNT_SITE_NEUTRAL_2024 =
      new BigDecimal("42750.00").setScale(2, RoundingMode.HALF_UP); // H-FIXED-LOSS-AMT-SNT
  private static final BigDecimal OPERATING_DSH_REDUCTION_FACTOR_2024 =
      new BigDecimal("0.6947").setScale(4, RoundingMode.HALF_UP); // H-OPER-DSH-REDUCTION-FACTOR
  private static final BigDecimal IPPS_CAPITAL_STANDARD_FED_RATE_2024 =
      new BigDecimal("503.83").setScale(2, RoundingMode.HALF_UP); // H-IPPS-CAPI-STD-FED-RATE

  public Ltch2024PricerContext(
      LtchClaimPricingRequest input, LtchClaimPricingResponse output, DataTables dataTables) {
    super(input, output, dataTables);
    this.calculationVersion = CALCULATION_VERSION_2024;
    getPaymentData().setBudgetNeutralityRate(BigDecimal.ONE.setScale(3, RoundingMode.HALF_UP));
  }

  /** Getters for annual constants, often called 'rates,' that may change each year */
  @Override
  public BigDecimal getStandardQualityFedRate() {
    return STANDARD_QUALITY_FED_RATE_2024;
  }

  @Override
  public BigDecimal getStandardFedRate() {
    return STANDARD_FED_RATE_2024;
  }

  @Override
  public BigDecimal getNationalLaborPercent() {
    return NATIONAL_LABOR_PERCENT_2024;
  }

  @Override
  public BigDecimal getNationalNonLaborPercent() {
    return NATIONAL_NON_LABOR_PERCENT_2024;
  }

  @Override
  public BigDecimal getHighIppsNationalLaborShare() {
    return HIGH_IPPS_NATIONAL_LABOR_SHARE_2024;
  }

  @Override
  public BigDecimal getLowIppsNationalLaborShare() {
    return LOW_IPPS_NATIONAL_LABOR_SHARE_2024;
  }

  @Override
  public BigDecimal getHighIppsNationalNonLaborShare() {
    return HIGH_IPPS_NATIONAL_NON_LABOR_SHARE_2024;
  }

  @Override
  public BigDecimal getLowIppsNationalNonLaborShare() {
    return LOW_IPPS_NATIONAL_NON_LABOR_SHARE_2024;
  }

  @Override
  public @FixedValue BigDecimal getFixedLossAmountStandard() {
    return FIXED_LOSS_AMOUNT_STANDARD_2024;
  }

  @Override
  public @FixedValue BigDecimal getFixedLossAmountSiteNeutral() {
    return FIXED_LOSS_AMOUNT_SITE_NEUTRAL_2024;
  }

  @Override
  public @FixedValue BigDecimal getOperatingDshReductionFactor() {
    return OPERATING_DSH_REDUCTION_FACTOR_2024;
  }

  @Override
  public @FixedValue BigDecimal getIppsCapitalStandardFedRate() {
    return IPPS_CAPITAL_STANDARD_FED_RATE_2024;
  }
}
