/**
 * Created on [2020/4/2 10:14] by Administrator
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
package com.cazio.springlenrning.aop.part1;

import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;

/**
 * 功能： TODO(用一句话描述类的功能)
 * <p>
 * ──────────────────────────────────────────
 * version  变更日期    修改人    修改说明
 * ------------------------------------------
 * V1.0.0   2020/4/2   Cazio     初版
 * ──────────────────────────────────────────
 */
public class ForumServiceImpl implements FormService {
    @Override
    public void removeTopic(int topicId) {
//        PerformanceMonitor.begin("com.cazio.springlenrning.aop.part1 removeTopic");
        System.out.println("删除Topic->" + topicId);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        PerformanceMonitor.end();
    }

    @Override
    public void removeForum(int forumId) {
//        PerformanceMonitor.begin("com.cazio.springlenrning.aop.part1 removeForum");
        System.out.println("删除Forum->" + forumId);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        PerformanceMonitor.end();
    }

    public static void main(String[] args) {
        FormService forumService = new ForumServiceImpl();
//        JDK动态代理
//        PerformanceHandler handler = new PerformanceHandler(forumService);
//        FormService obj = (FormService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), forumService.getClass().getInterfaces(), handler);
        //CGlib
        CglibProxy cglibProxy = new CglibProxy();
        ForumServiceImpl forumService1 = (ForumServiceImpl) cglibProxy.getProxy(ForumServiceImpl.class);
        forumService1.removeTopic(1);
        forumService1.removeForum(1024);
    }
}
