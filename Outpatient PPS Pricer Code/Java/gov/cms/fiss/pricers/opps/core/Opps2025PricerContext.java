package gov.cms.fiss.pricers.opps.core;

import gov.cms.fiss.pricers.opps.api.v2.OppsClaimPricingRequest;
import gov.cms.fiss.pricers.opps.api.v2.OppsClaimPricingResponse;
import gov.cms.fiss.pricers.opps.core.codes.PackageFlag;
import gov.cms.fiss.pricers.opps.core.codes.StatusIndicator;
import gov.cms.fiss.pricers.opps.core.tables.DataTables;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

/** 2025 extension of the base OppsPricerContext. */
public class Opps2025PricerContext extends OppsPricerContext {

  public static final String CALCULATION_VERSION = "2025.3";

  public Opps2025PricerContext(
      OppsClaimPricingRequest input, OppsClaimPricingResponse output, DataTables dataTables) {
    super(input, output, dataTables);
  }

  @Override
  public String getCalculationVersion() {
    return CALCULATION_VERSION;
  }

  // 2023 CRT X-RAY Reduction is 10%
  @Override
  public BigDecimal getXRayCRTReduction() {
    return new BigDecimal("0.90");
  }

  @Override
  public BigDecimal getInpatientDeductibleCap() {
    return new BigDecimal("1676");
  }

  // Quality Adjustment Factor
  @Override
  public BigDecimal getApcQualityReduction() {
    return new BigDecimal("0.9806");
  }

  @Override
  public BigDecimal getWageIndexQuartile() {
    return new BigDecimal("0.9009");
  }

  @Override
  public BigDecimal getLinePaymentOutlierOffset() {
    return new BigDecimal("7175");
  }

  @Override
  // Payment Adjustment Flag '25' cap set to 15% for 2023
  public BigDecimal getColonialProcedureCap() {
    return new BigDecimal("0.15");
  }

  @Override
  // 2025 new Status Indicator and 'H1' added to not Eligible for outlier payment
  public boolean notEligibleForOutlierPayment(
      String statusIndicator,
      String packageFlag,
      String paymentMethodFlag,
      List<String> paymentAdjustmentFlags) {
    return Stream.of(
                StatusIndicator.H_PASS_THROUGH_DEVICE,
                StatusIndicator.H1_NON_OPIOID_MEDICAL_DEVICE,
                StatusIndicator.N_PACKAGED_INTO_APC)
            .anyMatch(si -> si.is(statusIndicator))
        || PackageFlag.DRUG_ADMINISTRATION_4.is(packageFlag)
        || isSection603(paymentMethodFlag)
        || isComprehensiveBloodDeductible(
            statusIndicator, paymentAdjustmentFlags, getComprehensiveApcClaimStatus());
  }

  @Override
  // 2025 New Status Indicator 'K1' added to REH exclusion
  public boolean isREHStatusIndicatorExclusion(String statusIndicator) {
    return Stream.of(
            StatusIndicator.A_NOT_PAID_OPPS,
            StatusIndicator.F_CORNEAL_TISSUE,
            StatusIndicator.G_DRUG_PASS_THROUGH,
            StatusIndicator.K_NON_PASS_THROUGH_DRUG,
            StatusIndicator.K1_NON_OPIOID_DRUG,
            StatusIndicator.L_FLU_PPV_VACCINES)
        .anyMatch(si -> si.is(statusIndicator));
  }
}
