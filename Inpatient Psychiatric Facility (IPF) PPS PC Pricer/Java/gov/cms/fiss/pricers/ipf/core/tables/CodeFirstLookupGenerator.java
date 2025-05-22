package gov.cms.fiss.pricers.ipf.core.tables;

import gov.cms.fiss.pricers.common.csv.CsvContentReader;
import gov.cms.fiss.pricers.common.csv.CsvIngestionConfiguration;
import gov.cms.fiss.pricers.common.csv.LookupGenerator;
import java.util.List;
import java.util.Map;

public class CodeFirstLookupGenerator {
  private static final String CSV_FILE_PATTERN = "/codeFirst-%s.csv";

  private final CsvContentReader<CodeFirstTableEntry> contentProvider =
      new CsvContentReader<>(CodeFirstTableEntry.class)
          .customizeSchema(CsvContentReader.HEADER_ROW_CUSTOMIZER);

  public CodeFirstLookupGenerator(CsvIngestionConfiguration csvIngestionConfiguration) {
    if (!csvIngestionConfiguration.isValidationEnabled()) {
      contentProvider.disableValidation();
    }
  }

  /**
   * Creates a lookup table for Code First data for the given pricer year
   *
   * @return the populated table
   */
  public Map<String, CodeFirstTableEntry> generate(int pricerYear) {
    return generate(String.format(CSV_FILE_PATTERN, pricerYear));
  }

  /**
   * Creates a lookup table for add-on data with the given Code First file name
   *
   * @return the populated table
   */
  public Map<String, CodeFirstTableEntry> generate(String csvFile) {
    final List<CodeFirstTableEntry> content = contentProvider.read(csvFile);
    return LookupGenerator.generateMap(CodeFirstTableEntry::getCode, content);
  }
}
