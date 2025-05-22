package gov.cms.fiss.pricers.ipps.core.rules.rules2022.wage_index;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.CbsaReference;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.tables.CbsaWageIndexEntry;
import gov.cms.fiss.pricers.ipps.core.tables.DataTables;

/**
 * Determines the value of the wage index based on comparison to the imputed floor wage index.
 *
 * @since 2022
 */
public class AdjustIndexForImputedFloor2022
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final CbsaReference cbsaReference = calculationContext.getCbsaReference();
    final DataTables dataTables = calculationContext.getDataTables();

    final CbsaWageIndexEntry imputedCbsaTableEntry =
        dataTables.getCbsaWageIndexEntry(
            calculationContext.getStateCode(), calculationContext.getDischargeDate());

    if (imputedCbsaTableEntry != null
        && BigDecimalUtils.isGreaterThanZero(imputedCbsaTableEntry.getImputedFloorWageIndex())
        && BigDecimalUtils.isGreaterThan(
            imputedCbsaTableEntry.getImputedFloorWageIndex(), cbsaReference.getWageIndex())) {
      cbsaReference.setCbsa(imputedCbsaTableEntry.getCbsa());
      cbsaReference.setEffectiveDate(imputedCbsaTableEntry.getEffectiveDate());
      cbsaReference.setWageIndex(imputedCbsaTableEntry.getImputedFloorWageIndex());
    }
  }
}
