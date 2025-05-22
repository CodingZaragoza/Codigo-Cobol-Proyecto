package gov.cms.fiss.pricers.fqhc.core.rules.validation;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.fqhc.api.v2.IoceServiceLineData;
import gov.cms.fiss.pricers.fqhc.api.v2.ServiceLinePaymentData;
import gov.cms.fiss.pricers.fqhc.core.ReturnCode;
import gov.cms.fiss.pricers.fqhc.core.ServiceLineContext;
import org.apache.commons.lang3.StringUtils;

public class ValidateIoceFlags
    implements CalculationRule<IoceServiceLineData, ServiceLinePaymentData, ServiceLineContext> {

  @Override
  public void calculate(ServiceLineContext calculationContext) {
    final IoceServiceLineData ioceServiceLine = calculationContext.getInput();
    final ServiceLinePaymentData serviceLinePayment = calculationContext.getOutput();

    // *----------------------------------------------------------------*
    // * CHECK LINE IOCE FLAG VALUES FOR VALIDITY                       *
    // *----------------------------------------------------------------*
    //   PERFORM 3100-CHECK-IOCE-FLAGS
    //      THRU 3100-CHECK-IOCE-FLAGS-EXIT.
    validateFlags(calculationContext);

    //   IF O-LITEM-RETURN-CODE (LN-SUB) >= 10 OR
    //      NOT-PAID OR
    //      TELEHEALTH
    //      INITIALIZE HL-SERVICE-LINE
    //      GO TO 3000-VALIDATE-LINE-EXIT.
    if (Integer.parseInt(serviceLinePayment.getReturnCodeData().getCode()) >= 10
        || StringUtils.equalsAny(
            ioceServiceLine.getPaymentIndicator(),
            ServiceLineContext.PI_TELEHEALTH,
            ServiceLineContext.PI_NOT_PAID)) {
      // Don't validate anymore as this line has already finished processing
      calculationContext.setCalculationCompleted();
    }
  }

  /**
   * Checks the flags in the Service Line input to determine if the service line output needs an
   * invalid return code.
   *
   * <pre>
   * ****************************************************************
   *
   *  CHECK THE VALUES OF THE IOCE FLAGS ASSIGNED TO THE LINE
   *  FOR VALIDITY FOR PROCESSING THE LINE THROUGH THE FQHC PRICER
   *
   * ****************************************************************
   * </pre>
   *
   * <p>Converted from {@code 3100-CHECK-IOCE-FLAGS} in the COBOL code.
   *
   * @param calculationContext The calculation context for this service line.
   */
  protected void validateFlags(ServiceLineContext calculationContext) {
    final IoceServiceLineData lineInput = calculationContext.getInput();
    final ServiceLinePaymentData serviceLinePayment = calculationContext.getOutput();
    // *----------------------------------------------------------------*
    // *   IDENTIFY INVALID LINE ITEM ACTION FLAG VALUES & SET LINE     *
    // *   RETURN CODE TO 15 IF INVALID.                                *
    // *----------------------------------------------------------------*
    // *   "NON-COVERED":  LITEM-ACT-FLAG (LN-SUB) = '5'                *
    // *----------------------------------------------------------------*
    //   IF NON-COVERED
    //      MOVE 15 TO O-LITEM-RETURN-CODE (LN-SUB)
    //      GO TO 3100-CHECK-IOCE-FLAGS-EXIT.
    if (StringUtils.equals(lineInput.getActionFlag(), ServiceLineContext.LI_ACTION_NON_COVERED)) {
      serviceLinePayment.setReturnCodeData(
          ReturnCode.LINE_ITEM_ACTION_FLAG_INVALID_15.toReturnCodeData());
      return;
    }

    // *----------------------------------------------------------------*
    // *   IDENTIFY VALID LINE ITEM DENIAL OR REJECTION FLAG VALUES &   *
    // *   SET LINE RETURN CODE TO 14 IF INVALID.                       *
    // *----------------------------------------------------------------*
    // *   "NOT-DENY-REJECT": I-LITEM-DENY-REJ-FLAG (LN-SUB) = '0'      *
    // *----------------------------------------------------------------*
    //   IF NOT NOT-DENY-REJECT
    //      MOVE 14 TO O-LITEM-RETURN-CODE (LN-SUB)
    //      GO TO 3100-CHECK-IOCE-FLAGS-EXIT.
    if (!calculationContext.isNotDenyOrRejectFlag()) {
      serviceLinePayment.setReturnCodeData(
          ReturnCode.LINE_ITEM_DENIAL_OR_REJ_FLAG_INVALID_14.toReturnCodeData());

      return;
    }

    // *----------------------------------------------------------------*
    // *   IDENTIFY VALID PAYMENT INDICATORS & SET LINE RETURN CODE TO  *
    // *   11 IF INVALID.                                               *
    // *----------------------------------------------------------------*
    // *   "TELEHEALTH": I-PYMT-IND (LN-SUB) = ' 2'                     *
    // *   "PAID-ENCOUNTER": I-PYMT-IND (LN-SUB) = '10'                 *
    // *   "NOT-PAID": I-PYMT-IND (LN-SUB) = '11'                       *
    // *   "NO-ADDTNL-PYMT": I-PYMT-IND (LN-SUB) = '12'                 *
    // *   "PAID-WITH-ADD-ON": I-PYMT-IND (LN-SUB) = '13'               *
    // *   "PAID-GFTF":        I-PYMT-IND (LN-SUB) = '14'               *
    // *----------------------------------------------------------------*
    //   IF NOT ( TELEHEALTH       OR
    //            PAID-ENCOUNTER   OR
    //            NOT-PAID         OR
    //            NO-ADDTNL-PYMT   OR
    //            PAID-WITH-ADD-ON OR
    //            PAID-GFTF )
    //      MOVE 11 TO O-LITEM-RETURN-CODE (LN-SUB)
    //      GO TO 3100-CHECK-IOCE-FLAGS-EXIT.
    // ADDED PAYMENT INDICATOR '15' & '16' TO VALID LIST FOR 2024 AND BEYOND
    if (ServiceLineContext.isInvalidPaymentIndicator(lineInput.getPaymentIndicator())) {
      serviceLinePayment.setReturnCodeData(ReturnCode.LINE_PAY_IND_INVALID_11.toReturnCodeData());

      return;
    }

    // *----------------------------------------------------------------*
    // *   IDENTIFY VALID PACKAGING FLAG VALUES & SET LINE RETURN CODE  *
    // *   TO 13 IF INVALID.                                            *
    // *----------------------------------------------------------------*
    // *   "NOT-PKG": I-PKG-FLAG (LN-SUB) = '0'                         *
    // *   "PKG-ENCOUNTER": I-PKG-FLAG (LN-SUB) = '5'                   *
    // *   "PKG-PREVENTIVE": I-PKG-FLAG (LN-SUB) = '6'                  *
    // *----------------------------------------------------------------*
    //   IF NOT ( NOT-PKG        OR
    //            PKG-ENCOUNTER  OR
    //            PKG-PREVENTIVE    )
    //      MOVE 13 TO O-LITEM-RETURN-CODE (LN-SUB)
    //      GO TO 3100-CHECK-IOCE-FLAGS-EXIT.
    if (ServiceLineContext.isInvalidPackageFlag(lineInput.getPackageFlag())) {
      serviceLinePayment.setReturnCodeData(ReturnCode.LINE_PACK_FLAG_INVALID_13.toReturnCodeData());

      return;
    }

    // 06/12/2020 - CLG - COVID19 Updates
    // *--------------------------------------------------------------------*
    // *   IDENTIFY VALID PAYMENT METHOD FLAG VALUE AND SET LINE RETURN     *
    // *   CODE TO 10 IF INVALID.                                           *
    // *--------------------------------------------------------------------*
    // *   "FQHC-PPS-SERVICE": I-PYMT-METHOD-FLAG (LN-SUB) = '5' AND 'Z'    *
    // *   "FQHC-COINNA-SERVICE": I-PYMT-METHOD-FLAG (LN-SUB) = 'C' AND 'V' *
    // *--------------------------------------------------------------------*
    //   IF NOT (FQHC-PPS-SERVICE OR FQHC-COINNA-SERVICE)
    //      MOVE 10 TO O-LITEM-RETURN-CODE (LN-SUB)
    //      GO TO 3100-CHECK-IOCE-FLAGS-EXIT.
    //
    if (calculationContext.isNotValidPaymentMethodFlag()) {
      serviceLinePayment.setReturnCodeData(
          ReturnCode.LINE_PAY_METHOD_FLAG_INVALID_10.toReturnCodeData());

      return;
    }

    // *----------------------------------------------------------------*
    // *   IDENTIFY VALID COMPOSITE ADJUSTMENT FLAG VALUES & SET LINE   *
    // *   RETURN CODE TO 12 IF INVALID.                                *
    // *----------------------------------------------------------------*
    // *   "OTHER-LINE": I-COMP-ADJ-FLAG (LN-SUB) = '00'                *
    // *   "MEDICAL-LINE": I-COMP-ADJ-FLAG (LN-SUB) = '01'              *
    // *   "MENTAL-LINE": I-COMP-ADJ-FLAG (LN-SUB) = '02'               *
    // *   "MOD59-LINE": I-COMP-ADJ-FLAG (LN-SUB) = '03'                *
    // *----------------------------------------------------------------*
    //   IF NOT ( OTHER-LINE   OR
    //            MEDICAL-LINE OR
    //            MENTAL-LINE  OR
    //            MOD59-LINE      )
    //      MOVE 12 TO O-LITEM-RETURN-CODE (LN-SUB).
    if (ServiceLineContext.isInvalidLineType(lineInput.getCompositeAdjustmentFlag())) {
      serviceLinePayment.setReturnCodeData(
          ReturnCode.LINE_COMP_ADJ_FLAG_INVALID_12.toReturnCodeData());
    }
  }
}
