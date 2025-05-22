package gov.cms.fiss.pricers.irf.core.rules.bill_review;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimData;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.api.v2.IrfPaymentData;
import gov.cms.fiss.pricers.irf.core.IrfPricerContext;
import gov.cms.fiss.pricers.irf.core.ResultCode;
import java.math.RoundingMode;

/**
 * Initializes national values.
 *
 * <p>Converted from {@code 1000-EDIT-THE-BILL-INFO} in the COBOL code.
 */
public class InitializeNationalValues
    implements CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  @Override
  public boolean shouldExecute(IrfPricerContext calculationContext) {
    return calculationContext.matchesReturnCode(ResultCode.OK_00);
  }

  @Override
  public void calculate(IrfPricerContext calculationContext) {
    final IrfClaimData claimData = calculationContext.getClaimData();
    final IrfPaymentData paymentData = calculationContext.getPaymentData();

    // MOVE B-CMG-CODE TO PPS-SUBM-CMG-CODE.
    paymentData.setSubmittedCaseMixGroupCode(claimData.getCaseMixGroup());

    // MOVE .72700 TO PPS-NAT-LABOR-PCT.
    paymentData.setNationalLaborPercent(
        calculationContext.getNationalLaborPercentage().setScale(5, RoundingMode.HALF_UP));

    // MOVE .27300 TO PPS-NAT-NONLABOR-PCT.
    paymentData.setNationalNonLaborPercent(
        calculationContext.getNationalNonLaborPercentage().setScale(5, RoundingMode.HALF_UP));

    // MOVE  9300  TO PPS-NAT-THRESHOLD-ADJ.
    paymentData.setNationalThresholdAdjustmentAmount(
        calculationContext.getNationalThresholdAdjustment().setScale(2, RoundingMode.HALF_UP));
  }
}
