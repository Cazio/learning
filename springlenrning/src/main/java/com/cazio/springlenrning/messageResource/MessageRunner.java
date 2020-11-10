/**
 * Created on [2020/4/1 17:19] by Administrator
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
package com.cazio.springlenrning.messageResource;

import org.springframework.cglib.core.Local;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;

import java.text.MessageFormat;
import java.util.EventListener;
import java.util.EventObject;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 功能： TODO(用一句话描述类的功能)
 *
 * ──────────────────────────────────────────
 *   version  变更日期    修改人    修改说明
 * ------------------------------------------
 *   V1.0.0   2020/4/1   Cazio     初版
 * ──────────────────────────────────────────
 */
public class MessageRunner {
    public static void main(String[] args) {
        String pattern1 = "{0},你好！您于{1}在招商银行存入{2}元";
        String pattern2 = "At {1,time,short} On{1,date,long},{0} paid {2,number,currency}";
        Object[] params = {"cazio",new GregorianCalendar().getTime(),1.0E3};
        String message = MessageFormat.format(pattern1,params);
        System.out.println(message);

        MessageFormat messageFormat = new MessageFormat(pattern2, Locale.US);
        String message2 = messageFormat.format(params);
        System.out.println(message2);

    }
}
