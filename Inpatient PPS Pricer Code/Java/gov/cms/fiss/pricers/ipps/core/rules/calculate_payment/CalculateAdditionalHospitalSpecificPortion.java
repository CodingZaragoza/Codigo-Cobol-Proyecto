package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.api.v1.DrgsTableEntry;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.CbsaReference;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.ResultCode;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Calculate the additional hospital specific portion amount.
 *
 * <p>Converted from {@code 3450-CALC-ADDITIONAL-HSP} in the COBOL code.
 *
 * @since 2019
 */
public class CalculateAdditionalHospitalSpecificPortion
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public boolean shouldExecute(IppsPricerContext calculationContext) {
    return !ArrayUtils.contains(
        new ResultCode[] {
          ResultCode.RC_65_PAY_CODE_NOT_ABC,
          ResultCode.RC_67_OUTLIER_LOS_GT_COVERED_DAYS,
          ResultCode.RC_68_INVALID_VBPF_IN_PSF
        },
        calculationContext.getResultCode());
  }

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    //     IF P-N-SCH-REBASED-FY90 OR
    //        P-N-EACH OR
    //        P-N-MDH-REBASED-FY90
    //         PERFORM 3450-CALC-ADDITIONAL-HSP THRU 3450-EXIT.
    if (calculationContext.isSchRebasedFy90ProviderType()
        || calculationContext.isEachProviderType()
        || calculationContext.isInvalidProviderType()) {
      calculateAdditionalHsp(calculationContext);
    }
  }

  protected void calculateAdditionalHsp(IppsPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();

    // ***********************************************************
    // *---------------------------------------------------------*
    // * OBRA 89 CALCULATE ADDITIONAL HSP PAYMENT FOR SOLE COMMUNITY
    // * AND ESSENTIAL ACCESS COMMUNITY HOSPITALS (EACH)
    // * NOW REIMBURSED WITH 100% NATIONAL FEDERAL RATES
    // *---------------------------------------------------------*
    final BigDecimal updateFactor = calculateUpdateFactor(calculationContext);

    //     COMPUTE H-HSP-RATE ROUNDED =
    //         H-FAC-SPEC-RATE * H-UPDATE-FACTOR * H-DRG-WT.
    final BigDecimal hspRate = getHspRate(calculationContext, updateFactor);
    calculationContext.setHospitalSpecificPortionRate(hspRate);

    // ***************************************************************
    // ********YEARCHANGE 2011.0 *************************************
    // ***     OUTLIER OFFSETS NO LONGER USED IN HSP COMPARISON
    // ***     WE NOW USE THE ACTUAL OPERATING OUTLIER PAYMEMT
    // ***     IN THE HSP COMPARRISON
    // ********YEARCHANGE 2014.0 *XXXXXX******************************
    // *      THE HSP BUCKET FOR SCH                      ************
    // *      ADDED UNCOMPENSATED CARE TO COMPARRISON FOR 2014 *******
    final BigDecimal fspRate = getFspRate(calculationContext);
    final BigDecimal adjustedFspRate = fspRate.add(calculationContext.getUncompensatedCareAmount());

    // ****************************************************************
    // ****         INCLUDE UNCOMPENSATED CARE PER CLAIM IN HSP
    // *****        CHOICE
    //     IF  H-HSP-RATE > (H-FSP-RATE + WK-UNCOMP-CARE-AMOUNT)
    //           COMPUTE H-OPER-HSP-PART ROUNDED =
    //             (H-HSP-RATE - (H-FSP-RATE + WK-UNCOMP-CARE-AMOUNT))
    //                   ON SIZE ERROR MOVE 0 TO H-OPER-HSP-PART
    //     ELSE
    //         MOVE 0 TO H-OPER-HSP-PART.
    if (BigDecimalUtils.isGreaterThan(hspRate, adjustedFspRate)) {
      calculationContext.setOperatingHospitalSpecificPortionPart(
          hspRate.subtract(adjustedFspRate).setScale(9, RoundingMode.HALF_UP));
    }

    // ***************************************************************
    // ***  YEARCHANGE TURNING MDH BACK ON ***************************
    // ***************************************************************
    // ***  GET THE MDH REBASE
    //     IF  H-HSP-RATE > (H-FSP-RATE + WK-UNCOMP-CARE-AMOUNT)
    //         IF P-NEW-PROVIDER-TYPE = '14' OR '15'
    //           COMPUTE H-OPER-HSP-PART ROUNDED =
    //         (H-HSP-RATE - (H-FSP-RATE + WK-UNCOMP-CARE-AMOUNT)) * .75
    //                   ON SIZE ERROR MOVE 0 TO H-OPER-HSP-PART.
    if (BigDecimalUtils.isGreaterThan(hspRate, adjustedFspRate)
        && StringUtils.equalsAny(providerData.getProviderType(), "14", "15")) {
      calculationContext.setOperatingHospitalSpecificPortionPart(
          hspRate
              .subtract(adjustedFspRate)
              .multiply(new BigDecimal("0.75"))
              .setScale(9, RoundingMode.HALF_UP));
    }
  }

  protected BigDecimal getFspRate(IppsPricerContext calculationContext) {
    final CbsaReference cbsaReference = calculationContext.getCbsaReference();

    // ***************************************************************
    //     COMPUTE H-FSP-RATE ROUNDED =
    //        ((H-NAT-PCT * (H-NAT-LABOR * H-WAGE-INDEX +
    //         H-NAT-NONLABOR * H-OPER-COLA)) * H-DRG-WT-FRCTN *
    //         HLD-MID-ADJ-FACT) *
    //             (1 + H-OPER-IME-TEACH + (H-OPER-DSH * .25))
    //                               +
    //                         H-OPER-OUTLIER-PART
    //                   ON SIZE ERROR MOVE 0 TO H-FSP-RATE.
    return calculationContext
        .getNationalPct()
        .multiply(
            calculationContext
                .getNationalLabor()
                .multiply(cbsaReference.getWageIndex())
                .add(
                    calculationContext
                        .getNationalNonLabor()
                        .multiply(calculationContext.getOperatingCostOfLivingAdjustment())))
        .multiply(calculationContext.getDrgWeightFraction())
        .multiply(calculationContext.getMidnightAdjustmentFactor())
        .multiply(
            BigDecimal.ONE
                .add(calculationContext.getOperatingIndirectMedicalEducation())
                .add(
                    calculationContext
                        .getOperatingDisproportionateShare()
                        .multiply(new BigDecimal(".25"))))
        .add(calculationContext.getOperatingOutlierPart())
        .setScale(9, RoundingMode.HALF_UP);
  }

  protected BigDecimal getHspRate(IppsPricerContext calculationContext, BigDecimal updateFactor) {
    final InpatientProviderData providerData = calculationContext.getProviderData();
    final DrgsTableEntry drgsTableEntry = calculationContext.getDrgsTableEntry();

    return providerData
        .getPpsFacilitySpecificRate()
        .multiply(updateFactor.multiply(drgsTableEntry.getWeight()))
        .setScale(9, RoundingMode.HALF_UP);
  }

  protected BigDecimal calculateUpdateFactor(IppsPricerContext calculationContext) {
    // ***  GET THE RBN UPDATING FACTOR
    // *****YEARCHANGE 2019.0 ****************************************
    //     MOVE 0.997190 TO H-BUDG-NUTR190.
    // ***  GET THE MARKET BASKET UPDATE FACTOR
    // *****YEARCHANGE 2019.0 ****************************************
    //        MOVE 1.01350 TO H-UPDATE-190.
    // *** APPLY APPROPRIATE MARKET BASKET UPDATE FACTOR PER PSF FLAGS
    final BigDecimal currentYearUpdateFactor = calculateCurrentYearUpdateFactor(calculationContext);

    // ********YEARCHANGE 2019.0 *************************************
    //     COMPUTE H-UPDATE-FACTOR ROUNDED =
    //                       (H-UPDATE-190 *
    //                        H-BUDG-NUTR190 *
    //                        HLD-MID-ADJ-FACT).
    return currentYearUpdateFactor
        .multiply(calculationContext.getPriorYearHospitalSpecificPortionUpdateFactor())
        .multiply(calculationContext.getBudgetNeutralBase())
        .multiply(calculationContext.getMidnightAdjustmentFactor())
        .setScale(5, RoundingMode.HALF_UP);
  }

  protected BigDecimal calculateCurrentYearUpdateFactor(IppsPricerContext calculationContext) {
    // ***  GET THE RBN UPDATING FACTOR
    // *****YEARCHANGE 2019.0 ****************************************
    //     MOVE 0.997190 TO H-BUDG-NUTR190.
    // ***  GET THE MARKET BASKET UPDATE FACTOR
    // *****YEARCHANGE 2019.0 ****************************************
    //        MOVE 1.01350 TO H-UPDATE-190.
    // *** APPLY APPROPRIATE MARKET BASKET UPDATE FACTOR PER PSF FLAGS
    BigDecimal currentYearUpdateFactor = BigDecimal.ONE;

    final InpatientProviderData providerData = calculationContext.getProviderData();

    // *****YEARCHANGE 2019.0 ****************************************
    //     IF P-NEW-CBSA-HOSP-QUAL-IND = '1' AND
    //        P-EHR-REDUC-IND = ' '
    //        MOVE 1.01350 TO H-UPDATE-190.
    if (StringUtils.equals(providerData.getHospitalQualityIndicator(), "1")
        && StringUtils.isBlank(providerData.getEhrReductionIndicator())) {
      currentYearUpdateFactor =
          calculationContext.getHospitalSpecificPortionUpdateFactorWithQualityAndNoEhrReduction();
    }

    // *****YEARCHANGE 2019.0 ****************************************
    //     IF P-NEW-CBSA-HOSP-QUAL-IND = '1' AND
    //        P-EHR-REDUC-IND = 'Y'
    //        MOVE 0.99175 TO H-UPDATE-190.
    if (StringUtils.equals(providerData.getHospitalQualityIndicator(), "1")
        && StringUtils.equals(providerData.getEhrReductionIndicator(), "Y")) {
      currentYearUpdateFactor =
          calculationContext.getHospitalSpecificPortionUpdateFactorWithQualityAndEhrReduction();
    }

    // *****YEARCHANGE 2019.0 ****************************************
    //     IF P-NEW-CBSA-HOSP-QUAL-IND NOT = '1' AND
    //        P-EHR-REDUC-IND = ' '
    //        MOVE 1.00625 TO H-UPDATE-190.
    if (!StringUtils.equals(providerData.getHospitalQualityIndicator(), "1")
        && StringUtils.isBlank(providerData.getEhrReductionIndicator())) {
      currentYearUpdateFactor =
          calculationContext.getHospitalSpecificPortionUpdateFactorWithNoQualityAndNoEhrReduction();
    }

    // *****YEARCHANGE 2019.0 ****************************************
    //     IF P-NEW-CBSA-HOSP-QUAL-IND NOT = '1' AND
    //        P-EHR-REDUC-IND = 'Y'
    //        MOVE 0.98450 TO H-UPDATE-190.
    if (!StringUtils.equals(providerData.getHospitalQualityIndicator(), "1")
        && StringUtils.equals(providerData.getEhrReductionIndicator(), "Y")) {
      currentYearUpdateFactor =
          calculationContext.getHospitalSpecificPortionUpdateFactorWithNoQualityAndEhrReduction();
    }

    return currentYearUpdateFactor;
  }
}
