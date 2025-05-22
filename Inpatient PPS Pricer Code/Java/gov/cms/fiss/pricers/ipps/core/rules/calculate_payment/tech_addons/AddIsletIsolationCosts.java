package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.tech_addons;

import gov.cms.fiss.pricers.common.api.annotations.FixedValue;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimData;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.tables.ClaimCodeType;
import gov.cms.fiss.pricers.ipps.core.tables.DataTables;
import java.math.BigDecimal;

/**
 * Determines the islet isolation add-on payment amount.
 *
 * <p>Converted from {@code 4000-CALC-TECH-ADDON} in the COBOL code (continued).
 *
 * @since 2019
 */
public class AddIsletIsolationCosts
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  private static final String TECHNOLOGY_NAME = "ISLET";

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final DataTables dataTables = calculationContext.getDataTables();

    // Islet is a special case:
    // - requires both a procedure code and a diagnosis code
    // - the number of procedure codes is significant (not just presence of any one code)
    final IppsClaimData claimData = calculationContext.getClaimData();
    final int isletCount =
        dataTables.countMatchingCodes(
            TECHNOLOGY_NAME, ClaimCodeType.PROC, claimData.getProcedureCodes());

    //     IF DIAG-ISLET-FLAG = 'Y' AND PROC-ISLET-FLAG = 'Y'
    //       PERFORM 4100-ISLET-ISOLATION-ADD-ON THRU 4100-EXIT
    //     ELSE
    //       MOVE ZEROES TO H-NEW-TECH-ADDON-ISLET.
    if (dataTables.codesMatch(
            TECHNOLOGY_NAME,
            ClaimCodeType.DIAG,
            calculationContext.getClaimData().getDiagnosisCodes())
        && isletCount != 0) {
      calculationContext.setIsletIsolationPaymentAddOn(isletIsolationAddOn(isletCount));
    }
  }

  /**
   * Converted from {@code 4100-ISLET-ISOLATION-ADD-ON} in the COBOL code.
   *
   * @param isletCount the count of islet diagnoses
   */
  protected @FixedValue BigDecimal isletIsolationAddOn(int isletCount) {
    //     MOVE 0 TO H-NEW-TECH-ADDON-ISLET.
    //     IF  H-TECH-ADDON-ISLET-CNTR = 1
    //     MOVE 18848.00 TO H-NEW-TECH-ADDON-ISLET
    //           GO TO 4100-EXIT.
    //     IF  H-TECH-ADDON-ISLET-CNTR > 1
    //     MOVE 37696.00 TO H-NEW-TECH-ADDON-ISLET
    //           GO TO 4100-EXIT.
    if (isletCount == 1) {
      return new BigDecimal("18848.0");
    } else {
      return new BigDecimal("37696.0");
    }
  }
}
