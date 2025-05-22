package gov.cms.fiss.pricers.hospice.core;

import gov.cms.fiss.pricers.common.api.annotations.FixedValue;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingRequest;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingResponse;
import gov.cms.fiss.pricers.hospice.core.tables.DataTables;
import java.math.BigDecimal;

public class Hospice2022PricerContext extends HospicePricerContext {

  public static final String CALCULATION_VERSION_2022 = "2022.0";

  private static final BigDecimal HIGH_ROUTINE_HOME_CARE_LS_RATE_2022 = new BigDecimal("134.24");
  private static final BigDecimal HIGH_ROUTINE_HOME_CARE_NLS_RATE_2022 = new BigDecimal("69.16");
  private static final BigDecimal LOW_ROUTINE_HOME_CARE_LS_RATE_2022 = new BigDecimal("106.09");
  private static final BigDecimal LOW_ROUTINE_HOME_CARE_NLS_RATE_2022 = new BigDecimal("54.65");
  private static final BigDecimal CONTINUOUS_HOME_CARE_LS_RATE_2022 = new BigDecimal("1099.82");
  private static final BigDecimal CONTINUOUS_HOME_CARE_NLS_RATE_2022 = new BigDecimal("362.70");
  private static final BigDecimal INPATIENT_RESPITE_LS_RATE_2022 = new BigDecimal("288.99");
  private static final BigDecimal INPATIENT_RESPITE_NLS_RATE_2022 = new BigDecimal("184.76");
  private static final BigDecimal GENERAL_INPATIENT_LS_RATE_2022 = new BigDecimal("678.36");
  private static final BigDecimal GENERAL_INPATIENT_NLS_RATE_2022 = new BigDecimal("389.92");

  private static final BigDecimal HIGH_ROUTINE_HOME_CARE_LS_RATE_QUALITY_2022 =
      new BigDecimal("131.61");
  private static final BigDecimal HIGH_ROUTINE_HOME_CARE_NLS_RATE_QUALITY_2022 =
      new BigDecimal("67.80");
  private static final BigDecimal LOW_ROUTINE_HOME_CARE_LS_RATE_QUALITY_2022 =
      new BigDecimal("104.00");
  private static final BigDecimal LOW_ROUTINE_HOME_CARE_NLS_RATE_QUALITY_2022 =
      new BigDecimal("53.58");
  private static final BigDecimal CONTINUOUS_HOME_CARE_LS_RATE_QUALITY_2022 =
      new BigDecimal("1078.25");
  private static final BigDecimal CONTINUOUS_HOME_CARE_NLS_RATE_QUALITY_2022 =
      new BigDecimal("355.59");
  private static final BigDecimal INPATIENT_RESPITE_LS_RATE_QUALITY_2022 = new BigDecimal("283.32");
  private static final BigDecimal INPATIENT_RESPITE_NLS_RATE_QUALITY_2022 =
      new BigDecimal("181.14");
  private static final BigDecimal GENERAL_INPATIENT_LS_RATE_QUALITY_2022 = new BigDecimal("665.05");
  private static final BigDecimal GENERAL_INPATIENT_NLS_RATE_QUALITY = new BigDecimal("382.28");

  public Hospice2022PricerContext(
      HospiceClaimPricingRequest input,
      HospiceClaimPricingResponse hospiceOutput,
      DataTables dataTables) {
    super(input, hospiceOutput, dataTables);
  }

  @Override
  protected String getCalculationVersion() {
    return CALCULATION_VERSION_2022;
  }

  @Override
  public BigDecimal getGeneralInpatientLsRateQuality() {
    return GENERAL_INPATIENT_LS_RATE_QUALITY_2022;
  }

  @Override
  public BigDecimal getGeneralInpatientNlsRateQuality() {
    return GENERAL_INPATIENT_NLS_RATE_QUALITY;
  }

  @Override
  public BigDecimal getGeneralInpatientLsRate() {
    return GENERAL_INPATIENT_LS_RATE_2022;
  }

  @Override
  public BigDecimal getGeneralInpatientNlsRate() {
    return GENERAL_INPATIENT_NLS_RATE_2022;
  }

  @Override
  public BigDecimal getInpatientRespiteLsRateQuality() {
    return INPATIENT_RESPITE_LS_RATE_QUALITY_2022;
  }

  @Override
  public BigDecimal getInpatientRespiteLsRate() {
    return INPATIENT_RESPITE_LS_RATE_2022;
  }

  @Override
  public BigDecimal getInpatientRespiteNlsRateQuality() {
    return INPATIENT_RESPITE_NLS_RATE_QUALITY_2022;
  }

  @Override
  public BigDecimal getInpatientRespiteNlsRate() {
    return INPATIENT_RESPITE_NLS_RATE_2022;
  }

  @Override
  public @FixedValue BigDecimal getHighRoutineHomeCareLsRateQuality() {
    return HIGH_ROUTINE_HOME_CARE_LS_RATE_QUALITY_2022;
  }

  @Override
  public @FixedValue BigDecimal getHighRoutineHomeCareNlsRateQuality() {
    return HIGH_ROUTINE_HOME_CARE_NLS_RATE_QUALITY_2022;
  }

  @Override
  public @FixedValue BigDecimal getLowRoutineHomeCareLsRateQuality() {
    return LOW_ROUTINE_HOME_CARE_LS_RATE_QUALITY_2022;
  }

  @Override
  public @FixedValue BigDecimal getLowRoutineHomeCareNlsRateQuality() {
    return LOW_ROUTINE_HOME_CARE_NLS_RATE_QUALITY_2022;
  }

  @Override
  public BigDecimal getContinuousHomeCareLsRateQuality() {
    return CONTINUOUS_HOME_CARE_LS_RATE_QUALITY_2022;
  }

  @Override
  public BigDecimal getContinuousHomeCareNlsRateQuality() {
    return CONTINUOUS_HOME_CARE_NLS_RATE_QUALITY_2022;
  }

  @Override
  public @FixedValue BigDecimal getHighRoutineHomeCareLsRate() {
    return HIGH_ROUTINE_HOME_CARE_LS_RATE_2022;
  }

  @Override
  public @FixedValue BigDecimal getHighRoutineHomeCareNlsRate() {
    return HIGH_ROUTINE_HOME_CARE_NLS_RATE_2022;
  }

  @Override
  public @FixedValue BigDecimal getLowRoutineHomeCareLsRate() {
    return LOW_ROUTINE_HOME_CARE_LS_RATE_2022;
  }

  @Override
  public @FixedValue BigDecimal getLowRoutineHomeCareNlsRate() {
    return LOW_ROUTINE_HOME_CARE_NLS_RATE_2022;
  }

  @Override
  public @FixedValue BigDecimal getContinuousHomeCareLsRate() {
    return CONTINUOUS_HOME_CARE_LS_RATE_2022;
  }

  @Override
  public @FixedValue BigDecimal getContinuousHomeCareNlsRate() {
    return CONTINUOUS_HOME_CARE_NLS_RATE_2022;
  }
}
