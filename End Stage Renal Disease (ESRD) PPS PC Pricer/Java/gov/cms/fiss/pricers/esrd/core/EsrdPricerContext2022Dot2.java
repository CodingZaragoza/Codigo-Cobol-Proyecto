package gov.cms.fiss.pricers.esrd.core;

import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.tables.DataTables;

public class EsrdPricerContext2022Dot2 extends EsrdPricerContext2022 {

  public static final String CALCULATION_VERSION_22_2 = "2022.5";

  public static final String ETC_INDICATOR_HDPA_2022_2 = "H";
  public static final String ETC_INDICATOR_HDPA_NOTHING_2022_2 = " ";
  public static final String ETC_INDICATOR_PPA_2022_2 = "P";
  public static final String ETC_INDICATOR_BOTH_HDPA_AND_PPA_2022_2 = "B";

  public EsrdPricerContext2022Dot2(
      EsrdClaimPricingRequest input, EsrdClaimPricingResponse output, DataTables dataTables) {
    super(input, output, dataTables);
  }

  @Override
  public String getCalculationVersion() {
    return CALCULATION_VERSION_22_2;
  }
}
