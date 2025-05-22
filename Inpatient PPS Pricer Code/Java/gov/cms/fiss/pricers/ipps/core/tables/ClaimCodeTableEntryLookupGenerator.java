package gov.cms.fiss.pricers.ipps.core.tables;

import gov.cms.fiss.pricers.common.csv.CsvContentReader;
import gov.cms.fiss.pricers.common.csv.CsvIngestionConfiguration;
import gov.cms.fiss.pricers.common.csv.LookupGenerator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/** Creates the lookup table needed for claim code per-year data. */
public class ClaimCodeTableEntryLookupGenerator {
  private static final String CSV_FILE_PATTERN = "/claim-codes-%s.csv";

  private final CsvContentReader<ClaimCodeTableEntry> contentProvider =
      new CsvContentReader<>(ClaimCodeTableEntry.class)
          .customizeSchema(CsvContentReader.HEADER_ROW_CUSTOMIZER);

  public ClaimCodeTableEntryLookupGenerator(CsvIngestionConfiguration csvIngestionConfiguration) {
    if (!csvIngestionConfiguration.isValidationEnabled()) {
      contentProvider.disableValidation();
    }
  }

  /**
   * Generates the lookup cross-reference for the specified pricer year.
   *
   * @param pricerYear the year to reference
   * @return the generated lookup
   */
  public Map<String, Map<ClaimCodeType, List<String>>> generate(int pricerYear) {
    return generate(String.format(CSV_FILE_PATTERN, pricerYear));
  }

  /**
   * Generates the lookup cross-reference from the specified source.
   *
   * @param csvFilename the file to process
   * @return the generated lookup
   */
  public Map<String, Map<ClaimCodeType, List<String>>> generate(String csvFilename) {
    final List<ClaimCodeTableEntry> content = contentProvider.read(csvFilename);

    final Map<String, List<ClaimCodeTableEntry>> mapByName =
        LookupGenerator.generateCrossReferenceToList(
            ClaimCodeTableEntry::getReferenceName,
            content,
            Collectors.mapping(Function.identity(), Collectors.toList()));

    final Map<String, Map<ClaimCodeType, List<String>>> mapByNameAndCodeType = new HashMap<>();

    mapByName.forEach(
        (name, entries) ->
            mapByNameAndCodeType.put(
                name,
                LookupGenerator.generateCrossReferenceToList(
                    ClaimCodeTableEntry::getCodeType,
                    entries,
                    Collectors.mapping(ClaimCodeTableEntry::getCode, Collectors.toList()))));

    return mapByNameAndCodeType;
  }
}
