package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.api.v2.IppsPaymentData;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Determines the initial operating payment amounts and adjustments.
 *
 * <p>Converted from {@code 4000-CALC-TECH-ADDON} in the COBOL code (continued).
 *
 * @since 2019
 */
public class InitializeOperatingCosts
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final IppsPaymentData paymentData = calculationContext.getPaymentData();

    // ***********************************************************
    // ***  CALCULATE TOTALS FOR OPERATING  ADD ON FOR TECH
    //     COMPUTE PPS-OPER-HSP-PART ROUNDED =
    //         H-OPER-HSP-PCT * H-OPER-HSP-PART.
    paymentData.setOperatingHospitalSpecificPortionPart(
        calculationContext
            .getOperatingHospitalSpecificPortionPct()
            .multiply(calculationContext.getOperatingHospitalSpecificPortionPart())
            .setScale(2, RoundingMode.HALF_UP));

    //     COMPUTE PPS-OPER-FSP-PART ROUNDED =
    //         H-OPER-FSP-PCT * H-OPER-FSP-PART.
    paymentData.setOperatingFederalSpecificPortionPart(
        calculationContext
            .getOperatingFederalSpecificPortionPct()
            .multiply(calculationContext.getOperatingFederalSpecificPortionPart())
            .setScale(2, RoundingMode.HALF_UP));

    //     MOVE ZERO TO PPS-OPER-DSH-ADJ.
    paymentData.setOperatingDisproportionateShareHospitalAdjustment(BigDecimal.ZERO);

    //     IF  H-OPER-DSH NUMERIC
    //             COMPUTE PPS-OPER-DSH-ADJ ROUNDED =
    //             (PPS-OPER-FSP-PART
    //              * H-OPER-DSH) * .25.
    paymentData.setOperatingDisproportionateShareHospitalAdjustment(
        paymentData
            .getOperatingFederalSpecificPortionPart()
            .multiply(calculationContext.getOperatingDisproportionateShare())
            .multiply(new BigDecimal("0.25"))
            .setScale(2, RoundingMode.HALF_UP));

    //     COMPUTE PPS-OPER-IME-ADJ ROUNDED =
    //             PPS-OPER-FSP-PART *
    //             H-OPER-IME-TEACH.
    paymentData.setOperatingIndirectMedicalEducationAdjustment(
        paymentData
            .getOperatingFederalSpecificPortionPart()
            .multiply(calculationContext.getOperatingIndirectMedicalEducation())
            .setScale(2, RoundingMode.HALF_UP));

    //     COMPUTE H-BASE-DRG-PAYMENT ROUNDED =
    //             PPS-OPER-FSP-PART +
    //             PPS-OPER-DSH-ADJ + PPS-OPER-IME-ADJ +
    //             WK-UNCOMP-CARE-AMOUNT.
    calculationContext.setBaseDrgPayment(
        BigDecimalUtils.decimalSum(
                paymentData.getOperatingFederalSpecificPortionPart(),
                paymentData.getOperatingDisproportionateShareHospitalAdjustment(),
                paymentData.getOperatingIndirectMedicalEducationAdjustment(),
                calculationContext.getUncompensatedCareAmount())
            .setScale(2, RoundingMode.HALF_UP));
  }
}
