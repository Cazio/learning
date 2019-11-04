package com.cazio.demo.xademo;

import com.mysql.cj.jdbc.JdbcConnection;
import com.mysql.cj.jdbc.MysqlXAConnection;
import com.mysql.cj.jdbc.MysqlXid;

import javax.sql.XAConnection;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @program jta-atomikos
 * @description MysqlXA协议的实现类
 * @author: ca2io
 * @create: 2019-11-01 14:30
 **/
public class MysqlXAConnectionTest {
    public static void main(String[] args) throws SQLException {
        //true表示打印XA语句,用于调试
        boolean logXaCommands = true;
        //获得资源管理器操作接口实例 RM1
        Connection conn1 = DriverManager.getConnection("jdbc:/mysql://localhost:3006/test1","root","");
        XAConnection xaConnection1 = new MysqlXAConnection((JdbcConnection) conn1,logXaCommands);
        XAResource rm1 = xaConnection1.getXAResource();

        //获得资源管理器操作接口实例 RM1
        Connection conn2 = DriverManager.getConnection("jdbc:/mysql://localhost:3006/test2","root","");
        XAConnection xaConnection2 = new MysqlXAConnection((JdbcConnection) conn1,logXaCommands);
        XAResource rm2 = xaConnection2.getXAResource();
        //AP请求TM执行一个分布式事务，TM生成全局事务id

        byte[] gtrid = "g12345".getBytes();
        int formatId = 1;
        Xid xid1 = null;
        Xid xid2 = null;
        try {
            //---------------分别执行RM1和RM2上的事务分支---------------------
            //TM生成rm1上的事务分支od
            byte[] bqual1 = "b00001".getBytes();
            xid1= new MysqlXid(gtrid,bqual1,formatId);
            rm1.start(xid1,XAResource.TMNOFLAGS);
            PreparedStatement ps1 = conn1.prepareStatement("INSERT INTO USER(NAME) VALUES('我是A')");
            ps1.execute();
            rm1.end(xid1,XAResource.TMSUCCESS);

            //TM生成rm1上的事务分支od
            byte[] bqual2 = "b00002".getBytes();
            xid2 = new MysqlXid(gtrid,bqual2,formatId);
            rm2.start(xid1,XAResource.TMNOFLAGS);
            PreparedStatement ps2 = conn2.prepareStatement("INSERT INTO USER(NAME) VALUES('我是A')");
            ps2.execute();
            rm2.end(xid1,XAResource.TMSUCCESS);

            //================两阶段提交=======================
            //phase1:询问所有的RM准备提交事务分支
            int rm1_prepare = rm1.prepare(xid1);
            int rm2_prepare = rm2.prepare(xid2);
            //phase2:提交所有的分支
            boolean onePhase = false;//TM判断有两个事务分支，所以不能优化为一阶段提交
            if(rm1_prepare == XAResource.XA_OK && rm2_prepare == XAResource.XA_OK){
                //表示所有的事务都提交成功，开始提交所有事务
                rm1.commit(xid1,onePhase);
                rm1.commit(xid2,onePhase);
            }else{
                rm1.rollback(xid1);
                rm1.rollback(xid2);
            }
        } catch (XAException e) {
            if(xid1 != null){
                try {
                    rm1.rollback(xid1);
                } catch (XAException ex) {
                    ex.printStackTrace();
                }
            }
            if(xid2 != null){
                try {
                    rm1.rollback(xid2);
                } catch (XAException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }
    }
}
