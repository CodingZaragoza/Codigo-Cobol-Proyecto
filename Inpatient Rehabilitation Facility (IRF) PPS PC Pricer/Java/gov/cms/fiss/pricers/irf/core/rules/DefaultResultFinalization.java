package gov.cms.fiss.pricers.irf.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingRequest;
import gov.cms.fiss.pricers.irf.api.v2.IrfClaimPricingResponse;
import gov.cms.fiss.pricers.irf.core.IrfPricerContext;
import gov.cms.fiss.pricers.irf.core.ResultCode;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Updates output record with finalized values.
 *
 * <p>Converted from {@code 9000-MOVE-RESULTS} in the COBOL code.
 */
public class DefaultResultFinalization
    implements CalculationRule<IrfClaimPricingRequest, IrfClaimPricingResponse, IrfPricerContext> {

  @Override
  public void calculate(IrfPricerContext calculationContext) {
    final IrfClaimPricingResponse output = calculationContext.getOutput();

    // IF PPS-RTC < 50
    //  MOVE H-LOS                   TO  PPS-LOS
    //  MOVE H-OUTLIER-THRESHOLD     TO  PPS-OUTLIER-THRESHOLD
    //  MOVE H-CHG-OUTLIER-THRESHOLD TO PPS-CHG-OUTLIER-THRESHOLD
    //  MOVE W-NEW-CBSA              TO  PPS-CBSA
    //  MOVE 'V15.0'                 TO  PPS-CALC-VERS-CD
    // ELSE
    //   INITIALIZE PPS-DATA
    //   INITIALIZE PPS-OTHER-DATA
    //   MOVE 'V15.0'                TO  PPS-CALC-VERS-CD.

    if (calculationContext.getResultCode().isError()) {
      final BigDecimal outlierThreshold =
          calculationContext.getPaymentData().getChargeOutlierThresholdAmount();
      calculationContext.zeroPaymentRecord();

      // IF PPS-RTC = 67
      //    MOVE H-CHG-OUTLIER-THRESHOLD TO PPS-CHG-OUTLIER-THRESHOLD.
      if (calculationContext.getResultCode() == ResultCode.OUTLIER_LOS_GT_COVERED_DAYS_67) {
        calculationContext
            .getPaymentData()
            .setChargeOutlierThresholdAmount(outlierThreshold.setScale(2, RoundingMode.HALF_UP));
      }
    }

    output.setCalculationVersion(calculationContext.getCalculationVersion());
  }
}
