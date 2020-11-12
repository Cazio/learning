package com.cazio.springlenrning.bean.factory;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Ca2io
 * @version 1.0
 * @description
 * @since 2020/5/9 11:18
 */
@Component
public class DefaultUserFactory implements UserFactory , InitializingBean {
    @PostConstruct
    public void init(){
        System.out.println("正在初始化");
    }

    public void initUserFactory(){
        System.out.println("自定义初始化方法...");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("输出afterPropertiesSet");
    }
}
