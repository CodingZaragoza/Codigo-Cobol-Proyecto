package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.api.v1.DrgsTableEntry;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimData;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.CbsaReference;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.commons.lang3.StringUtils;

/**
 * Determine the operating dollar threshold, capital dollar threshold, operating cost outlier
 * amount, and the capital cost outlier amount.
 *
 * <p>Converted from {@code 3600-CALC-OUTLIER} in the COBOL code (continued).
 *
 * @since 2019
 */
public class CalculateOutlierThresholdAmounts
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final IppsClaimData claimData = calculationContext.getClaimData();
    final DrgsTableEntry drgsTableEntry = calculationContext.getDrgsTableEntry();

    // *-----------------------------*
    // * (YEARCHANGE 2020.0)         *
    // * OUTLIER THRESHOLD AMOUNTS   *
    // *-----------------------------*
    //     MOVE 26552.00 TO H-CST-THRESH.
    calculationContext.setCostThreshold(calculationContext.getCostThresholdBase());

    //     IF (B-REVIEW-CODE = '03') AND
    //         H-PERDIEM-DAYS < H-ALOS
    //        COMPUTE H-CST-THRESH ROUNDED =
    //                      (H-CST-THRESH * H-TRANSFER-ADJ)
    //                ON SIZE ERROR MOVE 0 TO H-CST-THRESH.
    if (StringUtils.equals(claimData.getReviewCode(), "03")
        && BigDecimalUtils.isLessThan(
            calculationContext.getPerDiemDays(), drgsTableEntry.getGeometricMeanLengthOfStay())) {
      calculationContext.setCostThreshold(
          calculationContext
              .getCostThreshold()
              .multiply(calculationContext.getTransferAdjustment())
              .setScale(2, RoundingMode.HALF_UP));
    }

    //     IF ((B-REVIEW-CODE = '09') AND
    //         (H-PERDIEM-DAYS < H-ALOS))
    //         IF (D-DRG-POSTACUTE-PERDIEM)
    //            COMPUTE H-CST-THRESH ROUNDED =
    //                      (H-CST-THRESH * H-TRANSFER-ADJ)
    //                ON SIZE ERROR MOVE 0 TO H-CST-THRESH.
    if (StringUtils.equals(claimData.getReviewCode(), "09")
        && BigDecimalUtils.isLessThan(
            calculationContext.getPerDiemDays(), drgsTableEntry.getGeometricMeanLengthOfStay())
        && calculationContext.isDrgPostacutePerDiem()) {
      calculationContext.setCostThreshold(
          calculationContext
              .getCostThreshold()
              .multiply(calculationContext.getTransferAdjustment())
              .setScale(2, RoundingMode.HALF_UP));
    }

    //     IF ((B-REVIEW-CODE = '09') AND
    //         (H-PERDIEM-DAYS < H-ALOS))
    //         IF (D-DRG-POSTACUTE-50-50)
    //           COMPUTE H-CST-THRESH ROUNDED =
    //          H-CST-THRESH * H-DSCHG-FRCTN
    //                ON SIZE ERROR MOVE 0 TO H-CST-THRESH.
    if (StringUtils.equals(claimData.getReviewCode(), "09")
        && BigDecimalUtils.isLessThan(
            calculationContext.getPerDiemDays(), drgsTableEntry.getGeometricMeanLengthOfStay())
        && calculationContext.isDrgPostacute5050()) {
      calculationContext.setCostThreshold(
          calculationContext
              .getCostThreshold()
              .multiply(calculationContext.getDischargeFraction())
              .setScale(2, RoundingMode.HALF_UP));
    }

    //     COMPUTE H-OPER-DOLLAR-THRESHOLD ROUNDED =
    //        ((H-CST-THRESH * H-LABOR-PCT * H-WAGE-INDEX) +
    //         (H-CST-THRESH * H-NONLABOR-PCT * H-OPER-COLA)) *
    //          H-OPER-SHARE-DOLL-THRESHOLD.
    final CbsaReference cbsaReference = calculationContext.getCbsaReference();
    calculationContext.setOperatingDollarThreshold(
        calculationContext
            .getOperatingShareDollarThreshold()
            .multiply(
                calculationContext
                    .getCostThreshold()
                    .multiply(
                        calculationContext
                            .getNationalLaborPct()
                            .multiply(cbsaReference.getWageIndex()))
                    .add(
                        calculationContext
                            .getCostThreshold()
                            .multiply(
                                calculationContext
                                    .getNationalNonLaborPct()
                                    .multiply(
                                        calculationContext.getOperatingCostOfLivingAdjustment()))))
            .setScale(9, RoundingMode.HALF_UP));

    // ***********************************************************
    //     COMPUTE H-CAPI-DOLLAR-THRESHOLD ROUNDED =
    //          H-CST-THRESH * H-CAPI-GAF * H-CAPI-LARG-URBAN *
    //          H-CAPI-SHARE-DOLL-THRESHOLD * H-CAPI-COLA.
    final BigDecimal capitalDollarThreshold =
        calculationContext
            .getCapitalShareDollarThreshold()
            .multiply(calculationContext.getCapitalCostOfLivingAdjustment())
            .multiply(calculationContext.getCapitalLargeUrbanFactor())
            .multiply(calculationContext.getCapitalGeographicAdjFactor())
            .multiply(calculationContext.getCostThreshold())
            .setScale(9, RoundingMode.HALF_UP);

    // ***********************************************************
    // ******NOW INCLUDES UNCOMPENSATED CARE**********************
    //     COMPUTE H-OPER-COST-OUTLIER ROUNDED =
    //         ((H-OPER-FSP-PART * (1 + H-OPER-IME-TEACH))
    //                       +
    //           ((H-OPER-FSP-PART * H-OPER-DSH) * .25))
    //                       +
    //             H-OPER-DOLLAR-THRESHOLD
    //                       +
    //                WK-UNCOMP-CARE-AMOUNT
    //                       +
    //                 H-NEW-TECH-PAY-ADD-ON.
    calculationContext.setOperatingCostOutlier(
        calculationContext
            .getOperatingFederalSpecificPortionPart()
            .multiply(BigDecimal.ONE.add(calculationContext.getOperatingIndirectMedicalEducation()))
            .add(
                calculationContext
                    .getOperatingFederalSpecificPortionPart()
                    .multiply(calculationContext.getOperatingDisproportionateShare())
                    .multiply(new BigDecimal("0.25")))
            .add(calculationContext.getOperatingDollarThreshold())
            .add(calculationContext.getUncompensatedCareAmount())
            .add(calculationContext.getNewTechAddOnPayment())
            .setScale(9, RoundingMode.HALF_UP));

    //     COMPUTE H-CAPI-COST-OUTLIER ROUNDED =
    //      (H-CAPI-FSP-PART * (1 + H-WK-CAPI-IME-TEACH + H-CAPI-DSH))
    //                       +
    //             H-CAPI-DOLLAR-THRESHOLD.
    calculationContext.setCapitalCostOutlier(
        capitalDollarThreshold
            .add(
                calculationContext
                    .getCapitalFederalSpecificPortionPart()
                    .multiply(
                        BigDecimal.ONE.add(
                            calculationContext
                                .getCapitalIndirectMedicalEducation()
                                .add(
                                    calculationContext.getCapitalDisproportionateShareHospital()))))
            .setScale(9, RoundingMode.HALF_UP));
    calculationContext
        .getAdditionalVariables()
        .getAdditionalCapitalVariables()
        .setCapitalCostOutlier(calculationContext.getCapitalCostOutlier());

    //     IF (P-NEW-CAPI-NEW-HOSP = 'Y')
    //         MOVE 0 TO H-CAPI-COST-OUTLIER.
    if (StringUtils.equals(calculationContext.getProviderData().getNewHospital(), "Y")) {
      calculationContext.setCapitalCostOutlier(BigDecimal.ZERO);
      calculationContext
          .getAdditionalVariables()
          .getAdditionalCapitalVariables()
          .setCapitalCostOutlier(BigDecimal.ZERO);
    }
  }
}
