/**
 * Created on [2020/4/2 11:24] by Administrator
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

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * 功能： TODO(用一句话描述类的功能)
 * <p>
 * ──────────────────────────────────────────
 * version  变更日期    修改人    修改说明
 * ------------------------------------------
 * V1.0.0   2020/4/2   Cazio     初版
 * ──────────────────────────────────────────
 */
public class GreetingBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        String clientName = (String) args[0];
        System.out.println("How are you!Mr." + clientName);
    }
}
