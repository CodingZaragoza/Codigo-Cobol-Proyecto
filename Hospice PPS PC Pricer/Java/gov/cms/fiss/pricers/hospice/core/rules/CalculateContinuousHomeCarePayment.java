package gov.cms.fiss.pricers.hospice.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.EvaluatingCalculationRule;
import gov.cms.fiss.pricers.hospice.api.v2.BillingGroupData;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingRequest;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingResponse;
import gov.cms.fiss.pricers.hospice.core.HospicePricerContext;
import java.util.List;

/**
 * Calculates payment for continuous home care.
 *
 * <pre>
 * ****************************************************************
 * **** V20.0   CHC - CONTINUOUS HOME CARE = REVENUE CODE = 0652
 * ****************************************************************
 * </pre>
 *
 * <p>Converted from @{code 2020-V200-CHC-0652} in the COBOL code.
 */
public class CalculateContinuousHomeCarePayment
    extends EvaluatingCalculationRule<
        HospiceClaimPricingRequest, HospiceClaimPricingResponse, HospicePricerContext> {
  public CalculateContinuousHomeCarePayment(
      List<
              CalculationRule<
                  HospiceClaimPricingRequest, HospiceClaimPricingResponse, HospicePricerContext>>
          calculationRules) {
    super(calculationRules);
  }

  @Override
  public boolean shouldExecute(HospicePricerContext calculationContext) {
    final BillingGroupData billGroup =
        calculationContext.getBillGroup(HospicePricerContext.CONTINUOUS_HOME_CARE_REVENUE_CODE);

    return billGroup != null
        // *==================================================
        // *  >>> IF REVENUE CODE '0652' UNITS <= 0         <<<
        // *  >>> [THE DAY IS A CHC LEVEL OF CARE DAY]     <<<
        // *  >>> 1 UNIT = 15 MIN.                         <<<
        // *==================================================
        && billGroup.getUnits() > 0;
  }
}
