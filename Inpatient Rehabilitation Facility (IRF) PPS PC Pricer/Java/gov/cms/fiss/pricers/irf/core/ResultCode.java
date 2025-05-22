package gov.cms.fiss.pricers.irf.core;

import gov.cms.fiss.pricers.common.api.ReturnCodeData;
import org.apache.commons.lang3.StringUtils;

public enum ResultCode {
  OK_00(0, "PAID OK", "Paid normal CMG payment without outlier"),
  CMG_PAYMENT_OUTLIER_01(1, "CMG PAYMENT OUTLIER", "Paid normal CMG payment with outlier"),
  TRANSFER_PAID_NO_OUTLIER_02(
      2, "TRANSFER PAID NO OUTLIER", "Transfer paid on a perdiem basis without outlier"),
  TRANSFER_PAID_OUTLIER_03(
      3, "TRANSFER PAID OUTLIER", "Transfer paid on a perdiem basis with outlier"),
  BLEND_CMG_PAY_NO_OUTLIER_04(
      4,
      "BLEND CMG PAY NO OUTLIER",
      "Blended CMG payment, 2/3 Federal PPS rate plus 1/3 provider specific rate, without outlier"),
  BLEND_CMG_PAY_OUTLIER_05(
      5,
      "BLEND CMG OUTLIER",
      "Blended CMG payment, 2/3 Federal PPS rate plus 1/3 provider specific rate,  with outlier"),
  BLEND_TRNSFR_PAY_NO_OUTLIER_06(
      6,
      "BLEND TRNSFR PAY NO OUTLIER",
      "Blended transfer payment, 2/3 Federal PPS transfer rate plus 1/3 provider specific rate, without outlier"),
  BLEND_TRNSFR_PAY_OUTLIER_07(
      7,
      "BLEND TRNSFR PAY OUTLIER",
      "Blended transfer payment, 2/3 Federal PPS transfer rate plus 1/3 provider specific rate, with outlier"),
  PENALTY_CMG_PAYMENT_10(
      10, "PENALTY CMG PAYMENT", "Paid normal CMG payment without outlier and with penalty"),
  PENALTY_CMG_PAYMENT_OUTLIER_11(
      11, "PENALTY CMG PAYMENT OUTLIER", "Paid normal CMG payment with outlier and penalty"),
  PENALTY_TRANSFER_PAID_NO_OUTLIER_12(
      12,
      "PENALTY TRANSFER PAID NO OUTLIER",
      "Transfer paid on a per diem basis without outlier " + "and with penalty"),
  PENALTY_TRANSFER_PAID_OUTLIER_13(
      13,
      "PENALTY TRANSFER PAID OUTLIER",
      "Transfer paid on per diem basis with outlier and penalty"),
  PENALTY_BLEND_CMG_PAY_NO_OUTLIER_14(
      14,
      "PENALTY BLEND CMG PAY NO OUTLIER",
      "Blended CMG payment, 2/3 Federal PPS rate plus 1/3 provider specific rate, with penalty, without outlier"),
  PENALTY_BLEND_CMG_PAY_OUTLIER_15(
      15,
      "PENALTY BLEND CMG PAY OUTLIER",
      "Blended CMG payment, 2/3 Federal PPS rate plus 1/3 provider specific rate, with penalty, with outlier"),
  PENALTY_BLEND_TRNSFR_PAY_NO_OUTLIER_16(
      16,
      "PENALTY BLEND TRNSFR PAY NO OUTLIER",
      "Blended transfer payment, 2/3 Federal PPS transfer rate plus 1/3 provider specific rate, with penalty, without outlier"),
  PENALTY_BLEND_TRNSFR_PAY_OUTLIER_17(
      17,
      "PENALTY BLEND TRNSFR PAY OUTLIER",
      "Blended transfer payment, 2/3 Federal PPS transfer rate plus 1/3 provider specific rate, with penalty, with outlier"),
  PROV_SPEC_RATE_NOT_NUM_50(50, "PROV SPEC RATE NOT NUM", "Provider specific rate not numeric"),
  PROV_REC_TERMINATED_51(51, "PROV REC TERMINATED", "Provider record terminated"),
  INVALID_WAGE_INDEX_52(52, "INVALID WAGE INDEX", "Invalid wage index"),
  WAIVER_STATE_NOT_CALC_53(53, "WAIVER STATE NOT CALC", "Waiver state - not calculated by PPS"),
  CMG_ON_CLAIM_NOT_IN_TABLE_54(54, "CMG CLAIM NOT IN TABLE", "CMG on claim not found on table"),
  DISCHRG_DT_LT_EFF_START_DT_55(
      55,
      "DISCHRG DT LT EFF START DT",
      "Discharge date less than provider effective start date or discharge date less than MSA effective start date for PPS"),
  INVALID_LENGTH_OF_STAY_56(56, "INVALID LENGTH OF STAY", "Invalid length of stay"),
  PROV_SPEC_RT_0_FOR_BLEND_57(
      57, "PROV SPEC RT 0 FOR BLEND", "Provider specific rate zero when blended rate requested"),
  TOT_COVERED_CHRGS_NOT_NUM_58(
      58, "TOT COVERED CHRGS NOT NUM", "Total covered charges not numeric"),
  PROV_REC_NOT_FD_59(59, "PROV REC NOT FD", "Provider specific record not found"),
  WAGE_INDEX_NOT_FD_60(60, "WAGE INDEX NOT FD", "CBSA wage index record not found"),
  LTR_NOT_NUM_OR_GT60_61(
      61,
      "LTR NOT NUM OR GT 60",
      "Lifetime reserve days not numeric or Bill-LTR days are greater than 60"),
  INVALID_NBR_COVERED_DAYS_62(62, "INVALID NBR COVERED DAYS", "Invalid number of covered days"),
  COST_TO_CHRG_NOT_NUM_65(
      65, "COST TO CHRG NOT NUM", "Operating cost-to-charge (CTC) not numeric or is zero"),
  OUTLIER_LOS_GT_COVERED_DAYS_67(
      67,
      "OUTLIER LOS GT COVERED DAYS",
      "Cost outlier with length of stay greater than covered days or cost outlier threshold calculation"),
  INVALID_BLEND_IND_72(72, "INVALID BLEND IND", "Invalid blend indicator (not 3 or 4)"),
  DISCHRG_PRIOR_TO_PROV_FY_73(
      73, "DISCHRG PRIOR TO PROV FY", "Discharged before provider fiscal year begin date"),
  PROV_FY_PRIOR_TO_2002_74(
      74, "PROV FY PRIOR TO 2002", "Provider fiscal year begin date not in 2002"),
  BILL_OUT_OF_PRICER_YEAR_RANGE_97(
      97,
      "BILL OUT OF PRICER YEAR RANGE",
      "Billing record contained a discharge date outside the range of the pricer"),
  BILL_OLDER_THAN_2002_98(
      98,
      "BILL OLDER THAN 2002",
      "Billing record contained a discharge date from before Jan 1, 2002");
  private final int code;
  private final String desc;
  private final String exp;

  ResultCode(int code, final String desc, final String exp) {
    this.code = code;
    this.desc = desc;
    this.exp = exp;
  }

  public ReturnCodeData toReturnCodeData() {
    final ReturnCodeData returnCodeData = new ReturnCodeData();
    final String paddedCode = StringUtils.leftPad(String.valueOf(this.code), 2, "0");

    returnCodeData.setCode(paddedCode);
    returnCodeData.setDescription(this.desc);
    returnCodeData.setExplanation(this.exp);

    return returnCodeData;
  }

  public static ResultCode fromCode(int code) {
    for (final ResultCode resultCode : values()) {
      if (code == resultCode.getCode()) {
        return resultCode;
      }
    }

    return null;
  }

  public int getCode() {
    return this.code;
  }

  public String getDescription() {
    return this.desc;
  }

  public String getExplanation() {
    return this.exp;
  }

  public boolean isPenalty() {
    switch (this) {
      case OK_00:
      case CMG_PAYMENT_OUTLIER_01:
      case TRANSFER_PAID_NO_OUTLIER_02:
      case TRANSFER_PAID_OUTLIER_03:
      case BLEND_CMG_PAY_NO_OUTLIER_04:
      case BLEND_CMG_PAY_OUTLIER_05:
      case BLEND_TRNSFR_PAY_NO_OUTLIER_06:
      case BLEND_TRNSFR_PAY_OUTLIER_07:
        return false;
      case PENALTY_CMG_PAYMENT_10:
      case PENALTY_CMG_PAYMENT_OUTLIER_11:
      case PENALTY_TRANSFER_PAID_NO_OUTLIER_12:
      case PENALTY_TRANSFER_PAID_OUTLIER_13:
      case PENALTY_BLEND_CMG_PAY_NO_OUTLIER_14:
      case PENALTY_BLEND_CMG_PAY_OUTLIER_15:
      case PENALTY_BLEND_TRNSFR_PAY_NO_OUTLIER_16:
      case PENALTY_BLEND_TRNSFR_PAY_OUTLIER_17:
        return true;
      default:
        return false;
    }
  }

  public boolean isOutlier() {
    switch (this) {
      case OK_00:
      case TRANSFER_PAID_NO_OUTLIER_02:
      case BLEND_CMG_PAY_NO_OUTLIER_04:
      case BLEND_TRNSFR_PAY_NO_OUTLIER_06:
      case PENALTY_CMG_PAYMENT_10:
      case PENALTY_TRANSFER_PAID_NO_OUTLIER_12:
      case PENALTY_BLEND_CMG_PAY_NO_OUTLIER_14:
      case PENALTY_BLEND_TRNSFR_PAY_NO_OUTLIER_16:
        return false;
      case CMG_PAYMENT_OUTLIER_01:
      case TRANSFER_PAID_OUTLIER_03:
      case BLEND_CMG_PAY_OUTLIER_05:
      case BLEND_TRNSFR_PAY_OUTLIER_07:
      case PENALTY_CMG_PAYMENT_OUTLIER_11:
      case PENALTY_TRANSFER_PAID_OUTLIER_13:
      case PENALTY_BLEND_CMG_PAY_OUTLIER_15:
      case PENALTY_BLEND_TRNSFR_PAY_OUTLIER_17:
        return true;
      default:
        return false;
    }
  }

  public boolean isError() {
    return isErrorCode(this.code);
  }

  public static boolean isErrorCode(int code) {
    return code >= 50;
  }

  /**
   * Maps the current result code to the corresponding penalty adjusted result code.
   *
   * @return penalty adjusted result code
   */
  public ResultCode adjustForPenalty() {
    // COMPUTE PPS-RTC = PPS-RTC + 10.
    switch (this) {
      case OK_00:
        return PENALTY_CMG_PAYMENT_10;
      case CMG_PAYMENT_OUTLIER_01:
        return PENALTY_CMG_PAYMENT_OUTLIER_11;
      case TRANSFER_PAID_NO_OUTLIER_02:
        return PENALTY_TRANSFER_PAID_NO_OUTLIER_12;
      case TRANSFER_PAID_OUTLIER_03:
        return PENALTY_TRANSFER_PAID_OUTLIER_13;
      case BLEND_CMG_PAY_NO_OUTLIER_04:
        return PENALTY_BLEND_CMG_PAY_NO_OUTLIER_14;
      case BLEND_CMG_PAY_OUTLIER_05:
        return PENALTY_BLEND_CMG_PAY_OUTLIER_15;
      case BLEND_TRNSFR_PAY_NO_OUTLIER_06:
        return PENALTY_BLEND_TRNSFR_PAY_NO_OUTLIER_16;
      case BLEND_TRNSFR_PAY_OUTLIER_07:
        return PENALTY_BLEND_TRNSFR_PAY_OUTLIER_17;
      default:
        return this;
    }
  }
}
