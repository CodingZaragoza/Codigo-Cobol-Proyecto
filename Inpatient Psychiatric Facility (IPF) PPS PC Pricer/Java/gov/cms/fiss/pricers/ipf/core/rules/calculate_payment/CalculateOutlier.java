package gov.cms.fiss.pricers.ipf.core.rules.calculate_payment;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.application.rules.EvaluatingCalculationRule;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimData;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class CalculateOutlier
    extends EvaluatingCalculationRule<
        IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {

  public CalculateOutlier(
      List<CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext>>
          calculationRules) {
    // PERFORM 3050-GET-OUTLIER THRU 3050-EXIT
    super(calculationRules);
  }

  @Override
  public boolean shouldExecute(IpfPricerContext calculationContext) {
    // ***************************************************************
    // ***  CHECK FOR OUTLIER TO BE APPLIED
    // ***************************************************************
    //      IF ((BILL-PATIENT-STATUS = '30' AND
    //           BILL-OUTL-OCCUR-IND  = 'Y')
    //                      OR
    //          (BILL-PATIENT-STATUS NOT = '30'))
    //           PERFORM 3050-GET-OUTLIER THRU 3050-EXIT.
    final IpfClaimData claimData = calculationContext.getClaimData();
    return calculationContext.isOutlierOccurrenceIndicator()
        || !StringUtils.equals(
            IpfPricerContext.PATIENT_STATUS_EXP_TO_REMAIN_A_PATIENT_30,
            claimData.getPatientStatus());
  }
}
