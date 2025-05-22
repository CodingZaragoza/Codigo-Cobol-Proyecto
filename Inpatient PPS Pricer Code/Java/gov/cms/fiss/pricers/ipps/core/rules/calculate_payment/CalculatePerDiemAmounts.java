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
import java.math.RoundingMode;
import org.apache.commons.lang3.StringUtils;

/**
 * Determines the per diem amounts for operating federal-specific portion part, capital
 * federal-specific portion part, and capital old hold harmless amount.
 *
 * <p>Converted from {@code 3000-CALC-PAYMENT} in the COBOL code (continued).
 *
 * @since 2019
 */
public class CalculatePerDiemAmounts
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final BigDecimal perDiemDays = calculationContext.getPerDiemDays();
    final IppsClaimData claimData = calculationContext.getClaimData();
    final DrgsTableEntry drgsTableEntry = calculationContext.getDrgsTableEntry();

    // ***********************************************************
    //        IF PAY-PERDIEM-DAYS
    //            IF  H-PERDIEM-DAYS < H-ALOS
    //                IF  NOT (B-DRG = 789)
    //                    PERFORM 3500-CALC-PERDIEM-AMT
    //                    MOVE 03 TO PPS-RTC.
    if (calculationContext.isPayPerDiemDays()
        && BigDecimalUtils.isLessThan(perDiemDays, drgsTableEntry.getGeometricMeanLengthOfStay())
        && !StringUtils.equals(claimData.getDiagnosisRelatedGroup(), "789")) {
      calculatePerDiemAmount(calculationContext);
      calculationContext.applyResultCode(ResultCode.RC_03_TRANSFER_PAID_PERDIEM_DAYS);
    }

    //        IF PAY-XFER-SPEC-DRG
    //            IF  H-PERDIEM-DAYS < H-ALOS
    //                IF  NOT (B-DRG = 789)
    //                    PERFORM 3550-CALC-PERDIEM-AMT.
    if (calculationContext.isPayTransferSpecialDrug()
        && BigDecimalUtils.isLessThan(perDiemDays, drgsTableEntry.getGeometricMeanLengthOfStay())
        && !StringUtils.equals(claimData.getDiagnosisRelatedGroup(), "789")) {
      continueCalcPerDiemAmt(calculationContext);
    }

    //        IF  PAY-XFER-NO-COST
    //            MOVE 00 TO PPS-RTC
    //            IF H-PERDIEM-DAYS < H-ALOS
    //               IF  NOT (B-DRG = 789)
    //                   PERFORM 3500-CALC-PERDIEM-AMT
    //                   MOVE 06 TO PPS-RTC.
    if (calculationContext.isPayTransferNoCost()) {
      calculationContext.applyResultCode(ResultCode.RC_00_OK);

      if (BigDecimalUtils.isLessThan(perDiemDays, drgsTableEntry.getGeometricMeanLengthOfStay())
          && !StringUtils.equals(claimData.getDiagnosisRelatedGroup(), "789")) {
        calculatePerDiemAmount(calculationContext);
        calculationContext.applyResultCode(ResultCode.RC_06_PAY_XFER_NO_COST);
      }
    }
  }

  /**
   * Converted from {@code 3500-CALC-PERDIEM-AMT} in the COBOL code.
   *
   * @param calculationContext the current pricing context
   */
  protected void calculatePerDiemAmount(IppsPricerContext calculationContext) {
    // *********************************************************
    // ***  REVIEW CODE = 03 OR 06
    // ***  OPERATING PERDIEM-AMT CALCULATION
    // ***  OPERATING HSP AND FSP CALCULATION FOR TRANSFERS
    //        COMPUTE H-OPER-FSP-PART ROUNDED =
    //        H-OPER-FSP-PART * H-TRANSFER-ADJ
    //        ON SIZE ERROR MOVE 0 TO H-OPER-FSP-PART.
    calculationContext.setOperatingFederalSpecificPortionPart(
        calculationContext
            .getOperatingFederalSpecificPortionPart()
            .multiply(calculationContext.getTransferAdjustment())
            .setScale(9, RoundingMode.HALF_UP));

    // *********************************************************
    // *********************************************************
    // ***  REVIEW CODE = 03 OR 06
    // ***  CAPITAL   PERDIEM-AMT CALCULATION
    // ***  CAPITAL   HSP AND FSP CALCULATION FOR TRANSFERS
    //        COMPUTE H-CAPI-FSP-PART ROUNDED =
    //        H-CAPI-FSP-PART * H-TRANSFER-ADJ
    //        ON SIZE ERROR MOVE 0 TO H-CAPI-FSP-PART.
    calculationContext.setCapitalFederalSpecificPortionPart(
        calculationContext
            .getCapitalFederalSpecificPortionPart()
            .multiply(calculationContext.getTransferAdjustment())
            .setScale(9, RoundingMode.HALF_UP));

    // *********************************************************
    // ***  REVIEW CODE = 03 OR 06
    // ***  CAPITAL PERDIEM-AMT, OLD-HARMLESS CALCULATION
    //        COMPUTE H-CAPI-OLD-HARMLESS ROUNDED =
    //        H-CAPI-OLD-HARMLESS * H-TRANSFER-ADJ
    //        ON SIZE ERROR MOVE 0 TO H-CAPI-OLD-HARMLESS.
    calculationContext.setCapitalOldHoldHarmless(
        calculationContext
            .getCapitalOldHoldHarmless()
            .multiply(calculationContext.getTransferAdjustment())
            .setScale(2, RoundingMode.HALF_UP));
    calculationContext
        .getAdditionalVariables()
        .getAdditionalCapitalVariables()
        .setCapitalOldHoldHarmlessRate(calculationContext.getCapitalOldHoldHarmless());
  }

  /**
   * Converted from {@code 3550-CALC-PERDIEM-AMT} in the COBOL code.
   *
   * @param calculationContext the current pricing context
   */
  protected void continueCalcPerDiemAmt(IppsPricerContext calculationContext) {
    // ***********************************************************
    // ***  REVIEW CODE = 09  OR 11 TRANSFER WITH SPECIAL DRG
    // ***  OPERATING PERDIEM-AMT CALCULATION
    // ***  OPERATING HSP AND FSP CALCULATION FOR TRANSFERS
    //     IF (D-DRG-POSTACUTE-50-50)
    //        MOVE 10 TO PPS-RTC
    //        COMPUTE H-OPER-FSP-PART ROUNDED =
    //        H-OPER-FSP-PART * H-DSCHG-FRCTN
    //        ON SIZE ERROR MOVE 0 TO H-OPER-FSP-PART.
    if (calculationContext.isDrgPostacute5050()) {
      calculationContext.applyResultCode(ResultCode.RC_10_POST_ACUTE_XFER);
      calculationContext.setOperatingFederalSpecificPortionPart(
          calculationContext
              .getOperatingFederalSpecificPortionPart()
              .multiply(calculationContext.getDischargeFraction())
              .setScale(9, RoundingMode.HALF_UP));
    }

    //     IF (D-DRG-POSTACUTE-PERDIEM)
    //        MOVE 12 TO PPS-RTC
    //        COMPUTE H-OPER-FSP-PART ROUNDED =
    //        H-OPER-FSP-PART *  H-TRANSFER-ADJ
    //        ON SIZE ERROR MOVE 0 TO H-OPER-FSP-PART.
    if (calculationContext.isDrgPostacutePerDiem()) {
      calculationContext.applyResultCode(ResultCode.RC_12_POST_ACUTE_XFER_WITH_DRGS);
      calculationContext.setOperatingFederalSpecificPortionPart(
          calculationContext
              .getOperatingFederalSpecificPortionPart()
              .multiply(calculationContext.getTransferAdjustment())
              .setScale(9, RoundingMode.HALF_UP));
    }

    // ***********************************************************
    // ***  CAPITAL PERDIEM-AMT CALCULATION
    // ***  CAPITAL HSP AND FSP CALCULATION FOR TRANSFERS
    //     IF (D-DRG-POSTACUTE-50-50)
    //        MOVE 10 TO PPS-RTC
    //        COMPUTE H-CAPI-FSP-PART ROUNDED =
    //        H-CAPI-FSP-PART * H-DSCHG-FRCTN
    //        ON SIZE ERROR MOVE 0 TO H-CAPI-FSP-PART.
    if (calculationContext.isDrgPostacute5050()) {
      calculationContext.applyResultCode(ResultCode.RC_10_POST_ACUTE_XFER);
      calculationContext.setCapitalFederalSpecificPortionPart(
          calculationContext
              .getCapitalFederalSpecificPortionPart()
              .multiply(calculationContext.getDischargeFraction())
              .setScale(9, RoundingMode.HALF_UP));
    }

    //     IF (D-DRG-POSTACUTE-PERDIEM)
    //        MOVE 12 TO PPS-RTC
    //        COMPUTE H-CAPI-FSP-PART ROUNDED =
    //        H-CAPI-FSP-PART *  H-TRANSFER-ADJ
    //        ON SIZE ERROR MOVE 0 TO H-CAPI-FSP-PART.
    if (calculationContext.isDrgPostacutePerDiem()) {
      calculationContext.applyResultCode(ResultCode.RC_12_POST_ACUTE_XFER_WITH_DRGS);
      calculationContext.setCapitalFederalSpecificPortionPart(
          calculationContext
              .getCapitalFederalSpecificPortionPart()
              .multiply(calculationContext.getTransferAdjustment())
              .setScale(9, RoundingMode.HALF_UP));
    }

    // ***********************************************************
    // ***  CAPITAL PERDIEM-AMT, OLD-HARMLESS CALCULATION
    // ***  CAPITAL PERDIEM-AMT, OLD-HARMLESS CALCULATION
    //     IF (D-DRG-POSTACUTE-50-50)
    //        MOVE 10 TO PPS-RTC
    //        COMPUTE H-CAPI-OLD-HARMLESS ROUNDED =
    //        H-CAPI-OLD-HARMLESS * H-DSCHG-FRCTN
    //        ON SIZE ERROR MOVE 0 TO H-CAPI-OLD-HARMLESS.
    if (calculationContext.isDrgPostacute5050()) {
      calculationContext.applyResultCode(ResultCode.RC_10_POST_ACUTE_XFER);
      calculationContext.setCapitalOldHoldHarmless(
          calculationContext
              .getCapitalOldHoldHarmless()
              .multiply(calculationContext.getDischargeFraction())
              .setScale(2, RoundingMode.HALF_UP));
      calculationContext
          .getAdditionalVariables()
          .getAdditionalCapitalVariables()
          .setCapitalOldHoldHarmlessRate(calculationContext.getCapitalOldHoldHarmless());
    }

    //     IF (D-DRG-POSTACUTE-PERDIEM)
    //        MOVE 12 TO PPS-RTC
    //        COMPUTE H-CAPI-OLD-HARMLESS ROUNDED =
    //        H-CAPI-OLD-HARMLESS *  H-TRANSFER-ADJ
    //        ON SIZE ERROR MOVE 0 TO H-CAPI-OLD-HARMLESS.
    if (calculationContext.isDrgPostacutePerDiem()) {
      calculationContext.applyResultCode(ResultCode.RC_12_POST_ACUTE_XFER_WITH_DRGS);
      calculationContext.setCapitalOldHoldHarmless(
          calculationContext
              .getCapitalOldHoldHarmless()
              .multiply(calculationContext.getTransferAdjustment())
              .setScale(2, RoundingMode.HALF_UP));
      calculationContext
          .getAdditionalVariables()
          .getAdditionalCapitalVariables()
          .setCapitalOldHoldHarmlessRate(calculationContext.getCapitalOldHoldHarmless());
    }
  }
}
