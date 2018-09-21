package com.gt.manager;

import com.gt.manager.ApiOriginFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.DispatcherType;

/**
 * <p>Copyright: All Rights Reserved</p>
 * <p>Description: api网关 </p>
 * <p>Author:stevehan/, 16/08/25</p>
 */
@SpringCloudApplication
@EnableZuulProxy
@EnableEurekaClient
// @EnableSidecar
public class GatewayApplication {
    private static final Logger log = LoggerFactory.getLogger(GatewayApplication.class);

    @Bean
    // 调用顺序
    public AccessFilter accessFilter() {
        return new AccessFilter();
    }


    @Bean
    public FilterRegistrationBean simpleCORSFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new ApiOriginFilter());
        registration.setEnabled(true);
        registration.addUrlPatterns("/*");
        return registration;
    }
    @Bean
    public ErrorFilter errorFilter() {
        return new ErrorFilter();
    }

    /**
      *
      * attention:简单跨域就是GET，HEAD和POST请求，但是POST请求的"Content-Type"只能是application/x-www-form-urlencoded, multipart/form-data 或 text/plain
      * 反之，就是非简单跨域，此跨域有一个预检机制，说直白点，就是会发两次请求，一次OPTIONS请求，一次真正的请求
      */
//    @Bean
//    public CorsFilter corsFilter() {
//         final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//         final CorsConfiguration config = new CorsConfiguration();
//         config.setAllowCredentials(true); // 允许cookies跨域
//         config.addAllowedOrigin("*");// #允许向该服务器提交请求的URI，*表示全部允许，在SpringMVC中，如果设成*，会自动转成当前请求头中的Origin
//         config.addAllowedHeader("*");// #允许访问的头信息,*表示全部
//         config.setMaxAge(18000L);// 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
//         config.addAllowedMethod("OPTIONS");// 允许提交请求的方法，*表示全部允许
//         config.addAllowedMethod("HEAD");
//         config.addAllowedMethod("GET");// 允许Get的请求方法
//         config.addAllowedMethod("PUT");
//        config.addAllowedMethod("POST");
//        config.addAllowedMethod("DELETE");
//        config.addAllowedMethod("PATCH");
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }


    /**
     * <p>Description: 启动入口 </p>
     *
     * @param args <p>Author:jmzhang/, 16/08/26</p>
     */
    public static void main(String[] args) {
        Class[] obj = { GatewayApplication.class };
        SpringApplication app = new SpringApplication(obj);
        ApplicationContext context = app.run(args);
        String[] activeProfiles = context.getEnvironment().getActiveProfiles();
        for (String activeProfile : activeProfiles) {
            log.info("当前环境: " + activeProfile);
        }
    }
}
