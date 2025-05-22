package gov.cms.fiss.pricers.esrd.core;

import gov.cms.fiss.pricers.common.application.ClaimProcessor;
import gov.cms.fiss.pricers.common.application.rules.CalculationEvaluator;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.RuleContextExecutor;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdPaymentData;
import gov.cms.fiss.pricers.esrd.core.tables.DataTables;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public abstract class EsrdRulePricer
    extends RuleContextExecutor<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext>
    implements ClaimProcessor<EsrdClaimPricingRequest, EsrdClaimPricingResponse> {

  protected final DataTables dataTables;

  protected EsrdRulePricer(
      DataTables dataTables,
      List<CalculationRule<EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext>>
          rules) {
    super(new CalculationEvaluator<>(rules));
    this.dataTables = dataTables;
  }

  protected abstract EsrdPricerContext contextFor(EsrdClaimPricingRequest input);

  protected EsrdClaimPricingResponse createDefaultResponse() {
    final EsrdClaimPricingResponse response = new EsrdClaimPricingResponse();
    response.setPaymentData(new EsrdPaymentData());
    response.getPaymentData().setTotalPayment(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));

    return response;
  }
}
