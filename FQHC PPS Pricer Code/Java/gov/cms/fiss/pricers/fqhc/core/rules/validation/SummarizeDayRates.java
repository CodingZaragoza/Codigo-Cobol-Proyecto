package gov.cms.fiss.pricers.fqhc.core.rules.validation;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.EvaluatingCalculationRule;
import gov.cms.fiss.pricers.fqhc.api.v2.IoceServiceLineData;
import gov.cms.fiss.pricers.fqhc.api.v2.ServiceLinePaymentData;
import gov.cms.fiss.pricers.fqhc.core.ServiceLineContext;
import java.util.List;

public class SummarizeDayRates
    extends EvaluatingCalculationRule<
        IoceServiceLineData, ServiceLinePaymentData, ServiceLineContext> {

  public SummarizeDayRates(
      List<CalculationRule<IoceServiceLineData, ServiceLinePaymentData, ServiceLineContext>>
          calculationRules) {
    super(calculationRules);
  }
}
