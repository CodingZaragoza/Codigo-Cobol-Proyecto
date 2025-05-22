package gov.cms.fiss.pricers.esrd.core.rules.validation;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import gov.cms.fiss.pricers.esrd.core.codes.ReturnCode;

/**
 * Performs patient height validation.
 *
 * <p>Converted from {@code 1000-VALIDATE-BILL-ELEMENTS} in the COBOL code.
 *
 * @since 2020
 */
public class ValidatePatientHeight
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public boolean shouldExecute(EsrdPricerContext calculationContext) {
    //    IF PPS-RTC = 00  THEN
    //    END-IF.
    return calculationContext.matchesReturnCode(ReturnCode.CALCULATION_STARTED_00);
  }

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();

    //        IF B-COND-CODE NOT = '84' THEN
    //           IF (B-PATIENT-HGT = 0)  OR  (B-PATIENT-HGT NOT NUMERIC)
    //              MOVE 56                     TO PPS-RTC

    // 2025 REPLACED
    //    if !StringUtils.equals(
    //           EsrdPricerContext.CONDITION_CODE_AKI_MONTHLY_84,
    //           calculationContext.getClaimData().getConditionCode()

    if (!calculationContext.isAki84() && BigDecimalUtils.isZero(claimData.getPatientHeight())) {
      calculationContext.applyReturnCode(ReturnCode.INVALID_PATIENT_HEIGHT_56);
    }
  }
}
