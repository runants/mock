package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author: liuyi on 2020/2/13
 * @Description:
 */
@Configuration
public class RedisConfig {

    @Autowired
    JedisSentinelPool pool;

    private static Jedis jedis;


    @Bean
    public void getRedis(){
        JedisPool jedisPool = pool.redisPoolFactory();
        this.jedis = jedisPool.getResource();
    }

    public static Jedis redisConfig(){
        return jedis;
    }
}
