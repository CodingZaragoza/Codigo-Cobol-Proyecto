package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.totals;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import java.math.RoundingMode;

/**
 * Determine capital total payment amount.
 *
 * <p>Converted from {@code 3800-CALC-TOT-AMT} in the COBOL code.
 *
 * @since 2019
 */
public class CalculateCapitalFinalTotal
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    // ***********************************************************
    // ***  CALCULATE FINAL TOTALS FOR CAPITAL AND OPERATING
    //     COMPUTE H-CAPI-TOTAL-PAY ROUNDED =
    //             H-CAPI-FSP + H-CAPI-IME-ADJ +
    //             H-CAPI-DSH-ADJ + H-CAPI-OUTLIER.
    calculationContext.setCapitalTotalPayment(
        BigDecimalUtils.decimalSum(
                calculationContext.getCapitalFederalSpecificPortion(),
                calculationContext.getCapitalIndirectMedicalEducationAdj(),
                calculationContext.getCapitalDisproportionateShareHospitalAdjustment(),
                calculationContext.getCapitalOutlierCost())
            .setScale(2, RoundingMode.HALF_UP));
  }
}
