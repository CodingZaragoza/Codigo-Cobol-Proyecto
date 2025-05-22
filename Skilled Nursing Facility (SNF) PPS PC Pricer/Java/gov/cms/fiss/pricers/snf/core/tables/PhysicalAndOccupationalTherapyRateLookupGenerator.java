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

public class PhysicalAndOccupationalTherapyRateLookupGenerator {
  private static final String CSV_FILE_PATTERN = "/ptot-rates-%s.csv";

  private final CsvContentReader<PhysicalAndOccupationalTherapyRateEntry> contentProvider =
      new CsvContentReader<>(PhysicalAndOccupationalTherapyRateEntry.class)
          .customizeSchema(CsvContentReader.HEADER_ROW_CUSTOMIZER);

  public PhysicalAndOccupationalTherapyRateLookupGenerator(
      CsvIngestionConfiguration csvIngestionConfiguration) {
    if (!csvIngestionConfiguration.isValidationEnabled()) {
      contentProvider.disableValidation();
    }
  }

  /**
   * Creates a lookup table for PT/OT rate data for the given pricer year.
   *
   * @param pricerYear the pricer year for the claim
   * @return the populated table
   */
  public Map<String, Map<String, PhysicalAndOccupationalTherapyRateEntry>> generate(
      int pricerYear) {
    return generate(String.format(CSV_FILE_PATTERN, pricerYear));
  }

  /**
   * Creates a lookup table for PT/OT rate data with the given CSV file name. Entries are grouped by
   * region type.
   *
   * @param csvFile filename of the csv file to use
   * @return the populated table
   */
  public Map<String, Map<String, PhysicalAndOccupationalTherapyRateEntry>> generate(
      String csvFile) {
    final List<PhysicalAndOccupationalTherapyRateEntry> content = contentProvider.read(csvFile);
    final BinaryOperator<PhysicalAndOccupationalTherapyRateEntry> comparator =
        BinaryOperator.maxBy((a, b) -> 0);
    final Collector<
            PhysicalAndOccupationalTherapyRateEntry,
            ?,
            Map<String, PhysicalAndOccupationalTherapyRateEntry>>
        collector =
            Collectors.toMap(
                PhysicalAndOccupationalTherapyRateEntry::getGroup,
                Function.identity(),
                comparator,
                HashMap::new);

    return LookupGenerator.generateCrossReferenceToMap(
        PhysicalAndOccupationalTherapyRateEntry::getRegion, content, HashMap::new, collector);
  }
}
