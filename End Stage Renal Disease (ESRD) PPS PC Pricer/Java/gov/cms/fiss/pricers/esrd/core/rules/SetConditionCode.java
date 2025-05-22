package gov.cms.fiss.pricers.esrd.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import gov.cms.fiss.pricers.esrd.core.codes.ReturnCode;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Applies the calculation results to the base response.
 *
 * <p>Converted from {@code 9100-MOVE-RESULTS} in the COBOL code.
 *
 * @since 2020
 */
public class SetConditionCode
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public boolean shouldExecute(EsrdPricerContext calculationContext) {
    return calculationContext.matchesReturnCode(ReturnCode.CALCULATION_STARTED_00);
  }

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final EsrdClaimData billingRecord = calculationContext.getClaimData();
    final EsrdClaimData claimData = calculationContext.getClaimData();

    if (billingRecord.getConditionCodes() == null) {
      calculationContext.setEsrdFacility(true);
      return;
    }

    // Claims prior to CY 2025 shouldn't have more than one Condition Code
    // NULL Check for no condition codes results in claim set to "ESRD at a Facility"
    if (LocalDateUtils.isBeforeOrEqual(
        claimData.getServiceThroughDate(), LocalDate.of(2024, 12, 31))) {
      if (billingRecord.getConditionCodes().size() == 1) {
        assignConditionCodeBooleansForOne(calculationContext);
      } else calculationContext.applyReturnCode(ReturnCode.INVALID_CONDITION_CODE_58);
    }

    // Claims during or after CY 2025 shouldn't have more than two Condition Codes
    // NULL Check for no condition codes results in claim set to "ESRD at a Facility"
    if (LocalDateUtils.isAfterOrEqual(
        claimData.getServiceThroughDate(), LocalDate.of(2025, 1, 1))) {
      if (billingRecord.getConditionCodes().size() == 1) {
        assignConditionCodeBooleansForOne(calculationContext);
      } else if (billingRecord.getConditionCodes().size() == 2) {
        validateConditionCodeBooleansForTwo(calculationContext);
      }
    }
  }

  private void assignConditionCodeBooleansForOne(EsrdPricerContext calculationContext) {
    final EsrdClaimData billingRecord = calculationContext.getClaimData();

    if (billingRecord.getConditionCodes().contains(null)) {
      calculationContext.setEsrdFacility(true);
      return;
    }

    if (calculationContext.hasConditionCode(
        EsrdPricerContext.CONDITION_CODE_SELF_CARE_TRAINING_73)) {
      calculationContext.setEsrdTraining73(true);
    } else if (calculationContext.hasConditionCode(
        EsrdPricerContext.CONDITION_CODE_HOME_SERVICES_74)) {
      calculationContext.setEsrdHome74(true);
    } else if (calculationContext.hasConditionCode(
        EsrdPricerContext.CONDITION_CODE_IN_FACILITY_BACK_UP_76)) {
      calculationContext.setEsrdBackup76(true);
    } else if (calculationContext.hasConditionCode(
        EsrdPricerContext.CONDITION_CODE_AKI_MONTHLY_84)) {
      calculationContext.setAkiFacility84(true);
      calculationContext.setAki84(true);
    } else if (calculationContext.hasConditionCode(
        EsrdPricerContext.CONDITION_CODE_SELF_CARE_RETRAINING_87)) {
      calculationContext.setEsrdRetraining87(true);
    } else calculationContext.applyReturnCode(ReturnCode.INVALID_CONDITION_CODE_58);
  }

  private void validateConditionCodeBooleansForTwo(EsrdPricerContext calculationContext) {
    final EsrdClaimData billingRecord = calculationContext.getClaimData();

    if (billingRecord.getConditionCodes().get(0) == null
        && billingRecord.getConditionCodes().get(1) == null) {
      return;
    }

    if (billingRecord.getConditionCodes().contains(null)) {

      List<String> codes = billingRecord.getConditionCodes();
      if (codes.size() == 2 && codes.contains(null)) {
        // Keep the non-null value
        String validCode = codes.stream()
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(null);

        if (validCode != null) {
          billingRecord.setConditionCodes(Collections.singletonList(validCode));
          assignConditionCodeBooleansForOne(calculationContext);
        }
      }
    } else assignConditionCodeBooleansForTwo(calculationContext);
  }

  private void assignConditionCodeBooleansForTwo(EsrdPricerContext calculationContext) {

    if (calculationContext.hasConditionCode(EsrdPricerContext.CONDITION_CODE_AKI_MONTHLY_84)) {
      if (calculationContext.hasConditionCode(
          EsrdPricerContext.CONDITION_CODE_SELF_CARE_TRAINING_73)) {
        calculationContext.setAkiTraining8473(true);
        calculationContext.setAki84(true);
      } else if (calculationContext.hasConditionCode(
          EsrdPricerContext.CONDITION_CODE_HOME_SERVICES_74)) {
        calculationContext.setAkiHome8474(true);
        calculationContext.setAki84(true);
      } else if (calculationContext.hasConditionCode(
          EsrdPricerContext.CONDITION_CODE_SELF_CARE_RETRAINING_87)) {
        calculationContext.setAkiRetraining8487(true);
        calculationContext.setAki84(true);
      } else calculationContext.applyReturnCode(ReturnCode.INVALID_CONDITION_CODE_58);
    } else calculationContext.applyReturnCode(ReturnCode.INVALID_CONDITION_CODE_58);
  }
}
