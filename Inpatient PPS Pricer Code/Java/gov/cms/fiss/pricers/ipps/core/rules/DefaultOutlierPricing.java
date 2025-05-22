package gov.cms.fiss.pricers.ipps.core.rules;

import gov.cms.fiss.pricers.common.api.ReturnCodeData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.EvaluatingCalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.api.v2.IppsPaymentData;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.ResultCode;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Encapsulates the outlier claim processing flow as a sub-sequence of rules.
 *
 * <p>Corresponds to the {@code PPCAL} process flow from COBOL (continued.
 *
 * @since 2019
 */
public class DefaultOutlierPricing
    extends EvaluatingCalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  public DefaultOutlierPricing(
      List<CalculationRule<IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext>>
          calculationRules) {
    super(calculationRules);
  }

  @Override
  public boolean shouldExecute(IppsPricerContext calculationContext) {
    return ArrayUtils.contains(
        new ResultCode[] {
          ResultCode.RC_00_OK,
          ResultCode.RC_03_TRANSFER_PAID_PERDIEM_DAYS,
          ResultCode.RC_10_POST_ACUTE_XFER,
          ResultCode.RC_12_POST_ACUTE_XFER_WITH_DRGS,
          ResultCode.RC_14_PAID_DRG_WITH_PERDIEM
        },
        calculationContext.getResultCode());
  }

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    // Need to flip outlier reconciliation flag before executing provided rules
    //     IF (PPS-RTC = '00' OR '03' OR '10' OR
    //                   '12' OR '14')
    //        MOVE 'Y' TO OUTLIER-RECON-FLAG
    //        MOVE PPS-DATA TO HLD-PPS-DATA
    //        PERFORM 0200-MAINLINE-CONTROL THRU 0200-EXIT
    //        MOVE HLD-PPS-DATA TO PPS-DATA.
    calculationContext.setOutlierReconciliation(true);
    calculationContext.clearContextState();

    final IppsClaimPricingResponse output = calculationContext.getOutput();

    // PPS-DATA only contains payment data, calculation version, and return code, ignoring
    // additional variables
    final IppsPaymentData holdPayment = output.getPaymentData();
    final ReturnCodeData holdReturnCode = output.getReturnCodeData();

    final ReturnCodeData returnCode = new ReturnCodeData();
    returnCode.setCode("00");
    returnCode.setDescription("No description available.");
    output.setReturnCodeData(returnCode);
    output.setPaymentData(new IppsPaymentData());

    super.calculate(calculationContext);

    // Copy "hold" variables back into output
    output.setPaymentData(holdPayment);
    output.setReturnCodeData(holdReturnCode);

    //     IF OUTLIER-RECON-FLAG = 'Y'
    //        MOVE 'N' TO OUTLIER-RECON-FLAG
    //        GO TO 0200-EXIT.
    calculationContext.setOutlierReconciliation(false);

    if (calculationContext.isOutlierAdjustmentEnabled()) {
      calculationContext.adjustResultCodeForOutliers();
    }
  }
}
