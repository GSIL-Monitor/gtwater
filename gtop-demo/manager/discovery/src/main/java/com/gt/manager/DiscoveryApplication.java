package com.gt.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ApplicationContext;

/**
 * <p>Copyright: All Rights Reserved</p>
 * <p>Description: 服务发现 </p>
 * <p>Author:hansteve </p>
 */

@SpringBootApplication
@EnableEurekaServer
@EnableDiscoveryClient
public class DiscoveryApplication {
	private static final Logger log = LoggerFactory.getLogger(DiscoveryApplication.class);
	
	/**
	 * <p>Description: 启动入口 </p>
	 * @param args
	 * <p>Author: hansteve </p>
	 */
	public static void main(String[] args) {
	 	Class[] obj = { DiscoveryApplication.class };
		SpringApplication app = new SpringApplication(obj);
		ApplicationContext context = app.run(args);
		String[] activeProfiles = context.getEnvironment().getActiveProfiles();
		for (String activeProfile : activeProfiles) {
			log.info("当前环境: " + activeProfile);
		}
	}
}
