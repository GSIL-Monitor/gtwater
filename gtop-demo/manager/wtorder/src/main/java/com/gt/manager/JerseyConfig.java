package com.gt.manager;

import com.gt.manager.order.resource.order.OrderResources;
import com.gt.manager.order.resource.send.SendController;
import com.gt.manager.order.resource.setMeal.SetMealResources;
import com.gt.manager.order.resource.wtSend.WtSendResources;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.glassfish.jersey.server.wadl.internal.WadlResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;



@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        // Register endpoints, providers, ...
        this.registerEndpoints();
    }

    @PostConstruct
    public void init() {
        // Register components where DI is needed
        this.configureSwagger();
    }

    /**
     *spring-boot升级到1.4+ 以后，spring-boot专门用来打包的maven plugin对目录结构进行了改变，如果使用了jersey来代替springmvc作为restful的方案，
     *然后并且自己在生成的JerseyConfig类里面，通过 package()函数来指定要扫描的包的路径的话，会导致找不到对应的路径，
     *从而导致了出现java.io.FileNotFoundException 的异常,所以采用注册api类的方式把接口注入,否则会出现404找不到的异常
     *
     */
    private void registerEndpoints() {
        // springBoot1.4以下版本使用注册方式
        // packages("com.weichuang.ecommerce");
        // springBoot1.4以上版本使用注册方式--开始
        this.register(RequestContextFilter.class);

        this.register(OrderResources.class);
        this.register(SendController.class);
        this.register(WtSendResources.class);
        this.register(SetMealResources.class);
        // Access through /<Jersey's servlet path>/application.wadl
        this.register(WadlResource.class);

    }

    private void configureSwagger() {
        // Available at localhost:port/swagger.json
        this.register(ApiListingResource.class);
        this.register(SwaggerSerializers.class);
        BeanConfig config = new BeanConfig();
        config.setConfigId("springboot-jersey-swagger");
        config.setTitle("Spring Boot + Jersey + Swagger");
        config.setVersion("v1");
        // config.setContact("Orlando L Otero");
        config.setSchemes(new String[] { "http" });
        config.setBasePath("/api");
        //config.setResourcePackage("com.gt.manager");
        config.setPrettyPrint(true);
        config.setScan(true);
    }
}
