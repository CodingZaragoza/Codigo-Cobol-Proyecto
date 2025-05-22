package gov.cms.fiss.pricers.fqhc.core.tables;

import gov.cms.fiss.pricers.common.csv.CsvIngestionConfiguration;
import gov.cms.fiss.pricers.fqhc.FqhcPricerConfiguration;
import gov.cms.fiss.pricers.fqhc.core.tables.GAFRateTableEntry.GAFRateKey;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;

public class DataTables {
  private final NavigableMap<LocalDate, BigDecimal> addOnRateTable;
  private final NavigableMap<LocalDate, BigDecimal> baseRateTable;
  private final NavigableMap<LocalDate, BigDecimal> iopRateGte4Table;
  private final NavigableMap<LocalDate, BigDecimal> iopRateLte3Table;
  private final Map<String, NavigableMap<LocalDate, BigDecimal>> gafRateTable;
  private final NavigableMap<LocalDate, BigDecimal> tribalRateTable;

  public DataTables(
      NavigableMap<LocalDate, BigDecimal> addOnRateTable,
      NavigableMap<LocalDate, BigDecimal> baseRateTable,
      NavigableMap<LocalDate, BigDecimal> iopRateGte4Table,
      NavigableMap<LocalDate, BigDecimal> iopRateLte3Table,
      Map<String, NavigableMap<LocalDate, BigDecimal>> gafRateTable,
      NavigableMap<LocalDate, BigDecimal> tribalRateTable) {
    this.addOnRateTable = addOnRateTable;
    this.baseRateTable = baseRateTable;
    this.iopRateGte4Table = iopRateGte4Table;
    this.iopRateLte3Table = iopRateLte3Table;
    this.gafRateTable = gafRateTable;
    this.tribalRateTable = tribalRateTable;
  }

  /**
   * Loads data tables with the given CSV ingestion configuration.
   *
   * @param pricerConfiguration the configuration to use
   * @return populated data tables
   */
  public static Map<Integer, DataTables> loadDataTables(
      FqhcPricerConfiguration pricerConfiguration) {
    final CsvIngestionConfiguration csvIngestionConfiguration =
        pricerConfiguration.getCsvIngestionConfiguration();
    final Map<Integer, DataTables> yearMap = new HashMap<>();
    final NavigableMap<LocalDate, BigDecimal> addOnRateTable =
        new AddOnRateTableEntryLookupGenerator(csvIngestionConfiguration).generate();
    final NavigableMap<LocalDate, BigDecimal> baseRateTable =
        new PaymentRateTableEntryLookupGenerator(csvIngestionConfiguration).generate("/base.csv");
    final NavigableMap<LocalDate, BigDecimal> iopRateGte4Table =
        new IopPaymentRateGte4TableEntryLookupGenerator(csvIngestionConfiguration)
            .generate("/iopRateGte4" + ".csv");
    final NavigableMap<LocalDate, BigDecimal> iopRateLte3Table =
        new IopPaymentRateLte3TableEntryLookupGenerator(csvIngestionConfiguration)
            .generate("/iopRateLte3" + ".csv");
    final NavigableMap<LocalDate, BigDecimal> tribalRateTable =
        new PaymentRateTableEntryLookupGenerator(csvIngestionConfiguration).generate("/tribal.csv");

    for (final int fiscalYear : pricerConfiguration.getSupportedYears()) {
      yearMap.put(
          fiscalYear,
          new DataTables(
              addOnRateTable,
              baseRateTable,
              iopRateGte4Table,
              iopRateLte3Table,
              new GAFRateTableEntryLookupGenerator(csvIngestionConfiguration).generate(fiscalYear),
              tribalRateTable));
    }

    return yearMap;
  }

  public BigDecimal getAddOnRate(LocalDate effectiveDate) {
    return getRate(effectiveDate, addOnRateTable);
  }

  public BigDecimal getBaseRate(LocalDate effectiveDate) {
    return getRate(effectiveDate, baseRateTable);
  }

  public BigDecimal getIopRateGte4(LocalDate effectiveDate) {
    return getRate(effectiveDate, iopRateGte4Table);
  }

  public BigDecimal getIopRateLte3(LocalDate effectiveDate) {
    return getRate(effectiveDate, iopRateLte3Table);
  }

  public BigDecimal getGafRate(GAFRateKey key) {
    final NavigableMap<LocalDate, BigDecimal> providerRates =
        gafRateTable.get(key.getCarrierLocality());

    if (providerRates == null) {
      return null;
    } else {
      return getRate(key.getServiceDate(), providerRates);
    }
  }

  public BigDecimal getTribalRate(LocalDate effectiveDate) {
    return getRate(effectiveDate, tribalRateTable);
  }

  private BigDecimal getRate(LocalDate effectiveDate, NavigableMap<LocalDate, BigDecimal> map) {
    final Entry<LocalDate, BigDecimal> floorEntry = map.floorEntry(effectiveDate);

    if (floorEntry == null) {
      return null;
    } else {
      return floorEntry.getValue();
    }
  }
}
