package com.gt.manager;
//package com.zhidian.ecommerce;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.serializer.SerializerFeature;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.cache.interceptor.KeyGenerator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.DefaultRedisCachePrefix;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.cache.RedisCachePrefix;
//import org.springframework.data.redis.connection.RedisClusterConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisClusterConnection;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.serializer.RedisSerializer;
//import org.springframework.data.redis.serializer.SerializationException;
//import org.springframework.util.Assert;
//import redis.clients.jedis.JedisCluster;
//import redis.clients.jedis.JedisPoolConfig;
//
//import java.lang.reflect.Method;
//import java.nio.charset.Charset;
//
//@Configuration
//@SuppressWarnings("SpringJavaAutowiringInspection")
//public class RedisCachingConfigurer extends CachingConfigurerSupport {
//
//    @Autowired
//    RedisClusterConfigurationProperties clusterProperties;
//
//    @Bean
//    public KeyGenerator wiselyKeyGenerator(){
//        return new KeyGenerator() {
//            @Override
//            public Object generate(Object target, Method method, Object... params) {
//                StringBuilder sb = new StringBuilder();
//                sb.append(target.getClass().getName());
//                sb.append(method.getName());
//                for (Object obj : params) {
//                    sb.append(obj.toString());
//                }
//                return sb.toString();
//            }
//        };
//    }
//
//    @Bean
//    public RedisClusterConfiguration redisClusterConfiguration() {
////        Map<String, Object> source = new HashMap();
////        source.put("spring.redis.cluster.nodes", clusterProperties.getNodes());
////        source.put("spring.redis.cluster.timeout", clusterProperties.getTimeout());
////        source.put("spring.redis.cluster.max-redirects", clusterProperties.getMaxRedirects());
////
////        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration(new MapPropertySource("RedisClusterConfiguration", source));
////        clusterConfiguration.setMaxRedirects(clusterProperties.getMaxRedirects());
////
////        return clusterConfiguration;
//
//        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration(clusterProperties.getNodes());
//        clusterConfiguration.setMaxRedirects(clusterProperties.getMaxRedirects());
//
//        return clusterConfiguration;
//    }
//
//	@Bean
//	public JedisConnectionFactory connectionFactory(RedisClusterConfiguration redisClusterConfiguration) {
//        JedisPoolConfig poolConfig = new JedisPoolConfig();
//        poolConfig.setMaxTotal(clusterProperties.getMaxTotal());
//        poolConfig.setMaxIdle(clusterProperties.getMaxIdle());
//        poolConfig.setMaxWaitMillis(clusterProperties.getMaxWaitMillis());
//        poolConfig.setTestOnBorrow(clusterProperties.isTestOnBorrow());
//        poolConfig.setTestOnReturn(clusterProperties.isTestOnReturn());
//
//        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(redisClusterConfiguration, poolConfig);
////        connectionFactory.setDatabase(clusterProperties.getDbIndex());  //3.0集群目前只支持db0
//        connectionFactory.setUsePool(true);
//
//        return connectionFactory;
//	}
//
//    @Bean
//    public JedisClusterConnection jedisClusterConnection(JedisConnectionFactory connectionFactory) {
//        JedisClusterConnection jedisClusterConnection = (JedisClusterConnection)connectionFactory.getConnection();
//
//        return jedisClusterConnection;
//    }
//
//    @Bean
//    public JedisCluster jedisCluster(JedisClusterConnection jedisClusterConnection) {
//        JedisCluster jedisCluster = jedisClusterConnection.getNativeConnection();
//
//        return jedisCluster;
//    }
//
////	@Bean
////	public JedisConnectionFactory redisConnectionFactory() {
////		JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
////
////		// Defaults
////		redisConnectionFactory.setHostName(redisProperties.getHost());
////		redisConnectionFactory.setPort(redisProperties.getPort());
////		return redisConnectionFactory;
////	}
//
//    @Bean
//    public CacheManager cacheManager(StringRedisTemplate stringRedisTemplate) {
//        RedisCacheManager cacheManager = new RedisCacheManager(stringRedisTemplate);
//        cacheManager.setUsePrefix(true);
//        RedisCachePrefix cachePrefix = new DefaultRedisCachePrefix();
//        cachePrefix.prefix("a1");
//        cacheManager.setCachePrefix(cachePrefix);
//
//        return cacheManager;
//    }
//
//    @Bean
//    public StringRedisTemplate redisTemplate(JedisConnectionFactory connectionFactory) {
//        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(connectionFactory);
//        stringRedisTemplate.setKeySerializer(new RedisKeySerializer());
//        stringRedisTemplate.setValueSerializer(new RedisValueSerializer());
//
//        return stringRedisTemplate;
//    }
//
////	@Bean
////	public RedisCacheManager cacheManager(RedisTemplate redisTemplate) {
////		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
////
////	    // Number of seconds before expiration. Defaults to unlimited (0)
////	    cacheManager.setDefaultExpiration(300);
////	    return cacheManager;
////	}
//
//
//    public class RedisKeySerializer implements RedisSerializer<Object> {
//        private final Charset charset;
//
//        public RedisKeySerializer() {
//            this(Charset.forName("UTF8"));
//        }
//
//        public RedisKeySerializer(Charset charset) {
//            Assert.notNull(charset);
//            this.charset = charset;
//        }
//
//        @Override
//        public byte[] serialize(Object t) throws SerializationException {
//            String string = String.valueOf(t);
//            return (string == null ? null : string.getBytes(charset));
//        }
//
//        @Override
//        public Object deserialize(byte[] bytes) throws SerializationException {
//            Object obj = (bytes == null ? null : new String(bytes, charset));
//            return obj;
//        }
//    }
//
//    public class RedisValueSerializer implements RedisSerializer<Object> {
//        @Override
//        public byte[] serialize(Object source) throws SerializationException {
//            if (source == null) {
//                return null;
//            }
//            // 打印类型以供反序列化
//            return JSON.toJSONBytes(source, SerializerFeature.WriteClassName);
//        }
//
//        @Override
//        public Object deserialize(byte[] source) throws SerializationException {
//            if (source == null || source.length == 0) {
//                return null;
//            }
//
//            return JSON.parse(source);
//        }
//    }
//}