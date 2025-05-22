package gov.cms.fiss.pricers.irf.core.rules;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.api.v2.IrfPaymentData;
import gov.cms.fiss.pricers.irf.core.IrfPricerContext;
import gov.cms.fiss.pricers.irf.core.ResultCode;
import gov.cms.fiss.pricers.irf.core.tables.CbsaWageIndexEntry;
import java.math.RoundingMode;
import org.apache.commons.lang3.StringUtils;

/**
 * Validates input fields.
 *
 * <p>Converted from {@code 2000-ASSEMBLE-PPS-VARIABLES} in the COBOL code.
 */
public class DefaultVariableExtraction
    implements CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  @Override
  public boolean shouldExecute(IrfPricerContext calculationContext) {
    return calculationContext.matchesReturnCode(ResultCode.OK_00);
  }

  @Override
  public void calculate(IrfPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();
    final IrfPaymentData paymentData = calculationContext.getPaymentData();
    paymentData.setFacilitySpecificRatePreBlend(
        providerData.getPpsFacilitySpecificRate().setScale(2, RoundingMode.HALF_UP));

    // IF P-NEW-FED-PPS-BLEND-IND = '3'
    //    IF PPS-FAC-SPEC-RT-PREBLEND = 0
    if (StringUtils.equals(
            calculationContext.getPpsBlendIndicator(), IrfPricerContext.PROV_BLEND_INDICATOR_3)
        && BigDecimalUtils.isZero(paymentData.getFacilitySpecificRatePreBlend())) {
      // MOVE 57 TO PPS-RTC
      calculationContext.applyResultCode(ResultCode.PROV_SPEC_RT_0_FOR_BLEND_57);
      return;
    }

    final CbsaWageIndexEntry billingCbsa = calculationContext.getCbsaWageIndexEntry();
    // IF W-NEW-WAGE-INDEX-C NUMERIC
    //         AND W-NEW-WAGE-INDEX-C > 0
    //    MOVE W-NEW-WAGE-INDEX-C TO PPS-WAGE-INDEX
    // ELSE
    if (BigDecimalUtils.isLessThanOrEqualToZero(billingCbsa.getGeographicWageIndex())) {
      // MOVE 52 TO PPS-RTC
      calculationContext.applyResultCode(ResultCode.INVALID_WAGE_INDEX_52);
    }
  }
}
