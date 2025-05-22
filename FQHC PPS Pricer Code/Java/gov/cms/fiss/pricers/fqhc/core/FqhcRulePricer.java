package gov.cms.fiss.pricers.fqhc.core;

import gov.cms.fiss.pricers.common.application.rules.CalculationEvaluator;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.RuleContextExecutor;
import gov.cms.fiss.pricers.fqhc.api.v2.FqhcClaimPricingRequest;
import gov.cms.fiss.pricers.fqhc.api.v2.FqhcClaimPricingResponse;
import gov.cms.fiss.pricers.fqhc.core.rules.DefaultGafAssignment;
import gov.cms.fiss.pricers.fqhc.core.rules.DefaultHandleNonMAClaimLine;
import gov.cms.fiss.pricers.fqhc.core.rules.DefaultHandlePaidLine;
import gov.cms.fiss.pricers.fqhc.core.rules.DefaultLineProcessing;
import gov.cms.fiss.pricers.fqhc.core.rules.DefaultLineValidation;
import gov.cms.fiss.pricers.fqhc.core.rules.DefaultServiceDateValidation;
import gov.cms.fiss.pricers.fqhc.core.rules.processing.CalculateCoinsurancePayment;
import gov.cms.fiss.pricers.fqhc.core.rules.processing.CalculateMdpcpReductionAmount;
import gov.cms.fiss.pricers.fqhc.core.rules.processing.CalculateMedicareAdvantageLinePayment;
import gov.cms.fiss.pricers.fqhc.core.rules.processing.CalculateNonMAClaimLinePayment;
import gov.cms.fiss.pricers.fqhc.core.rules.processing.CalculatePaymentRates;
import gov.cms.fiss.pricers.fqhc.core.rules.processing.HandleNonPaidLine;
import gov.cms.fiss.pricers.fqhc.core.rules.processing.SetNonMAClaimLineReturnCodes;
import gov.cms.fiss.pricers.fqhc.core.rules.processing.UpdateClaimTotals;
import gov.cms.fiss.pricers.fqhc.core.rules.validation.SummarizeDayRates;
import gov.cms.fiss.pricers.fqhc.core.rules.validation.ValidateGftf;
import gov.cms.fiss.pricers.fqhc.core.rules.validation.ValidateIoceFlags;
import gov.cms.fiss.pricers.fqhc.core.rules.validation.ValidateMdpcpReductionPercentage;
import gov.cms.fiss.pricers.fqhc.core.rules.validation.ValidateMedicareAdvantageStatus;
import gov.cms.fiss.pricers.fqhc.core.rules.validation.rates.DetermineDayRates;
import gov.cms.fiss.pricers.fqhc.core.rules.validation.rates.UpdateFlagsAndCharges;
import gov.cms.fiss.pricers.fqhc.core.rules.validation.rates.summary.InitializeDaySummary;
import gov.cms.fiss.pricers.fqhc.core.rules.validation.rates.summary.ProcessCompositeAdjustmentFlag;
import gov.cms.fiss.pricers.fqhc.core.rules.validation.rates.summary.ProcessPaymentAndPackageFlags;
import gov.cms.fiss.pricers.fqhc.core.tables.DataTables;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * <pre>
 * *---------------------------------------------------------------*
 * *                                                               *
 * *   STEP 3 - MOVE CLAIM LEVEL DATA TO OUTPUT RECORD             *
 * *   ------                                                      *
 * *                                                               *
 * *---------------------------------------------------------------*
 * ******************************************************************
 * *                                                                *
 * *                     END OF CLAIM PROCESSING                    *
 * *                                                                *
 * * - MOVE CLAIM LEVEL VARIABLE VALUES FROM THE HOLD AREA TO THE   *
 * *   OUTPUT RECORD.                                               *
 * * - INITIALIZE ALL CLAIM LEVEL HOLD VARIABLES                    *
 * *                                                                *
 * ******************************************************************
 *  5000-END-PRICE-RTN.
 *        MOVE HC-CLAIM-O TO O-CLAIM-LEVEL-OUTPUT.
 *        INITIALIZE HOLD-CLAIM-LEVEL-ITEMS.
 *  5000-END-PRICE-RTN-EXIT.
 *        EXIT.
 * </pre>
 */
public class FqhcRulePricer
    extends RuleContextExecutor<
        FqhcClaimPricingRequest, FqhcClaimPricingResponse, FqhcPricerContext> {
  protected final Map<Integer, DataTables> dataTables;

  public FqhcRulePricer(Map<Integer, DataTables> dataTables) {
    super(new CalculationEvaluator<>(rules()));

    this.dataTables = dataTables;
  }

  private static List<
          CalculationRule<FqhcClaimPricingRequest, FqhcClaimPricingResponse, FqhcPricerContext>>
      rules() {
    return List.of(
        new DefaultServiceDateValidation(),
        new ValidateMdpcpReductionPercentage(),
        new DefaultLineValidation(
            List.of(
                new ValidateGftf(),
                new ValidateMedicareAdvantageStatus(),
                new ValidateIoceFlags(),
                new SummarizeDayRates(
                    List.of(
                        new DetermineDayRates(),
                        new UpdateFlagsAndCharges(
                            List.of(
                                new InitializeDaySummary(),
                                new ProcessCompositeAdjustmentFlag(),
                                new ProcessPaymentAndPackageFlags())))))),
        new DefaultGafAssignment(),
        new DefaultLineProcessing(
            List.of(
                new HandleNonPaidLine(),
                new DefaultHandlePaidLine(
                    List.of(
                        new CalculatePaymentRates(),
                        new CalculateMedicareAdvantageLinePayment(),
                        new DefaultHandleNonMAClaimLine(
                            List.of(
                                new CalculateNonMAClaimLinePayment(),
                                new SetNonMAClaimLineReturnCodes())),
                        new CalculateCoinsurancePayment(),
                        new CalculateMdpcpReductionAmount(),
                        new UpdateClaimTotals())))));
  }

  protected FqhcPricerContext contextFor(FqhcClaimPricingRequest input) {
    final FqhcClaimPricingResponse output = new FqhcClaimPricingResponse();
    return new FqhcPricerContext(input, output, dataTables);
  }
}
