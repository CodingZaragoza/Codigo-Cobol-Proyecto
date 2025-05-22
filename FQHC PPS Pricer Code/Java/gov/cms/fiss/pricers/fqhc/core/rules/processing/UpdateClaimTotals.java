package gov.cms.fiss.pricers.fqhc.core.rules.processing;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.fqhc.api.v2.FqhcPaymentData;
import gov.cms.fiss.pricers.fqhc.api.v2.IoceServiceLineData;
import gov.cms.fiss.pricers.fqhc.api.v2.ServiceLinePaymentData;
import gov.cms.fiss.pricers.fqhc.core.ServiceLineContext;

/**
 * Calculates the claim totals.
 *
 * <pre>
 * *****************************************************************
 *                                                                *
 * UPDATE CLAIM PAYMENT, COINSURANCE, AND REIMBURSEMENT WITH      *
 * LINE VALUES (ACCUMULATE)                                       *
 *                                                                *
 * *****************************************************************
 * </pre>
 *
 * <p>Converted from {@code 4400-UPDATE-CLAIM-TOTALS} in the COBOL code.
 */
public class UpdateClaimTotals
    implements CalculationRule<IoceServiceLineData, ServiceLinePaymentData, ServiceLineContext> {

  @Override
  public void calculate(ServiceLineContext calculationContext) {
    final FqhcPaymentData outputRecord = calculationContext.getFqhcPricerContext().getPaymentData();
    //        COMPUTE HC-TOT-CLM-PYMT =
    //                HC-TOT-CLM-PYMT + HL-LITEM-PYMT.
    outputRecord.setTotalPayment(
        outputRecord.getTotalPayment().add(calculationContext.getOutput().getPayment()));

    //        COMPUTE HC-TOT-CLM-COIN =
    //                HC-TOT-CLM-COIN + HL-LITEM-COIN.
    outputRecord.setTotalClaimCoinsuranceAmount(
        outputRecord
            .getTotalClaimCoinsuranceAmount()
            .add(calculationContext.getOutput().getCoinsuranceAmount()));

    // There is a total claim reimbursement field in COBOL called HC-TOT-CLM-REIM, but
    // according to the COBOL code it is not a field within the claim object
    // to return to the end user. That means that this will not be used so don't set it
    //  4400-UPDATE-CLAIM-TOTALS-EXIT.
    //        EXIT.
  }
}
