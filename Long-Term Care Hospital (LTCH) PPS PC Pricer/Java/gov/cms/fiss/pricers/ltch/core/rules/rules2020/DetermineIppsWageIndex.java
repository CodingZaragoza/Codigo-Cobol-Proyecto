package gov.cms.fiss.pricers.ltch.core.rules.rules2020;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.common.util.LocalDateUtils;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimData;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingRequest;
import gov.cms.fiss.pricers.ltch.api.v2.LtchClaimPricingResponse;
import gov.cms.fiss.pricers.ltch.core.LtchPricerContext;
import gov.cms.fiss.pricers.ltch.core.codes.ReturnCode;
import gov.cms.fiss.pricers.ltch.core.tables.CbsaWageIndexEntry;
import gov.cms.fiss.pricers.ltch.core.tables.DataTables;
import gov.cms.fiss.pricers.ltch.core.tables.WageIppsIndexRuralEntry;
import java.time.LocalDate;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

public class DetermineIppsWageIndex
    implements CalculationRule<
        LtchClaimPricingRequest, LtchClaimPricingResponse, LtchPricerContext> {

  /** Converted from 0575-GET-IPPS-CBSA. in COBOL. Sets the IPPS CBSA values for calculation. */
  @Override
  public void calculate(LtchPricerContext calculationContext) {
    //    SET IPPS CBSA TO GEOGRAPHIC LOCATION CBSA IN PSFtrimmedGeolocation = "10540"
    //    ADDED FOLLOWING LINE TO ALLOW LTCH PRICER TO WORK
    //    WITH THE NEW ARIZONA STATE CODE OF 00
    final InpatientProviderData providerData = calculationContext.getProviderData();
    final LtchClaimData claimData = calculationContext.getClaimData();
    final DataTables dataTables = calculationContext.getDataTables();
    String trimmedGeolocation =
        StringUtils.trimToEmpty(providerData.getCbsaActualGeographicLocation());
    trimmedGeolocation = "00".equalsIgnoreCase(trimmedGeolocation) ? "03" : trimmedGeolocation;

    // lookup here
    CbsaWageIndexEntry entry =
        dataTables.getIppsCbsaWageIndex(trimmedGeolocation, providerData.getEffectiveDate());
    if (entry != null
        && !(claimData.getDischargeDate().isBefore(entry.getEffectiveDate()))
        && ("98".equalsIgnoreCase(trimmedGeolocation)
            || "99".equalsIgnoreCase(trimmedGeolocation)
            || LocalDateUtils.inRange(
                entry.getEffectiveDate(),
                calculationContext.getFyBegin(),
                calculationContext.getFyEnd()))) {
      calculationContext.setHoldProvIppsCBSA(entry);
    } else if (entry == null) {
      // set return code to 60
      calculationContext.applyReturnCode(ReturnCode.CBSA_WAGE_INDEX_NOT_FOUND_60);
    }
    // Set wage index floors
    final String defaultedGeolocation =
        getGeolocation(calculationContext.getProviderData().getStateCode());
    assignFloorCBSA(calculationContext, calculationContext.getReturnCode(), defaultedGeolocation);

    // Both of the below lines must set the entry variable due to the copyBuilder pattern
    entry = getIppsCbsaTableEntry(calculationContext, entry);
    entry = setIppsCbsaTableEntrySize(claimData, trimmedGeolocation, entry);

    setIppsCbsaTableEntrySize(claimData, trimmedGeolocation, entry);
    calculationContext.setHoldProvIppsCBSA(entry);
    calculationContext.calculateIppsLaborShares();
  }

  /**
   * Replaces the geolocation with the new Arizona state code of 03 if it is set to 00.
   *
   * @param geolocation the geolocation
   * @return String geolocation
   */
  protected String getGeolocation(String geolocation) {
    return "00".equals(geolocation) ? "03" : geolocation;
  }

  private CbsaWageIndexEntry setIppsCbsaTableEntrySize(
      LtchClaimData claimData, String trimmedGeolocation, CbsaWageIndexEntry entry) {
    // Get the size indicator
    if (entry != null && !((claimData.getDischargeDate().isBefore(entry.getEffectiveDate())))) {
      String size = "O";
      // COBOL checks to confirm that the first 3 chars of the geolocation string are spaces
      if (trimmedGeolocation.length() <= 2) {
        size = "R";
      } else if ("L".equals(entry.getSize())) {
        size = "L";
      }

      return entry.copyBuilder().size(size).build();
    }

    return entry;
  }

  /**
   * For the IPPS wage index, the imputed wage floor policy is being reintroduced and made permanent
   * effective FY 2022. As is being done in the IPPS Pricer, the LTCH Pricer will need to apply the
   * imputed wage floor after the rural floor has been applied when determining the IPPS wage index.
   * This adjustment only applies to a handful of states. August 2021 - The following specs were
   * provided for implementing the imputed wage floor for the IPPS Pricer. After the rural floor has
   * been applied, the Pricer will need to do the following:
   *
   * <ol>
   *   <li>Use provider’s state code to pull value from fourth column of wage index table.
   *   <li>If fourth column value in wage index table is null then do nothing.
   *   <li>If fourth column value in wage index table is not null do the following:
   *       <ol type="a">
   *         <li>If value in fourth column is greater than the providers wage index determined up to
   *             this point, then set providers wage index equal to fourth column value. If value in
   *             fourth column is less than or equal to the providers wage index determined up to
   *             this point, then do nothing.
   *       </ol>
   * </ol>
   *
   * @param calculationContext the LtchPricerContext for this rule
   * @param entry current IppsCbsaTableEntry
   * @return the IppsCbsaTableEntry to use for pricing
   */
  protected CbsaWageIndexEntry getIppsCbsaTableEntry(
      LtchPricerContext calculationContext, CbsaWageIndexEntry entry) {
    if (entry != null
        && BigDecimalUtils.isGreaterThan(
            calculationContext.getWageIndexFloor().getWageIndex(),
            entry.getGeographicWageIndex())) {
      entry =
          CbsaWageIndexEntry.builder()
              .cbsa(calculationContext.getWageIndexFloor().getCbsaPadded())
              .effectiveDate(calculationContext.getWageIndexFloor().getEffectiveDate())
              .geographicWageIndex(calculationContext.getWageIndexFloor().getWageIndex())
              .build();
    }

    return entry;
  }

  /**
   * Gets the wage index floor values for calculation.
   *
   * <p>Converted from {@code 0580-FY2015-LATER-FLOOR-CBSA} in COBOL.
   *
   * @param calculationContext the LtchPricerContext for this rule
   * @param returnCode the current return code
   * @param geolocation the claim's geolocation
   */
  protected void assignFloorCBSA(
      LtchPricerContext calculationContext, String returnCode, String geolocation) {
    WageIppsIndexRuralEntry ruralEntry = WageIppsIndexRuralEntry.DEFAULT.build();
    final LocalDate dischargeDate = calculationContext.getClaimData().getDischargeDate();

    if (StringUtils.equals(ReturnCode.NORMAL_DRG_00.getCode(), returnCode)) {
      ruralEntry = getRuralFloorIpps(calculationContext, geolocation, dischargeDate);
    }
    calculationContext.setWageIndexFloor(ruralEntry);
  }

  /**
   * Selects the correct IPPS wage index for the rural floor.
   *
   * <p>Converted from {@code 0190-GET-RURAL-FLOOR-IPPS} in COBOL.
   *
   * @param context the LtchPricerContext for this rule
   * @param geolocation the claim's geolocation
   * @param dischargeDate the claim's discharge date
   * @return returns the correct IPPS wage index rural floor entry
   */
  protected WageIppsIndexRuralEntry getRuralFloorIpps(
      LtchPricerContext context, String geolocation, LocalDate dischargeDate) {
    return Optional.ofNullable(
            context.getDataTables().getWageIppsIndexRural(geolocation, dischargeDate))
        .orElse(WageIppsIndexRuralEntry.DEFAULT.build());
  }
}
