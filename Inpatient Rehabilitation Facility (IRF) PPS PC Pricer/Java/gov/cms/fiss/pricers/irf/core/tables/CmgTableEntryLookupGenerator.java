package gov.cms.fiss.pricers.irf.core.tables;

import gov.cms.fiss.pricers.common.csv.CsvContentReader;
import gov.cms.fiss.pricers.common.csv.CsvIngestionConfiguration;
import gov.cms.fiss.pricers.common.csv.LookupGenerator;
import java.util.List;
import java.util.Map;

/** Creates the lookup table needed for CMG data. */
public class CmgTableEntryLookupGenerator {
  private static final String CSV_FILE_PATTERN = "/cmg-codes-%s.csv";

  private final CsvContentReader<CmgTableEntry> contentProvider =
      new CsvContentReader<>(CmgTableEntry.class)
          .customizeSchema(CsvContentReader.HEADER_ROW_CUSTOMIZER);

  public CmgTableEntryLookupGenerator(CsvIngestionConfiguration csvIngestionConfiguration) {
    if (!csvIngestionConfiguration.isValidationEnabled()) {
      contentProvider.disableValidation();
    }
  }

  /**
   * Generates the lookup cross-reference for the specified pricer year.
   *
   * @param pricerYear the year to reference
   */
  public Map<String, CmgTableEntry> generate(int pricerYear) {
    return generate(String.format(CSV_FILE_PATTERN, pricerYear));
  }

  /**
   * Generates the lookup cross-reference from the specified source.
   *
   * @param csvFilename the file to process
   * @return the generated lookup
   */
  public Map<String, CmgTableEntry> generate(String csvFilename) {
    final List<CmgTableEntry> content = contentProvider.read(csvFilename);

    return LookupGenerator.generateMap(CmgTableEntry::getCmgCode, content);
  }
}
