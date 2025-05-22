package gov.cms.fiss.pricers.ipps.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.api.v1.DrgsTableEntry;
import gov.cms.fiss.pricers.ipps.api.v2.AdditionalCalculationVariableData;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

/**
 * Initializes secondary variables for the claim response.
 *
 * <p>Converted from the {@code PPCAL} module in the COBOL code.
 *
 * @since 2019
 */
public class DefaultAdditionalVariableUpdate
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final AdditionalCalculationVariableData additionalVariables =
        calculationContext.getAdditionalVariables();

    //     MOVE    H-BUNDLE-ADJUST-AMT TO PPS-BUNDLE-ADJUST-AMT.
    additionalVariables
        .getAdditionalPaymentInformation()
        .setBundledAdjustmentPayment(
            getValueOrNull(calculationContext.getBundledAdjustmentPayment()));

    additionalVariables
        .getAdditionalCapitalVariables()
        .setCapitalFederalSpecificPortion2B(
            getValueOrNull(calculationContext.getCapital2BFederalSpecificPortionPart()));
    additionalVariables
        .getAdditionalCapitalVariables()
        .setCapitalHospitalSpecificPortionPart(
            getValueOrNull(calculationContext.getCapitalHospitalSpecificPortionPart()));
    additionalVariables
        .getAdditionalCapitalVariables()
        .setCapitalOutlier2B(getValueOrNull(calculationContext.getCapital2BOutlierPart()));
    additionalVariables
        .getAdditionalCapitalVariables()
        .setCapitalExceptionAmount(
            getValueOrNull(calculationContext.getCapitalExceptionPaymentRate()));
    additionalVariables
        .getAdditionalCapitalVariables()
        .setCapitalPaymentCode(calculationContext.getCapital2PayCode());
    final BigDecimal capitalFederalSpecificPortion =
        calculationContext
            .getCapitalFederalSpecificPortionPart()
            .multiply(calculationContext.getCapitalFederalSpecificPortionPct())
            .setScale(2, RoundingMode.HALF_UP);
    additionalVariables
        .getAdditionalCapitalVariables()
        .setCapitalDisproportionateShareHospitalAdjustment(
            getValueOrNull(
                calculationContext
                    .getCapitalDisproportionateShareHospital()
                    .multiply(capitalFederalSpecificPortion)
                    .setScale(2, RoundingMode.HALF_UP)));
    additionalVariables
        .getAdditionalCapitalVariables()
        .setCapitalFederalSpecificPortion(getValueOrNull(capitalFederalSpecificPortion));
    additionalVariables
        .getAdditionalCapitalVariables()
        .setCapitalIndirectMedicalEducationAdjustment(
            getValueOrNull(calculationContext.getCapitalIndirectMedicalEducationAdj()));

    additionalVariables
        .getAdditionalCapitalVariables()
        .setCapitalOldHoldHarmlessAmount(
            getValueOrNull(
                calculationContext.getCapitalOldHoldHarmless().setScale(2, RoundingMode.HALF_UP)));
    additionalVariables
        .getAdditionalCapitalVariables()
        .setCapitalOutlier(
            getValueOrNull(
                calculationContext.getCapitalOutlierPart().setScale(2, RoundingMode.HALF_UP)));
    additionalVariables
        .getAdditionalCapitalVariables()
        .setCapitalTotalPayment(
            getValueOrNull(
                calculationContext.getCapitalTotalPayment().setScale(2, RoundingMode.HALF_UP)));

    additionalVariables.setDischargeFraction(
        getValueOrNull(calculationContext.getDischargeFraction()));

    additionalVariables.setDrgRelativeWeight(
        getValueOrNull(
            Optional.ofNullable(calculationContext.getDrgsTableEntry())
                .orElse(DrgsTableEntry.ZERO_WEIGHT)
                .getWeight()));
    additionalVariables.setDrgRelativeWeightFraction(
        getValueOrNull(calculationContext.getDrgWeightFraction()));

    //     MOVE H-EHR-ADJUST-AMT TO  PPS-EHR-ADJUST-AMT.
    additionalVariables
        .getAdditionalPaymentInformation()
        .setElectronicHealthRecordAdjustmentPayment(
            getValueOrNull(calculationContext.getElectronicHealthRecordAdjustmentAmt()));

    additionalVariables.setFederalSpecificPortionPercent(
        getValueOrNull(calculationContext.getOperatingFederalSpecificPortionPct()));

    //     MOVE WK-HAC-AMOUNT  TO   PPS-HAC-PAYMENT-AMT.
    additionalVariables
        .getAdditionalPaymentInformation()
        .setHospitalAcquiredConditionPayment(
            getValueOrNull(
                calculationContext
                    .getHospitalAcquiredConditionAmount()
                    .setScale(2, RoundingMode.HALF_UP)));

    //     MOVE P-HAC-REDUC-IND  TO  PPS-HAC-PROG-REDUC-IND.
    additionalVariables.setHospitalReadmissionReductionIndicator(
        calculationContext.getHrrParticipantIndicator());
    additionalVariables.setHospitalReadmissionReductionAdjustment(
        getValueOrNull(calculationContext.getHospitalReadmissionReductionAdjustment()));

    additionalVariables.setIsletIsolationAddOnPayment(
        getValueOrNull(calculationContext.getIsletIsolationPaymentAddOn()));

    additionalVariables.setLowVolumePayment(
        getValueOrNull(calculationContext.getLowVolumePayment().setScale(2, RoundingMode.HALF_UP)));

    additionalVariables.setNationalPercent(getValueOrNull(calculationContext.getNationalPct()));
    additionalVariables.setNewTechnologyAddOnPayment(
        getValueOrNull(
            calculationContext.getNewTechAddOnPayment().setScale(2, RoundingMode.HALF_UP)));

    additionalVariables
        .getAdditionalOperatingVariables()
        .setOperatingBaseDrgPayment(
            getValueOrNull(calculationContext.getOperatingBaseDrgPayment()));
    additionalVariables
        .getAdditionalOperatingVariables()
        .setOperatingDollarThreshold(
            getValueOrNull(
                calculationContext
                    .getOperatingDollarThreshold()
                    .setScale(9, RoundingMode.HALF_UP)));

    //     MOVE    H-READMIS-ADJUST-AMT TO PPS-READMIS-ADJUST-AMT.
    additionalVariables
        .getAdditionalPaymentInformation()
        .setHospitalReadmissionReductionAdjustmentPayment(
            getValueOrNull(calculationContext.getReadmissionAdjustmentAmount()));

    //      MOVE H-STANDARD-ALLOWED-AMOUNT  TO  PPS-STNDRD-VALUE.
    additionalVariables
        .getAdditionalPaymentInformation()
        .setStandardValue(
            getValueOrNull(
                calculationContext.getStandardAllowedAmount().setScale(2, RoundingMode.HALF_UP)));

    //     MOVE    WK-UNCOMP-CARE-AMOUNT TO PPS-UNCOMP-CARE-AMOUNT.
    additionalVariables
        .getAdditionalPaymentInformation()
        .setUncompensatedCarePayment(
            getValueOrNull(calculationContext.getUncompensatedCareAmount()));

    additionalVariables.setValueBasedPurchasingAdjustmentAmount(
        getValueOrNull(calculationContext.getValueBasedPurchasingAdjustment()));

    //     MOVE    H-VAL-BASED-PURCH-ADJUST-AMT TO
    //                           PPS-VAL-BASED-PURCH-ADJUST-AMT.
    additionalVariables
        .getAdditionalPaymentInformation()
        .setValueBasedPurchasingAdjustmentPayment(
            getValueOrNull(calculationContext.getValueBasedPurchasingAdjustmentAmount()));
    additionalVariables.setValueBasedPurchasingParticipantIndicator(
        calculationContext.getValueBasedPurchasingParticipant());

    calculationContext
        .getOutput()
        .setCalculationVersion(calculationContext.getCalculationVersion());
  }

  private BigDecimal getValueOrNull(BigDecimal value) {
    BigDecimal returnVal = null;
    if (!BigDecimalUtils.isZero(value)) {
      returnVal = value;
    }

    return returnVal;
  }
}
