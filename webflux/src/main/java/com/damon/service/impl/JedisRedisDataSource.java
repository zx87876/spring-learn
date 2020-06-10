package com.damon.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by damon on 16/5/9.
 */
@Repository("jedisRedisDataSource")
public class JedisRedisDataSource implements com.damon.damon.service.JedisRedisDataSource {

    @Resource
    private JedisPool jedisPool;

    @Override
    @Scheduled(cron = "0 0/5 8-18 * * *") //周六,周日  每隔60分钟执行一次
    public void syncOrders1() {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.watch("syncOrdersListIsNotEmpty");
            System.out.println("开启 检测 syncOrdersListIsNotEmpty");

            String sysn = jedis.get("syncOrdersListIsNotEmpty");
            if (StringUtils.isBlank(sysn) || "false".equals(sysn)) {
                Transaction tx = jedis.multi();
                System.out.println("sync--false--:" + sysn);

                tx.set("syncOrdersListIsNotEmpty", "true");
                for (int i = 0; i < 10; i++) {
                    int timeout = new Random().nextInt(3000);
                    tx.rpush("syncOrdersList", "damon_" + String.valueOf(timeout));
                    System.out.println("-----:" + "damon_" + timeout);

                }
                Thread.sleep(5000);
                List<Object> result = tx.exec();
                if (result == null || result.isEmpty()) {
                    System.out.println("-----有人抢先更改syncOrdersListIsNotEmpty啦!");
                }
            } else {
                System.out.println("sync--syncOrdersListIsNotEmpty 存在值--:" + sysn);
            }


        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            jedis.unwatch();
            jedisPool.returnBrokenResource(jedis);
        }


    }


    @Override
    @Scheduled(cron = "0 0/2 * * * *") //周六,周日  每隔60分钟执行一次
    public void syncOrders2() {
        Jedis jedis = jedisPool.getResource();
        try {
            String sysn = jedis.get("syncOrdersListIsNotEmpty");
            boolean have = "true".equals(sysn);
            List<String> orderList = new ArrayList<>();
            while (have) {
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(new Random().nextInt(3000));
                    String orderNo = jedis.lpop("syncOrdersList");
                    if (StringUtils.isNotBlank(orderNo))
                        orderList.add(orderNo);
                    else {
                        have = false;
                        jedis.set("syncOrdersListIsNotEmpty", "false");
                        System.out.println("取完啦,设置 syncOrdersListIsNotEmpty 为false");
                        break;
                    }

                }
            }
            System.out.println("----------orderList:" + orderList.toString());

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            jedisPool.returnBrokenResource(jedis);
        }


    }
}
