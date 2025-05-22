package gov.cms.fiss.pricers.ipf.core.rules.pre_processing;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimData;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;
import gov.cms.fiss.pricers.ipf.core.codes.ReturnCode;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;

/**
 * Set the CBSA data based on the CBSA code and special wage index.
 *
 * <pre>
 *    SUPPLEMENTAL WAGE INDEX EDIT
 * </pre>
 *
 * <p>Converted from {@code 0550-GET-CBSA} in the COBOL code.
 */
public class ValidateSupplementalWageIndex
    implements CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {

  @Override
  public void calculate(IpfPricerContext calculationContext) {
    // *    SUPPLEMENTAL WAGE INDEX EDIT
    //
    //      IF BILL-DISCHARGE-DATE > W-FY2021-BEGIN-DT
    //      OR BILL-DISCHARGE-DATE = W-FY2021-BEGIN-DT
    //         IF P-NEW-SUPP-WI-IND = '1'
    //            IF P-NEW-SUPP-WI  > ZERO
    //                NEXT SENTENCE
    //            ELSE
    //                MOVE 52 TO IPF-RTC
    //                GOBACK.
    //      IF BILL-DISCHARGE-DATE > W-FY2021-BEGIN-DT
    //      OR BILL-DISCHARGE-DATE = W-FY2021-BEGIN-DT
    //         IF P-NEW-SUPP-WI-IND = '1'
    //            IF P-NEW-EFF-DATE < W-FY2021-BEGIN-DT
    //            OR P-NEW-EFF-DATE > W-FY2021-END-DT
    //                MOVE 52 TO IPF-RTC
    //                GOBACK.
    final InpatientProviderData providerData = calculationContext.getProviderData();
    final LocalDate effectiveDate = providerData.getEffectiveDate();
    final IpfClaimData claimData = calculationContext.getClaimData();

    if ((StringUtils.equals(providerData.getSupplementalWageIndexIndicator(), "1")
            || StringUtils.equals(providerData.getSupplementalWageIndexIndicator(), "3")
                && LocalDateUtils.isAfterOrEqual(
                    claimData.getDischargeDate(), IpfPricerContext.FISCAL_YEAR_2025_BEGIN))
        && (BigDecimalUtils.isLessThanOrEqualToZero(providerData.getSupplementalWageIndex())
            || LocalDateUtils.inRange(
                effectiveDate,
                IpfPricerContext.FISCAL_YEAR_2022_BEGIN,
                IpfPricerContext.FISCAL_YEAR_2022_END))) {
      calculationContext.completeWithReturnCode(ReturnCode.STATISTICAL_AREA_NOT_FOUND_52);
    }
  }
}
