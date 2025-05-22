package gov.cms.fiss.pricers.ipf.core.tables;

import gov.cms.fiss.pricers.common.csv.CsvContentReader;
import gov.cms.fiss.pricers.common.csv.CsvIngestionConfiguration;
import gov.cms.fiss.pricers.common.csv.LookupGenerator;
import java.util.List;
import java.util.Map;

public class ProcedureLookupGenerator {
  private static final String CSV_FILE_PATTERN = "/procedures-%s.csv";

  private final CsvContentReader<ProcedureTableEntry> contentProvider =
      new CsvContentReader<>(ProcedureTableEntry.class)
          .customizeSchema(CsvContentReader.HEADER_ROW_CUSTOMIZER);

  public ProcedureLookupGenerator(CsvIngestionConfiguration csvIngestionConfiguration) {
    if (!csvIngestionConfiguration.isValidationEnabled()) {
      contentProvider.disableValidation();
    }
  }

  /**
   * Creates a lookup table for oncology procedure data for the given pricer year.
   *
   * @return The populated table.
   */
  public Map<String, ProcedureTableEntry> generate(int pricerYear) {
    return generate(String.format(CSV_FILE_PATTERN, pricerYear));
  }

  /**
   * Creates a lookup table for oncology procedure data with the given CSV file name.
   *
   * @return The populated table.
   */
  public Map<String, ProcedureTableEntry> generate(String csvFile) {
    final List<ProcedureTableEntry> content = contentProvider.read(csvFile);
    return LookupGenerator.generateMap(ProcedureTableEntry::getCode, content);
  }
}
