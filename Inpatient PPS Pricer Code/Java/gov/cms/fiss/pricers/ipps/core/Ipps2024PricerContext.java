package gov.cms.fiss.pricers.ipps.core;

import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.tables.DataTables;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Ipps2024PricerContext extends IppsPricerContext {
  public static final String CALCULATION_VERSION = "2024.1";

  // Per-Year constants
  private static final BigDecimal CAPITAL_BASE_RATE_2024 = new BigDecimal("503.83");
  private static final BigDecimal MIDNIGHT_ADJUSTMENT_FACTOR_2024 = new BigDecimal("1.0");
  private static final BigDecimal COST_THRESHOLD_BASE_2024 = new BigDecimal("42750.00");
  private static final BigDecimal LABOR_PCT_WI_GT_ONE_2024 = new BigDecimal("0.676");
  private static final BigDecimal NON_LABOR_PCT_WI_GT_ONE_2024 = new BigDecimal("0.324");
  private static final BigDecimal LABOR_PCT_WI_LTEQ_ONE_2024 = new BigDecimal("0.62");
  private static final BigDecimal NON_LABOR_PCT_WI_LTEQ_ONE_2024 = new BigDecimal("0.38");
  private static final BigDecimal NO_COST_PRODUCT_ADJUSTMENT_FACTOR_2024 = new BigDecimal("0.27");
  private static final BigDecimal MARKET_BASKET_RATIO_EHR_FULL_2024 = new BigDecimal("1.024596273");
  private static final BigDecimal MARKET_BASKET_RATIO_EHR_QUALIFIED_FULL_2024 =
      new BigDecimal("1.024799599");
  private static final BigDecimal MARKET_BASKET_RATIO_EHR_FULL_PR_2024 =
      new BigDecimal("1.024596273");
  private static final BigDecimal OPERATING_BASE_RATE_2024 = new BigDecimal("6497.77");
  private static final BigDecimal OPER_HSP_BUDGET_NEUTRAL_BASE_2019 =
      new BigDecimal("0.99719").setScale(6, RoundingMode.HALF_UP);
  private static final BigDecimal OPER_HSP_BUDGET_NEUTRAL_BASE_2020 =
      new BigDecimal("0.996859").setScale(6, RoundingMode.HALF_UP);
  private static final BigDecimal OPER_HSP_BUDGET_NEUTRAL_BASE_2021 =
      new BigDecimal("0.997975").setScale(6, RoundingMode.HALF_UP);
  private static final BigDecimal OPER_HSP_BUDGET_NEUTRAL_BASE_2022 =
      new BigDecimal("1.000107").setScale(6, RoundingMode.HALF_UP);
  private static final BigDecimal OPER_HSP_BUDGET_NEUTRAL_BASE_2023_1 =
      new BigDecimal("1.000509").setScale(6, RoundingMode.HALF_UP);
  private static final BigDecimal OPER_HSP_BUDGET_NEUTRAL_BASE_2023_2 =
      new BigDecimal("0.999764").setScale(6, RoundingMode.HALF_UP);
  private static final BigDecimal OPER_HSP_BUDGET_NEUTRAL_BASE_2024_1 =
      new BigDecimal("1.001463").setScale(6, RoundingMode.HALF_UP);
  private static final BigDecimal OPER_HSP_BUDGET_NEUTRAL_BASE_2024_2 =
      new BigDecimal("0.999928").setScale(6, RoundingMode.HALF_UP);
  private static final BigDecimal OPER_HSP_UPDATE_2019 =
      new BigDecimal("1.0135").setScale(6, RoundingMode.HALF_UP);
  private static final BigDecimal OPER_HSP_UPDATE_2020 =
      new BigDecimal("1.02600").setScale(6, RoundingMode.HALF_UP);
  private static final BigDecimal OPER_HSP_UPDATE_2021 =
      new BigDecimal("1.02400").setScale(6, RoundingMode.HALF_UP);
  private static final BigDecimal OPER_HSP_UPDATE_2022 =
      new BigDecimal("1.02000").setScale(6, RoundingMode.HALF_UP);
  private static final BigDecimal OPER_HSP_UPDATE_2023 =
      new BigDecimal("1.03800").setScale(6, RoundingMode.HALF_UP);
  private static final BigDecimal OPER_HSP_UPDATE_NO_QUALITY_EHR_2024 =
      new BigDecimal("0.99800").setScale(6, RoundingMode.HALF_UP);
  private static final BigDecimal OPER_HSP_UPDATE_NO_QUALITY_NO_EHR_2024 =
      new BigDecimal("1.02275").setScale(6, RoundingMode.HALF_UP);
  private static final BigDecimal OPER_HSP_UPDATE_QUALITY_EHR_2024 =
      new BigDecimal("1.00625").setScale(6, RoundingMode.HALF_UP);
  private static final BigDecimal OPER_HSP_UPDATE_QUALITY_NO_EHR_2024 =
      new BigDecimal("1.03100").setScale(6, RoundingMode.HALF_UP);
  private static final BigDecimal OPER_HSP_UPDATE_NO_EHR_PR_2024 =
      new BigDecimal("1.03100").setScale(6, RoundingMode.HALF_UP);
  private static final BigDecimal OPER_HSP_UPDATE_EHR_PR_2024 =
      new BigDecimal("1.00625").setScale(6, RoundingMode.HALF_UP);
  private static final BigDecimal WAGE_INDEX_QUARTILE_LIMIT_2024 = new BigDecimal("0.8667");

  public Ipps2024PricerContext(
      IppsClaimPricingRequest input, IppsClaimPricingResponse output, DataTables dataTables) {
    super(input, output, dataTables);
  }

  @Override
  public BigDecimal getBudgetNeutralBase() {
    return OPER_HSP_BUDGET_NEUTRAL_BASE_2019
        .multiply(OPER_HSP_BUDGET_NEUTRAL_BASE_2020)
        .multiply(OPER_HSP_BUDGET_NEUTRAL_BASE_2021)
        .multiply(OPER_HSP_BUDGET_NEUTRAL_BASE_2022)
        .multiply(OPER_HSP_BUDGET_NEUTRAL_BASE_2023_1)
        .multiply(OPER_HSP_BUDGET_NEUTRAL_BASE_2023_2)
        .multiply(OPER_HSP_BUDGET_NEUTRAL_BASE_2024_1)
        .multiply(OPER_HSP_BUDGET_NEUTRAL_BASE_2024_2);
  }

  @Override
  public String getCalculationVersion() {
    return CALCULATION_VERSION;
  }

  @Override
  public BigDecimal getMidnightAdjustmentFactor() {
    return MIDNIGHT_ADJUSTMENT_FACTOR_2024;
  }

  @Override
  public BigDecimal getNoCostProductAdjustmentFactorValue() {
    return NO_COST_PRODUCT_ADJUSTMENT_FACTOR_2024;
  }

  @Override
  public BigDecimal getCapitalBaseRate() {
    return CAPITAL_BASE_RATE_2024;
  }

  @Override
  public BigDecimal getCostThresholdBase() {
    return COST_THRESHOLD_BASE_2024;
  }

  @Override
  public BigDecimal getNationalLaborPctWageIndexGtOne() {
    return LABOR_PCT_WI_GT_ONE_2024;
  }

  @Override
  public BigDecimal getNationalNonLaborPctWageIndexGtOne() {
    return NON_LABOR_PCT_WI_GT_ONE_2024;
  }

  @Override
  public BigDecimal getNationalLaborPctWageIndexLtEqOne() {
    return LABOR_PCT_WI_LTEQ_ONE_2024;
  }

  @Override
  public BigDecimal getNationalNonLaborPctWageIndexLtEqOne() {
    return NON_LABOR_PCT_WI_LTEQ_ONE_2024;
  }

  @Override
  public BigDecimal getHospitalSpecificPortionUpdateFactorWithQualityAndNoEhrReduction() {
    return OPER_HSP_UPDATE_QUALITY_NO_EHR_2024;
  }

  @Override
  public BigDecimal getHospitalSpecificPortionUpdateFactorWithNoQualityAndNoEhrReduction() {
    return OPER_HSP_UPDATE_NO_QUALITY_NO_EHR_2024;
  }

  @Override
  public BigDecimal getHospitalSpecificPortionUpdateFactorWithQualityAndEhrReduction() {
    return OPER_HSP_UPDATE_QUALITY_EHR_2024;
  }

  @Override
  public BigDecimal getHospitalSpecificPortionUpdateFactorWithNoQualityAndEhrReduction() {
    return OPER_HSP_UPDATE_NO_QUALITY_EHR_2024;
  }

  @Override
  public BigDecimal getHospitalSpecificPortionUpdateFactorWithNoEhrReductionPuertoRico() {
    return OPER_HSP_UPDATE_NO_EHR_PR_2024;
  }

  @Override
  public BigDecimal getHospitalSpecificPortionUpdateFactorWithEhrReductionPuertoRico() {
    return OPER_HSP_UPDATE_EHR_PR_2024;
  }

  @Override
  public BigDecimal getMarketBasketRatioEhrFull() {
    return MARKET_BASKET_RATIO_EHR_FULL_2024;
  }

  @Override
  public BigDecimal getMarketBasketRatioEhrQualifiedFull() {
    return MARKET_BASKET_RATIO_EHR_QUALIFIED_FULL_2024;
  }

  @Override
  public BigDecimal getMarketBasketRatioEhrFullPuertoRico() {
    return MARKET_BASKET_RATIO_EHR_FULL_PR_2024;
  }

  @Override
  public BigDecimal getOperatingBaseRate() {
    return OPERATING_BASE_RATE_2024;
  }

  @Override
  public BigDecimal getPriorYearHospitalSpecificPortionUpdateFactor() {
    return OPER_HSP_UPDATE_2019
        .multiply(OPER_HSP_UPDATE_2020)
        .multiply(OPER_HSP_UPDATE_2021)
        .multiply(OPER_HSP_UPDATE_2022)
        .multiply(OPER_HSP_UPDATE_2023);
  }

  @Override
  public BigDecimal getWageIndexQuartileLimit() {
    return WAGE_INDEX_QUARTILE_LIMIT_2024;
  }
}
