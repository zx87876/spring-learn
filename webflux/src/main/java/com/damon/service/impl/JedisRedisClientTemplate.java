package com.damon.service.impl;

import com.damon.damon.service.RedisDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.*;

import javax.annotation.Resource;

/**
 * Created by damon on 16/4/18.
 */
@Repository("jedisRedisClientTemplate")
public class JedisRedisClientTemplate {

    private static final Logger log = LoggerFactory.getLogger(JedisRedisClientTemplate.class);

    @Resource
    private com.damon.damon.service.RedisDataSource redisDataSource;

    public void disconnect() {
        Jedis jedis = redisDataSource.getJedisRedisClient();
        jedis.disconnect();
    }


}