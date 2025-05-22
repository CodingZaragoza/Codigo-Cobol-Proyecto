package gov.cms.fiss.pricers.hha.core.rules.final_calculation;

import gov.cms.fiss.pricers.hha.api.v2.RevenueLineData;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CalculateAddOnVisit2022 extends CalculateAddOnVisit {

  /**
   * Returns the revenue lines subject to an add-on amount.
   *
   * @param revenueData input revenue data
   * @return list of revenue lines subject to an add-on amount
   */
  @Override
  protected List<RevenueLineData> getAddOnRevenueData(List<RevenueLineData> revenueData) {
    return Stream.of(
            revenueData.get(PHYSICAL_THERAPY),
            revenueData.get(OCCUPATIONAL_THERAPY),
            revenueData.get(SPEECH_LANGUAGE_PATHOLOGY),
            revenueData.get(SKILLED_NURSING))
        .collect(Collectors.toList());
  }
}
