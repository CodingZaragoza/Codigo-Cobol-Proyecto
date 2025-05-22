package gov.cms.fiss.pricers.ipps.core;

import gov.cms.fiss.pricers.common.api.ReturnCodeData;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

public enum ResultCode {
  RC_00_OK(0, "PAID NORMAL", "Paid normal CMG payment with outlier"),
  RC_02_TRANSFER_PAID_AS_OUTLIER(2, "TRANSFER PAID AS OUTLIER", "Transfer paid as a cost outlier"),
  RC_03_TRANSFER_PAID_PERDIEM_DAYS(
      3, "TRANSFER PAID AS PERDIEM DAYS", "Transfer paid on a " + "perdiem basis with outlier"),
  RC_05_TRANSFER_PAID_ON_A_PERDIEM_BASIS(
      5,
      "TRANSFER PAID ON A PERDIEM BASIS",
      "Transfer paid on " + "a perdiem basis up to an amount"),
  RC_06_PAY_XFER_NO_COST(6, "PAY XFER NO COST", "Pay transfer with no cost"),
  RC_10_POST_ACUTE_XFER(10, "POST ACUTE XFER", "Paid normal payment with post a acute transfer"),
  RC_12_POST_ACUTE_XFER_WITH_DRGS(
      12, "POST ACUTE XFER WITH DRGS", "Transfer paid with post acute transfer with specific drgs"),
  RC_14_PAID_DRG_WITH_PERDIEM(
      14, "PAID DRG WITH PERDIEM", "Paid normal DRG payment with Perdiem Days"),
  RC_16_PAID_AS_COST_OUTLIER_WITH_PERDIEM(
      16, "PAID AS COST OUTLIER WITH PERDIEM", "Paid as a cost-outlier with perdiem days"),
  RC_52_INVALID_WAGE_INDEX(52, "INVALID WAGE INDEX", "Invalid wage index"),
  RC_53_WAIVER_STATE_NOT_CALC(53, "WAIVER STATE NOT CALC", "Waiver state - not calculated by PPS"),
  RC_54_INVALID_DRG(54, "INVALID DRG", "DRG on claim not found on table"),
  RC_55_DISCHRG_DT_LT_EFF_START_DT(
      55,
      "DISCHRG DT LT EFF START DT",
      "Discharge date less than provider effective start date or discharge date less than MSA effective start date for PPS"),
  RC_56_INVALID_LENGTH_OF_STAY(
      56, "INVALID LENGTH OF STAY", "The length of stay provided is invalid"),
  RC_57_REVIEW_CODE_INVALID(
      57, "REVIEW CODE INVALID", "The Review Code provided is not a valid code"),
  RC_61_LTR_NOT_NUM_OR_GT60(
      61,
      "LTR NOT NUM OR GT60",
      "Lifetime reserve days not numeric or Bill-LTR days are greater than 60"),
  RC_62_INVALID_NBR_COVERED_DAYS(62, "INVALID NBR COVERED DAYS", "Invalid number of covered days"),
  RC_65_PAY_CODE_NOT_ABC(65, "PAY CODE NOT ABC", "Pay Code in Provider Records not A, B, or C"),
  RC_67_OUTLIER_LOS_GT_COVERED_DAYS(
      67,
      "OUTLIER LOS GT COVERED DAYS",
      "Cost outlier with length of stay greater than covered days or cost outlier threshold calculation"),
  RC_68_INVALID_VBPF_IN_PSF(
      68, "INVALID VBPF IN PSF", "Invalid Value Based Purchasing Flag in Provider File");

  private final int code;
  private final String desc;
  private final String exp;

  ResultCode(int code, final String desc, final String exp) {
    this.code = code;
    this.desc = desc;
    this.exp = exp;
  }

  public static boolean isErrorCode(int code) {
    return code >= 50;
  }

  public static ResultCode fromCode(int code) {
    return Arrays.stream(ResultCode.values())
        .filter(resultCode -> resultCode.getCode() == code)
        .findFirst()
        .orElse(null);
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

  public boolean isError() {
    return isErrorCode(this.code);
  }

  public ReturnCodeData toReturnCodeData() {
    final ReturnCodeData returnCodeData = new ReturnCodeData();
    final String paddedCode = StringUtils.leftPad(Integer.toString(this.getCode()), 2, "0");

    returnCodeData.setCode(paddedCode);
    returnCodeData.setDescription(this.desc);
    returnCodeData.setExplanation(this.exp);

    return returnCodeData;
  }
}
