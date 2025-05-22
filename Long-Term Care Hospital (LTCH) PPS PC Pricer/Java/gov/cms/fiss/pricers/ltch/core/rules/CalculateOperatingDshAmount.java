package gov.cms.fiss.pricers.ltch.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.core.LtchPricerContext;
import gov.cms.fiss.pricers.ltch.core.codes.CbsaProviderType;
import java.math.BigDecimal;
import java.math.RoundingMode;

// *3) DETERMINE THE PROVIDER'S GEOGRAPHIC CLASSIFICATION
// *-----------------------------------------------------
// *4) CALCULATE OPERATING DSH AMOUNT BASED ON GEOGRAPHIC CLASS
// *-----------------------------------------------------------
public class CalculateOperatingDshAmount
    implements CalculationRule<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {
  private static final String RURAL_BEDS = "500";
  private static final String URBAN_BEDS = "100";

  @Override
  public void calculate(LtchPricerContext calculationContext) {
    final CbsaProviderType cbsaProviderType = calculationContext.getCbsaProviderType();
    // *3) DETERMINE THE PROVIDER'S GEOGRAPHIC CLASSIFICATION
    // *-----------------------------------------------------
    switch (determineGeographicalClass(cbsaProviderType, calculationContext)) {
        // GEOGRAPHIC CLASS 2
        // ------------------
      case "TWO":
        calculationContext.setHoldOperatingDshAmount(getOperatingDshAmount(calculationContext));
        break;
        // GEOGRAPHIC CLASS 3
        // ------------------
      case "THREE":
        calculationContext.setHoldOperatingDshAmount(getOperatingDshAmount(calculationContext));
        // IF H-OPER-DSH > .12
        //   MOVE .12 TO H-OPER-DSH
        // END-IF
        if (BigDecimalUtils.isGreaterThan(
            calculationContext.getHoldOperatingDshAmount(), new BigDecimal("0.12"))) {
          calculationContext.setHoldOperatingDshAmount(
              new BigDecimal("0.12").setScale(4, RoundingMode.HALF_UP));
        }
        break;
        // GEOGRAPHIC CLASS 4
        // ------------------
      case "FOUR":
        calculationContext.setHoldOperatingDshAmount(
            BigDecimal.ZERO.setScale(4, RoundingMode.HALF_UP));
        break;
      default:
        break;
    }

    // *** -------------------------------------------------------
    // *** CURRENT OPERATING DSH PAYMENT REDUCTION
    // *** -------------------------------------------------------
    // COMPUTE H-OPER-DSH ROUNDED =
    //             H-OPER-DSH * H-OPER-DSH-REDUCTION-FACTOR.
    calculationContext.setHoldOperatingDshAmount(
        calculationContext
            .getHoldOperatingDshAmount()
            .multiply(calculationContext.getOperatingDshReductionFactor())
            .setScale(4, RoundingMode.HALF_UP));
  }

  private BigDecimal getOperatingDshAmount(LtchPricerContext calculationContext) {
    final BigDecimal operatingDshAmount;
    final BigDecimal operatingMin = new BigDecimal("0.15");
    final BigDecimal operatingMax = new BigDecimal("0.202");
    // IF (H-OPER-DSH-PCT >= .15 AND <= .202)
    if (BigDecimalUtils.isGreaterThanOrEqualTo(
            calculationContext.getHoldOperatingDshPercent(), operatingMin)
        && BigDecimalUtils.isLessThanOrEqualTo(
            calculationContext.getHoldOperatingDshPercent(), operatingMax)) {
      // COMPUTE H-OPER-DSH ROUNDED =
      //               ((H-OPER-DSH-PCT - .15) * .65) + .025
      operatingDshAmount =
          calculationContext
              .getHoldOperatingDshPercent()
              .subtract(operatingMin)
              .multiply(new BigDecimal("0.65"))
              .add(new BigDecimal("0.025"));
    } else if (BigDecimalUtils.isGreaterThan(
        calculationContext.getHoldOperatingDshPercent(), operatingMax)) {
      // COMPUTE H-OPER-DSH ROUNDED =
      //                ((H-OPER-DSH-PCT - .202) * .825) + .0588
      operatingDshAmount =
          calculationContext
              .getHoldOperatingDshPercent()
              .subtract(operatingMax)
              .multiply(new BigDecimal("0.825"))
              .add(new BigDecimal("0.0588"));
    } else {
      operatingDshAmount = BigDecimal.ZERO;
    }
    return operatingDshAmount.setScale(4, RoundingMode.HALF_UP);
  }

  // *3) DETERMINE THE PROVIDER'S GEOGRAPHIC CLASSIFICATION
  // *-----------------------------------------------------
  private String determineGeographicalClass(
      CbsaProviderType cbsaProviderType, LtchPricerContext calculationContext) {
    // MOVE '4' TO H-GEO-CLASS (default)
    String geographicalClass = "FOUR";
    final BigDecimal minimumOperatingDshPercentage = new BigDecimal("0.15");
    final BigDecimal urbanBeds = new BigDecimal(URBAN_BEDS);
    final BigDecimal ruralBeds = new BigDecimal(RURAL_BEDS);
    // H-OPER-DSH-PCT >= .15
    if (BigDecimalUtils.isGreaterThanOrEqualTo(
        calculationContext.getHoldOperatingDshPercent(), minimumOperatingDshPercentage)) {
      // URBAN, < 100 BEDS
      // RURAL, < 500 BEDS
      // IF URBAN-CBSA AND H-BED-SIZE < 100
      // IF RURAL-CBSA AND H-BED-SIZE < 500
      if (cbsaProviderType.equals(CbsaProviderType.URBAN)
              && BigDecimalUtils.isLessThan(calculationContext.getHoldBedSize(), urbanBeds)
          || cbsaProviderType.equals(CbsaProviderType.RURAL)
              && BigDecimalUtils.isLessThan(calculationContext.getHoldBedSize(), ruralBeds)) {
        geographicalClass = "THREE";
      }
      // URBAN, >= 100 BEDS
      // RURAL, >= 500 BEDS
      // IF URBAN-CBSA AND H-BED-SIZE >= 100
      // IF RURAL-CBSA AND H-BED-SIZE < 500
      else if (cbsaProviderType.equals(CbsaProviderType.URBAN)
              && BigDecimalUtils.isGreaterThanOrEqualTo(
                  calculationContext.getHoldBedSize(), urbanBeds)
          || cbsaProviderType.equals(CbsaProviderType.RURAL)
              && BigDecimalUtils.isGreaterThanOrEqualTo(
                  calculationContext.getHoldBedSize(), ruralBeds)) {
        geographicalClass = "TWO";
      }
    }
    return geographicalClass;
  }
}
