package gov.cms.fiss.pricers.ltch.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ltch.api.v1.InpatientDrgsTableEntry;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimData;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.api.v2.LtchPaymentData;
import gov.cms.fiss.pricers.ltch.core.LtchPricerContext;
import gov.cms.fiss.pricers.ltch.core.codes.ReturnCode;

public class EditIppsDrgCode
    implements CalculationRule<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {

  /**
   * Converted from 1800-EDIT-IPPS-DRG-CODE in COBOL. FINDS THE IPPS DRG CODE IN THE TABLE. sets
   * holder variables if needed
   */
  @Override
  public void calculate(LtchPricerContext calculationContext) {
    // **-------------------------------------------------------**
    // ** THIS LOGIC WAS COPIED FROM THE IPPS PRICER (PPCAL140) **
    // ** ENSURE IT STAYS CONSISTENT BECAUSE IT REFERENCES THE  **
    // ** DRG TABLE USED BY THE IPPS PRICER.                    **
    // **-------------------------------------------------------**
    final LtchClaimData claimData = calculationContext.getClaimData();
    final LtchPaymentData paymentData = calculationContext.getPaymentData();

    final String drgCode = claimData.getDiagnosisRelatedGroup();
    final InpatientDrgsTableEntry entry =
        calculationContext
            .getDataTables()
            .getInpatientDrgEntry(drgCode, claimData.getDischargeDate());
    if (entry == null || drgCode == null) {
      calculationContext.applyReturnCode(ReturnCode.DRG_NOT_FOUND_54);
      calculationContext.setPaymentData(paymentData);
      return;
    }

    calculationContext.setHoldIppsDrgWeight(entry.getWeight());
    calculationContext.setHoldIppsDrgALengthOfStay(entry.getGeometricMeanLengthOfStay());
    calculationContext.setHoldIppsArithALengthOfStay(entry.getArithmeticMeanLengthOfStay());
  }
}
