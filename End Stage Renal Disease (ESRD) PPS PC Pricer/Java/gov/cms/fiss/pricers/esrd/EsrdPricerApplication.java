package gov.cms.fiss.pricers.esrd;

import gov.cms.fiss.pricers.common.application.OpenApiPricerApplication;
import gov.cms.fiss.pricers.common.application.contract.OpenApiCustomizer;
import gov.cms.fiss.pricers.common.application.resources.CbsaWageIndexResource;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext2020;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext2020Dot2;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext2021;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext2022;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext2023;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext2024;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext2025;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerDispatch;
import gov.cms.fiss.pricers.esrd.resources.EsrdClaimPricingResource;
import gov.cms.fiss.pricers.esrd.resources.EsrdExtractionUtil;
import io.dropwizard.setup.Environment;
import io.swagger.v3.oas.models.info.Info;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class EsrdPricerApplication extends OpenApiPricerApplication<EsrdPricerConfiguration> {

  public static void main(String[] args) throws Exception {
    new EsrdPricerApplication().run(args);
  }

  @Override
  protected void configureApplication(
      EsrdPricerConfiguration pricerConfiguration, Environment environment) {
    final EsrdClaimPricingResource v2Resource =
        new EsrdClaimPricingResource(new EsrdPricerDispatch(pricerConfiguration));

    environment.jersey().register(v2Resource);
    environment
        .jersey()
        .register(
            new CbsaWageIndexResource(pricerConfiguration, EsrdExtractionUtil.CBSA_EXTRACTOR));
  }

  @Override
  protected List<OpenApiCustomizer> getContractCustomizers() {
    final List<OpenApiCustomizer> customizers = new ArrayList<>(super.getContractCustomizers());
    customizers.add(
        (configuration, environment, oas) ->
            oas.info(new Info().title("ESRD Pricer").version("v1")));

    return customizers;
  }

  @Override
  protected Map<Integer, String> getCalculationVersions() {
    return Map.of(
        2020,
        StringUtils.join(
            new String[] {
              EsrdPricerContext2020.CALCULATION_VERSION_20_0,
              EsrdPricerContext2020Dot2.CALCULATION_VERSION_20_2
            },
            ','),
        2021,
        EsrdPricerContext2021.CALCULATION_VERSION_2021,
        2022,
        EsrdPricerContext2022.CALCULATION_VERSION_2022,
        2023,
        EsrdPricerContext2023.CALCULATION_VERSION_2023,
        2024,
        EsrdPricerContext2024.CALCULATION_VERSION_2024,
        2025,
        EsrdPricerContext2025.CALCULATION_VERSION_2025);
  }

  @Override
  public String getName() {
    return "esrd-pricer";
  }

  @Override
  protected List<Package> getResourcePackages() {
    return Collections.singletonList(EsrdClaimPricingResource.class.getPackage());
  }
}
