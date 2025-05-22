package gov.cms.fiss.pricers.irf.core.rules;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimData;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.api.v2.IrfPaymentData;
import gov.cms.fiss.pricers.irf.core.IrfPricerContext;
import gov.cms.fiss.pricers.irf.core.ResultCode;
import gov.cms.fiss.pricers.irf.core.tables.CbsaWageIndexEntry;
import java.math.RoundingMode;
import org.apache.commons.lang3.StringUtils;

/**
 * Determines the wage index.
 *
 * <p>Converted from {@code 0550-GET-CBSA} and {@code 0650-N-GET-WAGE-INDX} in the DRV COBOL code.
 */
public class DefaultWageIndexRetrieval
    implements CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  @Override
  public void calculate(IrfPricerContext calculationContext) {
    final IrfPaymentData paymentData = calculationContext.getPaymentData();

    // Set wage index
    determineWageIndex(calculationContext);
    // AT END
    if (calculationContext.getCbsaWageIndexEntry() == null) {
      // MOVE 60 TO PPS-RTC
      calculationContext.applyResultCode(ResultCode.WAGE_INDEX_NOT_FD_60);
      return;
    }

    // Update output record with CBSA data
    paymentData.setFinalCbsa(calculationContext.getCbsaWageIndexEntry().getCbsa());
    paymentData.setFinalWageIndex(
        calculationContext
            .getCbsaWageIndexEntry()
            .getGeographicWageIndex()
            .setScale(4, RoundingMode.HALF_UP));
  }

  /**
   * Determines the source of the wage index information (provider vs. CBSA table) and updates the
   * context accordingly.
   *
   * @param calculationContext the current execution context
   */
  private void determineWageIndex(IrfPricerContext calculationContext) {
    final IrfClaimData claimData = calculationContext.getClaimData();
    final InpatientProviderData providerData = calculationContext.getProviderData();

    // If the special payment indicator is 1 then create a CBSA record based on provider record
    // values otherwise retrieve the CBSA record from the lookup table.

    // IF P-NEW-CBSA-SPEC-PAY-IND = '1'
    if (StringUtils.equals(
        providerData.getSpecialPaymentIndicator(), IrfPricerContext.SPECIAL_PAY_INDICATOR)) {
      // MOVE P-NEW-CBSA-WAGE-INDEX TO
      //      W-NEW-INDEX-RECORD-C
      // MOVE P-NEW-GEO-LOC-CBSA9 TO W-NEW-CBSA
      final CbsaWageIndexEntry specialWageIndex =
          CbsaWageIndexEntry.builder()
              .cbsa(providerData.getCbsaActualGeographicLocation())
              .geographicWageIndex(providerData.getSpecialWageIndex())
              .build();
      calculationContext.setCbsaWageIndexEntry(specialWageIndex);
    } else {
      // ELSE
      //    SEARCH M-CBSA-DATA
      calculationContext.setCbsaWageIndexEntry(
          calculationContext.getCbsaWageIndexEntry(
              providerData.getCbsaActualGeographicLocation(), claimData.getDischargeDate()));
    }
  }
}
