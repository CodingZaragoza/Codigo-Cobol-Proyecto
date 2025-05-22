package gov.cms.fiss.pricers.hospice.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.EvaluatingCalculationRule;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.hospice.api.v2.BillingGroupData;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingRequest;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingResponse;
import gov.cms.fiss.pricers.hospice.core.HospicePricerContext;
import java.util.List;

/**
 * Calculates payment for routine home care.
 *
 * <p>Converted from {@code 2020-V200-RHC-0651} in the COBOL code.
 */
public class CalculateRoutineHomeCarePayment
    extends EvaluatingCalculationRule<
        HospiceClaimPricingRequest, HospiceClaimPricingResponse, HospicePricerContext> {
  public CalculateRoutineHomeCarePayment(
      List<
              CalculationRule<
                  HospiceClaimPricingRequest, HospiceClaimPricingResponse, HospicePricerContext>>
          calculationRules) {
    super(calculationRules);
  }

  @Override
  public boolean shouldExecute(HospicePricerContext calculationContext) {
    final BillingGroupData billGroup =
        calculationContext.getBillGroup(HospicePricerContext.ROUTINE_HOME_CARE_REVENUE_CODE);

    // *==================================================
    // *  >>> IF REVENUE CODE '0651' UNITS > 0         <<<
    // *  >>> [THE DAY IS AN RHC LEVEL OF CARE DAY]    <<<
    // *  >>> 1 UNIT = 1 DAY                           <<<
    // *==================================================
    //     IF BILL-UNITS1 NOT > 0
    //        GO TO 2020-V200-RHC-0651-EXIT
    //     END-IF.
    final boolean hasRhcUnits = billGroup != null && billGroup.getUnits() > 0;

    // *---------------------------------------------------------------
    // * DATE ERROR - CANNOT CONTINUE RHC LEVEL OF CARE DAY PROCESSING
    // *---------------------------------------------------------------
    //     IF BILL-LINE-ITEM-DOS1 < BILL-ADMISSION-DATE
    //        GO TO 2020-V200-RHC-0651-EXIT
    //     END-IF.
    return hasRhcUnits
        && LocalDateUtils.isAfterOrEqual(
            billGroup.getDateOfService(), calculationContext.getClaimData().getAdmissionDate());
  }
}
