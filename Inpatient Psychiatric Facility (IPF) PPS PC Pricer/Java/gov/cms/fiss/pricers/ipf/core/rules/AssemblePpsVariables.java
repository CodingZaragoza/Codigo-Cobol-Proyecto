package gov.cms.fiss.pricers.ipf.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.EvaluatingCalculationRule;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;
import java.util.List;

/**
 * Runs the subrules to assemble the PPS variables.
 *
 * <pre>
 * **************************************************************
 *    THE APPROPRIATE SET OF THESE IPF VARIABLES ARE SELECTED  *
 *    DEPENDING ON THE BILL DISCHARGE DATE AND EFFECTIVE DATE  *
 *    OF THAT VARIABLE.                                        *
 *    3/31/2009 - THE STANDARDIZATION FACTOR WAS USED ONLY ONCE*
 *    TO CORRECT WHERE A COMPUTER CODE INCORRECTLY ASSIGNED    *
 *    NON-TEACHING STATUS TO MOST TEACHING FACILITIES.         *
 *    IT HAS BEEN COMMENTED OUT AS IT IS NO LONGER NEEDED.     *
 * **************************************************************
 * </pre>
 *
 * <p>Converted from {@code 0200-MAINLINE-CONTROL} in the COBOL code.
 */
public class AssemblePpsVariables
    extends EvaluatingCalculationRule<
        IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {
  public AssemblePpsVariables(
      List<CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext>>
          calculationRules) {
    // PERFORM 2000-ASSEMBLE-PPS-VARIABLES THRU 2000-EXIT
    super(calculationRules);
  }
}
