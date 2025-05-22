package gov.cms.fiss.pricers.ipps.core.tables;

import gov.cms.fiss.pricers.common.csv.CsvContentReader;
import gov.cms.fiss.pricers.common.csv.CsvIngestionConfiguration;
import gov.cms.fiss.pricers.common.csv.LookupGenerator;
import java.util.List;
import java.util.Map;

/** Creates the lookup table needed for rural floor per-year data. */
public class RuralFloorTableEntryLookupGenerator {
  private static final String CSV_FILE_PATTERN = "/rufl-%s.csv";

  private final CsvContentReader<RuralFloorTableEntry> contentProvider =
      new CsvContentReader<>(RuralFloorTableEntry.class)
          .customizeSchema(CsvContentReader.HEADER_ROW_CUSTOMIZER);

  public RuralFloorTableEntryLookupGenerator(CsvIngestionConfiguration csvIngestionConfiguration) {
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
  public Map<String, RuralFloorTableEntry> generate(int pricerYear) {
    return generate(String.format(CSV_FILE_PATTERN, pricerYear));
  }

  /**
   * Generates the lookup cross-reference from the specified source.
   *
   * @param csvFilename the file to process
   * @return the generated lookup
   */
  public Map<String, RuralFloorTableEntry> generate(String csvFilename) {
    final List<RuralFloorTableEntry> content = contentProvider.read(csvFilename);

    return LookupGenerator.generateMap(RuralFloorTableEntry::getCbsa, content);
  }
}
