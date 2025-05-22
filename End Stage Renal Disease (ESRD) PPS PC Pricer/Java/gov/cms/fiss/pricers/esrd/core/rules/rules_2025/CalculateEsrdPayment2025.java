package gov.cms.fiss.pricers.esrd.core.rules.rules_2025;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.EvaluatingCalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import gov.cms.fiss.pricers.esrd.core.codes.ReturnCode;
import java.util.List;

/**
 * Processes the ESRD claim.
 *
 * <pre>
 * *****************************************************************
 *  THERE ARE VARIOUS WAYS TO COMPUTE A FINAL DOLLAR AMOUNT.  THE  *
 *  METHOD USED IN THIS PROGRAM IS TO USE ROUNDED INTERMEDIATE     *
 *  VARIABLES.  THIS WAS DONE TO SIMPLIFY THE CALCULATIONS SO THAT *
 *  WHEN SOMETHING GOES AWRY, ONE IS NOT LEFT WONDERING WHERE IN   *
 *  A VAST COMPUTE STATEMENT, THINGS HAVE GONE AWRY.  THE METHOD   *
 *  UTILIZED HERE HAS BEEN APPROVED BY THE DIVISION OF             *
 *  INSTITUTIONAL CLAIMS PROCESSING (DICP).                        *
 *                                                                 *
 *     PROCESSING:                                                 *
 *         A. WILL PROCESS CLAIMS BASED ON AGE/HEIGHT/WEIGHT       *
 *         B. INITIALIZE ESCAL HOLD VARIABLES.                     *
 *         C. EDIT THE DATA PASSED FROM THE CLAIM BEFORE           *
 *            ATTEMPTING TO CALCULATE PPS. IF THIS CLAIM           *
 *            CANNOT BE PROCESSED, SET A RETURN CODE AND           *
 *            GOBACK.                                              *
 *         D. ASSEMBLE PRICING COMPONENTS.                         *
 *         E. CALCULATE THE PRICE.                                 *
 * *****************************************************************
 * </pre>
 *
 * <p>Converted from {@code 0000-START-TO-FINISH} in the COBOL code.
 *
 * @since 2020
 */
public class CalculateEsrdPayment2025
    extends EvaluatingCalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  public CalculateEsrdPayment2025(
      List<CalculationRule<EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext>>
          calculationRules) {
    super(calculationRules);
  }

  @Override
  public boolean shouldExecute(EsrdPricerContext calculationContext) {
    final EsrdClaimData claimData = calculationContext.getClaimData();

    //        IF NOT B-COND-CODE = '84' THEN
    //   return
    // !EsrdPricerContext.CONDITION_CODE_AKI_MONTHLY_84.equals(claimData.getConditionCode())
    return !calculationContext.isAki84()
        && calculationContext.matchesReturnCode(ReturnCode.CALCULATION_STARTED_00);
  }
}
