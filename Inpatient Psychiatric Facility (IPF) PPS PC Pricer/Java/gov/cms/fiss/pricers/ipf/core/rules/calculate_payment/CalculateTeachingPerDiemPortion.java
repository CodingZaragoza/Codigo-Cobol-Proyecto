package gov.cms.fiss.pricers.ipf.core.rules.calculate_payment;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.EvaluatingCalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;
import java.math.BigDecimal;
import java.util.List;

/**
 * Calculate the teaching adjustment.
 *
 * <p>Converted from {@code 3000-CALC-PAYMENT} in the COBOL code.
 */
public class CalculateTeachingPerDiemPortion
    extends EvaluatingCalculationRule<
        IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {

  public CalculateTeachingPerDiemPortion(
      List<CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext>>
          calculationRules) {
    super(calculationRules);
  }

  @Override
  public boolean shouldExecute(IpfPricerContext calculationContext) {
    //      IF IPF-TEACH-ADJ = 1.00
    //         MOVE ZEROES TO WK-ADJ-PER-DIEM-STEP1
    //                        WK-TEACH-PORTION
    //         GO TO 3000-BYPASS-TEACH.
    return !BigDecimalUtils.equals(
        calculationContext.getPaymentData().getTeachingAdjustmentPercent(), BigDecimal.ONE);
  }
}
