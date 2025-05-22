package gov.cms.fiss.pricers.esrd.core.rules.move_results;

import gov.cms.fiss.pricers.common.api.OutpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.esrd.api.v2.AdditionalPaymentData;
import gov.cms.fiss.pricers.esrd.api.v2.BundledPaymentData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdPaymentData;
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
public class MoveBaseResults
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();
    final OutpatientProviderData providerData = calculationContext.getProviderData();
    final EsrdPaymentData paymentData = calculationContext.getPaymentData();
    final AdditionalPaymentData additionalPaymentInformation =
        paymentData.getAdditionalPaymentInformation();
    final AdditionalPaymentData additionalPaymentData2011 =
        paymentData.getAdditionalPaymentInformation2011();

    //     MOVE P-GEO-MSA                    TO PPS-MSA.    <-- NOT POPULATED; IDENTICAL TO INPUT
    //     MOVE P-GEO-CBSA                   TO PPS-CBSA.
    paymentData.setFinalCbsa(providerData.getCbsaActualGeographicLocation());
    //     MOVE H-WAGE-ADJ-PYMT-AMT          TO PPS-WAGE-ADJ-RATE.
    additionalPaymentInformation.setWageAdjustmentRate(BigDecimal.ZERO);
    //    Leaving this commented to preserve original cobol processing - data is copied from claim
    //    so it is already present in the client
    //    //     MOVE B-COND-CODE                  TO PPS-COND-CODE.
    //    //     MOVE B-REV-CODE                   TO PPS-REV-CODE.
    //    paymentData.setRevenueCode(claimData.getRevenueCode());
    //     MOVE H-BUN-BASE-WAGE-AMT          TO PPS-2011-WAGE-ADJ-RATE.
    additionalPaymentData2011.setWageAdjustmentRate(calculationContext.getBundledBaseWageAmount());
    //     MOVE BUN-NAT-LABOR-PCT            TO PPS-2011-NAT-LABOR-PCT.
    additionalPaymentData2011.setNationalLaborPercent(
        calculationContext.getBundledNationalLaborPercentage());
    //     MOVE BUN-NAT-NONLABOR-PCT         TO
    //                                    PPS-2011-NAT-NONLABOR-PCT.
    additionalPaymentData2011.setNationalNonLaborPercent(
        calculationContext.getBundledNationalNonLaborPercent());
    //     MOVE NAT-LABOR-PCT                TO PPS-NAT-LABOR-PCT.
    additionalPaymentInformation.setNationalLaborPercent(
        calculationContext.getNationalLaborPercent());
    //     MOVE NAT-NONLABOR-PCT             TO PPS-NAT-NONLABOR-PCT.
    additionalPaymentInformation.setNationalNonLaborPercent(
        calculationContext.getNationalNonLaborPercent());

    //     MOVE H-AGE-FACTOR                 TO PPS-AGE-FACTOR.
    additionalPaymentInformation.setAgeAdjustmentFactor(BigDecimal.ZERO);
    //     MOVE H-BSA-FACTOR                 TO PPS-BSA-FACTOR.
    additionalPaymentInformation.setBodySurfaceAreaFactor(BigDecimal.ZERO);
    //     MOVE H-BMI-FACTOR                 TO PPS-BMI-FACTOR.
    additionalPaymentInformation.setBodyMassIndexFactor(BigDecimal.ZERO);
    //     MOVE CASE-MIX-BDGT-NEUT-FACTOR    TO PPS-BDGT-NEUT-RATE.
    additionalPaymentInformation.setBudgetNeutralityRate(
        EsrdPricerContext.CASE_MIX_BUDGET_NEUTRAL_FACTOR);
    //     MOVE H-BUN-AGE-FACTOR             TO PPS-2011-AGE-FACTOR.
    additionalPaymentData2011.setAgeAdjustmentFactor(
        calculationContext.getBundledAgeAdjustmentFactor());
    //     MOVE H-BUN-BSA-FACTOR             TO PPS-2011-BSA-FACTOR.
    additionalPaymentData2011.setBodySurfaceAreaFactor(calculationContext.getBundledBsaFactor());
    //     MOVE H-BUN-BMI-FACTOR             TO PPS-2011-BMI-FACTOR.
    additionalPaymentData2011.setBodyMassIndexFactor(calculationContext.getBundledBmiFactor());
    //     MOVE TRANSITION-BDGT-NEUT-FACTOR  TO
    //                                    PPS-2011-BDGT-NEUT-RATE.
    additionalPaymentData2011.setBudgetNeutralityRate(
        EsrdPricerContext.TRANSITION_BUDGET_NEUTRAL_FACTOR);
    //     MOVE SPACES                       TO PPS-2011-COMORBID-MA. <--- NOT USED / INITIALIZED
    //     MOVE SPACES                       TO
    //                                    PPS-2011-COMORBID-MA-CC. <--- NOT USED / INITIALIZED

    //     IF (B-COND-CODE = '74')  AND
    //        (B-REV-CODE = '0841' OR '0851')  THEN
    //         COMPUTE H-OUT-PAYMENT ROUNDED = H-OUT-PAYMENT /
    //                                     B-CLAIM-NUM-DIALYSIS-SESSIONS
    //     END-IF.
    if (calculationContext.isEsrdHome74()
        && claimData.getDialysisSessionCount() != 0
        && StringUtils.equalsAny(
            claimData.getRevenueCode(),
            EsrdPricerContext.REVENUE_CODE_CONTINUOUS_AMBULATORY_PERITONEAL_DIALYSIS_0841,
            EsrdPricerContext.REVENUE_CODE_CONTINUOUS_CYCLING_PERITONEAL_DIALYSIS_0851)) {
      calculationContext.setOutlierPayment(
          calculationContext
              .getOutlierPayment()
              .divide(new BigDecimal(claimData.getDialysisSessionCount()), 4, RoundingMode.DOWN));
    }

    final BundledPaymentData bundledData = calculationContext.getBundledData();
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
    bundledData.setBlendedCompositeRate(BigDecimal.ZERO);
    //        MOVE ZERO                      TO
    //                                    PPS-2011-BLEND-PPS-RATE
    bundledData.setBlendedPaymentRate(BigDecimal.ZERO);
    //        MOVE ZERO                      TO
    //                                    PPS-2011-BLEND-OUTLIER-RATE
    bundledData.setBlendedOutlierRate(BigDecimal.ZERO);
    //     END-IF.
    //     MOVE H-PYMT-AMT                   TO
    //                                    PPS-2011-FULL-COMP-RATE.
    bundledData.setFullCompositeRate(BigDecimal.ZERO);
    //     MOVE H-PPS-FINAL-PAY-AMT          TO PPS-2011-FULL-PPS-RATE
    //                                          PPS-FINAL-PAY-AMT.
    bundledData.setFullPaymentRate(calculationContext.getFinalPaymentAmount());
    paymentData.setTotalPayment(calculationContext.getFinalPaymentAmount());
    //     MOVE H-OUT-PAYMENT                TO
    //                                    PPS-2011-FULL-OUTLIER-RATE.
    bundledData.setFullOutlierRate(
        calculationContext.getOutlierPayment().setScale(2, RoundingMode.DOWN));

    //     MOVE H-TDAPA-PAYMENT              TO TDAPA-RETURN.
    paymentData.setTdapaPaymentAdjustmentAmount(calculationContext.getTdapaPayment());

    adjustOutlierForQip(calculationContext);
  }

  protected void adjustOutlierForQip(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();
    final OutpatientProviderData providerData = calculationContext.getProviderData();

    final BundledPaymentData bundledData = calculationContext.getBundledData();

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
      bundledData.setFullPaymentRate(
          BigDecimalUtils.defaultValue(bundledData.getFullPaymentRate())
              .setScale(2, RoundingMode.DOWN)
              .multiply(calculationContext.getQipReduction()));
      bundledData.setBlendedOutlierRate(
          BigDecimalUtils.defaultValue(bundledData.getBlendedOutlierRate())
              .setScale(2, RoundingMode.DOWN)
              .multiply(calculationContext.getQipReduction()));
      bundledData.setFullOutlierRate(
          BigDecimalUtils.defaultValue(bundledData.getFullOutlierRate())
              .setScale(2, RoundingMode.DOWN)
              .multiply(calculationContext.getQipReduction()));
    }
  }
}
