package gov.cms.fiss.pricers.ipf.core.rules.validate_billing_info;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;
import gov.cms.fiss.pricers.ipf.core.codes.ReturnCode;
import org.apache.commons.lang3.StringUtils;

/**
 * Validate the DRG code.
 *
 * <pre>
 * *-------------------------------------------------------------*
 * *    FOR FY2020, REMOVED LIST OF INVALID DRG CODES.           *
 * *    HOWEVER, A DRG IS A REQUIRED FIELD AND MUST HAVE A VALUE *
 * *-------------------------------------------------------------*
 * </pre>
 *
 * <p>Converted from {@code 1000-EDIT-THE-BILL-INFO} in the COBOL code.
 */
public class ValidateDiagnosticRelatedGroup
    implements CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {

  @Override
  public void calculate(IpfPricerContext calculationContext) {
    // IF  BILL-DRG = ZEROES OR SPACES
    //     MOVE 54 TO IPF-RTC.
    final String diagnosticRelatedGroup =
        calculationContext.getClaimData().getDiagnosisRelatedGroup();
    if (diagnosticRelatedGroup == null || StringUtils.equals(diagnosticRelatedGroup, "000")) {
      calculationContext.completeWithReturnCode(ReturnCode.INVALID_DIAGNOSIS_RELATED_GROUP_54);
    }
  }
}
