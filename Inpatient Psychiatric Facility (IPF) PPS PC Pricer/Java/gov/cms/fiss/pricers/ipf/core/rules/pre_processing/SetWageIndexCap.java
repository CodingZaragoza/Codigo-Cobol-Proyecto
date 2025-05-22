package gov.cms.fiss.pricers.ipf.core.rules.pre_processing;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimData;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingRequest;
import gov.cms.fiss.pricers.ipf.api.v2.IpfClaimPricingResponse;
import gov.cms.fiss.pricers.ipf.core.IpfPricerContext;
import gov.cms.fiss.pricers.ipf.core.tables.CbsaWageIndexEntry;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.commons.lang3.StringUtils;

// TODO: Possibly remove from 2020 pricer

/**
 * Apply the supplemental wage index cap to the CBSA wage index, if applicable.
 *
 * <pre>
 * **  CHECK IF SUPPLMENTAL WAGE INDEX SHOULD BE USED
 * **  TO CALCULATE THE EFFECTIVE WAGE INDEX
 * **           ONLY FOR FY20201
 * </pre>
 *
 * <p>Converted from {@code 0560-SUPP-WAGE-INDEX} in the COBOL code.
 */
public class SetWageIndexCap
    implements CalculationRule<IpfClaimPricingRequest, IpfClaimPricingResponse, IpfPricerContext> {
  //      IF IPF-RTC = 00
  //         IF BILL-DISCHARGE-DATE > W-FY2021-BEGIN-DT
  //         OR BILL-DISCHARGE-DATE = W-FY2021-BEGIN-DT
  //            PERFORM 0560-SUPP-WAGE-INDEX THRU 0560-EXIT.
  // The above condition is used to call this rule in COBOL, however in Java it is not currently
  // possible to have a return code other than zero (0) at this point. The date check can be
  // handled by only including this rule in the 2021 and later pricers, so no check is required here

  @Override
  public void calculate(IpfPricerContext calculationContext) {
    //     IF SUPP-WI-PRIOR-YEAR
    //        IF (( W-CBSA-INDEX - P-NEW-SUPP-WI) / P-NEW-SUPP-WI )
    //            < W-WI-PCT-REDUC-FY2020
    //            COMPUTE W-CBSA-INDEX ROUNDED = P-NEW-SUPP-WI *
    //                                           W-WI-PCT-ADJ-FY2020
    //            END-COMPUTE
    //        END-IF
    //     END-IF.
    final InpatientProviderData providerData = calculationContext.getProviderData();
    final IpfClaimData claimData = calculationContext.getClaimData();

    // Supplemental wage index indicator 1 or 3 (eff 10/01/24) is eligible for wage index cap

    if (StringUtils.equals(providerData.getSupplementalWageIndexIndicator(), "1")
        || (StringUtils.equals(providerData.getSupplementalWageIndexIndicator(), "3")
            && LocalDateUtils.isAfterOrEqual(
                claimData.getDischargeDate(), IpfPricerContext.FISCAL_YEAR_2025_BEGIN))) {
      final CbsaWageIndexEntry cbsaWageIndexEntry = calculationContext.getCbsaWageIndexEntry();
      final BigDecimal wageIndex = cbsaWageIndexEntry.getGeographicWageIndex();
      final BigDecimal supplementalWageIndex = providerData.getSupplementalWageIndex();
      if (BigDecimalUtils.isLessThan(
          wageIndex
              .subtract(supplementalWageIndex)
              .divide(supplementalWageIndex, 4, RoundingMode.HALF_UP),
          IpfPricerContext.WAGE_INDEX_REDUCTION_FOR_2021)) {
        calculationContext.setCbsaWageIndexEntry(
            cbsaWageIndexEntry
                .copyBuilder()
                .geographicWageIndex(
                    supplementalWageIndex
                        .multiply(IpfPricerContext.WAGE_INDEX_ADJUSTMENT_FOR_2021)
                        .setScale(4, RoundingMode.HALF_UP))
                .build());
      }
    }
  }
}
