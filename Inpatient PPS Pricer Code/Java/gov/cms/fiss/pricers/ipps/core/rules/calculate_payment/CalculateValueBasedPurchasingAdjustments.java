package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.ResultCode;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Determine the value based purchasing adjustment amount.
 *
 * <p>Converted from {@code 7000-CALC-VALUE-BASED-PURCH}, {@code 7000-COMPUTE-VAL-BASED-PUR} in the
 * COBOL code.
 *
 * @since 2019
 */
public class CalculateValueBasedPurchasingAdjustments
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public boolean shouldExecute(IppsPricerContext calculationContext) {
    return !ArrayUtils.contains(
        new ResultCode[] {
          ResultCode.RC_65_PAY_CODE_NOT_ABC,
          ResultCode.RC_67_OUTLIER_LOS_GT_COVERED_DAYS,
          ResultCode.RC_68_INVALID_VBPF_IN_PSF
        },
        calculationContext.getResultCode());
  }

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();

    //     MOVE 0 TO H-VAL-BASED-PURCH-ADJUST-AMT.
    calculationContext.setValueBasedPurchasingAdjustmentAmount(BigDecimal.ZERO);

    //     IF  P-VAL-BASED-PURCH-PARTIPNT = 'N' OR 'Y'
    //           NEXT SENTENCE
    //     ELSE
    //           MOVE 68 TO PPS-RTC
    //           GO TO 7000-EXIT.
    //     IF  P-VAL-BASED-PURCH-PARTIPNT = 'N'
    //           GO TO 7000-EXIT.
    //     IF  P-VAL-BASED-PURCH-PARTIPNT = 'Y' AND
    //         P-NEW-CBSA-HOSP-QUAL-IND = '1'
    //           NEXT SENTENCE
    //     ELSE
    //           MOVE 68 TO PPS-RTC
    //           GO TO 7000-EXIT.
    //     IF  P-VBP-INVALID-STATE
    //           MOVE 68 TO PPS-RTC
    //           GO TO 7000-EXIT
    //     ELSE
    //           NEXT SENTENCE.
    //     IF P-VAL-BASED-PURCH-ADJUST < 0.9800000000 OR
    //        P-VAL-BASED-PURCH-ADJUST > 2.0000000000
    //           MOVE 68 TO PPS-RTC
    //           MOVE ZEROES TO H-VAL-BASED-PURCH-ADJUST-AMT
    //           GO TO 7000-EXIT
    //     ELSE
    //           GO TO 7000-COMPUTE-VAL-BASED-PUR.
    if (StringUtils.equals(providerData.getVbpParticipantIndicator(), "N")) {
      return;
    } else if (!StringUtils.equals(providerData.getVbpParticipantIndicator(), "Y")
        || !StringUtils.equals(providerData.getHospitalQualityIndicator(), "1")
        || calculationContext.isValueBasedPurchasingInvalidState()) {
      calculationContext.applyResultCode(ResultCode.RC_68_INVALID_VBPF_IN_PSF);

      return;
    } else if (BigDecimalUtils.isLessThan(providerData.getVbpAdjustment(), new BigDecimal(".98"))
        || BigDecimalUtils.isGreaterThan(providerData.getVbpAdjustment(), new BigDecimal("2"))) {
      calculationContext.applyResultCode(ResultCode.RC_68_INVALID_VBPF_IN_PSF);
      calculationContext.setValueBasedPurchasingAdjustmentAmount(BigDecimal.ZERO);

      return;
    }

    // 7000-COMPUTE-VAL-BASED-PUR.
    //     COMPUTE H-VAL-BASED-PURCH-ADJUST-AMT  ROUNDED =
    //              ((P-VAL-BASED-PURCH-ADJUST *
    //                  H-OPER-BASE-DRG-PAY) -
    //                  H-OPER-BASE-DRG-PAY).
    calculationContext.setValueBasedPurchasingAdjustmentAmount(
        providerData
            .getVbpAdjustment()
            .multiply(calculationContext.getOperatingBaseDrgPayment())
            .subtract(calculationContext.getOperatingBaseDrgPayment())
            .setScale(2, RoundingMode.HALF_UP));
  }
}
