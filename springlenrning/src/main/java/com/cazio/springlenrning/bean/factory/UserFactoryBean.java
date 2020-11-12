package com.cazio.springlenrning.bean.factory;

import com.cazio.springlenrning.bean.User;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author Ca2io
 * @version 1.0
 * @description
 * @since 2020/5/9 14:35
 */
public class UserFactoryBean implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        return new User();
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }
}
