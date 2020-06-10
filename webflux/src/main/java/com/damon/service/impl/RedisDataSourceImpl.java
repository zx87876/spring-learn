package com.damon.service.impl;

import com.damon.damon.service.RedisDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;

/**
 * Created by damon on 16/4/18.
 */
@Repository("redisDataSource")
public class RedisDataSourceImpl implements RedisDataSource {

    private static final Logger log = LoggerFactory.getLogger(RedisDataSourceImpl.class);

    @Resource
    private ShardedJedisPool shardedJedisPool;

    @Resource
    private JedisPool jedisPool;


    public ShardedJedis getShardedJedisRedisClient() {
        try {
            ShardedJedis shardJedis = shardedJedisPool.getResource();
            return shardJedis;
        } catch (Exception e) {
            log.error("getShardedJedisRedisClient error", e);
        }
        return null;
    }

    public void returnResource(ShardedJedis shardedJedis) {
        shardedJedisPool.returnResource(shardedJedis);
    }

    public void returnResource(ShardedJedis shardedJedis, boolean broken) {
        if (broken) {
            shardedJedisPool.returnBrokenResource(shardedJedis);
        } else {
            shardedJedisPool.returnResource(shardedJedis);
        }
    }

    @Override
    public Jedis getJedisRedisClient() {
        try {
            Jedis jedis = jedisPool.getResource();
            return jedis;
        } catch (Exception e) {
            log.error("getJedisRedisClient error", e);
        }
        return null;
    }

    @Override
    public void returnResource(Jedis jedis) {
        jedisPool.returnBrokenResource(jedis);

    }

    @Override
    public void returnResource(Jedis jedis, boolean broken) {
        if (broken) {
            jedisPool.returnBrokenResource(jedis);
        } else {
            jedisPool.returnResource(jedis);
        }

    }
}
