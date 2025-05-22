package gov.cms.fiss.pricers.hha.core.tables;

import gov.cms.fiss.pricers.common.csv.CsvContentReader;
import gov.cms.fiss.pricers.common.csv.CsvIngestionConfiguration;
import gov.cms.fiss.pricers.common.csv.LookupGenerator;
import java.util.List;
import java.util.Map;

/** Rural add-on by state county code. */
public class StateCountyCodeLookupGenerator {
  private final CsvContentReader<StateCountyCodeEntry> contentProvider =
      new CsvContentReader<>(StateCountyCodeEntry.class)
          .customizeSchema(CsvContentReader.HEADER_ROW_CUSTOMIZER);

  public StateCountyCodeLookupGenerator(CsvIngestionConfiguration csvIngestionConfiguration) {
    if (!csvIngestionConfiguration.isValidationEnabled()) {
      contentProvider.disableValidation();
    }
  }

  /**
   * Creates a lookup table for add-on data with the given CSV file name.
   *
   * @return the populated table
   */
  public Map<String, StateCountyCodeEntry> generate(String csvFile) {
    final List<StateCountyCodeEntry> content = contentProvider.read(csvFile);

    return LookupGenerator.generateMap(StateCountyCodeEntry::getCode, content);
  }
}
