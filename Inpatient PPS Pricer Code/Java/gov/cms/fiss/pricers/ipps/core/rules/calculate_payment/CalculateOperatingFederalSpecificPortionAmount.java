package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipps.api.v1.DrgsTableEntry;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.CbsaReference;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import java.math.RoundingMode;

/**
 * Determines the operating federal specific portion amount.
 *
 * <p>Converted from {@code 3300-CALC-OPER-FSP-AMT} in the COBOL code.
 *
 * @since 2019
 */
public class CalculateOperatingFederalSpecificPortionAmount
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final CbsaReference cbsaReference = calculationContext.getCbsaReference();
    final DrgsTableEntry drgsTableEntry = calculationContext.getDrgsTableEntry();

    // ***********************************************************
    // *  OPERATING FSP CALCULATION                              *
    // ***********************************************************
    //     COMPUTE H-OPER-FSP-PART ROUNDED =
    //       (H-NAT-PCT * (H-NAT-LABOR * H-WAGE-INDEX +
    //        H-NAT-NONLABOR * H-OPER-COLA) * H-DRG-WT *
    //        HLD-MID-ADJ-FACT)
    //           ON SIZE ERROR MOVE 0 TO H-OPER-FSP-PART.
    calculationContext.setOperatingFederalSpecificPortionPart(
        calculationContext
            .getNationalPct()
            .multiply(
                calculationContext
                    .getNationalLabor()
                    .multiply(cbsaReference.getWageIndex())
                    .add(
                        calculationContext
                            .getNationalNonLabor()
                            .multiply(calculationContext.getOperatingCostOfLivingAdjustment()))
                    .multiply(drgsTableEntry.getWeight())
                    .multiply(calculationContext.getMidnightAdjustmentFactor()))
            .setScale(9, RoundingMode.HALF_UP));
  }
}
