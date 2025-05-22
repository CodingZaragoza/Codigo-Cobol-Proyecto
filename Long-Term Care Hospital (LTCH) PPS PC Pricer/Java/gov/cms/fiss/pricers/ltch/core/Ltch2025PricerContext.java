package gov.cms.fiss.pricers.ltch.core;

import gov.cms.fiss.pricers.common.api.annotations.FixedValue;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.core.tables.DataTables;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Ltch2025PricerContext extends LtchPricerContext {

  public static final String CALCULATION_VERSION_2025 = "2025.1";
  private static final BigDecimal STANDARD_QUALITY_FED_RATE_2025 = new BigDecimal("49383.26");
  private static final BigDecimal STANDARD_FED_RATE_2025 = new BigDecimal("48424.36");
  private static final BigDecimal NATIONAL_LABOR_PERCENT_2025 = new BigDecimal(".72800");
  private static final BigDecimal NATIONAL_NON_LABOR_PERCENT_2025 = new BigDecimal(".27200");
  private static final BigDecimal HIGH_IPPS_NATIONAL_LABOR_SHARE_2025 = new BigDecimal("4478.09");
  private static final BigDecimal LOW_IPPS_NATIONAL_LABOR_SHARE_2025 = new BigDecimal("4107.12");
  private static final BigDecimal HIGH_IPPS_NATIONAL_NON_LABOR_SHARE_2025 =
      new BigDecimal("2146.30");
  private static final BigDecimal LOW_IPPS_NATIONAL_NON_LABOR_SHARE_2025 =
      new BigDecimal("2517.27");
  private static final BigDecimal FIXED_LOSS_AMOUNT_STANDARD_2025 =
      new BigDecimal("77048.00").setScale(2, RoundingMode.HALF_UP); // H-FIXED-LOSS-AMT-STD
  private static final BigDecimal FIXED_LOSS_AMOUNT_SITE_NEUTRAL_2025 =
      new BigDecimal("46217.00").setScale(2, RoundingMode.HALF_UP); // H-FIXED-LOSS-AMT-SNT
  private static final BigDecimal OPERATING_DSH_REDUCTION_FACTOR_2025 =
      new BigDecimal("0.6572").setScale(4, RoundingMode.HALF_UP); // H-OPER-DSH-REDUCTION-FACTOR
  private static final BigDecimal IPPS_CAPITAL_STANDARD_FED_RATE_2025 =
      new BigDecimal("512.14").setScale(2, RoundingMode.HALF_UP); // H-IPPS-CAPI-STD-FED-RATE

  public Ltch2025PricerContext(
      LtchClaimPricingRequest input, LtchClaimPricingResponse output, DataTables dataTables) {
    super(input, output, dataTables);
    this.calculationVersion = CALCULATION_VERSION_2025;
    getPaymentData().setBudgetNeutralityRate(BigDecimal.ONE.setScale(3, RoundingMode.HALF_UP));
  }

  /** Getters for annual constants, often called 'rates,' that may change each year */
  @Override
  public BigDecimal getStandardQualityFedRate() {
    return STANDARD_QUALITY_FED_RATE_2025;
  }

  @Override
  public BigDecimal getStandardFedRate() {
    return STANDARD_FED_RATE_2025;
  }

  @Override
  public BigDecimal getNationalLaborPercent() {
    return NATIONAL_LABOR_PERCENT_2025;
  }

  @Override
  public BigDecimal getNationalNonLaborPercent() {
    return NATIONAL_NON_LABOR_PERCENT_2025;
  }

  @Override
  public BigDecimal getHighIppsNationalLaborShare() {
    return HIGH_IPPS_NATIONAL_LABOR_SHARE_2025;
  }

  @Override
  public BigDecimal getLowIppsNationalLaborShare() {
    return LOW_IPPS_NATIONAL_LABOR_SHARE_2025;
  }

  @Override
  public BigDecimal getHighIppsNationalNonLaborShare() {
    return HIGH_IPPS_NATIONAL_NON_LABOR_SHARE_2025;
  }

  @Override
  public BigDecimal getLowIppsNationalNonLaborShare() {
    return LOW_IPPS_NATIONAL_NON_LABOR_SHARE_2025;
  }

  @Override
  public @FixedValue BigDecimal getFixedLossAmountStandard() {
    return FIXED_LOSS_AMOUNT_STANDARD_2025;
  }

  @Override
  public @FixedValue BigDecimal getFixedLossAmountSiteNeutral() {
    return FIXED_LOSS_AMOUNT_SITE_NEUTRAL_2025;
  }

  @Override
  public @FixedValue BigDecimal getOperatingDshReductionFactor() {
    return OPERATING_DSH_REDUCTION_FACTOR_2025;
  }

  @Override
  public @FixedValue BigDecimal getIppsCapitalStandardFedRate() {
    return IPPS_CAPITAL_STANDARD_FED_RATE_2025;
  }
}
