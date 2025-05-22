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

public class NursingRateLookupGenerator {
  private static final String CSV_FILE_PATTERN = "/nursing-rates-%s.csv";

  private final CsvContentReader<NursingRateEntry> contentProvider =
      new CsvContentReader<>(NursingRateEntry.class)
          .customizeSchema(CsvContentReader.HEADER_ROW_CUSTOMIZER);

  public NursingRateLookupGenerator(CsvIngestionConfiguration csvIngestionConfiguration) {
    if (!csvIngestionConfiguration.isValidationEnabled()) {
      contentProvider.disableValidation();
    }
  }

  /**
   * Creates a lookup table for Nursing rate data for the given pricer year.
   *
   * @param pricerYear the pricer year for the claim
   * @return the populated table
   */
  public Map<String, Map<String, NursingRateEntry>> generate(int pricerYear) {
    return generate(String.format(CSV_FILE_PATTERN, pricerYear));
  }

  /**
   * Creates a lookup table for Nursing rate data with the given CSV file name. Entries are grouped
   * by region type.
   *
   * @param csvFile filename of the csv file to use
   * @return the populated table
   */
  public Map<String, Map<String, NursingRateEntry>> generate(String csvFile) {
    final List<NursingRateEntry> content = contentProvider.read(csvFile);
    final BinaryOperator<NursingRateEntry> comparator = BinaryOperator.maxBy((a, b) -> 0);
    final Collector<NursingRateEntry, ?, Map<String, NursingRateEntry>> collector =
        Collectors.toMap(NursingRateEntry::getGroup, Function.identity(), comparator, HashMap::new);

    return LookupGenerator.generateCrossReferenceToMap(
        NursingRateEntry::getRegion, content, HashMap::new, collector);
  }
}
