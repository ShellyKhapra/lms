package com.lms.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

public class LibraryApplicationConfiguration extends Configuration {

    public LibraryApplicationConfiguration() {

    }

    @JsonProperty(value = "corsAllowedOrigins", required = false, defaultValue = "")

    public boolean enableSwagger() {
        return false;
    }

    public String getServiceName(){
        return  "lms";
    }

    public String getResourcePackage() {
        return "com.lms.api.resources";
    }

}
