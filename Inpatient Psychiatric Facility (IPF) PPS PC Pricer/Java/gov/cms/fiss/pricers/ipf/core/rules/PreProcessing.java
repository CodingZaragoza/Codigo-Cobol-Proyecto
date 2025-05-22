package gov.cms.fiss.pricers.ipf.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.EvaluatingCalculationRule;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;
import java.util.List;

/**
 * Runs the subrules used to do the pre-processing in the IPF drive file.
 *
 * <p>Converted from {@code IPDRV210} in the COBOL code.
 */
public class PreProcessing
    extends EvaluatingCalculationRule<
        IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {
  public PreProcessing(
      List<CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext>>
          calculationRules) {
    super(calculationRules);
  }
}
