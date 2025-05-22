package gov.cms.fiss.pricers.ipps.core.rules.rules2020.wage_index;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.CbsaReference;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.ResultCode;
import gov.cms.fiss.pricers.ipps.core.tables.PrevYearWIFY19TableEntry;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.commons.lang3.StringUtils;

/**
 * Determines whether the wage index value will undergo a previous-year adjustment.
 *
 * <p>Converted from {@code 0550-GET-CBSA} in the COBOL code (continued).
 *
 * @since 2020
 */
public class AdjustIndexForPreviousYear2020
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final CbsaReference cbsaReference = calculationContext.getCbsaReference();

    // JARED CHANGES FOR REVIEW - PREVIOUS YEAR
    final BigDecimal prevWageIndexAdjustment = prevWageIndexCheck(calculationContext);

    if (prevWageIndexAdjustment != null) {
      if (BigDecimalUtils.isZero(prevWageIndexAdjustment)) {
        calculationContext.applyResultCode(ResultCode.RC_52_INVALID_WAGE_INDEX);
        return;
      }

      // **----------------------------------------------------------------
      // ** FOR FY 2020, APPLY RURAL FLOOR POLICY TRANSITION ADJUSTMENT
      // **----------------------------------------------------------------
      //     IF B-N-DISCHARGE-DATE > 20190930 AND
      //        B-N-DISCHARGE-DATE < 20201001
      //        PERFORM 1000-GET-PREVYR-WI THRU 1000-EXIT.
      //     IF B-N-DISCHARGE-DATE > 20190930 AND
      //        B-N-DISCHARGE-DATE < 20201001
      //        IF (((W-NEW-CBSA-WI - HLD-PREV-WI) / HLD-PREV-WI)
      //              < WI_PCT_REDUC_FY2020)
      //           COMPUTE W-NEW-CBSA-WI ROUNDED =
      //              HLD-PREV-WI * WI_PCT_ADJ_FY2020.
      // 0550-BYPASS.
      if (BigDecimalUtils.isLessThan(
          cbsaReference
              .getWageIndex()
              .subtract(prevWageIndexAdjustment)
              .divide(prevWageIndexAdjustment, RoundingMode.HALF_UP),
          calculationContext.getWageIndexPctReduction())) {
        cbsaReference.setWageIndex(
            prevWageIndexAdjustment
                .multiply(calculationContext.getWageIndexPctAdj())
                .setScale(4, RoundingMode.HALF_UP));
      }
    } else {
      // Prev wage index adjustment is null so its invalid
      calculationContext.applyResultCode(ResultCode.RC_52_INVALID_WAGE_INDEX);
    }
  }

  protected BigDecimal prevWageIndexCheck(IppsPricerContext calculationContext) {
    BigDecimal prevWageIndexAdjustment = BigDecimal.ZERO;

    final String providerNumber = calculationContext.getProviderData().getProviderCcn();
    // Create a new variable to store the truncated string providerNumber
    // Ensure the providerNumber has at least 6 characters before truncating
    final String truncatedProviderNumber = StringUtils.truncate(providerNumber, 6);

    final PrevYearWIFY19TableEntry prevYearWIFY19TableEntry =
        calculationContext.getDataTables().getPrevWageIndex(truncatedProviderNumber);

    if (prevYearWIFY19TableEntry != null) {
      prevWageIndexAdjustment = prevYearWIFY19TableEntry.getPrevWageIndex();
    }

    return prevWageIndexAdjustment;
  }
}
