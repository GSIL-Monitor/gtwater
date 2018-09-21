package com.gt.manager;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.robert.vesta.service.factory.IdServiceFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.util.IntrospectorCleanupListener;

import javax.servlet.DispatcherType;
import java.lang.reflect.Method;
import java.nio.charset.Charset;

/**
 */
@SpringBootApplication
@EnableCaching
@SpringCloudApplication
@EnableDiscoveryClient
@EnableConfigurationProperties
@EnableFeignClients
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement(proxyTargetClass = true)
@EnableCircuitBreaker
@EnableScheduling


public class OrderApplication {
    private static final Logger log = LoggerFactory.getLogger(OrderApplication.class);

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
    public ServletListenerRegistrationBean<IntrospectorCleanupListener> introspectorCleanupListener() {
        ServletListenerRegistrationBean<IntrospectorCleanupListener> servletListenerRegistrationBean = new ServletListenerRegistrationBean<IntrospectorCleanupListener>(
                new IntrospectorCleanupListener());
        servletListenerRegistrationBean.setOrder(0);
        return servletListenerRegistrationBean;
    }

    // 使用@Cacheable缓存注解的自定义key生成方法,为了防止key重复
    @Bean
    public KeyGenerator customKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                StringBuilder sb = new StringBuilder();
                sb.append(o.getClass().getName());
                sb.append(method.getName());
                for (Object obj : objects) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    @Bean//使用@Bean注入fastJsonHttpMessageConvert
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        //1.需要定义一个Convert转换消息的对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        //2.添加fastjson的配置信息，比如是否要格式化返回的json数据
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastJsonConfig.setCharset(Charset.forName("UTF-8"));
        //3.在convert中添加配置信息
        fastConverter.setFastJsonConfig(fastJsonConfig);

        HttpMessageConverter<?> converter = fastConverter;
        return new HttpMessageConverters(converter);
    }

    @Bean(name = "idService")
    public IdServiceFactoryBean getIdService(){
        IdServiceFactoryBean idServiceFactoryBean = new IdServiceFactoryBean();
        idServiceFactoryBean.setProviderType(IdServiceFactoryBean.Type.PROPERTY);
        idServiceFactoryBean.setMachineId(1);
        idServiceFactoryBean.init();
        return idServiceFactoryBean;
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("ValidationMessages");
        // http://blog.csdn.net/a491057947/article/details/46724707
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setProviderClass(org.hibernate.validator.HibernateValidator.class);
        validator.setValidationMessageSource(messageSource);
        return validator;
    }

    public static void main(String[] args) {
        Object[] obj = { OrderApplication.class };
        SpringApplication app = new SpringApplication(obj);
       // app.setAddCommandLineProperties(false); //屏蔽命令行参数
        app.setWebEnvironment(true);
        ApplicationContext context = app.run(args);
        String[] activeProfiles = context.getEnvironment().getActiveProfiles();
        for (String activeProfile : activeProfiles) {
          log.info("spring boot使用环境为: " + activeProfile);
        }
    }
}
