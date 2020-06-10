package com.damon.controller;

import com.damon.damon.model.Account;
import com.damon.damon.service.AccountService;
import com.damon.damon.service.impl.ShardedJedisRedisClientTemplate;
import com.damon.damon.utils.ViewData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Random;

/**
 * Created by damon on 16/3/15.
 */
@Controller
public class UserController {

    @Resource
    private AccountService accountService;

    @Resource
    private ShardedJedisRedisClientTemplate redisClientTemplate;

    @RequestMapping("/query")
    @ResponseBody
    public ViewData queryPage() {
        return accountService.getCount();
    }

    @RequestMapping("/get")
    @ResponseBody
    public ViewData get(HttpServletRequest request) {

        ViewData viewData = new ViewData();
        long i = redisClientTemplate.append("username", "damon");
        System.out.println("---"+i);
        HttpSession session=request.getSession();
        session.setAttribute("damon","damon.zhang");
        viewData.setData(redisClientTemplate.get("username"));
        return viewData;
//        return accountService.getAccount(1);
    }

    @RequestMapping("/add")
    @ResponseBody
    public ViewData add() {
        Account account = new Account();
        Random random = new Random();
        account.setName("damon" + random.nextInt(100));
        account.setAge(10 + random.nextInt(20));
        account.setAddress("address is " + random.nextInt(100));
        account.setEmail("email is " + random.nextInt(100));
        return accountService.addAccount(account);
    }

    @RequestMapping("/damon/query")
    @ResponseBody
    public ViewData queryPage_damon() {
        return accountService.getCount_damon();
    }

    @RequestMapping("/damon/get")
    @ResponseBody
    public ViewData get_damon() {
        return accountService.getAccount_damon(1);
    }

    @RequestMapping("/damon/add")
    @ResponseBody
    public ViewData add_damon() {
        Account account = new Account();
        Random random = new Random();
        account.setName("damon" + random.nextInt(100));
        account.setAge(10 + random.nextInt(20));
        account.setAddress("address is " + random.nextInt(100));
        account.setEmail("email is " + random.nextInt(100));
        return accountService.addAccount_damon(account);
    }
}
