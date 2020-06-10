package com.damon.controller.aop;

import com.damon.damon.dao.Account_damon_test_Dao;
import com.damon.damon.dao.Account_test_Dao;
import com.damon.damon.utils.MultipleDataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Created by damon on 16/3/16.
 */
@Component
@Aspect
public class MultipleDataSourceAspectAdvice {

    @Around("execution(* com.damon.damon.dao.*.*(..))")
    public Object doAround(ProceedingJoinPoint jp) throws Throwable {
        if (jp.getTarget() instanceof Account_test_Dao) {
            MultipleDataSource.setDataSourceKey("dataSource_test");
        } else if (jp.getTarget() instanceof Account_damon_test_Dao) {
            MultipleDataSource.setDataSourceKey("dataSource_damon_test");
        }
        return jp.proceed();
    }
}