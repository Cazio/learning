package com.cazio.demo.config;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

/**
 * @program jta-atomikos
 * @description XA事务管理器配置星系
 * @author: ca2io
 * @create: 2019-11-04 11:18
 **/
@Configuration
@EnableTransactionManagement
public class XATranactionManagerConfig {

    @Bean(name="userTransaction")
    public UserTransaction userTransaction() throws SystemException {
        UserTransactionImp userTransactionImp = new UserTransactionImp();
        userTransactionImp.setTransactionTimeout(10000);
        return userTransactionImp;
    }

    @Bean(name="atomikosTransactionManager",initMethod = "init",destroyMethod = "close")
    public TransactionManager atomikosTransactionManager(){
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(false);
        return userTransactionManager;
    }

    @Bean(name="transactionManager")
    @DependsOn({"userTransaction","atomikosTransactionManager"})
    public PlatformTransactionManager transactionManager() throws SystemException {
        return new JtaTransactionManager(userTransaction(),atomikosTransactionManager());
    }
}
