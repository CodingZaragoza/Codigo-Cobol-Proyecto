package gov.cms.fiss.pricers.esrd.core.rules.rules_2020_2.move_results;

import gov.cms.fiss.pricers.common.api.OutpatientProviderData;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.esrd.api.v2.BundledPaymentData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdPaymentData;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import gov.cms.fiss.pricers.esrd.core.rules.move_results.MoveBaseResults;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Applies the calculation results to the bundled data.
 *
 * <p>Converted from {@code 9100-MOVE-RESULTS} in the COBOL code.
 *
 * @since 2020
 */
public class MoveBaseResults2020Dot2 extends MoveBaseResults {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    super.calculate(calculationContext);

    applyHdpaEtcAdjustment(calculationContext);
  }

  protected void applyHdpaEtcAdjustment(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();
    final BundledPaymentData bundledData = calculationContext.getBundledData();
    final EsrdPaymentData paymentData = calculationContext.getPaymentData();

    if (!calculationContext.isAki84()) {
      // ********************************************************
      // * NEW FOR VERSION 20.1 - APPLY THE HDPA-ETC ADJUSTMENT *
      // ********************************************************
      //       IF B-DATA-CODE = '94'
      //       THEN
      //         MOVE H-FINAL-AMT-WITH-HDPA TO PPS-2011-FULL-PPS-RATE
      //         MOVE H-FINAL-AMT-WITHOUT-HDPA TO
      //                          ADJ-BASE-WAGE-BEFORE-ETC-HDPA
      //       ELSE
      //         MOVE H-FINAL-AMT-WITHOUT-HDPA TO
      //                          PPS-2011-FULL-PPS-RATE
      //         MOVE ZERO TO ADJ-BASE-WAGE-BEFORE-ETC-HDPA
      //       END-IF
      //     END-IF.
      if (calculationContext.hasDemoCode(EsrdPricerContext.DEMO_CODE_ETC_PARTICIPANT)) {
        bundledData.setFullPaymentRate(calculationContext.getFinalAmountWithHdpa());
        paymentData.setAdjustedBaseWageBeforeEtcHdpaAmount(
            calculationContext.getFinalAmountWithoutHdpa());
      } else {
        bundledData.setFullPaymentRate(calculationContext.getFinalAmountWithoutHdpa());
        paymentData.setAdjustedBaseWageBeforeEtcHdpaAmount(BigDecimal.ZERO);
      }
    }
  }

  @Override
  protected void adjustOutlierForQip(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();
    final OutpatientProviderData providerData = calculationContext.getProviderData();

    final BundledPaymentData bundledData = calculationContext.getBundledData();
    //     IF B-COND-CODE NOT = '84'
    //     THEN
    //       IF P-QIP-REDUCTION = ' '
    //       THEN CONTINUE
    //       ELSE
    // * OLD BLEND CODE - NEED TO CONSIDER REMOVING
    //         COMPUTE PPS-2011-BLEND-COMP-RATE    ROUNDED =
    //                 PPS-2011-BLEND-COMP-RATE    *  QIP-REDUCTION
    //         COMPUTE PPS-2011-FULL-COMP-RATE     ROUNDED =
    //                 PPS-2011-FULL-COMP-RATE     *  QIP-REDUCTION
    //         COMPUTE PPS-2011-BLEND-PPS-RATE     ROUNDED =
    //                 PPS-2011-BLEND-PPS-RATE     *  QIP-REDUCTION
    //         COMPUTE PPS-2011-BLEND-OUTLIER-RATE ROUNDED =
    //                 PPS-2011-BLEND-OUTLIER-RATE *  QIP-REDUCTION
    //         COMPUTE H-FINAL-AMT-WITHOUT-HDPA    ROUNDED =
    //                 H-FINAL-AMT-WITHOUT-HDPA    *  QIP-REDUCTION
    //         COMPUTE H-FINAL-AMT-WITH-HDPA       ROUNDED =
    //                 H-FINAL-AMT-WITH-HDPA       *  QIP-REDUCTION
    //         COMPUTE PPS-2011-FULL-OUTLIER-RATE  ROUNDED =
    //                 PPS-2011-FULL-OUTLIER-RATE  *  QIP-REDUCTION
    //       END-IF
    //     END-IF.
    if (!calculationContext.isAki84() && null != providerData.getHospitalQualityIndicator()) {
      bundledData.setBlendedCompositeRate(
          BigDecimalUtils.defaultValue(bundledData.getBlendedCompositeRate())
              .setScale(2, RoundingMode.DOWN)
              .multiply(calculationContext.getQipReduction()));
      bundledData.setFullCompositeRate(
          BigDecimalUtils.defaultValue(bundledData.getFullCompositeRate())
              .setScale(2, RoundingMode.DOWN)
              .multiply(calculationContext.getQipReduction()));
      bundledData.setBlendedPaymentRate(
          BigDecimalUtils.defaultValue(bundledData.getBlendedPaymentRate())
              .setScale(2, RoundingMode.DOWN)
              .multiply(calculationContext.getQipReduction()));
      bundledData.setBlendedOutlierRate(
          BigDecimalUtils.defaultValue(bundledData.getBlendedOutlierRate())
              .setScale(2, RoundingMode.DOWN)
              .multiply(calculationContext.getQipReduction()));

      calculationContext.setFinalAmountWithoutHdpa(
          BigDecimalUtils.defaultValue(calculationContext.getFinalAmountWithoutHdpa())
              .setScale(2, RoundingMode.DOWN)
              .multiply(calculationContext.getQipReduction()));
      calculationContext.setFinalAmountWithHdpa(
          BigDecimalUtils.defaultValue(calculationContext.getFinalAmountWithHdpa())
              .setScale(2, RoundingMode.DOWN)
              .multiply(calculationContext.getQipReduction()));
      bundledData.setFullOutlierRate(
          BigDecimalUtils.defaultValue(bundledData.getFullOutlierRate())
              .setScale(2, RoundingMode.DOWN)
              .multiply(calculationContext.getQipReduction()));
    }
  }
}
