package gov.cms.fiss.pricers.ipps.core.rules.wage_index;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.CbsaReference;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;

/**
 * Determines whether the wage index value will be adjusted for outmigration.
 *
 * <p>Converted from {@code 0550-GET-CBSA} in the COBOL code.
 *
 * @since 2019
 */
public class AdjustForOutMigration
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final CbsaReference cbsaReference = calculationContext.getCbsaReference();

    // **----------------------------------------------------------------
    // ** FOR FYS 2018 AND AFTER, APPLY THE OUTMIGRATION ADJUSTMENT
    // **----------------------------------------------------------------
    //     IF OUTM-IND = 1
    //        COMPUTE W-NEW-CBSA-WI = W-NEW-CBSA-WI + HLD-OUTM-ADJ.
    if (cbsaReference.getWageIndex() != null) {
      cbsaReference.setWageIndex(
          cbsaReference.getWageIndex().add(calculationContext.getOutMigrationAdjustment()));
    } else {
      cbsaReference.setWageIndex(calculationContext.getOutMigrationAdjustment());
    }
  }
}
