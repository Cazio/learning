/**
 * Created on [2020/4/2 14:07] by Administrator
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
package com.cazio.springlenrning.aop.part2.intrtoduction;

import com.cazio.springlenrning.aop.part1.FormService;
import com.cazio.springlenrning.aop.part1.ForumServiceImpl;
import com.cazio.springlenrning.aop.part1.PerformanceMonitor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;

import java.awt.event.PaintEvent;

/**
 * 功能： TODO(用一句话描述类的功能)
 * <p>
 * ──────────────────────────────────────────
 * version  变更日期    修改人    修改说明
 * ------------------------------------------
 * V1.0.0   2020/4/2   Cazio     初版
 * ──────────────────────────────────────────
 */
public class ControllerablePerformMonitor extends DelegatingIntroductionInterceptor implements Monitorable {
    public static final ThreadLocal<Boolean> threadLocal = new ThreadLocal<>();

    @Override
    public void setActive(boolean active) {
        threadLocal.set(active);
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        Object obj = null;
        if (threadLocal.get() != null && threadLocal.get()) {
            PerformanceMonitor.begin(mi.getClass().getName() + "." + mi.getMethod().getName());
            obj = super.invoke(mi);
            PerformanceMonitor.end();
        }else{
            obj = super.invoke(mi);
        }
        return obj;
    }


    @Test
    public void testIntroduction(){
        FormService formService = new ForumServiceImpl();
        ControllerablePerformMonitor monitor = new ControllerablePerformMonitor();
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setInterfaces(Monitorable.class);
        proxyFactory.setTarget(formService);
//        proxyFactory.se
    }
}
