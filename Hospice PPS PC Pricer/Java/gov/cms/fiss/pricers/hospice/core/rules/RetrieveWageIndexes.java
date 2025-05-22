package gov.cms.fiss.pricers.hospice.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimData;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingRequest;
import gov.cms.fiss.pricers.hospice.api.v2.HospiceClaimPricingResponse;
import gov.cms.fiss.pricers.hospice.core.HospicePricerContext;
import gov.cms.fiss.pricers.hospice.core.codes.ReturnCode;
import gov.cms.fiss.pricers.hospice.core.tables.CbsaWageIndexEntry;
import gov.cms.fiss.pricers.hospice.core.tables.DataTables;
import org.apache.commons.lang3.StringUtils;

public class RetrieveWageIndexes
    implements CalculationRule<
        HospiceClaimPricingRequest, HospiceClaimPricingResponse, HospicePricerContext> {

  /**
   * Determines the beneficiary wage index.
   *
   * <p>Converted from {@code HOSPIDR} in the COBOL code.
   */
  @Override
  public void calculate(HospicePricerContext calculationContext) {
    final HospiceClaimData claimData = calculationContext.getClaimData();
    final DataTables dataTables = calculationContext.getDataTables();
    final String providerCbsa = StringUtils.trimToNull(claimData.getProviderCbsa());
    final String patientCbsa = StringUtils.trimToNull(claimData.getPatientCbsa());

    final CbsaWageIndexEntry providerTableEntry =
        dataTables.getCbsaWageIndexEntry(
            StringUtils.leftPad(providerCbsa, 5, "9"), claimData.getServiceFromDate());
    final CbsaWageIndexEntry beneficiaryTableEntry =
        dataTables.getCbsaWageIndexEntry(
            StringUtils.leftPad(patientCbsa, 5, "9"), claimData.getServiceFromDate());

    if (providerCbsa == null && patientCbsa == null
        || providerCbsa != null && providerTableEntry == null
        || patientCbsa != null && beneficiaryTableEntry == null) {
      calculationContext.applyReturnCodeAndComplete(ReturnCode.INVALID_CBSA_30);
      return;
    }

    if (providerTableEntry != null) {
      calculationContext.setProviderWageIndex(providerTableEntry.getGeographicWageIndex());
    }
    if (beneficiaryTableEntry != null) {
      calculationContext.setPatientWageIndex(beneficiaryTableEntry.getGeographicWageIndex());
    }
  }
}
