package com.cazio.springlenrning.bean;

import com.cazio.springlenrning.bean.factory.DefaultUserFactory;
import com.cazio.springlenrning.bean.factory.UserFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author Ca2io
 * @version 1.0
 * @description
 * @since 2020/5/9 10:55
 */
//@Import(SpecialBeanInstantiationDemo.Config.class)
public class SpecialBeanInstantiationDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.register(SpecialBeanInstantiationDemo.class);
        annotationConfigApplicationContext.refresh();
        BeanFactory beanFactory = annotationConfigApplicationContext.getBeanFactory();
        AutowireCapableBeanFactory autowireCapableBeanFactory = annotationConfigApplicationContext.getAutowireCapableBeanFactory();
//        ServiceLoader userFactoryServiceLoader = beanFactory.getBean(ServiceLoader.class);
//        UserFactory userFactory = (UserFactory) beanFactory.getBean(UserFactory.class);
//        System.out.println("userFactory" + userFactory);
//        demoServiceLoader();

        UserFactory userFactory = autowireCapableBeanFactory.createBean(DefaultUserFactory.class);
        System.out.println(userFactory.createUser());
//        displayServiceLoader(userFactoryServiceLoader);
        annotationConfigApplicationContext.close();
    }

    public static void demoServiceLoader() {
        ServiceLoader<UserFactory> serviceLoader = ServiceLoader.load(UserFactory.class, Thread.currentThread().getContextClassLoader());
        displayServiceLoader(serviceLoader);
    }

    public static void displayServiceLoader(ServiceLoader<UserFactory> serviceLoader) {
        Iterator<UserFactory> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            UserFactory userFactory = iterator.next();
            System.out.println(userFactory.createUser());
        }
    }

    //    public static class Config {
    @Bean(initMethod = "initUserFactory")
    public UserFactory getFactory() {
        return new DefaultUserFactory();
    }
//    }

//    @Bean
//    public ServiceLoaderFactoryBean userFactoryServiceLoader() throws Exception {
//        ServiceLoaderFactoryBean serviceLoaderFactoryBean = new ServiceLoaderFactoryBean();
//        serviceLoaderFactoryBean.setServiceType(UserFactory.class);
//        return serviceLoaderFactoryBean;
//    }
    public static void registryMyBean(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        UserFactory userFactory = new DefaultUserFactory();
        SingletonBeanRegistry singletonBeanRegistry = applicationContext.getBeanFactory();
        singletonBeanRegistry.registerSingleton("userFactory",singletonBeanRegistry);
        applicationContext.refresh();

        UserFactory userFactory1 = (UserFactory) applicationContext.getBean("userFactory");
    }
}
