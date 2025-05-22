package gov.cms.fiss.pricers.ipps.core;

import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.tables.DataTables;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Ipps2020PricerContext extends IppsPricerContext {
  public static final String CALCULATION_VERSION = "2020.5";

  // Per-Year constants
  private static final BigDecimal CAPITAL_BASE_RATE_2020 = new BigDecimal("462.33");
  private static final BigDecimal COST_THRESHOLD_BASE_2020 = new BigDecimal("26552.0");
  private static final BigDecimal LABOR_PCT_WI_GT_ONE_2020 = new BigDecimal("0.683");
  private static final BigDecimal NON_LABOR_PCT_WI_GT_ONE_2020 = new BigDecimal("0.317");
  private static final BigDecimal LABOR_PCT_WI_LTEQ_ONE_2020 = new BigDecimal("0.62");
  private static final BigDecimal NON_LABOR_PCT_WI_LTEQ_ONE_2020 = new BigDecimal("0.38");
  private static final BigDecimal COVID_ADJUSTMENT_FACTOR_2020 = new BigDecimal("1.2");
  private static final BigDecimal MARKET_BASKET_RATIO_EHR_FULL_2020 = new BigDecimal("1.022421525");
  private static final BigDecimal MARKET_BASKET_RATIO_EHR_QUALIFIED_FULL_2020 =
      new BigDecimal("1.022590361");
  private static final BigDecimal OPERATING_BASE_RATE_2020 = new BigDecimal("5796.63");
  private static final BigDecimal OPER_HSP_BUDGET_NEUTRAL_BASE_2019 =
      new BigDecimal("0.99719").setScale(6, RoundingMode.HALF_UP);
  private static final BigDecimal OPER_HSP_BUDGET_NEUTRAL_BASE_2020 =
      new BigDecimal("0.996859").setScale(6, RoundingMode.HALF_UP);
  private static final BigDecimal OPER_HSP_UPDATE_2019 =
      new BigDecimal("1.0135").setScale(6, RoundingMode.HALF_UP);
  private static final BigDecimal OPER_HSP_UPDATE_NO_QUALITY_EHR_2020 =
      new BigDecimal("0.9960").setScale(6, RoundingMode.HALF_UP);
  private static final BigDecimal OPER_HSP_UPDATE_NO_QUALITY_NO_EHR_2020 =
      new BigDecimal("1.0185").setScale(6, RoundingMode.HALF_UP);
  private static final BigDecimal OPER_HSP_UPDATE_QUALITY_EHR_2020 =
      new BigDecimal("1.0035").setScale(6, RoundingMode.HALF_UP);
  private static final BigDecimal OPER_HSP_UPDATE_QUALITY_NO_EHR_2020 =
      new BigDecimal("1.0260").setScale(6, RoundingMode.HALF_UP);

  public Ipps2020PricerContext(
      IppsClaimPricingRequest input, IppsClaimPricingResponse output, DataTables dataTables) {
    super(input, output, dataTables);
  }

  @Override
  public BigDecimal getBudgetNeutralBase() {
    return OPER_HSP_BUDGET_NEUTRAL_BASE_2019.multiply(OPER_HSP_BUDGET_NEUTRAL_BASE_2020);
  }

  @Override
  public BigDecimal getCovidAdjustmentFactorValue() {
    return COVID_ADJUSTMENT_FACTOR_2020;
  }

  @Override
  public String getCalculationVersion() {
    return CALCULATION_VERSION;
  }

  @Override
  public BigDecimal getCapitalBaseRate() {
    return CAPITAL_BASE_RATE_2020;
  }

  @Override
  public BigDecimal getCostThresholdBase() {
    return COST_THRESHOLD_BASE_2020;
  }

  @Override
  public BigDecimal getNationalLaborPctWageIndexGtOne() {
    return LABOR_PCT_WI_GT_ONE_2020;
  }

  @Override
  public BigDecimal getNationalNonLaborPctWageIndexGtOne() {
    return NON_LABOR_PCT_WI_GT_ONE_2020;
  }

  @Override
  public BigDecimal getNationalLaborPctWageIndexLtEqOne() {
    return LABOR_PCT_WI_LTEQ_ONE_2020;
  }

  @Override
  public BigDecimal getNationalNonLaborPctWageIndexLtEqOne() {
    return NON_LABOR_PCT_WI_LTEQ_ONE_2020;
  }

  @Override
  public BigDecimal getHospitalSpecificPortionUpdateFactorWithQualityAndNoEhrReduction() {
    return OPER_HSP_UPDATE_QUALITY_NO_EHR_2020;
  }

  @Override
  public BigDecimal getHospitalSpecificPortionUpdateFactorWithNoQualityAndNoEhrReduction() {
    return OPER_HSP_UPDATE_NO_QUALITY_NO_EHR_2020;
  }

  @Override
  public BigDecimal getHospitalSpecificPortionUpdateFactorWithQualityAndEhrReduction() {
    return OPER_HSP_UPDATE_QUALITY_EHR_2020;
  }

  @Override
  public BigDecimal getHospitalSpecificPortionUpdateFactorWithNoQualityAndEhrReduction() {
    return OPER_HSP_UPDATE_NO_QUALITY_EHR_2020;
  }

  @Override
  public BigDecimal getMarketBasketRatioEhrFull() {
    return MARKET_BASKET_RATIO_EHR_FULL_2020;
  }

  @Override
  public BigDecimal getMarketBasketRatioEhrQualifiedFull() {
    return MARKET_BASKET_RATIO_EHR_QUALIFIED_FULL_2020;
  }

  @Override
  public BigDecimal getOperatingBaseRate() {
    return OPERATING_BASE_RATE_2020;
  }

  @Override
  public BigDecimal getPriorYearHospitalSpecificPortionUpdateFactor() {
    return OPER_HSP_UPDATE_2019;
  }
}
