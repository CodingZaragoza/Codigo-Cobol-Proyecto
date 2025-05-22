package gov.cms.fiss.pricers.irf;

import static java.util.Map.entry;

import gov.cms.fiss.pricers.common.application.OpenApiPricerApplication;
import gov.cms.fiss.pricers.common.application.contract.OpenApiCustomizer;
import gov.cms.fiss.pricers.common.application.resources.CbsaWageIndexResource;
import gov.cms.fiss.pricers.irf.core.Irf2015PricerContext;
import gov.cms.fiss.pricers.irf.core.Irf2016PricerContext;
import gov.cms.fiss.pricers.irf.core.Irf2017PricerContext;
import gov.cms.fiss.pricers.irf.core.Irf2018PricerContext;
import gov.cms.fiss.pricers.irf.core.Irf2019PricerContext;
import gov.cms.fiss.pricers.irf.core.Irf2020PricerContext;
import gov.cms.fiss.pricers.irf.core.Irf2021PricerContext;
import gov.cms.fiss.pricers.irf.core.Irf2022PricerContext;
import gov.cms.fiss.pricers.irf.core.Irf2023PricerContext;
import gov.cms.fiss.pricers.irf.core.Irf2024PricerContext;
import gov.cms.fiss.pricers.irf.core.Irf2025PricerContext;
import gov.cms.fiss.pricers.irf.core.IrfPricerDispatch;
import gov.cms.fiss.pricers.irf.resources.IrfClaimPricingResource;
import gov.cms.fiss.pricers.irf.resources.IrfExtractionUtil;
import io.dropwizard.setup.Environment;
import io.swagger.v3.oas.models.info.Info;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class IrfPricerApplication extends OpenApiPricerApplication<IrfPricerConfiguration> {

  public static void main(String[] args) throws Exception {
    new IrfPricerApplication().run(args);
  }

  @Override
  protected void configureApplication(
      IrfPricerConfiguration configuration, Environment environment) {
    final IrfClaimPricingResource v2Resource =
        new IrfClaimPricingResource(new IrfPricerDispatch(configuration));

    environment.jersey().register(v2Resource);
    environment
        .jersey()
        .register(new CbsaWageIndexResource(configuration, IrfExtractionUtil.CBSA_EXTRACTOR));
  }

  @Override
  protected List<OpenApiCustomizer> getContractCustomizers() {
    final List<OpenApiCustomizer> customizers = new ArrayList<>(super.getContractCustomizers());
    customizers.add(
        (configuration, environment, oas) ->
            oas.info(new Info().title("IRF Pricer").version("v1")));

    return customizers;
  }

  @Override
  public String getName() {
    return "irf-pricer";
  }

  @Override
  protected List<Package> getResourcePackages() {
    return Collections.singletonList(IrfClaimPricingResource.class.getPackage());
  }

  @Override
  protected Map<Integer, String> getCalculationVersions() {

    return Map.ofEntries(
        entry(2015, Irf2015PricerContext.CALCULATION_VERSION_2015),
        entry(2016, Irf2016PricerContext.CALCULATION_VERSION_2016),
        entry(2017, Irf2017PricerContext.CALCULATION_VERSION_2017),
        entry(2018, Irf2018PricerContext.CALCULATION_VERSION_2018),
        entry(2019, Irf2019PricerContext.CALCULATION_VERSION_2019),
        entry(2020, Irf2020PricerContext.CALCULATION_VERSION_2020),
        entry(2021, Irf2021PricerContext.CALCULATION_VERSION_2021),
        entry(2022, Irf2022PricerContext.CALCULATION_VERSION_2022),
        entry(2023, Irf2023PricerContext.CALCULATION_VERSION_2023),
        entry(2024, Irf2024PricerContext.CALCULATION_VERSION_2024),
        entry(2025, Irf2025PricerContext.CALCULATION_VERSION_2025));
  }
}
