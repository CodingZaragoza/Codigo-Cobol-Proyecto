package gov.cms.fiss.pricers.ltch.core.tables;

import gov.cms.fiss.pricers.common.csv.CsvIngestionConfiguration;
import java.time.LocalDate;
import java.util.Map;
import java.util.NavigableMap;

public class IppsCbsaWageIndexLookupGenerator extends CbsaWageIndexLookupGenerator {

  private static final String CSV_FILE_PATTERN = "/cbsa-wage-index-ipps-%d.csv";

  public IppsCbsaWageIndexLookupGenerator(CsvIngestionConfiguration csvIngestionConfiguration) {
    super(csvIngestionConfiguration);
  }

  public Map<String, NavigableMap<LocalDate, CbsaWageIndexEntry>> generate(int pricerYear) {
    return generate(String.format(CSV_FILE_PATTERN, pricerYear));
  }
}
