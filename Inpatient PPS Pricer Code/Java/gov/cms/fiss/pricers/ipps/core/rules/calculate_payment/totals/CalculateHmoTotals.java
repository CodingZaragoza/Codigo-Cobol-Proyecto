package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.totals;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import java.math.BigDecimal;

/**
 * Determine the HMO calculation for pass through add-on amount.
 *
 * <p>Converted from {@code 3850-HMO-IME-ADJ} in the COBOL code.
 *
 * @since 2019
 */
public class CalculateHmoTotals
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public boolean shouldExecute(IppsPricerContext calculationContext) {
    // Execute for all web pricer requests
    return calculationContext.isWebPricerRequest();
  }

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();

    //     IF HMO-TAG  = 'Y'
    //        PERFORM 3850-HMO-IME-ADJ.
    if (calculationContext.isHmoClaim()) {
      // ***********************************************************
      // ***  HMO CALC FOR PASS-THRU ADDON
      //     COMPUTE H-WK-PASS-AMT-PLUS-MISC ROUNDED =
      //          (P-NEW-PASS-AMT-PLUS-MISC -
      //          (P-NEW-PASS-AMT-ORGAN-ACQ +
      //           P-NEW-PASS-AMT-DIR-MED-ED)) * B-LOS.
      calculationContext.setPassthroughAmountPlusMisc(
          providerData
              .getPassThroughTotalAmount()
              .subtract(
                  providerData
                      .getPassThroughAmountForOrganAcquisition()
                      .add(providerData.getPassThroughAmountForDirectMedicalEducation())
                      .add(providerData.getPassThroughAmountForAllogenicStemCellAcquisition()))
              .multiply(BigDecimal.valueOf(calculationContext.getClaimData().getLengthOfStay())));

      // ***********************************************************
      // ***  HMO IME ADJUSTMENT --- NO LONGER PAID AS OF 10/01/2002
      //     COMPUTE PPS-OPER-IME-ADJ ROUNDED =
      //                   PPS-OPER-IME-ADJ * .0.
      calculationContext
          .getPaymentData()
          .setOperatingIndirectMedicalEducationAdjustment(BigDecimalUtils.ZERO);
    } else {
      //     COMPUTE H-WK-PASS-AMT-PLUS-MISC ROUNDED =
      //             P-NEW-PASS-AMT-PLUS-MISC *  B-N-LOS
      calculationContext.setPassthroughAmountPlusMisc(
          providerData
              .getPassThroughTotalAmount()
              .multiply(BigDecimal.valueOf(calculationContext.getClaimData().getLengthOfStay())));
    }

    calculationContext
        .getOutput()
        .getAdditionalCalculationVariables()
        .setPassthroughTotalPlusMisc(calculationContext.getPassthroughAmountPlusMisc());
  }
}
