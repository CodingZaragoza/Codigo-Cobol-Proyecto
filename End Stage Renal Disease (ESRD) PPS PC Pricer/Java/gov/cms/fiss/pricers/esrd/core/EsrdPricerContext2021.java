package gov.cms.fiss.pricers.esrd.core;

import gov.cms.fiss.pricers.common.api.annotations.FixedValue;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.tables.DataTables;
import java.math.BigDecimal;

public class EsrdPricerContext2021 extends EsrdPricerContext {

  public static final String CALCULATION_VERSION_2021 = "2021.2";

  private static final BigDecimal NETWORK_REDUCTION_AMOUNT_PER_DIEM_2021 = new BigDecimal("0.21");
  private static final BigDecimal NETWORK_REDUCTION_AMOUNT_FULL_2021 = new BigDecimal("0.50");

  private static final BigDecimal ADJUSTED_AVERAGE_MAP_AMOUNT_OVER_17_2021 =
      new BigDecimal("50.92");
  private static final BigDecimal ADJUSTED_AVERAGE_MAP_AMOUNT_UNDER_18_2021 =
      new BigDecimal("30.88");
  private static final BigDecimal BASE_PAYMENT_RATE_2021 = new BigDecimal("145.20");
  private static final BigDecimal BSA_NATIONAL_AVERAGE_2021 = new BigDecimal("1.90");
  private static final BigDecimal BUNDLED_BASE_PAYMENT_RATE_2021 = new BigDecimal("253.13");
  private static final BigDecimal BUNDLED_BASE_PAYMENT_RATE_AKI_2021 = new BigDecimal("253.13");
  private static final BigDecimal BUNDLED_CBSA_BLEND_PERCENTAGE_2021 = new BigDecimal("1.00");
  private static final BigDecimal BUNDLED_NATIONAL_LABOR_PERCENTAGE_2021 =
      new BigDecimal("0.52300");
  private static final BigDecimal BUNDLED_NATIONAL_NON_LABOR_PERCENTAGE_2021 =
      new BigDecimal("0.47700");
  private static final BigDecimal CASE_MIX_AGE_18_TO_44_MULTIPLIER_2021 = new BigDecimal("1.257");
  private static final BigDecimal CASE_MIX_AGE_45_TO_59_MULTIPLIER_2021 = new BigDecimal("1.068");
  private static final BigDecimal CASE_MIX_AGE_60_TO_69_MULTIPLIER_2021 = new BigDecimal("1.070");
  private static final BigDecimal CASE_MIX_AGE_70_TO_79_MULTIPLIER_2021 = DEFAULT_MULTIPLIER;
  private static final BigDecimal CASE_MIX_AGE_80_PLUS_MULTIPLIER_2021 = new BigDecimal("1.109");
  private static final BigDecimal CASE_MIX_BMI_UNDER_18_5_MULTIPLIER_2021 = new BigDecimal("1.017");
  private static final BigDecimal CASE_MIX_BSA_MULTIPLIER_2021 = new BigDecimal("1.032");
  private static final BigDecimal CASE_MIX_GASTROINTESTINAL_BLEED_MULTIPLIER_2021 =
      new BigDecimal("1.082");
  private static final BigDecimal CASE_MIX_LOW_VOLUME_ADJUSTMENT_LT_400_MULTIPLIER_2021 =
      new BigDecimal("1.239");
  private static final BigDecimal CASE_MIX_MYELODYSPLASTIC_SYNDROME_MULTIPLIER_2021 =
      new BigDecimal("1.095");
  private static final BigDecimal CASE_MIX_ONSET_LTE_120_MULTIPLIER_2021 = new BigDecimal("1.327");
  private static final BigDecimal CASE_MIX_PERICARDITIS_MULTIPLIER_2021 = new BigDecimal("1.040");
  private static final BigDecimal CASE_MIX_RURAL_MULTIPLIER_2021 = new BigDecimal("1.008");
  private static final BigDecimal CASE_MIX_SICKLE_CELL_MULTIPLIER_2021 = new BigDecimal("1.192");
  private static final BigDecimal DRUG_ADD_ON_2021 = new BigDecimal("1.1400");
  private static final BigDecimal ETC_HDPA_PERCENT_2021 = new BigDecimal("1.03");
  private static final BigDecimal CRA_TPNIES_OFFSET_2021 = new BigDecimal("0.00");
  private static final BigDecimal
      EXPANDED_BUNDLE_13_TO_17_HEMODIALYSIS_MODE_PAYMENT_MULTIPLIER_2021 = new BigDecimal("1.327");
  private static final BigDecimal
      EXPANDED_BUNDLE_13_TO_17_PERITONEAL_DIALYSIS_MODE_PAYMENT_MULTIPLIER_2021 =
          new BigDecimal("1.102");
  private static final BigDecimal
      EXPANDED_BUNDLE_UNDER_13_HEMODIALYSIS_MODE_PAYMENT_MULTIPLIER_2021 = new BigDecimal("1.306");
  private static final BigDecimal
      EXPANDED_BUNDLE_UNDER_13_PERITONEAL_DIALYSIS_MODE_PAYMENT_MULTIPLIER_2021 =
          new BigDecimal("1.063");
  private static final BigDecimal FIXED_DOLLAR_LOSS_OVER_17_2021 = new BigDecimal("122.49");
  private static final BigDecimal FIXED_DOLLAR_LOSS_UNDER_18_2021 = new BigDecimal("44.78");
  private static final BigDecimal LOSS_SHARING_PERCENTAGE_OVER_17_2021 = new BigDecimal("0.80");
  private static final BigDecimal LOSS_SHARING_PERCENTAGE_UNDER_18_2021 = new BigDecimal("0.80");
  private static final BigDecimal NATIONAL_LABOR_PERCENTAGE_2021 = new BigDecimal("0.53711");
  private static final BigDecimal NATIONAL_NON_LABOR_PERCENTAGE_2021 = new BigDecimal("0.46289");
  private static final BigDecimal
      SEPARATELY_BILLABLE_13_TO_17_HEMODIALYSIS_MODE_PAYMENT_MULTIPLIER_2021 =
          new BigDecimal("1.494");
  private static final BigDecimal
      SEPARATELY_BILLABLE_13_TO_17_PERITONEAL_DIALYSIS_MODE_PAYMENT_MULTIPLIER_2021 =
          new BigDecimal("0.569");
  private static final BigDecimal SEPARATELY_BILLABLE_AGE_18_TO_44_MULTIPLIER_2021 =
      new BigDecimal("1.044");
  private static final BigDecimal SEPARATELY_BILLABLE_AGE_45_TO_59_MULTIPLIER_2021 =
      DEFAULT_MULTIPLIER;
  private static final BigDecimal SEPARATELY_BILLABLE_AGE_60_TO_69_MULTIPLIER_2021 =
      new BigDecimal("1.005");
  private static final BigDecimal SEPARATELY_BILLABLE_AGE_70_TO_79_MULTIPLIER_2021 =
      DEFAULT_MULTIPLIER;
  private static final BigDecimal SEPARATELY_BILLABLE_AGE_80_PLUS_MULTIPLIER_2021 =
      new BigDecimal("0.961");
  private static final BigDecimal SEPARATELY_BILLABLE_BMI_UNDER_CUTOFF_MULTIPLIER_2021 =
      new BigDecimal("1.090");
  private static final BigDecimal SEPARATELY_BILLABLE_BSA_2021 = new BigDecimal("1.000");
  private static final BigDecimal SEPARATELY_BILLABLE_GASTROINTESTINAL_BLEED_MULTIPLIER_2021 =
      new BigDecimal("1.426");
  private static final BigDecimal SEPARATELY_BILLABLE_LOW_VOLUME_LT_400_MULTIPLIER_2021 =
      new BigDecimal("0.955");
  private static final BigDecimal SEPARATELY_BILLABLE_MYELODYSPLASTIC_SYNDROME_MULTIPLIER_2021 =
      new BigDecimal("1.494");
  private static final BigDecimal SEPARATELY_BILLABLE_ONSET_LTE_120_MULTIPLIER_2021 =
      new BigDecimal("1.409");
  private static final BigDecimal SEPARATELY_BILLABLE_PERICARDITIS_MULTIPLIER_2021 =
      new BigDecimal("1.209");
  private static final BigDecimal SEPARATELY_BILLABLE_RURAL_MULTIPLIER_2021 =
      new BigDecimal("0.978");
  private static final BigDecimal SEPARATELY_BILLABLE_SICKLE_CELL_MULTIPLIER_2021 =
      new BigDecimal("1.999");
  private static final BigDecimal
      SEPARATELY_BILLABLE_UNDER_13_HEMODIALYSIS_MODE_PAYMENT_MULTIPLIER_2021 =
          new BigDecimal("1.406");
  private static final BigDecimal
      SEPARATELY_BILLABLE_UNDER_13_PERITONEAL_DIALYSIS_MODE_PAYMENT_MULTIPLIER_2021 =
          new BigDecimal("0.410");
  private static final BigDecimal TRAINING_ADD_ON_PAYMENT_AMOUNT_2021 = new BigDecimal("95.60");

  public EsrdPricerContext2021(
      EsrdClaimPricingRequest input, EsrdClaimPricingResponse output, DataTables dataTables) {
    super(input, output, dataTables);
  }

  @Override
  public String getCalculationVersion() {
    return CALCULATION_VERSION_2021;
  }

  @Override
  public @FixedValue BigDecimal getAdjustedAverageMapAmountOver17() {
    return ADJUSTED_AVERAGE_MAP_AMOUNT_OVER_17_2021;
  }

  @Override
  public @FixedValue BigDecimal getAdjustedAverageMapAmountUnder18() {
    return ADJUSTED_AVERAGE_MAP_AMOUNT_UNDER_18_2021;
  }

  @Override
  public @FixedValue BigDecimal getBasePaymentRate() {
    return BASE_PAYMENT_RATE_2021;
  }

  @Override
  public @FixedValue BigDecimal getBsaNationalAverage() {
    return BSA_NATIONAL_AVERAGE_2021;
  }

  @Override
  public @FixedValue BigDecimal getBundledBasePaymentRate() {
    return BUNDLED_BASE_PAYMENT_RATE_2021;
  }

  public @FixedValue BigDecimal getBundledBasePaymentRateAki() {
    return BUNDLED_BASE_PAYMENT_RATE_AKI_2021;
  }

  @Override
  public @FixedValue BigDecimal getBundledNationalLaborPercentage() {
    return BUNDLED_NATIONAL_LABOR_PERCENTAGE_2021;
  }

  @Override
  public @FixedValue BigDecimal getBundledNationalNonLaborPercent() {
    return BUNDLED_NATIONAL_NON_LABOR_PERCENTAGE_2021;
  }

  @Override
  public @FixedValue BigDecimal getCaseMixAge18To44Multiplier() {
    return CASE_MIX_AGE_18_TO_44_MULTIPLIER_2021;
  }

  @Override
  public @FixedValue BigDecimal getCaseMixAge45To59Multiplier() {
    return CASE_MIX_AGE_45_TO_59_MULTIPLIER_2021;
  }

  @Override
  public @FixedValue BigDecimal getCaseMixAge60To69Multiplier() {
    return CASE_MIX_AGE_60_TO_69_MULTIPLIER_2021;
  }

  @Override
  public @FixedValue BigDecimal getCaseMixAge70To79Multiplier() {
    return CASE_MIX_AGE_70_TO_79_MULTIPLIER_2021;
  }

  @Override
  public @FixedValue BigDecimal getCaseMixAge80PlusMultiplier() {
    return CASE_MIX_AGE_80_PLUS_MULTIPLIER_2021;
  }

  @Override
  public @FixedValue BigDecimal getCaseMixBmiUnderEighteenPointFiveMultiplier() {
    return CASE_MIX_BMI_UNDER_18_5_MULTIPLIER_2021;
  }

  @Override
  public @FixedValue BigDecimal getCaseMixBsaMultiplier() {
    return CASE_MIX_BSA_MULTIPLIER_2021;
  }

  @Override
  public @FixedValue BigDecimal getCaseMixGastrointestinalBleedMultiplier() {
    return CASE_MIX_GASTROINTESTINAL_BLEED_MULTIPLIER_2021;
  }

  @Override
  public @FixedValue BigDecimal getCaseMixLowVolumeAdjustmentLessThan4000Multiplier() {
    return CASE_MIX_LOW_VOLUME_ADJUSTMENT_LT_400_MULTIPLIER_2021;
  }

  public @FixedValue BigDecimal getCaseMixLowVolumeAdjustmentLessThan3000Multiplier() {
    return BigDecimal.ONE;
  }

  public @FixedValue BigDecimal getCaseMixLowVolumeAdjustment3001To3999Multiplier() {
    return BigDecimal.ONE;
  }

  @Override
  public @FixedValue BigDecimal getCaseMixMyelodysplasticSyndromeMultiplier() {
    return CASE_MIX_MYELODYSPLASTIC_SYNDROME_MULTIPLIER_2021;
  }

  @Override
  public @FixedValue BigDecimal getCaseMixOnsetLessThanOrEqualTo120Multiplier() {
    return CASE_MIX_ONSET_LTE_120_MULTIPLIER_2021;
  }

  @Override
  public @FixedValue BigDecimal getCaseMixPericarditisMultiplier() {
    return CASE_MIX_PERICARDITIS_MULTIPLIER_2021;
  }

  @Override
  public @FixedValue BigDecimal getCaseMixRuralMultiplier() {
    return CASE_MIX_RURAL_MULTIPLIER_2021;
  }

  @Override
  public @FixedValue BigDecimal getCaseMixSickleCellMultiplier() {
    return CASE_MIX_SICKLE_CELL_MULTIPLIER_2021;
  }

  @Override
  public @FixedValue BigDecimal getDrugAddOn() {
    return DRUG_ADD_ON_2021;
  }

  @Override
  public @FixedValue BigDecimal getEtcHdpaPercent() {
    return ETC_HDPA_PERCENT_2021;
  }

  @Override
  public BigDecimal getCraTpniesOffset() {
    return CRA_TPNIES_OFFSET_2021;
  }

  @Override
  public @FixedValue BigDecimal getExpandedBundle13To17HemodialysisModePaymentMultiplier() {
    return EXPANDED_BUNDLE_13_TO_17_HEMODIALYSIS_MODE_PAYMENT_MULTIPLIER_2021;
  }

  @Override
  public @FixedValue BigDecimal getExpandedBundle13To17PeritonealDialysisModePaymentMultiplier() {
    return EXPANDED_BUNDLE_13_TO_17_PERITONEAL_DIALYSIS_MODE_PAYMENT_MULTIPLIER_2021;
  }

  @Override
  public @FixedValue BigDecimal getExpandedBundleUnder13HemodialysisModePaymentMultiplier() {
    return EXPANDED_BUNDLE_UNDER_13_HEMODIALYSIS_MODE_PAYMENT_MULTIPLIER_2021;
  }

  @Override
  public @FixedValue BigDecimal getExpandedBundleUnder13PeritonealDialysisModePaymentMultiplier() {
    return EXPANDED_BUNDLE_UNDER_13_PERITONEAL_DIALYSIS_MODE_PAYMENT_MULTIPLIER_2021;
  }

  @Override
  public @FixedValue BigDecimal getFixedDollarLossOver17() {
    return FIXED_DOLLAR_LOSS_OVER_17_2021;
  }

  @Override
  public @FixedValue BigDecimal getFixedDollarLossUnder18() {
    return FIXED_DOLLAR_LOSS_UNDER_18_2021;
  }

  @Override
  public @FixedValue BigDecimal getLossSharingPercentageOver17() {
    return LOSS_SHARING_PERCENTAGE_OVER_17_2021;
  }

  @Override
  public @FixedValue BigDecimal getLossSharingPercentageUnder18() {
    return LOSS_SHARING_PERCENTAGE_UNDER_18_2021;
  }

  @Override
  public @FixedValue BigDecimal getNationalLaborPercent() {
    return NATIONAL_LABOR_PERCENTAGE_2021;
  }

  @Override
  public @FixedValue BigDecimal getNationalNonLaborPercent() {
    return NATIONAL_NON_LABOR_PERCENTAGE_2021;
  }

  @Override
  public @FixedValue BigDecimal getNetworkReductionPerDiemAmount() {
    return NETWORK_REDUCTION_AMOUNT_PER_DIEM_2021;
  }

  @Override
  public @FixedValue BigDecimal getNetworkReductionFullAmount() {
    return NETWORK_REDUCTION_AMOUNT_FULL_2021;
  }

  @Override
  public @FixedValue BigDecimal getSeparatelyBillable13To17HemodialysisModePaymentMultiplier() {
    return SEPARATELY_BILLABLE_13_TO_17_HEMODIALYSIS_MODE_PAYMENT_MULTIPLIER_2021;
  }

  @Override
  public @FixedValue BigDecimal
      getSeparatelyBillable13To17PeritonealDialysisModePaymentMultiplier() {
    return SEPARATELY_BILLABLE_13_TO_17_PERITONEAL_DIALYSIS_MODE_PAYMENT_MULTIPLIER_2021;
  }

  @Override
  public @FixedValue BigDecimal getSeparatelyBillableAge18To44Multiplier() {
    return SEPARATELY_BILLABLE_AGE_18_TO_44_MULTIPLIER_2021;
  }

  @Override
  public @FixedValue BigDecimal getSeparatelyBillableAge45To59Multiplier() {
    return SEPARATELY_BILLABLE_AGE_45_TO_59_MULTIPLIER_2021;
  }

  @Override
  public @FixedValue BigDecimal getSeparatelyBillableAge60To69Multiplier() {
    return SEPARATELY_BILLABLE_AGE_60_TO_69_MULTIPLIER_2021;
  }

  @Override
  public @FixedValue BigDecimal getSeparatelyBillableAge70To79Multiplier() {
    return SEPARATELY_BILLABLE_AGE_70_TO_79_MULTIPLIER_2021;
  }

  @Override
  public @FixedValue BigDecimal getSeparatelyBillableAge80PlusMultiplier() {
    return SEPARATELY_BILLABLE_AGE_80_PLUS_MULTIPLIER_2021;
  }

  @Override
  public @FixedValue BigDecimal getSeparatelyBillableBmiUnderCutoffMultiplier() {
    return SEPARATELY_BILLABLE_BMI_UNDER_CUTOFF_MULTIPLIER_2021;
  }

  @Override
  public @FixedValue BigDecimal getSeparatelyBillableBsa() {
    return SEPARATELY_BILLABLE_BSA_2021;
  }

  @Override
  public @FixedValue BigDecimal getSeparatelyBillableGastrointestinalBleedMultiplier() {
    return SEPARATELY_BILLABLE_GASTROINTESTINAL_BLEED_MULTIPLIER_2021;
  }

  @Override
  public @FixedValue BigDecimal getSeparatelyBillableLowVolumeAdjustmentLessThan4000Multiplier() {
    return SEPARATELY_BILLABLE_LOW_VOLUME_LT_400_MULTIPLIER_2021;
  }

  public BigDecimal getSeparatelyBillableLowVolumeAdjustmentLessThan3000Multiplier() {
    return BigDecimal.ONE;
  }

  public BigDecimal getSeparatelyBillableLowVolumeAdjustment3000To3999Multiplier() {
    return BigDecimal.ONE;
  }

  @Override
  public @FixedValue BigDecimal getSeparatelyBillableMyelodysplasticSyndromeMultiplier() {
    return SEPARATELY_BILLABLE_MYELODYSPLASTIC_SYNDROME_MULTIPLIER_2021;
  }

  @Override
  public @FixedValue BigDecimal getSeparatelyBillableOnsetLessThanOrEqualTo120Multiplier() {
    return SEPARATELY_BILLABLE_ONSET_LTE_120_MULTIPLIER_2021;
  }

  @Override
  public @FixedValue BigDecimal getSeparatelyBillablePericarditisMultiplier() {
    return SEPARATELY_BILLABLE_PERICARDITIS_MULTIPLIER_2021;
  }

  @Override
  public @FixedValue BigDecimal getSeparatelyBillableRuralMultiplier() {
    return SEPARATELY_BILLABLE_RURAL_MULTIPLIER_2021;
  }

  @Override
  public @FixedValue BigDecimal getSeparatelyBillableSickleCellMultiplier() {
    return SEPARATELY_BILLABLE_SICKLE_CELL_MULTIPLIER_2021;
  }

  @Override
  public @FixedValue BigDecimal getSeparatelyBillableUnder13HemodialysisModePaymentMultiplier() {
    return SEPARATELY_BILLABLE_UNDER_13_HEMODIALYSIS_MODE_PAYMENT_MULTIPLIER_2021;
  }

  @Override
  public @FixedValue BigDecimal
      getSeparatelyBillableUnder13PeritonealDialysisModePaymentMultiplier() {
    return SEPARATELY_BILLABLE_UNDER_13_PERITONEAL_DIALYSIS_MODE_PAYMENT_MULTIPLIER_2021;
  }

  @Override
  public @FixedValue BigDecimal getTrainingAddOnPaymentAmount() {
    return TRAINING_ADD_ON_PAYMENT_AMOUNT_2021;
  }
}
