package gov.cms.fiss.pricers.ipf.core.rules.rules2022.calculate_payment.calculate_outlier;

import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimData;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;
import gov.cms.fiss.pricers.ipf.core.rules.calculate_payment.calculate_outlier.CalculateOutlierPayment;

/**
 * Calculate the outlier data.
 *
 * <p>Converted from {@code 3050-GET-OUTLIER} in the COBOL code.
 */
public class CalculateOutlierPayment2022 extends CalculateOutlierPayment {

  @Override
  public void calculate(IpfPricerContext calculationContext) {
    final IpfClaimData claimData = calculationContext.getClaimData();

    // 2022 update includes a fix to a former bug that did not factor prior days into the length of
    // stay calculations
    final int priorDays = claimData.getPriorDays();
    final int lengthOfStay = claimData.getLengthOfStay();
    final int daysUpTo9 = Math.max(0, Math.min(9, lengthOfStay + priorDays) - priorDays);
    final int daysOver9 = Math.max(0, Math.min(lengthOfStay, lengthOfStay + priorDays - 9));

    setOutlierPayment(calculationContext, daysUpTo9, daysOver9);
  }
}
