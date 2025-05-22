package gov.cms.fiss.pricers.hospice;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cms.fiss.pricers.common.application.OpenApiPricerApplication;
import gov.cms.fiss.pricers.common.application.contract.OpenApiCustomizer;
import gov.cms.fiss.pricers.common.application.resources.CbsaWageIndexResource;
import gov.cms.fiss.pricers.hospice.core.Hospice2020PricerContext;
import gov.cms.fiss.pricers.hospice.core.Hospice2021PricerContext;
import gov.cms.fiss.pricers.hospice.core.Hospice2022PricerContext;
import gov.cms.fiss.pricers.hospice.core.Hospice2023PricerContext;
import gov.cms.fiss.pricers.hospice.core.Hospice2024PricerContext;
import gov.cms.fiss.pricers.hospice.core.Hospice2025PricerContext;
import gov.cms.fiss.pricers.hospice.core.HospicePricerDispatch;
import gov.cms.fiss.pricers.hospice.core.tables.DataTables;
import gov.cms.fiss.pricers.hospice.resources.HospiceClaimPricingResource;
import gov.cms.fiss.pricers.hospice.resources.HospiceExtractionUtil;
import io.dropwizard.setup.Environment;
import io.swagger.v3.oas.models.info.Info;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HospicePricerApplication extends OpenApiPricerApplication<HospicePricerConfiguration> {

  public static void main(String[] args) throws Exception {
    new HospicePricerApplication().run(args);
  }

  @Override
  protected void configureApplication(
      HospicePricerConfiguration configuration, Environment environment) {
    DataTables.loadDataTables(configuration);

    final HospiceClaimPricingResource v2Resource =
        new HospiceClaimPricingResource(new HospicePricerDispatch(configuration));

    environment.jersey().register(v2Resource);
    environment
        .jersey()
        .register(new CbsaWageIndexResource(configuration, HospiceExtractionUtil.CBSA_EXTRACTOR));
  }

  @Override
  protected List<OpenApiCustomizer> getContractCustomizers() {
    final List<OpenApiCustomizer> customizers = new ArrayList<>(super.getContractCustomizers());
    customizers.add(
        (configuration, environment, oas) ->
            oas.info(new Info().title("Hospice Pricer").version("v1")));

    return customizers;
  }

  @Override
  protected void customizeJacksonMapper(ObjectMapper objectMapper) {
    super.customizeJacksonMapper(objectMapper);

    objectMapper.setSerializationInclusion(Include.NON_NULL);
  }

  @Override
  public String getName() {
    return "hospice-pricer";
  }

  @Override
  protected List<Package> getResourcePackages() {
    return Collections.singletonList(HospiceClaimPricingResource.class.getPackage());
  }

  // 08/18/2021 - CLG - Removed for 2022 Release
  // @Override
  // protected Map<Integer, String> getCalculationVersions() {
  //  return Map.of(2020, Hospice2020PricerContext.CALCULATION_VERSION_2020);

  // 08/18/2021 - CLG - Added for 2022 Release
  @Override
  protected Map<Integer, String> getCalculationVersions() {
    return Map.of(
        2020,
        Hospice2020PricerContext.CALCULATION_VERSION_2020,
        2021,
        Hospice2021PricerContext.CALCULATION_VERSION_2021,
        2022,
        Hospice2022PricerContext.CALCULATION_VERSION_2022,
        2023,
        Hospice2023PricerContext.CALCULATION_VERSION_2023,
        2024,
        Hospice2024PricerContext.CALCULATION_VERSION_2024,
        2025,
        Hospice2025PricerContext.CALCULATION_VERSION_2025);
  }
}
