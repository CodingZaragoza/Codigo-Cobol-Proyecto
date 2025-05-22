package gov.cms.fiss.pricers.esrd.core;

import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.tables.DataTables;
import java.math.BigDecimal;

public class EsrdPricerContext2020Dot2 extends EsrdPricerContext2020 {

  public static final String CALCULATION_VERSION_20_2 = "2020.2";
  private static final BigDecimal ETC_HDPA_PERCENT_2020_2 = new BigDecimal("1.03");

  public EsrdPricerContext2020Dot2(
      EsrdClaimPricingRequest input, EsrdClaimPricingResponse output, DataTables dataTables) {
    super(input, output, dataTables);
  }

  @Override
  public String getCalculationVersion() {
    return CALCULATION_VERSION_20_2;
  }

  @Override
  public BigDecimal getEtcHdpaPercent() {
    return ETC_HDPA_PERCENT_2020_2;
  }
}
