package gov.cms.fiss.pricers.ipps.core.tables;

import gov.cms.fiss.pricers.common.csv.CsvContentReader;
import gov.cms.fiss.pricers.common.csv.CsvIngestionConfiguration;
import gov.cms.fiss.pricers.common.csv.LookupGenerator;
import java.util.List;
import java.util.Map;

/** Creates the lookup table needed for new technology code per-year data. */
public class NewTechnologyAmountTableEntryLookupGenerator {
  private static final String CSV_FILE_PATTERN = "/newtech-costs-%s.csv";

  private final CsvContentReader<NewTechnologyAmountTableEntry> contentProvider =
      new CsvContentReader<>(NewTechnologyAmountTableEntry.class)
          .customizeSchema(CsvContentReader.HEADER_ROW_CUSTOMIZER);

  public NewTechnologyAmountTableEntryLookupGenerator(
      CsvIngestionConfiguration csvIngestionConfiguration) {
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
  public Map<String, NewTechnologyAmountTableEntry> generate(int pricerYear) {
    return generate(String.format(CSV_FILE_PATTERN, pricerYear));
  }

  /**
   * Generates the lookup cross-reference from the specified source.
   *
   * @param csvFilename the file to process
   * @return the generated lookup
   */
  public Map<String, NewTechnologyAmountTableEntry> generate(String csvFilename) {
    final List<NewTechnologyAmountTableEntry> content = contentProvider.read(csvFilename);

    return LookupGenerator.generateMap(NewTechnologyAmountTableEntry::getTechnologyName, content);
  }
}
