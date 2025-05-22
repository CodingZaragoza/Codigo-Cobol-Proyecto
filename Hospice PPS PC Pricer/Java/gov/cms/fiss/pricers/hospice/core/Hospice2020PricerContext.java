package gov.cms.fiss.pricers.hospice.core;

import gov.cms.fiss.pricers.common.api.annotations.FixedValue;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingRequest;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingResponse;
import gov.cms.fiss.pricers.hospice.core.tables.DataTables;
import java.math.BigDecimal;

public class Hospice2020PricerContext extends HospicePricerContext {
  public static final String CALCULATION_VERSION_2020 = "2020.1";
  private static final BigDecimal GENERAL_INPATIENT_LS_RATE_QUALITY_2020 = new BigDecimal("640.96");
  private static final BigDecimal GENERAL_INPATIENT_NLS_RATE_QUALITY_2020 =
      new BigDecimal("360.39");
  private static final BigDecimal GENERAL_INPATIENT_LS_RATE_2020 = new BigDecimal("653.70");
  private static final BigDecimal GENERAL_INPATIENT_NLS_RATE_2020 = new BigDecimal("367.55");
  private static final BigDecimal INPATIENT_RESPITE_LS_RATE_QUALITY_2020 = new BigDecimal("238.89");
  private static final BigDecimal INPATIENT_RESPITE_LS_RATE_2020 = new BigDecimal("243.64");
  private static final BigDecimal INPATIENT_RESPITE_NLS_RATE_QUALITY_2020 =
      new BigDecimal("202.43");
  private static final BigDecimal INPATIENT_RESPITE_NLS_RATE_2020 = new BigDecimal("206.46");
  private static final BigDecimal HIGH_ROUTINE_HOME_CARE_LS_RATE_QUALITY_2020 =
      new BigDecimal("131.04");
  private static final BigDecimal HIGH_ROUTINE_HOME_CARE_NLS_RATE_QUALITY_2020 =
      new BigDecimal("59.67");
  private static final BigDecimal LOW_ROUTINE_HOME_CARE_LS_RATE_QUALITY_2020 =
      new BigDecimal("103.56");
  private static final BigDecimal LOW_ROUTINE_HOME_CARE_NLS_RATE_QUALITY_2020 =
      new BigDecimal("47.16");
  private static final BigDecimal CONTINUOUS_HOME_CARE_LS_RATE_QUALITY_2020 =
      new BigDecimal("940.24");
  private static final BigDecimal CONTINUOUS_HOME_CARE_NLS_RATE_QUALITY_2020 =
      new BigDecimal("428.18");
  private static final BigDecimal HIGH_ROUTINE_HOME_CARE_LS_RATE_2020 = new BigDecimal("133.64");
  private static final BigDecimal HIGH_ROUTINE_HOME_CARE_NLS_RATE_2020 = new BigDecimal("60.86");
  private static final BigDecimal LOW_ROUTINE_HOME_CARE_LS_RATE_2020 = new BigDecimal("105.62");
  private static final BigDecimal LOW_ROUTINE_HOME_CARE_NLS_RATE_2020 = new BigDecimal("48.10");
  private static final BigDecimal CONTINUOUS_HOME_CARE_LS_RATE_2020 = new BigDecimal("958.94");
  private static final BigDecimal CONTINUOUS_HOME_CARE_NLS_RATE_2020 = new BigDecimal("436.69");

  public Hospice2020PricerContext(
      HospiceClaimPricingRequest input, HospiceClaimPricingResponse output, DataTables dataTables) {
    super(input, output, dataTables);
  }

  @Override
  protected String getCalculationVersion() {
    return CALCULATION_VERSION_2020;
  }

  @Override
  public BigDecimal getGeneralInpatientLsRateQuality() {
    return GENERAL_INPATIENT_LS_RATE_QUALITY_2020;
  }

  @Override
  public BigDecimal getGeneralInpatientNlsRateQuality() {
    return GENERAL_INPATIENT_NLS_RATE_QUALITY_2020;
  }

  @Override
  public BigDecimal getGeneralInpatientLsRate() {
    return GENERAL_INPATIENT_LS_RATE_2020;
  }

  @Override
  public BigDecimal getGeneralInpatientNlsRate() {
    return GENERAL_INPATIENT_NLS_RATE_2020;
  }

  @Override
  public BigDecimal getInpatientRespiteLsRateQuality() {
    return INPATIENT_RESPITE_LS_RATE_QUALITY_2020;
  }

  @Override
  public BigDecimal getInpatientRespiteLsRate() {
    return INPATIENT_RESPITE_LS_RATE_2020;
  }

  @Override
  public BigDecimal getInpatientRespiteNlsRateQuality() {
    return INPATIENT_RESPITE_NLS_RATE_QUALITY_2020;
  }

  @Override
  public BigDecimal getInpatientRespiteNlsRate() {
    return INPATIENT_RESPITE_NLS_RATE_2020;
  }

  @Override
  public @FixedValue BigDecimal getHighRoutineHomeCareLsRateQuality() {
    return HIGH_ROUTINE_HOME_CARE_LS_RATE_QUALITY_2020;
  }

  @Override
  public @FixedValue BigDecimal getHighRoutineHomeCareNlsRateQuality() {
    return HIGH_ROUTINE_HOME_CARE_NLS_RATE_QUALITY_2020;
  }

  @Override
  public @FixedValue BigDecimal getLowRoutineHomeCareLsRateQuality() {
    return LOW_ROUTINE_HOME_CARE_LS_RATE_QUALITY_2020;
  }

  @Override
  public @FixedValue BigDecimal getLowRoutineHomeCareNlsRateQuality() {
    return LOW_ROUTINE_HOME_CARE_NLS_RATE_QUALITY_2020;
  }

  @Override
  public BigDecimal getContinuousHomeCareLsRateQuality() {
    return CONTINUOUS_HOME_CARE_LS_RATE_QUALITY_2020;
  }

  @Override
  public BigDecimal getContinuousHomeCareNlsRateQuality() {
    return CONTINUOUS_HOME_CARE_NLS_RATE_QUALITY_2020;
  }

  @Override
  public @FixedValue BigDecimal getHighRoutineHomeCareLsRate() {
    return HIGH_ROUTINE_HOME_CARE_LS_RATE_2020;
  }

  @Override
  public @FixedValue BigDecimal getHighRoutineHomeCareNlsRate() {
    return HIGH_ROUTINE_HOME_CARE_NLS_RATE_2020;
  }

  @Override
  public @FixedValue BigDecimal getLowRoutineHomeCareLsRate() {
    return LOW_ROUTINE_HOME_CARE_LS_RATE_2020;
  }

  @Override
  public @FixedValue BigDecimal getLowRoutineHomeCareNlsRate() {
    return LOW_ROUTINE_HOME_CARE_NLS_RATE_2020;
  }

  @Override
  public @FixedValue BigDecimal getContinuousHomeCareLsRate() {
    return CONTINUOUS_HOME_CARE_LS_RATE_2020;
  }

  @Override
  public @FixedValue BigDecimal getContinuousHomeCareNlsRate() {
    return CONTINUOUS_HOME_CARE_NLS_RATE_2020;
  }
}
