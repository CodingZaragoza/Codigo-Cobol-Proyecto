package gov.cms.fiss.pricers.esrd.core.rules.bundled_factors;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;

/**
 * Calculates the patient age.
 *
 * <p>Converted from {@code 2000-CALCULATE-BUNDLED-FACTORS} (continued) in the COBOL code.
 *
 * @since 2020
 */
public class ComputePatientAge
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();

    // From Policy: When a beneficiary reaches a birthday that results in a different age category,
    // the age change is effective from the first day of the birthday month, regardless of the date
    // the birthday occurs in that month.
    //
    //    COMPUTE H-PATIENT-AGE = B-THRU-CCYY - B-DOB-CCYY
    //    IF B-DOB-MM > B-THRU-MM  THEN
    //       COMPUTE H-PATIENT-AGE = H-PATIENT-AGE - 1
    //    END-IF
    calculationContext.setPatientAge(
        claimData.getServiceThroughDate().getYear() - claimData.getPatientDateOfBirth().getYear());
    if (claimData.getPatientDateOfBirth().getMonthValue()
        > claimData.getServiceThroughDate().getMonthValue()) {
      calculationContext.setPatientAge(calculationContext.getPatientAge() - 1);
    }

    //    IF H-PATIENT-AGE < 18  THEN
    //       MOVE "Y"                    TO PEDIATRIC-TRACK
    //    END-IF.
    if (calculationContext.getPatientAge() < 18) {
      calculationContext.setPediatricClaim(true);
    }
  }
}
