package gov.cms.fiss.pricers.ipf.core.tables;

import gov.cms.fiss.pricers.common.csv.CsvContentReader;
import gov.cms.fiss.pricers.common.csv.CsvIngestionConfiguration;
import gov.cms.fiss.pricers.common.csv.LookupGenerator;
import java.util.List;
import java.util.Map;

public class ComorbidityLookupGenerator {
  private static final String CSV_FILE_PATTERN = "/comorbidities-%s.csv";

  private final CsvContentReader<ComorbidityTableEntry> contentProvider =
      new CsvContentReader<>(ComorbidityTableEntry.class)
          .customizeSchema(CsvContentReader.HEADER_ROW_CUSTOMIZER);

  public ComorbidityLookupGenerator(CsvIngestionConfiguration csvIngestionConfiguration) {
    if (!csvIngestionConfiguration.isValidationEnabled()) {
      contentProvider.disableValidation();
    }
  }

  /**
   * Creates a lookup table for comorbidity data for the given pricer year
   *
   * @return the populated table
   */
  public Map<String, ComorbidityTableEntry> generate(int pricerYear) {
    return generate(String.format(CSV_FILE_PATTERN, pricerYear));
  }

  /**
   * Creates a lookup table for add-on data with the given CSV file name
   *
   * @return the populated table
   */
  public Map<String, ComorbidityTableEntry> generate(String csvFile) {
    final List<ComorbidityTableEntry> content = contentProvider.read(csvFile);
    return LookupGenerator.generateMap(ComorbidityTableEntry::getCode, content);
  }
}
