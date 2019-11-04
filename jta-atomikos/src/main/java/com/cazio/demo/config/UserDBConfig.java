package com.cazio.demo.config;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
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
 * @description 用户库配置
 * @author: ca2io
 * @create: 2019-11-04 11:06
 **/
@Configuration
@MapperScan(basePackages = {"com.cazio.demo.mapper.user"},sqlSessionTemplateRef = "userSqlSessionTemplate")
public class UserDBConfig {
    @Bean(name="userDataSource")
    @Primary
    public DataSource userDataSource(UserDBProperties userDBProperties){
        DruidXADataSource dataSource = new DruidXADataSource();
        BeanUtils.copyProperties(userDBProperties,dataSource);

        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setXaDataSource(dataSource);
        atomikosDataSourceBean.setUniqueResourceName("userDataSource");
        return atomikosDataSourceBean;
    }


    @Bean(name = "userSqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("userDataSource")DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setTypeAliasesPackage("com.cazio.demo.mapper.user");
        return bean.getObject();
    }

    @Bean(name="userSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("userSqlSessionFactory")SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
