package gov.cms.fiss.pricers.hha.core.tables;

import gov.cms.fiss.pricers.common.csv.CsvContentReader;
import gov.cms.fiss.pricers.common.csv.CsvIngestionConfiguration;
import gov.cms.fiss.pricers.common.csv.LookupGenerator;
import java.util.List;
import java.util.Map;

public class HrgLookupProvider {
  private static final String CSV_FILE_PATTERN = "/hrg-%s.csv";

  private final CsvContentReader<HrgEntry> contentProvider =
      new CsvContentReader<>(HrgEntry.class)
          .customizeSchema(CsvContentReader.HEADER_ROW_CUSTOMIZER);

  public HrgLookupProvider(CsvIngestionConfiguration csvIngestionConfiguration) {
    if (!csvIngestionConfiguration.isValidationEnabled()) {
      contentProvider.disableValidation();
    }
  }

  /**
   * Creates a lookup table for HRG data for the given pricer year
   *
   * @return the populated table
   */
  public Map<String, HrgEntry> generate(int pricerYear) {
    return generate(String.format(CSV_FILE_PATTERN, pricerYear));
  }

  /**
   * Creates a lookup table for HRG data with the given CSV file name
   *
   * @return the populated table
   */
  public Map<String, HrgEntry> generate(String csvFile) {
    final List<HrgEntry> content = contentProvider.read(csvFile);
    return LookupGenerator.generateMap(HrgEntry::getCode, content);
  }
}
