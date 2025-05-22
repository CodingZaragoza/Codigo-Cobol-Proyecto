package gov.cms.fiss.pricers.ltch.core.rules;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimData;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.api.v2.LtchPaymentData;
import gov.cms.fiss.pricers.ltch.core.LtchPricerContext;
import gov.cms.fiss.pricers.ltch.core.codes.ReturnCode;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;

public class EditInputData
    implements CalculationRule<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {

  /**
   * Converted from 1000-EDIT-INPUT-DATA. in COBOL. Sets RTC code if any checks fail and does not
   * price
   */
  @Override
  public void calculate(LtchPricerContext calculationContext) {
    // EDIT BILL (BILL-NEW-DATA) INPUT & SET ERROR CODE IF FAIL
    validateInputData(calculationContext);
  }

  private void validateInputData(LtchPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();
    final LtchClaimData claimData = calculationContext.getClaimData();
    final LtchPaymentData paymentData = calculationContext.getPaymentData();

    // TODO: Comment 1.5yr out-of-date. Possibly need to make this if/else
    if (claimData.getLengthOfStay() > 0) {
      calculationContext.setHoldLengthOfStay(BigDecimal.valueOf(claimData.getLengthOfStay()));
      paymentData.setLengthOfStay(claimData.getLengthOfStay());
    } else {
      calculationContext.applyReturnCode(ReturnCode.INVALID_LOS_56);
    }

    if ("Y".equals(providerData.getWaiverIndicator())) {
      calculationContext.applyReturnCode(ReturnCode.WAIVER_STATE_NOT_CALC_53);
    }
    if (LocalDateUtils.isBefore(claimData.getDischargeDate(), providerData.getEffectiveDate())) {
      calculationContext.applyReturnCode(ReturnCode.INVALID_DISCHARGE_DATE_55);
    }
    if (isProviderRecordTerminationDateInvalid(providerData, claimData)) {
      calculationContext.applyReturnCode(ReturnCode.PROV_RECORD_TERM_51);
    }
    if (isLifetimeReserveDaysInvalid(claimData)) {
      calculationContext.applyReturnCode(ReturnCode.LIFETIME_RESERVE_DAYS_NOT_NUM_OR_GR_60_61);
    }
    if (isCoveredDaysInvalid(paymentData, claimData)) {
      calculationContext.applyReturnCode(
          ReturnCode.COV_DAYS_NOT_NUM_OR_LT_LIFETIME_RESERVE_DAYS_62);
    }
    if (isReviewCodeInvalid(calculationContext)) {
      calculationContext.applyReturnCode(ReturnCode.INVALID_BLEND_IND_OR_REVIEW_CODE_72);
    }

    if (StringUtils.equals(
        ReturnCode.NORMAL_DRG_00.getCode(), calculationContext.getReturnCode())) {
      // CALCULATE DAY RELATED VARIABLE VALUES
      calculationContext.setHoldRegularDays(
          claimData.getCoveredDays() - claimData.getLifetimeReserveDays());
      calculationContext.setHoldTotalDays(
          calculationContext.getHoldRegularDays() + claimData.getLifetimeReserveDays());

      daysUsed(calculationContext);
    }

    // *** -----------------------------------------------------------
    // *** EDIT PSF FIELDS USED BY ALL CLAIMS & SET ERROR CODE IF FAIL
    // *** -----------------------------------------------------------
    // *-------------------------------------------------------------*
    // * PROVIDER FY BEGIN DATE BEFORE THE FIRST PPS FEDERAL FY      *
    // * (ALWAYS FED-FY-BEGIN-03)                                    *
    // *-------------------------------------------------------------*
    if (LocalDateUtils.isBefore(calculationContext.getFyBegin(), LocalDate.of(2002, 10, 1))) {
      calculationContext.applyReturnCode(ReturnCode.PROV_FY_BEGIN_DATE_BEFORE_10_01_2002_74);
    }

    // *** -----------------------------------------------------------
    // *** EDITS FOR PSF FIELDS USED FOR THE 4TH SHORT STAY PROVISION
    // *** -----------------------------------------------------------
    if (providerData.getCapitalIndirectMedicalEducationRatio() != null) {
      calculationContext.setHoldCapitalTeachingAdjustmentRatio(
          providerData.getCapitalIndirectMedicalEducationRatio());
    } else {
      calculationContext.setHoldCapitalTeachingAdjustmentRatio(BigDecimal.ZERO);
    }
    calculationContext.setHoldBedSize(BigDecimal.valueOf(providerData.getBedSize()));
  }

  private boolean isProviderRecordTerminationDateInvalid(
      InpatientProviderData providerData, LtchClaimData claimData) {
    return providerData.getTerminationDate() != null
        && providerData.getTerminationDate().isBefore(claimData.getDischargeDate());
  }

  private boolean isLifetimeReserveDaysInvalid(LtchClaimData claimData) {
    return claimData.getLifetimeReserveDays() > 60;
  }

  private boolean isCoveredDaysInvalid(LtchPaymentData paymentData, LtchClaimData claimData) {
    return claimData.getCoveredDays() == 0 && paymentData.getLengthOfStay() > 0
        || claimData.getLifetimeReserveDays() > claimData.getCoveredDays();
  }

  private boolean isReviewCodeInvalid(LtchPricerContext calculationContext) {
    return calculationContext.getClaimData().getReviewCode() == null
        || calculationContext.getReviewCodeAsInt() < 0
        || calculationContext.getReviewCodeAsInt() > 8;
  }

  /** Converted from 1200-DAYS-USED in COBOL. CALCULATE DAY RELATED VARIABLE VALUES */
  private void daysUsed(LtchPricerContext calculationContext) {
    final LtchClaimData claimData = calculationContext.getClaimData();
    final LtchPaymentData paymentData = calculationContext.getPaymentData();

    final int lifeTimeReserveDays = claimData.getLifetimeReserveDays();
    final int coveredDays = claimData.getCoveredDays();
    final int holdRegularDays = coveredDays - lifeTimeReserveDays;

    if (lifeTimeReserveDays > 0 && holdRegularDays == 0) {
      paymentData.setLifetimeReserveDaysUsed(
          Math.min(lifeTimeReserveDays, calculationContext.getHoldLengthOfStay().intValue()));
    } else if (holdRegularDays > 0 && lifeTimeReserveDays == 0) {
      paymentData.setRegularDaysUsed(
          Math.min(holdRegularDays, calculationContext.getHoldLengthOfStay().intValue()));
      paymentData.setLifetimeReserveDaysUsed(0);
    } else if (holdRegularDays > 0 && lifeTimeReserveDays > 0) {
      if (holdRegularDays > calculationContext.getHoldLengthOfStay().intValue()) {
        paymentData.setRegularDaysUsed(calculationContext.getHoldLengthOfStay().intValue());
        paymentData.setLifetimeReserveDaysUsed(0);
      } else if (calculationContext.getHoldTotalDays()
          > calculationContext.getHoldLengthOfStay().intValue()) {
        paymentData.setRegularDaysUsed(calculationContext.getHoldRegularDays());
        paymentData.setLifetimeReserveDaysUsed(
            calculationContext.getHoldLengthOfStay().intValue()
                - calculationContext.getHoldRegularDays());
      } else if (calculationContext.getHoldTotalDays()
          <= calculationContext.getHoldLengthOfStay().intValue()) {
        paymentData.setRegularDaysUsed(calculationContext.getHoldRegularDays());
        paymentData.setLifetimeReserveDaysUsed(claimData.getLifetimeReserveDays());
      }
    }
  }
}
