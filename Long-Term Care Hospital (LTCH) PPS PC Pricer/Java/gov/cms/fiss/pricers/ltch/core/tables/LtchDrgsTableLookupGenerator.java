package gov.cms.fiss.pricers.ltch.core.tables;

import gov.cms.fiss.pricers.common.csv.CsvContentReader;
import gov.cms.fiss.pricers.common.csv.LookupGenerator;
import gov.cms.fiss.pricers.ltch.api.v1.LtchDrgsTableEntry;
import java.util.List;
import java.util.Map;

public class LtchDrgsTableLookupGenerator {

  private static final String CSV_FILE_PATTERN = "/ltdrgstable-%d.csv";

  private final CsvContentReader<LtchDrgsTableEntry> contentProvider =
      new CsvContentReader<>(LtchDrgsTableEntry.class)
          .customizeSchema(CsvContentReader.HEADER_ROW_CUSTOMIZER);

  public Map<String, LtchDrgsTableEntry> generate(int pricerYear) {
    final List<LtchDrgsTableEntry> content =
        contentProvider.read(String.format(CSV_FILE_PATTERN, pricerYear));

    return LookupGenerator.generateMap(LtchDrgsTableEntry::getDiagnosticRelatedGroup, content);
  }
}
