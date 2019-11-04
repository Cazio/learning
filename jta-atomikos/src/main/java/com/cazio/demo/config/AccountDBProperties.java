package com.cazio.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program jta-atomikos
 * @description
 * @author: ca2io
 * @create: 2019-11-04 11:02
 **/
@Component
@ConfigurationProperties(prefix = "spring.datasource.druid.accountdb")
@Data
public class AccountDBProperties {
    private String name;
    private String url;
    private  String username;
    private  String password;
    private  Integer initialSize;
    private  Integer minIdle;
    private  Integer  maxActive;
    private  Long maxWait;
    private  Long timeBetweenEvictionRunsMills;
    private  Long minEvictableIdleTimeMills;
    private  String validationQuery;
    private  Long validationQueryTimeout;
    private  Boolean testWhileIdle;
    private  Boolean testOnBorrow;
    private  Boolean testOnReturn;
    private  Boolean poolPreparedStatements;
    private  Boolean useGloballDataSourceStat;
}
