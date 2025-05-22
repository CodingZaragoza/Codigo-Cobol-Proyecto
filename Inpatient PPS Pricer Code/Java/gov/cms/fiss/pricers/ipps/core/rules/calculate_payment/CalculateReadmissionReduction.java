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
import org.apache.commons.lang3.StringUtils;

/**
 * Determine the hospital readmissions reduction amount.
 *
 * <p>Converted from {@code 6000-CALC-READMIS-REDU} in the COBOL code.
 *
 * @since 2019
 */
public class CalculateReadmissionReduction
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();

    // *---------------------------------------------------------*
    // * (YEARCHANGE 2016.0)
    // * READMISSIONS PROCESS ADJUSTMENTS
    // *   + FY16: RANGE OF ALLOWABLE FACTORS (< 0.97 OR > 1.0)
    // *---------------------------------------------------------*
    //     MOVE 0 TO H-READMIS-ADJUST-AMT.
    calculationContext.setReadmissionAdjustmentAmount(BigDecimal.ZERO);

    //     IF P-HOSP-READMISSION-REDU = '1'
    //           GO TO 6000-EDIT-READMISN
    //     ELSE
    //           NEXT SENTENCE.
    if (!StringUtils.equals(providerData.getHrrParticipantIndicator(), "1")) {
      //     IF P-HOSP-READMISSION-REDU = '0' AND
      //        P-HOSP-HRR-ADJUSTMT = 0.0000
      //           MOVE ZEROES TO H-READMIS-ADJUST-AMT
      //           GO TO 6000-EXIT.
      //     IF P-HOSP-READMISSION-REDU = '0' AND
      //        P-HOSP-HRR-ADJUSTMT > 0.0000
      //           MOVE 65 TO PPS-RTC
      //           MOVE ZEROES TO H-READMIS-ADJUST-AMT
      //           GO TO 6000-EXIT.
      //     IF P-HOSP-READMISSION-REDU = '2' OR '3' OR '4' OR '5' OR
      //                                  '6' OR '7' OR '8' OR
      //                                  '9' OR ' '
      //           MOVE 65 TO PPS-RTC
      //           MOVE ZEROES TO H-READMIS-ADJUST-AMT
      //           GO TO 6000-EXIT.
      if (StringUtils.equals(providerData.getHrrParticipantIndicator(), "0")
          && BigDecimalUtils.isZero(providerData.getHrrAdjustment())) {
        return;
      } else if (StringUtils.equals(providerData.getHrrParticipantIndicator(), "0")
              && BigDecimalUtils.isGreaterThanZero(providerData.getHrrAdjustment())
          || StringUtils.isBlank(providerData.getHrrParticipantIndicator())
          || StringUtils.equalsAny(
              providerData.getHrrParticipantIndicator(), "2", "3", "4", "5", "6", "7", "8", "9")) {
        calculationContext.applyResultCode(ResultCode.RC_65_PAY_CODE_NOT_ABC);
        calculationContext.setReadmissionAdjustmentAmount(BigDecimal.ZERO);

        return;
      }
    }

    editReadmission(calculationContext);
  }

  /**
   * Converted from {@code 6000-EDIT-READMISN} in the COBOL code.
   *
   * @param calculationContext the current pricing context
   */
  protected void editReadmission(IppsPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();

    //     IF P-HOSP-HRR-ADJUSTMT < 0.9700
    //           MOVE 65 TO PPS-RTC
    //           MOVE ZEROES TO H-READMIS-ADJUST-AMT
    //           GO TO 6000-EXIT.
    //     IF P-HOSP-HRR-ADJUSTMT > 1.0000
    //           MOVE 65 TO PPS-RTC
    //           MOVE ZEROES TO H-READMIS-ADJUST-AMT
    //           GO TO 6000-EXIT.
    //     IF P-READ-INVALID-STATE
    //           MOVE 65 TO PPS-RTC
    //           MOVE ZEROES TO H-READMIS-ADJUST-AMT
    //           GO TO 6000-EXIT.
    if (BigDecimalUtils.isLessThan(providerData.getHrrAdjustment(), new BigDecimal(".97"))
        || BigDecimalUtils.isGreaterThan(providerData.getHrrAdjustment(), BigDecimal.ONE)
        || calculationContext.isReadInvalidState()) {
      calculationContext.applyResultCode(ResultCode.RC_65_PAY_CODE_NOT_ABC);
      calculationContext.setReadmissionAdjustmentAmount(BigDecimal.ZERO);

      return;
    }

    computeReadmission(calculationContext);
  }

  /**
   * Converted from {@code 6000-COMPUTE-READMISN} in the COBOL code.
   *
   * @param calculationContext the current pricing context
   */
  protected void computeReadmission(IppsPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();

    //        COMPUTE H-READMIS-ADJUST-AMT         ROUNDED =
    //              ((P-HOSP-HRR-ADJUSTMT * H-OPER-BASE-DRG-PAY) -
    //                H-OPER-BASE-DRG-PAY).
    calculationContext.setReadmissionAdjustmentAmount(
        providerData
            .getHrrAdjustment()
            .multiply(calculationContext.getOperatingBaseDrgPayment())
            .subtract(calculationContext.getOperatingBaseDrgPayment())
            .setScale(2, RoundingMode.HALF_UP));
  }
}
