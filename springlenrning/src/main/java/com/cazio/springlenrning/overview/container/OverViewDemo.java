/**
 * Created on [2020/3/24 21:57] by Administrator
 * <p>
 * 项目名称： springlenrning TODO(项目名称)
 * <p>
 * 本程序版权属于福建慧政通信息科技有限公司所有。
 * 任何组织和个人未经福建慧政通信息科技有限公司许可与授权,不得擅自传播、复制、更改该程序的内容。
 * 本程序受版权法和国际条约的保护。如未经授权而擅自复制或传播本程序(或其中任何部分),
 * 将受到严厉的刑事及民事制裁，并将在法律许可的范围内受到最大可能的起诉!
 * <p>
 * ©2020 福建慧政通信息科技有限公司
 */
package com.cazio.springlenrning.overview.container;

import com.cazio.springlenrning.overview.annotation.Super;
import com.cazio.springlenrning.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.Map;

/**
 * 功能： TODO(用一句话描述类的功能)
 * <p>
 * ──────────────────────────────────────────
 * version  变更日期    修改人    修改说明
 * ------------------------------------------
 * V1.0.0   2020/3/24   Cazio     初版
 * ──────────────────────────────────────────
 */
public class OverViewDemo {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);

        String location = "classpath:META-INF/applicationContext.xml";

        int beanDefintionsCount = reader.loadBeanDefinitions(location);
        System.out.println("bean 数量" + beanDefintionsCount);
        lookupByAnnotation(beanFactory);
    }

    private static void lookupByAnnotation(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            Map<String,User> map =(Map)((ListableBeanFactory) beanFactory).getBeansWithAnnotation(Super.class);
            System.out.println(map);
        }
    }

    private static void lookupCollectionByType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> users = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("查找到所有的User集合对象:" + users);
        }
    }

    private static void lookupInlazy(BeanFactory beanFactory) {
        ObjectFactory<User> objectFactory = (ObjectFactory<User>) beanFactory.getBean("objectFactory");
        User user = objectFactory.getObject();
        System.out.println("延迟查找:" + user);
    }

    private static void lookupByType(BeanFactory beanFactory) {
        User user = beanFactory.getBean(User.class);
        System.out.println("实时查找" + user);
    }

    private static void lookupInRealTime(BeanFactory beanFactory) {
//        User user = beanFactory.getBean("user");
    }
}
