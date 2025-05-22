package gov.cms.fiss.pricers.ltch.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimData;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.api.v2.LtchPaymentData;
import gov.cms.fiss.pricers.ltch.core.LtchPricerContext;
import gov.cms.fiss.pricers.ltch.core.codes.ErrorCode;
import gov.cms.fiss.pricers.ltch.core.codes.PaymentType;
import gov.cms.fiss.pricers.ltch.core.codes.SecondaryPaymentTypeSiteNeutral;
import gov.cms.fiss.pricers.ltch.core.codes.SecondaryPaymentTypeStandard;

public class SetNewReturnCodes
    implements CalculationRule<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {

  @Override
  public void calculate(LtchPricerContext calculationContext) {
    if (!ErrorCode.isErrorCode(calculationContext.getReturnCode())
        && calculationContext.isPaymentStandardNewSiteNeutralOrBlendTypes()) {
      setNewReturnCodes(calculationContext);
    }
  }

  /** 7200-SET-NEW-RETURN-CODES. SET RETURN CODES FOR NEW POLICY CLAIMS */
  private void setNewReturnCodes(LtchPricerContext calculationContext) {
    final LtchClaimData claimData = calculationContext.getClaimData();

    // DEFAULT (NO PSYCH/REHAB NOR VENTILATOR SERVICE)
    // *--------------------------------------------------*
    // * VENTILATOR SERVICE PRESENT                       *
    // *--------------------------------------------------*
    calculationContext.setReturnCodePart1("C");
    final boolean ventilatorServicePresent =
        claimData.getProcedureCodes() != null
            && claimData.getProcedureCodes().contains(calculationContext.getVentilationIcd10Code());
    if (ventilatorServicePresent) {
      calculationContext.setReturnCodePart1("B");
    }

    // *--------------------------------------------------*
    // * SITE NEUTRAL PMT BECAUSE PSYCH/REHAB DRG PRESENT *
    // * (PRESENCE OF PSCHY/REHAB DRG TRUMPS VENT PROC.)  *
    // *--------------------------------------------------*
    if (calculationContext.isPsychRehabDrg() && calculationContext.isPaymentBlendOrSiteNeutral()) {
      calculationContext.setReturnCodePart1("A");
    }

    // ***************************************************************
    // * SET THE SECOND POSITION OF RETURN CODE                      *
    // ***************************************************************
    setNewReturnCodesBlendAndSiteNeutralCost(calculationContext);
    setNewReturnCodesBlendAndSiteNeutralIpps(calculationContext);
    setNewReturnCodesSiteNeutral(calculationContext);
    setNewReturnCodesStandardNew(calculationContext);

    // Apply the return code from above
    calculationContext.applyReturnCodeFromTwoPartCode();
  }

  private void setNewReturnCodesBlendAndSiteNeutralCost(LtchPricerContext calculationContext) {
    // *-------------------------------------------------------------*
    // * BLENDED PAYMENT CLAIMS; SITE NEUTRAL PORTION = COST         *
    // *-------------------------------------------------------------*
    final PaymentType paymentType = calculationContext.getPaymentType();
    final SecondaryPaymentTypeSiteNeutral secondaryPaymentTypeSiteNeutral =
        calculationContext.getSecondaryPaymentTypeSiteNeutral();
    final LtchPaymentData paymentData = calculationContext.getPaymentData();
    final SecondaryPaymentTypeStandard secondaryPaymentTypeStandard =
        calculationContext.getSecondaryPaymentTypeStandard();

    if (paymentType.equals(PaymentType.BLEND)
        && secondaryPaymentTypeSiteNeutral.equals(SecondaryPaymentTypeSiteNeutral.COST)) {
      if (BigDecimalUtils.isZero(paymentData.getOutlierPayment())
          && secondaryPaymentTypeStandard.equals(SecondaryPaymentTypeStandard.FULL)) {
        calculationContext.setReturnCodePart2("0");
      }
      if (BigDecimalUtils.isGreaterThanZero(paymentData.getOutlierPayment())
          && secondaryPaymentTypeStandard.equals(SecondaryPaymentTypeStandard.FULL)) {
        calculationContext.setReturnCodePart2("1");
      }
      if (BigDecimalUtils.isZero(paymentData.getOutlierPayment())
          && secondaryPaymentTypeStandard.equals(SecondaryPaymentTypeStandard.SSO)) {
        calculationContext.setReturnCodePart2("2");
      }
      if (BigDecimalUtils.isGreaterThanZero(paymentData.getOutlierPayment())
          && secondaryPaymentTypeStandard.equals(SecondaryPaymentTypeStandard.SSO)) {
        calculationContext.setReturnCodePart2("3");
      }
    }
  }

  private void setNewReturnCodesBlendAndSiteNeutralIpps(LtchPricerContext calculationContext) {
    // *-------------------------------------------------------------*
    // * BLENDED PAYMENT CLAIMS; SITE NEUTRAL PORTION = IPPS COMP.   *
    // *-------------------------------------------------------------*
    final PaymentType paymentType = calculationContext.getPaymentType();
    final SecondaryPaymentTypeSiteNeutral secondaryPaymentTypeSiteNeutral =
        calculationContext.getSecondaryPaymentTypeSiteNeutral();
    final LtchPaymentData paymentData = calculationContext.getPaymentData();
    final SecondaryPaymentTypeStandard secondaryPaymentTypeStandard =
        calculationContext.getSecondaryPaymentTypeStandard();

    if (paymentType.equals(PaymentType.BLEND)
        && secondaryPaymentTypeSiteNeutral.equals(SecondaryPaymentTypeSiteNeutral.IPPS)) {
      if (BigDecimalUtils.isZero(paymentData.getOutlierPayment())
          && secondaryPaymentTypeStandard.equals(SecondaryPaymentTypeStandard.FULL)) {
        calculationContext.setReturnCodePart2("4");
      }
      if (BigDecimalUtils.isGreaterThanZero(paymentData.getOutlierPayment())
          && secondaryPaymentTypeStandard.equals(SecondaryPaymentTypeStandard.FULL)) {
        calculationContext.setReturnCodePart2("5");
      }
      if (BigDecimalUtils.isZero(paymentData.getOutlierPayment())
          && secondaryPaymentTypeStandard.equals(SecondaryPaymentTypeStandard.SSO)) {
        calculationContext.setReturnCodePart2("6");
      }
      if (BigDecimalUtils.isGreaterThanZero(paymentData.getOutlierPayment())
          && secondaryPaymentTypeStandard.equals(SecondaryPaymentTypeStandard.SSO)) {
        calculationContext.setReturnCodePart2("7");
      }
    }
  }

  private void setNewReturnCodesSiteNeutral(LtchPricerContext calculationContext) {
    // *-------------------------------------------------------------*
    // * 100% SITE NEUTRAL PAYMENT CLAIMS                            *
    // *-------------------------------------------------------------*
    final PaymentType paymentType = calculationContext.getPaymentType();
    final SecondaryPaymentTypeSiteNeutral secondaryPaymentTypeSiteNeutral =
        calculationContext.getSecondaryPaymentTypeSiteNeutral();
    final LtchPaymentData paymentData = calculationContext.getPaymentData();

    if (paymentType.equals(PaymentType.SITE_NEUTRAL)) {
      if (secondaryPaymentTypeSiteNeutral.equals(SecondaryPaymentTypeSiteNeutral.COST)) {
        calculationContext.setReturnCodePart2("A");
      }
      if (secondaryPaymentTypeSiteNeutral.equals(SecondaryPaymentTypeSiteNeutral.IPPS)) {
        if (BigDecimalUtils.isZero(paymentData.getOutlierPayment())) {
          calculationContext.setReturnCodePart2("B");
        } else {
          calculationContext.setReturnCodePart2("C");
        }
      }
    }
  }

  private void setNewReturnCodesStandardNew(LtchPricerContext calculationContext) {
    // *-------------------------------------------------------------*
    // * 100% STANDARD PAYMENT CLAIMS                                *
    // *-------------------------------------------------------------*
    final PaymentType paymentType = calculationContext.getPaymentType();
    final LtchPaymentData paymentData = calculationContext.getPaymentData();
    final SecondaryPaymentTypeStandard secondaryPaymentTypeStandard =
        calculationContext.getSecondaryPaymentTypeStandard();

    if (paymentType.equals(PaymentType.STANDARD_NEW)) {
      if (BigDecimalUtils.isZero(paymentData.getOutlierPayment())
          && secondaryPaymentTypeStandard.equals(SecondaryPaymentTypeStandard.SSO)) {
        calculationContext.setReturnCodePart2("D");
      }
      if (BigDecimalUtils.isGreaterThanZero(paymentData.getOutlierPayment())
          && secondaryPaymentTypeStandard.equals(SecondaryPaymentTypeStandard.SSO)) {
        calculationContext.setReturnCodePart2("E");
      }
      if (BigDecimalUtils.isZero(paymentData.getOutlierPayment())
          && secondaryPaymentTypeStandard.equals(SecondaryPaymentTypeStandard.FULL)) {
        calculationContext.setReturnCodePart2("F");
      }
      if (BigDecimalUtils.isGreaterThanZero(paymentData.getOutlierPayment())
          && secondaryPaymentTypeStandard.equals(SecondaryPaymentTypeStandard.FULL)) {
        calculationContext.setReturnCodePart2("G");
      }
    }
  }
}
