package com.lms.api.filters;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class SwaggerToOpenApiFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext ctx) throws IOException {
        String path = ctx.getUriInfo().getPath();
        try {
            if ("swagger.json".equals(path))
                ctx.setRequestUri(new URI("/lms/openapi.json"));
            if ("swagger.yaml".equals(path))
                ctx.setRequestUri(new URI("/lms/openapi.yaml"));
        } catch (URISyntaxException ex) {
            throw new IOException(ex.getMessage());
        }
    }
}

