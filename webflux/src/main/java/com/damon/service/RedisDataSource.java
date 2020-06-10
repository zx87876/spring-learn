package com.damon.service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

/**
 * Created by damon on 16/4/18.
 */
public interface RedisDataSource {
    ShardedJedis getShardedJedisRedisClient();

    void returnResource(ShardedJedis shardedJedis);

    void returnResource(ShardedJedis shardedJedis, boolean broken);



    Jedis getJedisRedisClient();

    void returnResource(Jedis jedis);

    void returnResource(Jedis jedis, boolean broken);
}



