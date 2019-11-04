package com.cazio.demo.config;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @program jta-atomikos
 * @description 账户库配置
 * @author: ca2io
 * @create: 2019-11-04 10:33
 **/
@Configuration
@MapperScan(basePackages = {"com.cazio.demo.mapper.account"},sqlSessionTemplateRef = "accountSqlSessionTemplate")
public class AccountDbConfig {
    @Bean(name="accountDataSource")
    public DataSource accountDataSource(AccountDBProperties accountDBProperties){
        DruidXADataSource dataSource = new DruidXADataSource();
        BeanUtils.copyProperties(accountDBProperties,dataSource);

        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setXaDataSource(dataSource);
        atomikosDataSourceBean.setUniqueResourceName("accountDataSource");
        return atomikosDataSourceBean;
    }


    @Bean(name = "accountSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("accountDataSource")DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setTypeAliasesPackage("com.cazio.demo.mapper.account");
        return bean.getObject();
    }

    @Bean(name="accountSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("accountSqlSessionFactory")SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
