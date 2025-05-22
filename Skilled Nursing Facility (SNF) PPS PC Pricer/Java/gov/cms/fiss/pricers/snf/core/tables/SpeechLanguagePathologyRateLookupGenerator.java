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

public class SpeechLanguagePathologyRateLookupGenerator {
  private static final String CSV_FILE_PATTERN = "/slp-rates-%s.csv";

  private final CsvContentReader<SpeechLanguagePathologyRateEntry> contentProvider =
      new CsvContentReader<>(SpeechLanguagePathologyRateEntry.class)
          .customizeSchema(CsvContentReader.HEADER_ROW_CUSTOMIZER);

  public SpeechLanguagePathologyRateLookupGenerator(
      CsvIngestionConfiguration csvIngestionConfiguration) {
    if (!csvIngestionConfiguration.isValidationEnabled()) {
      contentProvider.disableValidation();
    }
  }

  /**
   * Creates a lookup table for SLP rate data for the given pricer year.
   *
   * @param pricerYear the pricer year for the claim
   * @return the populated table
   */
  public Map<String, Map<String, SpeechLanguagePathologyRateEntry>> generate(int pricerYear) {
    return generate(String.format(CSV_FILE_PATTERN, pricerYear));
  }

  /**
   * Creates a lookup table for SLP rate data with the given CSV file name. Entries are grouped by
   * region type.
   *
   * @param csvFile filename of the csv file to use
   * @return the populated table
   */
  public Map<String, Map<String, SpeechLanguagePathologyRateEntry>> generate(String csvFile) {
    final List<SpeechLanguagePathologyRateEntry> content = contentProvider.read(csvFile);
    final BinaryOperator<SpeechLanguagePathologyRateEntry> comparator =
        BinaryOperator.maxBy((a, b) -> 0);
    final Collector<
            SpeechLanguagePathologyRateEntry, ?, Map<String, SpeechLanguagePathologyRateEntry>>
        collector =
            Collectors.toMap(
                SpeechLanguagePathologyRateEntry::getGroup,
                Function.identity(),
                comparator,
                HashMap::new);

    return LookupGenerator.generateCrossReferenceToMap(
        SpeechLanguagePathologyRateEntry::getRegion, content, HashMap::new, collector);
  }
}
