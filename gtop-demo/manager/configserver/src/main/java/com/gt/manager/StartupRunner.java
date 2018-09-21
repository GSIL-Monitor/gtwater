package com.gt.manager;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.config.client.ConfigClientProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StartupRunner implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(StartupRunner.class);

    @Autowired
    private ConfigClientProperties configClientProperties;

    @Autowired
    private DiscoveryClient client;

    @Override
    public void run(String... strings) throws Exception {
        log.info("CommandLineRunner.........");

        log.info("configClientProperties: " + ReflectionToStringBuilder.toString(configClientProperties));

        String serviceId = this.configClientProperties.getDiscovery().getServiceId();
        log.info("serviceId:"+serviceId);
        List<ServiceInstance> instances = this.client.getInstances(serviceId);
        if (instances.isEmpty()) {
            log.warn("No instances found of configserver (" + serviceId + ")");
        }

        log.info("CommandLineRunner End");
    }

}
