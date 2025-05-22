package gov.cms.fiss.pricers.ipf.core.rules.calculate_payment;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipf.api.v2.AdditionalVariableData;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.api.v2.IpfPaymentData;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Calculate the per diem basis without the teaching adjustment.
 *
 * <pre>
 * **************************************************************
 * **  CALCULATE ADJUSTED PER DIEM AMOUNT W/O TEACH-ADJ
 * **************************************************************
 * </pre>
 *
 * <p>Converted from {@code 3000-CALC-PAYMENT} in the COBOL code.
 */
public class CalculatePerDiemBasis
    implements CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {
  @Override
  public void calculate(IpfPricerContext calculationContext) {
    final AdditionalVariableData additionalVariables = calculationContext.getAdditionalVariables();
    final IpfPaymentData paymentData = calculationContext.getPaymentData();
    //      COMPUTE WK-ADJ-PER-DIEM-STEP2 ROUNDED =
    //           (IPF-COMORB-FACTOR *
    //            IPF-AGE-ADJ * IPF-DRG-FACTOR *
    //            WS-IPF-GEO-RURAL-ADJ)
    //                          *
    //                 IPF-WAGE-ADJ-AMT.
    final BigDecimal perDiem =
        paymentData
            .getComorbidityFactor()
            .multiply(paymentData.getAgeAdjustmentPercent())
            .multiply(paymentData.getDiagnosisRelatedGroupFactor())
            .multiply(paymentData.getRuralAdjustmentPercent())
            .multiply(additionalVariables.getWageAdjustedAmount())
            .setScale(2, RoundingMode.HALF_UP);

    //      MOVE WK-ADJ-PER-DIEM-STEP2 TO IPF-ADJUSTED-PER-DIEM-AMT
    //                                    WK-PER-DIEM-AMT.
    // Set in context
    calculationContext.setPerDiemAmount(perDiem);
    // Set in output
    additionalVariables.setAdjustedPerDiemAmount(perDiem);
  }
}
