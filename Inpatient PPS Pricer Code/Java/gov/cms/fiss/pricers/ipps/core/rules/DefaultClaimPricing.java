package gov.cms.fiss.pricers.ipps.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.EvaluatingCalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.api.v1.DrgsTableEntry;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.ResultCode;
import java.util.List;

public class DefaultClaimPricing
    extends EvaluatingCalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  public DefaultClaimPricing(
      List<CalculationRule<IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext>>
          calculationRules) {
    // Replaces mainlineControl
    super(calculationRules);
  }

  /**
   * Converted from {@code 0200-MAINLINE-CONTROL} in the COBOL code.
   *
   * @param calculationContext the current pricing context
   */
  @Override
  public void calculate(IppsPricerContext calculationContext) {
    super.calculate(calculationContext);

    final DrgsTableEntry drgsTableEntry = calculationContext.getDrgsTableEntry();

    //     IF PPS-RTC = 00
    //        IF H-PERDIEM-DAYS = H-ALOS OR
    //           H-PERDIEM-DAYS > H-ALOS
    //           MOVE 14 TO PPS-RTC.
    if (calculationContext.getResultCode() == ResultCode.RC_00_OK
        && BigDecimalUtils.isLessThanOrEqualTo(
            drgsTableEntry.getGeometricMeanLengthOfStay(), calculationContext.getPerDiemDays())) {
      calculationContext.applyResultCode(ResultCode.RC_14_PAID_DRG_WITH_PERDIEM);
    }

    //     IF PPS-RTC = 02
    //        IF H-PERDIEM-DAYS = H-ALOS OR
    //           H-PERDIEM-DAYS > H-ALOS
    //           MOVE 16 TO PPS-RTC.
    if (calculationContext.getResultCode() == ResultCode.RC_02_TRANSFER_PAID_AS_OUTLIER
        && BigDecimalUtils.isLessThanOrEqualTo(
            drgsTableEntry.getGeometricMeanLengthOfStay(), calculationContext.getPerDiemDays())) {
      calculationContext.applyResultCode(ResultCode.RC_16_PAID_AS_COST_OUTLIER_WITH_PERDIEM);
    }
  }
}
