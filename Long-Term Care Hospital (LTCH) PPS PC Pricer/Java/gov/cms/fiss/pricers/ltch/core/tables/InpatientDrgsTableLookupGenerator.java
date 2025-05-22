package gov.cms.fiss.pricers.ltch.core.tables;

import com.fasterxml.jackson.dataformat.csv.CsvParser.Feature;
import gov.cms.fiss.pricers.common.csv.CsvContentReader;
import gov.cms.fiss.pricers.common.csv.LookupGenerator;
import gov.cms.fiss.pricers.ltch.api.v1.InpatientDrgsTableEntry;
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

public class InpatientDrgsTableLookupGenerator {

  private static final String CSV_FILE_PATTERN = "/ipdrgstable-%d.csv";

  private final CsvContentReader<InpatientDrgsTableEntry> contentProvider =
      new CsvContentReader<>(InpatientDrgsTableEntry.class)
          .customizeSchema(CsvContentReader.HEADER_ROW_CUSTOMIZER)
          .customizeMapper(m -> m.enable(Feature.TRIM_SPACES));

  public Map<String, NavigableMap<LocalDate, InpatientDrgsTableEntry>> generate(int pricerYear) {
    final List<InpatientDrgsTableEntry> content =
        contentProvider.read(String.format(CSV_FILE_PATTERN, pricerYear));

    // This comparison is a no-op, as there is no chance of merge conflicts here
    final BinaryOperator<InpatientDrgsTableEntry> comparator = BinaryOperator.maxBy((a, b) -> 0);
    final Collector<InpatientDrgsTableEntry, ?, NavigableMap<LocalDate, InpatientDrgsTableEntry>>
        collector =
            Collectors.toMap(
                InpatientDrgsTableEntry::getEffectiveDate,
                Function.identity(),
                comparator,
                TreeMap::new);

    return LookupGenerator.generateCrossReferenceToMap(
        InpatientDrgsTableEntry::getDiagnosticRelatedGroup, content, HashMap::new, collector);
  }
}
