package com.lms.api;

import com.codahale.metrics.SharedMetricRegistries;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.Sets;
import com.lms.api.filters.SwaggerToOpenApiFilter;
import com.lms.api.spring.SpringBundle;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.jersey.jackson.JsonProcessingExceptionMapper;
import io.dropwizard.lifecycle.setup.LifecycleEnvironment;
import io.dropwizard.server.DefaultServerFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import io.swagger.v3.jaxrs2.integration.resources.BaseOpenApiResource;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class LibraryApplication extends Application<LibraryApplicationConfiguration> {

    private static final Logger LOG = LoggerFactory.getLogger(LibraryApplication.class);

    private String serviceName = "";

    public static void main(String[] args) throws Exception {
        final String version = LibraryApplication.class.getPackage().getImplementationVersion();
        LOG.info("Library Management version " + version);
        new LibraryApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<LibraryApplicationConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        new ResourceConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));

        // swagger.yaml
        bootstrap.addBundle(new AssetsBundle("/generated", "/generated"));
        try {
            bootstrap.addBundle(new SpringBundle<>(getSpringApplicationContext(), true, true));
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }

        super.initialize(bootstrap);

        bootstrap.addBundle(new SwaggerBundle<LibraryApplicationConfiguration>() {
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(LibraryApplicationConfiguration configuration) {
                return configuration.getSwaggerBundleConfiguration();
            }
        });
    }

    @Override
    public void run(LibraryApplicationConfiguration libraryApplicationConfiguration, Environment environment) throws Exception {
//        environment.getApplicationContext().setErrorHandler(new UnbrandedErrorHandler());
//        environment.getAdminContext().setErrorHandler(new UnbrandedErrorHandler());
        environment.getObjectMapper().registerModule(new JavaTimeModule());
        environment
                .jersey()
                .register(new JsonProcessingExceptionMapper()); // make it easier to debug "400 Bad Request"

//        environment.jersey().register(new SPMExceptionMapper());

        environment.jersey().register(openApi(libraryApplicationConfiguration));
        environment.jersey().register(SwaggerToOpenApiFilter.class);

        runApp(libraryApplicationConfiguration, environment);
    }

    private void runApp(LibraryApplicationConfiguration libraryApplicationConfiguration, Environment environment){
        LOG.info("Initializing service...");
        SharedMetricRegistries.add("default", environment.metrics());
//        CollectorRegistry.defaultRegistry.register(new DropwizardExports(environment.metrics()));
//        DefaultExports.initialize();
//        this.enableCORSFilter(rmsApiConfiguration, environment);
//        this.enableRewriteUrlFilter(environment);
        environment.getObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true);
        environment.getObjectMapper().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//        environment.jersey().register(MultiPartFeature.class);

//        environment.jersey().getResourceConfig().register(new RequestFilter());
//        environment.jersey().getResourceConfig().register(new ResponseFilter());
//        environment.jersey().getResourceConfig().register(new LoggingFilter());

        environment.jersey().enable("jersey.config.beanValidation.enableOutputValidationErrorEntity.server");
//        environment.getObjectMapper().registerModule(new DefaultScalaModule());
//        this.registerAuthFeatures(libraryApplicationConfiguration, environment);
        ((DefaultServerFactory)libraryApplicationConfiguration.getServerFactory()).setRegisterDefaultExceptionMappers(false);

//        this.addResources(libraryApplicationConfiguration, environment);
//        environment.jersey().getResourceConfig().register(ApiExceptionMapper.class);

        this.serviceName = libraryApplicationConfiguration.getServiceName();
//        LifecycleEnvironment var10000 = environment.lifecycle();
//        AtomicReference var10001 = this.server;
//        var10000.addServerLifecycleListener(var10001::set);

        LOG.info("REST Service: {} started; listening at port 8080.", this.serviceName);
    }

    private AnnotationConfigApplicationContext getSpringApplicationContext() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.lms.api", "com.lms.dal");
        return context;
    }

    private BaseOpenApiResource openApi(LibraryApplicationConfiguration apiConfiguration) {
        OpenAPI oas = new OpenAPI();
        SwaggerConfiguration config =
                new SwaggerConfiguration()
                        .openAPI(oas)
                        .prettyPrint(true)
                        .ignoredRoutes(Arrays.asList("/swagger", "/swagger.{type}"))
                        .resourcePackages(Sets.newHashSet(apiConfiguration.getResourcePackage()));
        return new OpenApiResource().openApiConfiguration(config);
    }
}
