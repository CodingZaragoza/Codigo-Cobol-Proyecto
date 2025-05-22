package gov.cms.fiss.pricers.hha.core;

import gov.cms.fiss.pricers.common.api.annotations.FixedValue;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingRequest;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingResponse;
import gov.cms.fiss.pricers.hha.core.tables.DataTables;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Hha2022PricerContext extends HhaPricerContext {

  public static final String CALCULATION_VERSION_2022 = "2022.0";
  private static final BigDecimal WS_STDV_RURAL_FAC_2022 = new BigDecimal("1.0000");
  private static final BigDecimal STANDARD_VALUE_AMOUNT_2022 = new BigDecimal("2031.64");
  private static final BigDecimal RURAL_ADDON_A_2022 = new BigDecimal("1.000");
  private static final BigDecimal RURAL_ADDON_B_2022 = new BigDecimal("1.010");
  private static final BigDecimal RURAL_ADDON_C_2022 = new BigDecimal("1.000");
  private static final BigDecimal LABOR_PERCENT_2022 = new BigDecimal("0.76100");
  private static final BigDecimal NONLABOR_PERCENT_2022 = new BigDecimal("0.23900");
  private static final BigDecimal LUPA_ADD_ON_SN4_2022 = new BigDecimal("0.8451");
  private static final BigDecimal LUPA_ADD_ON_PT1_2022 = new BigDecimal("0.6700");
  private static final BigDecimal LUPA_ADD_ON_SLT3_2022 = new BigDecimal("0.6266");
  private static final BigDecimal LUPA_ADD_ON_OT2_2022 = new BigDecimal("0.6700");
  private static final BigDecimal OUTLIER_LOSS_SHARING_RATIO_2022 = new BigDecimal("0.80");
  private static final BigDecimal FIXED_DOLLAR_LOSS_RATE_2022 = new BigDecimal(".40");
  private static final BigDecimal RATE_AMOUNT_CHECKED_2022 = new BigDecimal("2031.64");
  private static final BigDecimal THRESHOLD_AMOUNT_CHECKED_2022 =
      RATE_AMOUNT_CHECKED_2022
          .multiply(FIXED_DOLLAR_LOSS_RATE_2022)
          .setScale(2, RoundingMode.HALF_UP);
  private static final BigDecimal RATE_AMOUNT_UNCHECKED_2022 = new BigDecimal("1992.04");
  private static final BigDecimal THRESHOLD_AMOUNT_UNCHECKED_2022 =
      RATE_AMOUNT_UNCHECKED_2022
          .multiply(FIXED_DOLLAR_LOSS_RATE_2022)
          .setScale(2, RoundingMode.HALF_UP);

  public Hha2022PricerContext(
      HhaClaimPricingRequest input, HhaClaimPricingResponse output, DataTables dataTables) {
    super(input, output, dataTables);
  }

  @Override
  public String getCalculationVersion() {
    return CALCULATION_VERSION_2022;
  }

  @Override
  public @FixedValue BigDecimal getRateAmountChecked() {
    return RATE_AMOUNT_CHECKED_2022;
  }

  @Override
  public BigDecimal getThresholdAmountChecked() {
    return THRESHOLD_AMOUNT_CHECKED_2022;
  }

  @Override
  public @FixedValue BigDecimal getRateAmountUnchecked() {
    return RATE_AMOUNT_UNCHECKED_2022;
  }

  @Override
  public BigDecimal getThresholdAmountUnchecked() {
    return THRESHOLD_AMOUNT_UNCHECKED_2022;
  }

  @Override
  public BigDecimal getStandardizedRuralMultiplier() {
    return WS_STDV_RURAL_FAC_2022;
  }

  @Override
  public BigDecimal getStandardValueAmount() {
    return STANDARD_VALUE_AMOUNT_2022;
  }

  @Override
  public BigDecimal getLaborPercent() {
    return LABOR_PERCENT_2022;
  }

  @Override
  public BigDecimal getNonLaborPercent() {
    return NONLABOR_PERCENT_2022;
  }

  @Override
  public BigDecimal getLupaAddOnSkilledNursing() {
    return LUPA_ADD_ON_SN4_2022;
  }

  @Override
  public BigDecimal getLupaAddOnPhysicalTherapy() {
    return LUPA_ADD_ON_PT1_2022;
  }

  @Override
  public BigDecimal getLupaAddOnSpeechLanguagePathology() {
    return LUPA_ADD_ON_SLT3_2022;
  }

  @Override
  public BigDecimal getLupaAddOnOccupationalTherapy() {
    return LUPA_ADD_ON_OT2_2022;
  }

  @Override
  public BigDecimal getOutlierLossSharingRatio() {
    return OUTLIER_LOSS_SHARING_RATIO_2022;
  }

  @Override
  protected @FixedValue BigDecimal getRuralAddOnA() {
    return RURAL_ADDON_A_2022;
  }

  @Override
  protected @FixedValue BigDecimal getRuralAddOnB() {
    return RURAL_ADDON_B_2022;
  }

  @Override
  protected @FixedValue BigDecimal getRuralAddOnC() {
    return RURAL_ADDON_C_2022;
  }
}
