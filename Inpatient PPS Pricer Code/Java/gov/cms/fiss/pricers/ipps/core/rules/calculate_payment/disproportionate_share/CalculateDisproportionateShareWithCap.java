package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.disproportionate_share;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class CalculateDisproportionateShareWithCap
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final BigDecimal defaultOperatingDisproportionateShare =
        calculationContext.calculateDefaultOperatingDisproportionateShare();

    if (BigDecimalUtils.isGreaterThan(
            defaultOperatingDisproportionateShare,
            calculationContext.getOperatingDisproportionateShareLowRangeMin())
        && BigDecimalUtils.isLessThan(
            defaultOperatingDisproportionateShare,
            calculationContext.getOperatingDisproportionateShareLowRangeMax())) {
      calculationContext.setOperatingDisproportionateShare(
          defaultOperatingDisproportionateShare
              .subtract(calculationContext.getOperatingDisproportionateShareSubtrahendLow())
              .multiply(calculationContext.getOperatingDisproportionateShareMultiplicandLow())
              .add(calculationContext.getOperatingDisproportionateShareAddendLow())
              .setScale(4, RoundingMode.HALF_UP));

      if (BigDecimalUtils.isGreaterThan(
          calculationContext.getOperatingDisproportionateShare(),
          calculationContext.getOperatingDisproportionateShareLimit())) {
        calculationContext.setOperatingDisproportionateShare(
            calculationContext.getOperatingDisproportionateShareLimit());
      }
    }

    if (BigDecimalUtils.isGreaterThan(
        defaultOperatingDisproportionateShare,
        calculationContext.getOperatingDisproportionateShareHighRangeMin())) {
      calculationContext.setOperatingDisproportionateShare(
          defaultOperatingDisproportionateShare
              .subtract(calculationContext.getOperatingDisproportionateShareSubtrahendHigh())
              .multiply(calculationContext.getOperatingDisproportionateShareMultiplicandHigh())
              .add(calculationContext.getOperatingDisproportionateShareAddendHigh())
              .setScale(4, RoundingMode.HALF_UP));

      if (BigDecimalUtils.isGreaterThan(
          calculationContext.getOperatingDisproportionateShare(),
          calculationContext.getOperatingDisproportionateShareLimit())) {
        calculationContext.setOperatingDisproportionateShare(
            calculationContext.getOperatingDisproportionateShareLimit());
      }
    }
  }
}
