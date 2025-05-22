package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.api.v2.IppsPaymentData;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.ResultCode;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Initialize fields used in payment calculations.
 *
 * <p>Converted from {@code 3000-CALC-PAYMENT} in the COBOL code (continued).
 *
 * @since 2019
 */
public class InitializeCalculationFields
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final IppsPaymentData paymentData = calculationContext.getPaymentData();

    // ***********************************************************
    //     MOVE 00                 TO  PPS-RTC.
    //     MOVE H-WAGE-INDEX       TO  PPS-WAGE-INDX.
    //     MOVE H-ALOS             TO  PPS-AVG-LOS.
    //     MOVE H-DAYS-CUTOFF      TO  PPS-DAYS-CUTOFF.
    //     MOVE B-LOS TO H-PERDIEM-DAYS.
    calculationContext.applyResultCode(ResultCode.RC_00_OK);
    paymentData.setFinalWageIndex(calculationContext.getCbsaReference().getWageIndex());
    paymentData.setAverageLengthOfStay(
        calculationContext.getDrgsTableEntry().getGeometricMeanLengthOfStay());
    paymentData.setDaysCutoff(BigDecimal.ZERO.setScale(1, RoundingMode.UNNECESSARY));
    BigDecimal perdiemDays =
        BigDecimal.valueOf(calculationContext.getClaimData().getLengthOfStay());

    //     IF H-PERDIEM-DAYS < 1
    //         MOVE 1 TO H-PERDIEM-DAYS.
    if (BigDecimalUtils.isLessThan(perdiemDays, BigDecimal.ONE)) {
      perdiemDays = BigDecimal.ONE;
    }

    //     ADD 1 TO H-PERDIEM-DAYS.
    calculationContext.setPerDiemDays(perdiemDays.add(BigDecimal.ONE));
  }
}
