package com.cazio.springlenrning.ioc.overview.dependency.injection;

import com.cazio.springlenrning.ioc.overview.repository.UserRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Ca2io
 * @version 1.0
 * @description 依赖注入
 * @since 2020/11/10 21:02
 */
public class InjectionDemo {
    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("META-INF\\application-injection.xml");
        UserRepository userRepository = beanFactory.getBean(UserRepository.class);

        System.out.println(userRepository.getBeanFactory());

        System.out.println(beanFactory);
    }
}
