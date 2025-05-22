package gov.cms.fiss.pricers.ipf.core.rules.pre_processing;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;
import gov.cms.fiss.pricers.ipf.core.codes.ReturnCode;
import gov.cms.fiss.pricers.ipf.core.tables.CbsaWageIndexEntry;

/**
 * Set the CBSA data based on the CBSA code and special wage index.
 *
 * <p>Converted from {@code 0550-GET-CBSA} in the COBOL code.
 */
public class SetCbsaWageIndexInContext
    implements CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {

  @Override
  public boolean shouldExecute(IpfPricerContext calculationContext) {
    return calculationContext.getCbsaWageIndexEntry() == null;
  }

  @Override
  public void calculate(IpfPricerContext calculationContext) {
    // *    RETRIEVE THE CBSA DATA
    //      PERFORM 0150-GET-CBSA THRU 0150-EXIT.
    //
    // ***     RTC = 52  --  CBSA NOT FOUND
    //      IF IPF-RTC = 52    GOBACK.
    //
    //      IF IPF-RTC = 00
    //         PERFORM 0650-N-GET-WAGE-INDX
    //            THRU 0650-N-EXIT VARYING MA2
    //            FROM MA1 BY 1 UNTIL
    //            TB-CBSA (MA2) NOT = HOLD-PROV-CBSA.
    //
    // ***     RTC = 52  --  WAGE-INDEX NOT FOUND
    //      IF IPF-RTC = 52    GOBACK.
    final CbsaWageIndexEntry wageIndex =
        calculationContext
            .getDataTables()
            .getCbsaWageIndexEntry(
                calculationContext.getProviderData().getCbsaActualGeographicLocation(),
                calculationContext.getClaimData().getDischargeDate());
    if (wageIndex == null) {
      calculationContext.completeWithReturnCode(ReturnCode.STATISTICAL_AREA_NOT_FOUND_52);
      return;
    }

    calculationContext.setCbsaWageIndexEntry(wageIndex);

    //      IF W-CBSA-INDEX       = 00.0000
    //         MOVE 52 TO IPF-RTC.
    if (BigDecimalUtils.isZero(wageIndex.getGeographicWageIndex())) {
      calculationContext.completeWithReturnCode(ReturnCode.STATISTICAL_AREA_NOT_FOUND_52);
    }
  }
}
