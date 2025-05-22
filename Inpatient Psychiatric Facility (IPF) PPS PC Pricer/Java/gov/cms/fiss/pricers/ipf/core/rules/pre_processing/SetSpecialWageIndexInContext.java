package gov.cms.fiss.pricers.ipf.core.rules.pre_processing;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;
import gov.cms.fiss.pricers.ipf.core.codes.ReturnCode;
import gov.cms.fiss.pricers.ipf.core.tables.CbsaWageIndexEntry;
import org.apache.commons.lang3.StringUtils;

/**
 * Set output CBSA code and Special Wage Index, if applicable.
 *
 * <p>Converted from {@code 0550-GET-CBSA} in the COBOL code.
 */
public class SetSpecialWageIndexInContext
    implements CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {

  @Override
  public void calculate(IpfPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();
    // *    SPECIAL WAGE INDEX EDIT
    //      IF (P-NEW-CBSA-WI-SPECIAL AND
    //          P-NEW-CBSA-SPEC-WI-N NOT NUMERIC)
    //          MOVE 52 TO IPF-RTC
    //          GOBACK.
    // There's no reason to keep checking if P-NEW-CBSA-WI-SPECIAL. Just nest the other
    // conditions inside the first. Also, P-NEW-CBSA-SPEC-WI-N cannot be non-numeric in Java.
    if (StringUtils.equalsAny(providerData.getSpecialPaymentIndicator(), "1", "2")) {
      //      IF (P-NEW-CBSA-WI-SPECIAL AND
      //          P-NEW-CBSA-SPEC-WI-N = ZEROES)
      //          MOVE 52 TO IPF-RTC
      //          GOBACK.
      if (BigDecimalUtils.isZero(providerData.getSpecialWageIndex())) {
        calculationContext.completeWithReturnCode(ReturnCode.STATISTICAL_AREA_NOT_FOUND_52);
        return;
      }

      //      IF P-NEW-CBSA-WI-SPECIAL
      //         MOVE 'SPEC*'  TO W-CBSA-X
      //         MOVE P-NEW-EFF-DATE TO W-CBSA-EFF-DATE
      //         MOVE P-NEW-CBSA-SPEC-WI TO W-CBSA-INDEX
      //         GO TO 0550-EXIT.
      final CbsaWageIndexEntry specialWageIndex =
          CbsaWageIndexEntry.builder()
              .cbsa("SPEC*")
              .effectiveDate(providerData.getEffectiveDate())
              .geographicWageIndex(providerData.getSpecialWageIndex())
              .build();
      calculationContext.setCbsaWageIndexEntry(specialWageIndex);
    }
  }
}
