package gov.cms.fiss.pricers.irf.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.api.v2.IrfPaymentData;
import gov.cms.fiss.pricers.irf.core.IrfPricerContext;
import gov.cms.fiss.pricers.irf.core.ResultCode;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Determines the priced CMG and relative weights.
 *
 * <p>Converted from {@code 3000-CALC-PAYMENT} in the COBOL code.
 */
public class DeterminePricedCaseMixGroupCodeAndRelativeWeight
    implements CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  @Override
  public boolean shouldExecute(IrfPricerContext calculationContext) {
    return calculationContext.matchesReturnCode(ResultCode.OK_00);
  }

  @Override
  public void calculate(IrfPricerContext calculationContext) {
    final IrfPaymentData paymentData = calculationContext.getPaymentData();

    // *******************
    // ** RULE: THE PROVIDER TRANSFER PERCENTAGE FOR PATIENTS (WHOSE
    // ** PATIENT STATUS CODE INDICATES THAT THEY WERE TRANSFERRED TO
    // ** ANOTHER FACILITY) WHEN THE BILLED LENGTH OF STAY IS LESS THAN
    // ** THE PPS NORMAL AVERAGE LENGTH OF STAY IS COMPUTED AS THE
    // ** BILLED LENGTH OF STAY PLUS A HALF DAY DIVIDED BY THE PPS
    // ** NORMAL AVERAGE LENGTH OF STAY.
    // ** IN OTHER WORDS, THE PROVIDER GETS AN
    // ** ADDITIONAL HALF DAY ADDED TO THEIR TRANSFER PERCENTAGE
    // ** CALCUATION IF THEY TRANSFER THE PATIENT IN LESS TIME THAN IS
    // ** NORMALLY AVERAGE FOR THE TYPE OF CLAIM.
    // ** RULE: THE PPS SUBMITTED CASE MIX GROUP CODE IS USED AS THE PPS
    // ** PRICED CASE MIX GROUP CODE FOR PATIENTS TRANSFERRED TO ANOTHER
    // ** FACILITY (CODES 02, 03, 61, 62, 63, 64, 82, 83, 89, 90, 91,
    // ** 92) WHEN THE BILLED LENGTH OF STAY IS LESS THAN THE PPS NORMAL
    // ** AVERAGE LENGTH OF STAY.
    // ** TRANSFERRED PATIENTS

    // ****************************************************************
    // *  IF A TRANSFER CASE, CALCULATE THE STANDARD PAYMENT USING    *
    // *  THE PER DIEM IN THE CALCULATION.                            *
    // ****************************************************************

    // IF B-PATIENT-STATUS =
    //         '02' OR '03' OR '61' OR '62' OR '63' OR '64' OR
    //         '82' OR '83' OR '89' OR '90' OR '91' OR '92'
    //   IF H-LOS < PPS-AVG-LOS
    if (calculationContext.isPatientTransferred()
        && paymentData.getLengthOfStay() < paymentData.getAverageLengthOfStay()) {
      // Using a scale of 4 here since the paymentData.transferPercent is a 4-decimal
      // value
      // COMPUTE PPS-TRANSFER-PCT =
      //     ((H-LOS + .5) / PPS-AVG-LOS)
      paymentData.setTransferPercent(
          BigDecimal.valueOf(paymentData.getLengthOfStay())
              .add(IrfPricerContext.HALF_DAY_LENGTH_OF_STAY)
              .divide(
                  BigDecimal.valueOf(paymentData.getAverageLengthOfStay()), 4, RoundingMode.DOWN)
              .setScale(4, RoundingMode.HALF_UP));

      // MOVE PPS-SUBM-CMG-CODE TO PPS-PRICED-CMG-CODE
      paymentData.setPricedCaseMixGroupCode(paymentData.getSubmittedCaseMixGroupCode());

      return;
    }

    // ** RULE: BILLS FOR PATIENTS SHALL USE THE PPS CASE MIX GROUP CODE
    // ** OF A5001, ASSOCIATED WITH THE RATE IN THE TABLE, WHEN A BILL
    // ** SHOWS A STAY OF THREE DAYS OR LESS
    // ** STAY OF 3 DAYS OR LESS

    // IF H-LOS > 3
    //    NEXT SENTENCE
    // ELSE
    if (paymentData.getLengthOfStay() <= IrfPricerContext.THREE_DAY_LENGTH_OF_STAY) {
      // MOVE 'A5001' TO PPS-PRICED-CMG-CODE
      paymentData.setPricedCaseMixGroupCode(IrfPricerContext.CMG_A5001);
      // MOVE A-REL-WGT (DX6) TO PPS-RELATIVE-WGT
      paymentData.setCaseMixGroupRelativeWeight(
          calculationContext
              .getCmgEntry("5001")
              .getARelativeWeight()
              .setScale(4, RoundingMode.HALF_UP));

      return;
    }

    // ** RULE: BILLS FOR PATIENTS WHO DID NOT EXPIRE WHILE IN CARE WILL
    // ** USE THE PPS SUBMITTED CASE MIX GROUP CODE AS THE PPS PRICED
    // ** CASE MIX GROUP CODE.
    // ** PATIENT HAS NOT EXPIRED

    // IF B-PATIENT-STATUS = '20'
    //    NEXT SENTENCE
    // ELSE
    if (!calculationContext.isPatientExpired()) {
      // MOVE PPS-SUBM-CMG-CODE TO PPS-PRICED-CMG-CODE
      paymentData.setPricedCaseMixGroupCode(paymentData.getSubmittedCaseMixGroupCode());
      // ** RULE: PATIENTS WHO EXPIRED DURING CARE, AND WHO HAD AN
      // ** ORTHOPEDIC BILL SUBMITTED, AND THE PATIENT STAY WAS LESS THAN
      // ** 14 DAYS, SHALL USE THE PPS CASE MIX GROUP CODE OF A5101,
      // ** ASSOCIATED WITH THE RATE IN THE TABLE
      // ** PATIENT HAS EXPIRED AND
      // ** THIS IS AN ORTHOPEDIC BILL

      return;
    }

    // IF PPS-CMG-RIC = ('07' OR '08' OR '09')
    if (IrfPricerContext.isOrthopedicCmg(paymentData.getSubmittedCaseMixGroupCode())) {
      // IF H-LOS < 14
      if (paymentData.getLengthOfStay() < IrfPricerContext.FOURTEEN_DAY_LENGTH_OF_STAY) {
        // MOVE 'A5101' TO PPS-PRICED-CMG-CODE
        paymentData.setPricedCaseMixGroupCode(IrfPricerContext.CMG_A5101);
        // MOVE A-REL-WGT (DX6) TO PPS-RELATIVE-WGT
        paymentData.setCaseMixGroupRelativeWeight(
            calculationContext
                .getCmgEntry("5101")
                .getARelativeWeight()
                .setScale(4, RoundingMode.HALF_UP));
        // ** RULE: PATIENTS WHO EXPIRED DURING CARE, AND WHO HAD AN
        // ** ORTHOPEDIC BILL SUBMITTED, AND THE PATIENT STAY WAS 14 DAYS OR
        // ** LONGER, SHALL USE THE PPS CASE MIX GROUP CODE OF A5102,
        // ** ASSOCIATED WITH THE RATE IN THE TABLE.
      } else {
        // ELSE
        //   MOVE 'A5102' TO PPS-PRICED-CMG-CODE
        paymentData.setPricedCaseMixGroupCode(IrfPricerContext.CMG_A5102);
        //   MOVE A-REL-WGT (DX6) TO PPS-RELATIVE-WGT
        paymentData.setCaseMixGroupRelativeWeight(
            calculationContext
                .getCmgEntry("5102")
                .getARelativeWeight()
                .setScale(4, RoundingMode.HALF_UP));
      }
      // ** RULE: PATIENTS WHO EXPIRED DURING CARE, AND WHO'S BILL IS NOT
      // ** AN ORTHOPEDIC BILL, AND THE PATIENT STAY WAS LESS THAN 16
      // ** DAYS, SHALL USE THE PPS CASE MIX GROUP CODE OF A5103,
      // ** ASSOCIATED WITH THE RATE IN THE TABLE
      // ** PATIENT HAS EXPIRED AND
      // ** NOT AN ORTHOPEDIC BILL
    } else if (paymentData.getLengthOfStay() < IrfPricerContext.SIXTEEN_DAY_LENGTH_OF_STAY) {
      // ELSE
      //    IF H-LOS < 16
      //       MOVE 'A5103' TO PPS-PRICED-CMG-CODE
      paymentData.setPricedCaseMixGroupCode(IrfPricerContext.CMG_A5103);
      //       MOVE A-REL-WGT (DX6) TO PPS-RELATIVE-WGT
      paymentData.setCaseMixGroupRelativeWeight(
          calculationContext
              .getCmgEntry("5103")
              .getARelativeWeight()
              .setScale(4, RoundingMode.HALF_UP));
      // ** RULE: PATIENTS WHO EXPIRED DURING CARE, AND WHO'S BILL IS NOT
      // ** AN ORTHOPEDIC BILL, AND THE PATIENT STAY WAS 16 DAYS OR
      // ** GREATER, SHALL USE THE PPS CASE MIX GROUP CODE OF A5104,
      // ** ASSOCIATED WITH THE RATE IN THE TABLE
    } else {
      //    ELSE
      //       MOVE 'A5104' TO PPS-PRICED-CMG-CODE
      paymentData.setPricedCaseMixGroupCode(IrfPricerContext.CMG_A5104);
      //       MOVE A-REL-WGT (DX6) TO PPS-RELATIVE-WGT.
      paymentData.setCaseMixGroupRelativeWeight(
          calculationContext
              .getCmgEntry("5104")
              .getARelativeWeight()
              .setScale(4, RoundingMode.HALF_UP));
    }
  }
}
