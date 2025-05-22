package gov.cms.fiss.pricers.hospice.core.rules.continuous_home_care;

import gov.cms.fiss.pricers.hospice.api.v2.BillingGroupData;
import gov.cms.fiss.pricers.hospice.core.HospicePricerContext;
import gov.cms.fiss.pricers.hospice.core.rules.CalculatePriorSvcDays;

/**
 * Calculates prior service days for continuous home care claims.
 *
 * <pre>
 * ***============================================================
 * *** CHC - APPLY QIP REDUCTION
 * ***============================================================
 * </pre>
 *
 * <p>Converted from @{code 2020-V200-CHC-0652} in the COBOL code (continuation).
 */
public class CalculateContinuousHomeCarePriorSvcDays extends CalculatePriorSvcDays {
  @Override
  public boolean shouldExecute(HospicePricerContext calculationContext) {
    final BillingGroupData billGroup =
        calculationContext.getBillGroup(HospicePricerContext.CONTINUOUS_HOME_CARE_REVENUE_CODE);

    return billGroup != null && billGroup.getUnits() < 32;
  }

  @Override
  protected void applyDateOfService(HospicePricerContext calculationContext) {
    final BillingGroupData billGroup =
        calculationContext.getBillGroup(HospicePricerContext.CONTINUOUS_HOME_CARE_REVENUE_CODE);
    if (null != billGroup) {
      //     MOVE BILL-LINE-ITEM-DOS2 TO DATE-2-DOS.
      calculationContext.setDateOfService(billGroup.getDateOfService());
    }
  }
}
