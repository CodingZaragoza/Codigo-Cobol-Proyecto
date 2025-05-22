package gov.cms.fiss.pricers.ltch.core.codes;

import gov.cms.fiss.pricers.common.api.ReturnCodeData;

public enum ReturnCode {
  NORMAL_DRG_00(
      "00",
      "NORMAL DRG PAYMENT WITHOUT OUTLIER",
      "Normal diagnosis related group payment without outlier"),
  NORMAL_DRG_WITH_OUTLIER_01(
      "01",
      "NORMAL DRG PAYMENT WITH OUTLIER",
      "Normal diagnosis related group payment with outlier"),
  SHORT_STAY_02("02", "SHORT STAY PAYMENT WITHOUT OUTLIER", "Short stay payment without outlier"),
  SHORT_STAY_WITH_OUTLIER_03(
      "03", "SHORT STAY PAYMENT WITH OUTLIER", "Short stay payment with outlier"),
  BLEND_YEAR_1_NORMAL_DRG_04(
      "04",
      "BLEND YEAR 1 - 80% FACILITY RATE PLUS 20% NORMAL DRG PAYMENT WITHOUT OUTLIER",
      "Blend year 1 - 80% facility rate plus 20% normal drg payment without outlier"),
  BLEND_YEAR_1_NORMAL_DRG_WITH_OUTLIER_05(
      "05",
      "BLEND YEAR 1 - 80% FACILITY RATE PLUS 20% NORMAL DRG PAYMENT WITH OUTLIER",
      "Blend year 1 - 80% facility rate plus 20% normal drg payment with outlier"),
  BLEND_YEAR_1_SHORT_STAY_06(
      "06",
      "BLEND YEAR 1 - 80% FACILITY RATE PLUS 20% SHORT STAY PAYMENT WITHOUT OUTLIER",
      "Blend year 1 - 80% facility rate 20% short stay payment without outlier"),
  BLEND_YEAR_1_SHORT_STAY_WITH_OUTLIER_07(
      "07",
      "BLEND YEAR 1 - 80% FACILITY RATE PLUS 20% SHORT STAY PAYMENT WITH OUTLIER",
      "Blend year 1 - 80% facility rate 20% short stay payment with outlier"),
  BLEND_YEAR_2_NORMAL_DRG_08(
      "08",
      "BLEND YEAR 2 - 60% FACILITY RATE PLUS 40% NORMAL DRG PAYMENT WITHOUT OUTLIER",
      "Blend year 2 - 60% facility rate plus 40% normal drg payment without outlier"),
  BLEND_YEAR_2_NORMAL_DRG_WITH_OUTLIER_09(
      "09",
      "BLEND YEAR 2 - 60% FACILITY RATE PLUS 40% NORMAL DRG PAYMENT WITH OUTLIER",
      "Blend year 2 - 60% facility rate plus 40% normal drg payment with outlier"),
  BLEND_YEAR_2_SHORT_STAY_10(
      "10",
      "BLEND YEAR 2 - 60% FACILITY RATE PLUS 40% SHORT STAY PAYMENT WITHOUT OUTLIER",
      "Blend year 2 - 60% facility rate plus 40% short stay payment without outlier"),
  BLEND_YEAR_2_SHORT_STAY_WITH_OUTLIER_11(
      "11",
      "BLEND YEAR 2 - 60% FACILITY RATE PLUS 40% SHORT STAY PAYMENT WITH OUTLIER",
      "Blend year 2 - 60% facility rate plus 40% short stay payment with outlier"),
  BLEND_YEAR_3_NORMAL_DRG_12(
      "12",
      "BLEND YEAR 3 - 40% FACILITY RATE PLUS 60% NORMAL DRG PAYMENT WITHOUT OUTLIER",
      "Blend year 3 - 40% facility rate plus 60% normal drg payment without outlier"),
  BLEND_YEAR_3_NORMAL_DRG_WITH_OUTLIER_13(
      "13",
      "BLEND YEAR 3 - 40% FACILITY RATE PLUS 60% NORMAL DRG PAYMENT WITH OUTLIER",
      "Blend year 3 - 40% facility rate plus 60% normal drg payment with outlier"),
  BLEND_YEAR_3_SHORT_STAY_14(
      "14",
      "BLEND YEAR 3 - 40% FACILITY RATE PLUS 60% SHORT STAY PAYMENT WITHOUT OUTLIER",
      "Blend year 3 - 40% facility rate plus 60% short stay payment without outlier"),
  BLEND_YEAR_3_SHORT_STAY_WITH_OUTLIER(
      "15",
      "BLEND YEAR 3 - 40% FACILITY RATE PLUS 60% SHORT STAY PAYMENT WITH OUTLIER",
      "Blend year 3 - 40% facility rate plus 60% short stay payment with outlier"),
  BLEND_YEAR_4_NORMAL_DRG_16(
      "16",
      "BLEND YEAR 4 - 20% FACILITY RATE PLUS 80% NORMAL DRG PAYMENT WITHOUT OUTLIER",
      "Blend year 4 - 20% facility rate plus 80% normal drg payment without outlier"),
  BLEND_YEAR_4_NORMAL_DRG_WITH_OUTLIER_17(
      "17",
      "BLEND YEAR 4 - 20% FACILITY RATE PLUS 80% NORMAL DRG PAYMENT WITH OUTLIER",
      "Blend year 4 - 20% facility rate plus 80% normal drg payment with outlier"),
  BLEND_YEAR_4_SHORT_STAY_18(
      "18",
      "BLEND YEAR 4 - 20% FACILITY RATE PLUS 80% SHORT STAY PAYMENT WITHOUT OUTLIER",
      "Blend year 4 - 20% facility rate plus 80% short stay payment without outlier"),
  BLEND_YEAR_4_SHORT_STAY_WITH_OUTLIER_19(
      "19",
      "BLEND YEAR 4 - 20% FACILITY RATE PLUS 80% SHORT STAY PAYMENT WITH OUTLIER",
      "Blend year 4 - 20% facility rate plus 80% short stay payment with outlier"),
  SHORT_STAY_BASED_ON_EST_COST_20(
      "20",
      "SHORT STAY PAYMENT BASED ON ESTIMATED COST WITHOUT OUTLIER",
      "Short stay payment based on estimated cost without outlier"),
  SHORT_STAY_BASED_ON_LTC_DRG_PER_DIEM_21(
      "21",
      "SHORT STAY PAYMENT BASED ON LTC-DRG PER DIEM WITHOUT OUTLIER",
      "Short stay payment based on LTC-DRG per diem without outlier"),
  SHORT_STAY_BASED_ON_LTC_DRG_AND_IPPS_COMP_22(
      "22",
      "SHORT STAY PAYMENT BASED ON BLEND OF LTC-DRG PER DIEM AND IPPS COMPARABLE AMOUNT WITHOUT OUTLIER",
      "Short stay payment based on blend of LTC-DRG per diem and IPPS comparable amount without outlier"),
  SHORT_STAY_BASED_ON_EST_COST_WITH_OUTLIER_23(
      "23",
      "SHORT STAY PAYMENT BASED ON ESTIMATED COST WITH OUTLIER",
      "Short stay payment based on estimated cost with outlier"),
  SHORT_STAY_BASED_ON_LTC_DRG_PER_DIEM_WITH_OUTLIER_24(
      "24",
      "SHORT STAY PAYMENT BASED ON LTC-DRG PER DIEM WITH OUTLIER",
      "Short stay payment based on LTC-DRG per diem with outlier"),
  SHORT_STAY_BASED_ON_LTC_DRG_AND_IPPS_COMP_WITH_OUTLIER_25(
      "25",
      "SHORT STAY PAYMENT BASED ON BLEND OF LTC-DRG PER DIEM AND IPPS COMPARABLE AMOUNT WITH OUTLIER",
      "Short stay payment based on blend of LTC-DRG per diem and IPPS comparable amount with outlier"),
  SHORT_STAY_BASED_ON_IPPS_THRESHOLD_WITH_OUTLIER_27(
      "27",
      "SHORT STAY BASED ON IPPS-COMPARABLE THRESHOLD WITH OUTLIER",
      "Short stay based on IPPS-comparable threshold with outlier"),
  PROV_SPEC_RATE_OR_COLA_NOT_NUM_50(
      "50",
      "PROVIDER SPECIFIC RATE OR COLA NOT NUMERIC",
      "Provider specific rate or cost of living adjustment not numeric"),
  PROV_RECORD_TERM_51("51", "PROVIDER RECORD TERMINATED", "Provider record terminated"),
  INVALID_WAGE_INDEX_52("52", "INVALID WAGE INDEX", "Invalid wage index"),
  WAIVER_STATE_NOT_CALC_53(
      "53", "WAIVER STATE NOT CALCULATED BY PPS", "Waiver state not calculated by PPS"),
  DRG_NOT_FOUND_54("54", "DRG ON CLAIM NOT FOUND IN TABLE", "DRG on claim not found in table"),
  INVALID_DISCHARGE_DATE_55(
      "55",
      "DISCHARGE DATE < PROVIDER EFF START DATE OR DISCHARGE DATE < CBSA EFF START DATE FOR PSS",
      "Discharge date < provider effective start date or discharge date < cbsa effective start date for PPS"),
  INVALID_LOS_56("56", "INVALID LENGTH OF STAY", "Invalid length of stay"),
  TOTAL_COVERED_CHARGES_NOT_NUM_58(
      "58", "TOTAL COVERED CHARGES NOT NUMERIC", "Total covered charges not numeric"),
  PROV_SPEC_RECORD_NOT_FOUND_59(
      "59", "PROVIDER SPECIFIC RECORD NOT FOUND", "Provider specific record not found"),
  CBSA_WAGE_INDEX_NOT_FOUND_60(
      "60", "CBSA WAGE INDEX RECORD NOT FOUND", "CBSA wage index record not found"),
  LIFETIME_RESERVE_DAYS_NOT_NUM_OR_GR_60_61(
      "61",
      "LIFETIME RESERVE DAYS NOT NUMERIC OR BILL-LTR-DAYS > 60",
      "Lifetime reserve days not numeric or BILL-LTR-DAYS > 60"),
  COV_DAYS_NOT_NUM_OR_LT_LIFETIME_RESERVE_DAYS_62(
      "62",
      "COVERED DAYS NOT NUMERIC OR BILL-LTR-DAYS > COVERED DAYS",
      "Covered days not numeric or BILL-LTR-DAYS > covered days"),
  OPER_COST_TO_CHARGE_NOT_NUM_65(
      "65",
      "OPERATING COST-TO-CHARGE RATIO NOT NUMERIC",
      "Operating cost-to-charge ratio not numeric"),
  COST_OUTLIER_WITH_LOS_LT_COV_DAYS_67(
      "67",
      "COST OUTLIER WITH LOS > COVERED DAYS OR COST OUTLIER THRESHOLD CALCULATION",
      "Cost outlier with LOS > covered days or cost outlier threshold calculation"),
  PROV_SPEC_STATE_CODE_INVALID_68(
      "68", "PROVIDER SPECIFIC STATE CODE INVALID", "Provider specific state code invalid"),
  INVALID_BLEND_IND_OR_REVIEW_CODE_72(
      "72", "INVALID BLEND INDICATOR OR REVIEW CODE", "Invalid blend indicator or review code"),
  DISCHARGED_BEFORE_PROV_FY_BEGIN_73(
      "73", "DISCHARGED BEFORE PROVIDER FY BEGIN", "Discharged before provider fiscal year begin"),
  PROV_FY_BEGIN_DATE_BEFORE_10_01_2002_74(
      "74",
      "PROVIDER FY BEGIN DATE BEFORE 10/01/2002",
      "Provider fiscal year begin date before 10/01/2002"),
  CANNOT_PROCESS_BILL_OLDER_THAN_FIVE_YEARS_98(
      "98",
      "CANNOT PROCESS BILL OLDER THAN FIVE YEARS",
      "Cannot process bill older than five years"),
  BLEND_YEAR_SITE_NEUTRAL_COST_PSYCH_REHAB_A0(
      "A0",
      "BLEND YR, SITE-NEUTRAL BASED ON COST, PSYCH/REHAB",
      "Blend year, site-neutral based on cost, psych/rehab"),
  BLEND_YEAR_SITE_NEUTRAL_COST_OUTLIER_PSYCH_REHAB_A1(
      "A1",
      "BLEND YR, SITE-NEUTRAL BASED ON COST, OUTLIER, PSYCH/REHAB",
      "Blend year, site-neutral based on cost, outlier, psych/rehab"),
  BLEND_YEAR_SITE_NEUTRAL_COST_SSO_PSYCH_REHAB_A2(
      "A2",
      "BLEND YR, SITE-NEUTRAL BASED ON COST, SSO, PSYCH/REHAB",
      "Blend year, site-neutral based on cost, SSO, psych/rehab"),
  BLEND_YEAR_SITE_NEUTRAL_COST_SSO_OUTLIER_PSYCH_REHAB_A3(
      "A3",
      "BLEND YR, SITE-NEUTRAL BASED ON COST, SSO, OUTLIER, PSYCH/REHAB",
      "Blend year, site-neutral based on cost, SSO, outlier, psych/rehab"),
  BLEND_YEAR_SITE_NEUTRAL_IPPS_PSYCH_REHAB_A4(
      "A4",
      "BLEND YR, SITE-NEUTRAL BASED ON IPPS, PSYCH/REHAB",
      "Blend year, site-neutral based on IPPS, psych/rehab"),
  BLEND_YEAR_SITE_NEUTRAL_IPPS_OUTLIER_PSYCH_REHAB_A5(
      "A5",
      "BLEND YR, SITE-NEUTRAL BASED ON IPPS, OUTLIER, PSYCH/REHAB",
      "Blend year, site-neutral based on IPPS, outlier, psych/rehab"),
  BLEND_YEAR_SITE_NEUTRAL_IPPS_SSO_PSYCH_REHAB_A6(
      "A6",
      "BLEND YR, SITE-NEUTRAL BASED ON IPPS, SSO, PSYCH/REHAB",
      "Blend year, site-neutral based on IPPS, SSO, psych/rehab"),
  BLEND_YEAR_SITE_NEUTRAL_IPPS_SSO_OUTLIER_PSYCH_REHAB_A7(
      "A7",
      "BLEND YR, SITE-NEUTRAL BASED ON IPPS, SSO, OUTLIER, PSYCH/REHAB",
      "Blend year, site-neutral based on IPPS, SSO, outlier, psych/rehab"),
  SITE_NEUTRAL_COST_PSYCH_REHAB_AA(
      "AA", "SITE-NEUTRAL BASED ON COST, PSYCH/REHAB", "Site-neutral based on cost, psych/rehab"),
  SITE_NEUTRAL_IPPS_PSYCH_REHAB_AB(
      "AB", "SITE-NEUTRAL BASED ON IPPS, PSYCH/REHAB", "Site-neutral based on IPPS, psych/rehab"),
  SITE_NEUTRAL_IPPS_OUTLIER_PSYCH_REHAB_AC(
      "AC",
      "SITE-NEUTRAL BASED ON IPPS, OUTLIER, PSYCH/REHAB",
      "Site-neutral based on IPPS, outlier, psych/rehab"),
  BLEND_YEAR_SITE_NEUTRAL_COST_VENT_B0(
      "B0",
      "BLEND YR, SITE-NEUTRAL BASED ON COST, VENT",
      "Blend year, site-neutral based on cost, vent"),
  BLEND_YEAR_SITE_NEUTRAL_COST_OUTLIER_VENT_B1(
      "B1",
      "BLEND YR, SITE-NEUTRAL BASED ON COST, OUTLIER, VENT",
      "Blend year, site-neutral based on cost, outlier, vent"),
  BLEND_YEAR_SITE_NEUTRAL_COST_SSO_VENT_B2(
      "B2",
      "BLEND YR, SITE-NEUTRAL BASED ON COST, SSO, VENT",
      "Blend year, site-neutral based on cost, SSO, vent"),
  BLEND_YEAR_SITE_NEUTRAL_COST_SSO_OUTLIER_VENT_B3(
      "B3",
      "BLEND YR, SITE-NEUTRAL BASED ON COST, SSO, OUTLIER, VENT",
      "Blend year, site-neutral based on cost, SSO, outlier, vent"),
  BLEND_YEAR_SITE_NEUTRAL_IPPS_VENT_B4(
      "B4",
      "BLEND YR, SITE-NEUTRAL BASED ON IPPS, VENT",
      "Blend year, site-neutral based on IPPS, vent"),
  BLEND_YEAR_SITE_NEUTRAL_IPPS_OUTLIER_VENT_B5(
      "B5",
      "BLEND YR, SITE-NEUTRAL BASED ON IPPS, OUTLIER, VENT",
      "Blend year, site-neutral based on IPPS, outlier, vent"),
  BLEND_YEAR_SITE_NEUTRAL_IPPS_SSO_VENT_B6(
      "B6",
      "BLEND YR, SITE-NEUTRAL BASED ON IPPS, SSO, VENT",
      "Blend year, site-neutral based on IPPS, SSO, vent"),
  BLEND_YEAR_SITE_NEUTRAL_IPPS_SSO_OUTLIER_VENT_B7(
      "B7",
      "BLEND YR, SITE-NEUTRAL BASED ON IPPS, SSO, OUTLIER, VENT",
      "Blend year, site-neutral based on IPPS, SSO, outlier, vent"),
  SITE_NEUTRAL_COST_VENT_BA(
      "BA", "SITE-NEUTRAL BASED ON COST, VENT", "Site-neutral based on cost, vent"),
  SITE_NEUTRAL_IPPS_VENT_BB(
      "BB", "SITE-NEUTRAL BASED ON IPPS, VENT", "Site-neutral based on IPPS, vent"),
  SITE_NEUTRAL_IPPS_OUTLIER_VENT_BC(
      "BC",
      "SITE-NEUTRAL BASED ON IPPS, OUTLIER, VENT",
      "Site-neutral based on IPPS, outlier, vent"),
  SSO_STANDARD_PAY_VENT_BD("BD", "SSO STANDARD PAYMENT, VENT", "SSO standard payment, vent"),
  SSO_STANDARD_PAY_OUTLIER_VENT_BE(
      "BE", "SSO STANDARD PAYMENT, OUTLIER, VENT", "SSO standard payment, outlier, vent"),
  STANDARD_FULL_DRG_VENT_BF(
      "BF",
      "STANDARD PAYMENT FULL DRG, VENT",
      "Standard payment full diagnosis related group, vent"),
  STANDARD_FULL_DRG_OUTLIER_VENT_BG(
      "BG",
      "STANDARD PAYMENT FULL DRG, OUTLIER, VENT",
      "Standard payment full diagnosis related group, outlier, vent"),
  BLEND_YEAR_SITE_NEUTRAL_COST_NO_VENT_C0(
      "C0",
      "BLEND YR, SITE-NEUTRAL BASED ON COST, NO VENT",
      "Blend year, site-neutral based on cost, no vent"),
  BLEND_YEAR_SITE_NEUTRAL_COST_OUTLIER_NO_VENT_C1(
      "C1",
      "BLEND YR, SITE-NEUTRAL BASED ON COST, OUTLIER, NO VENT",
      "Blend year, site-neutral based on cost, outlier, no vent"),
  BLEND_YEAR_SITE_NEUTRAL_COST_SSO_NO_VENT_C2(
      "C2",
      "BLEND YR, SITE-NEUTRAL BASED ON COST, SSO, NO VENT",
      "Blend year, site-neutral based on cost, SSO, no vent"),
  BLEND_YEAR_SITE_NEUTRAL_COST_SSO_OUTLIER_NO_VENT_C3(
      "C3",
      "BLEND YR, SITE-NEUTRAL BASED ON COST, SSO, OUTLIER, NO VENT",
      "Blend year, site-neutral based on cost, SSO, outlier, no vent"),
  BLEND_YEAR_SITE_NEUTRAL_IPPS_NO_VENT_C4(
      "C4",
      "BLEND YR, SITE-NEUTRAL BASED ON IPPS, NO VENT",
      "Blend year, site-neutral based on IPPS, no vent"),
  BLEND_YEAR_SITE_NEUTRAL_IPPS_OUTLIER_NO_VENT_C5(
      "C5",
      "BLEND YR, SITE-NEUTRAL BASED ON IPPS, OUTLIER, NO VENT",
      "Blend year, site-neutral based on IPPS, outlier, no vent"),
  BLEND_YEAR_SITE_NEUTRAL_IPPS_SSO_NO_VENT_C6(
      "C6",
      "BLEND YR, SITE-NEUTRAL BASED ON IPPS, SSO, NO VENT",
      "Blend year, site-neutral based on IPPS, SSO, no vent"),
  BLEND_YEAR_SITE_NEUTRAL_IPPS_SSO_OUTLIER_NO_VENT_C7(
      "C7",
      "BLEND YR, SITE-NEUTRAL BASED ON IPPS, SSO, OUTLIER, NO VENT",
      "Blend year, site-neutral based on IPPS, SSO, outlier, no vent"),
  SITE_NEUTRAL_COST_NO_VENT_CA(
      "CA", "SITE-NEUTRAL BASED ON COST, NO VENT", "Site-neutral based on cost, no vent"),
  SITE_NEUTRAL_IPPS_NO_VENT_CB(
      "CB", "SITE-NEUTRAL BASED ON IPPS, NO VENT", "Site-neutral based on IPPS, no vent"),
  SITE_NEUTRAL_IPPS_OUTLIER_NO_VENT_CC(
      "CC",
      "SITE-NEUTRAL BASED ON IPPS, OUTLIER, NO VENT",
      "Site-neutral based on IPPS, outlier, no vent"),
  SSO_STANDARD_PAY_NO_VENT_CD(
      "CD", "SSO STANDARD PAYMENT, NO VENT", "SSO standard payment, no vent"),
  SSO_STANDARD_PAY_OUTLIER_NO_VENT_CE(
      "CE", "SSO STANDARD PAYMENT, OUTLIER, NO VENT", "SSO standard payment, outlier, no vent"),
  STANDARD_PAY_FULL_DRG_NO_VENT_CF(
      "CF",
      "STANDARD PAYMENT FULL DRG, NO VENT",
      "Standard payment full diagnosis related group, no vent"),
  STANDARD_PAY_FULL_DRG_OUTLEIR_NO_VENT_CG(
      "CG",
      "STANDARD FULL DRG, OUTLIER, NO VENT",
      "Standard full diagnosis related group, outlier, no vent");

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
