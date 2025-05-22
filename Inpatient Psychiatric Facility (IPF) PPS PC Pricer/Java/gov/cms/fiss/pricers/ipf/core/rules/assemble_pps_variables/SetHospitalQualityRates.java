package gov.cms.fiss.pricers.ipf.core.rules.assemble_pps_variables;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;
import org.apache.commons.lang3.StringUtils;

/**
 * Initialize values based on hospital quality indicator.
 *
 * <p>Converted from {@code 2000-ASSEMBLE-PPS-VARIABLES} in the COBOL code.
 */
public class SetHospitalQualityRates
    implements CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {

  @Override
  public void calculate(IpfPricerContext calculationContext) {
    // IF P-NEW-CBSA-HOSP-QUAL-IND IS EQUAL TO '1'
    //   MOVE 0798.55  TO IPF-BUDGNUT-RATE-AMT
    //   MOVE 0343.79  TO IPF-ECT-RATE-AMT
    if (StringUtils.equals(
        calculationContext.getProviderData().getHospitalQualityIndicator(), "1")) {
      calculationContext
          .getAdditionalVariables()
          .setBudgetRateAmount(calculationContext.getHighQualityBudgetRate());
      calculationContext
          .getAdditionalVariables()
          .setElectroConvulsiveTherapyRateAmount(calculationContext.getHighQualityEctRate());
    } else {
      // ELSE
      //   MOVE 0782.85  TO IPF-BUDGNUT-RATE-AMT
      //   MOVE 0337.03  TO IPF-ECT-RATE-AMT
      calculationContext
          .getAdditionalVariables()
          .setBudgetRateAmount(calculationContext.getLowQualityBudgetRate());
      calculationContext
          .getAdditionalVariables()
          .setElectroConvulsiveTherapyRateAmount(calculationContext.getLowQualityEctRate());
    }
  }
}
