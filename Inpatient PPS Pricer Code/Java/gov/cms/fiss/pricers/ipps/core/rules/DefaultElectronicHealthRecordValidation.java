package gov.cms.fiss.pricers.ipps.core.rules;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.ResultCode;
import org.apache.commons.lang3.StringUtils;

/**
 * Check for the presence of the electronic health record indicator.
 *
 * <p>Converted from the {@code PPDRV} module in the COBOL code.
 *
 * @since 2019
 */
public class DefaultElectronicHealthRecordValidation
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();

    // *****************************************************************
    // *EHR INDICATOR CHECK FOR 2015 FORWARD
    // CHECK-EHR-IND.
    //     IF B-N-DISCHARGE-DATE > 20140930 AND
    //        (P-NEW-EHR-REDUC-INDN NOT = 'Y' AND
    //         P-NEW-EHR-REDUC-INDN NOT = ' ')
    //           MOVE 65 TO PPS-RTC
    //             GOBACK
    //     END-IF.
    if (StringUtils.isNotBlank(providerData.getEhrReductionIndicator())
        && !StringUtils.equals(providerData.getEhrReductionIndicator(), "Y")) {
      calculationContext.applyResultCode(ResultCode.RC_65_PAY_CODE_NOT_ABC);
      calculationContext.setCalculationCompleted();
    }
  }
}
