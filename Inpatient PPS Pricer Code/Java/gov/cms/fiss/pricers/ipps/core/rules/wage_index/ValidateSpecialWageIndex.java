package gov.cms.fiss.pricers.ipps.core.rules.wage_index;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.CbsaReference;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.ResultCode;

/**
 * Ensures the provided special wage index information is valid and is used in the calculation.
 *
 * <p>Converted from {@code 0550-GET-CBSA} in the COBOL code (continued).
 *
 * @since 2019
 */
public class ValidateSpecialWageIndex
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {
  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();

    // **----------------------------------------------------------------
    // ** IF THE PSF INDICATES THE SPECIAL WAGE INDEX SHOULD BE USED,
    // ** VALIDATE THE SPECIAL WAGE INDEX VALUE AND EFFECTIVE DATE. IF
    // ** VALID, USE THE SPECIAL WAGE INDEX AND SKIP THE NON-PR SPECIFIC
    // ** CBSA WAGE INDEX SEARCH.
    // **----------------------------------------------------------------
    //     IF (P-NEW-CBSA-WI-SPECIAL AND
    //         P-NEW-CBSA-SPEC-WI-N NOT NUMERIC)
    //        MOVE ALL '0' TO PPS-ADDITIONAL-VARIABLES
    //        MOVE 52 TO PPS-RTC
    //        GOBACK.
    //     IF (P-NEW-CBSA-WI-SPECIAL AND
    //         P-NEW-CBSA-SPEC-WI-N = ZEROES)
    //        MOVE ALL '0' TO PPS-ADDITIONAL-VARIABLES
    //        MOVE 52 TO PPS-RTC
    //        GOBACK.
    if (calculationContext.isCbsaSpecialPaymentIndicatorSpecial()
        && BigDecimalUtils.isZero(providerData.getSpecialWageIndex())) {
      calculationContext.applyResultCode(ResultCode.RC_52_INVALID_WAGE_INDEX);

      return;
    }

    //     IF (P-NEW-CBSA-WI-SPECIAL AND
    //        (P-NEW-EFF-DATE < W-FY-BEGIN-DATE OR
    //         P-NEW-EFF-DATE > W-FY-END-DATE))
    //        MOVE ALL '0' TO PPS-ADDITIONAL-VARIABLES
    //        MOVE 52 TO PPS-RTC
    //        GOBACK.
    if (calculationContext.isCbsaSpecialPaymentIndicatorSpecial()
        && !LocalDateUtils.inRange(
            calculationContext.getEffectiveDate(),
            calculationContext.fiscalYearStart(),
            calculationContext.fiscalYearEnd())) {
      calculationContext.applyResultCode(ResultCode.RC_52_INVALID_WAGE_INDEX);

      return;
    }

    final CbsaReference cbsaReference = calculationContext.getCbsaReference();

    //     IF P-NEW-CBSA-WI-SPECIAL
    //        MOVE 'SPEC*'            TO W-NEW-CBSA-X
    //        MOVE P-NEW-EFF-DATE     TO W-NEW-CBSA-EFF-DATE
    //        MOVE P-NEW-CBSA-SPEC-WI TO W-NEW-CBSA-WI
    //        GO TO 0550-BYPASS.
    if (calculationContext.isCbsaSpecialPaymentIndicatorSpecial()) {
      cbsaReference.setCbsa(IppsPricerContext.SPECIAL_WAGE_INDEX_CBSA);
      cbsaReference.setEffectiveDate(calculationContext.getEffectiveDate());
      cbsaReference.setWageIndex(providerData.getSpecialWageIndex());
    }
  }
}
