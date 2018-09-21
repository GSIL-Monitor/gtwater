package com.gt.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by hansteve on 16/9/1.
 */
@Component
public class StartupRunner implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(StartupRunner.class);

	  @Autowired
	  private RedisHelper redisHelper;
	
	@Override
	public void run(String... strings) throws Exception {
		log.info("CommandLineRunner.........");
		//	log.info("Datasource: " + ReflectionToStringBuilder.toString(dataSource));
		redisHelper.set("a1", "测试redis");
		log.info("jedisCluster: " + redisHelper.get("a1"));

		//redisHelper.setToken("b","b2","test1",2L);
	///	log.info("jedisCluster: " + redisHelper.getToken("b","b2"));

		log.info("CommandLineRunner End");
	}
}
