package com.gt.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.Assert;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Method;
import java.nio.charset.Charset;

@Configuration
public class RedisCacheConfig extends CachingConfigurerSupport {

    @Autowired
    private RedisConfigurationProperties redisConfigurationProperties;

    @Bean
    public KeyGenerator wiselyKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName() + "_");
                sb.append(method.getName());

                // 参数
                // for (Object obj : params) {
                // sb.append(obj.toString());
                // }

                return sb.toString();
            }
        };
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(redisConfigurationProperties.getMaxTotal());
        poolConfig.setMaxIdle(redisConfigurationProperties.getMaxIdle());
        poolConfig.setMaxWaitMillis(redisConfigurationProperties.getMaxWaitMillis());
        poolConfig.setTestOnBorrow(redisConfigurationProperties.isTestOnBorrow());
        poolConfig.setTestOnReturn(redisConfigurationProperties.isTestOnReturn());

        return poolConfig;
    }

    @Bean
    public JedisPool jedisPool(JedisPoolConfig jedisPoolConfig) {
        if (StringUtils.isBlank(redisConfigurationProperties.getPassword())) {
            redisConfigurationProperties.setPassword(null);
        }
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, redisConfigurationProperties.getHost(),
                redisConfigurationProperties.getPort(), 0, redisConfigurationProperties.getPassword());

        return jedisPool;
    }

    @Bean
    public JedisConnectionFactory connectionFactory(JedisPoolConfig poolConfig) {
        // JedisPoolConfig poolConfig = new JedisPoolConfig();
        // poolConfig.setMaxTotal(redisConfigurationProperties.getMaxTotal());
        // poolConfig.setMaxIdle(redisConfigurationProperties.getMaxIdle());
        // poolConfig.setMaxWaitMillis(redisConfigurationProperties.getMaxWaitMillis());
        // poolConfig.setTestOnBorrow(redisConfigurationProperties.isTestOnBorrow());
        // poolConfig.setTestOnReturn(redisConfigurationProperties.isTestOnReturn());

        if (StringUtils.isBlank(redisConfigurationProperties.getPassword())) {
            redisConfigurationProperties.setPassword(null);
        }

        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(poolConfig);
        connectionFactory.setHostName(redisConfigurationProperties.getHost());
        connectionFactory.setPort(redisConfigurationProperties.getPort());
        connectionFactory.setPassword(redisConfigurationProperties.getPassword());
        connectionFactory.setUsePool(true);

        return connectionFactory;
    }

    @Bean
    public CacheManager cacheManager(StringRedisTemplate stringRedisTemplate) {
        return new RedisCacheManager(stringRedisTemplate);
    }

    // @Bean
    // public JedisConnectionFactory redisConnectionFactory() {
    // JedisConnectionFactory redisConnectionFactory = new
    // JedisConnectionFactory();
    //
    // // Defaults
    // redisConnectionFactory.setHostName(redisProperties.getHost());
    // redisConnectionFactory.setPort(redisProperties.getPort());
    // return redisConnectionFactory;
    // }

    // @Bean
    // public ExtendedRedisCacheManager cacheManager(StringRedisTemplate
    // stringRedisTemplate) {
    // RedisCachePrefix cachePrefix = new DefaultRedisCachePrefix();
    //
    // ExtendedRedisCacheManager cacheManager = new
    // ExtendedRedisCacheManager(stringRedisTemplate);
    // cacheManager.setUsePrefix(true);
    // // cachePrefix.prefix("prefix");
    // cacheManager.setCachePrefix(cachePrefix);
    // cacheManager.setLoadRemoteCachesOnStartup(true);
    // cacheManager.setDefaultExpiration(1000*60*60); //有效期1小时
    // // cacheManager.setPrefix("prefix"); //前缀
    //
    // return cacheManager;
    // }

    @Bean
    public StringRedisTemplate redisTemplate(JedisConnectionFactory connectionFactory) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate(connectionFactory);
        redisTemplate.setKeySerializer(new RedisKeySerializer());
        redisTemplate.setValueSerializer(new RedisValueSerializer());

        return redisTemplate;
    }

    // @Bean
    // public RedisCacheManager cacheManager(RedisTemplate redisTemplate) {
    // RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
    //
    // // Number of seconds before expiration. Defaults to unlimited (0)
    // cacheManager.setDefaultExpiration(300);
    // return cacheManager;
    // }

    public class RedisKeySerializer implements RedisSerializer<Object> {
        private final Charset charset;

        public RedisKeySerializer() {
            this(Charset.forName("UTF8"));
        }

        public RedisKeySerializer(Charset charset) {
            Assert.notNull(charset);
            this.charset = charset;
        }

        @Override
        public byte[] serialize(Object t) throws SerializationException {
            String string = String.valueOf(t);
            return (string == null ? null : string.getBytes(charset));
        }

        @Override
        public Object deserialize(byte[] bytes) throws SerializationException {
            Object obj = (bytes == null ? null : new String(bytes, charset));
            return obj;
        }
    }

    public class RedisValueSerializer implements RedisSerializer<Object> {
        @Override
        public byte[] serialize(Object source) throws SerializationException {
            if (source == null) {
                return null;
            }
            // 打印类型以供反序列化
            return JSON.toJSONBytes(source, SerializerFeature.WriteClassName);
        }

        @Override
        public Object deserialize(byte[] source) throws SerializationException {
            if (source == null || source.length == 0) {
                return null;
            }

            return JSON.parse(source);
        }
    }
}
