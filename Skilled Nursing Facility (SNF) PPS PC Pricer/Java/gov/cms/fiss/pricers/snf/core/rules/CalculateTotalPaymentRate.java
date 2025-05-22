package gov.cms.fiss.pricers.snf.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingRequest;
import gov.cms.fiss.pricers.snf.api.v2.SnfClaimPricingResponse;
import gov.cms.fiss.pricers.snf.core.SnfPricerContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculateTotalPaymentRate
    implements CalculationRule<SnfClaimPricingRequest, SnfClaimPricingResponse, SnfPricerContext> {

  /**
   * Calculates the total payment rate.
   *
   * <pre>
   * **  THIS PARAGRAPH WILL CALCULATE THE TOTAL UNADJUSTED RATE   **
   * **  FOR THE NUMBER OF PDPM UNITS ON THE CLAIM.  IT SUMS THE   **
   * **  APPROPRIATE PT AND OT RATES AND MULTIPLIES IT BY THE      **
   * **  TOTAL NUMBER OF UTILIZATION DAYS FROM THE 6000 PARAGRAPH  **
   * **  TO GET THE PT/OT PORTION.  SIMILARLY, THE NTA RATE IS     **
   * **  MULTIPLIED BY THE NTA UTILIZATION DAYS FROM THE 6000      **
   * **  PARAGRAPH TO GET THE NTA PORTION.  THE APPROPRIATE NURSE  **
   * **  SLP AND NCM RATES ARE SUMMED AND MULTIPLIED BY THE PDPM   **
   * **  UNITS FROM THE CLAIM TO GET THE NURSE, SLP AND NCM        **
   * **  PORTION.  ALL 3 PORTIONS ARE ADDED TOGETHER TO GET THE    **
   * **  TOTAL CASE MIX PER DIEM.  THE WAGE INDEX, LABOR AND       **
   * **  NON-LABOR ADJUSTMENTS ARE APPLIED TO THIS AMOUNT TO GIVE  **
   * **  TOTAL ADJUSTED AMOUNT.                                    **
   * </pre>
   *
   * <p>Converted from {@code 700-CALC-UNADJUSTED-RATE} in the COBOL code.
   */
  @Override
  public void calculate(SnfPricerContext context) {

    // ****--------***-----------**
    // ****--------*** PT-OT FEE **
    // ****--------***-----------**
    // ****--------*
    // ****--------*STEP # 1
    // ****--------*
    //
    //      COMPUTE WS-PT-OT-FEE ROUNDED =
    //              HIPPS-PT-RATE-COMP + HIPPS-OT-RATE-COMP.
    final BigDecimal physicalAndOccupationalTherapyFee =
        context
            .getPhysicalTherapyRate()
            .add(context.getOccupationalTherapyRate())
            .setScale(2, RoundingMode.HALF_UP);

    // ****--------***---------------**
    // ****--------*** PT-OT PORTION **
    // ****--------***---------------**
    // ****--------*
    // ****--------*STEP # 2
    // ****--------*
    //
    //      COMPUTE WS-PT-OT-PORTION ROUNDED =
    //              WS-PT-OT-FEE * WS-PT-OT-UTIL.
    final BigDecimal physicalAndOccupationalTherapyPortion =
        physicalAndOccupationalTherapyFee
            .multiply(context.getPhysicalAndOccupationalTherapyUtilization())
            .setScale(2, RoundingMode.HALF_UP);

    // ****--------***---------------**
    // ****--------*** NTA PORTION    **
    // ****--------***---------------**
    // ****--------*
    // ****--------*
    // ****--------*STEP # 3
    // ****--------*
    //
    //      COMPUTE WS-NTA-PORTION ROUNDED =
    //              HIPPS-NTA-RATE-COMP * WS-NTA-UTIL.
    final BigDecimal nonTherapyAncillaryPortion =
        context
            .getNonTherapyAncillaryRate()
            .multiply(context.getNonTherapyAncillaryUtilization())
            .setScale(2, RoundingMode.HALF_UP);

    // ****        ***----------------------------**
    // ****--------*** NURS SLP NCM NTA PORTION   **
    // ****--------***----------------------------**
    // ****--------*
    // ****--------***----------------*
    // ****--------***    STEP # 4    *
    // ****--------***----------------*
    //
    //      COMPUTE WS-NURS-SLP-NCM-PORTION ROUNDED =
    //              (HIPPS-NURSE-RATE-COMP +
    //               HIPPS-SLP-RATE-COMP +
    //               HIPPS-NCM-RATE-COMP) * CURRENT-DAYS.
    final BigDecimal nursingSlpNcmPortion =
        context
            .getNursingRate()
            .add(context.getSpeechLanguagePathologyRate())
            .add(context.getNonCaseMixRate())
            .multiply(BigDecimal.valueOf(context.getCurrentDays()))
            .setScale(2, RoundingMode.HALF_UP);

    // ****--------***------------------**
    // ****--------*** CASEMIX PER DIEM **
    // ****--------***------------------**
    // ****--------*STEP # 5
    // ****--------*
    //
    //      COMPUTE TOT-PDPM-CASEMIX-PERDIEM =
    //              (WS-PT-OT-PORTION +
    //               WS-NTA-PORTION +
    //               WS-NURS-SLP-NCM-PORTION).
    final BigDecimal totalCaseMixPerDiem =
        physicalAndOccupationalTherapyPortion
            .add(nonTherapyAncillaryPortion)
            .add(nursingSlpNcmPortion);

    // ****--------***----------------*
    // ****--------*** LABOR PORTION  *
    // ****--------***----------------*
    // ****--------*
    // ****--------*STEP # 6
    //
    //      COMPUTE LABOR-PORTION ROUNDED =
    //              (TOT-PDPM-CASEMIX-PERDIEM * PERCENT-2020-LABOR).
    final BigDecimal laborPortion =
        totalCaseMixPerDiem.multiply(context.getLaborRate()).setScale(2, RoundingMode.HALF_UP);

    // ****--------***-----------------*
    // ****--------*** LABOR ADJUSTED  *
    // ****--------***-----------------*
    // ****--------*STEP # 7
    // ****--------*
    //
    //      COMPUTE LABOR-ADJUSTED ROUNDED =
    //              (LABOR-PORTION * AREA-WAGE-INDEX).
    final BigDecimal adjustedLabor =
        laborPortion.multiply(context.getWageIndex()).setScale(2, RoundingMode.HALF_UP);

    // ****--------***--------------------*
    // ****--------*** NON LABOR PORTION  *
    // ****--------***--------------------*
    // ****--------*STEP # 8
    //
    //      COMPUTE NON-LABOR-PORTION ROUNDED =
    //              (TOT-PDPM-CASEMIX-PERDIEM * PERCENT-2020-NLABOR).
    final BigDecimal nonLaborPortion =
        totalCaseMixPerDiem.multiply(context.getNonLaborRate()).setScale(2, RoundingMode.HALF_UP);

    // ****--------***-----------------*
    // ****--------*** LABOR ADJ RATE  *
    // ****--------***-----------------*
    // ****--------*STEP # 9
    //
    //      COMPUTE TOTAL-LABOR-ADJ-RATE ROUNDED =
    //              (LABOR-ADJUSTED + NON-LABOR-PORTION).
    final BigDecimal totalLaborAdjustedRate = adjustedLabor.add(nonLaborPortion);

    // ****--------***-------------------*
    // ****--------*** TOT CALC PAYMENT  *
    // ****--------***-------------------*
    // ****--------*STEP # 10
    //
    //      COMPUTE TOTAL-CALC-PAYMENT-RATE ROUNDED =
    //              TOTAL-LABOR-ADJ-RATE.
    context.setTotalCalculatedPaymentRate(totalLaborAdjustedRate.setScale(2, RoundingMode.HALF_UP));
  }
}
