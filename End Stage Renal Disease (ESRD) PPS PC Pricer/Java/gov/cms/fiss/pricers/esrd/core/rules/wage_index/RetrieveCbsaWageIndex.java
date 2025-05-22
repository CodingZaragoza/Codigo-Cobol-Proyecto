package gov.cms.fiss.pricers.esrd.core.rules.wage_index;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import gov.cms.fiss.pricers.esrd.core.codes.ReturnCode;
import gov.cms.fiss.pricers.esrd.core.tables.CbsaWageIndexEntry;
import org.apache.commons.lang3.StringUtils;

/**
 * Retrieves the wage index by CBSA.
 *
 * <p>Emulates the functions from {@code 0800-FIND-BUNDLED-CBSA-WI} &amp; {@code
 * 0850-GET-BUNDLED-CBSA-RATE} in the {@code ESDRV} COBOL code.
 *
 * @since 2020
 */
public class RetrieveCbsaWageIndex
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public boolean shouldExecute(EsrdPricerContext calculationContext) {
    return !StringUtils.equals(
        "1", calculationContext.getProviderData().getSpecialPaymentIndicator());
  }

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    //     SEARCH ALL BUN-CBSA-ENTRY
    //       AT END
    //          IF MAINFRAME-PC-SWITCH = DS-ERROR-CODE  THEN
    //             MOVE 60              TO PPS-RTC
    //             GO TO 0800-FIND-EXIT
    //          ELSE
    //             MOVE 61              TO PPS-RTC
    //             GO TO 0800-FIND-EXIT
    //          END-IF
    //       WHEN BUN-CBSA-VALUE (BUN-INDX) = P-GEO-CBSA
    //          MOVE BUN-PTR (BUN-INDX) TO W-SUB3
    //          PERFORM 0850-GET-BUNDLED-CBSA-RATE
    //             THRU 0850-BUNDLED-EXIT
    //     END-SEARCH.
    final CbsaWageIndexEntry cbsaWageIndexTableEntry =
        calculationContext
            .getDataTables()
            .getCbsaWageIndexEntry(
                calculationContext.getCbsa(),
                calculationContext.getClaimData().getServiceThroughDate());
    if (null == cbsaWageIndexTableEntry) {
      calculationContext.applyReturnCode(ReturnCode.CBSA_NOT_FOUND_60);
      calculationContext.setCalculationCompleted();
    } else {
      calculationContext.setBundledWageIndex(cbsaWageIndexTableEntry.getGeographicWageIndex());
    }
  }
}
