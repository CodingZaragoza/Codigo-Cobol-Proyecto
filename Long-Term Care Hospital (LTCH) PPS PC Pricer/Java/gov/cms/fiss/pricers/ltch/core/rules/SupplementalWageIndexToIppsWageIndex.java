package gov.cms.fiss.pricers.ltch.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.core.LtchPricerContext;
import gov.cms.fiss.pricers.ltch.core.codes.ReturnCode;
import gov.cms.fiss.pricers.ltch.core.tables.CbsaWageIndexEntry;

public class SupplementalWageIndexToIppsWageIndex
    implements CalculationRule<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {

  /**
   *
   *
   * <pre>
   * * ADDED 9-10-20 FOR SUPPLEMENTAL WAGE INDEX.
   * * WHEN P-SUPP-WI-IND = '2' THE SUPPLEMENTAL WAGE INDEX FIELD
   * * CONTAINS THE IPPS WAGE INDEX VALUE.
   * </pre>
   *
   * For LTCHs a value of ‘2’ is the current year IPPS-comparable wage index value that is used for
   * short-stay outlier and site neutral payment rate payments.
   */
  @Override
  public void calculate(LtchPricerContext calculationContext) {
    if ("2".equals(calculationContext.getSupplementalWageIndexIndicator())
        && calculationContext.getSupplementalWageIndex() != null) {
      if (BigDecimalUtils.isGreaterThanZero(calculationContext.getSupplementalWageIndex())
          && calculationContext.getHoldProvIppsCBSA() != null) {
        final CbsaWageIndexEntry entry =
            calculationContext
                .getHoldProvIppsCBSA()
                .copyBuilder()
                .geographicWageIndex(calculationContext.getSupplementalWageIndex())
                .build();
        // Set the variable again because of the builder pattern
        calculationContext.setHoldProvIppsCBSA(entry);
      } else {
        calculationContext.applyReturnCode(ReturnCode.INVALID_WAGE_INDEX_52);
      }
    }
  }
}
