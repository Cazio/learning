/**
 * Created on [2020/4/2 11:28] by Administrator
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
package com.cazio.springlenrning.aop.part2;

import org.junit.Test;
import org.springframework.aop.AfterAdvice;
import org.springframework.aop.BeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;

/**
 * 功能： TODO(用一句话描述类的功能)
 * <p>
 * ──────────────────────────────────────────
 * version  变更日期    修改人    修改说明
 * ------------------------------------------
 * V1.0.0   2020/4/2   Cazio     初版
 * ──────────────────────────────────────────
 */
public class BeforeAdviceTest {
    @Test
    public void before() {
        Waiter waiter = new NativeWaiter();
        BeforeAdvice advice = new GreetingBeforeAdvice();
        AfterAdvice afterAdvice = new GreetingAfterAdvice();
        //1.ProxyFactory动态代理
        ProxyFactory pf = new ProxyFactory();
        //2.代理类 使用Cglib来进行代理
        pf.setTarget(waiter);
        //3.接口代理方式使用JDK动态代理来进行代理
        pf.setInterfaces(waiter.getClass().getInterfaces());
        //4.设置优化后 原来使用JDK动态代理的的会使用Cglib
        pf.setOptimize(true);
        pf.addAdvice(advice);
        pf.addAdvice(afterAdvice);
        Waiter proxy = (Waiter) pf.getProxy();
        proxy.greetTo("John");
        proxy.severTo("Tom");
    }
}
