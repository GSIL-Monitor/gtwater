package com.gt.manager;


import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class JerseyResourceConfig extends ResourceConfig {
    private static final Logger log = LoggerFactory.getLogger(JerseyResourceConfig.class);

    public JerseyResourceConfig() {
        super();
        log.info("加载jersey配置");
        // register(LoggingFilter.class);

        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        property(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true);

        register(GsonProvider.class);
        // register(JerseyLogFilter.class, 0);
        register(RequestContextFilter.class);
        register(ExceptionMapperSupport.class);

        //packages("io.swagger.jaxrs.listing");
        packages("com.gt.manager");

        register(JacksonFeature.class);
        // swagger
        Set<Class<?>> resources = new HashSet<>();
//        resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
//        resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
        registerClasses(resources);
    }
}
