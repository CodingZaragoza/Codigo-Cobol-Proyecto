package gov.cms.fiss.pricers.ltch.core.rules;

import static gov.cms.fiss.pricers.ltch.core.codes.PaymentType.STANDARD_OLD;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.api.v2.LtchPaymentData;
import gov.cms.fiss.pricers.ltch.core.LtchPricerContext;
import gov.cms.fiss.pricers.ltch.core.codes.ErrorCode;
import gov.cms.fiss.pricers.ltch.core.codes.PaymentType;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CalcFinalPayment
    implements CalculationRule<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {

  /**
   *
   *
   * <pre>
   * ***************************************************************
   * *   CALCULATE THE "FINAL" PAYMENT AMOUNT.                     *
   * *   UNIQUE CALCULATION FOR EACH CLAIM PAYMENT TYPE COMBO      *
   * ***************************************************************
   * </pre>
   *
   * Converted from {@code 8000-CALC-FINAL-PMT}
   */
  @Override
  public void calculate(LtchPricerContext calculationContext) {
    final PaymentType paymentType = calculationContext.getPaymentType();
    final LtchPaymentData paymentData = calculationContext.getPaymentData();
    final InpatientProviderData providerData = calculationContext.getProviderData();
    if (!ErrorCode.isErrorCode(calculationContext.getReturnCode())) {
      switch (paymentType) {
          // *-------------------------------------------------------------*
          // * OLD POLICY CLAIMS (100% STANDARD PAYMENT)                   *
          // *-------------------------------------------------------------*
        case STANDARD_OLD:
          // COMPUTE PPS-FINAL-PAY-AMT =
          //         PPS-DRG-ADJ-PAY-AMT + PPS-OUTLIER-PAY-AMT
          paymentData.setTotalPayment(
              paymentData
                  .getAdjustedPayment()
                  .add(paymentData.getOutlierPayment())
                  .setScale(2, RoundingMode.HALF_UP));
          break;
          // NEW POLICY CLAIMS (ANY PAYMENT TYPE) ONLY APPLICABLE PAYMENT FIELDS CONTAIN VALUES > $0
          // APPLY BUDGET NEUTRALITY AND/OR BLEND TO PAYMENT IF NEEDED ANY APPLICABLE BUDGET
          // NEUTRALITY AND/OR BLEND WAS ALREADY APPLIED TO OUTLIER PAYMENT APPLY BUDGET NEUTRALITY
          // AND SITE-NEUTRAL IPPS ADJ. TO 100% SITE-NEUTRAL PAYMENTS
        case SITE_NEUTRAL:
          // COMPUTE PPS-SITE-NEUTRAL-COST-PMT ROUNDED =
          //        PPS-SITE-NEUTRAL-COST-PMT *
          //        H-BDGT-NEUT-FACTOR
          paymentData.setSiteNeutralCostPayment(
              paymentData
                  .getSiteNeutralCostPayment()
                  .multiply(calculationContext.getHoldBudgetNeutralFactor())
                  .setScale(2, RoundingMode.HALF_UP));
          // COMPUTE PPS-SITE-NEUTRAL-IPPS-PMT ROUNDED =
          //         PPS-SITE-NEUTRAL-IPPS-PMT *
          //         H-BDGT-NEUT-FACTOR *
          //         H-SITE-NEUTRAL-IPPS-ADJ
          paymentData.setSiteNeutralIppsPayment(
              paymentData
                  .getSiteNeutralIppsPayment()
                  .multiply(calculationContext.getHoldBudgetNeutralFactor())
                  .multiply(calculationContext.getHoldSiteNeutralIppsAdj())
                  .setScale(2, RoundingMode.HALF_UP));
          // COMPUTE PPS-OUTLIER-PAY-AMT ROUNDED =
          //         PPS-OUTLIER-PAY-AMT *
          //         H-SITE-NEUTRAL-IPPS-ADJ
          paymentData.setOutlierPayment(
              paymentData
                  .getOutlierPayment()
                  .multiply(calculationContext.getHoldSiteNeutralIppsAdj())
                  .setScale(2, RoundingMode.HALF_UP));
          break;
          // *-------------------------------------------------------*
          // * APPLY BLEND PERCENTS, BUDGET NEUT, AND SITE-NEUTRAL   *
          // * IPPS ADJUSTMENT TO BLENDED PAYMENTS                   *
          // *-------------------------------------------------------*
        case BLEND:
          // COMPUTE PPS-SITE-NEUTRAL-COST-PMT ROUNDED =
          //         PPS-SITE-NEUTRAL-COST-PMT *
          //         H-BDGT-NEUT-FACTOR *
          //         H-BLEND-SNT
          paymentData.setSiteNeutralCostPayment(
              paymentData
                  .getSiteNeutralCostPayment()
                  .multiply(calculationContext.getHoldBudgetNeutralFactor())
                  .multiply(calculationContext.getHoldBlendSiteNeutral())
                  .setScale(2, RoundingMode.HALF_UP));
          // COMPUTE PPS-SITE-NEUTRAL-IPPS-PMT ROUNDED =
          //         PPS-SITE-NEUTRAL-IPPS-PMT *
          //         H-BDGT-NEUT-FACTOR *
          //         H-SITE-NEUTRAL-IPPS-ADJ *
          //         H-BLEND-SNT
          paymentData.setSiteNeutralIppsPayment(
              paymentData
                  .getSiteNeutralIppsPayment()
                  .multiply(calculationContext.getHoldBudgetNeutralFactor())
                  .multiply(calculationContext.getHoldSiteNeutralIppsAdj())
                  .multiply(calculationContext.getHoldBlendSiteNeutral())
                  .setScale(2, RoundingMode.HALF_UP));
          // COMPUTE PPS-STANDARD-FULL-PMT ROUNDED =
          //         PPS-STANDARD-FULL-PMT * H-BLEND-STD
          paymentData.setStandardFullPayment(
              paymentData
                  .getStandardFullPayment()
                  .multiply(calculationContext.getHoldBlendStandard())
                  .setScale(2, RoundingMode.HALF_UP));
          // COMPUTE PPS-STANDARD-SSO-PMT ROUNDED =
          //         PPS-STANDARD-SSO-PMT * H-BLEND-STD
          paymentData.setStandardShortStayOutlierPayment(
              paymentData
                  .getStandardShortStayOutlierPayment()
                  .multiply(calculationContext.getHoldBlendStandard())
                  .setScale(2, RoundingMode.HALF_UP));
          break;
        default:
          break;
      }
      // *-------------------------------------------------------*
      // * SUM PAYMENT FIELDS FOR FINAL PAYMENT                  *
      // *-------------------------------------------------------*
      if (!paymentType.equals(STANDARD_OLD)) {
        // COMPUTE PPS-FINAL-PAY-AMT =
        //         PPS-STANDARD-FULL-PMT +
        //         PPS-STANDARD-SSO-PMT +
        //         PPS-SITE-NEUTRAL-COST-PMT +
        //         PPS-SITE-NEUTRAL-IPPS-PMT +
        //         PPS-OUTLIER-PAY-AMT
        paymentData.setTotalPayment(
            paymentData
                .getStandardFullPayment()
                .add(paymentData.getStandardShortStayOutlierPayment())
                .add(paymentData.getSiteNeutralCostPayment())
                .add(paymentData.getSiteNeutralIppsPayment())
                .add(paymentData.getOutlierPayment())
                .setScale(2, RoundingMode.HALF_UP));
        // *-------------------------------------------------------------*
        // * IF THERE'S AN LTCH DPP PAYMENT ADJUSTMENT THEN              *
        // * CALCULATE THE 'LTCH DPP ADJUSTMENT AMOUNT' AND              *
        // * ADD IT TO THE FINAL PAYMENT                                 *
        // *-------------------------------------------------------------*
        if ("Y".equals(providerData.getLtchDppIndicator())) {
          calculateIPPSLikeAmt(calculationContext);
          // COMPUTE PPS-LTCH-DPP-ADJ-AMT =
          //    H-IPPS-LIKE-AMT - H-PRE-DPP-PAY
          paymentData.setDischargePaymentPercentAmount(
              calculationContext
                  .getHoldIPPSLikeAmt()
                  .subtract(paymentData.getTotalPayment())
                  .setScale(2, RoundingMode.HALF_UP));
          // COMPUTE PPS-FINAL-PAY-AMT =
          //    H-PRE-DPP-PAY + PPS-LTCH-DPP-ADJ-AMT
          paymentData.setTotalPayment(
              paymentData.getTotalPayment().add(paymentData.getDischargePaymentPercentAmount()));
        }
      }
    }
  }

  void calculateIPPSLikeAmt(LtchPricerContext calculationContext) {
    // *-------------------------------------------------------------*
    // * GET AN 'IPPS-LIKE AMOUNT' TO BE USED FOR THE DPP ADJUSTMENT *
    // *-------------------------------------------------------------*
    final LtchPaymentData paymentData = calculationContext.getPaymentData();
    // *-------------------------------------------------------------*
    // * GET THE IPPS PAY AMT                                        *
    // *-------------------------------------------------------------*
    // TODO: We should find a better solution than creating and calling the rule, especially
    //  since we're already doing this twice in the rules list
    new CalculateIppsComparablePayment(
            List.of(
                new CalculateHoldValues(),
                new CalculateOperatingDshAmount(),
                new CalculateCapitalDshAdjustment(),
                new CalculateCapitalRate(),
                new CalculatePerDiem()))
        .calculate(calculationContext);
    // *-------------------------------------------------------------*
    // * GET THE IPPS PMT HIGH COST OUTLIER (HCO)                    *
    // *-------------------------------------------------------------*
    // COMPUTE PPS-OUTLIER-THRESHOLD ROUNDED =
    //             H-IPPS-PAY-AMT + H-FIXED-LOSS-AMT-SNT
    paymentData.setOutlierThresholdAmount(
        calculationContext
            .getHoldIppsPayAmount()
            .add(calculationContext.getFixedLossAmountSiteNeutral()));
    // IF PPS-FAC-COSTS > PPS-OUTLIER-THRESHOLD
    if (BigDecimalUtils.isGreaterThan(
        paymentData.getFacilityCosts(), paymentData.getOutlierThresholdAmount())) {
      // COMPUTE H-IPPS-LIKE-AMT-OUTLIER =
      //        (PPS-FAC-COSTS - PPS-OUTLIER-THRESHOLD) * .8
      calculationContext.setHoldIPPSOutlier(
          paymentData
              .getFacilityCosts()
              .subtract(paymentData.getOutlierThresholdAmount())
              .multiply(new BigDecimal("0.8")));
    }
    // *-------------------------------------------------------------*
    // * COMPUTE THE IPPS LIKE AMOUNT                                *
    // *-------------------------------------------------------------*
    // COMPUTE H-IPPS-LIKE-AMT =
    //             H-IPPS-PAY-AMT +
    //             H-IPPS-LIKE-AMT-OUTLIER.
    calculationContext.setHoldIPPSLikeAmt(
        calculationContext.getHoldIppsPayAmount().add(calculationContext.getHoldIPPSOutlier()));
  }
}
