package com.damon.service;

import com.damon.damon.model.Account;
import com.damon.damon.utils.ViewData;

/**
 * Created by damon on 16/3/15.
 */
public interface AccountService {
    ViewData getCount();

    ViewData getAccount(Integer id);

    ViewData addAccount(Account account);


    ViewData getCount_damon();

    ViewData getAccount_damon(Integer id);

    ViewData addAccount_damon(Account account);
}
