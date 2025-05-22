package gov.cms.fiss.pricers.ipf.core.tables;

import gov.cms.fiss.pricers.common.csv.CsvContentReader;
import gov.cms.fiss.pricers.common.csv.CsvIngestionConfiguration;
import gov.cms.fiss.pricers.common.csv.LookupGenerator;
import java.util.List;
import java.util.Map;

public class DrgLookupGenerator {
  private static final String CSV_FILE_PATTERN = "/drg-%s.csv";

  private final CsvContentReader<DrgTableEntry> contentProvider =
      new CsvContentReader<>(DrgTableEntry.class)
          .customizeSchema(CsvContentReader.HEADER_ROW_CUSTOMIZER);

  public DrgLookupGenerator(CsvIngestionConfiguration csvIngestionConfiguration) {
    if (!csvIngestionConfiguration.isValidationEnabled()) {
      contentProvider.disableValidation();
    }
  }

  /**
   * Creates a lookup table for DRG data for the given pricer year
   *
   * @return the populated table
   */
  public Map<String, DrgTableEntry> generate(int pricerYear) {
    return generate(String.format(CSV_FILE_PATTERN, pricerYear));
  }

  /**
   * Creates a lookup table for add-on data with the given CSV file name
   *
   * @return the populated table
   */
  public Map<String, DrgTableEntry> generate(String csvFile) {
    final List<DrgTableEntry> content = contentProvider.read(csvFile);
    return LookupGenerator.generateMap(DrgTableEntry::getCode, content);
  }
}
