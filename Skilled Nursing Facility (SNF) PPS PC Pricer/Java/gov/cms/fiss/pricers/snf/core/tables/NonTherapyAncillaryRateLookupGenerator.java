package gov.cms.fiss.pricers.snf.core.tables;

import gov.cms.fiss.pricers.common.csv.CsvContentReader;
import gov.cms.fiss.pricers.common.csv.CsvIngestionConfiguration;
import gov.cms.fiss.pricers.common.csv.LookupGenerator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class NonTherapyAncillaryRateLookupGenerator {
  private static final String CSV_FILE_PATTERN = "/nta-rates-%s.csv";

  private final CsvContentReader<NonTherapyAncillaryRateEntry> contentProvider =
      new CsvContentReader<>(NonTherapyAncillaryRateEntry.class)
          .customizeSchema(CsvContentReader.HEADER_ROW_CUSTOMIZER);

  public NonTherapyAncillaryRateLookupGenerator(
      CsvIngestionConfiguration csvIngestionConfiguration) {
    if (!csvIngestionConfiguration.isValidationEnabled()) {
      contentProvider.disableValidation();
    }
  }

  /**
   * Creates a lookup table for NTA rate data for the given pricer year.
   *
   * @param pricerYear the pricer year for the claim
   * @return the populated table
   */
  public Map<String, Map<String, NonTherapyAncillaryRateEntry>> generate(int pricerYear) {
    return generate(String.format(CSV_FILE_PATTERN, pricerYear));
  }

  /**
   * Creates a lookup table for NTA rate data with the given CSV file name. Entries are grouped by
   * region type.
   *
   * @param csvFile filename of the csv file to use
   * @return the populated table
   */
  public Map<String, Map<String, NonTherapyAncillaryRateEntry>> generate(String csvFile) {
    final List<NonTherapyAncillaryRateEntry> content = contentProvider.read(csvFile);
    final BinaryOperator<NonTherapyAncillaryRateEntry> comparator =
        BinaryOperator.maxBy((a, b) -> 0);
    final Collector<NonTherapyAncillaryRateEntry, ?, Map<String, NonTherapyAncillaryRateEntry>>
        collector =
            Collectors.toMap(
                NonTherapyAncillaryRateEntry::getGroup,
                Function.identity(),
                comparator,
                HashMap::new);

    return LookupGenerator.generateCrossReferenceToMap(
        NonTherapyAncillaryRateEntry::getRegion, content, HashMap::new, collector);
  }
}
