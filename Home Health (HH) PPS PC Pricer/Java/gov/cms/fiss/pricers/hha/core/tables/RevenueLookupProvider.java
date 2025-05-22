package gov.cms.fiss.pricers.hha.core.tables;

import gov.cms.fiss.pricers.common.csv.CsvContentReader;
import gov.cms.fiss.pricers.common.csv.CsvIngestionConfiguration;
import gov.cms.fiss.pricers.common.csv.LookupGenerator;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class RevenueLookupProvider {
  private static final String CSV_FILE_PATTERN = "/rev-%s.csv";

  private final CsvContentReader<RevenueEntry> contentProvider =
      new CsvContentReader<>(RevenueEntry.class)
          .customizeSchema(CsvContentReader.HEADER_ROW_CUSTOMIZER);

  public RevenueLookupProvider(CsvIngestionConfiguration csvIngestionConfiguration) {
    if (!csvIngestionConfiguration.isValidationEnabled()) {
      contentProvider.disableValidation();
    }
  }

  /**
   * Creates a lookup table for Revenue Wage Index data for the given pricer year
   *
   * @return the populated table
   */
  public Map<String, NavigableMap<LocalDate, RevenueEntry>> generate(int pricerYear) {
    return generate(String.format(CSV_FILE_PATTERN, pricerYear));
  }

  /**
   * Creates a lookup table for add-on data with the given CSV file name
   *
   * @return the populated table
   */
  public Map<String, NavigableMap<LocalDate, RevenueEntry>> generate(String csvFile) {
    final List<RevenueEntry> content = contentProvider.read(csvFile);
    final BinaryOperator<RevenueEntry> comparator = BinaryOperator.maxBy(Comparator.naturalOrder());
    final Collector<RevenueEntry, ?, NavigableMap<LocalDate, RevenueEntry>> collector =
        Collectors.toMap(
            RevenueEntry::getEffectiveDate, Function.identity(), comparator, TreeMap::new);

    return LookupGenerator.generateCrossReferenceToMap(
        RevenueEntry::getCode, content, HashMap::new, collector);
  }
}
