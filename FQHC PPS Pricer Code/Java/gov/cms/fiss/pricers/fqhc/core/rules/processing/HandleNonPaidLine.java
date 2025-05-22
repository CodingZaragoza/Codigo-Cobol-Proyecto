package gov.cms.fiss.pricers.fqhc.core.rules.processing;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.fqhc.api.v2.IoceServiceLineData;
import gov.cms.fiss.pricers.fqhc.api.v2.ServiceLinePaymentData;
import gov.cms.fiss.pricers.fqhc.core.ReturnCode;
import gov.cms.fiss.pricers.fqhc.core.ServiceLineContext;
import org.apache.commons.lang3.StringUtils;

public class HandleNonPaidLine
    implements CalculationRule<IoceServiceLineData, ServiceLinePaymentData, ServiceLineContext> {

  @Override
  public void calculate(ServiceLineContext calculationContext) {
    final IoceServiceLineData ioceServiceLine = calculationContext.getInput();
    final ServiceLinePaymentData serviceLinePayment = calculationContext.getOutput();

    // Non-paid info line
    // *----------------------------------------------------------------*
    // * NON-PAID INFORMATIONAL LINE: SET LINE RETURN CODE              *
    // *   (FLU/PPV VACCINE ADMINISTRATION/TELEHEALTH)                  *
    // *----------------------------------------------------------------*
    //   IF NOT-PAID OR TELEHEALTH
    //      MOVE 08 TO HL-LITEM-RETURN-CODE.
    if (StringUtils.equalsAny(
        ioceServiceLine.getPaymentIndicator(),
        ServiceLineContext.PI_NOT_PAID,
        ServiceLineContext.PI_TELEHEALTH)) {
      serviceLinePayment.setReturnCodeData(ReturnCode.LINE_INFO_ONLY_08.toReturnCodeData());
    }

    // Non-paid package line
    // *----------------------------------------------------------------*
    // * NON-PAID PACKAGED LINE: SET LINE RETURN CODE                   *
    // *   (QUALIFYING VISIT/PREVENTIVE SERVICE/ANCILLARY SERVICE/OTHER)*
    // *----------------------------------------------------------------*
    //   IF NO-ADDTNL-PYMT AND (PKG-ENCOUNTER OR PKG-PREVENTIVE)
    //      MOVE 09 TO HL-LITEM-RETURN-CODE.
    if (StringUtils.equals(
            ioceServiceLine.getPaymentIndicator(), ServiceLineContext.PI_NO_ADDITIONAL_PAYMENT)
        && StringUtils.equalsAny(
            ioceServiceLine.getPackageFlag(),
            ServiceLineContext.PACKAGE_ENCOUNTER,
            ServiceLineContext.PACKAGE_PREVENTIVE)) {
      serviceLinePayment.setReturnCodeData(
          ReturnCode.LINE_PAY_APP_TO_ANOTHER_LINE_09.toReturnCodeData());
    }
  }
}
