package gov.cms.fiss.pricers.hha.core.rules.validate_input;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingRequest;
import gov.cms.fiss.pricers.hha.api.v2.HhaClaimPricingResponse;
import gov.cms.fiss.pricers.hha.core.HhaPricerContext;
import gov.cms.fiss.pricers.hha.core.codes.ReturnCode;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Retrieves the CBSA data for the provider.
 *
 * <pre>
 * *================================================================*
 * * -- GET THE CBSA DATA                                           *
 * * -- DO NOT GET THE CBSA FOR A TOB-RAP WITH A                    *
 * *    HHA-SERV-FROM-DATE GREATER THAN '20201231'                  *
 * *================================================================*
 * </pre>
 *
 * <p>Converted from {@code 0100-PROCESS-RECORDS} in the DRV COBOL code.
 */
public class SetCbsaWageIndex
    implements CalculationRule<HhaClaimPricingRequest, HhaClaimPricingResponse, HhaPricerContext> {
  @Override
  public boolean shouldExecute(HhaPricerContext calculationContext) {
    final LocalDate serviceFromDate = calculationContext.getClaimData().getServiceFromDate();

    // IF HHA-VALID-TOB-RAP AND HHA-SERV-FROM-DATE > '20201231'
    //    NEXT SENTENCE
    return !(calculationContext.isBillTypeRap()
        && LocalDateUtils.isAfter(serviceFromDate, LocalDate.of(2020, 12, 31)));
  }

  @Override
  public void calculate(HhaPricerContext calculationContext) {
    final BigDecimal wageIndex =
        calculationContext
            .getDataTables()
            .getGeographicWageIndex(
                calculationContext.getInput().getProviderData().getCbsaActualGeographicLocation(),
                calculationContext.getClaimData().getServiceThroughDate());
    if (wageIndex == null || BigDecimalUtils.isZero(wageIndex)) {
      calculationContext.completeWithReturnCode(ReturnCode.INVALID_CBSA_CODE_30);
      return;
    }
    calculationContext.setCbsaWageIndex(wageIndex);
  }
}
