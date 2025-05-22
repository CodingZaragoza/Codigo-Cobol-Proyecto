package gov.cms.fiss.pricers.ipps.core.tables;

import gov.cms.fiss.pricers.common.csv.CsvContentReader;
import gov.cms.fiss.pricers.common.csv.CsvIngestionConfiguration;
import gov.cms.fiss.pricers.common.csv.LookupGenerator;
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

/** Creates the lookup table needed for out-migration per-year data. */
public class OutMigrationTableEntryLookupGenerator {
  private static final String CSV_FILE_PATTERN = "/outmigration-%s.csv";

  private final CsvContentReader<OutMigrationTableEntry> contentProvider =
      new CsvContentReader<>(OutMigrationTableEntry.class)
          .customizeSchema(CsvContentReader.HEADER_ROW_CUSTOMIZER);

  public OutMigrationTableEntryLookupGenerator(
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
  public Map<String, NavigableMap<LocalDate, OutMigrationTableEntry>> generate(int pricerYear) {
    return generate(String.format(CSV_FILE_PATTERN, pricerYear));
  }

  /**
   * Generates the lookup cross-reference from the specified source.
   *
   * @param csvFilename the file to process
   * @return the generated lookup
   */
  public Map<String, NavigableMap<LocalDate, OutMigrationTableEntry>> generate(String csvFilename) {
    final List<OutMigrationTableEntry> content = contentProvider.read(csvFilename);

    // This comparison is a no-op, as there is no chance of merge conflicts here
    final BinaryOperator<OutMigrationTableEntry> comparator = BinaryOperator.maxBy((a, b) -> 0);
    final Collector<OutMigrationTableEntry, ?, NavigableMap<LocalDate, OutMigrationTableEntry>>
        collector =
            Collectors.toMap(
                OutMigrationTableEntry::getStartDate,
                Function.identity(),
                comparator,
                TreeMap::new);

    return LookupGenerator.generateCrossReferenceToMap(
        OutMigrationTableEntry::getCounty, content, HashMap::new, collector);
  }
}
