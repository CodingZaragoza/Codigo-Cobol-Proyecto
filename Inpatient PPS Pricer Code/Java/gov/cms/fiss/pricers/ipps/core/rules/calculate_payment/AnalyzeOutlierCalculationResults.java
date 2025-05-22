package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.api.v1.DrgsTableEntry;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimData;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.ResultCode;
import java.math.BigDecimal;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Check and revise return codes and outlier values.
 *
 * <p>Converted from {@code 3000-CALC-PAYMENT} in the COBOL code (continued).
 *
 * @since 2019
 */
public class AnalyzeOutlierCalculationResults
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
    final DrgsTableEntry drgsTableEntry = calculationContext.getDrgsTableEntry();
    final IppsClaimData claimData = calculationContext.getClaimData();

    //        IF PAY-XFER-SPEC-DRG
    //            IF  H-PERDIEM-DAYS < H-ALOS
    //                IF  NOT (B-DRG = 789)
    //                    PERFORM 3560-CHECK-RTN-CODE THRU 3560-EXIT.
    // ***********************************************************
    // 3560-CHECK-RTN-CODE.
    //     IF (D-DRG-POSTACUTE-50-50)
    //        MOVE 10 TO PPS-RTC.
    //     IF (D-DRG-POSTACUTE-PERDIEM)
    //        MOVE 12 TO PPS-RTC.
    if (calculationContext.isPayTransferSpecialDrug()
        && BigDecimalUtils.isLessThan(
            calculationContext.getPerDiemDays(), drgsTableEntry.getGeometricMeanLengthOfStay())
        && !claimData.getDiagnosisRelatedGroup().equals("789")) {
      if (calculationContext.isDrgPostacute5050()) {
        calculationContext.applyResultCode(ResultCode.RC_10_POST_ACUTE_XFER);
      }

      if (calculationContext.isDrgPostacutePerDiem()) {
        calculationContext.applyResultCode(ResultCode.RC_12_POST_ACUTE_XFER_WITH_DRGS);
      }
    }

    //         IF  PAY-PERDIEM-DAYS
    //            IF  H-OPER-OUTCST-PART > 0
    //                MOVE H-OPER-OUTCST-PART TO
    //                     H-OPER-OUTLIER-PART
    //                MOVE 05 TO PPS-RTC
    //            ELSE
    //            IF  PPS-RTC NOT = 03
    //                MOVE 00 TO PPS-RTC
    //                MOVE 0  TO H-OPER-OUTLIER-PART.
    if (calculationContext.isPayPerDiemDays()) {
      if (BigDecimalUtils.isGreaterThanZero(calculationContext.getOperatingOutlierCostPart())) {
        calculationContext.setOperatingOutlierPart(
            calculationContext.getOperatingOutlierCostPart());
        calculationContext.applyResultCode(ResultCode.RC_05_TRANSFER_PAID_ON_A_PERDIEM_BASIS);
      } else if (ResultCode.RC_03_TRANSFER_PAID_PERDIEM_DAYS
          != calculationContext.getResultCode()) {
        calculationContext.applyResultCode(ResultCode.RC_00_OK);
        calculationContext.setOperatingOutlierPart(BigDecimal.ZERO);
      }

      //        IF  PAY-PERDIEM-DAYS
      //            IF  H-CAPI-OUTCST-PART > 0
      //                MOVE H-CAPI-OUTCST-PART TO
      //                     H-CAPI-OUTLIER-PART
      //                MOVE 05 TO PPS-RTC
      //            ELSE
      //            IF  PPS-RTC NOT = 03
      //                MOVE 0  TO H-CAPI-OUTLIER-PART.
      if (BigDecimalUtils.isGreaterThanZero(calculationContext.getCapitalOutlierCostPart())) {
        calculationContext.setCapitalOutlierPart(calculationContext.getCapitalOutlierCostPart());
        calculationContext.applyResultCode(ResultCode.RC_05_TRANSFER_PAID_ON_A_PERDIEM_BASIS);
      } else if (ResultCode.RC_03_TRANSFER_PAID_PERDIEM_DAYS
          != calculationContext.getResultCode()) {
        calculationContext.setCapitalOutlierPart(BigDecimal.ZERO);
      }
    }
  }
}
