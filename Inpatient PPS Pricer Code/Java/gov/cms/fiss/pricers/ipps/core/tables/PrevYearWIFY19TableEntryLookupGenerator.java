package gov.cms.fiss.pricers.ipps.core.tables;

import gov.cms.fiss.pricers.common.csv.CsvContentReader;
import gov.cms.fiss.pricers.common.csv.CsvIngestionConfiguration;
import gov.cms.fiss.pricers.common.csv.LookupGenerator;
import java.util.List;
import java.util.Map;

/** Creates the lookup table needed for FY 19 wage index per-year data. */
public class PrevYearWIFY19TableEntryLookupGenerator {
  private static final String CSV_FILE_PATTERN = "/prev-%s.csv";

  private final CsvContentReader<PrevYearWIFY19TableEntry> contentProvider =
      new CsvContentReader<>(PrevYearWIFY19TableEntry.class)
          .customizeSchema(CsvContentReader.HEADER_ROW_CUSTOMIZER);

  public PrevYearWIFY19TableEntryLookupGenerator(
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
  public Map<String, PrevYearWIFY19TableEntry> generate(int pricerYear) {
    return generate(String.format(CSV_FILE_PATTERN, pricerYear));
  }

  /**
   * Generates the lookup cross-reference from the specified source.
   *
   * @param csvFilename the file to process
   * @return the generated lookup
   */
  public Map<String, PrevYearWIFY19TableEntry> generate(String csvFilename) {
    final List<PrevYearWIFY19TableEntry> content = contentProvider.read(csvFilename);

    return LookupGenerator.generateMap(PrevYearWIFY19TableEntry::getProvider, content);
  }
}
