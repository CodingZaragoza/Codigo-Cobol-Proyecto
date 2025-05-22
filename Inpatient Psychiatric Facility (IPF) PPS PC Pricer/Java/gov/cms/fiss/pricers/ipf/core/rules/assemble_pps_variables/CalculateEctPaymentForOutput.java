package gov.cms.fiss.pricers.ipf.core.rules.assemble_pps_variables;

import gov.cms.fiss.pricers.common.api.annotations.FixedValue;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Calculate the electroconvulsive therapy adjusted payment.
 *
 * <pre>
 * ***************************************************************
 * ***  GET THE ECT ADJUSTED PAYMENT
 * ***************************************************************
 * </pre>
 *
 * <p>Converted from {@code 2000-ASSEMBLE-PPS-VARIABLES} in the COBOL code.
 */
public class CalculateEctPaymentForOutput
    implements CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {

  @Override
  public void calculate(IpfPricerContext calculationContext) {
    final @FixedValue BigDecimal ectRate =
        calculationContext.getAdditionalVariables().getElectroConvulsiveTherapyRateAmount();

    // COMPUTE IPF-ECT-PAYMENT ROUNDED =
    //        (((IPF-ECT-RATE-AMT * IPF-LABOR-SHARE) *
    //               W-CBSA-WAGE-INDEX)
    //                      +
    //         ((IPF-ECT-RATE-AMT * IPF-NLABOR-SHARE) *
    //                  IPF-COLA)).
    final BigDecimal laborShare =
        ectRate
            .multiply(calculationContext.getLaborShare())
            .multiply(calculationContext.getPaymentData().getFinalWageIndex());
    final BigDecimal nonLaborShare =
        ectRate
            .multiply(calculationContext.getNonLaborShare())
            .multiply(calculationContext.getPaymentData().getCostOfLivingAdjustmentPercent());
    @FixedValue
    BigDecimal ectPayment = laborShare.add(nonLaborShare).setScale(2, RoundingMode.HALF_UP);

    // COMPUTE IPF-ECT-PAYMENT ROUNDED =
    //        IPF-ECT-PAYMENT * BILL-ECT-NO-OF-UNITS.
    ectPayment =
        ectPayment
            .multiply(BigDecimal.valueOf(calculationContext.getClaimData().getServiceUnits()))
            .setScale(2, RoundingMode.HALF_UP);

    calculationContext.getAdditionalVariables().setElectroConvulsiveTherapyPayment(ectPayment);
  }
}
