package com.damon.service.impl;

import com.damon.damon.service.RedisDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.*;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by damon on 16/4/18.
 */
@Repository("shardedJedisRedisClientTemplate")
public class ShardedJedisRedisClientTemplate {

    private static final Logger log = LoggerFactory.getLogger(ShardedJedisRedisClientTemplate.class);

    @Resource
    private RedisDataSource redisDataSource;

    public void disconnect() {
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        shardedJedis.disconnect();
    }

    /**
     * 设置单个值
     *
     * @param key
     * @param value
     * @return
     */
    public String set(String key, String value) {
        String result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.set(key, value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * 获取单个值
     *
     * @param key
     * @return
     */
    public String get(String key) {
        String result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        boolean broken = false;
        try {
            result = shardedJedis.get(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Boolean exists(String key) {
        Boolean result = false;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.exists(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * 返回 key 所储存的值的类型。
     *
     * @param key
     * @return
     */
    public String type(String key) {
        String result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.type(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * 为 key 设置过期时间
     * 设置成功返回 1 。 当 key 不存在或者不能为 key 设置过期时间时(比如在低于 2.1.3 版本的 Redis 中你尝试更新 key 的过期时间)返回 0
     *
     * @param key
     * @return
     */
    public Long expire(String key, int seconds) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.expire(key, seconds);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * 为 key 设置过期时间
     * 设置成功返回 1 。 当 key 不存在或者不能为 key 设置过期时间时(比如在低于 2.1.3 版本的 Redis 中你尝试更新 key 的过期时间)返回 0 。
     *
     * @param key
     * @param unixTime
     * @return
     */
    public Long expireAt(String key, long unixTime) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.expireAt(key, unixTime);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * 以秒为单位，返回给定 key 的剩余生存时间(TTL, time to live)
     * 当 key 不存在时，返回 -2 。 当 key 存在但没有设置剩余生存时间时，返回 -1 。 否则，以毫秒为单位，返回 key 的剩余生存时间。
     * 注意：在 Redis 2.8 以前，当 key 不存在，或者 key 没有设置剩余生存时间时，命令都返回 -1 。
     *
     * @param key
     * @return
     */
    public Long ttl(String key) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.ttl(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * 对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)
     *
     * @param key
     * @param offset
     * @param value
     * @return
     */
    public boolean setbit(String key, long offset, boolean value) {

        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        boolean result = false;
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.setbit(key, offset, value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * 对 key 所储存的字符串值，获取指定偏移量上的位(bit)。
     *
     * @param key
     * @param offset
     * @return
     */
    public boolean getbit(String key, long offset) {
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        boolean result = false;
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;

        try {
            result = shardedJedis.getbit(key, offset);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * 用 value 参数覆写给定 key 所储存的字符串值，从偏移量 offset 开始
     *
     * @param key
     * @param offset
     * @param value
     * @return
     */
    public long setrange(String key, long offset, String value) {
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        Jedis jedis = new Jedis();
        long result = 0;
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.setrange(key, offset, value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * 返回 key 中字符串值的子字符
     *
     * @param key
     * @param startOffset
     * @param endOffset
     * @return
     */
    public String getrange(String key, long startOffset, long endOffset) {
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        String result = null;
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.getrange(key, startOffset, endOffset);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * - 将给定 key 的值设为 value ，并返回 key 的旧值(old value)。
     *
     * @param key
     * @param value
     * @return 返回给定 key 的旧值。 当 key 没有旧值时，即 key 不存在时，返回 nil 。
     * <p>
     * 当 key 存在但不是字符串类型时，返回一个错误。
     */
    public String getSet(String key, String value) {
        String result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.getSet(key, value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * SET if Not eXists） 命令在指定的 key 不存在时，为 key 设置指定的值。
     *
     * @param key
     * @param value
     * @return
     */
    public Long setnx(String key, String value) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.setnx(key, value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * Redis Setex 命令为指定的 key 设置值及其过期时间。如果 key 已经存在， SETEX 命令将会替换旧的值。
     *
     * @param key
     * @param seconds
     * @param value
     * @return
     */
    public String setex(String key, int seconds, String value) {
        String result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.setex(key, seconds, value);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * Redis Decrby 命令将 key 所储存的值减去指定的减量值。
     * <p>
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECRBY 操作。
     * <p>
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
     * <p>
     * 本操作的值限制在 64 位(bit)有符号数字表示之内。
     *
     * @param key
     * @param integer
     * @return
     */
    public Long decrBy(String key, long integer) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.decrBy(key, integer);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * Redis Decr 命令将 key 中储存的数字值减一。
     * <p>
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECR 操作。
     * <p>
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
     * <p>
     * 本操作的值限制在 64 位(bit)有符号数字表示之内。
     *
     * @param key
     * @return
     */
    public Long decr(String key) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.decr(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * Redis Incrby 命令将 key 中储存的数字加上指定的增量值。
     * <p>
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCRBY 命令。
     * <p>
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
     * <p>
     * 本操作的值限制在 64 位(bit)有符号数字表示之内。
     *
     * @param key
     * @param integer
     * @return
     */
    public Long incrBy(String key, long integer) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.incrBy(key, integer);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * Redis Incr 命令将 key 中储存的数字值增一。
     * <p>
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
     * <p>
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
     * <p>
     * 本操作的值限制在 64 位(bit)有符号数字表示之内。
     *
     * @param key
     * @return
     */
    public Long incr(String key) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.incr(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * 如果 key 已经存在并且是一个字符串， APPEND 命令将 value 追加到 key 原来的值的末尾。
     *
     * @param key
     * @param value
     * @return
     */
    public Long append(String key, String value) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.append(key, value);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public String substr(String key, int start, int end) {
        String result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.substr(key, start, end);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * Redis Hset 命令用于为哈希表中的字段赋值 。
     * <p>
     * 如果哈希表不存在，一个新的哈希表被创建并进行 HSET 操作。
     * <p>
     * 如果字段已经存在于哈希表中，旧值将被覆盖。
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Long hset(String key, String field, String value) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.hset(key, field, value);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * Redis Hget 命令用于返回哈希表中指定字段的值。
     *
     * @param key
     * @param field
     * @return
     */
    public String hget(String key, String field) {
        String result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.hget(key, field);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * Redis Hsetnx 命令用于为哈希表中不存在的的字段赋值 。
     * <p>
     * 如果哈希表不存在，一个新的哈希表被创建并进行 HSET 操作。
     * <p>
     * 如果字段已经存在于哈希表中，操作无效。
     * <p>
     * 如果 key 不存在，一个新哈希表被创建并执行 HSETNX 命令。
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Long hsetnx(String key, String field, String value) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.hsetnx(key, field, value);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * Redis Hmset 命令用于同时将多个 field-value (字段-值)对设置到哈希表中。
     * <p>
     * 此命令会覆盖哈希表中已存在的字段。
     * <p>
     * 如果哈希表不存在，会创建一个空哈希表，并执行 HMSET 操作。
     *
     * @param key
     * @param hash
     * @return
     */
    public String hmset(String key, Map<String, String> hash) {
        String result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.hmset(key, hash);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * Redis Hmget 命令用于返回哈希表中，一个或多个给定字段的值。
     * <p>
     * 如果指定的字段不存在于哈希表，那么返回一个 nil 值。
     *
     * @param key
     * @param fields
     * @return
     */
    public List<String> hmget(String key, String... fields) {
        List<String> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.hmget(key, fields);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * Redis Hincrby 命令用于为哈希表中的字段值加上指定增量值。
     * <p>
     * 增量也可以为负数，相当于对指定字段进行减法操作。
     * <p>
     * 如果哈希表的 key 不存在，一个新的哈希表被创建并执行 HINCRBY 命令。
     * <p>
     * 如果指定的字段不存在，那么在执行命令前，字段的值被初始化为 0 。
     * <p>
     * 对一个储存字符串值的字段执行 HINCRBY 命令将造成一个错误。
     * <p>
     * 本操作的值被限制在 64 位(bit)有符号数字表示之内。
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Long hincrBy(String key, String field, long value) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.hincrBy(key, field, value);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * Redis Hexists 命令用于查看哈希表的指定字段是否存在。
     *
     * @param key
     * @param field
     * @return
     */
    public Boolean hexists(String key, String field) {
        Boolean result = false;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.hexists(key, field);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * 该命令用于在 key 存在是删除 key。
     *
     * @param key
     * @return
     */
    public Long del(String key) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.del(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * Redis Hdel 命令用于删除哈希表 key 中的一个或多个指定字段，不存在的字段将被忽略。
     *
     * @param key
     * @param field
     * @return
     */
    public Long hdel(String key, String field) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.hdel(key, field);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * Redis Hlen 命令用于获取哈希表中字段的数量。
     *
     * @param key
     * @return
     */
    public Long hlen(String key) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.hlen(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * Redis Hkeys 命令用于获取哈希表中的所有字段名。
     *
     * @param key
     * @return
     */
    public Set<String> hkeys(String key) {
        Set<String> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.hkeys(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * Redis Hvals 命令返回哈希表所有字段的值。
     *
     * @param key
     * @return
     */
    public List<String> hvals(String key) {
        List<String> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.hvals(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * Redis Hgetall 命令用于返回哈希表中，所有的字段和值。
     *
     * @param key
     * @return
     */
    public Map<String, String> hgetAll(String key) {
        Map<String, String> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.hgetAll(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    // ================list ====== l表示 list或 left, r表示right====================

    /**
     * Redis Rpush 命令用于将一个或多个值插入到列表的尾部(最右边)。
     * <p>
     * 如果列表不存在，一个空列表会被创建并执行 RPUSH 操作。 当列表存在但不是列表类型时，返回一个错误
     *
     * @param key
     * @param string
     * @return
     */
    public Long rpush(String key, String string) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.rpush(key, string);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * Redis Lpush 命令将一个或多个值插入到列表头部。 如果 key 不存在，一个空列表会被创建并执行 LPUSH 操作。 当 key 存在但不是列表类型时，返回一个错误。
     *
     * @param key
     * @param string
     * @return
     */
    public Long lpush(String key, String string) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.lpush(key, string);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * Redis Llen 命令用于返回列表的长度。 如果列表 key 不存在，则 key 被解释为一个空列表，返回 0 。 如果 key 不是列表类型，返回一个错误。
     *
     * @param key
     * @return
     */
    public Long llen(String key) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.llen(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * Redis Lrange 返回列表中指定区间内的元素，区间以偏移量 START 和 END 指定。
     * 其中 0 表示列表的第一个元素， 1 表示列表的第二个元素，以此类推。 你
     * 也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> lrange(String key, long start, long end) {
        List<String> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.lrange(key, start, end);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * Redis Ltrim 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
     * <p>
     * 下标 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public String ltrim(String key, long start, long end) {
        String result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.ltrim(key, start, end);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * Redis Lindex 命令用于通过索引获取列表中的元素。你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
     *
     * @param key
     * @param index
     * @return
     */
    public String lindex(String key, long index) {
        String result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.lindex(key, index);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * Redis Lset 通过索引来设置元素的值。
     * <p>
     * 当索引参数超出范围，或对一个空列表进行 LSET 时，返回一个错误。
     *
     * @param key
     * @param index
     * @param value
     * @return
     */
    public String lset(String key, long index, String value) {
        String result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.lset(key, index, value);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * Redis Lrem 根据参数 COUNT 的值，移除列表中与参数 VALUE 相等的元素。
     * <p>
     * COUNT 的值可以是以下几种：
     * <p>
     * count > 0 : 从表头开始向表尾搜索，移除与 VALUE 相等的元素，数量为 COUNT 。
     * count < 0 : 从表尾开始向表头搜索，移除与 VALUE 相等的元素，数量为 COUNT 的绝对值。
     * count = 0 : 移除表中所有与 VALUE 相等的值。
     *
     * @param key
     * @param count
     * @param value
     * @return
     */
    public Long lrem(String key, long count, String value) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.lrem(key, count, value);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * Redis Lpop 命令用于移除并返回列表的第一个元素。
     *
     * @param key
     * @return
     */
    public String lpop(String key) {
        String result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.lpop(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * Redis Rpop 命令用于移除并返回列表的最后一个元素。
     *
     * @param key
     * @return
     */
    public String rpop(String key) {
        String result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.rpop(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    //return 1 add a not exist value ,
    //return 0 add a exist value
    public Long sadd(String key, String member) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.sadd(key, member);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<String> smembers(String key) {
        Set<String> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.smembers(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long srem(String key, String member) {
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();

        Long result = null;
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.srem(key, member);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public String spop(String key) {
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        String result = null;
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.spop(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long scard(String key) {
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        Long result = null;
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.scard(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Boolean sismember(String key, String member) {
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        Boolean result = null;
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.sismember(key, member);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public String srandmember(String key) {
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        String result = null;
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.srandmember(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long zadd(String key, double score, String member) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.zadd(key, score, member);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<String> zrange(String key, int start, int end) {
        Set<String> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.zrange(key, start, end);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long zrem(String key, String member) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.zrem(key, member);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Double zincrby(String key, double score, String member) {
        Double result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zincrby(key, score, member);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long zrank(String key, String member) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrank(key, member);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long zrevrank(String key, String member) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrevrank(key, member);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<String> zrevrange(String key, int start, int end) {
        Set<String> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrevrange(key, start, end);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<Tuple> zrangeWithScores(String key, int start, int end) {
        Set<Tuple> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrangeWithScores(key, start, end);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<Tuple> zrevrangeWithScores(String key, int start, int end) {
        Set<Tuple> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrevrangeWithScores(key, start, end);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long zcard(String key) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zcard(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Double zscore(String key, String member) {
        Double result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zscore(key, member);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public List<String> sort(String key) {
        List<String> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.sort(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public List<String> sort(String key, SortingParams sortingParameters) {
        List<String> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.sort(key, sortingParameters);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long zcount(String key, double min, double max) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zcount(key, min, max);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<String> zrangeByScore(String key, double min, double max) {
        Set<String> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrangeByScore(key, min, max);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<String> zrevrangeByScore(String key, double max, double min) {
        Set<String> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrevrangeByScore(key, max, min);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        Set<String> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrangeByScore(key, min, max, offset, count);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
        Set<String> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrevrangeByScore(key, max, min, offset, count);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
        Set<Tuple> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrangeByScoreWithScores(key, min, max);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
        Set<Tuple> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrevrangeByScoreWithScores(key, max, min);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
        Set<Tuple> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrangeByScoreWithScores(key, min, max, offset, count);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
        Set<Tuple> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrevrangeByScoreWithScores(key, max, min, offset, count);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long zremrangeByRank(String key, int start, int end) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zremrangeByRank(key, start, end);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long zremrangeByScore(String key, double start, double end) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zremrangeByScore(key, start, end);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long linsert(String key, BinaryClient.LIST_POSITION where, String pivot, String value) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.linsert(key, where, pivot, value);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public String set(byte[] key, byte[] value) {
        String result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.set(key, value);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public byte[] get(byte[] key) {
        byte[] result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.get(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Boolean exists(byte[] key) {
        Boolean result = false;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.exists(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public String type(byte[] key) {
        String result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.type(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long expire(byte[] key, int seconds) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.expire(key, seconds);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long expireAt(byte[] key, long unixTime) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.expireAt(key, unixTime);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long ttl(byte[] key) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.ttl(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public byte[] getSet(byte[] key, byte[] value) {
        byte[] result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.getSet(key, value);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long setnx(byte[] key, byte[] value) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.setnx(key, value);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public String setex(byte[] key, int seconds, byte[] value) {
        String result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.setex(key, seconds, value);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long decrBy(byte[] key, long integer) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.decrBy(key, integer);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long decr(byte[] key) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.decr(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long incrBy(byte[] key, long integer) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.incrBy(key, integer);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long incr(byte[] key) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.incr(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long append(byte[] key, byte[] value) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.append(key, value);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public byte[] substr(byte[] key, int start, int end) {
        byte[] result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.substr(key, start, end);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long hset(byte[] key, byte[] field, byte[] value) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.hset(key, field, value);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public byte[] hget(byte[] key, byte[] field) {
        byte[] result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.hget(key, field);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long hsetnx(byte[] key, byte[] field, byte[] value) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.hsetnx(key, field, value);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public String hmset(byte[] key, Map<byte[], byte[]> hash) {
        String result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.hmset(key, hash);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public List<byte[]> hmget(byte[] key, byte[]... fields) {
        List<byte[]> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.hmget(key, fields);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long hincrBy(byte[] key, byte[] field, long value) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.hincrBy(key, field, value);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Boolean hexists(byte[] key, byte[] field) {
        Boolean result = false;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.hexists(key, field);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long hdel(byte[] key, byte[] field) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.hdel(key, field);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long hlen(byte[] key) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.hlen(key);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<byte[]> hkeys(byte[] key) {
        Set<byte[]> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.hkeys(key);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Collection<byte[]> hvals(byte[] key) {
        Collection<byte[]> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.hvals(key);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Map<byte[], byte[]> hgetAll(byte[] key) {
        Map<byte[], byte[]> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.hgetAll(key);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long rpush(byte[] key, byte[] string) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.rpush(key, string);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long lpush(byte[] key, byte[] string) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.lpush(key, string);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long llen(byte[] key) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.llen(key);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public List<byte[]> lrange(byte[] key, int start, int end) {
        List<byte[]> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.lrange(key, start, end);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public String ltrim(byte[] key, int start, int end) {
        String result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.ltrim(key, start, end);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public byte[] lindex(byte[] key, int index) {
        byte[] result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.lindex(key, index);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public String lset(byte[] key, int index, byte[] value) {
        String result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.lset(key, index, value);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long lrem(byte[] key, int count, byte[] value) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.lrem(key, count, value);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public byte[] lpop(byte[] key) {
        byte[] result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.lpop(key);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public byte[] rpop(byte[] key) {
        byte[] result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.rpop(key);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long sadd(byte[] key, byte[] member) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.sadd(key, member);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<byte[]> smembers(byte[] key) {
        Set<byte[]> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.smembers(key);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long srem(byte[] key, byte[] member) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.srem(key, member);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public byte[] spop(byte[] key) {
        byte[] result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.spop(key);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long scard(byte[] key) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.scard(key);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Boolean sismember(byte[] key, byte[] member) {
        Boolean result = false;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.sismember(key, member);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public byte[] srandmember(byte[] key) {
        byte[] result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.srandmember(key);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long zadd(byte[] key, double score, byte[] member) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zadd(key, score, member);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<byte[]> zrange(byte[] key, int start, int end) {
        Set<byte[]> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrange(key, start, end);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long zrem(byte[] key, byte[] member) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrem(key, member);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Double zincrby(byte[] key, double score, byte[] member) {
        Double result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zincrby(key, score, member);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long zrank(byte[] key, byte[] member) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrank(key, member);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long zrevrank(byte[] key, byte[] member) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrevrank(key, member);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<byte[]> zrevrange(byte[] key, int start, int end) {
        Set<byte[]> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrevrange(key, start, end);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<Tuple> zrangeWithScores(byte[] key, int start, int end) {
        Set<Tuple> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrangeWithScores(key, start, end);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<Tuple> zrevrangeWithScores(byte[] key, int start, int end) {
        Set<Tuple> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrevrangeWithScores(key, start, end);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long zcard(byte[] key) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zcard(key);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Double zscore(byte[] key, byte[] member) {
        Double result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zscore(key, member);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public List<byte[]> sort(byte[] key) {
        List<byte[]> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.sort(key);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public List<byte[]> sort(byte[] key, SortingParams sortingParameters) {
        List<byte[]> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.sort(key, sortingParameters);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long zcount(byte[] key, double min, double max) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zcount(key, min, max);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<byte[]> zrangeByScore(byte[] key, double min, double max) {
        Set<byte[]> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrangeByScore(key, min, max);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<byte[]> zrangeByScore(byte[] key, double min, double max, int offset, int count) {
        Set<byte[]> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrangeByScore(key, min, max, offset, count);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max) {
        Set<Tuple> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrangeByScoreWithScores(key, min, max);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max, int offset, int count) {
        Set<Tuple> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrangeByScoreWithScores(key, min, max, offset, count);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min) {
        Set<byte[]> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrevrangeByScore(key, max, min);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min, int offset, int count) {
        Set<byte[]> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrevrangeByScore(key, max, min, offset, count);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min) {
        Set<Tuple> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrevrangeByScoreWithScores(key, max, min);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min, int offset, int count) {
        Set<Tuple> result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrevrangeByScoreWithScores(key, max, min, offset, count);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long zremrangeByRank(byte[] key, int start, int end) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zremrangeByRank(key, start, end);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long zremrangeByScore(byte[] key, double start, double end) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zremrangeByScore(key, start, end);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long linsert(byte[] key, BinaryClient.LIST_POSITION where, byte[] pivot, byte[] value) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.linsert(key, where, pivot, value);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public List<Object> pipelined(ShardedJedisPipeline shardedJedisPipeline) {
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        List<Object> result = null;
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.pipelined(shardedJedisPipeline);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Jedis getShard(byte[] key) {
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        Jedis result = null;
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.getShard(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Jedis getShard(String key) {
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        Jedis result = null;
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.getShard(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public JedisShardInfo getShardInfo(byte[] key) {
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        JedisShardInfo result = null;
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.getShardInfo(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public JedisShardInfo getShardInfo(String key) {
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        JedisShardInfo result = null;
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.getShardInfo(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public String getKeyTag(String key) {
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        String result = null;
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.getKeyTag(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Collection<JedisShardInfo> getAllShardInfo() {
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        Collection<JedisShardInfo> result = null;
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.getAllShardInfo();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Collection<Jedis> getAllShards() {
        ShardedJedis shardedJedis = redisDataSource.getShardedJedisRedisClient();
        Collection<Jedis> result = null;
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.getAllShards();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

}