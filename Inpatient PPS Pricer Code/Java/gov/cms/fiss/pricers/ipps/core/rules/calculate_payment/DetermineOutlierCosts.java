package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimData;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.ResultCode;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.commons.lang3.StringUtils;

/**
 * Determine cost outlier status and applicable review codes.
 *
 * <p>Converted from {@code 3600-CALC-OUTLIER} in the COBOL code (continued).
 *
 * @since 2019
 */
public class DetermineOutlierCosts
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();

    // ***********************************************************
    // ***  DETERMINES THE BILL TO BE COST  OUTLIER
    //     IF (P-NEW-CAPI-NEW-HOSP = 'Y')
    //         MOVE 0 TO H-CAPI-OUTDAY-PART
    //                   H-CAPI-OUTCST-PART.
    if (StringUtils.equals(providerData.getNewHospital(), "Y")) {
      calculationContext.setCapitalOutlierCostPart(BigDecimal.ZERO);
    }

    //     IF (H-OPER-OUTCST-PART + H-CAPI-OUTCST-PART) > 0
    //                 MOVE H-OPER-OUTCST-PART TO
    //                      H-OPER-OUTLIER-PART
    //                 MOVE H-CAPI-OUTCST-PART TO
    //                      H-CAPI-OUTLIER-PART
    //                 MOVE 02 TO PPS-RTC.
    if (BigDecimalUtils.isGreaterThanZero(
        calculationContext
            .getOperatingOutlierCostPart()
            .add(calculationContext.getCapitalOutlierCostPart()))) {
      calculationContext.setOperatingOutlierPart(calculationContext.getOperatingOutlierCostPart());
      calculationContext.setCapitalOutlierPart(calculationContext.getCapitalOutlierCostPart());
      calculationContext.applyResultCode(ResultCode.RC_02_TRANSFER_PAID_AS_OUTLIER);
    }

    //     IF OUTLIER-RECON-FLAG = 'Y'
    //        IF (H-OPER-OUTCST-PART + H-CAPI-OUTCST-PART) > 0
    //           COMPUTE HLD-PPS-RTC = HLD-PPS-RTC + 30
    //           GO TO 3600-EXIT
    //        ELSE
    //           GO TO 3600-EXIT
    //     ELSE
    //        NEXT SENTENCE.
    if (calculationContext.isOutlierReconciliation()) {
      if (BigDecimalUtils.isGreaterThanZero(
          calculationContext
              .getOperatingOutlierCostPart()
              .add(calculationContext.getCapitalOutlierCostPart()))) {
        calculationContext.setOutlierAdjustmentEnabled(true);
      }
    } else {
      // **********************************************************
      // **  DETERMINES IF COST OUTLIER
      // **  RECOMPUTES DOLLAR THRESHOLD TO BE SENT BACK WITH
      // **         RETURN CODE OF 02
      dollarThresholdRTCBlock(calculationContext);

      // **********************************************************
      // **  DETERMINES IF COST OUTLIER WITH LOS IS > COVERED  DAYS
      // **         RETURN CODE OF 67
      costOutlierWithLOSBlock(calculationContext);

      // ***********************************************************
      // ***  DETERMINES THE OUTLIER AMOUNT THAT WOULD BE PAID IF
      // ***  THE PROVIDER WAS TYPE B-HOLD-HARMLESS 100% FED RATE
      // ***********************************************************
      // *
      // ***********************************************************
      //     IF P-NEW-CAPI-PPS-PAY-CODE = 'B'
      //        COMPUTE H-CAPI2-B-OUTLIER-PART ROUNDED =
      //                H-CAPI-OUTLIER-PART.
      if (StringUtils.equals(providerData.getCapitalPpsPaymentCode(), "B")) {
        calculationContext.setCapital2BOutlierPart(calculationContext.getCapitalOutlierPart());
      }

      //     IF P-NEW-CAPI-PPS-PAY-CODE = 'C' AND
      //        H-CAPI-PAYCDE-PCT1 > 0
      //        COMPUTE H-CAPI2-B-OUTLIER-PART ROUNDED =
      //                H-CAPI-OUTLIER-PART / H-CAPI-PAYCDE-PCT1
      //         ON SIZE ERROR MOVE 0 TO H-CAPI2-B-OUTLIER-PART
      //     ELSE MOVE 0 TO H-CAPI2-B-OUTLIER-PART.
      if (StringUtils.equals(providerData.getCapitalPpsPaymentCode(), "C")
          && BigDecimalUtils.isGreaterThanZero(calculationContext.getCapitalPaycodePct1())) {
        calculationContext.setCapital2BOutlierPart(
            calculationContext
                .getCapitalOutlierPart()
                .divide(calculationContext.getCapitalPaycodePct1(), 9, RoundingMode.HALF_UP));
      } else {
        calculationContext.setCapital2BOutlierPart(BigDecimal.ZERO);
      }
    }
  }

  protected void dollarThresholdRTCBlock(IppsPricerContext calculationContext) {
    //     MOVE 0 TO H-OPER-DOLLAR-THRESHOLD.
    calculationContext.setOperatingDollarThreshold(BigDecimal.ZERO);

    //     IF PPS-RTC = 02
    //       IF H-CAPI-CSTCHG-RATIO > 0 OR
    //          H-OPER-CSTCHG-RATIO > 0
    //             COMPUTE H-OPER-DOLLAR-THRESHOLD ROUNDED =
    //                     (H-CAPI-COST-OUTLIER  +
    //                      H-OPER-COST-OUTLIER)
    //                             /
    //                    (H-CAPI-CSTCHG-RATIO  +
    //                     H-OPER-CSTCHG-RATIO)
    //             ON SIZE ERROR MOVE 0 TO H-OPER-DOLLAR-THRESHOLD
    //       ELSE MOVE 0 TO H-OPER-DOLLAR-THRESHOLD.
    if (calculationContext.getResultCode() == ResultCode.RC_02_TRANSFER_PAID_AS_OUTLIER) {
      if (BigDecimalUtils.isGreaterThanZero(
              calculationContext.getCapitalOperatingCostToChargeRatio())
          || BigDecimalUtils.isGreaterThanZero(
              calculationContext.getOperatingCostToChargeRatio())) {
        calculationContext.setOperatingDollarThreshold(
            calculationContext
                .getCapitalCostOutlier()
                .add(calculationContext.getOperatingCostOutlier())
                .divide(
                    calculationContext
                        .getCapitalOperatingCostToChargeRatio()
                        .add(calculationContext.getOperatingCostToChargeRatio()),
                    9,
                    RoundingMode.HALF_UP));
      } else {
        calculationContext.setOperatingDollarThreshold(BigDecimal.ZERO);
      }
    }
  }

  protected void costOutlierWithLOSBlock(IppsPricerContext calculationContext) {
    final IppsClaimData claimData = calculationContext.getClaimData();

    //     IF PPS-RTC = 02
    //         IF ((H-REG-DAYS + H-LTR-DAYS) < B-LOS) OR
    //            PPS-PC-COT-FLAG = 'Y'
    //             MOVE 67 TO PPS-RTC.
    if (calculationContext.getResultCode() == ResultCode.RC_02_TRANSFER_PAID_AS_OUTLIER
        && (claimData.getLengthOfStay()
                > calculationContext.getRegularDays() + claimData.getLifetimeReserveDays()
            || calculationContext.isCostOutlierThresholdClaim())) {
      calculationContext.applyResultCode(ResultCode.RC_67_OUTLIER_LOS_GT_COVERED_DAYS);
    }
  }
}
