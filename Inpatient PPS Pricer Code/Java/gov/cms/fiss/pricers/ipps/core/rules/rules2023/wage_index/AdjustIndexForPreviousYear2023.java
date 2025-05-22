package gov.cms.fiss.pricers.ipps.core.rules.rules2023.wage_index;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.CbsaReference;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.ResultCode;
import java.math.RoundingMode;
import org.apache.commons.lang3.StringUtils;

/**
 * Determines whether the wage index value will undergo a previous-year adjustment.
 *
 * <p>Converted from {@code 0550-GET-CBSA} in the COBOL code (continued).
 *
 * @since 2020
 */
public class AdjustIndexForPreviousYear2023
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final CbsaReference cbsaReference = calculationContext.getCbsaReference();
    final InpatientProviderData providerData = calculationContext.getProviderData();

    // **----------------------------------------------------------------
    // ** APPLY RURAL FLOOR POLICY TRANSITION ADJUSTMENT
    // **----------------------------------------------------------------
    //
    // PERFORM ERROR HANDLING CHECK
    if (StringUtils.equals(providerData.getSupplementalWageIndexIndicator(), "1")
        && BigDecimalUtils.isLessThanOrEqualToZero(providerData.getSupplementalWageIndex())) {
      calculationContext.applyResultCode(ResultCode.RC_52_INVALID_WAGE_INDEX);
      return;
    }

    //     IF B-N-DISCHARGE-DATE > 20190930
    //        IF (((W-NEW-CBSA-WI - HLD-PREV-WI) / HLD-PREV-WI)
    //              < WI_PCT_REDUC_FY2020)
    //           COMPUTE W-NEW-CBSA-WI ROUNDED =
    //              HLD-PREV-WI * WI_PCT_ADJ_FY2020.
    if (StringUtils.equals(providerData.getSupplementalWageIndexIndicator(), "1")
        && BigDecimalUtils.isLessThan(
            cbsaReference
                .getWageIndex()
                .subtract(providerData.getSupplementalWageIndex())
                .divide(providerData.getSupplementalWageIndex(), RoundingMode.HALF_UP),
            calculationContext.getWageIndexPctReduction())) {
      cbsaReference.setWageIndex(
          providerData
              .getSupplementalWageIndex()
              .multiply(calculationContext.getWageIndexPctAdj())
              .setScale(4, RoundingMode.HALF_UP));
    }
  }
}
