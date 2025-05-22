package gov.cms.fiss.pricers.fqhc.core;

import gov.cms.fiss.pricers.common.api.ReturnCodeData;

public enum ReturnCode {
  // CLAIM CODES
  CLAIM_OK_01("01", "CLAIM PROCESSED", "Claim processed"),
  CLAIM_INVALID_SERVICE_DATE_02(
      "02",
      "CLAIM NOT PROCESSED - SERVICE FROM DATE NOT NUMERIC OR LESS THAN FQHC PPS START DATE",
      "Service from date not numeric or less than the FQHC PSS start date"),

  // LINE CODES
  LINE_PAY_BASED_ON_PPS_RATE_01(
      "01", "PAYMENT BASED ON PPS RATE OR IOP RATE", "Payment based on " + "PPS rate or IOP rate"),
  LINE_PAY_BASED_ON_PROV_SUB_CHRGS_02(
      "02",
      "LINE PROCESSED - PAYMENT BASED ON PROVIDER SUBMITTED CHARGES",
      "Payment based on provider submitted charges"),
  LINE_PAY_BASED_ON_PPS_RATE_PS_PRES_03(
      "03",
      "LINE PROCESSED - PAYMENT BASED ON PPS RATE, PREVENTIVE SERVICE(S) PRESENT",
      "Payment based on PPS rate, preventive service(s) present"),
  LINE_PAY_BASED_ON_PROV_SUB_CHRGS_PS_PRES_04(
      "04",
      "LINE PROCESSED - PAYMENT BASED ON PROVIDER SUBMITTED CHARGES, PREVENTIVE SERVICE(S) PRESENT",
      "Payment based on provider submitted charges, preventive service(s) present"),
  LINE_PAY_BASED_ON_PPS_RATE_W_ADD_ON_PAY_05(
      "05",
      "PAYMENT BASED ON PPS RATE WITH ADD ON PAYMENT",
      "Payment based on PPS rate with add on payment"),
  LINE_SUP_MA_PAY_APPLIED_06(
      "06",
      "LINE PROCESSED - SUPPLEMENTAL MA PAYMENT APPLIED OR IOP PAYMENT APPLIED",
      "Supplemental MA payment applied or IOP payment applied"),
  LINE_SUP_MA_PAY_NOT_APPLIED_07(
      "07",
      "LINE PROCESSED - SUPPLEMENTAL MA PAYMENT NOT APPLIED",
      "Supplemental MA payment not " + "applied"),
  LINE_INFO_ONLY_08("08", "LINE PROCESSED - INFORMATIONAL ONLY", "Informational only"),
  LINE_PAY_APP_TO_ANOTHER_LINE_09(
      "09",
      "LINE PROCESSED - PAYMENT NOT APPLIED; PAYMENT APPLIED TO ANOTHER LINE",
      "Payment not applied, payment applied to another line"),
  LINE_PAY_METHOD_FLAG_INVALID_10(
      "10",
      "LINE PROCESSING DISCONTINUED - PAYMENT METHOD FLAG INVALID",
      "Line processing discontinued, payment method flag invalid for FQHC pricer"),
  LINE_PAY_IND_INVALID_11(
      "11",
      "LINE PROCESSING DISCONTINUED - PAYMENT INDICATOR INVALID",
      "Payment indicator invalid for FQHC Pricer"),
  LINE_COMP_ADJ_FLAG_INVALID_12(
      "12",
      "LINE PROCESSING DISCONTINUED - COMPOSITE ADJUSTMENT FLAG INVALID",
      "Composite adjustment flag invalid for FQHC pricer"),
  LINE_PACK_FLAG_INVALID_13(
      "13",
      "LINE PROCESSING DISCONTINUED - PACKAGING FLAG INVALID",
      "Packaging flag invalid for FQHC pricer"),
  LINE_ITEM_DENIAL_OR_REJ_FLAG_INVALID_14(
      "14",
      "LINE PROCESSING DISCONTINUED - LINE ITEM DENIAL OR REJECTION FLAG INVALID",
      "Line item denial or rejection flag invalid for FQHC pricer"),
  LINE_ITEM_ACTION_FLAG_INVALID_15(
      "15",
      "LINE PROCESSING DISCONTINUED - LINE ITEM ACTION FLAG INVALID",
      "Line item action flag invalid"),
  LINE_MA_PLAN_AMT_EQUALS_ZERO_17(
      "17",
      "LINE PROCESSING DISCONTINUED - MA PLAN AMOUNT EQUAL TO ZERO",
      "MA plan amount equal to zero"),
  LINE_NO_EFF_BASE_RATE_18(
      "18", "LINE PROCESSING DISCONTINUED - NO EFFECTIVE BASE RATE", "No effective base rate"),
  LINE_NO_EFF_GAF_19("19", "LINE PROCESSING DISCONTINUED - NO EFFECTIVE GAF", "No effective GAF"),
  LINE_NO_EFF_ADD_ON_RATE_20(
      "20", "LINE PROCESSING DISCONTINUED - NO EFFECTIVE ADD-ON RATE", "No effective add-on rate"),
  LINE_PAY_BASED_ON_GF_TRIBAL_PAY_21(
      "21",
      "LINE PROCESSED - PAYMENT BASED ON GRANDFATHERED TRIBAL FQHC (GFTF) PAYMENT",
      "Payment based on grandfathered tribal FQHC (GFTF) payment"),
  LINE_PAY_BASED_ON_GF_TRIBAL_SUB_CHRGS_22(
      "22",
      "LINE PROCESSED - PAYMENT BASED ON GRANDFATHERED TRIBAL FQHC (GFTF) SUBMITTED CHARGES",
      "Payment based on grandfathered tribal FQHC (GFTF) submitted charges"),
  INVALID_MDPCP_REDUCTION_PERCENTAGE_23(
      "23",
      "CLAIM NOT PROCESSED - DEMO 83 - NO REDUCTION WAS APPLICABLE DUE TO THE RATE NOT BEING 10, "
          + "25, 45, OR 65.",
      "MDPCP Reduction percentage was not valid."),
  MDPCP_REDUCTION_PERCENTAGE_IS_ZERO_24(
      "24",
      "CLAIM NOT PROCESSED - DEMO 83 NO REDUCTION WAS APPLICABLE DUE "
          + "TO THE REDUCTION RATE BEING ZERO OR BLANK",
      "MDPCP Reduction percentage is zero.");

  private final String code;
  private final String desc;
  private final String exp;

  ReturnCode(String code, final String desc, final String exp) {
    this.code = code;
    this.desc = desc;
    this.exp = exp;
  }

  public static ReturnCode fromCode(String code) {
    for (final ReturnCode returnCode : values()) {
      if (code.equals(returnCode.getCode())) {
        return returnCode;
      }
    }

    return null;
  }

  public String getCode() {
    return this.code;
  }

  public String getDescription() {
    return this.desc;
  }

  public String getExplanation() {
    return this.exp;
  }

  public ReturnCodeData toReturnCodeData() {
    final ReturnCodeData returnCodeData = new ReturnCodeData();
    returnCodeData.setCode(this.getCode());
    returnCodeData.setDescription(this.getDescription());
    returnCodeData.setExplanation(this.getExplanation());

    return returnCodeData;
  }
}
