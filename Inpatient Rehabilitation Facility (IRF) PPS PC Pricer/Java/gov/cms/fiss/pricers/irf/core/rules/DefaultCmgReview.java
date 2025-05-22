package gov.cms.fiss.pricers.irf.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.api.v2.IrfPaymentData;
import gov.cms.fiss.pricers.irf.core.IrfPricerContext;
import gov.cms.fiss.pricers.irf.core.ResultCode;
import gov.cms.fiss.pricers.irf.core.tables.CmgTableEntry;
import java.math.RoundingMode;
import org.apache.commons.lang3.StringUtils;

/**
 * Retrieves CMG data.
 *
 * <pre>
 * ** REQUIREMENT: THE SYSTEM MUST RETURN AN ERROR CODE (CODE = 54)
 * ** IF THE PPS CASE MIX GROUP IS NOT NUMERIC OR IS GREATER THAN '2102'
 * </pre>
 *
 * Converted from {@code 1700-EDIT-CMG-CODE} and {@code 1750-FIND-VALUE} in the COBOL code.
 */
public class DefaultCmgReview
    implements CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  @Override
  public boolean shouldExecute(IrfPricerContext calculationContext) {
    return calculationContext.matchesReturnCode(ResultCode.OK_00);
  }

  @Override
  public void calculate(IrfPricerContext calculationContext) {
    final IrfPaymentData paymentData = calculationContext.getPaymentData();
    final String cmgCode = paymentData.getSubmittedCaseMixGroupCode();

    if (cmgCode.length() < 5) {
      calculationContext.applyResultCode(ResultCode.CMG_ON_CLAIM_NOT_IN_TABLE_54);
      return;
    }

    // Remove leading letter
    final String cmgNumeric = cmgCode.substring(1, 5);

    // IF PPS-CMG-NUMERIC = '9999' OR '5001'
    //    NEXT SENTENCE
    // ELSE
    //    IF PPS-CMG-NUMERIC < '2103'
    //       NEXT SENTENCE
    //    ELSE
    //       MOVE 54 TO PPS-RTC.
    if (!cmgNumeric.equals(IrfPricerContext.CMG_9999)
        && !cmgNumeric.equals(IrfPricerContext.CMG_5001)
        && cmgNumeric.compareTo(IrfPricerContext.CMG_2103) >= 0) {
      calculationContext.applyResultCode(
          ResultCode.CMG_ON_CLAIM_NOT_IN_TABLE_54,
          ResultCode.CMG_ON_CLAIM_NOT_IN_TABLE_54.getDescription(),
          "PPS case mix group is not numeric or greater than 2102");
      return;
    }

    //  SEARCH ALL CMG-DATA
    //     AT END
    //       MOVE 54 TO PPS-RTC
    //  WHEN CMG-NUM (DX6) = PPS-CMG-NUMERIC
    //       PERFORM 1750-FIND-VALUE
    //          THRU 1750-EXIT
    //  END-SEARCH.
    final CmgTableEntry cmgEntry = calculationContext.getCmgEntry(cmgNumeric);
    if (cmgEntry == null) {
      calculationContext.applyResultCode(ResultCode.CMG_ON_CLAIM_NOT_IN_TABLE_54);
    } else {
      findValue(calculationContext, paymentData, cmgEntry);
    }
  }

  /**
   * FINDS THE VALUE IN THE CMG CODE IN THE TABLE.
   *
   * <pre>
   * ** RULE: CASE MIX GROUPS MUST HAVE ONE OF FOUR SEVERITY LEVELS: A, B, C, OR D.
   * ** REQUIREMENT: THE SYSTEM MUST BE ABLE TO IDENTIFY AND USE THE
   * ** PPS-RELATIVE-WEIGHT AND PPS-AVERAGE LENGTH OF STAY FOR THE
   * ** CLAIM'S ASSIGNED CASE MIX GROUP SEVERITY LEVEL (A, B, C, OR D)
   * </pre>
   */
  protected void findValue(
      IrfPricerContext calculationContext, IrfPaymentData paymentData, CmgTableEntry cmgEntry) {

    switch (StringUtils.left(paymentData.getSubmittedCaseMixGroupCode(), 1)) {
        // IF PPS-CMG-ALPHA = 'A'
      case "A":
        // MOVE A-REL-WGT (DX6) TO PPS-RELATIVE-WGT
        paymentData.setCaseMixGroupRelativeWeight(
            cmgEntry.getARelativeWeight().setScale(4, RoundingMode.HALF_UP));
        // MOVE A-LOS-TABLE (DX6) TO PPS-AVG-LOS
        paymentData.setAverageLengthOfStay(cmgEntry.getAAvgLengthOfStay());

        break;
        // ELSE
        //   IF PPS-CMG-ALPHA = 'B'
      case "B":
        // MOVE B-REL-WGT (DX6) TO PPS-RELATIVE-WGT
        paymentData.setCaseMixGroupRelativeWeight(
            cmgEntry.getBRelativeWeight().setScale(4, RoundingMode.HALF_UP));
        // MOVE B-LOS-TABLE (DX6) TO PPS-AVG-LOS
        paymentData.setAverageLengthOfStay(cmgEntry.getBAvgLengthOfStay());

        break;
        // ELSE
        //    IF PPS-CMG-ALPHA = 'C'
      case "C":
        // MOVE C-REL-WGT (DX6) TO PPS-RELATIVE-WGT
        paymentData.setCaseMixGroupRelativeWeight(
            cmgEntry.getCRelativeWeight().setScale(4, RoundingMode.HALF_UP));
        // MOVE C-LOS-TABLE (DX6) TO PPS-AVG-LOS
        paymentData.setAverageLengthOfStay(cmgEntry.getCAvgLengthOfStay());

        break;
        // ELSE
        //    IF PPS-CMG-ALPHA = 'D'
      case "D":
        // MOVE D-REL-WGT (DX6) TO PPS-RELATIVE-WGT
        paymentData.setCaseMixGroupRelativeWeight(
            cmgEntry.getDRelativeWeight().setScale(4, RoundingMode.HALF_UP));
        // MOVE D-LOS-TABLE (DX6) TO PPS-AVG-LOS
        paymentData.setAverageLengthOfStay(cmgEntry.getDAvgLengthOfStay());

        break;
        // ELSE
      default:
        // MOVE 54 TO PPS-RTC.
        calculationContext.applyResultCode(
            ResultCode.CMG_ON_CLAIM_NOT_IN_TABLE_54,
            ResultCode.CMG_ON_CLAIM_NOT_IN_TABLE_54.getDescription(),
            "CMG provided in claim doesn't have a valid severity level");
    }
  }
}
