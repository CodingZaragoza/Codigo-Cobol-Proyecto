package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.EvaluatingCalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.ResultCode;
import java.math.BigDecimal;
import java.util.List;

/**
 * Encapsulates the outlier amount calculations as a sub-sequence of rules.
 *
 * <p>Converted from {@code 3000-CALC-PAYMENT} in the COBOL code.
 *
 * @since 2019
 */
public class CalculateTotals
    extends EvaluatingCalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  public CalculateTotals(
      List<CalculationRule<IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext>>
          calculationRules) {
    super(calculationRules);
  }

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    //     IF  PPS-RTC = 67
    //         MOVE H-OPER-DOLLAR-THRESHOLD TO
    //              WK-H-OPER-DOLLAR-THRESHOLD.

    final BigDecimal operChargeThreshold = calculationContext.getOperatingChargeThreshold();
    final BigDecimal operDollarThreshold = calculationContext.getOperatingDollarThreshold();

    //     IF  PPS-RTC < 50
    //         PERFORM 3800-CALC-TOT-AMT THRU 3800-EXIT.
    if (!calculationContext.isErrorResult()) {
      super.calculate(calculationContext);
    }

    //     IF  PPS-RTC < 50
    //         NEXT SENTENCE
    //     ELSE
    //         MOVE ALL '0' TO PPS-OPER-HSP-PART
    //                         PPS-OPER-FSP-PART
    //                         PPS-OPER-OUTLIER-PART
    //                         PPS-OUTLIER-DAYS
    //                         PPS-REG-DAYS-USED
    //                         PPS-LTR-DAYS-USED
    //                         PPS-TOTAL-PAYMENT
    //                         WK-HAC-TOTAL-PAYMENT
    //                         PPS-OPER-DSH-ADJ
    //                         PPS-OPER-IME-ADJ
    //                         H-DSCHG-FRCTN
    //                         H-DRG-WT-FRCTN
    //                         HOLD-ADDITIONAL-VARIABLES
    //                         HOLD-CAPITAL-VARIABLES
    //                         HOLD-CAPITAL2-VARIABLES
    //                         HOLD-OTHER-VARIABLES
    //                         HOLD-PC-OTH-VARIABLES
    //                        H-ADDITIONAL-PAY-INFO-DATA
    //                        H-ADDITIONAL-PAY-INFO-DATA2.
    if (calculationContext.isErrorResult()) {
      calculationContext.zeroResponse();
    }

    //     IF  PPS-RTC = 67
    //         MOVE WK-H-OPER-DOLLAR-THRESHOLD TO
    //                 H-OPER-DOLLAR-THRESHOLD.
    if (ResultCode.RC_67_OUTLIER_LOS_GT_COVERED_DAYS == calculationContext.getResultCode()) {
      calculationContext.setOperatingChargeThreshold(operChargeThreshold);
      calculationContext.setOperatingDollarThreshold(operDollarThreshold);
    }
  }
}
