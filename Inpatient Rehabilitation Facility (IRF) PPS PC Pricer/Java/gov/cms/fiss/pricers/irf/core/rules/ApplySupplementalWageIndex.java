package gov.cms.fiss.pricers.irf.core.rules;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.api.v2.IrfPaymentData;
import gov.cms.fiss.pricers.irf.core.IrfPricerContext;
import gov.cms.fiss.pricers.irf.core.ResultCode;
import gov.cms.fiss.pricers.irf.core.tables.CbsaWageIndexEntry;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.commons.lang3.StringUtils;

public class ApplySupplementalWageIndex
    implements CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  @Override
  public boolean shouldExecute(IrfPricerContext calculationContext) {
    return calculationContext.matchesReturnCode(ResultCode.OK_00)
        && StringUtils.equals(
            calculationContext.getProviderData().getSupplementalWageIndexIndicator(), "1");
  }

  @Override
  public void calculate(IrfPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();
    final IrfPaymentData paymentData = calculationContext.getPaymentData();

    // Check for presence of supplemental wage index value
    if (null == providerData.getSupplementalWageIndex()
        || BigDecimalUtils.isZero(providerData.getSupplementalWageIndex())) {
      calculationContext.applyResultCode(
          ResultCode.INVALID_WAGE_INDEX_52,
          ResultCode.INVALID_WAGE_INDEX_52.getDescription(),
          "Supplemental wage index required");

      return;
    }

    //  Verify that supplemental wage index is in fiscal year
    if (!LocalDateUtils.inRange(
        calculationContext.getProviderEffectiveDate(),
        calculationContext.getFiscalYearStart(),
        calculationContext.getFiscalYearEnd())) {
      calculationContext.applyResultCode(
          ResultCode.INVALID_WAGE_INDEX_52,
          ResultCode.INVALID_WAGE_INDEX_52.getDescription(),
          "Supplemental wage index not within fiscal year");

      return;
    }

    final CbsaWageIndexEntry cbsaWageIndex = calculationContext.getCbsaWageIndexEntry();
    // removed capWageIndexDecrease from the
    //  conditional if statement of null <> cbsaWageIndex.getEffectiveDate()
    // because not null validation was added for the effective date
    capWageIndexDecrease(cbsaWageIndex, calculationContext);

    // Update output record with CBSA data
    paymentData.setFinalCbsa(calculationContext.getCbsaWageIndexEntry().getCbsa());
    paymentData.setFinalWageIndex(
        calculationContext
            .getCbsaWageIndexEntry()
            .getGeographicWageIndex()
            .setScale(4, RoundingMode.HALF_UP));
  }

  /**
   * Applies a reduction cap to the supplemental wage index if the difference between CBSA and
   * supplemental * wage indexes is less than the reduction cap percentage.
   */
  private void capWageIndexDecrease(
      CbsaWageIndexEntry cbsaTableEntry, IrfPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();

    final BigDecimal wageIndexPercentReduction =
        cbsaTableEntry
            .getGeographicWageIndex()
            .subtract(providerData.getSupplementalWageIndex())
            .divide(providerData.getSupplementalWageIndex(), RoundingMode.HALF_UP);

    if (BigDecimalUtils.isLessThan(
        wageIndexPercentReduction, calculationContext.getWageIndexPercentReductionCap())) {
      final CbsaWageIndexEntry entryCopy =
          cbsaTableEntry
              .copyBuilder()
              .geographicWageIndex(
                  providerData
                      .getSupplementalWageIndex()
                      .multiply(calculationContext.getWageIndexPercentAdjustment()))
              .build();
      // After copying the entry, reset it on the context
      calculationContext.setCbsaWageIndexEntry(entryCopy);
    }
  }
}
