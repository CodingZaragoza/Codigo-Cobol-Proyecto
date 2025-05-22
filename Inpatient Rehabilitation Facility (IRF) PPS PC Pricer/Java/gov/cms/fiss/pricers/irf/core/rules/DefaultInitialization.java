package gov.cms.fiss.pricers.irf.core.rules;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.api.v2.IrfPaymentData;
import gov.cms.fiss.pricers.irf.core.IrfPricerContext;
import gov.cms.fiss.pricers.irf.core.ResultCode;
import java.math.RoundingMode;
import org.apache.commons.lang3.StringUtils;

/**
 * Initializes input/output records.
 *
 * <p>Converted from {@code 0100-INITIAL-ROUTINE} in the COBOL code.
 */
public class DefaultInitialization
    implements CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  @Override
  public void calculate(IrfPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();
    final IrfPaymentData paymentData = calculationContext.getPaymentData();

    // MOVE ZEROS TO PPS-RTC.
    calculationContext.applyResultCode(ResultCode.OK_00);

    // IF P-NEW-CBSA-HOSP-QUAL-IND IS EQUAL TO '1'
    //    MOVE 16489  TO PPS-BDGT-NEUT-CONV-AMT
    if (StringUtils.equals(
        IrfPricerContext.PROV_CBSA_HOSPITAL_QUALITY_INDICATOR_1,
        providerData.getHospitalQualityIndicator())) {
      paymentData.setBudgetNeutralityConversionAmount(
          calculationContext.getBudgetNeutralConversionAmount().setScale(2, RoundingMode.HALF_UP));
    } else {
      // ELSE
      //    MOVE 16167  TO PPS-BDGT-NEUT-CONV-AMT
      paymentData.setBudgetNeutralityConversionAmount(
          calculationContext.getBudgetNeutralConversionAmount2().setScale(2, RoundingMode.HALF_UP));
    }
    // END-IF.
  }
}
