package gov.cms.fiss.pricers.fqhc.core.rules.validation;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.fqhc.api.v2.IoceServiceLineData;
import gov.cms.fiss.pricers.fqhc.api.v2.ServiceLinePaymentData;
import gov.cms.fiss.pricers.fqhc.core.FqhcPricerContext;
import gov.cms.fiss.pricers.fqhc.core.ReturnCode;
import gov.cms.fiss.pricers.fqhc.core.ServiceLineContext;
import org.apache.commons.lang3.StringUtils;

public class ValidateGftf
    implements CalculationRule<IoceServiceLineData, ServiceLinePaymentData, ServiceLineContext> {

  @Override
  public void calculate(ServiceLineContext calculationContext) {
    final IoceServiceLineData ioceServiceLine = calculationContext.getInput();

    // *----------------------------------------------------------------*
    // *  DETERMINE IF CLAIM IS FROM A GRANDFATHERED TRIBAL FQHC (GFTF) *
    // *  - STOP PROCESSING IF FROM DATE IS BEFORE START OF GFTF PPS    *
    // *----------------------------------------------------------------*
    //   IF PAID-GFTF
    //     SET GFTF-CLAIM TO TRUE
    //     IF I-SRVC-FROM-DATE < W-GFTF-PPS-START-DATE
    //       MOVE 02 TO O-CLM-RETURN-CODE
    //       INITIALIZE HL-SERVICE-LINE
    //       GOBACK
    //     END-IF
    //   END-IF.
    if (StringUtils.equals(
        ioceServiceLine.getPaymentIndicator(), ServiceLineContext.PI_PAID_GFTF)) {
      final FqhcPricerContext fqhcPricerContext = calculationContext.getFqhcPricerContext();
      // Set GFTF-CLAIM to true
      // If any line item has a PAID-GFTF indicator, then the entire claim is marked as GFTF
      fqhcPricerContext.setGftfClaim();
      // Stop processing if from date is before start of GFTF
      if (LocalDateUtils.isBefore(
          fqhcPricerContext.getClaimData().getServiceFromDate(),
          fqhcPricerContext.getGftfStartDate())) {
        fqhcPricerContext.applyClaimReturnCode(ReturnCode.CLAIM_INVALID_SERVICE_DATE_02);

        // Halts claim processing
        fqhcPricerContext.setCalculationCompleted();
        // Halts line processing
        calculationContext.setCalculationCompleted();
      }
    }
  }
}
