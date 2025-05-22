package gov.cms.fiss.pricers.esrd.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.EvaluatingCalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
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
public class ProcessClaim
    extends EvaluatingCalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  public ProcessClaim(
      List<CalculationRule<EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext>>
          calculationRules) {
    super(calculationRules);
  }
}
