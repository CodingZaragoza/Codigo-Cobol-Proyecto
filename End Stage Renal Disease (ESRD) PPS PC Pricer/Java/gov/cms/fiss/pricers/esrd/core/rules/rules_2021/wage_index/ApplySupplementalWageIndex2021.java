package gov.cms.fiss.pricers.esrd.core.rules.rules_2021.wage_index;

import gov.cms.fiss.pricers.common.api.OutpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import gov.cms.fiss.pricers.esrd.core.codes.ReturnCode;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;

/**
 * Conditionally applies the supplemental wage index to that referenced by CBSA.
 *
 * <p>Emulates the functions from {@code 0800-FIND-BUNDLED-CBSA-WI} in the {@code ESDRV} COBOL code.
 *
 * @since 2021
 */
public class ApplySupplementalWageIndex2021
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public boolean shouldExecute(EsrdPricerContext calculationContext) {
    return !StringUtils.equals(
        "1", calculationContext.getProviderData().getSpecialPaymentIndicator());
  }

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();
    final OutpatientProviderData providerData = calculationContext.getProviderData();

    // *** SUPPLEMENTAL WAGE INDEX ***
    // * WHEN P-SUPP-WI-IND = '1' THE SUPPLEMENTAL WAGE INDEX FIELD
    // * CONTAINS THE PRIOR YEAR'S ESRD WAGE INDEX.
    // * THIS CALCULATION CAPS THE ESRD WAGE INDEX DECREASE AT 5%.
    // * Change of B-THRU-DATE check from COBOL as per R. Beck
    //     IF B-THRU-DATE > 20201231
    //        IF P-SUPP-WI-IND = '1'
    //           IF P-SUPP-WI > 0
    //             COMPUTE H-ESRD-SUPP-WI-RATIO =
    //              (BUN-CBSA-W-INDEX - P-SUPP-WI) / P-SUPP-WI
    //             IF H-ESRD-SUPP-WI-RATIO < -0.05
    //                COMPUTE BUN-CBSA-W-INDEX ROUNDED =
    //                  P-SUPP-WI * 0.95
    //             END-IF
    //             GO TO 0800-FIND-EXIT
    //           ELSE
    //             MOVE 60 TO PPS-RTC
    //             GO TO 0800-FIND-EXIT
    //           END-IF
    //       END-IF
    //     END-IF.
    if (LocalDateUtils.isAfter(claimData.getServiceThroughDate(), LocalDate.of(2020, 12, 31))
        && StringUtils.equals("1", providerData.getSupplementalWageIndexIndicator())) {
      if (BigDecimalUtils.isGreaterThanZero(providerData.getSupplementalWageIndex())) {
        final BigDecimal supplementalWageIndexRatio =
            calculationContext
                .getBundledWageIndex()
                .subtract(providerData.getSupplementalWageIndex())
                .divide(providerData.getSupplementalWageIndex(), RoundingMode.DOWN);
        if (BigDecimalUtils.isLessThan(supplementalWageIndexRatio, new BigDecimal("-0.05"))) {
          calculationContext.setBundledWageIndex(
              providerData
                  .getSupplementalWageIndex()
                  .multiply(new BigDecimal("0.95"))
                  .setScale(4, RoundingMode.HALF_UP));
        }
      } else {
        calculationContext.applyReturnCode(ReturnCode.CBSA_NOT_FOUND_60);
        calculationContext.setCalculationCompleted();
      }
    }
  }
}
