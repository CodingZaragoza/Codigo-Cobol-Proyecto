package gov.cms.fiss.pricers.esrd.core.rules.validation;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import gov.cms.fiss.pricers.esrd.core.codes.ReturnCode;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Performs condition code validation.
 *
 * <p>Converted from {@code 1000-VALIDATE-BILL-ELEMENTS} in the COBOL code.
 *
 * @since 2020
 */
public class ValidateCraTpnies
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public boolean shouldExecute(EsrdPricerContext calculationContext) {
    //    IF PPS-RTC = 00  THEN
    //    END-IF.
    return calculationContext.matchesReturnCode(ReturnCode.CALCULATION_STARTED_00);
  }

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();

    //       to prevent Negative CRA TPNIES
    //       IF QH / Sessions < Offset
    //       MOVE 99 TO PPS-RTC
    if (null != claimData.getTotalTpniesCraAmountQh()
        && claimData.getDialysisSessionCount() != 0
        && BigDecimalUtils.isLessThan(
            claimData
                .getTotalTpniesCraAmountQh()
                .divide(new BigDecimal(claimData.getDialysisSessionCount()), 4, RoundingMode.DOWN),
            calculationContext.getCraTpniesOffset())) {
      calculationContext.applyReturnCode(ReturnCode.UNSPECIFIED_ERROR_OCCURRED_99);
      calculationContext.setCalculationCompleted();
    }
  }
}
