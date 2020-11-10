package com.cazio.springlenrning.ioc.overview.repository;

import com.cazio.springlenrning.ioc.overview.dependency.domain.User;
import lombok.Data;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

/**
 * @author Ca2io
 * @version 1.0
 * @description 用户
 * @since 2020/11/10 20:59
 */
@Data
public class UserRepository {
    private Collection<User> list;
    private BeanFactory beanFactory;
}
