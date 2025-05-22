package gov.cms.fiss.pricers.esrd.core.rules.rules_2025;

import gov.cms.fiss.pricers.common.api.OutpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.esrd.api.v2.*;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.commons.lang3.StringUtils;

/**
 * Applies the calculation results to the base response.
 *
 * <p>Converted from {@code 9100-MOVE-RESULTS} in the COBOL code.
 *
 * @since 2020
 */
public class MoveBaseResults2025
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final EsrdClaimData billingRecord = calculationContext.getClaimData();
    final OutpatientProviderData providerData = calculationContext.getProviderData();
    final EsrdPaymentData paymentData = calculationContext.getPaymentData();
    final AdditionalPaymentData additionalPaymentData =
        paymentData.getAdditionalPaymentInformation();
    final AdditionalPaymentData additionalPaymentInformation2011 =
        paymentData.getAdditionalPaymentInformation2011();

    //     MOVE P-GEO-MSA                    TO PPS-MSA.    <-- NOT POPULATED; IDENTICAL TO INPUT
    //     MOVE P-GEO-CBSA                   TO PPS-CBSA.
    paymentData.setFinalCbsa(providerData.getCbsaActualGeographicLocation());
    //     MOVE H-WAGE-ADJ-PYMT-AMT          TO PPS-WAGE-ADJ-RATE.
    additionalPaymentData.setWageAdjustmentRate(BigDecimal.ZERO);
    //     MOVE H-BUN-BASE-WAGE-AMT          TO PPS-2011-WAGE-ADJ-RATE.
    additionalPaymentInformation2011.setWageAdjustmentRate(
        calculationContext.getBundledBaseWageAmount());
    //     MOVE BUN-NAT-LABOR-PCT            TO PPS-2011-NAT-LABOR-PCT.
    additionalPaymentInformation2011.setNationalLaborPercent(
        calculationContext.getBundledNationalLaborPercentage());
    //     MOVE BUN-NAT-NONLABOR-PCT         TO
    //                                    PPS-2011-NAT-NONLABOR-PCT.
    additionalPaymentInformation2011.setNationalNonLaborPercent(
        calculationContext.getBundledNationalNonLaborPercent());
    //     MOVE NAT-LABOR-PCT                TO PPS-NAT-LABOR-PCT.
    additionalPaymentData.setNationalLaborPercent(calculationContext.getNationalLaborPercent());
    //     MOVE NAT-NONLABOR-PCT             TO PPS-NAT-NONLABOR-PCT.
    additionalPaymentData.setNationalNonLaborPercent(
        calculationContext.getNationalNonLaborPercent());

    //     MOVE H-AGE-FACTOR                 TO PPS-AGE-FACTOR.
    additionalPaymentData.setAgeAdjustmentFactor(BigDecimal.ZERO);
    //     MOVE H-BSA-FACTOR                 TO PPS-BSA-FACTOR.
    additionalPaymentData.setBodySurfaceAreaFactor(BigDecimal.ZERO);
    //     MOVE H-BMI-FACTOR                 TO PPS-BMI-FACTOR.
    additionalPaymentData.setBodyMassIndexFactor(BigDecimal.ZERO);
    //     MOVE CASE-MIX-BDGT-NEUT-FACTOR    TO PPS-BDGT-NEUT-RATE.
    additionalPaymentData.setBudgetNeutralityRate(EsrdPricerContext.CASE_MIX_BUDGET_NEUTRAL_FACTOR);
    //     MOVE H-BUN-AGE-FACTOR             TO PPS-2011-AGE-FACTOR.
    additionalPaymentInformation2011.setAgeAdjustmentFactor(
        calculationContext.getBundledAgeAdjustmentFactor());
    //     MOVE H-BUN-BSA-FACTOR             TO PPS-2011-BSA-FACTOR.
    additionalPaymentInformation2011.setBodySurfaceAreaFactor(
        calculationContext.getBundledBsaFactor());
    //     MOVE H-BUN-BMI-FACTOR             TO PPS-2011-BMI-FACTOR.
    additionalPaymentInformation2011.setBodyMassIndexFactor(
        calculationContext.getBundledBmiFactor());
    //     MOVE TRANSITION-BDGT-NEUT-FACTOR  TO
    //                                    PPS-2011-BDGT-NEUT-RATE.
    additionalPaymentInformation2011.setBudgetNeutralityRate(
        EsrdPricerContext.TRANSITION_BUDGET_NEUTRAL_FACTOR);
    //     MOVE SPACES                       TO PPS-2011-COMORBID-MA. <--- NOT USED / INITIALIZED
    //     MOVE SPACES                       TO
    //                                    PPS-2011-COMORBID-MA-CC. <--- NOT USED / INITIALIZED

    //     IF (B-COND-CODE = '74')  AND
    //        (B-REV-CODE = '0841' OR '0851')  THEN
    //         COMPUTE H-OUT-PAYMENT ROUNDED = H-OUT-PAYMENT /
    //                                     B-CLAIM-NUM-DIALYSIS-SESSIONS
    //     END-IF.

    // CHANGED FOR 2025
    //    if StringUtils.equals(
    //            EsrdPricerContext.CONDITION_CODE_HOME_SERVICES_74,
    // billingRecord.getConditionCode()

    if (calculationContext.isEsrdHome74()
        && billingRecord.getDialysisSessionCount() != 0
        && StringUtils.equalsAny(
            billingRecord.getRevenueCode(),
            EsrdPricerContext.REVENUE_CODE_CONTINUOUS_AMBULATORY_PERITONEAL_DIALYSIS_0841,
            EsrdPricerContext.REVENUE_CODE_CONTINUOUS_CYCLING_PERITONEAL_DIALYSIS_0851)) {
      calculationContext.setOutlierPayment(
          calculationContext
              .getOutlierPayment()
              .divide(
                  new BigDecimal(billingRecord.getDialysisSessionCount()), 4, RoundingMode.DOWN));
    }

    final BundledPaymentData bundledPaymentData = calculationContext.getBundledData();
    // ************************************************************************
    // No implementation provided for the following block, as the value for
    // P-PROV-WAIVE-BLEND-PAY-INDIC is always true, and the fields default to
    // zero.
    // ************************************************************************
    //     IF P-PROV-WAIVE-BLEND-PAY-INDIC        = 'N'  THEN
    //           COMPUTE PPS-2011-BLEND-COMP-RATE    ROUNDED =
    //              H-PYMT-AMT              *  COM-CBSA-BLEND-PCT
    //           COMPUTE PPS-2011-BLEND-PPS-RATE     ROUNDED =
    //              H-PPS-FINAL-PAY-AMT     *  BUN-CBSA-BLEND-PCT
    //           COMPUTE PPS-2011-BLEND-OUTLIER-RATE ROUNDED =
    //              H-OUT-PAYMENT           *  BUN-CBSA-BLEND-PCT
    //     ELSE
    // ************************************************************************
    // This block is converted below
    // ************************************************************************
    //        MOVE ZERO                      TO
    //                                    PPS-2011-BLEND-COMP-RATE
    bundledPaymentData.setBlendedCompositeRate(BigDecimal.ZERO);
    //        MOVE ZERO                      TO
    //                                    PPS-2011-BLEND-PPS-RATE
    bundledPaymentData.setBlendedPaymentRate(BigDecimal.ZERO);
    //        MOVE ZERO                      TO
    //                                    PPS-2011-BLEND-OUTLIER-RATE
    bundledPaymentData.setBlendedOutlierRate(BigDecimal.ZERO);
    //     END-IF.
    //     MOVE H-PYMT-AMT                   TO
    //                                    PPS-2011-FULL-COMP-RATE.
    bundledPaymentData.setFullCompositeRate(BigDecimal.ZERO);
    //     MOVE H-PPS-FINAL-PAY-AMT          TO PPS-2011-FULL-PPS-RATE
    //                                          PPS-FINAL-PAY-AMT.
    bundledPaymentData.setFullPaymentRate(
        calculationContext
            .getFinalPaymentAmount()
            .subtract(calculationContext.getNetworkReductionAmount()));
    paymentData.setTotalPayment(calculationContext.getFinalPaymentAmount());
    //     MOVE H-OUT-PAYMENT                TO
    //                                    PPS-2011-FULL-OUTLIER-RATE.
    bundledPaymentData.setFullOutlierRate(
        calculationContext.getOutlierPayment().setScale(2, RoundingMode.DOWN));

    //     MOVE H-TDAPA-PAYMENT              TO TDAPA-RETURN.
    paymentData.setTdapaPaymentAdjustmentAmount(calculationContext.getTdapaPayment());

    //     MOVE H-TPNIES-PAYMENT             TO TPNIES-RETURN.
    paymentData.setTpniesPaymentAdjustmentAmount(calculationContext.getTpniesPayment());

    //     MOVE H-CRA-TPNIES-PAYMENT             TO CRA-TPNIES-RETURN.
    paymentData.setTpniesCraPaymentAdjustmentAmount(calculationContext.getCraTpniesPayment());

    //     MOVE H-NETWORK-REDUCTION          TO NETWORK-REDUCTION-RETURN.
    paymentData.setNetworkReductionAmount(calculationContext.getNetworkReductionAmount());

    determineEtcAdjustmentAmount(calculationContext);
    // now have the final ETC adjustment amount, and
    // now have the final AdjustedBaseWageBeforeEtcHdpa amount

    determineLineItemAmounts(calculationContext);
    // now have before and after ETC line item amounts
    // adjusted for QIP
    // BUT NOT adjusted for Network Reduction to match the formula in the Presentation

    determineFinalAmounts(calculationContext);
    // now have Final Amount
    // adjusted for Network Reduction

  }

  protected void determineEtcAdjustmentAmount(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();
    final EsrdPaymentData paymentData = calculationContext.getPaymentData();

    paymentData.setPpaAdjustmentAmount(BigDecimal.ZERO);

    // This check is added because the logic was split out from within this conditional in the flow
    // CHANGED FOR 2025
    //   if !EsrdPricerContext.CONDITION_CODE_AKI_MONTHLY_84.equals(claimData.getConditionCode())

    if (!calculationContext.isAki84()) {
      // determine PerformancePaymentAdjustmentAmount
      if (calculationContext.hasDemoCode(EsrdPricerContext.DEMO_CODE_ETC_PARTICIPANT)) {
        if (StringUtils.equalsAny(
            claimData.getTreatmentChoicesIndicator(),
            EsrdPricerContext.ETC_INDICATOR_HDPA,
            EsrdPricerContext.ETC_INDICATOR_HDPA_NOTHING)) {
          paymentData.setPpaAdjustmentAmount(BigDecimal.ZERO);
          paymentData.setAdjustedBaseWageBeforeEtcHdpaAmount(
              calculationContext
                  .getFinalAmountWithoutEtc()
                  .multiply(calculationContext.getQipReduction())
                  .setScale(2, RoundingMode.HALF_UP));
        } else if (StringUtils.equals(
            claimData.getTreatmentChoicesIndicator(), EsrdPricerContext.ETC_INDICATOR_PPA)) {
          paymentData.setPpaAdjustmentAmount(
              claimData
                  .getPpaAdjustmentPercent()
                  .subtract(BigDecimal.ONE)
                  .multiply(calculationContext.getBundledAdjustedBaseWageAmount()));
          paymentData.setAdjustedBaseWageBeforeEtcHdpaAmount(BigDecimal.ZERO);
        } else if (StringUtils.equals(
            claimData.getTreatmentChoicesIndicator(),
            EsrdPricerContext.ETC_INDICATOR_BOTH_HDPA_AND_PPA)) {
          paymentData.setPpaAdjustmentAmount(
              claimData
                  .getPpaAdjustmentPercent()
                  .subtract(BigDecimal.ONE)
                  .multiply(calculationContext.getBundledAdjustedBaseWageAmount()));
          paymentData.setAdjustedBaseWageBeforeEtcHdpaAmount(
              calculationContext
                  .getFinalAmountWithoutEtc()
                  .multiply(calculationContext.getQipReduction())
                  .setScale(2, RoundingMode.HALF_UP));
        } else {
          paymentData.setPpaAdjustmentAmount(BigDecimal.ZERO);
          paymentData.setAdjustedBaseWageBeforeEtcHdpaAmount(BigDecimal.ZERO);
        }
      } else {
        paymentData.setPpaAdjustmentAmount(BigDecimal.ZERO);
        paymentData.setAdjustedBaseWageBeforeEtcHdpaAmount(BigDecimal.ZERO);
      }
    }
  }

  protected void determineLineItemAmounts(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();
    final EsrdPaymentData paymentData = calculationContext.getPaymentData();

    // determine PerformancePaymentAdjustmentAmount
    // CHANGED FOR 2025
    //   if !EsrdPricerContext.CONDITION_CODE_AKI_MONTHLY_84.equals(claimData.getConditionCode()

    if (!calculationContext.isAki84()
        && calculationContext.hasDemoCode(EsrdPricerContext.DEMO_CODE_ETC_PARTICIPANT)) {
      // Line item payment amount before model ETC PPA adjustment.
      paymentData.setPrePpaAdjustmentAmount(
          calculationContext
              .getFinalAmountWithoutEtc()
              .multiply(calculationContext.getQipReduction())
              .add(
                  calculationContext
                      .getOutlierPayment()
                      .multiply(calculationContext.getQipReduction()))
              .setScale(4, RoundingMode.HALF_UP));

      // ppaAdjustmentAmountWithPerDiem = ppaAdjustmentAmount + hdpaAdjustmentAmount
      // reduced by daily adjustment if it's a daily claim
      final BigDecimal ppaAdjustmentAmountWithPerDiem =
          getPpaAdjumentWithPerDiem(calculationContext, claimData, paymentData);

      // Line item payment amount after model ETC PPA adjustment.
      paymentData.setPostPpaAdjustmentAmount(
          paymentData
              .getPrePpaAdjustmentAmount()
              .add(ppaAdjustmentAmountWithPerDiem.multiply(calculationContext.getQipReduction())));
    }
  }

  private static BigDecimal getPpaAdjumentWithPerDiem(
      EsrdPricerContext calculationContext, EsrdClaimData claimData, EsrdPaymentData paymentData) {

    // ppaAdjustmentAmountWithPerDiem = ppaAdjustmentAmount
    BigDecimal ppaAdjustmentAmountWithPerDiem = paymentData.getPpaAdjustmentAmount();

    // if there is an HDPA Adjustment, then add it to the ppaAdjustmentAmountWithPerDiem
    if (StringUtils.equalsAny(
        claimData.getTreatmentChoicesIndicator(),
        EsrdPricerContext.ETC_INDICATOR_HDPA,
        EsrdPricerContext.ETC_INDICATOR_HDPA_NOTHING,
        EsrdPricerContext.ETC_INDICATOR_BOTH_HDPA_AND_PPA)) {
      ppaAdjustmentAmountWithPerDiem =
          ppaAdjustmentAmountWithPerDiem.add(
              calculationContext
                  .getEtcHdpaPercent()
                  .subtract(BigDecimal.ONE)
                  .multiply(calculationContext.getBundledAdjustedBaseWageAmount()));
    }

    //      if it's a daily claim then do the daily adjustment:
    //      ppaAdjustmentAmountWithPerDiem =
    //          3/7  *  ppaAdjustmentAmountWithPerDiem
    if (calculationContext.isPerDiemClaim()) {
      ppaAdjustmentAmountWithPerDiem =
          ppaAdjustmentAmountWithPerDiem
              .multiply(new BigDecimal(3))
              .divide(new BigDecimal(7), 2, RoundingMode.HALF_UP);
    }
    return ppaAdjustmentAmountWithPerDiem;
  }

  protected void determineFinalAmounts(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();
    final OutpatientProviderData providerData = calculationContext.getProviderData();
    final BundledPaymentData bundledPaymentData = calculationContext.getBundledData();
    final EsrdPaymentData paymentData = calculationContext.getPaymentData();

    //     IF B-COND-CODE NOT = '84' THEN
    //        IF P-QIP-REDUCTION = ' ' THEN
    //           NEXT SENTENCE
    //        ELSE
    //           COMPUTE PPS-2011-BLEND-COMP-RATE    ROUNDED =
    //                PPS-2011-BLEND-COMP-RATE    *  QIP-REDUCTION
    //           COMPUTE PPS-2011-FULL-COMP-RATE     ROUNDED =
    //                PPS-2011-FULL-COMP-RATE     *  QIP-REDUCTION
    //           COMPUTE PPS-2011-BLEND-PPS-RATE     ROUNDED =
    //                PPS-2011-BLEND-PPS-RATE     *  QIP-REDUCTION
    //           COMPUTE PPS-2011-FULL-PPS-RATE      ROUNDED =
    //                PPS-2011-FULL-PPS-RATE      *  QIP-REDUCTION
    //           COMPUTE PPS-2011-BLEND-OUTLIER-RATE ROUNDED =
    //                PPS-2011-BLEND-OUTLIER-RATE *  QIP-REDUCTION
    //           COMPUTE PPS-2011-FULL-OUTLIER-RATE  ROUNDED =
    //                PPS-2011-FULL-OUTLIER-RATE  *  QIP-REDUCTION
    //        END-IF
    //     END-IF.
    // CHANGED FOR 2025
    //  if !EsrdPricerContext.CONDITION_CODE_AKI_MONTHLY_84.equals(claimData.getConditionCode()

    if (!calculationContext.isAki84()) {
      final BigDecimal ppaAdjustmentAmountWithPerDiem =
          getPpaAdjumentWithPerDiem(calculationContext, claimData, paymentData);

      bundledPaymentData.setFullPaymentRate(
          BigDecimalUtils.defaultValue(calculationContext.getFinalAmountWithoutEtc())
              .setScale(2, RoundingMode.DOWN)
              .add(ppaAdjustmentAmountWithPerDiem)
              .multiply(calculationContext.getQipReduction())
              .subtract(calculationContext.getNetworkReductionAmount()));

      if (null != providerData.getHospitalQualityIndicator()) {

        bundledPaymentData.setBlendedCompositeRate(
            BigDecimalUtils.defaultValue(bundledPaymentData.getBlendedCompositeRate())
                .setScale(2, RoundingMode.DOWN)
                .multiply(calculationContext.getQipReduction()));

        bundledPaymentData.setFullCompositeRate(
            BigDecimalUtils.defaultValue(bundledPaymentData.getFullCompositeRate())
                .setScale(2, RoundingMode.DOWN)
                .multiply(calculationContext.getQipReduction()));

        bundledPaymentData.setBlendedPaymentRate(
            BigDecimalUtils.defaultValue(bundledPaymentData.getBlendedPaymentRate())
                .setScale(2, RoundingMode.DOWN)
                .multiply(calculationContext.getQipReduction()));

        bundledPaymentData.setBlendedOutlierRate(
            BigDecimalUtils.defaultValue(bundledPaymentData.getBlendedOutlierRate())
                .setScale(2, RoundingMode.DOWN)
                .multiply(calculationContext.getQipReduction()));

        bundledPaymentData.setFullOutlierRate(
            BigDecimalUtils.defaultValue(bundledPaymentData.getFullOutlierRate())
                .setScale(2, RoundingMode.DOWN)
                .multiply(calculationContext.getQipReduction()));
      }
      // adjust the ppaAdjustmentAmount for perdiem
      if (calculationContext.isPerDiemClaim()) {
        paymentData.setPpaAdjustmentAmount(
            paymentData
                .getPpaAdjustmentAmount()
                .multiply(new BigDecimal(3))
                .divide(new BigDecimal(7), 4, RoundingMode.HALF_UP));
      }
    }
  }
}
