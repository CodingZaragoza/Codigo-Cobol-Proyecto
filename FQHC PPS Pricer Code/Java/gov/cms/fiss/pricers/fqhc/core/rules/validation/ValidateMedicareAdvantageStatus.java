package gov.cms.fiss.pricers.fqhc.core.rules.validation;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.fqhc.api.v2.IoceServiceLineData;
import gov.cms.fiss.pricers.fqhc.api.v2.ServiceLinePaymentData;
import gov.cms.fiss.pricers.fqhc.core.FqhcPricerContext;
import gov.cms.fiss.pricers.fqhc.core.ReturnCode;
import gov.cms.fiss.pricers.fqhc.core.ServiceLineContext;
import org.apache.commons.lang3.StringUtils;

public class ValidateMedicareAdvantageStatus
    implements CalculationRule<IoceServiceLineData, ServiceLinePaymentData, ServiceLineContext> {

  @Override
  public void calculate(ServiceLineContext calculationContext) {
    final FqhcPricerContext fqhcPricerContext = calculationContext.getFqhcPricerContext();
    final IoceServiceLineData ioceServiceLine = calculationContext.getInput();

    // *----------------------------------------------------------------*
    // *  CHECK MEDICARE ADVANTAGE (MA) STATUS AND VALIDATE             *
    // *----------------------------------------------------------------*
    //   IF MA-CLAIM-REV
    //      IF I-MA-PLAN-AMT > 0
    //         SET MA-CLAIM TO TRUE
    //      ELSE
    //         MOVE 17 TO O-LITEM-RETURN-CODE (LN-SUB)
    //         INITIALIZE HL-SERVICE-LINE
    //         GO TO 3000-VALIDATE-LINE-EXIT
    //      END-IF
    //   END-IF.
    if (StringUtils.equals(
        ioceServiceLine.getRevenueCode(), ServiceLineContext.REV_CODE_MA_CLAIM)) {
      // and has a MA-PLAN-AMOUNT (ie. greater than 0), then is an MA status
      if (BigDecimalUtils.isGreaterThanZero(
          fqhcPricerContext.getClaimData().getMedicareAdvantagePlanAmount())) {
        fqhcPricerContext.setMaClaim();
      } else {
        // Return this line with no other values set as it is not valid
        calculationContext.applyReturnCode(ReturnCode.LINE_MA_PLAN_AMT_EQUALS_ZERO_17);
        // Halts line processing
        calculationContext.setCalculationCompleted();
      }
    }
  }
}
