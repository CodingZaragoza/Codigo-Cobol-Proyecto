package gov.cms.fiss.pricers.ipps.core.rules.rules2021.assemble_variables;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimData;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.tables.ClaimCodeType;
import gov.cms.fiss.pricers.ipps.core.tables.DataTables;
import org.apache.commons.lang3.StringUtils;

/**
 * Determine the claim's eligibility for an adjustment per the qualification as a CAR-T and clinical
 * trial case based on DRG code and diagnosis/condition code.
 *
 * <p>Converted from {@code 2800-CART-CLIN-TRIAL-REDUC} in the COBOL code.
 *
 * @since 2021
 */
public class DetermineNoCostProductAdjustment
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final DataTables dataTables = calculationContext.getDataTables();
    final IppsClaimData claimData = calculationContext.getClaimData();

    //    CAR-T AND CLINICAL TRIAL CASE REDUCTION FACTOR TO DRG RATE
    //        + NO COST PRODUCT/PAYMENT ADJUSTMENT FACTOR OF 0.17 FOR FY2021
    //        + MS-DRG 018, DIAGNOSIS CODE Z00.6 IN 2-25, AND CONDITION CODE OF "ZB" NOT "ZC"
    //
    //    MOVE 1 TO IDX-CLIN.
    //    MOVE 1 TO IDX-CART.
    //    MOVE 1.0 TO NO-COST-PRODUCT.
    //
    //    PERFORM 10200-CLIN-FLAG THRU 10200-EXIT
    //     VARYING IDX-CLIN FROM 1 BY 1 UNTIL IDX-CLIN > 25.
    //
    //    PERFORM 10300-CART-FLAG THRU 10300-EXIT
    //     VARYING IDX-CART FROM 1 BY 1 UNTIL IDX-CART > 5.
    //
    //    IF B-DRG = 018
    //       IF (DIAG-CLIN-FLAG = 'Y' AND
    //          COND-CART-NONCP-FLAG NOT = 'Y') OR
    //          COND-CART-NCP-FLAG = 'Y'
    //       MOVE 0.17 TO NO-COST-PRODUCT.

    // A factor of 0.17 is effective as of 10/01/2020
    if (StringUtils.equals(claimData.getDiagnosisRelatedGroup(), "018")
            && dataTables.codesMatch(
                "NOCOSTPROD1", ClaimCodeType.DIAG, claimData.getDiagnosisCodes())
            && !dataTables.codesMatch(
                "NOCOSTPROD2", ClaimCodeType.COND, claimData.getConditionCodes())
        || StringUtils.equals(claimData.getDiagnosisRelatedGroup(), "018")
            && dataTables.codesMatch(
                "NOCOSTPROD1", ClaimCodeType.COND, claimData.getConditionCodes())) {
      calculationContext.setNoCostProductAdjustmentFactor(
          calculationContext.getNoCostProductAdjustmentFactorValue());
    }
  }
}
