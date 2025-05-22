package gov.cms.fiss.pricers.esrd.core.rules.rules_2025;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdPaymentData;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext2022Dot2;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.commons.lang3.StringUtils;

/** Calculates the HDPA Adjustment Amount. */
public class CalculateHdpaAdjustmentAmount2025
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();
    final EsrdPaymentData paymentData = calculationContext.getPaymentData();
    paymentData.setHdpaAdjustmentAmount(BigDecimal.ZERO);

    // CHANGED FOR 2025
    //   if !EsrdPricerContext.CONDITION_CODE_AKI_MONTHLY_84.equals(claimData.getConditionCode()

    if (!calculationContext.isAki84()
        && calculationContext.hasDemoCode(EsrdPricerContext.DEMO_CODE_ETC_PARTICIPANT)
        && StringUtils.equalsAny(
            claimData.getTreatmentChoicesIndicator(),
            EsrdPricerContext2022Dot2.ETC_INDICATOR_HDPA_2022_2,
            EsrdPricerContext2022Dot2.ETC_INDICATOR_HDPA_NOTHING_2022_2,
            EsrdPricerContext2022Dot2.ETC_INDICATOR_BOTH_HDPA_AND_PPA_2022_2)) {
      paymentData.setHdpaAdjustmentAmount(
          calculationContext
              .getEtcHdpaPercent()
              .subtract(BigDecimal.ONE)
              .multiply(calculationContext.getBundledAdjustedBaseWageAmount()));
      if (calculationContext.isPerDiemClaim()) {
        paymentData.setHdpaAdjustmentAmount(
            paymentData
                .getHdpaAdjustmentAmount()
                .multiply(new BigDecimal(3))
                .divide(new BigDecimal(7), 2, RoundingMode.HALF_UP));
      }
    }
  }
}
