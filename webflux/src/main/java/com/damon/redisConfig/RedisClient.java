package com.damon.redisConfig;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RefreshScope
@Component
public class RedisClient {

    @Value("${spring.redis.database}")
    private int database;

    @Autowired
    private RedisTemplate redisTemplate;


    public Boolean zaddMax5000(String key, String value, double score) {
        try {
            ZSetOperations zSetOperations = redisTemplate.opsForZSet();
            //先变向 查询是否存在此值
            Long valueNo = zSetOperations.reverseRank(key, value);
            if (valueNo == null) {
                Boolean num = zSetOperations.add(key, value, score);
                if (num) {
                    if (zSetOperations.zCard(key) > 5000)
                        //删除掉第一个元素
                        zSetOperations.removeRange(key, 0, 0);
                }
                return num;
            } else{
                return false;
            }


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    public Boolean haveValue(String key, String value) {
        try {
            ZSetOperations zSetOperations = redisTemplate.opsForZSet();
            Long valueNo = zSetOperations.reverseRank(key, value);
            if (valueNo == null) {
                return false;
            } else{
                return true;
            }

        } catch (Exception e) {
            return null;
        }
    }

    public List<String> haveValue(String key, String[] domains) {
        List<String> list = new ArrayList<>();
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        try {
            for (String domain : domains) {
                if (StringUtils.isNotBlank(domain)) {
                    Long valueNo = zSetOperations.reverseRank(key, domain);
                    if (valueNo != null) {
                        list.add(domain);
                    }
                }
            }
            return list;
        } catch (Exception e) {
            return list;
        }
    }
}
