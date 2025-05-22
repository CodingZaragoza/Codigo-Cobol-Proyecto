package gov.cms.fiss.pricers.ipps.core.tables;

import com.fasterxml.jackson.databind.MapperFeature;
import gov.cms.fiss.pricers.common.csv.CsvContentReader;
import gov.cms.fiss.pricers.common.csv.CsvIngestionConfiguration;
import gov.cms.fiss.pricers.common.csv.LookupGenerator;
import gov.cms.fiss.pricers.ipps.core.tables.RatexTableEntry.RatexKey;
import java.util.List;
import java.util.Map;

/** Creates the lookup table needed for RateX per-year data. */
public class RatexTableEntryLookupGenerator {
  private static final String CSV_FILE_PATTERN = "/ratex-%s.csv";

  private final CsvContentReader<RatexTableEntry> contentProvider =
      new CsvContentReader<>(RatexTableEntry.class)
          .customizeSchema(CsvContentReader.HEADER_ROW_CUSTOMIZER);

  public RatexTableEntryLookupGenerator(CsvIngestionConfiguration csvIngestionConfiguration) {
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
  public Map<RatexKey, RatexTableEntry> generate(int pricerYear) {
    return generate(String.format(CSV_FILE_PATTERN, pricerYear));
  }

  /**
   * Generates the lookup cross-reference from the specified source.
   *
   * @param csvFilename the file to process
   * @return the generated lookup
   */
  public Map<RatexKey, RatexTableEntry> generate(String csvFilename) {
    contentProvider.customizeMapper(
        csvMapper -> {
          csvMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
          return csvMapper;
        });
    final List<RatexTableEntry> content = contentProvider.read(csvFilename);

    return LookupGenerator.generateMap(RatexKey::fromRatex, content);
  }
}
