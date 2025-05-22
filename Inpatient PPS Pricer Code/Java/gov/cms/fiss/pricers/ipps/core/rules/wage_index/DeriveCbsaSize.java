package gov.cms.fiss.pricers.ipps.core.rules.wage_index;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.CbsaReference;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.ResultCode;
import gov.cms.fiss.pricers.ipps.core.tables.CbsaWageIndexEntry;
import org.apache.commons.lang3.StringUtils;

/**
 * Determines the CBSA size.
 *
 * <p>Converted from {@code 0750-GET-CBSA-SIZE} in the COBOL code.
 *
 * @since 2019
 */
public class DeriveCbsaSize
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public boolean shouldExecute(IppsPricerContext calculationContext) {
    return ResultCode.RC_52_INVALID_WAGE_INDEX != calculationContext.getResultCode();
  }

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final CbsaWageIndexEntry cbsaTableEntry = calculationContext.getCbsaWageIndexEntry();

    //    IF  B-N-DISCHARGE-DATE NOT < T-CBSA-EFF-DATE (MA2)
    //        IF  P-NEW-CBSA-STD-RURAL-CHECK
    //            MOVE 'R' TO W-NEW-CBSA-SIZE
    //        ELSE
    //        IF  T-CBSA-SIZE (MA2) = 'L'
    //            MOVE 'L' TO W-NEW-CBSA-SIZE
    //        ELSE
    //            MOVE 'O' TO W-NEW-CBSA-SIZE.
    if (LocalDateUtils.isAfterOrEqual(
        calculationContext.getDischargeDate(), cbsaTableEntry.getEffectiveDate())) {
      // Due to how SoftwareMining had structured the code, it was assumed that the standard amount
      // location would be 5
      // digits long, but would have spaces as padding in the front. Our conversion had removed this
      // padding in most
      // situations so we need to check against the length and then do substring checking in full
      // length locations.
      final CbsaReference cbsaReference = calculationContext.getCbsaReference();
      final InpatientProviderData providerData = calculationContext.getProviderData();
      final CbsaWageIndexEntry stdAmtCbsaTableEntry =
          calculationContext
              .getDataTables()
              .getCbsaWageIndexEntry(
                  StringUtils.trim(providerData.getCbsaStandardizedAmountLocation()),
                  cbsaTableEntry.getEffectiveDate());

      if (null == stdAmtCbsaTableEntry) {
        calculationContext.applyResultCode(ResultCode.RC_52_INVALID_WAGE_INDEX);
        calculationContext.setCalculationCompleted();

        return;
      }

      // Given that the CBSA from the static data lookup is pre-trimmed, we only need to check the
      // resulting CBSA length here.
      if (stdAmtCbsaTableEntry.getCbsa().length() == 2) {
        cbsaReference.setSize("R");
      } else if (StringUtils.equals(stdAmtCbsaTableEntry.getSize(), "L")) {
        cbsaReference.setSize("L");
      } else {
        cbsaReference.setSize("O");
      }
    }
  }
}
