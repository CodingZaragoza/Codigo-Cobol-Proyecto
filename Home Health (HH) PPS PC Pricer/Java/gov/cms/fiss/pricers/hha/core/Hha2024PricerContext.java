package gov.cms.fiss.pricers.hha.core;

import gov.cms.fiss.pricers.common.api.annotations.FixedValue;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingRequest;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingResponse;
import gov.cms.fiss.pricers.hha.core.tables.DataTables;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Hha2024PricerContext extends HhaPricerContext {

  public static final String CALCULATION_VERSION_2024 = "2024.0";
  private static final BigDecimal WS_STDV_RURAL_FAC_2024 = new BigDecimal("1.0000");
  private static final BigDecimal STANDARD_VALUE_AMOUNT_2024 = new BigDecimal("2038.13");
  private static final BigDecimal RURAL_ADDON_A_2024 = new BigDecimal("1.000");
  private static final BigDecimal RURAL_ADDON_B_2024 = new BigDecimal("1.000");
  private static final BigDecimal RURAL_ADDON_C_2024 = new BigDecimal("1.000");
  private static final BigDecimal LABOR_PERCENT_2024 = new BigDecimal("0.74900");
  private static final BigDecimal NONLABOR_PERCENT_2024 = new BigDecimal("0.25100");
  private static final BigDecimal LUPA_ADD_ON_SN4_2024 = new BigDecimal("0.8451");
  private static final BigDecimal LUPA_ADD_ON_PT1_2024 = new BigDecimal("0.6700");
  private static final BigDecimal LUPA_ADD_ON_SLT3_2024 = new BigDecimal("0.6266");
  private static final BigDecimal LUPA_ADD_ON_OT2_2024 = new BigDecimal("0.6700");
  private static final BigDecimal OUTLIER_LOSS_SHARING_RATIO_2024 = new BigDecimal("0.80");
  private static final BigDecimal FIXED_DOLLAR_LOSS_RATE_2024 = new BigDecimal(".27");
  private static final BigDecimal RATE_AMOUNT_CHECKED_2024 = new BigDecimal("2038.13");
  private static final BigDecimal THRESHOLD_AMOUNT_CHECKED_2024 =
      RATE_AMOUNT_CHECKED_2024
          .multiply(FIXED_DOLLAR_LOSS_RATE_2024)
          .setScale(2, RoundingMode.HALF_UP);
  private static final BigDecimal RATE_AMOUNT_UNCHECKED_2024 = new BigDecimal("1998.56");
  private static final BigDecimal THRESHOLD_AMOUNT_UNCHECKED_2024 =
      RATE_AMOUNT_UNCHECKED_2024
          .multiply(FIXED_DOLLAR_LOSS_RATE_2024)
          .setScale(2, RoundingMode.HALF_UP);

  public Hha2024PricerContext(
      HhaClaimPricingRequest input, HhaClaimPricingResponse output, DataTables dataTables) {
    super(input, output, dataTables);
  }

  @Override
  public String getCalculationVersion() {
    return CALCULATION_VERSION_2024;
  }

  @Override
  public @FixedValue BigDecimal getRateAmountChecked() {
    return RATE_AMOUNT_CHECKED_2024;
  }

  @Override
  public BigDecimal getThresholdAmountChecked() {
    return THRESHOLD_AMOUNT_CHECKED_2024;
  }

  @Override
  public @FixedValue BigDecimal getRateAmountUnchecked() {
    return RATE_AMOUNT_UNCHECKED_2024;
  }

  @Override
  public BigDecimal getThresholdAmountUnchecked() {
    return THRESHOLD_AMOUNT_UNCHECKED_2024;
  }

  @Override
  public BigDecimal getStandardizedRuralMultiplier() {
    return WS_STDV_RURAL_FAC_2024;
  }

  @Override
  public BigDecimal getStandardValueAmount() {
    return STANDARD_VALUE_AMOUNT_2024;
  }

  @Override
  public BigDecimal getLaborPercent() {
    return LABOR_PERCENT_2024;
  }

  @Override
  public BigDecimal getNonLaborPercent() {
    return NONLABOR_PERCENT_2024;
  }

  @Override
  public BigDecimal getLupaAddOnSkilledNursing() {
    return LUPA_ADD_ON_SN4_2024;
  }

  @Override
  public BigDecimal getLupaAddOnPhysicalTherapy() {
    return LUPA_ADD_ON_PT1_2024;
  }

  @Override
  public BigDecimal getLupaAddOnSpeechLanguagePathology() {
    return LUPA_ADD_ON_SLT3_2024;
  }

  @Override
  public BigDecimal getLupaAddOnOccupationalTherapy() {
    return LUPA_ADD_ON_OT2_2024;
  }

  @Override
  public BigDecimal getOutlierLossSharingRatio() {
    return OUTLIER_LOSS_SHARING_RATIO_2024;
  }

  @Override
  protected @FixedValue BigDecimal getRuralAddOnA() {
    return RURAL_ADDON_A_2024;
  }

  @Override
  protected @FixedValue BigDecimal getRuralAddOnB() {
    return RURAL_ADDON_B_2024;
  }

  @Override
  protected @FixedValue BigDecimal getRuralAddOnC() {
    return RURAL_ADDON_C_2024;
  }
}
