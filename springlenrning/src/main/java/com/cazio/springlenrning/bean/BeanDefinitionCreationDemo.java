package com.cazio.springlenrning.bean;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * @author Ca2io
 * @version 1.0
 * @description Bean定义原数据信息
 * @since 2020/11/11 19:21
 */
public class BeanDefinitionCreationDemo {
    public static void main(String[] args) {
        //1.通过BeanDefinitionBuilder进行构建
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        //通过属性配置
        beanDefinitionBuilder.addPropertyValue("age", 24);
        //获取BeanDefinition实例
        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();

        //2.通过AbstactBeanDefiniiton进行构建
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        genericBeanDefinition.setBeanClass(User.class);
        MutablePropertyValues properties = new MutablePropertyValues();
        properties.add("id", 1);
        genericBeanDefinition.setPropertyValues(properties);
    }
}
