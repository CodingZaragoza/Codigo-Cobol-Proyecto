package gov.cms.fiss.pricers.fqhc.core.rules.validation.rates;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.EvaluatingCalculationRule;
import gov.cms.fiss.pricers.fqhc.api.v2.IoceServiceLineData;
import gov.cms.fiss.pricers.fqhc.api.v2.ServiceLinePaymentData;
import gov.cms.fiss.pricers.fqhc.core.ServiceLineContext;
import java.util.List;

/**
 * Allows for the update of an entry in the Day Summary map. Will create an entry if one does not
 * already exist in the mapping for the service date provided.
 *
 * <pre>
 * ****************************************************************
 * DETERMINE WHETHER A NEW DAY SUMMARY TABLE RECORD SHOULD BE
 * ADDED OR IF AN EXISTING RECORD MUST BE UPDATED
 *
 * ****************************************************************
 * </pre>
 *
 * <p>Converted from {@code 3510-SEARCH-DAY-SUM-TBL} in the COBOL code.
 */
public class UpdateFlagsAndCharges
    extends EvaluatingCalculationRule<
        IoceServiceLineData, ServiceLinePaymentData, ServiceLineContext> {

  public UpdateFlagsAndCharges(
      List<CalculationRule<IoceServiceLineData, ServiceLinePaymentData, ServiceLineContext>>
          calculationRules) {
    super(calculationRules);
  }
}
