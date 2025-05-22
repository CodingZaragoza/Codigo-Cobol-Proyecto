package gov.cms.fiss.pricers.ltch.core.rules;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimData;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.api.v2.LtchPaymentData;
import gov.cms.fiss.pricers.ltch.core.LtchPricerContext;
import gov.cms.fiss.pricers.ltch.core.codes.PaymentType;
import gov.cms.fiss.pricers.ltch.core.codes.ReturnCode;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

public class DeterminePaymentType
    implements CalculationRule<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {

  /*   -------------------------------------------------------------*
      DETERMINE WHICH PAYMENT METHOD TO USE. EITHER:            *
      STANDARD PAYMENT UNDER THE OLD POLICY,                    *
      STANDARD PAYMENT UNDER THE NEW POLICY,                    *
      100% SITE NEUTRAL PAYMENT, OR                             *
      50/50 BLEND OF SITE NEUTRAL & STANDARD PAYMENT.           *
  *-------------------------------------------------------------*/

  /** Converted from 3100-DETERMINE-PAYMENT-TYPE in COBOL. */
  @Override
  public void calculate(LtchPricerContext calculationContext) {
    PaymentType returnPaymentType = null;
    final LtchClaimData claimData = calculationContext.getClaimData();
    final LtchPaymentData paymentData = calculationContext.getPaymentData();
    final InpatientProviderData providerData = calculationContext.getProviderData();
    paymentData.setFacilityCosts(
        providerData
            .getOperatingCostToChargeRatio()
            .multiply(claimData.getCoveredCharges())
            .setScale(2, RoundingMode.HALF_UP));
    final int reviewCode = calculationContext.getReviewCodeAsInt();
    // STANDARD PAYMENT (OLD POLICY)
    if (reviewCode == 0) {
      returnPaymentType = PaymentType.STANDARD_OLD;
    }
    // STANDARD PAYMENT (NEW POLICY)
    else if (calculationContext.isStandardPaymentNew()) {
      returnPaymentType = PaymentType.STANDARD_NEW;
    }
    // *-------------------------------------------------------------*
    // * FOR COVID-19 PRICING, CLAIMS THAT WOULD NORMALLY GET PAID   *
    // * AS SITE-NEUTRAL WILL GET PAID AS STANDARD WHEN THE          *
    // * ADMISSION DATE IS ON OR AFTER JANUARY 27, 2020.             *
    // * SINCE THE ADMISSION DATE IS NOT INCLUDED IN THE INPUT BILL  *
    // * RECORD IT GETS CALCULATED FIRST.                            *
    // *-------------------------------------------------------------*
    else if (reviewCode == 1 && calculationContext.isPsychRehabDrg()
        || Arrays.asList(2, 3, 6, 7, 8).contains(reviewCode)) {
      final LocalDate admissionDate =
          claimData.getDischargeDate().minus(claimData.getLengthOfStay(), ChronoUnit.DAYS);
      if (LocalDateUtils.inRange(
          admissionDate,
          LocalDate.of(2020, 1, 26),
          false,
          calculationContext.getPublicHealthEmergencyLastDay(),
          true)) {
        calculationContext.setPaymentType(PaymentType.STANDARD_NEW);
        return;
      }
      returnPaymentType = getReturnPaymentType(calculationContext, paymentData);
    }

    // *-------------------------------------------------------------*
    // * CLAIM MEETS NONE OF THE ABOVE CRITERIA - SET RETURN CODE
    // *-------------------------------------------------------------*
    if (returnPaymentType == null) {
      calculationContext.applyReturnCode(ReturnCode.INVALID_BLEND_IND_OR_REVIEW_CODE_72);
    }

    calculationContext.setPaymentType(returnPaymentType);
  }

  private PaymentType getReturnPaymentType(
      LtchPricerContext calculationContext, LtchPaymentData paymentData) {
    PaymentType returnPaymentType = null;
    if (paymentData.getBlendYear() == 8) {
      // SITE NEUTRAL PAYMENT (NEW POLICY)
      returnPaymentType = PaymentType.SITE_NEUTRAL;
    } else if (paymentData.getBlendYear() == 6 || paymentData.getBlendYear() == 7) {
      // 50% SITE NEUTRAL + 50% STANDARD BLENDED PAYMENT
      returnPaymentType = PaymentType.BLEND;
      calculationContext.setHoldBlendStandard(
          new BigDecimal("0.5").setScale(1, RoundingMode.HALF_UP));
      calculationContext.setHoldBlendSiteNeutral(
          new BigDecimal("0.5").setScale(1, RoundingMode.HALF_UP));
    }
    return returnPaymentType;
  }
}
