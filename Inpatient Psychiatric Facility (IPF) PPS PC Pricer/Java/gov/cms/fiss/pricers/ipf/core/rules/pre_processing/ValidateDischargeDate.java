package gov.cms.fiss.pricers.ipf.core.rules.pre_processing;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;
import gov.cms.fiss.pricers.ipf.core.codes.ReturnCode;
import java.time.LocalDate;

/**
 * Validate the patient's discharge date.
 *
 * <p>Converted from {@code ipdrv210} in the COBOL code.
 */
public class ValidateDischargeDate
    implements CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {
  @Override
  public void calculate(IpfPricerContext calculationContext) {
    //      IF BILL-DISCHARGE-DATE < P-NEW-EFF-DATE
    //         MOVE 55 TO IPF-RTC
    //         GOBACK.
    //
    //      IF BILL-DISCHARGE-DATE > 20060630 AND
    //         P-NEW-EFF-DATE < 20060701
    //         MOVE 55 TO IPF-RTC
    //         GOBACK.
    final LocalDate dischargeDate = calculationContext.getClaimData().getDischargeDate();
    final LocalDate effectiveDate = calculationContext.getProviderData().getEffectiveDate();
    if (LocalDateUtils.isBefore(dischargeDate, effectiveDate)
        || LocalDateUtils.isBefore(effectiveDate, LocalDate.of(2006, 7, 1))) {
      calculationContext.completeWithReturnCode(ReturnCode.INVALID_DISCHARGE_DATE_55);
    }
  }
}
