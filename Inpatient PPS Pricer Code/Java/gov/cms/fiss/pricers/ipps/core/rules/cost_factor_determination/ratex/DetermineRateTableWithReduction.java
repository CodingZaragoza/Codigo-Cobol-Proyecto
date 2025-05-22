package gov.cms.fiss.pricers.ipps.core.rules.cost_factor_determination.ratex;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.CbsaReference;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import java.math.BigDecimal;
import org.apache.commons.lang3.StringUtils;

/**
 * Determines the rate table to utilize for a claim when there is an EHR reduction.
 *
 * <p>Converted from {@code 2050-RATES-TB} in the COBOL code (continued).
 *
 * @since 2019
 */
public class DetermineRateTableWithReduction
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();
    final CbsaReference cbsaReference = calculationContext.getCbsaReference();

    //     IF ((P-NEW-CBSA-HOSP-QUAL-IND = '1') AND
    //        (P-EHR-REDUC-IND = 'Y')           AND
    //        (H-WAGE-INDEX > 01.0000))
    //        PERFORM 2300-GET-LAB-NONLAB-TB5-RATES
    //           THRU 2300-GET-LAB-NONLAB-TB5-EXIT
    //             VARYING R1 FROM 1 BY 1 UNTIL R1 > 1.
    if (StringUtils.equals(providerData.getHospitalQualityIndicator(), "1")
        && StringUtils.equals(providerData.getEhrReductionIndicator(), "Y")
        && BigDecimalUtils.isGreaterThan(cbsaReference.getWageIndex(), BigDecimal.ONE)) {
      calculationContext.setRatexTable("tab5");
    }

    //     IF ((P-NEW-CBSA-HOSP-QUAL-IND NOT = '1') AND
    //        (P-EHR-REDUC-IND = 'Y')               AND
    //         (H-WAGE-INDEX > 01.0000))
    //        PERFORM 2300-GET-LAB-NONLAB-TB6-RATES
    //           THRU 2300-GET-LAB-NONLAB-TB6-EXIT
    //             VARYING R1 FROM 1 BY 1 UNTIL R1 > 1.
    if (!StringUtils.equals(providerData.getHospitalQualityIndicator(), "1")
        && StringUtils.equals(providerData.getEhrReductionIndicator(), "Y")
        && BigDecimalUtils.isGreaterThan(cbsaReference.getWageIndex(), BigDecimal.ONE)) {
      calculationContext.setRatexTable("tab6");
    }

    //     IF ((P-NEW-CBSA-HOSP-QUAL-IND  = '1') AND
    //        (P-EHR-REDUC-IND = 'Y')            AND
    //         (H-WAGE-INDEX < 01.0000 OR H-WAGE-INDEX = 01.0000))
    //        PERFORM 2300-GET-LAB-NONLAB-TB7-RATES
    //           THRU 2300-GET-LAB-NONLAB-TB7-EXIT
    //             VARYING R1 FROM 1 BY 1 UNTIL R1 > 1.
    if (StringUtils.equals(providerData.getHospitalQualityIndicator(), "1")
        && StringUtils.equals(providerData.getEhrReductionIndicator(), "Y")
        && BigDecimalUtils.isLessThanOrEqualTo(cbsaReference.getWageIndex(), BigDecimal.ONE)) {
      calculationContext.setRatexTable("tab7");
    }

    //     IF ((P-NEW-CBSA-HOSP-QUAL-IND NOT = '1') AND
    //        (P-EHR-REDUC-IND = 'Y')               AND
    //         (H-WAGE-INDEX < 01.0000 OR H-WAGE-INDEX = 01.0000))
    //        PERFORM 2300-GET-LAB-NONLAB-TB8-RATES
    //           THRU 2300-GET-LAB-NONLAB-TB8-EXIT
    //             VARYING R1 FROM 1 BY 1 UNTIL R1 > 1.
    if (!StringUtils.equals(providerData.getHospitalQualityIndicator(), "1")
        && StringUtils.equals(providerData.getEhrReductionIndicator(), "Y")
        && BigDecimalUtils.isLessThanOrEqualTo(cbsaReference.getWageIndex(), BigDecimal.ONE)) {
      calculationContext.setRatexTable("tab8");
    }
  }
}
