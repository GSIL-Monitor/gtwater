package com.gt.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;

/**
 * <p>Copyright: All Rights Reserved</p>
 * <p>Description: 集中配置 </p>
 * <p>Author:stevehan</p>
 */
@SpringBootApplication
@EnableConfigServer
@EnableEurekaClient
public class ConfigServerApplication {
	private static final Logger log = LoggerFactory.getLogger(ConfigServerApplication.class);
	
	/**
	 * <p>Description: 启动入口 </p>
	 * @param args
	 * <p>Author:steve</p>
	 */
	public static void main(String[] args) {
		/**
		 * 启动后获取配置测试
		 * curl http://localhost:8888/spring-configserver/some-dev.yml
		 */
		Class[] obj = { ConfigServerApplication.class };
		SpringApplication app = new SpringApplication(obj );
		ApplicationContext context = app.run(args);
		String profile = context.getEnvironment().getProperty("server.profile");
		String[] activeProfiles = context.getEnvironment().getActiveProfiles();
		for (String activeProfile : activeProfiles) {
			log.info("当前环境: " + profile + ", configserver配置：" + activeProfile);
		}
	}
}
