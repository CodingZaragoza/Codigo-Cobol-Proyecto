package gov.cms.fiss.pricers.ipps.core.tables;

import com.fasterxml.jackson.dataformat.csv.CsvParser.Feature;
import gov.cms.fiss.pricers.common.csv.CsvContentReader;
import gov.cms.fiss.pricers.common.csv.CsvIngestionConfiguration;
import gov.cms.fiss.pricers.common.csv.LookupGenerator;
import gov.cms.fiss.pricers.ipps.api.v1.DrgsTableEntry;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/** Creates the lookup table needed for DRG per-year data. */
public class DrgsTableEntryLookupGenerator {
  private static final String CSV_FILE_PATTERN = "/drgstable-%s.csv";

  private final CsvContentReader<DrgsTableEntry> contentProvider =
      new CsvContentReader<>(DrgsTableEntry.class)
          .customizeSchema(CsvContentReader.HEADER_ROW_CUSTOMIZER)
          .customizeMapper(m -> m.enable(Feature.TRIM_SPACES));

  public DrgsTableEntryLookupGenerator(CsvIngestionConfiguration csvIngestionConfiguration) {
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
  public Map<String, NavigableMap<LocalDate, DrgsTableEntry>> generate(int pricerYear) {
    return generate(String.format(CSV_FILE_PATTERN, pricerYear));
  }

  /**
   * Generates the lookup cross-reference from the specified source.
   *
   * @param csvFilename the file to process
   * @return the generated lookup
   */
  public Map<String, NavigableMap<LocalDate, DrgsTableEntry>> generate(String csvFilename) {
    final List<DrgsTableEntry> content = contentProvider.read(csvFilename);

    // This comparison is a no-op, as there is no chance of merge conflicts here
    final BinaryOperator<DrgsTableEntry> comparator = BinaryOperator.maxBy((a, b) -> 0);
    final Collector<DrgsTableEntry, ?, NavigableMap<LocalDate, DrgsTableEntry>> collector =
        Collectors.toMap(
            DrgsTableEntry::getEffectiveDate, Function.identity(), comparator, TreeMap::new);

    return LookupGenerator.generateCrossReferenceToMap(
        DrgsTableEntry::getDiagnosticRelatedGroup, content, HashMap::new, collector);
  }
}
