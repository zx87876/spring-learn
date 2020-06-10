package com.damon.controller;

import com.damon.damon.service.impl.JedisRedisDataSource;
import com.damon.damon.service.impl.ShardedJedisRedisClientTemplate;
import com.damon.damon.utils.ViewData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by damon on 16/3/15.
 */
@Controller
public class RedisController {


    @Resource
    private ShardedJedisRedisClientTemplate shardedJedisRedisClientTemplate;


    @Resource
    private JedisRedisDataSource jedisRedisDataSource;


    @RequestMapping("/redis/lpop")
    @ResponseBody
    public ViewData get(HttpServletRequest request) {

        ViewData viewData = new ViewData();
        List<String> list = new ArrayList<String>();
        list.add("one");
        list.add("two");
        list.add("three");
        list.add("four");
        list.add("five");
        list.add("six");
        System.out.println(list.toString().replace("[", "").replace("]", ""));
//        for(String s:list){
//            redisClientTemplate.rpush("sycOrders",s);
//        }

        System.out.println("---");
//        HttpSession session = request.getSession();
//        session.setAttribute("damon", "damon.zhang");
        Object object = shardedJedisRedisClientTemplate.lpop("sycOrders");
        viewData.setData(object);
        return viewData;
    }

    @RequestMapping("/redis/session/set")
    @ResponseBody
    public ViewData setSession(HttpServletRequest request) {

        ViewData viewData = new ViewData();
        HttpSession session = request.getSession();
        session.setAttribute("damon", "damon.zhang");
        return viewData;
    }

    @RequestMapping("/redis/session/get")
    @ResponseBody
    public ViewData getSession(HttpServletRequest request) {

        ViewData viewData = new ViewData();
        HttpSession session = request.getSession();
        viewData.setData(session.getAttribute("damon"));
        return viewData;
    }


    @RequestMapping("/redis/session/del")
    @ResponseBody
    public ViewData delSession(HttpServletRequest request) {

        ViewData viewData = new ViewData();
        HttpSession session = request.getSession();
        session.removeAttribute("damon");
        viewData.setData(session.getAttribute("damon"));
        return viewData;
    }

    @RequestMapping("/redis/sync1")
    @ResponseBody
    public ViewData sync1(HttpServletRequest request) {

        ViewData viewData = new ViewData();
        jedisRedisDataSource.syncOrders1();
        return viewData;
    }

    @RequestMapping("/redis/sync2")
    @ResponseBody
    public ViewData sync2(HttpServletRequest request) {

        ViewData viewData = new ViewData();
        jedisRedisDataSource.syncOrders2();
        return viewData;
    }

}
