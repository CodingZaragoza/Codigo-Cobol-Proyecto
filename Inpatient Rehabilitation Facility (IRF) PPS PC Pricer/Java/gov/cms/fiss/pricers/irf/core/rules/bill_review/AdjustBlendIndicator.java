package gov.cms.fiss.pricers.irf.core.rules.bill_review;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.core.IrfPricerContext;
import gov.cms.fiss.pricers.irf.core.ResultCode;
import java.time.LocalDate;

/**
 * Adjust PPS blend indicator.
 *
 * <pre>
 * ** RULE: BILLS WITH A DISCHARGE DATE FOR PATIENTS WHO HAVE
 * ** BEEN IN A FACILITY LONGER THAN 14 MONTHS OR PROVIDERS WITH A
 * ** NEW FISCAL YEAR BEGIN DATE FROM
 * ** OCTOBER 1, 2002 TO DECEMBER 31, 2002 SHALL
 * ** HAVE THEIR BLEND INDICATOR SET SO THAT THE FEDERAL GOVERNMENT
 * ** PAYS 100% OF THE BILL (BLEND INDICATOR = 4)
 * ** THE 14 MONTHS IS THE CALCULATION
 * ** WHICH WORKS OUT TO 14 MONTHS (YOU ARE ADDING 1 YEAR AND 2 MONTHS (14 MONTHS)).
 * </pre>
 *
 * Converted from {@code 1000-EDIT-THE-BILL-INFO} in the COBOL code.
 */
public class AdjustBlendIndicator
    implements CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  @Override
  public boolean shouldExecute(IrfPricerContext calculationContext) {
    return calculationContext.matchesReturnCode(ResultCode.OK_00);
  }

  @Override
  public void calculate(IrfPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();

    LocalDate fiscalYearBeginDate = providerData.getFiscalYearBeginDate();
    calculationContext.setPpsBlendIndicator(providerData.getFederalPpsBlend());

    // COMPUTE H-FY-BEGIN-DATE = H-FY-BEGIN-DATE + 10200
    fiscalYearBeginDate = fiscalYearBeginDate.plusYears(1).plusMonths(2);

    // IF (H-DISCHARGE-DATE > H-FY-BEGIN-DATE)
    if (LocalDateUtils.isAfter(calculationContext.getClaimDischargeDate(), fiscalYearBeginDate)) {
      // MOVE '4' TO P-NEW-FED-PPS-BLEND-IND.
      calculationContext.setPpsBlendIndicator(IrfPricerContext.PROV_BLEND_INDICATOR_4);
    }
  }
}
