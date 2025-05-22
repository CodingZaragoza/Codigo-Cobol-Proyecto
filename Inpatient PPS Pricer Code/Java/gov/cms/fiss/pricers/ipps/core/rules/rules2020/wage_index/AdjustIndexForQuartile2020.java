package gov.cms.fiss.pricers.ipps.core.rules.rules2020.wage_index;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.CbsaReference;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Determines whether the wage index value will undergo a quartile adjustment.
 *
 * <p>Converted from {@code 0550-GET-CBSA} in the COBOL code (continued).
 *
 * @since 2020
 */
public class AdjustIndexForQuartile2020
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final CbsaReference cbsaReference = calculationContext.getCbsaReference();

    // **----------------------------------------------------------------
    // ** FOR FYS 2020 THROUGH 2023, APPLY THE WAGE INDEX BOOST
    // **----------------------------------------------------------------
    //     IF B-N-DISCHARGE-DATE > 20190930 AND
    //        B-N-DISCHARGE-DATE < 20201001
    //        IF W-NEW-CBSA-WI < WI_QUARTILE_FY2020
    //           COMPUTE W-NEW-CBSA-WI ROUNDED =
    //             ((WI_QUARTILE_FY2020 - W-NEW-CBSA-WI) / 2)
    //             + W-NEW-CBSA-WI.
    final BigDecimal wageIndexQuartileLimit = calculationContext.getWageIndexQuartileLimit();
    if (cbsaReference.getWageIndex() != null
        && BigDecimalUtils.isLessThan(cbsaReference.getWageIndex(), wageIndexQuartileLimit)) {
      cbsaReference.setWageIndex(
          wageIndexQuartileLimit
              .subtract(cbsaReference.getWageIndex())
              .multiply(new BigDecimal("0.5"))
              .add(cbsaReference.getWageIndex())
              .setScale(4, RoundingMode.HALF_UP));
    }
  }
}
