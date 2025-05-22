package gov.cms.fiss.pricers.fqhc.core.rules.processing;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.fqhc.api.v2.IoceServiceLineData;
import gov.cms.fiss.pricers.fqhc.api.v2.ServiceLinePaymentData;
import gov.cms.fiss.pricers.fqhc.core.FqhcPricerContext;
import gov.cms.fiss.pricers.fqhc.core.ServiceLineContext;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.commons.lang3.StringUtils;

public class CalculateMdpcpReductionAmount
    implements CalculationRule<IoceServiceLineData, ServiceLinePaymentData, ServiceLineContext> {

  @Override
  public boolean shouldExecute(ServiceLineContext serviceLineContext) {
    //  09/10/2021 - CLG - MDPCP Release
    //              IF  I-SRVC-DEMO-CODE (DemoCodes) = ‘083’ AND
    //                  MEDICAL-VISIT (HcpcCode equal G0466, G0467, G0468) AND
    //                  HL-REVENUE CODE (1:3) EQUAL '052' (RevenueCode) AND
    //                  MDPCP_Percentage_Values (MdpcpReductionPercentage)
    //                  PERFORM 4350-CALC-MDPCP-REDUCTION-AMOUNT
    //                     THRU 4350-CALC-MDPCP-REDUCTION-AMOUNT-EXIT
    //              END-IF.

    final FqhcPricerContext calculationContext = serviceLineContext.getFqhcPricerContext();
    return calculationContext.containsMdpcpDemoCode()
        && StringUtils.equalsAny(
            serviceLineContext.getInput().getHcpcsCode(), "G0466", "G0467", "G0468")
        && serviceLineContext.isValidRevenueCode()
        && calculationContext.isValidMdpcpReductionPercentage();
  }

  @Override
  public void calculate(ServiceLineContext serviceLineContext) {
    // 11/10/2021 - CLG - Release 2022
    // COMPUTE HL-LITEM-REIM = HL-LITEM-PYMT – HL-LITEM-COIN
    // COMPUTE MDPCP_Reduction_Amount = MDPCP_Percentage * HL-LITEM-REIM
    // COMPUTE HL-LITEM-REIM = HL-LITEM-REIM – MDPCP_Reduction_Amount
    // COMPUTE HL-LITEM-PYMT = HL-LITEM-REIM + HL-LITEM-COIN

    final ServiceLinePaymentData serviceLinePayment = serviceLineContext.getOutput();
    final BigDecimal coInsurancePayment = serviceLinePayment.getCoinsuranceAmount();
    BigDecimal lineItemReimbursement = serviceLinePayment.getPayment().subtract(coInsurancePayment);
    final FqhcPricerContext calculationContext = serviceLineContext.getFqhcPricerContext();

    final BigDecimal mdpcpReductionAmount =
        calculationContext
            .getClaimData()
            .getMdpcpReductionPercent()
            .multiply(lineItemReimbursement)
            .setScale(2, RoundingMode.HALF_UP);
    lineItemReimbursement =
        lineItemReimbursement.subtract(mdpcpReductionAmount).setScale(2, RoundingMode.HALF_UP);

    final BigDecimal lineItemPayment =
        coInsurancePayment.add(lineItemReimbursement).setScale(2, RoundingMode.HALF_UP);

    serviceLinePayment.setMdpcpReductionAmount(mdpcpReductionAmount);
    serviceLinePayment.setPayment(lineItemPayment);
  }
}
