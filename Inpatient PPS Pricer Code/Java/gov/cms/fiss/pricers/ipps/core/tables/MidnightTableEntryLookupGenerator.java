package gov.cms.fiss.pricers.ipps.core.tables;

import gov.cms.fiss.pricers.common.csv.CsvContentReader;
import gov.cms.fiss.pricers.common.csv.CsvIngestionConfiguration;
import gov.cms.fiss.pricers.common.csv.LookupGenerator;
import java.util.List;
import java.util.Map;

/** Creates the lookup table needed for midnight data. */
public class MidnightTableEntryLookupGenerator {
  private static final String CSV_FILE_NAME = "/midnight.csv";

  private final CsvContentReader<MidnightTableEntry> contentProvider =
      new CsvContentReader<>(MidnightTableEntry.class)
          .customizeSchema(CsvContentReader.HEADER_ROW_CUSTOMIZER);

  public MidnightTableEntryLookupGenerator(CsvIngestionConfiguration csvIngestionConfiguration) {
    if (!csvIngestionConfiguration.isValidationEnabled()) {
      contentProvider.disableValidation();
    }
  }

  /**
   * Generates the lookup cross-reference for the default content.
   *
   * @return the generated lookup
   */
  public Map<String, MidnightTableEntry> generate() {
    return generate(CSV_FILE_NAME);
  }

  /**
   * Generates the lookup cross-reference from the specified source.
   *
   * @param csvFilename the file to process
   * @return the generated lookup
   */
  public Map<String, MidnightTableEntry> generate(String csvFilename) {
    final List<MidnightTableEntry> content = contentProvider.read(csvFilename);

    return LookupGenerator.generateMap(MidnightTableEntry::getMsa, content);
  }
}
