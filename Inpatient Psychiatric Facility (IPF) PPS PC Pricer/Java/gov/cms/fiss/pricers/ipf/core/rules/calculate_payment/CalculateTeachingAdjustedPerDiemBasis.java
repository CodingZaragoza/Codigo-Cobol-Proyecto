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
 * Calculate per diem basis with the teaching adjustment.
 *
 * <pre>
 * **************************************************************
 * **  CALCULATE ADJUSTED PER DIEM AMOUNT WITH TEACHING-ADJ
 * **************************************************************
 * </pre>
 *
 * <p>Converted from {@code 3000-CALC-PAYMENT} in the COBOL code.
 */
public class CalculateTeachingAdjustedPerDiemBasis
    implements CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {
  @Override
  public void calculate(IpfPricerContext calculationContext) {
    final AdditionalVariableData additionalVariables = calculationContext.getAdditionalVariables();
    final IpfPaymentData paymentData = calculationContext.getPaymentData();
    //
    //      COMPUTE WK-ADJ-PER-DIEM-STEP1 ROUNDED =
    //           (IPF-COMORB-FACTOR *
    //            IPF-AGE-ADJ * IPF-DRG-FACTOR *
    //            IPF-TEACH-ADJ * WS-IPF-GEO-RURAL-ADJ)
    //                          *
    //                 IPF-WAGE-ADJ-AMT.
    final BigDecimal perDiem =
        paymentData
            .getComorbidityFactor()
            .multiply(paymentData.getAgeAdjustmentPercent())
            .multiply(paymentData.getDiagnosisRelatedGroupFactor())
            .multiply(paymentData.getTeachingAdjustmentPercent())
            .multiply(paymentData.getRuralAdjustmentPercent())
            .multiply(additionalVariables.getWageAdjustedAmount())
            .setScale(2, RoundingMode.HALF_UP);

    // ***************************************************************
    // ***  CALCULATE THE ADJUSTED PER DIEM AMOUNT
    // ***************************************************************
    //
    //      COMPUTE  WK-PER-DIEM-AMT ROUNDED =
    //              WK-ADJ-PER-DIEM-STEP1 - WK-ADJ-PER-DIEM-STEP2.
    //
    //      MOVE WK-ADJ-PER-DIEM-STEP1 TO IPF-ADJUSTED-PER-DIEM-AMT.
    // Set in context
    calculationContext.setPerDiemAmount(
        perDiem.subtract(additionalVariables.getAdjustedPerDiemAmount()));
    // Set in output
    additionalVariables.setAdjustedPerDiemAmount(perDiem);
  }
}
