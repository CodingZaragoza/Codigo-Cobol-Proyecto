package gov.cms.fiss.pricers.ltch.core.rules;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimData;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.api.v2.LtchPaymentData;
import gov.cms.fiss.pricers.ltch.core.LtchPricerContext;
import gov.cms.fiss.pricers.ltch.core.codes.ReturnCode;
import gov.cms.fiss.pricers.ltch.core.models.LtchWageIndexTableEntry;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.commons.lang3.StringUtils;

/* ************************************************************
     GET THE PROVIDER SPECIFIC VARIABLES AND LTCH WAGE INDEX  *
                                                              *
     THE APPROPRIATE SET OF THESE PPS VARIABLES ARE SELECTED  *
     DEPENDING ON THE BILL DISCHARGE DATE AND EFFECTIVE DATE  *
     OF THAT VARIABLE.                                        *
                                                              *
************************************************************** */
/** Converted from 2000-ASSEMBLE-PPS-VARIABLES in COBOL. */
public class AssemblePpsVariables
    implements CalculationRule<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {

  @Override
  public void calculate(LtchPricerContext calculationContext) {
    final LtchPaymentData paymentData = calculationContext.getPaymentData();
    final LtchWageIndexTableEntry ltchWageIndexTableEntry =
        calculationContext.getLtchWageIndexTableEntry();
    // ASSIGN FULL (5/5) LTCH WAGE INDEX TO ALL CLAIMS DISCHARGED
    // ON AND AFTER 7/1/2008 (THIRD COLUMN WAGE INDEX IN LTWIX***)

    final BigDecimal ltchWageIndex3 = ltchWageIndexTableEntry.getLtchWageIndex3();

    if (ltchWageIndex3 != null && BigDecimalUtils.isGreaterThanZero(ltchWageIndex3)) {
      paymentData.setFinalWageIndex(ltchWageIndex3);
    } else {
      calculationContext.applyReturnCode(ReturnCode.INVALID_WAGE_INDEX_52);
      return;
    }

    assembleBlendVariables(paymentData, calculationContext);
  }

  private void assembleBlendVariables(
      LtchPaymentData paymentData, LtchPricerContext calculationContext) {
    final LtchClaimData claimData = calculationContext.getClaimData();
    final InpatientProviderData providerData = calculationContext.getProviderData();
    int blendYear = Integer.parseInt(providerData.getFederalPpsBlend());
    // OLD POLICY CLAIMS MUST HAVE A BLEND YEAR INDICATOR OF 5
    if (StringUtils.equals(claimData.getReviewCode(), "00")) {
      blendYear = 5;
    }
    // NEW POLICY CLAIMS MUST HAVE A BLEND YR. IND. OF 6, 7, OR 8
    paymentData.setBlendYear(blendYear);
    final Integer reviewCode = calculationContext.getReviewCodeAsInt();
    if (reviewCode >= 1 && reviewCode <= 8 && (blendYear > 8 || blendYear < 6)) {
      calculationContext.applyReturnCode(ReturnCode.INVALID_BLEND_IND_OR_REVIEW_CODE_72);
      return;
    }

    // SET DEFAULT BLEND VARIABLE VALUES
    // H-BLEND-STD = % STANDARD PMT CONTRIBUTES TO FINAL PMT
    // H-BLEND-SNT = % SITE NEUTRAL PMT CONTRIBUTES TO FINAL PMT

    calculationContext.setHoldBlendSiteNeutral(BigDecimal.ZERO);
    calculationContext.setHoldBlendStandard(BigDecimal.ONE.setScale(1, RoundingMode.HALF_UP));
    calculationContext.setHoldBlendRtc(BigDecimal.ZERO);

    // *-------------------------------------------------------------*
    // * FORCE COLA VALUE TO 1.000 (EXCEPT ALASKA & HAWAII)          *
    // *-------------------------------------------------------------*
    if (StringUtils.equalsAny(providerData.getStateCode(), "02", "12")) {
      paymentData.setCostOfLivingAdjustmentPercent(providerData.getCostOfLivingAdjustment());
    } else {
      paymentData.setCostOfLivingAdjustmentPercent(
          BigDecimal.ONE.setScale(3, RoundingMode.HALF_UP));
    }
  }
}
