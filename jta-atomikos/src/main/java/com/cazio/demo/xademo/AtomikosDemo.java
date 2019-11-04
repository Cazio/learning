package com.cazio.demo.xademo;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.jdbc.AtomikosDataSourceBean;

import javax.transaction.*;
import java.sql.*;
import java.util.Properties;

/**
 * @program jta-atomikos
 * @description 1.使用Atomikos的实现TM管理器 ，使用TransactionEssentials
 * @author: ca2io
 * @create: 2019-11-01 15:47
 **/
public class AtomikosDemo {
    private static AtomikosDataSourceBean createAtomikosDataSourceBean(String dbName) {
        //连接池基本属性
        Properties p = new Properties();
        p.setProperty("url", "jdbc:mysql://192.168.99.100:3306/" + dbName);
        p.setProperty("user", "root");
        p.setProperty("password", "k12345678");

        //使用AtomikosDataSourceBean封
        AtomikosDataSourceBean dataSourceBean = new AtomikosDataSourceBean();
        //atomikos要求每个AtomikosDataSOurceBean名称，为了方便记忆，设置与DbName相同
        dataSourceBean.setUniqueResourceName(dbName);
        dataSourceBean.setXaDataSourceClassName("com.mysql.cj.jdbc.MysqlXADataSource");
        dataSourceBean.setXaProperties(p);
        return dataSourceBean;
    }

    public static void main(String[] args) {
        AtomikosDataSourceBean ds1 = createAtomikosDataSourceBean("db_user");
        AtomikosDataSourceBean ds2 = createAtomikosDataSourceBean("db_account");

        Connection connection1 = null;
        Connection connection2 = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;

        UserTransaction userTransaction = new UserTransactionImp();

        try {
            userTransaction.begin();
            connection1 = ds1.getConnection();
            ps1 = connection1.prepareStatement("INSERT into user(name) Value(?)", Statement.RETURN_GENERATED_KEYS);
            ps1.setString(1, "cazio");
            ps1.executeUpdate();
            ResultSet resultSet = ps1.getGeneratedKeys();
            int userId = -1;
            while (resultSet.next()) {
                userId = resultSet.getInt(1);//获得自动生成的userId
            }

            //模拟异常，直接进入catch代码块，2个都不会提交
//            int i = 1/0;


            connection2 = ds2.getConnection();
            ps2 = connection2.prepareStatement("INSERT INTO account(user,money)VALUES(?,?)");
            ps2.setInt(1, userId);
            ps2.setDouble(2, 1000000);
            ps2.executeUpdate();

            //执行结束，进行两阶段提交

            userTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                userTransaction.rollback();
            } catch (SystemException ex) {
                ex.printStackTrace();
            }
        }finally {
            try {
                ps1.close();
                ps2.close();
                connection1.close();
                connection2.close();
                ds1.close();
                ds2.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
