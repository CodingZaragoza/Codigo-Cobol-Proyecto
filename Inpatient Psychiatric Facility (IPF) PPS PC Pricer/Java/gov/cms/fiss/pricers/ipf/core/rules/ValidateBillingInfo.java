package gov.cms.fiss.pricers.ipf.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.EvaluatingCalculationRule;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;
import java.util.List;

/**
 * Validate the billing information.
 *
 * <pre>
 * **************************************************************
 *    BILL DATA EDITS IF ANY FAIL SET IPF-RTC                  *
 *    AND DO NOT ATTEMPT TO PRICE.                             *
 * **************************************************************
 * </pre>
 *
 * <p>Converted from {@code 1000-EDIT-THE-BILL-INFO} in the COBOL code.
 */
public class ValidateBillingInfo
    extends EvaluatingCalculationRule<
        IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {
  public ValidateBillingInfo(
      List<CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext>>
          rules) {
    // PERFORM 1000-EDIT-THE-BILL-INFO
    super(rules);
  }
}
