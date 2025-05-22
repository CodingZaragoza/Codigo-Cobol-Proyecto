package gov.cms.fiss.pricers.ltch.core.tables;

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

public class WageIppsIndexRuralLookupGenerator {
  private static final String CSV_FILE_PATTERN = "/rufl.csv";

  private final CsvContentReader<WageIppsIndexRuralEntry> contentProvider =
      new CsvContentReader<>(WageIppsIndexRuralEntry.class);

  public WageIppsIndexRuralLookupGenerator(CsvIngestionConfiguration csvIngestionConfiguration) {
    if (!csvIngestionConfiguration.isValidationEnabled()) {
      contentProvider.disableValidation();
    }
  }

  /**
   * Creates a lookup table for CBSA Wage Index data.
   *
   * @return the populated table
   */
  public Map<String, NavigableMap<LocalDate, WageIppsIndexRuralEntry>> generate() {
    return generate(CSV_FILE_PATTERN);
  }

  /**
   * Creates a lookup table for Apc Rate History data with the given CSV file name.
   *
   * @return the populated table
   */
  public Map<String, NavigableMap<LocalDate, WageIppsIndexRuralEntry>> generate(String csvFile) {
    final List<WageIppsIndexRuralEntry> content = contentProvider.read(csvFile);
    final BinaryOperator<WageIppsIndexRuralEntry> comparator = BinaryOperator.maxBy((a, b) -> 0);
    final Collector<WageIppsIndexRuralEntry, ?, NavigableMap<LocalDate, WageIppsIndexRuralEntry>>
        collector =
            Collectors.toMap(
                WageIppsIndexRuralEntry::getEffectiveDate,
                Function.identity(),
                comparator,
                TreeMap::new);

    return LookupGenerator.generateCrossReferenceToMap(
        WageIppsIndexRuralEntry::getCbsa, content, HashMap::new, collector);
  }
}
