package gov.cms.fiss.pricers.ipf.core.rules.assemble_pps_variables;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;
import java.math.BigDecimal;

/**
 * Initialize emergency adjustment values.
 *
 * <pre>
 * ***************************************************************
 * ***  GET THE EMERGENCY ADJUSTMENT
 * ***************************************************************
 * </pre>
 *
 * <p>Converted from {@code 2000-ASSEMBLE-PPS-VARIABLES} in the COBOL code.
 */
public class CalculateEmergencyAdjustmentForOutput
    implements CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {

  @Override
  public void calculate(IpfPricerContext calculationContext) {
    // IF P-NEW-TEMP-RELIEF-IND = 'Y'
    //   MOVE 1.31 TO IPF-EMERG-ADJ
    //                DAY-VALUE2 (1)
    // ELSE
    //   MOVE 1.19 TO IPF-EMERG-ADJ
    //                DAY-VALUE2 (1).
    if (calculationContext.hasTemporaryReliefIndicator()) {
      setEmergencyAdjustmentAndDay1Value(
          calculationContext, calculationContext.getTemporaryReliefEmergencyAdjustment());
    } else {
      setEmergencyAdjustmentAndDay1Value(
          calculationContext, calculationContext.getDefaultEmergencyAdjustment());
    }

    // ***  CHECK FOR FACILITY W/O FULL SERVICE FROM CLAIM
    // IF BILL-SRC-OF-ADMISSION = 'D'
    //   MOVE 1.19 TO IPF-EMERG-ADJ
    //                DAY-VALUE2 (1).
    if (calculationContext.isSourceOfAdmission()) {
      setEmergencyAdjustmentAndDay1Value(
          calculationContext, calculationContext.getSourceOfAdmissionEmergencyAdjustment());
    }
  }

  private void setEmergencyAdjustmentAndDay1Value(
      IpfPricerContext calculationContext, BigDecimal adjustmentValue) {
    // Set in output
    calculationContext.getPaymentData().setEmergencyAdjustmentPercent(adjustmentValue);
    // Set in context
    calculationContext.setDay1Value(adjustmentValue);
  }
}
