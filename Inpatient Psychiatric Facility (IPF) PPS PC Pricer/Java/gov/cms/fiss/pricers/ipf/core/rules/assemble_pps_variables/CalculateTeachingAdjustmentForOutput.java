package gov.cms.fiss.pricers.ipf.core.rules.assemble_pps_variables;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Initialize teaching adjustment value.
 *
 * <pre>
 * ***************************************************************
 * ***  GET THE TEACHING ADJUSTMENT
 * ***************************************************************
 * </pre>
 *
 * <p>Converted from {@code 2000-ASSEMBLE-PPS-VARIABLES} in the COBOL code.
 */
public class CalculateTeachingAdjustmentForOutput
    implements CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {

  @Override
  public void calculate(IpfPricerContext calculationContext) {
    // IF P-NEW-INTERN-RATIO NUMERIC (P-NEW-INTERN-RATIO cannot be non-numeric, ignore else
    //   COMPUTE IPF-TEACH-ADJ ROUNDED =
    //         ((1 + P-NEW-INTERN-RATIO) ** 0.5150)
    // ELSE
    //   MOVE 1.00 TO IPF-TEACH-ADJ.
    final BigDecimal teachingAdjustment =
        BigDecimalUtils.pow(
                calculationContext.getProviderData().getInternsToBedsRatio().add(BigDecimal.ONE),
                calculationContext.getInternRatioExponent(),
                3)
            .setScale(2, RoundingMode.HALF_UP);
    calculationContext.getPaymentData().setTeachingAdjustmentPercent(teachingAdjustment);
  }
}
