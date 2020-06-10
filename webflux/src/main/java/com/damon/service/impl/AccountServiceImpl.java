package com.damon.service.impl;

import com.damon.damon.dao.Account_damon_test_Dao;
import com.damon.damon.dao.Account_test_Dao;
import com.damon.damon.model.Account;
import com.damon.damon.service.AccountService;
import com.damon.damon.utils.ViewData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by damon on 16/3/15.
 */
@Service("accountService")
public class AccountServiceImpl implements com.damon.damon.service.AccountService {
    @Resource
    private Account_test_Dao accountMapper;

    @Resource
    private Account_damon_test_Dao accountMapper_damon;

    @Override
    public ViewData getCount() {
        ViewData viewData = new ViewData();
        try {
            viewData.setData(accountMapper.getCount());
        } catch (Exception e) {
            e.printStackTrace();
            viewData.error(e.getMessage());
        } finally {
            return viewData;
        }

    }

    @Override
    public ViewData getAccount(Integer id) {
        ViewData viewData = new ViewData();
        try {
            viewData.setData(accountMapper.get());
        } catch (Exception e) {
            e.printStackTrace();
            viewData.error(e.getMessage());
        } finally {
            return viewData;
        }
    }

    @Override
    public ViewData addAccount(Account account) {
        ViewData viewData = new ViewData();
        try {
           accountMapper.add(account);
        } catch (Exception e) {
            e.printStackTrace();
            viewData.error(e.getMessage());
        } finally {
            return viewData;
        }
    }


    @Override
    public ViewData getCount_damon() {
        ViewData viewData = new ViewData();
        try {
            viewData.setData(accountMapper_damon.getCount());
        } catch (Exception e) {
            e.printStackTrace();
            viewData.error(e.getMessage());
        } finally {
            return viewData;
        }

    }

    @Override
    public ViewData getAccount_damon(Integer id) {
        ViewData viewData = new ViewData();
        try {
            viewData.setData(accountMapper_damon.get());
        } catch (Exception e) {
            e.printStackTrace();
            viewData.error(e.getMessage());
        } finally {
            return viewData;
        }
    }

    @Override
    public ViewData addAccount_damon(Account account) {
        ViewData viewData = new ViewData();
        try {
            accountMapper_damon.add(account);
        } catch (Exception e) {
            e.printStackTrace();
            viewData.error(e.getMessage());
        } finally {
            return viewData;
        }
    }
}
