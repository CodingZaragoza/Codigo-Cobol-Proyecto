package gov.cms.fiss.pricers.ipps.core.rules.cost_factor_determination.ratex;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingRequest;
import gov.cms.fiss.pricers.ipps.api.v2.IppsClaimPricingResponse;
import gov.cms.fiss.pricers.ipps.core.CbsaReference;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import gov.cms.fiss.pricers.ipps.core.tables.DataTables;
import gov.cms.fiss.pricers.ipps.core.tables.RatexTableEntry;
import gov.cms.fiss.pricers.ipps.core.tables.RatexTableEntry.Scope;

/**
 * Determines the labor costs from the identified rate table.
 *
 * <p>Converted as the assignment portion of the following paragraphs from {@code 2050-RATES-TB} in
 * the COBOL code:
 *
 * <ul>
 *   <li>{@code 2300-GET-LAB-NONLAB-TB1-RATES}
 *   <li>{@code 2300-GET-LAB-NONLAB-TB2-RATES}
 *   <li>{@code 2300-GET-LAB-NONLAB-TB3-RATES}
 *   <li>{@code 2300-GET-LAB-NONLAB-TB4-RATES}
 *   <li>{@code 2300-GET-LAB-NONLAB-TB5-RATES}
 *   <li>{@code 2300-GET-LAB-NONLAB-TB6-RATES}
 *   <li>{@code 2300-GET-LAB-NONLAB-TB7-RATES}
 *   <li>{@code 2300-GET-LAB-NONLAB-TB8-RATES}
 * </ul>
 *
 * @since 2019
 */
public class DetermineLaborCosts
    implements CalculationRule<
        IppsClaimPricingRequest, IppsClaimPricingResponse, IppsPricerContext> {

  @Override
  public void calculate(IppsPricerContext calculationContext) {
    final DataTables dataTables = calculationContext.getDataTables();
    final CbsaReference cbsaReference = calculationContext.getCbsaReference();

    final RatexTableEntry ratexEntry =
        dataTables.getRatexIndex(
            calculationContext.getRatexTable(), Scope.NAT, cbsaReference.isLargeUrban());
    calculationContext.setNationalLabor(ratexEntry.getRegLabor());
    calculationContext.setNationalNonLabor(ratexEntry.getRegNLabor());
  }
}
