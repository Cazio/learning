package com.cazio.springlenrning.beandefinition;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author Ca2io
 * @version 1.0
 * @description
 * @since 2020/5/9 9:18
 */
@Import(AnnotationBeanDefinitionDemo.Config.class)
public class AnnotationBeanDefinitionDemo {
    public static void main(String[] args) {
        //1创建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
//        applicationContext.register(AnnotationBeanDefinitionDemo.class);
        registerBeanDefinition(applicationContext,"abc_user");
        registerBeanDefinition(applicationContext);
        applicationContext.refresh();
        //2.1通过@Bean方式定义
        //2.2通过@Component方式定义
        //2.3通@Import进行导入
        Map<String, Config> configMap = applicationContext.getBeansOfType(Config.class);
        //关闭
        applicationContext.close();
    }

    public static void registerBeanDefinition(BeanDefinitionRegistry registry, String beanName) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        beanDefinitionBuilder.addPropertyValue("id", 1L)
                .addPropertyValue("name", "Spring Bean Definition");
        if (StringUtils.hasText(beanName)) {
//注册Bean
            registry.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
        } else {
            BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinitionBuilder.getBeanDefinition(), registry);
        }

    }

    public static void registerBeanDefinition(BeanDefinitionRegistry registry) {
        registerBeanDefinition(registry, null);
    }

    @Configuration
    public static class Config {
        @Bean(name = {"user", "user1"})
        public User user() {
            User user = new User();
            user.setId(1L);
            user.setName("Spring 在哪里");
            return user;
        }
    }

}
