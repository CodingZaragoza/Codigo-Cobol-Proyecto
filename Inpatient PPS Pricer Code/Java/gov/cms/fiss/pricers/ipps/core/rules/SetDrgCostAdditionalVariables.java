package gov.cms.fiss.pricers.ipps.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.api.v2.AdditionalCalculationVariableData;
import gov.cms.fiss.pricers.ipps.api.v2.AdditionalCapitalVariableData;
import gov.cms.fiss.pricers.ipps.api.v2.AdditionalOperatingVariableData;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import java.math.BigDecimal;

/**
 * Propagates internal variables for use in downstream DRG cost calculations.
 *
 * @since 2019
 */
public class SetDrgCostAdditionalVariables
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final AdditionalCalculationVariableData additionalVariables =
        calculationContext.getAdditionalVariables();

    // Capital
    final AdditionalCapitalVariableData additionalCapitalVariables =
        additionalVariables.getAdditionalCapitalVariables();

    additionalCapitalVariables.setCapitalFederalSpecificPortionPercent(
        getValueOrNull(calculationContext.getCapitalFederalSpecificPortionPct()));
    additionalCapitalVariables.setCapitalCostOutlier(
        getValueOrNull(calculationContext.getCapitalCostOutlier()));
    additionalCapitalVariables.setCapitalDisproportionateShareHospitalAmount(
        getValueOrNull(calculationContext.getCapitalDisproportionateShareHospital()));
    additionalCapitalVariables.setCapitalOldHoldHarmlessRate(
        getValueOrNull(calculationContext.getCapitalOldHoldHarmlessRate()));
    additionalCapitalVariables.setCapitalFederalRate(calculationContext.getCapitalFederalRate());
    additionalCapitalVariables.setCapitalLargeUrbanFactor(
        getValueOrNull(calculationContext.getCapitalLargeUrbanFactor()));
    additionalCapitalVariables.setCapitalGeographicAdjustmentFactor(
        getValueOrNull(calculationContext.getCapitalGeographicAdjFactor()));
    additionalCapitalVariables.setCapitalHospitalSpecificPortionPercent(
        getValueOrNull(calculationContext.getCapitalHospitalSpecificPortionPercentage()));
    additionalCapitalVariables.setCapitalIndirectMedicalEducationAmount(
        getValueOrNull(calculationContext.getCapitalIndirectMedicalEducation()));
    additionalCapitalVariables.setCapitalHospitalSpecificPortionPart(
        getValueOrNull(calculationContext.getCapitalHospitalSpecificPortionPart()));

    // Operating
    final AdditionalOperatingVariableData additionalOperatingVariables =
        additionalVariables.getAdditionalOperatingVariables();

    additionalOperatingVariables.setOperatingHospitalSpecificPortionPart(
        getValueOrNull(calculationContext.getOperatingHospitalSpecificPortionPart()));
    additionalOperatingVariables.setOperatingDisproportionateShareHospitalRatio(
        getValueOrNull(calculationContext.calculateDefaultOperatingDisproportionateShare()));
    additionalOperatingVariables.setOperatingFederalSpecificPortionPart(
        getValueOrNull(calculationContext.getOperatingFederalSpecificPortionPart()));
    additionalOperatingVariables.setOperatingIndirectMedicalEducationAmount(
        getValueOrNull(calculationContext.getOperatingIndirectMedicalEducation()));
    additionalOperatingVariables.setOperatingDisproportionateShareHospitalAmount(
        getValueOrNull(calculationContext.getOperatingDisproportionateShare()));

    // Root
    additionalVariables.setWageIndex(calculationContext.getCbsaReference().getWageIndex());
    additionalVariables.setHospitalSpecificPortionPercent(
        getValueOrNull(calculationContext.getOperatingHospitalSpecificPortionPct()));
    additionalVariables.setCostThreshold(calculationContext.getCostThreshold());
    additionalVariables.setHospitalSpecificPortionRate(
        getValueOrNull(calculationContext.getHospitalSpecificPortionRate()));
    additionalVariables.setRegularPercent(
        getValueOrNull(calculationContext.getRegularPercentage()));
    additionalVariables.setNationalLaborCost(getValueOrNull(calculationContext.getNationalLabor()));
    additionalVariables.setNationalLaborPercent(
        getValueOrNull(calculationContext.getNationalLaborPct()));
    additionalVariables.setNationalNonLaborCost(
        getValueOrNull(calculationContext.getNationalNonLabor()));
    additionalVariables.setNationalNonLaborPercent(
        getValueOrNull(calculationContext.getNationalNonLaborPct()));
    additionalVariables.setRegularLaborCost(getValueOrNull(calculationContext.getNationalLabor()));
    additionalVariables.setRegularNonLaborCost(
        getValueOrNull(calculationContext.getNationalNonLabor()));
  }

  private BigDecimal getValueOrNull(BigDecimal value) {
    BigDecimal returnVal = null;
    if (!BigDecimalUtils.isZero(value)) {
      returnVal = value;
    }

    return returnVal;
  }
}
