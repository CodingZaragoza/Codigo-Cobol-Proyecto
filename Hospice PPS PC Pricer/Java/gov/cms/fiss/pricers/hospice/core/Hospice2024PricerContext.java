package gov.cms.fiss.pricers.hospice.core;

import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingRequest;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingResponse;
import gov.cms.fiss.pricers.hospice.core.tables.DataTables;
import java.math.BigDecimal;

public class Hospice2024PricerContext extends HospicePricerContext {
  public static final String CALCULATION_VERSION_2024 = "2024.0";
  private static final BigDecimal GENERAL_INPATIENT_NLS_RATE_QUALITY = new BigDecimal("401.82");
  private static final BigDecimal GENERAL_INPATIENT_NLS_RATE_2024 = new BigDecimal("418.04");
  private static final BigDecimal GENERAL_INPATIENT_LS_RATE_QUALITY_2024 = new BigDecimal("699.05");
  private static final BigDecimal GENERAL_INPATIENT_LS_RATE_2024 = new BigDecimal("727.27");
  private static final BigDecimal INPATIENT_RESPITE_LS_RATE_QUALITY_2024 = new BigDecimal("297.69");
  private static final BigDecimal INPATIENT_RESPITE_LS_RATE_2024 = new BigDecimal("309.70");
  private static final BigDecimal INPATIENT_RESPITE_NLS_RATE_QUALITY_2024 =
      new BigDecimal("190.32");
  private static final BigDecimal INPATIENT_RESPITE_NLS_RATE_2024 = new BigDecimal("198.01");
  private static final BigDecimal HIGH_ROUTINE_HOME_CARE_LS_RATE_QUALITY_2024 =
      new BigDecimal("138.51");
  private static final BigDecimal HIGH_ROUTINE_HOME_CARE_LS_RATE_2024 = new BigDecimal("144.10");
  private static final BigDecimal HIGH_ROUTINE_HOME_CARE_NLS_RATE_QUALITY_2024 =
      new BigDecimal("71.35");
  private static final BigDecimal HIGH_ROUTINE_HOME_CARE_NLS_RATE_2024 = new BigDecimal("74.23");
  private static final BigDecimal LOW_ROUTINE_HOME_CARE_LS_RATE_QUALITY_2024 =
      new BigDecimal("109.34");
  private static final BigDecimal LOW_ROUTINE_HOME_CARE_LS_RATE_2024 = new BigDecimal("113.75");
  private static final BigDecimal LOW_ROUTINE_HOME_CARE_NLS_RATE_QUALITY_2024 =
      new BigDecimal("56.32");
  private static final BigDecimal LOW_ROUTINE_HOME_CARE_NLS_RATE_2024 = new BigDecimal("58.60");
  private static final BigDecimal CONTINUOUS_HOME_CARE_LS_RATE_QUALITY_2024 =
      new BigDecimal("1131.55");
  private static final BigDecimal CONTINUOUS_HOME_CARE_LS_RATE_2024 = new BigDecimal("1177.23");
  private static final BigDecimal CONTINUOUS_HOME_CARE_NLS_RATE_QUALITY_2024 =
      new BigDecimal("373.17");
  private static final BigDecimal CONTINUOUS_HOME_CARE_NLS_RATE_2024 = new BigDecimal("388.23");

  public Hospice2024PricerContext(
      HospiceClaimPricingRequest input,
      HospiceClaimPricingResponse hospiceOutput,
      DataTables dataTables) {
    super(input, hospiceOutput, dataTables);
  }

  @Override
  protected String getCalculationVersion() {
    return CALCULATION_VERSION_2024;
  }

  @Override
  public BigDecimal getGeneralInpatientLsRateQuality() {
    return GENERAL_INPATIENT_LS_RATE_QUALITY_2024;
  }

  @Override
  public BigDecimal getGeneralInpatientNlsRateQuality() {
    return GENERAL_INPATIENT_NLS_RATE_QUALITY;
  }

  @Override
  public BigDecimal getGeneralInpatientLsRate() {
    return GENERAL_INPATIENT_LS_RATE_2024;
  }

  @Override
  public BigDecimal getGeneralInpatientNlsRate() {
    return GENERAL_INPATIENT_NLS_RATE_2024;
  }

  @Override
  public BigDecimal getInpatientRespiteLsRateQuality() {
    return INPATIENT_RESPITE_LS_RATE_QUALITY_2024;
  }

  @Override
  public BigDecimal getInpatientRespiteLsRate() {
    return INPATIENT_RESPITE_LS_RATE_2024;
  }

  @Override
  public BigDecimal getInpatientRespiteNlsRateQuality() {
    return INPATIENT_RESPITE_NLS_RATE_QUALITY_2024;
  }

  @Override
  public BigDecimal getInpatientRespiteNlsRate() {
    return INPATIENT_RESPITE_NLS_RATE_2024;
  }

  @Override
  public BigDecimal getHighRoutineHomeCareLsRateQuality() {
    return HIGH_ROUTINE_HOME_CARE_LS_RATE_QUALITY_2024;
  }

  @Override
  public BigDecimal getHighRoutineHomeCareNlsRateQuality() {
    return HIGH_ROUTINE_HOME_CARE_NLS_RATE_QUALITY_2024;
  }

  @Override
  public BigDecimal getLowRoutineHomeCareLsRateQuality() {
    return LOW_ROUTINE_HOME_CARE_LS_RATE_QUALITY_2024;
  }

  @Override
  public BigDecimal getLowRoutineHomeCareNlsRateQuality() {
    return LOW_ROUTINE_HOME_CARE_NLS_RATE_QUALITY_2024;
  }

  @Override
  public BigDecimal getContinuousHomeCareLsRateQuality() {
    return CONTINUOUS_HOME_CARE_LS_RATE_QUALITY_2024;
  }

  @Override
  public BigDecimal getContinuousHomeCareNlsRateQuality() {
    return CONTINUOUS_HOME_CARE_NLS_RATE_QUALITY_2024;
  }

  @Override
  public BigDecimal getHighRoutineHomeCareLsRate() {
    return HIGH_ROUTINE_HOME_CARE_LS_RATE_2024;
  }

  @Override
  public BigDecimal getHighRoutineHomeCareNlsRate() {
    return HIGH_ROUTINE_HOME_CARE_NLS_RATE_2024;
  }

  @Override
  public BigDecimal getLowRoutineHomeCareLsRate() {
    return LOW_ROUTINE_HOME_CARE_LS_RATE_2024;
  }

  @Override
  public BigDecimal getLowRoutineHomeCareNlsRate() {
    return LOW_ROUTINE_HOME_CARE_NLS_RATE_2024;
  }

  @Override
  public BigDecimal getContinuousHomeCareLsRate() {
    return CONTINUOUS_HOME_CARE_LS_RATE_2024;
  }

  @Override
  public BigDecimal getContinuousHomeCareNlsRate() {
    return CONTINUOUS_HOME_CARE_NLS_RATE_2024;
  }
}
