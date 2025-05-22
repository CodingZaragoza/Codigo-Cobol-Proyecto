package gov.cms.fiss.pricers.hospice.core;

import gov.cms.fiss.pricers.common.api.annotations.FixedValue;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingRequest;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingResponse;
import gov.cms.fiss.pricers.hospice.core.tables.DataTables;
import java.math.BigDecimal;

public class Hospice2021PricerContext extends HospicePricerContext {
  public static final String CALCULATION_VERSION_2021 = "2021.0";
  private static final BigDecimal GENERAL_INPATIENT_LS_RATE_QUALITY_2021 = new BigDecimal("656.25");
  private static final BigDecimal GENERAL_INPATIENT_NLS_RATE_QUALITY = new BigDecimal("368.98");
  private static final BigDecimal GENERAL_INPATIENT_LS_RATE_2021 = new BigDecimal("669.33");
  private static final BigDecimal GENERAL_INPATIENT_NLS_RATE_2021 = new BigDecimal("376.33");
  private static final BigDecimal INPATIENT_RESPITE_LS_RATE_QUALITY_2021 = new BigDecimal("244.71");
  private static final BigDecimal INPATIENT_RESPITE_LS_RATE_2021 = new BigDecimal("249.59");
  private static final BigDecimal INPATIENT_RESPITE_NLS_RATE_QUALITY_2021 =
      new BigDecimal("207.37");
  private static final BigDecimal INPATIENT_RESPITE_NLS_RATE_2021 = new BigDecimal("211.50");
  private static final BigDecimal HIGH_ROUTINE_HOME_CARE_LS_RATE_QUALITY_2021 =
      new BigDecimal("134.23");
  private static final BigDecimal HIGH_ROUTINE_HOME_CARE_NLS_RATE_QUALITY_2021 =
      new BigDecimal("61.13");
  private static final BigDecimal LOW_ROUTINE_HOME_CARE_LS_RATE_QUALITY_2021 =
      new BigDecimal("106.10");
  private static final BigDecimal LOW_ROUTINE_HOME_CARE_NLS_RATE_QUALITY_2021 =
      new BigDecimal("48.32");
  private static final BigDecimal CONTINUOUS_HOME_CARE_LS_RATE_QUALITY_2021 =
      new BigDecimal("964.99");
  private static final BigDecimal CONTINUOUS_HOME_CARE_NLS_RATE_QUALITY_2021 =
      new BigDecimal("439.45");
  private static final BigDecimal HIGH_ROUTINE_HOME_CARE_LS_RATE_2021 = new BigDecimal("136.90");
  private static final BigDecimal HIGH_ROUTINE_HOME_CARE_NLS_RATE_2021 = new BigDecimal("62.35");
  private static final BigDecimal LOW_ROUTINE_HOME_CARE_LS_RATE_2021 = new BigDecimal("108.21");
  private static final BigDecimal LOW_ROUTINE_HOME_CARE_NLS_RATE_2021 = new BigDecimal("49.28");
  private static final BigDecimal CONTINUOUS_HOME_CARE_LS_RATE_2021 = new BigDecimal("984.21");
  private static final BigDecimal CONTINUOUS_HOME_CARE_NLS_RATE_2021 = new BigDecimal("448.20");

  public Hospice2021PricerContext(
      HospiceClaimPricingRequest input,
      HospiceClaimPricingResponse hospiceOutput,
      DataTables dataTables) {
    super(input, hospiceOutput, dataTables);
  }

  @Override
  protected String getCalculationVersion() {
    return CALCULATION_VERSION_2021;
  }

  @Override
  public BigDecimal getGeneralInpatientLsRateQuality() {
    return GENERAL_INPATIENT_LS_RATE_QUALITY_2021;
  }

  @Override
  public BigDecimal getGeneralInpatientNlsRateQuality() {
    return GENERAL_INPATIENT_NLS_RATE_QUALITY;
  }

  @Override
  public BigDecimal getGeneralInpatientLsRate() {
    return GENERAL_INPATIENT_LS_RATE_2021;
  }

  @Override
  public BigDecimal getGeneralInpatientNlsRate() {
    return GENERAL_INPATIENT_NLS_RATE_2021;
  }

  @Override
  public BigDecimal getInpatientRespiteLsRateQuality() {
    return INPATIENT_RESPITE_LS_RATE_QUALITY_2021;
  }

  @Override
  public BigDecimal getInpatientRespiteLsRate() {
    return INPATIENT_RESPITE_LS_RATE_2021;
  }

  @Override
  public BigDecimal getInpatientRespiteNlsRateQuality() {
    return INPATIENT_RESPITE_NLS_RATE_QUALITY_2021;
  }

  @Override
  public BigDecimal getInpatientRespiteNlsRate() {
    return INPATIENT_RESPITE_NLS_RATE_2021;
  }

  @Override
  public @FixedValue BigDecimal getHighRoutineHomeCareLsRateQuality() {
    return HIGH_ROUTINE_HOME_CARE_LS_RATE_QUALITY_2021;
  }

  @Override
  public @FixedValue BigDecimal getHighRoutineHomeCareNlsRateQuality() {
    return HIGH_ROUTINE_HOME_CARE_NLS_RATE_QUALITY_2021;
  }

  @Override
  public @FixedValue BigDecimal getLowRoutineHomeCareLsRateQuality() {
    return LOW_ROUTINE_HOME_CARE_LS_RATE_QUALITY_2021;
  }

  @Override
  public @FixedValue BigDecimal getLowRoutineHomeCareNlsRateQuality() {
    return LOW_ROUTINE_HOME_CARE_NLS_RATE_QUALITY_2021;
  }

  @Override
  public BigDecimal getContinuousHomeCareLsRateQuality() {
    return CONTINUOUS_HOME_CARE_LS_RATE_QUALITY_2021;
  }

  @Override
  public BigDecimal getContinuousHomeCareNlsRateQuality() {
    return CONTINUOUS_HOME_CARE_NLS_RATE_QUALITY_2021;
  }

  @Override
  public @FixedValue BigDecimal getHighRoutineHomeCareLsRate() {
    return HIGH_ROUTINE_HOME_CARE_LS_RATE_2021;
  }

  @Override
  public @FixedValue BigDecimal getHighRoutineHomeCareNlsRate() {
    return HIGH_ROUTINE_HOME_CARE_NLS_RATE_2021;
  }

  @Override
  public @FixedValue BigDecimal getLowRoutineHomeCareLsRate() {
    return LOW_ROUTINE_HOME_CARE_LS_RATE_2021;
  }

  @Override
  public @FixedValue BigDecimal getLowRoutineHomeCareNlsRate() {
    return LOW_ROUTINE_HOME_CARE_NLS_RATE_2021;
  }

  @Override
  public @FixedValue BigDecimal getContinuousHomeCareLsRate() {
    return CONTINUOUS_HOME_CARE_LS_RATE_2021;
  }

  @Override
  public @FixedValue BigDecimal getContinuousHomeCareNlsRate() {
    return CONTINUOUS_HOME_CARE_NLS_RATE_2021;
  }
}
