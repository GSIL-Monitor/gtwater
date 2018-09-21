package com.gt.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * <p>Copyright: All Rights Reserved</p>
 * <p>Description: redis帮助类 </p>
 * <p>Author:steve/han, 16/09/20</p>
 */
@Component("redisHelper")
public class RedisHelper {
    private static final Logger log = LoggerFactory.getLogger(RedisHelper.class);

    // http://www.tuicool.com/articles/JrmYVzA
    public static final String TYPE_VERIFICATION = "register"; // 注册

    public static final String TYPE_LOGIN = "login"; // 登录

    @Autowired
    private JedisPool jedisPool;

    /**
     * <p>method: 添加 </p>
     * <p>Description: 这里用一句话描述这个方法的作用</p>
     * <p>author steve </p>
     * <p>date 2016/11/11 16:52 </p>
     * <p>param redisType 缓存包</p>
     * <p>param key </p>
     * <p>param value </p>
     * <p>return </p>
     */
    public String setDictionary(String redisType, String key, String value) {
        // nxxx的值只能取NX或者XX，如果取NX，则只有当key不存在是才进行set，如果取XX，则只有当key已经存在时才进行set
        // 的值只能取EX或者PX，代表数据过期时间的单位，EX代表秒，PX代表毫秒。
        String result;
        Long time = null;
        // 登录
        if (redisType.equals(TYPE_LOGIN)) {
            time = 15 * 24 * 60 * 60L; // 15天后超时
        }
        if (this.exists(redisType + ":" + key)) {
            result = this.set(redisType + ":" + key, value, "XX", "EX", time);
        }
        else {
            result = this.set(redisType + ":" + key, value, "NX", "EX", time);
        }
        return result;
    }

    /**
     * <p>method: 查询 </p>
     * <p>Description: 这里用一句话描述这个方法的作用</p>
     * <p>author licheng </p>
     * <p>date 2016/11/11 16:52 </p>
     * <p>param redisType 缓存包</p>
     * <p>param key </p>
     * <p>return </p>
     */
    public String getDictionary(String redisType, String key) {
        String jsonData = this.get(redisType + ":" + key);
        return jsonData;
    }

    public void expire(String key, int seconds) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.expire(key,seconds);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        finally {
            jedis.close();
        }
    }
    public String set(String key, String value, String nxxx, String expx, long time) {
        String statusCode = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            statusCode = jedis.set(key, value, nxxx, expx, time);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        finally {
            jedis.close();
        }
        return statusCode;
    }

    public String set(String key, String value) {
        String statusCode = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            statusCode = jedis.set(key, value);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        finally {
            jedis.close();
        }
        return statusCode;
    }

    public String get(String key) {
        String value = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            value = jedis.get(key);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        finally {
            jedis.close();
        }
        return value;
    }

    public boolean exists(String key) {
        boolean flag = false;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            flag = jedis.exists(key);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        finally {
            jedis.close();
        }
        return flag;
    }

    public Long del(String key) {
        Long statusCode = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            statusCode = jedis.del(key);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        finally {
            jedis.close();
        }
        return statusCode;
    }

    /**
     * <p>method: 添加 </p>
     * <p>Description: 这里用一句话描述这个方法的作用</p>
     * <p>author licheng </p>
     * <p>date 2016/11/11 16:52 </p>
     * <p>param redisType 缓存包</p>
     * <p>param key </p>
     * <p>param value </p>
     * <p>return </p>
     */
    public String setSession(String redisType, String key, String value, Long time) {
        // nxxx的值只能取NX或者XX，如果取NX，则只有当key不存在是才进行set，如果取XX，则只有当key已经存在时才进行set
        // 的值只能取EX或者PX，代表数据过期时间的单位，EX代表秒，PX代表毫秒。
        String result;
        // Long time = 60 * 60 * 12L; // 12小时超时
        Long oneHour = 60 * 60L;
        if (this.exists(redisType + ":" + key)) {
            result = this.set(redisType + ":" + key, value, "XX", "EX", oneHour * time);
        }
        else {
            result = this.set(redisType + ":" + key, value, "NX", "EX", oneHour * time);
        }
        return result;
    }

    /**
     * <p>method: 添加 </p>
     * <p>Description: 这里用一句话描述这个方法的作用</p>
     * <p>author steve </p>
     * <p>date 2016/11/11 16:52 </p>
     * <p>param redisType 缓存包</p>
     * <p>param key </p>
     * <p>param value </p>
     * <p>return </p>
     */
    public String setToken(String redisType, String key, String value, Long time) {
        // nxxx的值只能取NX或者XX，如果取NX，则只有当key不存在是才进行set，如果取XX，则只有当key已经存在时才进行set
        // 的值只能取EX或者PX，代表数据过期时间的单位，EX代表秒，PX代表毫秒。
        String result;
        // Long time = 60 * 60 * 24L * 7; // 7天小时超时
        Long oneHour = 60 * 60L;
        if (this.exists(redisType + ":" + key)) {
            result = this.set(redisType + ":" + key, value, "XX", "EX", oneHour * time);
        }
        else {
            result = this.set(redisType + ":" + key, value, "NX", "EX", oneHour * time);
        }
        return result;
    }

    /**
     * <p>method: 查询 </p>
     * <p>Description: 这里用一句话描述这个方法的作用</p>
     * <p>author licheng </p>
     * <p>date 2016/11/11 16:52 </p>
     * <p>param redisType 缓存包</p>
     * <p>param key </p>
     * <p>return </p>
     */
    public String getSession(String redisType, String key) {
        String jsonData = this.get(redisType + ":" + key);
        return jsonData;
    }

    /**
     * <p>Description: 获取token</p>
     * <p>param  </p>
     * <p>author steve </p>
     * <p>date 2017/1/19 14:23 </p>
     * <p>return </p>
     */
    public String getToken(String redisType, String key) {
        String jsonData = this.get(redisType + ":" + key);
        return jsonData;
    }
}
