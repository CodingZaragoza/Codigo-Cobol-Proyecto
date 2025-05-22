package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.totals;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.api.v2.IppsPaymentData;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.commons.lang3.StringUtils;

/**
 * Determine the low volume total amounts.
 *
 * <p>Converted from {@code 3800-CALC-TOT-AMT} in the COBOL code.
 *
 * @since 2019
 */
public class CalculateLowVolumeTotals
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final IppsPaymentData paymentData = calculationContext.getPaymentData();

    // ***********************************************************
    // *LOW VOLUME CALCULATIONS
    // ***********************************************************
    // *---------------------------------------------------------*
    // * (YEARCHANGE 2016.0)
    // * LOW VOLUME PAYMENT ADD-ON PERCENT
    // *---------------------------------------------------------*
    //     MOVE ZERO TO PPS-OPER-DSH-ADJ.
    paymentData.setOperatingDisproportionateShareHospitalAdjustment(BigDecimal.ZERO);

    // ************************************************
    // * FOR FY 2014 WE APPLY AN ADJUSTMENT OF 0.25 TO CALCULATE
    // * EMPERICAL DSH
    // ************************************************
    //     IF  H-OPER-DSH NUMERIC
    //         COMPUTE PPS-OPER-DSH-ADJ ROUNDED =
    //                     (PPS-OPER-FSP-PART  * H-OPER-DSH) * .25.
    paymentData.setOperatingDisproportionateShareHospitalAdjustment(
        paymentData
            .getOperatingFederalSpecificPortionPart()
            .multiply(calculationContext.getOperatingDisproportionateShare())
            .multiply(new BigDecimal("0.25"))
            .setScale(2, RoundingMode.HALF_UP));

    // 2023 Update -
    // Maintain IME adjustment value of $0 for HMO claims within the low volume calculations
    if (!calculationContext.isHmoClaim()) {
      paymentData.setOperatingIndirectMedicalEducationAdjustment(
          paymentData
              .getOperatingFederalSpecificPortionPart()
              .multiply(calculationContext.getOperatingIndirectMedicalEducation())
              .setScale(2, RoundingMode.HALF_UP)); // used
    }

    //     COMPUTE PPS-OPER-FSP-PART ROUNDED =
    //                           H-OPER-FSP-PART * H-OPER-FSP-PCT.
    paymentData.setOperatingFederalSpecificPortionPart(
        calculationContext
            .getOperatingFederalSpecificPortionPart()
            .multiply(calculationContext.getOperatingFederalSpecificPortionPct())
            .setScale(2, RoundingMode.HALF_UP));

    //     COMPUTE PPS-OPER-HSP-PART ROUNDED =
    //                           H-OPER-HSP-PART * H-OPER-HSP-PCT.
    paymentData.setOperatingHospitalSpecificPortionPart(
        calculationContext
            .getOperatingHospitalSpecificPortionPart()
            .multiply(calculationContext.getOperatingHospitalSpecificPortionPct())
            .setScale(2, RoundingMode.HALF_UP));

    //     COMPUTE PPS-OPER-OUTLIER-PART ROUNDED =
    //                         H-OPER-OUTLIER-PART * H-OPER-FSP-PCT.
    paymentData.setOperatingOutlierPaymentPart(
        calculationContext
            .getOperatingOutlierPart()
            .multiply(calculationContext.getOperatingFederalSpecificPortionPct())
            .setScale(2, RoundingMode.HALF_UP));

    BigDecimal lowVolAddon = BigDecimal.ZERO;
    final InpatientProviderData providerData = calculationContext.getProviderData();

    //     IF P-NEW-TEMP-RELIEF-IND = 'Y'
    //        AND P-LV-ADJ-FACTOR > 0.00
    //        AND P-LV-ADJ-FACTOR <= 0.25
    //     COMPUTE WK-LOW-VOL-ADDON ROUNDED =
    //       (PPS-OPER-HSP-PART +
    //        PPS-OPER-FSP-PART +
    //        PPS-OPER-IME-ADJ +
    //        PPS-OPER-DSH-ADJ +
    //        PPS-OPER-OUTLIER-PART +
    //        H-CAPI-FSP +
    //        H-CAPI-IME-ADJ +
    //        H-CAPI-DSH-ADJ +
    //        H-CAPI-OUTLIER +
    //        WK-UNCOMP-CARE-AMOUNT +
    //        PPS-NEW-TECH-PAY-ADD-ON) * P-LV-ADJ-FACTOR
    //     ELSE
    //     COMPUTE WK-LOW-VOL-ADDON ROUNDED = 0.
    if (StringUtils.equals(providerData.getTemporaryReliefIndicator(), "Y")
        && BigDecimalUtils.isGreaterThanZero(providerData.getLowVolumeAdjustmentFactor())
        && BigDecimalUtils.isLessThanOrEqualTo(
            providerData.getLowVolumeAdjustmentFactor(), new BigDecimal("0.25"))) {
      lowVolAddon =
          BigDecimalUtils.decimalSum(
                  paymentData.getOperatingHospitalSpecificPortionPart(),
                  paymentData.getOperatingFederalSpecificPortionPart(),
                  paymentData.getOperatingIndirectMedicalEducationAdjustment(),
                  paymentData.getOperatingDisproportionateShareHospitalAdjustment(),
                  paymentData.getOperatingOutlierPaymentPart(),
                  calculationContext.getCapitalFederalSpecificPortion(),
                  calculationContext.getCapitalIndirectMedicalEducationAdj(),
                  calculationContext.getCapitalDisproportionateShareHospitalAdjustment(),
                  calculationContext.getCapitalOutlierCost(),
                  calculationContext.getUncompensatedCareAmount(),
                  calculationContext.getNewTechAddOnPayment())
              .multiply(providerData.getLowVolumeAdjustmentFactor())
              .setScale(2, RoundingMode.HALF_UP);
    }

    //     COMPUTE H-LOW-VOL-PAYMENT ROUNDED = WK-LOW-VOL-ADDON.
    calculationContext.setLowVolumePayment(lowVolAddon);
  }
}
