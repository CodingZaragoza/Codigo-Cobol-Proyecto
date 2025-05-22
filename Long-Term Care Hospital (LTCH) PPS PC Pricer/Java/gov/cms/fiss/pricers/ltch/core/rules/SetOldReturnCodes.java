package gov.cms.fiss.pricers.ltch.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.api.v2.LtchPaymentData;
import gov.cms.fiss.pricers.ltch.core.LtchPricerContext;
import gov.cms.fiss.pricers.ltch.core.codes.ErrorCode;
import gov.cms.fiss.pricers.ltch.core.codes.PaymentType;
import gov.cms.fiss.pricers.ltch.core.codes.ReturnCode;
import java.math.BigDecimal;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

public class SetOldReturnCodes
    implements CalculationRule<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {

  /**
   *
   *
   * <pre>
   * ***************************************************************
   * *   SET RETURN CODES FOR OLD POLICY CLAIMS                    *
   * ***************************************************************
   * </pre>
   *
   * Converted from {@code 7100-SET-OLD-RETURN-CODES}.
   */
  @Override
  public void calculate(LtchPricerContext calculationContext) {
    final PaymentType paymentType = calculationContext.getPaymentType();
    if (!ErrorCode.isErrorCode(calculationContext.getReturnCode())
        && paymentType.equals(PaymentType.STANDARD_OLD)) {
      setReturnCodes(calculationContext);
    }
  }

  private void setReturnCodes(LtchPricerContext calculationContext) {
    final LtchPaymentData paymentData = calculationContext.getPaymentData();
    // *-------------------------------------------------------------*
    // * ALTER RETURN CODES FOR OLD POLICY CLAIMS TO REFLECT OUTLIER *
    // * PAYMENT IF OUTLIER PAYMENT IS > $0                          *
    // *-------------------------------------------------------------*
    if (BigDecimalUtils.isGreaterThanZero(paymentData.getOutlierPayment())) {
      if ("21".equals(calculationContext.getReturnCode())) {
        calculationContext.applyReturnCode(ReturnCode.PROV_FY_BEGIN_DATE_BEFORE_10_01_2002_74);
      }
      if ("22".equals(calculationContext.getReturnCode())) {
        calculationContext.applyReturnCode(
            ReturnCode.SHORT_STAY_BASED_ON_LTC_DRG_AND_IPPS_COMP_WITH_OUTLIER_25);
      }
      if ("26".equals(calculationContext.getReturnCode())) {
        calculationContext.applyReturnCode(
            ReturnCode.SHORT_STAY_BASED_ON_IPPS_THRESHOLD_WITH_OUTLIER_27);
      }
      if (StringUtils.equals(
          ReturnCode.NORMAL_DRG_00.getCode(), calculationContext.getReturnCode())) {
        calculationContext.applyReturnCode(ReturnCode.NORMAL_DRG_WITH_OUTLIER_01);
      }
    }

    if (Arrays.asList("00", "20", "21", "22", "26").contains(calculationContext.getReturnCode())
        && BigDecimalUtils.isGreaterThan(
            BigDecimal.valueOf(paymentData.getRegularDaysUsed()),
            calculationContext.getHoldShortStayOutlierThreshold())) {
      paymentData.setLifetimeReserveDaysUsed(0);
    }
  }
}
