package gov.cms.fiss.pricers.hha.core;

import gov.cms.fiss.pricers.common.api.annotations.FixedValue;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingRequest;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingResponse;
import gov.cms.fiss.pricers.hha.core.tables.DataTables;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Hha2025PricerContext extends HhaPricerContext {

  public static final String CALCULATION_VERSION_2025 = "2025.1";
  private static final BigDecimal WS_STDV_RURAL_FAC_2025 = new BigDecimal("1.0000");
  private static final BigDecimal STANDARD_VALUE_AMOUNT_2025 = new BigDecimal("2057.35");
  private static final BigDecimal RURAL_ADDON_A_2025 = new BigDecimal("1.000");
  private static final BigDecimal RURAL_ADDON_B_2025 = new BigDecimal("1.000");
  private static final BigDecimal RURAL_ADDON_C_2025 = new BigDecimal("1.000");
  private static final BigDecimal LABOR_PERCENT_2025 = new BigDecimal("0.74900");
  private static final BigDecimal NONLABOR_PERCENT_2025 = new BigDecimal("0.25100");
  private static final BigDecimal LUPA_ADD_ON_SN4_2025 = new BigDecimal("0.7200");
  private static final BigDecimal LUPA_ADD_ON_PT1_2025 = new BigDecimal("0.6225");
  private static final BigDecimal LUPA_ADD_ON_SLT3_2025 = new BigDecimal("0.6696");
  private static final BigDecimal LUPA_ADD_ON_OT2_2025 = new BigDecimal("0.7238");
  private static final BigDecimal OUTLIER_LOSS_SHARING_RATIO_2025 = new BigDecimal("0.80");
  private static final BigDecimal FIXED_DOLLAR_LOSS_RATE_2025 = new BigDecimal(".35");
  private static final BigDecimal RATE_AMOUNT_CHECKED_2025 = new BigDecimal("2057.35");
  private static final BigDecimal THRESHOLD_AMOUNT_CHECKED_2025 =
      RATE_AMOUNT_CHECKED_2025
          .multiply(FIXED_DOLLAR_LOSS_RATE_2025)
          .setScale(2, RoundingMode.HALF_UP);
  private static final BigDecimal RATE_AMOUNT_UNCHECKED_2025 = new BigDecimal("2017.28");
  private static final BigDecimal THRESHOLD_AMOUNT_UNCHECKED_2025 =
      RATE_AMOUNT_UNCHECKED_2025
          .multiply(FIXED_DOLLAR_LOSS_RATE_2025)
          .setScale(2, RoundingMode.HALF_UP);

  public Hha2025PricerContext(
      HhaClaimPricingRequest input, HhaClaimPricingResponse output, DataTables dataTables) {
    super(input, output, dataTables);
  }

  @Override
  public String getCalculationVersion() {
    return CALCULATION_VERSION_2025;
  }

  @Override
  public @FixedValue BigDecimal getRateAmountChecked() {
    return RATE_AMOUNT_CHECKED_2025;
  }

  @Override
  public BigDecimal getThresholdAmountChecked() {
    return THRESHOLD_AMOUNT_CHECKED_2025;
  }

  @Override
  public @FixedValue BigDecimal getRateAmountUnchecked() {
    return RATE_AMOUNT_UNCHECKED_2025;
  }

  @Override
  public BigDecimal getThresholdAmountUnchecked() {
    return THRESHOLD_AMOUNT_UNCHECKED_2025;
  }

  @Override
  public BigDecimal getStandardizedRuralMultiplier() {
    return WS_STDV_RURAL_FAC_2025;
  }

  @Override
  public BigDecimal getStandardValueAmount() {
    return STANDARD_VALUE_AMOUNT_2025;
  }

  @Override
  public BigDecimal getLaborPercent() {
    return LABOR_PERCENT_2025;
  }

  @Override
  public BigDecimal getNonLaborPercent() {
    return NONLABOR_PERCENT_2025;
  }

  @Override
  public BigDecimal getLupaAddOnSkilledNursing() {
    return LUPA_ADD_ON_SN4_2025;
  }

  @Override
  public BigDecimal getLupaAddOnPhysicalTherapy() {
    return LUPA_ADD_ON_PT1_2025;
  }

  @Override
  public BigDecimal getLupaAddOnSpeechLanguagePathology() {
    return LUPA_ADD_ON_SLT3_2025;
  }

  @Override
  public BigDecimal getLupaAddOnOccupationalTherapy() {
    return LUPA_ADD_ON_OT2_2025;
  }

  @Override
  public BigDecimal getOutlierLossSharingRatio() {
    return OUTLIER_LOSS_SHARING_RATIO_2025;
  }

  @Override
  protected @FixedValue BigDecimal getRuralAddOnA() {
    return RURAL_ADDON_A_2025;
  }

  @Override
  protected @FixedValue BigDecimal getRuralAddOnB() {
    return RURAL_ADDON_B_2025;
  }

  @Override
  protected @FixedValue BigDecimal getRuralAddOnC() {
    return RURAL_ADDON_C_2025;
  }
}
