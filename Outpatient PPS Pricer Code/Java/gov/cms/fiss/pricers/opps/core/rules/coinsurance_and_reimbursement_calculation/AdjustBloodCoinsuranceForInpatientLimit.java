package gov.cms.fiss.pricers.opps.core.rules.coinsurance_and_reimbursement_calculation;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.opps.api.v2.ServiceLinePaymentData;
import gov.cms.fiss.pricers.opps.core.CoinsuranceCapContext;
import gov.cms.fiss.pricers.opps.core.OppsPricerContext;
import gov.cms.fiss.pricers.opps.core.codes.ReturnCode;
import gov.cms.fiss.pricers.opps.core.model.CoinsuranceCapEntry;
import gov.cms.fiss.pricers.opps.core.model.CoinsuranceCapValues;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class AdjustBloodCoinsuranceForInpatientLimit
    implements CalculationRule<CoinsuranceCapEntry, CoinsuranceCapValues, CoinsuranceCapContext> {
  /**
   * REDUCE THE BLOOD LINE'S NATIONAL COINSURANCE AMOUNT AND ADD THE REDUCTION AMOUNT TO THE LINE'S
   * REIMBURSEMENT AMOUNT WHEN THE INPATIENT LIMIT IS EXCEEDED.
   *
   * <p>(19840-PROCESS-TYPE2)
   */
  @Override
  public void calculate(CoinsuranceCapContext calculationContext) {
    final OppsPricerContext pricerContext = calculationContext.getPricerContext();
    final CoinsuranceCapEntry entry = calculationContext.getInput();
    final CoinsuranceCapValues coinsuranceCapValues = calculationContext.getOutput();
    BigDecimal newTotal = coinsuranceCapValues.getTotal();

    if (calculationContext.getInput().getCode() == 2) {
      // CURRENT TYPE 2 BLOOD COIN REC HAS SAME DATE OF SERVICE AS THE LAST TYPE 1 RECORD PROCESSED
      if (entry.getDateOfService().equals(coinsuranceCapValues.getLastCoinsuranceDateOfService())) {

        // GO TO SERVICE LINE THAT CORRESPONDS TO THE BLOOD COIN RECORD
        final ServiceLinePaymentData line =
            pricerContext.getServiceLinePaymentByLineNumber(entry.getServiceLine().getLineNumber());

        // CALCULATE ACTUAL COIN AMT TO BE REIMBURSED DUE TO IP LIMIT
        // COMPUTE H-SHIFT = W-DCP-COIN2 (W-DCP-INDX) * (1 - H-RATIO)
        BigDecimal shift =
            entry
                .getCoinsurance2()
                .multiply(BigDecimal.ONE.subtract(coinsuranceCapValues.getRatio()))
                .setScale(2, RoundingMode.DOWN);

        // ACCUMULATE THE TOTAL NATIONAL COIN DUE FOR THE DAY (LESS ACTUAL COIN AMT TO BE REIMBURSED
        // DUE TO IP LIMIT)
        // COMPUTE H-TOTAL = A-ADJ-COIN (LN-SUB) + H-TOTAL - H-SHIFT
        newTotal = line.getCoinsuranceAmount().add(newTotal).subtract(shift);

        // DETERMINE HOW MUCH THE DAY'S TOTAL NATIONAL COIN DUE
        // EXCEEDS THE DAILY INPATIENT LIMIT (IF IT EXCEEDS THE LIMIT)
        //
        // OCCURS WHEN THE NATIONAL COIN OF THIS LINE AND/OR PREVIOUS
        // LINES FROM THE SAME DAY IS GREATER THAN THE ACTUAL COIN DUE
        // & PUSHES THE DAILY TOTAL OVER THE INPATIENT LIMIT
        if (BigDecimalUtils.isGreaterThan(newTotal, pricerContext.getInpatientDeductibleLimit())) {

          // COMPUTE H-SHIFT = H-SHIFT + H-TOTAL - H-IP-LIMIT
          shift = shift.add(newTotal).subtract(pricerContext.getInpatientDeductibleLimit());
        }

        // CALCULATE THE ADJUSTED NATIONAL COIN FOR THE BLOOD LINE BY DEDUCTING THE AMT THAT EXCEEDS
        // THE INPATIENT LIMIT
        // COMPUTE A-ADJ-COIN (LN-SUB) = A-ADJ-COIN (LN-SUB) - H-SHIFT
        line.setCoinsuranceAmount(line.getCoinsuranceAmount().subtract(shift));

        // check if coinsuranceAmount is LESS Than Zero, set coinsuranceAmount to Zero
        // Negative Value validation check
        // Set boolean for reimbursement calculation to true
        boolean reimbursementWith25Percent = false;
        if (BigDecimalUtils.isLessThanZero(line.getCoinsuranceAmount())) {
          line.setCoinsuranceAmount(BigDecimalUtils.ZERO);
          reimbursementWith25Percent = true;
        }
        // check if reimbursement flag is True, will re-add 25% back to reimbursement based on
        // medicare 80/20 bene split of total payment unadjusted
        if (reimbursementWith25Percent == true) {
          line.setReimbursementAmount(
              line.getReimbursementAmount().add(calculationContext.getInput().getCoinsurance2()));
        } else {

          // ADD BLOOD COIN AMT THAT EXCEEDS IP LIMIT TO LINE REIM AMT SET RETURN CODE TO INDICATE
          // DAILY
          // COINSURANCE LIMITATION
          // COMPUTE A-LITEM-REIM (LN-SUB) = A-LITEM-REIM (LN-SUB) + H-SHIFT
          line.setReimbursementAmount(line.getReimbursementAmount().add(shift));
        }

        line.setReturnCode(ReturnCode.DAILY_COINSURANCE_LIMITATION_22.toReturnCodeData());
      }
      coinsuranceCapValues.setTotal(newTotal);
    }
  }
}
