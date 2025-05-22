package gov.cms.fiss.pricers.snf;

import static java.util.Map.entry;

import gov.cms.fiss.pricers.common.application.OpenApiPricerApplication;
import gov.cms.fiss.pricers.common.application.contract.OpenApiCustomizer;
import gov.cms.fiss.pricers.common.application.resources.CbsaWageIndexResource;
import gov.cms.fiss.pricers.snf.core.Snf2020PricerContext;
import gov.cms.fiss.pricers.snf.core.Snf2021PricerContext;
import gov.cms.fiss.pricers.snf.core.Snf2022PricerContext;
import gov.cms.fiss.pricers.snf.core.Snf2023PricerContext;
import gov.cms.fiss.pricers.snf.core.Snf2024PricerContext;
import gov.cms.fiss.pricers.snf.core.Snf2025PricerContext;
import gov.cms.fiss.pricers.snf.core.SnfPricerDispatch;
import gov.cms.fiss.pricers.snf.resources.SnfClaimPricingResource;
import gov.cms.fiss.pricers.snf.resources.SnfExtractionUtil;
import io.dropwizard.setup.Environment;
import io.swagger.v3.oas.models.info.Info;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SnfPricerApplication extends OpenApiPricerApplication<SnfPricerConfiguration> {
  public static void main(String[] args) throws Exception {
    new SnfPricerApplication().run(args);
  }

  @Override
  protected void configureApplication(
      SnfPricerConfiguration configuration, Environment environment) {
    final SnfClaimPricingResource v2Resource =
        new SnfClaimPricingResource(new SnfPricerDispatch(configuration));

    // Add the v2 resource
    environment.jersey().register(v2Resource);

    environment
        .jersey()
        .register(new CbsaWageIndexResource(configuration, SnfExtractionUtil.CBSA_EXTRACTOR));
  }

  @Override
  protected List<OpenApiCustomizer> getContractCustomizers() {
    final List<OpenApiCustomizer> customizers = new ArrayList<>(super.getContractCustomizers());
    customizers.add(
        (configuration, environment, oas) ->
            oas.info(new Info().title("SNF Pricer").version("v1")));

    return customizers;
  }

  @Override
  protected Map<Integer, String> getCalculationVersions() {
    return Map.ofEntries(
        entry(2020, Snf2020PricerContext.CALCULATION_VERSION_2020),
        entry(2021, Snf2021PricerContext.CALCULATION_VERSION_2021),
        entry(2022, Snf2022PricerContext.CALCULATION_VERSION_2022),
        entry(2023, Snf2023PricerContext.CALCULATION_VERSION_2023),
        entry(2024, Snf2024PricerContext.CALCULATION_VERSION_2024),
        entry(2025, Snf2025PricerContext.CALCULATION_VERSION_2025));
  }

  @Override
  public String getName() {
    return "snf-pricer";
  }

  @Override
  protected List<Package> getResourcePackages() {
    return Collections.singletonList(SnfClaimPricingResource.class.getPackage());
  }
}
