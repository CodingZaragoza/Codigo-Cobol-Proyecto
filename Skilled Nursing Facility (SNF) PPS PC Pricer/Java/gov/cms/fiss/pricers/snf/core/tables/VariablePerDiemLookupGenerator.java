package gov.cms.fiss.pricers.snf.core.tables;

import gov.cms.fiss.pricers.common.csv.CsvContentReader;
import gov.cms.fiss.pricers.common.csv.CsvIngestionConfiguration;
import gov.cms.fiss.pricers.common.csv.LookupGenerator;
import java.util.List;
import java.util.Map;

public class VariablePerDiemLookupGenerator {
  private static final String CSV_FILE_PATTERN = "/vpd-adj-factors.csv";

  private final CsvContentReader<VariablePerDiemEntry> contentProvider =
      new CsvContentReader<>(VariablePerDiemEntry.class)
          .customizeSchema(CsvContentReader.HEADER_ROW_CUSTOMIZER);

  public VariablePerDiemLookupGenerator(CsvIngestionConfiguration csvIngestionConfiguration) {
    if (!csvIngestionConfiguration.isValidationEnabled()) {
      contentProvider.disableValidation();
    }
  }

  /**
   * Creates a lookup table for VPD Adjustment Factor data.
   *
   * @return the populated table
   */
  public Map<Integer, VariablePerDiemEntry> generate() {
    return generate(CSV_FILE_PATTERN);
  }

  /**
   * Creates a lookup table for VPD Adjustment Factor data with the given CSV file name.
   *
   * @param csvFile filename of the csv file to use
   * @return the populated table
   */
  public Map<Integer, VariablePerDiemEntry> generate(String csvFile) {
    final List<VariablePerDiemEntry> content = contentProvider.read(csvFile);

    return LookupGenerator.generateMap(VariablePerDiemEntry::getDay, content);
  }
}
