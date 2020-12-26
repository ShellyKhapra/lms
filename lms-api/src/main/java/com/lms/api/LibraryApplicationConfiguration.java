package com.lms.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.jetty.ConnectorFactory;
import io.dropwizard.jetty.HttpConnectorFactory;
import io.dropwizard.jetty.HttpsConnectorFactory;
import io.dropwizard.server.AbstractServerFactory;
import io.dropwizard.server.DefaultServerFactory;
import io.dropwizard.server.ServerFactory;
import io.dropwizard.server.SimpleServerFactory;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LibraryApplicationConfiguration extends Configuration {

    private static final String HTTP = "http";
    private static final String HTTPS = "https";
    private static final String VALIDATE_OAUTH2_TOKEN_URL = "/users/current";

//    @NotEmpty
    private String authEndpoint;

    @JsonProperty(
            value = "corsAllowedOrigins",
            required = false,
            defaultValue = ""
    )
    private String corsAllowedOrigins;

    @NotEmpty
    @JsonProperty
    private String serviceName;

    @Valid
    @NotNull
    protected final SwaggerBundleConfiguration swaggerBundleConfiguration;

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration getSwaggerBundleConfiguration() {
        return this.swaggerBundleConfiguration;
    }

    public LibraryApplicationConfiguration() {
        swaggerBundleConfiguration = new SwaggerBundleConfiguration();
        swaggerBundleConfiguration.setResourcePackage(getResourcePackage());
        this.serviceName = "lms";
        this.corsAllowedOrigins = "";
    }

    public String getUriPrefix() {
        return "/";
    }

    public boolean enableSwagger() {
        return true;
    }

    public String getApiVersion() {
        return "1.0";
    }

    public String getSwaggerTemplateUrl() {
        return "index.ftl";
    }

    public String getGenerateTokenEndPoint() {
        return this.authEndpoint;
    }

    public String getValidateTokenEndPoint() {
        return this.authEndpoint + "/users/current";
    }

    public String getCORSAllowedOrigins() {
        return this.corsAllowedOrigins;
    }

    public String[] getSchemes() {
        String[] output = new String[0];
        ServerFactory serverFactory = this.getServerFactory();
        if (serverFactory instanceof SimpleServerFactory) {
            ConnectorFactory connectorFactory = ((SimpleServerFactory)serverFactory).getConnector();
            if (isHTTP(connectorFactory)) {
                output = new String[]{"http"};
            } else if (isHTTPS(connectorFactory)) {
                output = new String[]{"https"};
            }
        } else if (serverFactory instanceof DefaultServerFactory) {
            Set<String> connectors = new HashSet();
            List<ConnectorFactory> factories = ((DefaultServerFactory)serverFactory).getApplicationConnectors();
            if (factories != null && !factories.isEmpty()) {
                factories.forEach((f) -> {
                    if (isHTTP(f)) {
                        connectors.add("http");
                    } else if (isHTTPS(f)) {
                        connectors.add("https");
                    }

                });
                output = (String[])connectors.toArray(new String[connectors.size()]);
            }
        }

        return output;
    }

    public String getValidatorUrl() {
        return "http://online.swagger.io/validator?url=";
    }


    private static boolean isHTTP(ConnectorFactory connectorFactory) {
        return connectorFactory instanceof HttpConnectorFactory;
    }

    private static boolean isHTTPS(ConnectorFactory connectorFactory) {
        return connectorFactory instanceof HttpsConnectorFactory;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getResourcePackage() {
        return "com.lms.api.resources";
    }

}
