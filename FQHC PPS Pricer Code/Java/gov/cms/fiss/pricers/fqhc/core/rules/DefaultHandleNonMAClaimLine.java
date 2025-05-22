package gov.cms.fiss.pricers.fqhc.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.EvaluatingCalculationRule;
import gov.cms.fiss.pricers.fqhc.api.v2.IoceServiceLineData;
import gov.cms.fiss.pricers.fqhc.api.v2.ServiceLinePaymentData;
import gov.cms.fiss.pricers.fqhc.core.ServiceLineContext;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class DefaultHandleNonMAClaimLine
    extends EvaluatingCalculationRule<
        IoceServiceLineData, ServiceLinePaymentData, ServiceLineContext> {

  public DefaultHandleNonMAClaimLine(
      List<CalculationRule<IoceServiceLineData, ServiceLinePaymentData, ServiceLineContext>>
          calculationRules) {
    super(calculationRules);
  }

  @Override
  public boolean shouldExecute(ServiceLineContext calculationContext) {
    return !(calculationContext.getFqhcPricerContext().isMaClaim()
        && StringUtils.equals(
            calculationContext.getInput().getRevenueCode(), ServiceLineContext.REV_CODE_MA_CLAIM));
  }
}
