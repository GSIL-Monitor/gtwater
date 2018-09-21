package com.gt.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

/**
 * Created by steve on 16/9/1.
 */
@Component
public class StartupRunner implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(StartupRunner.class);

 //   @Autowired
   // JedisCluster jedisCluster;

 //   @Autowired
  //  private RedisHelper redisHelper;

    @Override
    public void run(String... strings) throws Exception {
      //  log.info("CommandLineRunner.........");
      //  redisHelper.set("a1", "abcd");
     //  log.info("redisHelper: "+redisHelper.get("a1"));

//        redisTemplate.opsForValue().set("", "");
//        redisTemplate.opsForValue().set("ff", "abcd");
//        log.info("RedisTemplate: "+redisTemplate);
//        log.info("RedisTemplate.opsForValue(): "+redisTemplate.opsForValue());
//        RedisClusterConnection connection = connectionFactory.getClusterConnection();
//        connection.set("foo", "foo".getBytes());
//        connection.set("bar", "bar".getBytes());
        log.info("CommandLineRunner End");
    }
}
