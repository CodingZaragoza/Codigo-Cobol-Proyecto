package gov.cms.fiss.pricers.hospice.core;

import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingRequest;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingResponse;
import gov.cms.fiss.pricers.hospice.core.tables.DataTables;
import java.math.BigDecimal;

public class Hospice2025PricerContext extends HospicePricerContext {
  public static final String CALCULATION_VERSION_2025 = "2025.0";
  private static final BigDecimal GENERAL_INPATIENT_NLS_RATE_QUALITY = new BigDecimal("410.46");
  private static final BigDecimal GENERAL_INPATIENT_NLS_RATE_2025 = new BigDecimal("427.06");
  private static final BigDecimal GENERAL_INPATIENT_LS_RATE_QUALITY_2025 = new BigDecimal("714.10");
  private static final BigDecimal GENERAL_INPATIENT_LS_RATE_2025 = new BigDecimal("742.98");
  private static final BigDecimal INPATIENT_RESPITE_LS_RATE_QUALITY_2025 = new BigDecimal("304.15");
  private static final BigDecimal INPATIENT_RESPITE_NLS_RATE_2025 = new BigDecimal("202.32");
  private static final BigDecimal INPATIENT_RESPITE_NLS_RATE_QUALITY_2025 =
      new BigDecimal("194.46");
  private static final BigDecimal INPATIENT_RESPITE_LS_RATE_2025 = new BigDecimal("316.46");
  private static final BigDecimal HIGH_ROUTINE_HOME_CARE_NLS_RATE_QUALITY_2025 =
      new BigDecimal("73.40");
  private static final BigDecimal HIGH_ROUTINE_HOME_CARE_NLS_RATE_2025 = new BigDecimal("76.37");
  private static final BigDecimal HIGH_ROUTINE_HOME_CARE_LS_RATE_QUALITY_2025 =
      new BigDecimal("142.48");
  private static final BigDecimal HIGH_ROUTINE_HOME_CARE_LS_RATE_2025 = new BigDecimal("148.25");
  private static final BigDecimal LOW_ROUTINE_HOME_CARE_LS_RATE_QUALITY_2025 =
      new BigDecimal("112.23");
  private static final BigDecimal LOW_ROUTINE_HOME_CARE_LS_RATE_2025 = new BigDecimal("116.77");
  private static final BigDecimal LOW_ROUTINE_HOME_CARE_NLS_RATE_QUALITY_2025 =
      new BigDecimal("57.82");
  private static final BigDecimal LOW_ROUTINE_HOME_CARE_NLS_RATE_2025 = new BigDecimal("60.15");
  private static final BigDecimal CONTINUOUS_HOME_CARE_LS_RATE_QUALITY_2025 =
      new BigDecimal("1169.86");
  private static final BigDecimal CONTINUOUS_HOME_CARE_LS_RATE_2025 = new BigDecimal("1217.18");
  private static final BigDecimal CONTINUOUS_HOME_CARE_NLS_RATE_QUALITY_2025 =
      new BigDecimal("385.81");
  private static final BigDecimal CONTINUOUS_HOME_CARE_NLS_RATE_2025 = new BigDecimal("401.41");

  public Hospice2025PricerContext(
      HospiceClaimPricingRequest input,
      HospiceClaimPricingResponse hospiceOutput,
      DataTables dataTables) {
    super(input, hospiceOutput, dataTables);
  }

  @Override
  protected String getCalculationVersion() {
    return CALCULATION_VERSION_2025;
  }

  @Override
  public BigDecimal getGeneralInpatientLsRateQuality() {
    return GENERAL_INPATIENT_LS_RATE_QUALITY_2025;
  }

  @Override
  public BigDecimal getGeneralInpatientNlsRateQuality() {
    return GENERAL_INPATIENT_NLS_RATE_QUALITY;
  }

  @Override
  public BigDecimal getGeneralInpatientLsRate() {
    return GENERAL_INPATIENT_LS_RATE_2025;
  }

  @Override
  public BigDecimal getGeneralInpatientNlsRate() {
    return GENERAL_INPATIENT_NLS_RATE_2025;
  }

  @Override
  public BigDecimal getInpatientRespiteLsRateQuality() {
    return INPATIENT_RESPITE_LS_RATE_QUALITY_2025;
  }

  @Override
  public BigDecimal getInpatientRespiteLsRate() {
    return INPATIENT_RESPITE_LS_RATE_2025;
  }

  @Override
  public BigDecimal getInpatientRespiteNlsRateQuality() {
    return INPATIENT_RESPITE_NLS_RATE_QUALITY_2025;
  }

  @Override
  public BigDecimal getInpatientRespiteNlsRate() {
    return INPATIENT_RESPITE_NLS_RATE_2025;
  }

  @Override
  public BigDecimal getHighRoutineHomeCareLsRateQuality() {
    return HIGH_ROUTINE_HOME_CARE_LS_RATE_QUALITY_2025;
  }

  @Override
  public BigDecimal getHighRoutineHomeCareNlsRateQuality() {
    return HIGH_ROUTINE_HOME_CARE_NLS_RATE_QUALITY_2025;
  }

  @Override
  public BigDecimal getLowRoutineHomeCareLsRateQuality() {
    return LOW_ROUTINE_HOME_CARE_LS_RATE_QUALITY_2025;
  }

  @Override
  public BigDecimal getLowRoutineHomeCareNlsRateQuality() {
    return LOW_ROUTINE_HOME_CARE_NLS_RATE_QUALITY_2025;
  }

  @Override
  public BigDecimal getContinuousHomeCareLsRateQuality() {
    return CONTINUOUS_HOME_CARE_LS_RATE_QUALITY_2025;
  }

  @Override
  public BigDecimal getContinuousHomeCareNlsRateQuality() {
    return CONTINUOUS_HOME_CARE_NLS_RATE_QUALITY_2025;
  }

  @Override
  public BigDecimal getHighRoutineHomeCareLsRate() {
    return HIGH_ROUTINE_HOME_CARE_LS_RATE_2025;
  }

  @Override
  public BigDecimal getHighRoutineHomeCareNlsRate() {
    return HIGH_ROUTINE_HOME_CARE_NLS_RATE_2025;
  }

  @Override
  public BigDecimal getLowRoutineHomeCareLsRate() {
    return LOW_ROUTINE_HOME_CARE_LS_RATE_2025;
  }

  @Override
  public BigDecimal getLowRoutineHomeCareNlsRate() {
    return LOW_ROUTINE_HOME_CARE_NLS_RATE_2025;
  }

  @Override
  public BigDecimal getContinuousHomeCareLsRate() {
    return CONTINUOUS_HOME_CARE_LS_RATE_2025;
  }

  @Override
  public BigDecimal getContinuousHomeCareNlsRate() {
    return CONTINUOUS_HOME_CARE_NLS_RATE_2025;
  }
}
