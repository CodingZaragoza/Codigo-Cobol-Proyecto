package gov.cms.fiss.pricers.ipps;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cms.fiss.pricers.common.application.OpenApiPricerApplication;
import gov.cms.fiss.pricers.common.application.contract.OpenApiCustomizer;
import gov.cms.fiss.pricers.common.application.resources.CbsaWageIndexResource;
import gov.cms.fiss.pricers.ipps.core.Ipps2019PricerContext;
import gov.cms.fiss.pricers.ipps.core.Ipps2020PricerContext;
import gov.cms.fiss.pricers.ipps.core.Ipps2021PricerContext;
import gov.cms.fiss.pricers.ipps.core.Ipps2022PricerContext;
import gov.cms.fiss.pricers.ipps.core.Ipps2023PricerContext;
import gov.cms.fiss.pricers.ipps.core.Ipps2024PricerContext;
import gov.cms.fiss.pricers.ipps.core.Ipps2025PricerContext;
import gov.cms.fiss.pricers.ipps.core.IppsPricerDispatch;
import gov.cms.fiss.pricers.ipps.core.tables.DataTables;
import gov.cms.fiss.pricers.ipps.resources.IppsClaimPricingResource;
import gov.cms.fiss.pricers.ipps.resources.IppsDrgEntryResource;
import gov.cms.fiss.pricers.ipps.resources.IppsExtractionUtil;
import io.dropwizard.setup.Environment;
import io.swagger.v3.oas.models.info.Info;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class IppsPricerApplication extends OpenApiPricerApplication<IppsPricerConfiguration> {

  public static void main(String[] args) throws Exception {
    new IppsPricerApplication().run(args);
  }

  @Override
  public String getName() {
    return "ipps-pricer";
  }

  @Override
  protected void configureApplication(
      IppsPricerConfiguration configuration, Environment environment) {
    DataTables.loadDataTables(configuration);

    final IppsClaimPricingResource v2Resource =
        new IppsClaimPricingResource(new IppsPricerDispatch(configuration));

    environment.jersey().register(v2Resource);
    environment
        .jersey()
        .register(new CbsaWageIndexResource(configuration, IppsExtractionUtil.CBSA_EXTRACTOR));
    environment.jersey().register(new IppsDrgEntryResource(configuration));
  }

  @Override
  protected void customizeJacksonMapper(ObjectMapper objectMapper) {
    super.customizeJacksonMapper(objectMapper);

    objectMapper.setSerializationInclusion(Include.ALWAYS);
  }

  @Override
  protected List<OpenApiCustomizer> getContractCustomizers() {
    final List<OpenApiCustomizer> customizers = new ArrayList<>(super.getContractCustomizers());
    customizers.add(
        (configuration, environment, oas) ->
            oas.info(new Info().title("IPPS Pricer").version("v1")));

    return customizers;
  }

  @Override
  protected List<Package> getResourcePackages() {
    return Collections.singletonList(IppsClaimPricingResource.class.getPackage());
  }

  @Override
  protected Map<Integer, String> getCalculationVersions() {
    return Map.of(
        2019,
        Ipps2019PricerContext.CALCULATION_VERSION,
        2020,
        Ipps2020PricerContext.CALCULATION_VERSION,
        2021,
        Ipps2021PricerContext.CALCULATION_VERSION,
        2022,
        Ipps2022PricerContext.CALCULATION_VERSION,
        2023,
        Ipps2023PricerContext.CALCULATION_VERSION,
        2024,
        Ipps2024PricerContext.CALCULATION_VERSION,
        2025,
        Ipps2025PricerContext.CALCULATION_VERSION);
  }
}
