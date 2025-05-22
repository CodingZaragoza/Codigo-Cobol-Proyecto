package gov.cms.fiss.pricers.ipps.core.rules.cost_factor_determination;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.ResultCode;

/**
 * Determines the diagnostic-related group for the claim.
 *
 * <p>Converted from {@code 2000-ASSEMBLE-PPS-VARIABLES} in the COBOL code (continued).
 *
 * @since 2019
 */
public class DetermineDiagnosticRelatedGroup
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    calculationContext.initializeDrgEntry();

    //     IF  B-DISCHARGE-DATE NOT < WK-DRGX-EFF-DATE
    //     SET DRG-IDX TO 1
    //     SEARCH DRG-TAB VARYING DRG-IDX
    //         AT END
    //           MOVE ' NO DRG CODE    FOUND' TO HLDDRG-DESC
    //           MOVE 'I' TO  HLDDRG-VALID
    //           MOVE 0 TO HLDDRG-WEIGHT
    //           MOVE 54 TO PPS-RTC
    //           GO TO 2600-EXIT
    //       WHEN WK-DRG-DRGX(DRG-IDX) = B-DRG
    //         MOVE DRG-DATA-TAB(DRG-IDX) TO HLDDRG-DATA.
    if (null == calculationContext.getDrgsTableEntry()) {
      calculationContext.applyResultCode(ResultCode.RC_54_INVALID_DRG);
    }
  }
}
