package gov.cms.fiss.pricers.ltch.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ltch.api.v1.LtchDrgsTableEntry;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimData;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.api.v2.LtchPaymentData;
import gov.cms.fiss.pricers.ltch.core.LtchPricerContext;
import gov.cms.fiss.pricers.ltch.core.codes.ReturnCode;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class EditDrgCode
    implements CalculationRule<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {

  /**
   * FINDS THE LTCH DRG CODE IN THE TABLE, sets the drg code, length of stay, and relative weight.
   * Sets return code 54 if it cannot be found.
   *
   * <p>Converted from {@code 1700-EDIT-DRG-CODE} in the COBOL code.
   */
  @Override
  public void calculate(LtchPricerContext calculationContext) {
    final LtchClaimData claimData = calculationContext.getClaimData();
    final LtchPaymentData paymentData = calculationContext.getPaymentData();

    final String drgCode = claimData.getDiagnosisRelatedGroup();
    paymentData.setSubmittedDiagnosisRelatedGroup(drgCode);
    final LtchDrgsTableEntry entry = calculationContext.getDataTables().getLtchDrgsEntry(drgCode);

    if (entry == null) {
      calculationContext.applyReturnCode(ReturnCode.DRG_NOT_FOUND_54);
    } else {
      paymentData.setDrgRelativeWeight(entry.getRelativeWeight());
      paymentData.setAverageLengthOfStay(entry.getArithmeticMeanLengthOfStay());
    }

    // changes 1-24-2022
    // The following logic was removed from FISS and added to the Cloud because it could no longer
    // be performed after the LTCH DRG was removed from FISS and added to the Cloud due to
    // PPS-AVG-LOS only being available in the LTCH DRG
    // IF (CLMS-LOS ! = 1)
    //   COMPUTE OUTPUT-SSOT-TRUNCATE = (PPS-VG-LOS / 6) * 5.
    //   IF ((CLMS-COVERED-DAYS <= OUTPUT-SSOT-TRUNCATE) && (COV-DY-CNT <= CLMS-LOS))
    //       MOVE COV-DY-CNT TO CLMS-LOS
    //
    // think of how it could be done in java
    // IF (CLMS-LOS ! = 1)
    //   IF (
    //      (COV-DY-CNT <= (PPS-AVG-LOS / 6) * 5))
    //      &&
    //      (COV-DY-CNT <= CLMS-LOS)
    //      )
    //       MOVE COV-DY-CNT TO CLMS-LOS
    //
    // FISS's CLMS-LOS is B-LOS in COBOL and LengthOfStay in Java
    // FISS's PPS-AVG-LOS is same in COBOL and in Java is averageLengthOfStay
    // FISS's CLMS-COVERED-DAYS is B-COV-DAYS in COBOL and coveredDays in Java
    // FISS's COV-DY-CNT also is also B-COV-DAYS in COBOL and coveredDays in Java
    // replace with java field names
    // IF (LengthOfStay != 1)
    //   IF (
    //      (coveredDays <= (averageLengthOfStay / 6) * 5))
    //      &&
    //      (coveredDays <= lengthOfStay)
    //      )
    //       MOVE coveredDays TO lengthOfStay
    final Integer coveredDays = claimData.getCoveredDays();
    final BigDecimal averageLengthOfStay = paymentData.getAverageLengthOfStay();
    if (claimData.getLengthOfStay() != 1
        && claimData.getCoveredDays() <= claimData.getLengthOfStay()
        && isCoveredDaysLessThanOrEqualToTruncatedAverage(coveredDays, averageLengthOfStay)) {
      paymentData.setLengthOfStay(claimData.getCoveredDays());
      calculationContext.setHoldLengthOfStay(BigDecimal.valueOf(claimData.getLengthOfStay()));
    }
  }

  private boolean isCoveredDaysLessThanOrEqualToTruncatedAverage(
      Integer coveredDays, BigDecimal averageLengthOfStay) {
    return BigDecimalUtils.isLessThanOrEqualTo(
        BigDecimal.valueOf(coveredDays),
        averageLengthOfStay
            .divide(new BigDecimal("6"), new MathContext(10))
            .multiply(new BigDecimal("5"))
            .setScale(1, RoundingMode.HALF_UP));
  }
}
